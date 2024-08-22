package com.itwill.rest.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.itwill.rest.domain.QAlbum;
import com.itwill.rest.domain.QArtist;
import com.itwill.rest.domain.QArtistRole;
import com.itwill.rest.domain.QLike;
import com.itwill.rest.domain.QSong;
import com.itwill.rest.domain.QUser;
import com.itwill.rest.dto.UserLikeDto;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class UserQuerydslImpl extends QuerydslRepositorySupport implements UserQuerydsl {

	public UserQuerydslImpl() {
		super(QUser.class);
	}
	
	// 08/21 날짜 기준 artist name 여러개 출력안됨! 수정필요
	@Override
	public List<UserLikeDto> selectLikesByUserid(Integer id) {
        QLike like = QLike.like;
        QSong song = QSong.song;
        QAlbum album = QAlbum.album;
        QArtistRole artistRole = QArtistRole.artistRole;
        QArtist artist = QArtist.artist;

        List<Tuple> tuples = new JPAQuery<>(getEntityManager())
                .select(
                    song.songId,
                    song.title,
                    album.albumId,
                    album.albumName,
                    album.albumImage,
                    artist.id,
                    artist.artistName
                )
                .from(like)
                .join(song).on(like.likeId.songId.eq(song.songId))
                .join(album).on(song.album.albumId.eq(album.albumId))
                .leftJoin(artistRole).on(song.songId.eq(artistRole.song.songId))
                .leftJoin(artist).on(artistRole.artist.id.eq(artist.id))
                .where(like.likeId.id.eq(id).and(artistRole.roleCode.roleId.eq(10)))
                .fetch();

        if (!tuples.isEmpty()) {
            // 중복 제거 및 집계
            Map<Integer, UserLikeDto> dtoMap = new HashMap<>();

            for (Tuple tuple : tuples) {
                Integer songId = tuple.get(song.songId);
                String title = tuple.get(song.title);
                Integer albumId = tuple.get(album.albumId);
                String albumName = tuple.get(album.albumName);
                String albumImage = tuple.get(album.albumImage);
                Integer artistId = tuple.get(artist.id);
                String artistName = tuple.get(artist.artistName);

                // DTO가 이미 존재하면 아티스트 정보를 추가
                UserLikeDto dto = dtoMap.get(songId);
                if (dto == null) {
                    dto = new UserLikeDto();
                    dto.setSongId(songId);
                    dto.setTitle(title);
                    dto.setAlbumId(albumId);
                    dto.setAlbumName(albumName);
                    dto.setAlbumImage(albumImage);
//                    dto.setArtistId("");  // 초기값
//                    dto.setArtistName(""); // 초기값
                    dtoMap.put(songId, dto);
                }

                // 아티스트 정보를 추가
                Set<Integer> artistIds = new HashSet<>();
                Set<String> artistNames = new HashSet<>();
                
                if (artistId != null) {
                    artistIds.add(artistId);
                }
                if (artistName != null) {
                    artistNames.add(artistName);
                }

                // 현재 DTO의 아티스트 정보 업데이트
//                dto.setArtistId(String.join(", ", artistIds.stream().map(String::valueOf).collect(Collectors.toSet())));
//                dto.setArtistName(String.join(", ", artistNames));
            }

            // 모든 DTO 반환
            return dtoMap.values().stream().collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}
