package com.itwill.rest.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itwill.rest.domain.Album;
import com.itwill.rest.domain.Song;
import com.itwill.rest.dto.SongSearchResultDto;

public interface SongRepository extends JpaRepository<Song, Integer>, SongQuerydsl {
	
	
	@Query(value = "WITH song_data AS (" +
            "    SELECT " +
            "        s.song_id AS songId, " +
            "        s.title AS name, " +
            "        a.album_id AS albumId, " +
            "        a.album_name AS albumName, " +
            "        a.album_image AS albumImage, " +
            "        GROUP_CONCAT(DISTINCT CASE " +
            "            WHEN g.group_name IS NOT NULL THEN g.group_name " +
            "            ELSE NULL " +
            "        END ORDER BY g.group_name SEPARATOR ', ') AS groupName, " +
            "        GROUP_CONCAT(DISTINCT CASE " +
            "            WHEN art.artist_name IS NOT NULL AND g.group_id IS NULL THEN art.artist_name " +
            "            ELSE NULL " +
            "        END ORDER BY art.artist_name SEPARATOR ', ') AS artistName, " +
            "        GROUP_CONCAT(DISTINCT CASE " +
            "            WHEN g.group_id IS NOT NULL THEN CAST(g.group_id AS CHAR) " +
            "            ELSE NULL " +
            "        END ORDER BY g.group_name SEPARATOR ', ') AS groupId, " +
            "        GROUP_CONCAT(DISTINCT CASE " +
            "            WHEN art.artist_id IS NOT NULL AND g.group_id IS NULL THEN CAST(art.artist_id AS CHAR) " +
            "            ELSE NULL " +
            "        END ORDER BY art.artist_name SEPARATOR ', ') AS artistId, " +
            "        'album' AS type " +
            "    FROM albums a " +
            "    LEFT JOIN songs s ON a.album_id = s.album_id " +
            "    LEFT JOIN artist_roles ar ON s.song_id = ar.song_id AND ar.role_id = 10 " +
            "    LEFT JOIN artists art ON ar.artist_id = art.artist_id " +
            "    LEFT JOIN `groups` g ON ar.group_id = g.group_id " +
            "    WHERE MATCH(a.album_name) AGAINST(:keyword IN BOOLEAN MODE) " +
            "    GROUP BY s.song_id, s.title, a.album_id, a.album_name, a.album_image " +
            "    UNION ALL " +
            "    SELECT " +
            "        s.song_id AS songId, " +
            "        s.title AS name, " +
            "        a.album_id AS albumId, " +
            "        a.album_name AS albumName, " +
            "        a.album_image AS albumImage, " +
            "        NULL AS groupName, " +
            "        GROUP_CONCAT(DISTINCT art.artist_name ORDER BY art.artist_name SEPARATOR ', ') AS artistName, " +
            "        NULL AS groupId, " +
            "        GROUP_CONCAT(DISTINCT CAST(art.artist_id AS CHAR) ORDER BY art.artist_name SEPARATOR ', ') AS artistId, " +
            "        'artist' AS type " +
            "    FROM artists art " +
            "    LEFT JOIN artist_roles ar ON art.artist_id = ar.artist_id " +
            "    LEFT JOIN songs s ON ar.song_id = s.song_id " +
            "    LEFT JOIN albums a ON s.album_id = a.album_id " +
            "    WHERE ar.role_id = 10 AND MATCH(art.artist_name) AGAINST(:keyword IN BOOLEAN MODE) " +
            "    GROUP BY s.song_id, s.title, a.album_id, a.album_name, a.album_image " +
            "    UNION ALL " +
            "    SELECT " +
            "        s.song_id AS songId, " +
            "        s.title AS name, " +
            "        a.album_id AS albumId, " +
            "        a.album_name AS albumName, " +
            "        a.album_image AS albumImage, " +
            "        GROUP_CONCAT(DISTINCT g.group_name ORDER BY g.group_name SEPARATOR ', ') AS groupName, " +
            "        NULL AS artistName, " +
            "        GROUP_CONCAT(DISTINCT CAST(g.group_id AS CHAR) ORDER BY g.group_name SEPARATOR ', ') AS groupId, " +
            "        NULL AS artistId, " +
            "        'group' AS type " +
            "    FROM `groups` g " +
            "    LEFT JOIN artist_roles ar ON g.group_id = ar.group_id " +
            "    LEFT JOIN songs s ON ar.song_id = s.song_id " +
            "    LEFT JOIN albums a ON s.album_id = a.album_id " +
            "    WHERE ar.role_id = 10 AND MATCH(g.group_name) AGAINST(:keyword IN BOOLEAN MODE) " +
            "    GROUP BY s.song_id, s.title, a.album_id, a.album_name, a.album_image " +
            "    UNION ALL " +
            "    SELECT " +
            "        s.song_id AS songId, " +
            "        s.title AS name, " +
            "        s.album_id AS albumId, " +
            "        a.album_name AS albumName, " +
            "        a.album_image AS albumImage, " +
            "        GROUP_CONCAT(DISTINCT CASE " +
            "            WHEN g.group_name IS NOT NULL THEN g.group_name " +
            "            ELSE NULL " +
            "        END ORDER BY g.group_name SEPARATOR ', ') AS groupName, " +
            "        GROUP_CONCAT(DISTINCT CASE " +
            "            WHEN art.artist_name IS NOT NULL AND g.group_id IS NULL THEN art.artist_name " +
            "            ELSE NULL " +
            "        END ORDER BY art.artist_name SEPARATOR ', ') AS artistName, " +
            "        GROUP_CONCAT(DISTINCT CASE " +
            "            WHEN g.group_id IS NOT NULL THEN CAST(g.group_id AS CHAR) " +
            "            ELSE NULL " +
            "        END ORDER BY g.group_name SEPARATOR ', ') AS groupId, " +
            "        GROUP_CONCAT(DISTINCT CASE " +
            "            WHEN art.artist_id IS NOT NULL AND g.group_id IS NULL THEN CAST(art.artist_id AS CHAR) " +
            "            ELSE NULL " +
            "        END ORDER BY art.artist_name SEPARATOR ', ') AS artistId, " +
            "        'song' AS type " +
            "    FROM songs s " +
            "    LEFT JOIN albums a ON s.album_id = a.album_id " +
            "    LEFT JOIN artist_roles ar ON s.song_id = ar.song_id AND ar.role_id = 10 " +
            "    LEFT JOIN artists art ON ar.artist_id = art.artist_id " +
            "    LEFT JOIN `groups` g ON ar.group_id = g.group_id " +
            "    WHERE MATCH(s.title) AGAINST(:keyword IN BOOLEAN MODE) " +
            "    GROUP BY s.song_id, s.title, a.album_id, a.album_name, a.album_image " +
            ") " +
            "SELECT DISTINCT " +
            "    sd.songId AS songId, " +
            "    sd.name AS title, " +
            "    sd.albumId AS albumId, " +
            "    sd.albumName AS albumName, " +
            "    sd.albumImage AS albumImage, " +
            "    sd.artistName AS artistName, " +
            "    sd.groupName AS groupName, " +
            "    sd.artistId AS artistId, " +
            "    sd.groupId AS groupId, " +
            "    COALESCE(sl.like_count, 0) AS likeCount " +
            "FROM song_data sd " +
            "LEFT JOIN (SELECT song_id, COUNT(*) AS like_count FROM likes GROUP BY song_id) AS sl ON sd.songId = sl.song_id " +
            "ORDER BY LENGTH(sd.name), likeCount DESC " +
            "LIMIT :limit OFFSET :offset",
        nativeQuery = true)
	 List<Object[]> findSongsByKeywordOrderByAccuracy(@Param("keyword") String keyword,  @Param("limit") int limit,
	        @Param("offset") int offset );
	 
	 @Query(value = "WITH song_data AS (" +
	            "    SELECT " +
	            "        s.song_id AS songId, " +
	            "        s.title AS name, " +
	            "        a.album_id AS albumId, " +
	            "        a.album_name AS albumName, " +
	            "        a.album_image AS albumImage, " +
	            "        GROUP_CONCAT(DISTINCT CASE " +
	            "            WHEN g.group_name IS NOT NULL THEN g.group_name " +
	            "            ELSE NULL " +
	            "        END ORDER BY g.group_name SEPARATOR ', ') AS groupName, " +
	            "        GROUP_CONCAT(DISTINCT CASE " +
	            "            WHEN art.artist_name IS NOT NULL AND g.group_id IS NULL THEN art.artist_name " +
	            "            ELSE NULL " +
	            "        END ORDER BY art.artist_name SEPARATOR ', ') AS artistName, " +
	            "        GROUP_CONCAT(DISTINCT CASE " +
	            "            WHEN g.group_id IS NOT NULL THEN CAST(g.group_id AS CHAR) " +
	            "            ELSE NULL " +
	            "        END ORDER BY g.group_name SEPARATOR ', ') AS groupId, " +
	            "        GROUP_CONCAT(DISTINCT CASE " +
	            "            WHEN art.artist_id IS NOT NULL AND g.group_id IS NULL THEN CAST(art.artist_id AS CHAR) " +
	            "            ELSE NULL " +
	            "        END ORDER BY art.artist_name SEPARATOR ', ') AS artistId, " +
	            "        'album' AS type " +
	            "    FROM albums a " +
	            "    LEFT JOIN songs s ON a.album_id = s.album_id " +
	            "    LEFT JOIN artist_roles ar ON s.song_id = ar.song_id AND ar.role_id = 10 " +
	            "    LEFT JOIN artists art ON ar.artist_id = art.artist_id " +
	            "    LEFT JOIN `groups` g ON ar.group_id = g.group_id " +
	            "    WHERE MATCH(a.album_name) AGAINST(:keyword IN BOOLEAN MODE) " +
	            "    GROUP BY s.song_id, s.title, a.album_id, a.album_name, a.album_image " +
	            "    UNION ALL " +
	            "    SELECT " +
	            "        s.song_id AS songId, " +
	            "        s.title AS name, " +
	            "        a.album_id AS albumId, " +
	            "        a.album_name AS albumName, " +
	            "        a.album_image AS albumImage, " +
	            "        NULL AS groupName, " +
	            "        GROUP_CONCAT(DISTINCT art.artist_name ORDER BY art.artist_name SEPARATOR ', ') AS artistName, " +
	            "        NULL AS groupId, " +
	            "        GROUP_CONCAT(DISTINCT CAST(art.artist_id AS CHAR) ORDER BY art.artist_name SEPARATOR ', ') AS artistId, " +
	            "        'artist' AS type " +
	            "    FROM artists art " +
	            "    LEFT JOIN artist_roles ar ON art.artist_id = ar.artist_id " +
	            "    LEFT JOIN songs s ON ar.song_id = s.song_id " +
	            "    LEFT JOIN albums a ON s.album_id = a.album_id " +
	            "    WHERE ar.role_id = 10 AND MATCH(art.artist_name) AGAINST(:keyword IN BOOLEAN MODE) " +
	            "    GROUP BY s.song_id, s.title, a.album_id, a.album_name, a.album_image " +
	            "    UNION ALL " +
	            "    SELECT " +
	            "        s.song_id AS songId, " +
	            "        s.title AS name, " +
	            "        a.album_id AS albumId, " +
	            "        a.album_name AS albumName, " +
	            "        a.album_image AS albumImage, " +
	            "        GROUP_CONCAT(DISTINCT g.group_name ORDER BY g.group_name SEPARATOR ', ') AS groupName, " +
	            "        NULL AS artistName, " +
	            "        GROUP_CONCAT(DISTINCT CAST(g.group_id AS CHAR) ORDER BY g.group_name SEPARATOR ', ') AS groupId, " +
	            "        NULL AS artistId, " +
	            "        'group' AS type " +
	            "    FROM `groups` g " +
	            "    LEFT JOIN artist_roles ar ON g.group_id = ar.group_id " +
	            "    LEFT JOIN songs s ON ar.song_id = s.song_id " +
	            "    LEFT JOIN albums a ON s.album_id = a.album_id " +
	            "    WHERE ar.role_id = 10 AND MATCH(g.group_name) AGAINST(:keyword IN BOOLEAN MODE) " +
	            "    GROUP BY s.song_id, s.title, a.album_id, a.album_name, a.album_image " +
	            "    UNION ALL " +
	            "    SELECT " +
	            "        s.song_id AS songId, " +
	            "        s.title AS name, " +
	            "        s.album_id AS albumId, " +
	            "        a.album_name AS albumName, " +
	            "        a.album_image AS albumImage, " +
	            "        GROUP_CONCAT(DISTINCT CASE " +
	            "            WHEN g.group_name IS NOT NULL THEN g.group_name " +
	            "            ELSE NULL " +
	            "        END ORDER BY g.group_name SEPARATOR ', ') AS groupName, " +
	            "        GROUP_CONCAT(DISTINCT CASE " +
	            "            WHEN art.artist_name IS NOT NULL AND g.group_id IS NULL THEN art.artist_name " +
	            "            ELSE NULL " +
	            "        END ORDER BY art.artist_name SEPARATOR ', ') AS artistName, " +
	            "        GROUP_CONCAT(DISTINCT CASE " +
	            "            WHEN g.group_id IS NOT NULL THEN CAST(g.group_id AS CHAR) " +
	            "            ELSE NULL " +
	            "        END ORDER BY g.group_name SEPARATOR ', ') AS groupId, " +
	            "        GROUP_CONCAT(DISTINCT CASE " +
	            "            WHEN art.artist_id IS NOT NULL AND g.group_id IS NULL THEN CAST(art.artist_id AS CHAR) " +
	            "            ELSE NULL " +
	            "        END ORDER BY art.artist_name SEPARATOR ', ') AS artistId, " +
	            "        'song' AS type " +
	            "    FROM songs s " +
	            "    LEFT JOIN albums a ON s.album_id = a.album_id " +
	            "    LEFT JOIN artist_roles ar ON s.song_id = ar.song_id AND ar.role_id = 10 " +
	            "    LEFT JOIN artists art ON ar.artist_id = art.artist_id " +
	            "    LEFT JOIN `groups` g ON ar.group_id = g.group_id " +
	            "    WHERE MATCH(s.title) AGAINST(:keyword IN BOOLEAN MODE) " +
	            "    GROUP BY s.song_id, s.title, a.album_id, a.album_name, a.album_image " +
	            ") " +
	            "SELECT DISTINCT " +
	            "    sd.songId AS songId, " +
	            "    sd.name AS title, " +
	            "    sd.albumId AS albumId, " +
	            "    sd.albumName AS albumName, " +
	            "    sd.albumImage AS albumImage, " +
	            "    sd.artistName AS artistName, " +
	            "    sd.groupName AS groupName, " +
	            "    sd.artistId AS artistId, " +
	            "    sd.groupId AS groupId, " +
	            "    COALESCE(sl.like_count, 0) AS likeCount " +
	            "FROM song_data sd " +
	            "LEFT JOIN (SELECT song_id, COUNT(*) AS like_count FROM likes GROUP BY song_id) AS sl ON sd.songId = sl.song_id " +
	            "ORDER BY LENGTH(sd.name), likeCount DESC " +
	            "LIMIT :limit OFFSET :offset",
	        nativeQuery = true)
		 List<Object[]> findSongsByKeywordOrderByRecency(@Param("keyword") String keyword,  @Param("limit") int limit,
		        @Param("offset") int offset );
	
}
