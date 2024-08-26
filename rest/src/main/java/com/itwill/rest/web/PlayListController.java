package com.itwill.rest.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itwill.rest.domain.Artist;
import com.itwill.rest.domain.Group;
import com.itwill.rest.domain.PlayList;
import com.itwill.rest.domain.PlayListSong;
import com.itwill.rest.domain.Song;
import com.itwill.rest.dto.playlist.PlayListCreateDto;
import com.itwill.rest.dto.playlist.PlayListFirstAlbumImgDto;
import com.itwill.rest.dto.playlist.PlayListSongInfoDto;
import com.itwill.rest.service.AlbumSongsService;
import com.itwill.rest.service.PlayListService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PlayListController {
	
	private final PlayListService playListSvc;
	
	private final AlbumSongsService albumSongSvc;
	
	@GetMapping("/playlists/playlist")
	public void playlist(@RequestParam(name = "plistId") Integer pListId, Model model) {
		log.info("playlist(plistId={})", pListId);
		
		PlayList playList = playListSvc.getPlayListInfoByListId(pListId);
		
		model.addAttribute("playList", playList);
	}
	
	@GetMapping("/getPlayList/{id}")
	@ResponseBody
	public ResponseEntity<List<PlayListFirstAlbumImgDto>> getPlayList(@PathVariable Integer id) {
		log.info("getPlayList(id={})", id);
		
		List<PlayListFirstAlbumImgDto> result = playListSvc.getPlayListByUserId(id);
		
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/getPlayListSong/{id}")
	@ResponseBody
	public ResponseEntity<List<PlayListSongInfoDto>> getPlayListSong(@PathVariable Integer id) {
		log.info("getPlayListSong(id={})", id);
		
		// 1. 플레이리스트의 곡 리스트 조회
		List<PlayListSong> playListSongs = playListSvc.getSongByPlayListId(id);  // getPlayListSongsById 메서드를 추가하여 PlayListSong 리스트를 가져옴
		List<PlayListSongInfoDto> list = new ArrayList<>();
		
		for (PlayListSong playListSong : playListSongs) {
			Song song = playListSong.getSong();
			PlayList playList = playListSong.getPlayList();

			// 2.1 곡의 아티스트 및 그룹 정보를 조회
			List<Song> innerSong = new ArrayList<>();
			innerSong.add(song);
			
			Map<Song, List<Object>> songAndArtists = albumSongSvc.getArtistsOrGroupsBySongsAndRoleId(innerSong, 10);
			List<Artist> sortedArtists = albumSongSvc.getSortedArtists(innerSong, 10);
			List<Group> sortedGroups = albumSongSvc.getSortedGroups(innerSong, 10);
			
			List<Artist> artists = null;
			if (!sortedGroups.isEmpty()) {
				List<Artist> groupMembers = sortedGroups.stream()
					.flatMap(group -> group.getGroupMembers().stream().map(gm -> gm.getArtist()))
					.collect(Collectors.toList());
				
				artists = sortedArtists.stream()
					.filter(artist -> groupMembers.stream().noneMatch(member -> member.equals(artist)))
					.collect(Collectors.toList());
			} else {
				artists = sortedArtists;
			}
			
			// DTO 생성
			List<Integer> artistIds = artists.stream().map(a -> a.getId()).collect(Collectors.toList());
			List<String> artistNames = artists.stream().map(a -> a.getArtistName()).collect(Collectors.toList());
			List<Integer> groupIds = sortedGroups != null ? sortedGroups.stream().map(g -> g.getId()).collect(Collectors.toList()) : null;
			List<String> groupNames = sortedGroups != null ? sortedGroups.stream().map(g -> g.getGroupName()).collect(Collectors.toList()) : null;
			
			PlayListSongInfoDto dto = PlayListSongInfoDto.builder()
				.pListId(playList.getPListId())
				.songId(song.getSongId())
				.createdTime(playListSong.getCreatedTime())  // 여기서 createdTime 가져오기
				.title(song.getTitle())
				.albumId(song.getAlbum().getAlbumId())
				.albumImage(song.getAlbum().getAlbumImage())  // Assuming album has an image
				.albumName(song.getAlbum().getAlbumName())
				.artistId(artistIds)
				.artistName(artistNames)
				.groupId(groupIds)
				.groupName(groupNames)
				.build();
			
			list.add(dto);
		}
		
		return ResponseEntity.ok(list);
	}
	
	@PostMapping("/addPlayList")
	@ResponseBody
	public ResponseEntity<PlayList> addPlayList(@RequestBody PlayListCreateDto dto) {
		log.info("addPlayList()");
		
		PlayList entity = playListSvc.create(dto);
		log.info("save 결과: {}", entity);
		
		return ResponseEntity.ok(entity);
	}
	
	@DeleteMapping("/deletePlayList/{pListId}")
	@ResponseBody
	public ResponseEntity<Integer> deleteByListId(@PathVariable Integer pListId) {
		log.info("deleteById(pListId={})", pListId);
		
		playListSvc.deleteByListId(pListId);
		
		return ResponseEntity.ok(pListId);
	}
	
	@DeleteMapping("/deletePlayListSong/{pListId}/{songId}/{createdTime}")
	@ResponseBody
	public ResponseEntity<Integer> deleteListSongBySongId(
			@PathVariable Integer pListId, @PathVariable Integer songId, @PathVariable String createdTime) {
		log.info("deleteListSongBySongId(pListId={})", pListId);
		
		LocalDateTime localDateTime =  LocalDateTime.parse(createdTime, DateTimeFormatter.ISO_DATE_TIME);
		
		playListSvc.deleteListSongBySongId(pListId, songId, localDateTime);
		
		return ResponseEntity.ok(pListId);
	}

}
