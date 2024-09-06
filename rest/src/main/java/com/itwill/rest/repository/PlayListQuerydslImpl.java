package com.itwill.rest.repository;

import com.itwill.rest.domain.QAlbum;
import com.itwill.rest.domain.QPlayList;
import com.itwill.rest.domain.QPlayListSong;
import com.itwill.rest.domain.QSong;
import com.itwill.rest.dto.PlayListFirstAlbumImgDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlayListQuerydslImpl extends QuerydslRepositorySupport implements PlayListQuerydsl {

    public PlayListQuerydslImpl() {
        super(QPlayList.class);
    }

    @Override
    public List<PlayListFirstAlbumImgDto> selectByUserId(Integer id) {
        QPlayList playlists = QPlayList.playList;
        QPlayListSong playlistSongs = QPlayListSong.playListSong;
        QSong songs = QSong.song;
        QAlbum albums = QAlbum.album;

        // 서브쿼리: 가장 최근의 노래 정보를 찾기 위한 서브쿼리
        JPAQuery<Integer> subQuery = new JPAQuery<>(getEntityManager())
            .select(playlistSongs.playlistSongId.max())
            .from(playlistSongs)
            .where(playlistSongs.playList.pListId.eq(playlists.pListId));

        // 메인 쿼리
        JPAQuery<PlayListFirstAlbumImgDto> query = new JPAQuery<>(getEntityManager());

        return query.select(Projections.bean(
                PlayListFirstAlbumImgDto.class,
                playlists.pListId.as("plistId"),
                playlists.pListName.as("plistName"),
                albums.albumImage.as("albumImage")
            ))
            .from(playlists)
            .leftJoin(playlistSongs)
                .on(playlists.pListId.eq(playlistSongs.playList.pListId)
                    .and(playlistSongs.playlistSongId.eq(subQuery)))  // 서브쿼리에서 가장 최근의 createdTime을 가져오는 조건
            .leftJoin(songs).on(playlistSongs.song.songId.eq(songs.songId))
            .leftJoin(albums).on(songs.album.albumId.eq(albums.albumId))
            .where(playlists.user.id.eq(id))  // 사용자 ID로 필터링
            .orderBy(playlists.pListId.asc())
            .fetch();
    }
}
