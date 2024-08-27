package com.itwill.rest.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.itwill.rest.domain.QAlbum;
import com.itwill.rest.domain.QArtist;
import com.itwill.rest.domain.QArtistRole;
import com.itwill.rest.domain.QGroup;
import com.itwill.rest.domain.QGroupMember;
import com.itwill.rest.domain.QSong;
import com.itwill.rest.domain.QTitleSong;
import com.itwill.rest.dto.ArtistAlbumDto;
import com.itwill.rest.dto.ArtistSongDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class ArtistQuerydslImpl extends QuerydslRepositorySupport implements ArtistQuerydsl {
	
	private final JPAQueryFactory queryFactory;
	
	public ArtistQuerydslImpl(JPAQueryFactory queryFactory) {
		super(QArtist.class);
		this.queryFactory = queryFactory;
	}
	
	@Override
	public List<ArtistSongDto> selectSongsByArtistId(Integer artistId) {
        QSong song = QSong.song;
        QAlbum album = QAlbum.album;
        QArtist artist = QArtist.artist;
        QArtistRole artistRole = QArtistRole.artistRole;
        QGroupMember groupMember = QGroupMember.groupMember;
        QGroup group = QGroup.group;

        List<Tuple> result = queryFactory
                .select(
                    song.songId,
                    album.albumId,
                    album.albumImage,
                    album.albumName,
                    song.title,
                    artist.artistName,
                    artist.id,
                    group.id,     // 그룹 ID
                    group.groupName     // 그룹 이름
                )
                .from(song)
                .join(song.album, album)
                .join(song.artistRole, artistRole)
                .join(artistRole.artist, artist)
                .leftJoin(groupMember).on(artist.id.eq(groupMember.artist.id))
                .leftJoin(group).on(groupMember.group.id.eq(group.id))
                .where(artistRole.artist.id.eq(artistId)
                    .and(artistRole.roleCode.roleId.eq(10))) // role_id가 10인 경우만 선택
                .groupBy(song.songId, album.albumId, album.albumImage, album.albumName, song.title, artist.artistName, artist.id, group.id, group.groupName)
                .orderBy(song.title.asc())
                .fetch();

        // DTO로 변환
        Map<Integer, ArtistSongDto> songMap = new HashMap<>();

        for (Tuple tuple : result) {
            Integer songId = tuple.get(song.songId);
            ArtistSongDto dto = songMap.computeIfAbsent(songId, k -> {
                ArtistSongDto newDto = new ArtistSongDto();
                newDto.setSongId(songId);
                newDto.setAlbumId(tuple.get(album.albumId));
                newDto.setAlbumImage(tuple.get(album.albumImage));
                newDto.setTitle(tuple.get(song.title));
                newDto.setAlbumName(tuple.get(album.albumName));
                newDto.setArtistId(new ArrayList<>()); // 초기화
                newDto.setArtistName(new ArrayList<>()); // 초기화
                newDto.setGroupId(new ArrayList<>()); // 초기화
                newDto.setGroupName(new ArrayList<>()); // 초기화
                return newDto;
            });

            Integer artistIdFromResult = tuple.get(artist.id);
            String artistName = tuple.get(artist.artistName);
            if (artistIdFromResult != null && !dto.getArtistId().contains(artistIdFromResult)) {
                dto.getArtistId().add(artistIdFromResult);
                dto.getArtistName().add(artistName);
            }

            Integer groupId = tuple.get(group.id);
            String groupName = tuple.get(group.groupName);
            if (groupId != null && !dto.getGroupId().contains(groupId)) {
                dto.getGroupId().add(groupId);
                dto.getGroupName().add(groupName);
            }
        }

        List<ArtistSongDto> sortedSongs = new ArrayList<>(songMap.values());
        sortedSongs.sort(Comparator.comparing(ArtistSongDto::getTitle).reversed());

        return sortedSongs;
	}
	
	@Override
	public List<ArtistAlbumDto> selectAlbumsByArtistId(Integer artistId) {
	    QAlbum album = QAlbum.album;
	    QSong song = QSong.song;
	    QArtist artist = QArtist.artist;
	    QArtistRole artistRole = QArtistRole.artistRole;

	    List<Tuple> result = queryFactory
	        .select(
	            album.albumId,
	            album.albumName,
	            album.albumImage,
	            album.albumType,
	            album.albumReleaseDate,
	            Expressions.stringTemplate("LISTAGG({0}, ', ') WITHIN GROUP (ORDER BY {0})", artist.artistName).as("artistNames"),
	            Expressions.stringTemplate("LISTAGG({0}, ', ') WITHIN GROUP (ORDER BY {1})", artist.id.stringValue(), artist.artistName).as("artistIds")
	        )
	        .from(album)
	        .join(song).on(album.albumId.eq(song.album.albumId))
	        .join(artistRole).on(song.songId.eq(artistRole.song.songId))
	        .join(artist).on(artistRole.artist.id.eq(artist.id))
	        .where(artistRole.artist.id.eq(artistId)
	            .and(artistRole.roleCode.roleId.eq(10)))
	        .groupBy(album.albumId, album.albumName, album.albumImage, album.albumType, album.albumReleaseDate)
	        .orderBy(album.albumReleaseDate.desc())
	        .fetch();

	    return result.stream().map(tuple -> {
	        ArtistAlbumDto dto = new ArtistAlbumDto();
	        dto.setAlbumId(tuple.get(album.albumId));
	        dto.setAlbumName(tuple.get(album.albumName));
	        dto.setAlbumImage(tuple.get(album.albumImage));
	        dto.setAlbumType(tuple.get(album.albumType));
	        dto.setAlbumReleaseDate(tuple.get(album.albumReleaseDate));
	        dto.setArtistName(Arrays.asList(tuple.get(5, String.class).split(", ")));
	        dto.setArtistId(Arrays.stream(tuple.get(6, String.class).split(", "))
	                .map(Integer::parseInt)
	                .collect(Collectors.toList()));
	        return dto;
	    }).collect(Collectors.toList());
	}

}
