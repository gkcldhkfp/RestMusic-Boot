package com.itwill.rest.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.itwill.rest.domain.QAlbum;
import com.itwill.rest.domain.QArtist;
import com.itwill.rest.domain.QArtistRole;
import com.itwill.rest.domain.QGenreCode;
import com.itwill.rest.domain.QGroup;
import com.itwill.rest.domain.QGroupMember;
import com.itwill.rest.domain.QLike;
import com.itwill.rest.domain.QRoleCode;
import com.itwill.rest.domain.QSong;
import com.itwill.rest.domain.QSongGenre;
import com.itwill.rest.domain.Song;
import com.itwill.rest.dto.SongChartDto;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SongQuerydslImpl extends QuerydslRepositorySupport implements SongQuerydsl {

	private final JPAQueryFactory queryFactory;

    public SongQuerydslImpl(JPAQueryFactory queryFactory) {
        super(Song.class);
        this.queryFactory = queryFactory;
    }

    // top 30
    @Override
    public List<SongChartDto> getTopSongs() {
        QSong song = QSong.song;
        QAlbum album = QAlbum.album;
        QArtist artist = QArtist.artist;
        QArtistRole artistRole = QArtistRole.artistRole;
        QLike like = QLike.like;
        QGroupMember groupMember = QGroupMember.groupMember;
        QGroup group = QGroup.group;
        QRoleCode roleCode = QRoleCode.roleCode;

        // 데이터 가져오기
        List<Tuple> result = queryFactory
            .select(
                song.songId,
                album.albumId,
                album.albumImage,
                song.title,
                album.albumName,
                like.likeId.songId.count().intValue(), // 좋아요 수를 Integer로 변환
                song.songPath,
                song.videoLink,
                artist.id.as("artistId"),
                artist.artistName.as("artistName"),
                group.id.as("groupId"),
                group.groupName.as("groupName")
            )
            .from(song)
            .leftJoin(album).on(song.album.albumId.eq(album.albumId))
            .leftJoin(artistRole).on(song.songId.eq(artistRole.song.songId))
            .leftJoin(artist).on(artistRole.artist.id.eq(artist.id))
            .leftJoin(groupMember).on(artist.id.eq(groupMember.artist.id))
            .leftJoin(group).on(groupMember.group.id.eq(group.id))
            .leftJoin(like).on(song.songId.eq(like.likeId.songId))
            .leftJoin(roleCode).on(artistRole.roleCode.roleId.eq(roleCode.roleId)) // RoleCode와 연결
            .where(roleCode.roleId.eq(10)) // role_id가 10인 경우로 필터링
            .groupBy(
                song.songId, album.albumId, album.albumImage, song.title, album.albumName,
                song.songPath, song.videoLink, artist.id, artist.artistName, group.id, group.groupName
            )
            .orderBy(like.likeId.songId.count().desc()) // 좋아요 수에 따라 내림차순 정렬
            .fetch();

        // DTO로 변환 및 좋아요 수에 따라 정렬
        Map<Integer, SongChartDto> songMap = new HashMap<>();

        for (Tuple tuple : result) {
            Integer songId = tuple.get(song.songId);
            SongChartDto dto = songMap.computeIfAbsent(songId, k -> {
                SongChartDto newDto = new SongChartDto();
                newDto.setSongId(songId);
                newDto.setAlbumId(tuple.get(album.albumId));
                newDto.setAlbumImage(tuple.get(album.albumImage));
                newDto.setTitle(tuple.get(song.title));
                newDto.setAlbumName(tuple.get(album.albumName));
                newDto.setLikes(tuple.get(5, Integer.class)); // Integer 타입으로 좋아요 수 설정
                newDto.setSongPath(tuple.get(song.songPath));
                newDto.setVideoLink(tuple.get(song.videoLink));
                newDto.setArtistIds(new ArrayList<>());
                newDto.setArtistNames(new ArrayList<>());
                newDto.setGroupIds(new ArrayList<>());
                newDto.setGroupNames(new ArrayList<>());
                return newDto;
            });

            // Add artist information
            Integer artistId = tuple.get(artist.id.as("artistId"));
            String artistName = tuple.get(artist.artistName.as("artistName"));
            if (artistId != null && !dto.getArtistIds().contains(artistId)) {
                dto.getArtistIds().add(artistId);
                dto.getArtistNames().add(artistName);
            }

            // Add group information
            Integer groupId = tuple.get(group.id.as("groupId"));
            String groupName = tuple.get(group.groupName.as("groupName"));
            if (groupId != null && !dto.getGroupIds().contains(groupId)) {
                dto.getGroupIds().add(groupId);
                dto.getGroupNames().add(groupName);
            }
        }

        // 좋아요 수에 따라 내림차순 정렬 후 상위 30개 반환
        List<SongChartDto> sortedSongs = new ArrayList<>(songMap.values());
        sortedSongs.sort(Comparator.comparing(SongChartDto::getLikes).reversed());

        return sortedSongs.stream().limit(30).collect(Collectors.toList());
    }
    
    // 장르별 차트(전체)
    @Override
    public List<SongChartDto> getAllSongs() {
        QSong song = QSong.song;
        QAlbum album = QAlbum.album;
        QArtist artist = QArtist.artist;
        QArtistRole artistRole = QArtistRole.artistRole;
        QLike like = QLike.like;
        QGroupMember groupMember = QGroupMember.groupMember;
        QGroup group = QGroup.group;
        QRoleCode roleCode = QRoleCode.roleCode;

        // Step 1: Fetch unique artist and group information
        List<Tuple> uniqueArtists = queryFactory
            .select(
                song.songId,
                artist.id.as("artistId"),
                artist.artistName.as("artistName"),
                group.id.as("groupId"),
                group.groupName.as("groupName")
            )
            .from(song)
            .leftJoin(artistRole).on(song.songId.eq(artistRole.song.songId))
            .leftJoin(artist).on(artistRole.artist.id.eq(artist.id))
            .leftJoin(groupMember).on(artist.id.eq(groupMember.artist.id))
            .leftJoin(group).on(groupMember.group.id.eq(group.id))
            .leftJoin(roleCode).on(artistRole.roleCode.roleId.eq(roleCode.roleId))
            .where(roleCode.roleId.eq(10)) // role_id가 10인 경우로 필터링
            .fetch();

        // Step 2: Fetch ranked songs with likes count
        List<Tuple> rankedSongs = queryFactory
            .select(
                song.songId,
                album.albumId,
                album.albumImage,
                song.title,
                album.albumName,
                like.likeId.songId.count().intValue(), // 좋아요 수를 Integer로 변환
                song.songPath,
                song.videoLink
            )
            .from(song)
            .leftJoin(album).on(song.album.albumId.eq(album.albumId))
            .leftJoin(like).on(song.songId.eq(like.likeId.songId))
            .groupBy(
                song.songId, album.albumId, album.albumImage, song.title, album.albumName,
                song.songPath, song.videoLink
            )
            .orderBy(like.likeId.songId.count().desc()) // 좋아요 수에 따라 내림차순 정렬
            .fetch();

        // Step 3: Convert results to DTO
        Map<Integer, SongChartDto> songMap = new HashMap<>();

        for (Tuple tuple : rankedSongs) {
            Integer songId = tuple.get(song.songId);
            SongChartDto dto = songMap.computeIfAbsent(songId, k -> {
                SongChartDto newDto = new SongChartDto();
                newDto.setSongId(songId);
                newDto.setAlbumId(tuple.get(album.albumId));
                newDto.setAlbumImage(tuple.get(album.albumImage));
                newDto.setTitle(tuple.get(song.title));
                newDto.setAlbumName(tuple.get(album.albumName));
                newDto.setLikes(tuple.get(5, Integer.class)); // Integer 타입으로 좋아요 수 설정
                newDto.setSongPath(tuple.get(song.songPath));
                newDto.setVideoLink(tuple.get(song.videoLink));
                newDto.setArtistIds(new ArrayList<>());
                newDto.setArtistNames(new ArrayList<>());
                newDto.setGroupIds(new ArrayList<>());
                newDto.setGroupNames(new ArrayList<>());
                return newDto;
            });

            // Add artist information
            Integer artistId = tuple.get(artist.id.as("artistId"));
            String artistName = tuple.get(artist.artistName.as("artistName"));
            if (artistId != null && !dto.getArtistIds().contains(artistId)) {
                dto.getArtistIds().add(artistId);
                dto.getArtistNames().add(artistName);
            }

            // Add group information
            Integer groupId = tuple.get(group.id.as("groupId"));
            String groupName = tuple.get(group.groupName.as("groupName"));
            if (groupId != null && !dto.getGroupIds().contains(groupId)) {
                dto.getGroupIds().add(groupId);
                dto.getGroupNames().add(groupName);
            }
        }

        // 좋아요 수에 따라 내림차순 정렬 후 반환
        List<SongChartDto> sortedSongs = new ArrayList<>(songMap.values());
        sortedSongs.sort(Comparator.comparing(SongChartDto::getLikes).reversed());

        return sortedSongs;
    }

    // 장르별 차트
    @Override
    public List<SongChartDto> getSongsByGenre(String genreName) {
        QSong song = QSong.song;
        QAlbum album = QAlbum.album;
        QArtist artist = QArtist.artist;
        QArtistRole artistRole = QArtistRole.artistRole;
        QLike like = QLike.like;
        QGenreCode genreCode = QGenreCode.genreCode;
        QSongGenre songGenre = QSongGenre.songGenre;
        QRoleCode roleCode = QRoleCode.roleCode;

        // Genre ID 쿼리
        Integer genreId = queryFactory
            .select(genreCode.genreId)
            .from(genreCode)
            .where(genreCode.genreName.eq(genreName))
            .fetchOne();

        if (genreId == null) {
            return Collections.emptyList(); // 장르가 존재하지 않으면 빈 리스트 반환
        }

        // 데이터 가져오기
        List<Tuple> result = queryFactory
            .select(
                song.songId,
                album.albumId,
                album.albumImage,
                song.title,
                album.albumName,
                like.likeId.songId.count().intValue(), // 좋아요 수를 Integer로 변환
                song.songPath,
                song.videoLink,
                artist.id,
                artist.artistName
            )
            .from(song)
            .join(album).on(song.album.albumId.eq(album.albumId))
            .join(songGenre).on(song.songId.eq(songGenre.id.songId))
            .join(genreCode).on(songGenre.id.genreId.eq(genreCode.genreId))
            .leftJoin(artistRole).on(song.songId.eq(artistRole.song.songId))
            .leftJoin(artist).on(artistRole.artist.id.eq(artist.id))
            .leftJoin(like).on(song.songId.eq(like.likeId.songId))
            .leftJoin(roleCode).on(artistRole.roleCode.roleId.eq(roleCode.roleId))
            .where(songGenre.id.genreId.eq(genreId)
                .and(roleCode.roleId.eq(10))) // role_id가 10인 경우로 필터링
            .groupBy(
                song.songId,
                album.albumId,
                album.albumImage,
                song.title,
                album.albumName,
                song.songPath,
                song.videoLink,
                artist.id,
                artist.artistName
            )
            .orderBy(like.likeId.songId.count().desc()) // 좋아요 수에 따라 내림차순 정렬
            .fetch();

        // DTO로 변환 및 좋아요 수에 따라 정렬
        Map<Integer, SongChartDto> songMap = new HashMap<>();

        for (Tuple tuple : result) {
            Integer songId = tuple.get(song.songId);
            SongChartDto dto = songMap.computeIfAbsent(songId, k -> {
                SongChartDto newDto = new SongChartDto();
                newDto.setSongId(songId);
                newDto.setAlbumId(tuple.get(album.albumId));
                newDto.setAlbumImage(tuple.get(album.albumImage));
                newDto.setTitle(tuple.get(song.title));
                newDto.setAlbumName(tuple.get(album.albumName));
                newDto.setLikes(tuple.get(5, Integer.class)); // Integer 타입으로 좋아요 수 설정
                newDto.setSongPath(tuple.get(song.songPath));
                newDto.setVideoLink(tuple.get(song.videoLink));
                newDto.setArtistIds(new ArrayList<>());
                newDto.setArtistNames(new ArrayList<>());
                return newDto;
            });

            Integer artistId = tuple.get(artist.id);
            String artistName = tuple.get(artist.artistName);
            if (artistId != null && !dto.getArtistIds().contains(artistId)) {
                dto.getArtistIds().add(artistId);
                dto.getArtistNames().add(artistName);
            }
        }

        // 좋아요 수에 따라 내림차순 정렬 후 반환
        List<SongChartDto> sortedSongs = new ArrayList<>(songMap.values());
        sortedSongs.sort(Comparator.comparing(SongChartDto::getLikes).reversed());

        return sortedSongs;
    }
    
    // 최신 음악
    @Override
    public List<SongChartDto> getNewestSongs() {
        QSong song = QSong.song;
        QAlbum album = QAlbum.album;
        QArtist artist = QArtist.artist;
        QArtistRole artistRole = QArtistRole.artistRole;
        QLike like = QLike.like;
        QRoleCode roleCode = QRoleCode.roleCode;

        // 데이터 가져오기
        List<Tuple> result = queryFactory
            .select(
                song.songId,
                album.albumId,
                album.albumImage,
                album.albumReleaseDate,
                song.title,
                song.songPath,
                song.videoLink,
                artist.id,
                artist.artistName,
                like.likeId.songId.count().intValue() // 좋아요 수를 Integer로 변환
            )
            .from(song)
            .leftJoin(album).on(song.album.albumId.eq(album.albumId))
            .leftJoin(artistRole).on(song.songId.eq(artistRole.song.songId))
            .leftJoin(artist).on(artistRole.artist.id.eq(artist.id))
            .leftJoin(like).on(song.songId.eq(like.likeId.songId))
            .leftJoin(roleCode).on(artistRole.roleCode.roleId.eq(roleCode.roleId))
            .where(roleCode.roleId.eq(10)) // role_id가 10인 경우로 필터링
            .groupBy(
                song.songId,
                album.albumId,
                album.albumImage,
                album.albumReleaseDate,
                song.title,
                song.songPath,
                song.videoLink,
                artist.id,
                artist.artistName
            )
            .orderBy(album.albumReleaseDate.desc()) // 앨범 발매일 기준으로 내림차순 정렬
            .fetch();

        // DTO로 변환
        Map<Integer, SongChartDto> songMap = new HashMap<>();

        for (Tuple tuple : result) {
            Integer songId = tuple.get(song.songId);
            SongChartDto dto = songMap.computeIfAbsent(songId, k -> {
                SongChartDto newDto = new SongChartDto();
                newDto.setSongId(songId);
                newDto.setAlbumId(tuple.get(album.albumId));
                newDto.setAlbumImage(tuple.get(album.albumImage));
                newDto.setTitle(tuple.get(song.title));
                newDto.setAlbumName(tuple.get(album.albumName));
                newDto.setLikes(tuple.get(9, Integer.class)); // Integer 타입으로 좋아요 수 설정
                newDto.setSongPath(tuple.get(song.songPath));
                newDto.setVideoLink(tuple.get(song.videoLink));
                newDto.setArtistIds(new ArrayList<>());
                newDto.setArtistNames(new ArrayList<>());
                newDto.setAlbumReleaseDate(tuple.get(album.albumReleaseDate)); // 앨범 발매일 설정
                return newDto;
            });

            Integer artistId = tuple.get(artist.id);
            String artistName = tuple.get(artist.artistName);
            if (artistId != null && !dto.getArtistIds().contains(artistId)) {
                dto.getArtistIds().add(artistId);
                dto.getArtistNames().add(artistName);
            }
        }

        // DTO 리스트로 변환 및 albumReleaseDate 기준으로 내림차순 정렬
        List<SongChartDto> sortedSongs = new ArrayList<>(songMap.values());
        sortedSongs.sort(Comparator.comparing(SongChartDto::getAlbumReleaseDate).reversed());

        return sortedSongs;
    }
    
     // 최신 음악(페이징)
//    @Override
//    public Page<SongChartDto> getNewestSongs(int page, int size) {
//        QSong song = QSong.song;
//        QAlbum album = QAlbum.album;
//        QArtist artist = QArtist.artist;
//        QArtistRole artistRole = QArtistRole.artistRole;
//        QLike like = QLike.like;
//        QRoleCode roleCode = QRoleCode.roleCode;
//
//        // 데이터 가져오기
//        List<Tuple> result = queryFactory
//            .select(
//                song.songId,
//                album.albumId,
//                album.albumImage,
//                album.albumReleaseDate,
//                song.title,
//                song.songPath,
//                song.videoLink,
//                artist.id,
//                artist.artistName,
//                like.likeId.songId.count().intValue() // 좋아요 수를 Integer로 변환
//            )
//            .from(song)
//            .leftJoin(album).on(song.album.albumId.eq(album.albumId))
//            .leftJoin(artistRole).on(song.songId.eq(artistRole.song.songId))
//            .leftJoin(artist).on(artistRole.artist.id.eq(artist.id))
//            .leftJoin(like).on(song.songId.eq(like.likeId.songId))
//            .leftJoin(roleCode).on(artistRole.roleCode.roleId.eq(roleCode.roleId))
//            .where(roleCode.roleName.eq("가수"))
//            .groupBy(
//                song.songId,
//                album.albumId,
//                album.albumImage,
//                album.albumReleaseDate,
//                song.title,
//                song.songPath,
//                song.videoLink,
//                artist.id,
//                artist.artistName
//            )
//            .orderBy(album.albumReleaseDate.desc())
//            .offset(page * size)
//            .limit(size)
//            .fetch();
//
//        // DTO로 변환 및 정렬
//        Map<Integer, SongChartDto> songMap = new HashMap<>();
//        for (Tuple tuple : result) {
//            Integer songId = tuple.get(song.songId);
//            SongChartDto dto = songMap.computeIfAbsent(songId, k -> {
//                SongChartDto newDto = new SongChartDto();
//                newDto.setSongId(songId);
//                newDto.setAlbumId(tuple.get(album.albumId));
//                newDto.setAlbumImage(tuple.get(album.albumImage));
//                newDto.setTitle(tuple.get(song.title));
//                newDto.setSongPath(tuple.get(song.songPath));
//                newDto.setVideoLink(tuple.get(song.videoLink));
//                newDto.setLikes(tuple.get(9, Integer.class)); // Integer 타입으로 좋아요 수 설정
//                newDto.setArtistIds(new ArrayList<>());
//                newDto.setArtistNames(new ArrayList<>());
//                newDto.setAlbumReleaseDate(tuple.get(album.albumReleaseDate)); // 앨범 발매일 설정
//                return newDto;
//            });
//
//            Integer artistId = tuple.get(artist.id);
//            String artistName = tuple.get(artist.artistName);
//            if (artistId != null && !dto.getArtistIds().contains(artistId)) {
//                dto.getArtistIds().add(artistId);
//                dto.getArtistNames().add(artistName);
//            }
//        }
//
//        // DTO 리스트로 변환
//        return new PageImpl<>(new ArrayList<>(songMap.values()), PageRequest.of(page, size), result.size());
//    }
    
}
