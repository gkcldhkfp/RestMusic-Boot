package com.itwill.rest.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.itwill.rest.domain.Artist;
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
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class SongQuerydslImpl extends QuerydslRepositorySupport implements SongQuerydsl {

	private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

	
	public SongQuerydslImpl(JPAQueryFactory queryFactory, EntityManager entityManager) {
		super(Song.class);
		this.queryFactory = queryFactory;
		this.entityManager = entityManager;
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
	
	 // 전체 카테고리 검색: 각 카테고리의 상위 5개를 정확도 순으로 리턴
	public SearchResultDto searchAllCategories(String keyword, String sortType) {
        List<SongSearchResultDto> songResults = searchCategory("t", keyword, sortType);
        List<AlbumSearchResultDto> albumResults = searchCategory("a", keyword, sortType);
        List<ArtistSearchResultDto> artistResults = searchCategory("s", keyword, sortType);

        // 상위 5개만 선택
        return new SearchResultDto(
                songResults.stream().limit(5).collect(Collectors.toList()),
                albumResults.stream().limit(5).collect(Collectors.toList()),
                artistResults.stream().limit(5).collect(Collectors.toList())
        );
    }

    // 카테고리별 페이징 검색
    public Page<?> searchCategoryWithPagination(String category, String keyword, String sortType, Pageable pageable) {
        BooleanExpression keywordCondition = createKeywordCondition(keyword, category, QSong.song, QAlbum.album, QArtist.artist);

        JPAQuery<?> query = new JPAQuery<>(queryFactory.getEntityManager()); // 수정된 부분

        query
                .from(getEntityPathByCategory(category))
                .where(keywordCondition)
                .orderBy(getOrderSpecifier(sortType))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        addJoins(query, category);

        List<?> results = query.fetch();
        long total = getCount(category, keywordCondition);

        List<?> dtoResults = mapToDto(results, category);
        return new PageImpl<>(dtoResults, pageable, total);
    }

    // 카테고리 검색, 상위 5개만 리턴
    private List<?> searchCategory(String category, String keyword, String sortType) {
        BooleanExpression keywordCondition = createKeywordCondition(keyword, category, QSong.song, QAlbum.album, QArtist.artist);

        JPAQuery<?> query = new JPAQuery<>(queryFactory.getEntityManager()); // 수정된 부분

        query
                .from(getEntityPathByCategory(category))
                .where(keywordCondition)
                .orderBy(getOrderSpecifier(sortType))
                .limit(5);  // Limit the number of results to 5

        addJoins(query, category);

        List<?> results = query.fetch();
        return mapToDto(results, category);
    }

    private BooleanExpression createKeywordCondition(String keyword, String category, QSong song, QAlbum album, QArtist artist) {
        StringPath titlePath = song.title;
        StringPath albumNamePath = album.albumName;
        StringPath artistNamePath = artist.artistName;

        BooleanExpression condition = Expressions.asBoolean(true).isTrue();

        switch (category) {
            case "t":
                condition = titlePath.containsIgnoreCase(keyword);
                break;
            case "a":
                condition = albumNamePath.containsIgnoreCase(keyword);
                break;
            case "s":
                condition = artistNamePath.containsIgnoreCase(keyword);
                break;
            default:
                throw new IllegalArgumentException("Invalid category");
        }

        return condition;
    }

    private com.querydsl.core.types.OrderSpecifier<?> getOrderSpecifier(String sortType) {
        QSong song = QSong.song;
        QAlbum album = QAlbum.album;

        switch (sortType.toLowerCase()) {
            case "accuracy":
                NumberTemplate<Double> scoreTemplate = Expressions.numberTemplate(Double.class, "SCORE(1)");
                return scoreTemplate.desc();
            case "recency":
                return album.albumReleaseDate.desc();
            case "alphabet":
                return song.title.asc();
            default:
                throw new IllegalArgumentException("Invalid sort type");
        }
    }

    private com.querydsl.core.types.Path<?> getEntityPathByCategory(String category) {
        switch (category) {
            case "t":
                return QSong.song;
            case "a":
                return QAlbum.album;
            case "s":
                return QArtist.artist;
            default:
                throw new IllegalArgumentException("Invalid category");
        }
    }

    private void addJoins(JPAQuery<?> query, String category) {
        switch (category) {
            case "t":
                query
                    .join(QSong.song.album, QAlbum.album)
                    .leftJoin(QArtistRole.artistRole).on(QArtistRole.artistRole.song.eq(QSong.song))
                    .leftJoin(QArtistRole.artistRole.artist, QArtist.artist);
                break;
            case "a":
                query
                    .join(QAlbum.album.songs, QSong.song)
                    .leftJoin(QSong.song.artistRole, QArtistRole.artistRole)
                    .leftJoin(QArtistRole.artistRole.artist, QArtist.artist);
                break;
            case "s":
                query
                    .join(QArtist.artist.artistRoles, QArtistRole.artistRole)
                    .join(QArtistRole.artistRole.song, QSong.song);
                break;
            default:
                throw new IllegalArgumentException("Invalid category");
        }
    }

    private long getCount(String category, BooleanExpression keywordCondition) {
        JPAQuery<?> countQuery = new JPAQuery<>(queryFactory.getEntityManager()); // 수정된 부분
        return countQuery
                .from(getEntityPathByCategory(category))
                .where(keywordCondition)
                .fetchCount();
    }

    private List<?> mapToDto(List<?> results, String category) {
        switch (category) {
            case "t":
                return results.stream()
                        .filter(result -> result instanceof Song)
                        .map(result -> {
                            Song songResult = (Song) result;
                            SongSearchResultDto dto = new SongSearchResultDto();

                            dto.setSongId(songResult.getSongId());
                            dto.setSongTitle(songResult.getTitle());
                            dto.setAlbumImage(songResult.getAlbum().getAlbumImage());
                            dto.setAlbumTitle(songResult.getAlbum().getAlbumName());

                            // Get artist names with roleId 10 for this song
                            List<String> singerNames = queryFactory
                                    .select(QArtist.artist.artistName)
                                    .from(QArtistRole.artistRole)
                                    .join(QArtistRole.artistRole.artist, QArtist.artist)
                                    .where(QArtistRole.artistRole.song.eq(songResult)
                                            .and(QArtistRole.artistRole.roleCode.roleId.eq(10)))
                                    .fetch();

                            dto.setSingerNames(String.join(", ", singerNames));
                            return dto;
                        })
                        .collect(Collectors.toList());

            case "a":
                return results.stream()
                        .filter(result -> result instanceof Album)
                        .map(result -> {
                            Album albumResult = (Album) result;
                            AlbumSearchResultDto dto = new AlbumSearchResultDto();

                            dto.setAlbumId(albumResult.getAlbumId());
                            dto.setAlbumName(albumResult.getAlbumName());
                            dto.setAlbumImage(albumResult.getAlbumImage());

                            return dto;
                        })
                        .collect(Collectors.toList());

            case "s":
                return results.stream()
                        .filter(result -> result instanceof Artist)
                        .map(result -> {
                            Artist artistResult = (Artist) result;
                            ArtistSearchResultDto dto = new ArtistSearchResultDto();

                            dto.setArtistId(artistResult.getArtistId());
                            dto.setArtistName(artistResult.getArtistName());
                            dto.setArtistImage(artistResult.getArtistImage());

                            return dto;
                        })
                        .collect(Collectors.toList());

            default:
                throw new IllegalArgumentException("Invalid category");
        }
    }

	    
}
