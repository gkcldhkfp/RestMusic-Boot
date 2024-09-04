package com.itwill.rest.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.itwill.rest.domain.Album;
import com.itwill.rest.domain.ArtistRole;
import com.itwill.rest.domain.Group;
import com.itwill.rest.domain.GroupMember;
import com.itwill.rest.domain.Like;
import com.itwill.rest.domain.LikeId;
import com.itwill.rest.domain.Song;
import com.itwill.rest.dto.SongChartDto;
import com.itwill.rest.dto.SongDetailsDto;
import com.itwill.rest.dto.SongLikeDto;
import com.itwill.rest.repository.AlbumRepository;
import com.itwill.rest.repository.ArtistRoleRepository;
import com.itwill.rest.repository.GroupMemberRepository;
import com.itwill.rest.repository.LikeRepository;
import com.itwill.rest.repository.SongLikeRepository;
import com.itwill.rest.repository.SongRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class SongService {

	private final SongRepository songRepo;
    private final SongLikeRepository songLikeRepo;
    private final ArtistRoleRepository artistRoleRepo;
    private final GroupMemberRepository groupMemberRepo;
    private final AlbumRepository albumRepo;
    private final LikeRepository likeRepo;

    // top30
    @Transactional(readOnly = true)
    public List<SongChartDto> getTopSongs() {
        // 좋아요 수 기준으로 정렬된 상위 30개의 노래를 조회
        List<Song> topSongs = songRepo.findByOrderByLikesCountDesc().stream()
                                      .limit(30)
                                      .collect(Collectors.toList());

        // 상위 30곡을 SongChartDto로 변환하여 반환
        return topSongs.stream()
                .map(this::convertToSongChartDto)
                .collect(Collectors.toList());
    }
	
    // 장르별 차트(전체) 페이징 처리
    @Transactional(readOnly = true)
    public Page<SongChartDto> getAllSongs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        // 좋아요 수 기준으로 페이징된 곡 목록을 조회
        Page<Song> songsPage = songRepo.findByOrderByLikesCountDesc(pageable);

        // 페이징된 곡 목록을 SongChartDto로 변환하여 반환
        return mapToSongChartDto(songsPage, pageable);
    }

    // 장르별 차트 페이징 처리
    @Transactional(readOnly = true)
    public Page<SongChartDto> getSongsByGenre(String genreName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        // 특정 장르의 곡을 좋아요 수 기준으로 페이징된 목록 조회
        Page<Song> songsPage = songRepo.findByGenreNameOrderByLikesCountDesc(genreName, pageable);

        // 페이징된 곡 목록을 SongChartDto로 변환하여 반환
        return mapToSongChartDto(songsPage, pageable);
    }
    
    // 최신 음악 페이징 처리
    @Transactional(readOnly = true)
    public Page<SongChartDto> getNewestSongs(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
        // 최신 음악을 발매일 기준으로 페이징된 목록 조회
        Page<Song> songPage = songRepo.findByOrderByAlbum_AlbumReleaseDateDesc(pageable);

        // 페이징된 곡 목록을 SongChartDto로 변환하여 반환
        return mapToSongChartDto(songPage, pageable);
    }
    
    // Song 엔티티를 SongChartDto로 변환하여 반환하는 메서드
    private Page<SongChartDto> mapToSongChartDto(Page<Song> songsPage, Pageable pageable) {
        List<Song> songs = songsPage.getContent();
        List<SongChartDto> songChartDtos = songs.stream()
                .map(this::convertToSongChartDto)
                .collect(Collectors.toList());

        return new PageImpl<>(songChartDtos, pageable, songsPage.getTotalElements());
    }

    // Song 엔티티를 SongChartDto로 변환하는 보조 메서드
    private SongChartDto convertToSongChartDto(Song song) {
        SongChartDto dto = new SongChartDto();
        dto.setSongId(song.getSongId());
        dto.setTitle(song.getTitle());
        dto.setLikes(songLikeRepo.countByLikeIdSongId(song.getSongId()));
        dto.setSongPath(song.getSongPath());
        dto.setVideoLink(song.getVideoLink());

        // 리스트 필드들을 빈 ArrayList로 초기화하여 null 방지
        dto.setArtistIds(new ArrayList<>());
        dto.setArtistNames(new ArrayList<>());
        dto.setGroupIds(new ArrayList<>());
        dto.setGroupNames(new ArrayList<>());

        // Album 정보 추가
        Album album = albumRepo.findById(song.getAlbum().getAlbumId()).orElse(null);
        if (album != null) {
            dto.setAlbumId(album.getAlbumId());
            dto.setAlbumImage(album.getAlbumImage());
            dto.setAlbumName(album.getAlbumName());
            dto.setAlbumReleaseDate(album.getAlbumReleaseDate());
        }

        // 아티스트와 그룹 정보 추가
        List<ArtistRole> artistRoles = artistRoleRepo.findBySongAndRoleCode_RoleId(song, 10);
        for (ArtistRole artistRole : artistRoles) {
            Integer artistId = artistRole.getArtist().getId();
            String artistName = artistRole.getArtist().getArtistName();
            dto.getArtistIds().add(artistId);
            dto.getArtistNames().add(artistName);

            // 그룹 정보 추가
            List<GroupMember> groupMembers = groupMemberRepo.findByArtist_Id(artistId);
            for (GroupMember groupMember : groupMembers) {
                Group group = groupMember.getGroup();
                if (group != null && !dto.getGroupIds().contains(group.getId())) {
                    dto.getGroupIds().add(group.getId());
                    dto.getGroupNames().add(group.getGroupName());
                }
            }
        }

        return dto;
    }
    
    
	public SongDetailsDto readDetails(int songId) {
		log.info("readDetails(id={})",songId);
		
		SongDetailsDto dto = songRepo.searchDetailsById(songId);
		
		log.info("lyrics={}",dto);
		
		try {
            // 파일을 ClassPath에서 읽어옵니다.
            ClassPathResource resource = new ClassPathResource("static/lyrics/" + dto.getLyrics());
            // Files.readString() 메서드를 사용하여 문자열로 읽어옵니다.
            String lyrics = Files.readString(Paths.get(resource.getURI()), StandardCharsets.UTF_8);
            // 읽어온 문자열을 DTO에 설정합니다.
            if(!lyrics.equals("")) {
            dto.setLyrics(lyrics);
            } else {
            	dto.setLyrics("가사를 찾을 수 없습니다");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		return dto; 
	}
	
	public boolean like(LikeId likeId) {
		
//		Optional<Like> like = likeRepo.findById(LikeId.builder().id(dto.getUserId()).songId(dto.getSongId()).build());
//		log.info("like={}",like);
		
		
		boolean exists = likeRepo.existsById(likeId);
		
		log.info("like={}",exists);
		
		if(exists) {
			likeRepo.deleteById(likeId);
			return false;
		} else {
			Like like = Like.builder().likeId(likeId).build(); 
			likeRepo.save(like);
			return true;
			
		}
	}
	
	public boolean isLiked(LikeId likeId) {
		
//		Optional<Like> result = likeRepo.findById(likeId);
//		
//		if(result.isEmpty()) {
//			return false;
//		} else {
//			return true;
//		}
		
		return likeRepo.existsById(likeId);
		
	}

	public Song selectBySongId(Integer songId) {
		log.info("songId = {}", songId);
		Song song = songRepo.findById(songId).get();
		return song;
	}
	
	// 특정 사용자가 특정 노래를 좋아요 했는지 여부 확인
	public boolean isUserLikedSong(SongLikeDto dto) {
		log.info("isUserLikedSong(dto = {})", dto);
        LikeId likeId = new LikeId(dto.getSongId(), dto.getLoginUserId());
        return songLikeRepo.existsByLikeId(likeId);
    }

	// 특정 곡의 좋아요 개수 확인
    public int countSongLikes(Integer songId) {
    	log.info("countSongLikes(songId = {})", songId);
        return (int) songLikeRepo.countByLikeIdSongId(songId);
    }
    
    // 좋아요 추가
    public void addSongLike(SongLikeDto dto) {	
        log.info("addSongLike(dto = {})", dto);
        // Song 객체를 불러옴
        Song song = songRepo.findById(dto.getSongId())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid song ID"));

        // Like 객체를 생성하며 Song 객체를 설정
        LikeId likeId = new LikeId(dto.getSongId(), dto.getLoginUserId());
        Like like = Like.builder()
                        .likeId(likeId)
                        .song(song) // 여기에서 Song 설정
                        .build();
        
        songLikeRepo.save(like);
    }
    
    // 좋아요 취소
    public void cancelSongLike(SongLikeDto dto) {
        log.info("cancelSongLike(dto = {})", dto);
        LikeId likeId = new LikeId(dto.getSongId(), dto.getLoginUserId());
        songLikeRepo.deleteById(likeId);
    }
    
    
    
    
    
    
    
    // 메인 top10
    @Transactional(readOnly = true)
    public List<SongChartDto> getTop10Songs() {
        // 좋아요 수 기준으로 정렬된 상위 30개의 노래를 조회
        List<Song> topSongs = songRepo.findByOrderByLikesCountDesc().stream()
                                      .limit(10)
                                      .collect(Collectors.toList());

        // 상위 10곡을 SongChartDto로 변환하여 반환
        return topSongs.stream()
                .map(this::convertToSongChartDto)
                .collect(Collectors.toList());
    }
    
    
    
    
 

        public List<SongChartDto> getNewestSongs() {
            // 최신 음악을 발매일 기준으로 내림차순 정렬하여 상위 10개 조회
            List<Song> songs = songRepo.findByOrderByAlbum_AlbumReleaseDateDesc().stream()
            							.limit(10)
            							.collect(Collectors.toList());
            							

            // 조회된 곡 목록을 SongChartDto로 변환하여 반환
            return mapToSongChartDto(songs);
        }

        private List<SongChartDto> mapToSongChartDto(List<Song> songs) {
            return songs.stream()
                    .map(this::convertToSongChartDto)
                    .collect(Collectors.toList());
        }



    
}
