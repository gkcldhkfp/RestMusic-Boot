package com.itwill.rest.repository;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.itwill.rest.domain.QAlbum;
import com.itwill.rest.domain.QArtist;
import com.itwill.rest.domain.QArtistRole;
import com.itwill.rest.domain.QGenreCode;
import com.itwill.rest.domain.QSong;
import com.itwill.rest.domain.QSongGenre;
import com.itwill.rest.domain.Song;
import com.itwill.rest.dto.SongDetailsDto;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;


public class SongQuerydslImpl extends QuerydslRepositorySupport implements SongQuerydsl {

	private final JPAQueryFactory queryFactory;
	private final EntityManager entityManager;
//	private String databaseType;
	
	public SongQuerydslImpl(JPAQueryFactory queryFactory, EntityManager entityManager) {
		super(Song.class);
		this.queryFactory = queryFactory;
		this.entityManager = entityManager;
//		this.entityManager = entityManager;
	}
	
	
	
	@Override
	public SongDetailsDto searchDetailsById(int id) {
	    QSong song = QSong.song;
	    QAlbum album = QAlbum.album;
	    QArtist artist = QArtist.artist;
	    QArtistRole artistRole = QArtistRole.artistRole;
	    QSongGenre songGenre = QSongGenre.songGenre;
	    QGenreCode genreCode = QGenreCode.genreCode;

	    // 모든 정보를 한 번의 쿼리로 가져옵니다
	    List<Tuple> tuples = queryFactory
	            .select(
	                song.songId,
	                song.title,
	                song.lyrics,
	                album.albumId,
	                album.albumName,
	                album.albumImage,
	                artistRole.roleCode.roleId,
	                artist.artistName,
	                artist.id.stringValue(),
	                genreCode.genreName
	            )
	            .from(song)
	            .join(album).on(song.album.albumId.eq(album.albumId))
	            .leftJoin(songGenre).on(song.songId.eq(songGenre.song.songId))
	            .leftJoin(genreCode).on(songGenre.genreCode.genreId.eq(genreCode.genreId))
	            .leftJoin(artistRole).on(song.songId.eq(artistRole.song.songId))
	            .leftJoin(artist).on(artistRole.artist.id.eq(artist.id))
	            .where(song.songId.eq(id))
	            .fetch();

	    if (!tuples.isEmpty()) {
	        // 역할별 아티스트 이름 및 ID 집계 (중복 제거)
	        Map<Integer, List<Map.Entry<String, String>>> roleToArtistsMap = new HashMap<>();
	        Set<String> genres = new HashSet<>();

	        for (Tuple tuple : tuples) {
	            Integer roleId = tuple.get(artistRole.roleCode.roleId);
	            String artistName = tuple.get(artist.artistName);
	            String artistId = tuple.get(artist.id.stringValue());
	            String genreName = tuple.get(genreCode.genreName);

	            // 역할별 아티스트 이름 및 ID 집계
	            if (roleId != null) {
	                Map.Entry<String, String> artistEntry = new AbstractMap.SimpleEntry<>(
	                        artistName != null ? artistName : "Unknown Artist",
	                        artistId != null ? artistId : "Unknown ID"
	                );
	                roleToArtistsMap.computeIfAbsent(roleId, k -> new ArrayList<>()).add(artistEntry);
	            }
	            // 장르 집계
	            if (genreName != null) {
	                genres.add(genreName);
	            }
	        }

	        // 역할별 아티스트 이름 및 ID 정렬 및 조합
	        String singers = roleToArtistsMap.getOrDefault(10, Collections.emptyList())
	                .stream()
	                .map(entry -> entry.getKey())
	                .collect(Collectors.joining(", "));
	        String singerIds = roleToArtistsMap.getOrDefault(10, Collections.emptyList())
	                .stream()
	                .map(entry -> entry.getValue())
	                .collect(Collectors.joining(", "));
	        String writers = roleToArtistsMap.getOrDefault(30, Collections.emptyList())
	                .stream()
	                .map(entry -> entry.getKey())
	                .collect(Collectors.joining(", "));
	        String writerIds = roleToArtistsMap.getOrDefault(30, Collections.emptyList())
	                .stream()
	                .map(entry -> entry.getValue())
	                .collect(Collectors.joining(", "));
	        String composers = roleToArtistsMap.getOrDefault(20, Collections.emptyList())
	                .stream()
	                .map(entry -> entry.getKey())
	                .collect(Collectors.joining(", "));
	        String composerIds = roleToArtistsMap.getOrDefault(20, Collections.emptyList())
	                .stream()
	                .map(entry -> entry.getValue())
	                .collect(Collectors.joining(", "));
	        String arrangers = roleToArtistsMap.getOrDefault(40, Collections.emptyList())
	                .stream()
	                .map(entry -> entry.getKey())
	                .collect(Collectors.joining(", "));
	        String arrangerIds = roleToArtistsMap.getOrDefault(40, Collections.emptyList())
	                .stream()
	                .map(entry -> entry.getValue())
	                .collect(Collectors.joining(", "));
	        String genreList = String.join(", ", genres);

	        // DTO 생성
	        SongDetailsDto dto = SongDetailsDto.builder()
	            .songId(tuples.get(0).get(song.songId))
	            .songTitle(tuples.get(0).get(song.title))
	            .albumId(tuples.get(0).get(album.albumId))
	            .albumName(tuples.get(0).get(album.albumName))
	            .albumImage(tuples.get(0).get(album.albumImage))
	            .singers(singers)
	            .singerIds(singerIds)
	            .writers(writers)
	            .writerIds(writerIds)
	            .composers(composers)
	            .composerIds(composerIds)
	            .arrangers(arrangers)
	            .arrangerIds(arrangerIds)
	            .lyrics(tuples.get(0).get(song.lyrics))
	            .genres(genreList)
	            .build();

	        return dto;
	    }

	    return null;
	}
	
	

	
	    
}
