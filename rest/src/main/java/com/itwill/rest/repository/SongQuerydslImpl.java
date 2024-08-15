package com.itwill.rest.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

public class SongQuerydslImpl extends QuerydslRepositorySupport implements SongQuerydsl {

	private final JPAQueryFactory queryFactory;
	
	public SongQuerydslImpl(JPAQueryFactory queryFactory) {
		super(Song.class);
		this.queryFactory = queryFactory;
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
	        // 중복 제거 및 집계
	        Map<Integer, Set<String>> roleToNamesMap = new HashMap<>();
	        Map<Integer, Set<String>> roleToIdsMap = new HashMap<>();
	        Set<String> genres = new HashSet<>();

	        for (Tuple tuple : tuples) {
	            int roleId = tuple.get(artistRole.roleCode.roleId);
	            String artistName = tuple.get(artist.artistName);
	            String artistId = tuple.get(artist.id.stringValue());
	            String genreName = tuple.get(genreCode.genreName);

	            // 역할별 아티스트 이름 및 ID 집계 (중복 제거)
	            roleToNamesMap.computeIfAbsent(roleId, k -> new HashSet<>()).add(artistName);
	            roleToIdsMap.computeIfAbsent(roleId, k -> new HashSet<>()).add(artistId);

	            // 장르 집계
	            if (genreName != null) {
	                genres.add(genreName);
	            }
	        }

	        // 역할별 아티스트 이름 및 ID 집계
	        String singers = String.join(", ", roleToNamesMap.getOrDefault(10, Collections.emptySet()));
	        String singerIds = String.join(", ", roleToIdsMap.getOrDefault(10, Collections.emptySet()));
	        String writers = String.join(", ", roleToNamesMap.getOrDefault(30, Collections.emptySet()));
	        String writerIds = String.join(", ", roleToIdsMap.getOrDefault(30, Collections.emptySet()));
	        String composers = String.join(", ", roleToNamesMap.getOrDefault(20, Collections.emptySet()));
	        String composerIds = String.join(", ", roleToIdsMap.getOrDefault(20, Collections.emptySet()));
	        String arrangers = String.join(", ", roleToNamesMap.getOrDefault(40, Collections.emptySet()));
	        String arrangerIds = String.join(", ", roleToIdsMap.getOrDefault(40, Collections.emptySet()));
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
