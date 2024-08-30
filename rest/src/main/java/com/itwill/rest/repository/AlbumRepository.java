package com.itwill.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itwill.rest.domain.Album;
import com.itwill.rest.dto.ContentDto;

public interface AlbumRepository extends JpaRepository<Album, Integer> {
    // groupId로 모든 albumId를 찾는 메서드
    @Query("SELECT DISTINCT ar.song.album.albumId FROM ArtistRole ar WHERE ar.group.id = :groupId")
    List<Integer> findAlbumIdsByGroupId(@Param("groupId") Integer groupId);
    
 // groupId로 모든 albumId를 찾는 메서드
    @Query("SELECT DISTINCT ar.song.album.albumId FROM ArtistRole ar WHERE ar.artist.id = :artistId")
    List<Integer> findAlbumIdsByArtistId(@Param("artistId") Integer artistId);
	
	@Query(value = "SELECT  "
	        + "    'album' AS type, "
	        + "    a.album_id AS id, "
	        + "    a.album_name AS name, "
	        + "    a.album_release_date AS release_date, "
	        + "    COUNT(al.album_id) AS like_count "
	        + "FROM albums a "
	        + "LEFT JOIN album_likes al ON a.album_id = al.album_id "
	        + "WHERE a.album_name LIKE CONCAT('%', :keyword, '%') "
	        + "GROUP BY a.album_id, a.album_name, a.album_release_date "
	        + " "
	        + "UNION ALL "
	        + " "
	        + "SELECT "
	        + "    'song' AS type, "
	        + "    s.song_id AS id, "
	        + "    s.title AS name, "
	        + "    NULL AS release_date, "
	        + "    COUNT(sl.song_id) AS like_count "
	        + "FROM songs s "
	        + "LEFT JOIN likes sl ON s.song_id = sl.song_id "
	        + "WHERE s.title LIKE CONCAT('%', :keyword, '%') "
	        + "GROUP BY s.song_id, s.title "
	        + " "
	        + "UNION ALL "
	        + " "
	        + "SELECT "
	        + "    'group' AS type, "
	        + "    g.group_id AS id, "
	        + "    g.group_name AS name, "
	        + "    NULL AS release_date, "
	        + "    COUNT(gl.group_id) AS like_count "
	        + "FROM `groups` g "
	        + "LEFT JOIN group_likes gl ON g.group_id = gl.group_id "
	        + "WHERE g.group_name LIKE CONCAT('%', :keyword, '%') "
	        + "GROUP BY g.group_id, g.group_name "
	        + " "
	        + "UNION ALL "
	        + " "
	        + "SELECT "
	        + "    'artist' AS type, "
	        + "    art.artist_id AS id, "
	        + "    art.artist_name AS name, "
	        + "    NULL AS release_date, "
	        + "    COUNT(artl.artist_id) AS like_count "
	        + "FROM artists art "
	        + "LEFT JOIN artist_likes artl ON art.artist_id = artl.artist_id "
	        + "WHERE art.artist_name LIKE CONCAT('%', :keyword, '%') "
	        + "GROUP BY art.artist_id, art.artist_name "
	        + " "
	        + "ORDER BY "
	        + "    LENGTH(name), "
	        + "    like_count DESC; "
	        + "", nativeQuery = true)
	List<Object[]> findAllContentByKeyword(@Param("keyword") String keyword);

	 @Query(value = "SELECT " +
	            "    'album' AS type, " +
	            "    a.album_id AS id, " +
	            "    a.album_name AS name, " +
	            "    a.album_release_date AS release_date, " +
	            "    COUNT(al.album_id) AS like_count " +
	            "FROM albums a " +
	            "LEFT JOIN album_likes al ON a.album_id = al.album_id " +
	            "WHERE MATCH(a.album_name) AGAINST(:keyword IN BOOLEAN MODE) " +
	            "GROUP BY a.album_id, a.album_name, a.album_release_date " +
	            " " +
	            "UNION ALL " +
	            " " +
	            "SELECT " +
	            "    'song' AS type, " +
	            "    s.song_id AS id, " +
	            "    s.title AS name, " +
	            "    NULL AS release_date, " +
	            "    COUNT(sl.song_id) AS like_count " +
	            "FROM songs s " +
	            "LEFT JOIN likes sl ON s.song_id = sl.song_id " +
	            "WHERE MATCH(s.title) AGAINST(:keyword IN BOOLEAN MODE) " +
	            "GROUP BY s.song_id, s.title " +
	            " " +
	            "UNION ALL " +
	            " " +
	            "SELECT " +
	            "    'group' AS type, " +
	            "    g.group_id AS id, " +
	            "    g.group_name AS name, " +
	            "    NULL AS release_date, " +
	            "    COUNT(gl.group_id) AS like_count " +
	            "FROM `groups` g " +
	            "LEFT JOIN group_likes gl ON g.group_id = gl.group_id " +
	            "WHERE MATCH(g.group_name) AGAINST(:keyword IN BOOLEAN MODE) " +
	            "GROUP BY g.group_id, g.group_name " +
	            " " +
	            "UNION ALL " +
	            " " +
	            "SELECT " +
	            "    'artist' AS type, " +
	            "    art.artist_id AS id, " +
	            "    art.artist_name AS name, " +
	            "    NULL AS release_date, " +
	            "    COUNT(artl.artist_id) AS like_count " +
	            "FROM artists art " +
	            "LEFT JOIN artist_likes artl ON art.artist_id = artl.artist_id " +
	            "WHERE MATCH(art.artist_name) AGAINST(:keyword IN BOOLEAN MODE) " +
	            "GROUP BY art.artist_id, art.artist_name " +
	            " " +
	            "ORDER BY " +
	            "    LENGTH(name), " +
	            "    like_count DESC", nativeQuery = true)
	    List<Object[]> findAllContentByKeywordFullText(@Param("keyword") String keyword);

	

	@Query(value = "SELECT * FROM ALBUMS WHERE GET_INITIAL_SOUND(album_name) LIKE CONCAT('%', GET_INITIAL_SOUND(:keyword), '%')", nativeQuery = true)
	List<Album> findByAlbumNameInitialSound(@Param("keyword") String keyword);

}
