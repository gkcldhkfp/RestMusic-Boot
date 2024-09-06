package com.itwill.rest.repository;

import java.time.LocalDate;
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
import com.itwill.rest.dto.AlbumSearchResultDto;
import com.itwill.rest.dto.ArtistSearchResultDto;
import com.itwill.rest.dto.SearchResultDto;
import com.itwill.rest.dto.SongDetailsDto;
import com.itwill.rest.dto.SongSearchResultDto;
import com.itwill.rest.service.DatabaseService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;


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
	        // 중복 제거 및 집계
	        Map<Integer, Set<String>> roleToNamesMap = new HashMap<>();
	        Map<Integer, Set<String>> roleToIdsMap = new HashMap<>();
	        Set<String> genres = new HashSet<>();

	        for (Tuple tuple : tuples) {
	            Integer roleId = tuple.get(artistRole.roleCode.roleId);
	            String artistName = tuple.get(artist.artistName);
	            String artistId = tuple.get(artist.id.stringValue());
	            String genreName = tuple.get(genreCode.genreName);

	            // 역할별 아티스트 이름 및 ID 집계 (중복 제거)
	            if (roleId != null) {
	                roleToNamesMap.computeIfAbsent(roleId, k -> new HashSet<>()).add(artistName != null ? artistName : "Unknown Artist");
	                roleToIdsMap.computeIfAbsent(roleId, k -> new HashSet<>()).add(artistId != null ? artistId : "Unknown ID");
	            }
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
	
	public Page<AlbumSearchResultDto> searchAlbums(String keyword, String sortType, Pageable pageable) {
	    if (pageable.getPageNumber() < 0) {
	        throw new IllegalArgumentException("Page index must not be negative");
	    }

	    // Base SQL query to fetch albums
	    String sql = "SELECT album.album_id AS albumId, album.album_name AS albumName, album.album_image AS albumImage, " +
	                 "album.album_type AS albumType, album.album_release_date AS albumReleaseDate " +
	                 "FROM albums album " +
	                 "WHERE MATCH(album.album_name) AGAINST (?1 IN BOOLEAN MODE) ";

	    // Add sorting based on sortType
	    switch (sortType) {
	        case "accuracy":
	            sql += "ORDER BY MATCH(album.album_name) AGAINST (?1 IN BOOLEAN MODE) DESC, " +
	                   "LENGTH(album.album_name) ASC ";
	            break;
	        case "alphabet":
	            sql += "ORDER BY album.album_name ASC ";
	            break;
	        case "recency":
	            sql += "ORDER BY album.album_release_date DESC ";
	            break;
	        default:
	            sql += "ORDER BY MATCH(album.album_name) AGAINST (?1 IN BOOLEAN MODE) DESC ";
	            break;
	    }

	    sql += "LIMIT ?2 OFFSET ?3";

	    // Create and configure query for fetching albums
	    Query query = entityManager.createNativeQuery(sql);
	    query.setParameter(1, keyword);
	    query.setParameter(2, pageable.getPageSize());
	    query.setParameter(3, pageable.getOffset());

	    List<Object[]> results = query.getResultList();

	    // Fetch album IDs from results
	    List<Integer> albumIds = results.stream()
	        .map(result -> ((Number) result[0]).intValue())
	        .collect(Collectors.toList());

	    // Create a map to hold albumId to singerNames and singerIds
	    final Map<Integer, String[]> albumSingerMap = new HashMap<>();

	    if (!albumIds.isEmpty()) {
	        String songAndArtistSql = "SELECT song.album_id AS albumId, " +
	                                  "GROUP_CONCAT(DISTINCT artist.artist_name ORDER BY artist.artist_name ASC SEPARATOR ', ') AS singerNames, " +
	                                  "GROUP_CONCAT(DISTINCT artist.artist_id ORDER BY artist.artist_name ASC SEPARATOR ', ') AS singerIds " +
	                                  "FROM songs song " +
	                                  "LEFT JOIN artist_roles artist_roles ON song.song_id = artist_roles.song_id " +
	                                  "LEFT JOIN artists artist ON artist_roles.artist_id = artist.artist_id " +
	                                  "WHERE song.album_id IN (?1) AND artist_roles.role_id = 10 " +
	                                  "GROUP BY song.album_id";

	        Query songAndArtistQuery = entityManager.createNativeQuery(songAndArtistSql);
	        songAndArtistQuery.setParameter(1, albumIds);

	        List<Object[]> songAndArtistResults = songAndArtistQuery.getResultList();

	        // Populate the map with albumId, singerNames, and singerIds
	        for (Object[] result : songAndArtistResults) {
	            Integer albumId = ((Number) result[0]).intValue();
	            String singerNames = (String) result[1];
	            String singerIds = (String) result[2];
	            albumSingerMap.put(albumId, new String[]{singerNames, singerIds});
	        }
	    }

	    // Map query results to AlbumSearchResultDto
	    List<AlbumSearchResultDto> albumResults = results.stream()
	        .map(result -> {
	            int albumId = ((Number) result[0]).intValue();
	            String albumName = (String) result[1];
	            String albumImage = (String) result[2];
	            String albumType = (String) result[3];
	            LocalDate albumReleaseDate = ((java.sql.Date) result[4]) != null
	                ? ((java.sql.Date) result[4]).toLocalDate() // Convert java.sql.Date to LocalDate
	                : null;

	            // Fetch singerNames and singerIds if available
	            String[] singerInfo = albumSingerMap.getOrDefault(albumId, new String[]{"", ""});

	            return new AlbumSearchResultDto(
	                albumId, // albumId
	                albumName, // albumName
	                albumImage, // albumImage
	                albumType, // albumType
	                albumReleaseDate, // albumReleaseDate
	                singerInfo[0], // singerNames
	                singerInfo[1]  // singerIds
	            );
	        })
	        .collect(Collectors.toList());

	    // Get total count for pagination
	    String countSql = "SELECT COUNT(DISTINCT album.album_id) " +
	                      "FROM albums album " +
	                      "WHERE MATCH(album.album_name) AGAINST (?1 IN BOOLEAN MODE)";

	    Query countQuery = entityManager.createNativeQuery(countSql);
	    countQuery.setParameter(1, keyword);
	    long total = ((Number) countQuery.getSingleResult()).longValue();

	    return new PageImpl<>(albumResults, pageable, total);
	}







	public Page<ArtistSearchResultDto> searchArtists(String keyword, String sortType, Pageable pageable) {
	    if (pageable.getPageNumber() < 0) {
	        throw new IllegalArgumentException("Page index must not be negative");
	    }

	    // Base SQL query
	    String sql = "SELECT artist.artist_id AS artistId, artist.artist_name AS artistName, artist.artist_image AS artistImage " +
	                 "FROM artists artist " +
	                 "WHERE MATCH(artist.artist_name) AGAINST (?1 IN BOOLEAN MODE) " +
	                 "ORDER BY ";

	    // Add sorting based on sortType
	    switch (sortType) {
	        case "accuracy":
	            sql += "MATCH(artist.artist_name) AGAINST (?1 IN BOOLEAN MODE) DESC ";
	            break;
	        case "alphabet":
	            sql += "artist.artist_name ASC ";
	            break;
	        case "recency":
	            sql += "artist.artist_debut_date DESC "; // Assuming there's a debut date for recency sorting
	            break;
	        default:
	            sql += "MATCH(artist.artist_name) AGAINST (?1 IN BOOLEAN MODE) DESC ";
	            break;
	    }

	    sql += "LIMIT ?2 OFFSET ?3";

	    // Create and configure query
	    Query query = entityManager.createNativeQuery(sql);
	    query.setParameter(1, keyword);
	    query.setParameter(2, pageable.getPageSize());
	    query.setParameter(3, pageable.getOffset());

	    List<Object[]> results = query.getResultList();

	    List<ArtistSearchResultDto> artistResults = results.stream()
	        .map(result -> new ArtistSearchResultDto(
	            ((Number) result[0]).intValue(),       // artistId
	            (String) result[1],                     // artistName
	            (String) result[2]                      // artistImage
	        ))
	        .collect(Collectors.toList());

	    // Get total count for pagination
	    String countSql = "SELECT COUNT(DISTINCT artist.artist_id) " +
	                      "FROM artists artist " +
	                      "WHERE MATCH(artist.artist_name) AGAINST (?1 IN BOOLEAN MODE)";
	    
	    Query countQuery = entityManager.createNativeQuery(countSql);
	    countQuery.setParameter(1, keyword);
	    long total = ((Number) countQuery.getSingleResult()).longValue();

	    return new PageImpl<>(artistResults, pageable, total);
	}



	public SearchResultDto searchAll(String keyword, String sortType, Pageable pageable) {
	    SearchResultDto results = new SearchResultDto();
	    results.setAlbum(searchAlbums(keyword, sortType, pageable).getContent());
	    results.setArtist(searchArtists(keyword, sortType, pageable).getContent());
	    return results;
	}



	@Override
	public List<Object[]> findSongsByKeyword(String keyword, int limit, int offset, String sort) {
		// TODO Auto-generated method stub
		return null;
	}


	    
}
