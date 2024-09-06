package com.itwill.rest.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
	 
	 
	 
	 @Query(value = "WITH song_data AS ("
	 		+ "    SELECT "
	 		+ "        s.song_id, "
	 		+ "        s.title AS name, "
	 		+ "        a.album_id, "
	 		+ "        a.album_name, "
	 		+ "        a.album_image, "
	 		+ "        a.album_release_date, "
	 		+ "        GROUP_CONCAT(DISTINCT CASE  "
	 		+ "            WHEN g.group_name IS NOT NULL THEN g.group_name  "
	 		+ "            ELSE NULL  "
	 		+ "        END ORDER BY g.group_name SEPARATOR ', ') AS group_name, "
	 		+ "        GROUP_CONCAT(DISTINCT CASE  "
	 		+ "            WHEN art.artist_name IS NOT NULL AND g.group_id IS NULL THEN art.artist_name  "
	 		+ "            ELSE NULL  "
	 		+ "        END ORDER BY art.artist_name SEPARATOR ', ') AS artist_name, "
	 		+ "        GROUP_CONCAT(DISTINCT CASE  "
	 		+ "            WHEN g.group_id IS NOT NULL THEN CAST(g.group_id AS CHAR)  "
	 		+ "            ELSE NULL  "
	 		+ "        END ORDER BY g.group_name SEPARATOR ', ') AS group_id, "
	 		+ "        GROUP_CONCAT(DISTINCT CASE  "
	 		+ "            WHEN art.artist_id IS NOT NULL AND g.group_id IS NULL THEN CAST(art.artist_id AS CHAR)  "
	 		+ "            ELSE NULL  "
	 		+ "        END ORDER BY art.artist_name SEPARATOR ', ') AS artist_id, "
	 		+ "        'album' AS type "
	 		+ "    FROM albums a "
	 		+ "    LEFT JOIN songs s ON a.album_id = s.album_id "
	 		+ "    LEFT JOIN artist_roles ar ON s.song_id = ar.song_id AND ar.role_id = 10 "
	 		+ "    LEFT JOIN artists art ON ar.artist_id = art.artist_id "
	 		+ "    LEFT JOIN `groups` g ON ar.group_id = g.group_id "
	 		+ "    WHERE MATCH(a.album_name) AGAINST(:keyword IN BOOLEAN MODE) "
	 		+ "    GROUP BY s.song_id, s.title, a.album_id, a.album_name, a.album_image, a.album_release_date "
	 		+ " "
	 		+ "    UNION ALL "
	 		+ " "
	 		+ "    SELECT "
	 		+ "        s.song_id, "
	 		+ "        s.title AS name, "
	 		+ "        a.album_id, "
	 		+ "        a.album_name, "
	 		+ "        a.album_image, "
	 		+ "        a.album_release_date, "
	 		+ "        NULL AS group_name, "
	 		+ "        GROUP_CONCAT(DISTINCT art.artist_name ORDER BY art.artist_name SEPARATOR ', ') AS artist_name, "
	 		+ "        NULL AS group_id, "
	 		+ "        GROUP_CONCAT(DISTINCT CAST(art.artist_id AS CHAR) ORDER BY art.artist_name SEPARATOR ', ') AS artist_id, "
	 		+ "        'artist' AS type "
	 		+ "    FROM artists art "
	 		+ "    LEFT JOIN artist_roles ar ON art.artist_id = ar.artist_id "
	 		+ "    LEFT JOIN songs s ON ar.song_id = s.song_id "
	 		+ "    LEFT JOIN albums a ON s.album_id = a.album_id "
	 		+ "    WHERE ar.role_id = 10 AND MATCH(art.artist_name) AGAINST(:keyword IN BOOLEAN MODE) "
	 		+ "    GROUP BY s.song_id, s.title, a.album_id, a.album_name, a.album_image, a.album_release_date "
	 		+ " "
	 		+ "    UNION ALL "
	 		+ " "
	 		+ "    SELECT "
	 		+ "        s.song_id, "
	 		+ "        s.title AS name, "
	 		+ "        a.album_id, "
	 		+ "        a.album_name, "
	 		+ "        a.album_image, "
	 		+ "        a.album_release_date, "
	 		+ "        GROUP_CONCAT(DISTINCT g.group_name ORDER BY g.group_name SEPARATOR ', ') AS group_name, "
	 		+ "        NULL AS artist_name, "
	 		+ "        GROUP_CONCAT(DISTINCT CAST(g.group_id AS CHAR) ORDER BY g.group_name SEPARATOR ', ') AS group_id, "
	 		+ "        NULL AS artist_id, "
	 		+ "        'group' AS type "
	 		+ "    FROM `groups` g "
	 		+ "    LEFT JOIN artist_roles ar ON g.group_id = ar.group_id "
	 		+ "    LEFT JOIN songs s ON ar.song_id = s.song_id "
	 		+ "    LEFT JOIN albums a ON s.album_id = a.album_id "
	 		+ "    WHERE ar.role_id = 10 AND MATCH(g.group_name) AGAINST(:keyword IN BOOLEAN MODE) "
	 		+ "    GROUP BY s.song_id, s.title, a.album_id, a.album_name, a.album_image, a.album_release_date "
	 		+ " "
	 		+ "    UNION ALL "
	 		+ " "
	 		+ "    SELECT "
	 		+ "        s.song_id, "
	 		+ "        s.title AS name, "
	 		+ "        s.album_id, "
	 		+ "        a.album_name, "
	 		+ "        a.album_image, "
	 		+ "        a.album_release_date, "
	 		+ "        GROUP_CONCAT(DISTINCT CASE  "
	 		+ "            WHEN g.group_name IS NOT NULL THEN g.group_name  "
	 		+ "            ELSE NULL  "
	 		+ "        END ORDER BY g.group_name SEPARATOR ', ') AS group_name, "
	 		+ "        GROUP_CONCAT(DISTINCT CASE  "
	 		+ "            WHEN art.artist_name IS NOT NULL AND g.group_id IS NULL THEN art.artist_name  "
	 		+ "            ELSE NULL  "
	 		+ "        END ORDER BY art.artist_name SEPARATOR ', ') AS artist_name, "
	 		+ "        GROUP_CONCAT(DISTINCT CASE  "
	 		+ "            WHEN g.group_id IS NOT NULL THEN CAST(g.group_id AS CHAR)  "
	 		+ "            ELSE NULL  "
	 		+ "        END ORDER BY g.group_name SEPARATOR ', ') AS group_id, "
	 		+ "        GROUP_CONCAT(DISTINCT CASE  "
	 		+ "            WHEN art.artist_id IS NOT NULL AND g.group_id IS NULL THEN CAST(art.artist_id AS CHAR)  "
	 		+ "            ELSE NULL  "
	 		+ "        END ORDER BY art.artist_name SEPARATOR ', ') AS artist_id, "
	 		+ "        'song' AS type "
	 		+ "    FROM songs s "
	 		+ "    LEFT JOIN albums a ON s.album_id = a.album_id "
	 		+ "    LEFT JOIN artist_roles ar ON s.song_id = ar.song_id AND ar.role_id = 10 "
	 		+ "    LEFT JOIN artists art ON ar.artist_id = art.artist_id "
	 		+ "    LEFT JOIN `groups` g ON ar.group_id = g.group_id "
	 		+ "    WHERE MATCH(s.title) AGAINST(:keyword IN BOOLEAN MODE) "
	 		+ "    GROUP BY s.song_id, s.title, s.album_id, a.album_name, a.album_image, a.album_release_date "
	 		+ ")  "
	 		+ "SELECT DISTINCT "
	 		+ "    sd.song_id, "
	 		+ "    sd.name AS title, "
	 		+ "    sd.album_id, "
	 		+ "    sd.album_name, "
	 		+ "    sd.album_image, "
	 		+ "    sd.album_release_date, "
	 		+ "    sd.artist_name, "
	 		+ "    sd.group_name, "
	 		+ "    sd.artist_id, "
	 		+ "    sd.group_id, "
	 		+ "    COALESCE(sl.like_count, 0) AS like_count  "
	 		+ "FROM song_data sd  "
	 		+ "LEFT JOIN (SELECT song_id, COUNT(*) AS like_count FROM likes GROUP BY song_id) AS sl ON sd.song_id = sl.song_id  "
	 		+ "ORDER BY sd.album_release_date DESC, like_count DESC  "
	 		+ "LIMIT :limit OFFSET :offset "
	 		+ "",
	        nativeQuery = true)
		 List<Object[]> findSongsByKeywordOrderByRecency(@Param("keyword") String keyword,  @Param("limit") int limit,
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
		            "ORDER BY sd.name, likeCount DESC " +
		            "LIMIT :limit OFFSET :offset",
		        nativeQuery = true)
			 List<Object[]> findSongsByKeywordOrderByAlphabet(@Param("keyword") String keyword,  @Param("limit") int limit,
			        @Param("offset") int offset );
	

	// 앨범 발매일 기준 내림차순 정렬(페이징)
	Page<Song> findByOrderByAlbum_AlbumReleaseDateDesc(Pageable pageable);

	// 좋아요 수 기준 내림차순 정렬
	@Query("SELECT s FROM Song s LEFT JOIN s.likes l GROUP BY s.songId ORDER BY COUNT(l) DESC")
	List<Song> findByOrderByLikesCountDesc();

	// 좋아요 수 기준 내림차순 정렬(페이징)
	@Query("SELECT s FROM Song s LEFT JOIN s.likes l GROUP BY s.songId ORDER BY COUNT(l) DESC")
	Page<Song> findByOrderByLikesCountDesc(Pageable pageable);

	// 좋아요 수 기준으로 특정 장르의 노래들을 내림차순으로 정렬(페이징)
	@Query("SELECT sg.song FROM SongGenre sg LEFT JOIN sg.song.likes l WHERE sg.genreCode.genreName = :genreName GROUP BY sg.song.songId ORDER BY COUNT(l) DESC")
	Page<Song> findByGenreNameOrderByLikesCountDesc(@Param("genreName") String genreName, Pageable pageable);
	
	
	@Query(value = "SELECT "
            + "    s.song_id, "
            + "    s.title, "
            + "    a.album_id, "
            + "    a.album_name, "
            + "    a.album_image, "
            + "    GROUP_CONCAT(DISTINCT CASE "
            + "        WHEN g.group_id IS NULL THEN art.artist_name "
            + "        ELSE NULL "
            + "    END ORDER BY art.artist_name SEPARATOR ', ') AS artist_name, "
            + "    GROUP_CONCAT(DISTINCT g.group_name ORDER BY g.group_name SEPARATOR ', ') AS group_name, "
            + "    GROUP_CONCAT(DISTINCT CASE "
            + "        WHEN g.group_id IS NULL THEN CAST(art.artist_id AS CHAR) "
            + "        ELSE NULL "
            + "    END ORDER BY art.artist_name SEPARATOR ', ') AS artist_id, "
            + "    GROUP_CONCAT(DISTINCT g.group_id ORDER BY g.group_name SEPARATOR ', ') AS group_id, "
            + "    COUNT(sl.song_id) AS like_count "
            + "FROM songs s "
            + "LEFT JOIN likes sl ON s.song_id = sl.song_id "
            + "LEFT JOIN albums a ON s.album_id = a.album_id "
            + "LEFT JOIN artist_roles ar ON s.song_id = ar.song_id AND ar.role_id = 10 "
            + "LEFT JOIN artists art ON ar.artist_id = art.artist_id "
            + "LEFT JOIN `groups` g ON ar.group_id = g.group_id "
            + "WHERE MATCH(s.title) AGAINST(? IN BOOLEAN MODE) "
            + "GROUP BY s.song_id, s.title, a.album_id, a.album_name, a.album_image "
            + "ORDER BY LENGTH(s.title), like_count DESC "
            + "LIMIT 5", 
	  nativeQuery = true)
	List<Object[]> searchAllSongs(@Param("keyword") String keyword);

	
}
