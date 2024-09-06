package com.itwill.rest.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itwill.rest.domain.User;
import com.itwill.rest.dto.AlbumSearchResultDto;
import com.itwill.rest.dto.GroupAndArtistDto;
import com.itwill.rest.dto.LoadMoreDataDto;
import com.itwill.rest.dto.SongSearchResultDto;
import com.itwill.rest.service.AlbumSongsService;
import com.itwill.rest.service.ArtistService;
import com.itwill.rest.service.SongService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

	private final SongService songSvc;
	private final AlbumSongsService albumSvc;
	private final ArtistService artSvc;
	
	@GetMapping("/all")
	public String searchAll(@RequestParam String keyword, Model model, Authentication authentication) {
		log.info("searchAll");
		
		List<SongSearchResultDto> songs = songSvc.searchAllSongs(keyword);
		List<AlbumSearchResultDto> albums = albumSvc.searchAllAlbums(keyword);
		List<GroupAndArtistDto> artists = artSvc.searchAllArtist(keyword);
		List<GroupAndArtistDto> groups = artSvc.searchAllGroups(keyword);
		
		
		// 리스트가 null인지 확인하고, null일 경우 빈 리스트로 초기화
		artists = (artists != null) ? artists : new ArrayList<>();
		groups = (groups != null) ? groups : new ArrayList<>();

		// 두 리스트를 합치기
		List<GroupAndArtistDto> combinedList = new ArrayList<>();
		combinedList.addAll(artists);
		combinedList.addAll(groups);

		// combinedList가 비어 있는지 확인하고, 비어 있지 않으면 정렬
		if (!combinedList.isEmpty()) {
		    combinedList.sort((dto1, dto2) -> Integer.compare(dto2.getLikeCount(), dto1.getLikeCount()));
		}
	    int size = combinedList.size();
        List<GroupAndArtistDto> top5List;
        
        if (size > 5) {
            top5List = combinedList.subList(0, 5); // 상위 5개 원소 추출
        } else {
            top5List = new ArrayList<>(combinedList); // 원본 리스트의 모든 원소를 복사
        }

        
        int firstSong = (songs != null && !songs.isEmpty()) ? songs.getFirst().getTitle().length() : 1000;
		int firstAlbum = (albums != null && !albums.isEmpty()) ? albums.getFirst().getAlbumName().length() : 1000;
		int firstArtist = (combinedList != null && !combinedList.isEmpty()) ? combinedList.get(0).getName().length() : 1000;
		int min = Math.min(Math.min(firstArtist, firstAlbum), firstSong);
		
		log.info("{}",songs);
		log.info("{}",albums);
		log.info("{}",combinedList);
		log.info("{}",min);
		
		String first = "";
		
		int s = (songs != null && !songs.isEmpty()) ? songs.getFirst().getLikeCount() : 0;
		int al = (albums != null && !albums.isEmpty()) ? albums.getFirst().getLikeCount() : 0;
		int ar = (combinedList != null && !combinedList.isEmpty()) ? combinedList.get(0).getLikeCount() : 0;
		
		// 길이가 같을시 좋아요 순 
		if(firstAlbum == firstSong && firstAlbum != 1000) {
			int max = Math.max(al, ar);
			if (max == s) {
			    first = "song";
			} else if (max == al) {
			    first = "album";
			} else if (max == ar) {
			    first = "artist";
			} 
		}
		
		if(firstAlbum == firstSong && firstAlbum != 1000) {
			int max = Math.max(s, al);
			if (max == s) {
			    first = "song";
			} else if (max == al) {
			    first = "album";
			} else if (max == ar) {
			    first = "artist";
			} 
		}
		
		if(firstArtist == firstSong && firstArtist != 1000) {
			int max = Math.max(s, ar);
			if (max == s) {
			    first = "song";
			} else if (max == al) {
			    first = "album";
			} else if (max == ar) {
			    first = "artist";
			} 
		}
		
		if(first == "" && !(firstArtist != 1000 && firstSong != 1000 && firstAlbum != 1000)) {
			if (min == firstSong) {
			    first = "song";
			} else if (min == firstAlbum) {
			    first = "album";
			} else if (min == firstArtist) {
			    first = "artist";
			} 
		}
		
		log.info("max = {}",first);
		Object firstObj = null;
		if(first.equals("song") && !songs.isEmpty()) {
			firstObj = songs.getFirst();
		} else if(first.equals("album") && !albums.isEmpty()) {
			firstObj = albums.getFirst();
		} else if(first.equals("artist") && !combinedList.isEmpty()) {
			firstObj = combinedList.getFirst();
		}
		
		log.info("firstObj={}",firstObj);
		
		model.addAttribute("first", first);
		model.addAttribute("firstObj", firstObj);
		model.addAttribute("songs", songs);
		model.addAttribute("albums", albums);
		model.addAttribute("artists", top5List);
		
		Integer loginUserId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            loginUserId = user.getId();
        }
        model.addAttribute("loginUserId", loginUserId);
        
        return "search/all";
	}
	
	@GetMapping("/songs")
	public void searchSongs(@RequestParam String keyword, @RequestParam(defaultValue = "accuracy") String sortType, Model model, Authentication authentication) {
		
		Integer loginUserId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            loginUserId = user.getId();
        }
        model.addAttribute("loginUserId", loginUserId);
        
        
		List<SongSearchResultDto> songs = songSvc.searchSongs(keyword, sortType, 20, 0);
		model.addAttribute("songs", songs);
	}
	
	@GetMapping("/rest/songs")
	@ResponseBody
	public List<SongSearchResultDto> loadMoreSongs(LoadMoreDataDto dto) {
		log.info("restsongs");
		
		
		return songSvc.searchSongs(dto.getKeyword(), dto.getSortType(), 20, dto.getOffset());
	}
	
	@GetMapping("/albums")
	public void searchAlbums(@RequestParam String keyword, 
			@RequestParam(defaultValue = "accuracy") String sortType, Model model, Authentication authentication) {
		
		Integer loginUserId = null;
		if (authentication != null && authentication.isAuthenticated()) {
			User user = (User) authentication.getPrincipal();
			loginUserId = user.getId();
		}
		model.addAttribute("loginUserId", loginUserId);
		 
		model.addAttribute("albums", albumSvc.searchAlbums(keyword, sortType, 0));
		
	}
	
	@GetMapping("/rest/albums")
	@ResponseBody
	public List<AlbumSearchResultDto> loadMoreAlbums(LoadMoreDataDto dto) {
		log.info("restalbums");
		
		
		return albumSvc.searchAlbums(dto.getKeyword(), dto.getSortType(), dto.getOffset());
	}
	
	@GetMapping("/artists")
	public void searchArtists(@RequestParam String keyword, 
			@RequestParam(defaultValue = "accuracy") String sortType, Model model, Authentication authentication) {
		
		Integer loginUserId = null;
		if (authentication != null && authentication.isAuthenticated()) {
			User user = (User) authentication.getPrincipal();
			loginUserId = user.getId();
		}
		
		model.addAttribute("loginUserId", loginUserId);
		
		List<GroupAndArtistDto> artists = artSvc.searchArtists(keyword, sortType, 0);
		
		model.addAttribute("artists", artists);
		
		log.info("=={}", artists);
	}
	
	@GetMapping("/rest/artists")
	@ResponseBody
	public List<GroupAndArtistDto> loadMoreArtists(LoadMoreDataDto dto) {
		log.info("restartists");
		
		
		return artSvc.searchArtists(dto.getKeyword(), dto.getSortType(), dto.getOffset());
	}
	
	
}
