package com.itwill.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itwill.rest.domain.Album;

public interface AlbumRepository extends JpaRepository<Album, Integer> {
	// groupId로 모든 albumId를 찾는 메서드
	@Query("SELECT DISTINCT ar.song.album.albumId FROM ArtistRole ar WHERE ar.group.id = :groupId")
	List<Integer> findAlbumIdsByGroupId(@Param("groupId") Integer groupId);

	// artistId로 모든 albumId를 찾는 메서드
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

	@Query(value = """
						SELECT
			    a.album_id,
			    a.album_name,
			    a.album_image,
			    a.album_type,
			    a.album_release_date,
			    mp.participant_name AS artist_name,
			    mp.participant_id AS artist_id,
			    mp.participant_type AS artist_type,
			    (SELECT COALESCE(ls.like_count, 0)
			     FROM likes_statistics ls
			     WHERE ls.type = 'album'
			       AND ls.item_id = a.album_id
			    ) AS like_count
			FROM albums a
			LEFT JOIN (
			    SELECT
			        tmp.album_id,
			        tmp.participant_name,
			        tmp.participant_id,
			        tmp.participant_type,
			        tmp.participation_count
			    FROM (
			        SELECT
			            s.album_id,
			            COALESCE(g.group_name, art.artist_name) AS participant_name,
			            COALESCE(g.group_id, art.artist_id) AS participant_id,
			            CASE
			                WHEN g.group_id IS NOT NULL THEN 'group'
			                ELSE 'artist'
			            END AS participant_type,
			            COUNT(*) as participation_count,
			            ROW_NUMBER() OVER (PARTITION BY s.album_id ORDER BY COUNT(*) DESC) as rn
			        FROM songs s
			        JOIN artist_roles ar ON s.song_id = ar.song_id
							AND ar.role_id = 10
			        LEFT JOIN artists art ON ar.artist_id = art.artist_id
			        LEFT JOIN `groups` g ON ar.group_id = g.group_id
			        WHERE EXISTS (
			            SELECT 1
			            FROM albums a_inner
			            WHERE a_inner.album_id = s.album_id
			              AND a_inner.album_name LIKE CONCAT('%', :keyword, '%')
			        ) 
			        GROUP BY s.album_id, participant_name, participant_id, participant_type
			    ) AS tmp
			    WHERE tmp.rn = 1
			) mp ON a.album_id = mp.album_id
			WHERE a.album_name LIKE CONCAT('%', :keyword, '%')
			GROUP BY a.album_id, a.album_name, a.album_image, a.album_type, a.album_release_date,
			         mp.participant_name, mp.participant_id, mp.participant_type
			ORDER BY LENGTH(a.album_name), like_count DESC
			LIMIT 5;
						""", nativeQuery = true)
	List<Object[]> searchAllAlbums(@Param("keyword") String keyword);

	@Query(value = """
						SELECT
			    a.album_id,
			    a.album_name,
			    a.album_image,
			    a.album_type,
			    a.album_release_date,
			    mp.participant_name AS artist_name,
			    mp.participant_id AS artist_id,
			    mp.participant_type AS artist_type,
			    (SELECT COALESCE(ls.like_count, 0)
			     FROM likes_statistics ls
			     WHERE ls.type = 'album'
			       AND ls.item_id = a.album_id
			    ) AS like_count
			FROM albums a
			LEFT JOIN (
			    SELECT
			        tmp.album_id,
			        tmp.participant_name,
			        tmp.participant_id,
			        tmp.participant_type,
			        tmp.participation_count
			    FROM (
			        SELECT
			            s.album_id,
			            COALESCE(g.group_name, art.artist_name) AS participant_name,
			            COALESCE(g.group_id, art.artist_id) AS participant_id,
			            CASE
			                WHEN g.group_id IS NOT NULL THEN 'group'
			                ELSE 'artist'
			            END AS participant_type,
			            COUNT(*) as participation_count,
			            ROW_NUMBER() OVER (PARTITION BY s.album_id ORDER BY COUNT(*) DESC) as rn
			        FROM songs s
			        JOIN artist_roles ar ON s.song_id = ar.song_id
							AND ar.role_id = 10
			        LEFT JOIN artists art ON ar.artist_id = art.artist_id
			        LEFT JOIN `groups` g ON ar.group_id = g.group_id
			        WHERE EXISTS (
			            SELECT 1
			            FROM albums a_inner
			            WHERE a_inner.album_id = s.album_id
			              AND a_inner.album_name LIKE CONCAT('%', :keyword, '%')
			        ) 
			        GROUP BY s.album_id, participant_name, participant_id, participant_type
			    ) AS tmp
			    WHERE tmp.rn = 1
			) mp ON a.album_id = mp.album_id
			WHERE a.album_name LIKE CONCAT('%', :keyword, '%')
			GROUP BY a.album_id, a.album_name, a.album_image, a.album_type, a.album_release_date,
			         mp.participant_name, mp.participant_id, mp.participant_type
			ORDER BY LENGTH(a.album_name), like_count DESC
			"""
			+ "LIMIT 18 OFFSET :offset"
						, nativeQuery = true)
	List<Object[]> searchAlbumsAccuracy(@Param("keyword") String keyword, @Param("offset") int offset);

	@Query(value = """
						SELECT
			    a.album_id,
			    a.album_name,
			    a.album_image,
			    a.album_type,
			    a.album_release_date,
			    mp.participant_name AS artist_name,
			    mp.participant_id AS artist_id,
			    mp.participant_type AS artist_type,
			    (SELECT COALESCE(ls.like_count, 0)
			     FROM likes_statistics ls
			     WHERE ls.type = 'album'
			       AND ls.item_id = a.album_id
			    ) AS like_count
			FROM albums a
			LEFT JOIN (
			    SELECT
			        tmp.album_id,
			        tmp.participant_name,
			        tmp.participant_id,
			        tmp.participant_type,
			        tmp.participation_count
			    FROM (
			        SELECT
			            s.album_id,
			            COALESCE(g.group_name, art.artist_name) AS participant_name,
			            COALESCE(g.group_id, art.artist_id) AS participant_id,
			            CASE
			                WHEN g.group_id IS NOT NULL THEN 'group'
			                ELSE 'artist'
			            END AS participant_type,
			            COUNT(*) as participation_count,
			            ROW_NUMBER() OVER (PARTITION BY s.album_id ORDER BY COUNT(*) DESC) as rn
			        FROM songs s
			        JOIN artist_roles ar ON s.song_id = ar.song_id
							AND ar.role_id = 10
			        LEFT JOIN artists art ON ar.artist_id = art.artist_id
			        LEFT JOIN `groups` g ON ar.group_id = g.group_id
			        WHERE EXISTS (
			            SELECT 1
			            FROM albums a_inner
			            WHERE a_inner.album_id = s.album_id
			              AND a_inner.album_name LIKE CONCAT('%', :keyword, '%')
			        ) 
			        GROUP BY s.album_id, participant_name, participant_id, participant_type
			    ) AS tmp
			    WHERE tmp.rn = 1
			) mp ON a.album_id = mp.album_id
			WHERE a.album_name LIKE CONCAT('%', :keyword, '%')
			GROUP BY a.album_id, a.album_name, a.album_image, a.album_type, a.album_release_date,
			         mp.participant_name, mp.participant_id, mp.participant_type
			ORDER BY a.album_release_date DESC, like_count DESC
			"""
			+ "LIMIT 18 OFFSET :offset"
						, nativeQuery = true)
	List<Object[]> searchAlbumsRecency(@Param("keyword") String keyword, @Param("offset") int offset);

	@Query(value = """
						SELECT
			    a.album_id,
			    a.album_name,
			    a.album_image,
			    a.album_type,
			    a.album_release_date,
			    mp.participant_name AS artist_name,
			    mp.participant_id AS artist_id,
			    mp.participant_type AS artist_type,
			    (SELECT COALESCE(ls.like_count, 0)
			     FROM likes_statistics ls
			     WHERE ls.type = 'album'
			       AND ls.item_id = a.album_id
			    ) AS like_count
			FROM albums a
			LEFT JOIN (
			    SELECT
			        tmp.album_id,
			        tmp.participant_name,
			        tmp.participant_id,
			        tmp.participant_type,
			        tmp.participation_count
			    FROM (
			        SELECT
			            s.album_id,
			            COALESCE(g.group_name, art.artist_name) AS participant_name,
			            COALESCE(g.group_id, art.artist_id) AS participant_id,
			            CASE
			                WHEN g.group_id IS NOT NULL THEN 'group'
			                ELSE 'artist'
			            END AS participant_type,
			            COUNT(*) as participation_count,
			            ROW_NUMBER() OVER (PARTITION BY s.album_id ORDER BY COUNT(*) DESC) as rn
			        FROM songs s
			        JOIN artist_roles ar ON s.song_id = ar.song_id
			        LEFT JOIN artists art ON ar.artist_id = art.artist_id
			        LEFT JOIN `groups` g ON ar.group_id = g.group_id
			        WHERE EXISTS (
			            SELECT 1
			            FROM albums a_inner
			            WHERE a_inner.album_id = s.album_id
			              AND a_inner.album_name LIKE CONCAT('%', :keyword, '%')
			        )
			        GROUP BY s.album_id, participant_name, participant_id, participant_type
			    ) AS tmp
			    WHERE tmp.rn = 1
			) mp ON a.album_id = mp.album_id
			WHERE a.album_name LIKE CONCAT('%', :keyword, '%')
			GROUP BY a.album_id, a.album_name, a.album_image, a.album_type, a.album_release_date,
			         mp.participant_name, mp.participant_id, mp.participant_type
			ORDER BY a.album_name, like_count DESC
			"""
			+ "LIMIT 18 OFFSET :offset"
						, nativeQuery = true)
	List<Object[]> searchAlbumsAlphabet(@Param("keyword") String keyword, @Param("offset") int offset);

	/*
	 * @Query(value = """
	 * select
	 * 'album' AS type,
	 * a.album_id AS id,
	 * a.album_name AS name,
	 * a.album_release_date AS release_date,
	 * (SELECT COALESCE(ls.like_count, 0)
	 * FROM likes_statistics ls
	 * WHERE ls.type = 'album'
	 * AND ls.item_id = a.album_id
	 * ) AS like_count
	 * FROM albums a
	 * WHERE a.album_name LIKE CONCAT('%', :keyword, '%')
	 * 
	 * UNION ALL
	 * 
	 * SELECT
	 * 'group' AS type,
	 * g.group_id AS id,
	 * g.group_name AS name,
	 * NULL AS release_date,
	 * (SELECT COALESCE(ls.like_count, 0)
	 * FROM likes_statistics ls
	 * WHERE ls.type = 'group'
	 * AND ls.item_id = g.group_id
	 * ) AS like_count
	 * FROM `groups` g
	 * WHERE g.group_name LIKE CONCAT('%', :keyword, '%')
	 * 
	 * UNION ALL
	 * 
	 * SELECT
	 * 'artist' AS type,
	 * art.artist_id AS id,
	 * art.artist_name AS name,
	 * NULL AS release_date,
	 * (SELECT COALESCE(ls.like_count, 0)
	 * FROM likes_statistics ls
	 * WHERE ls.type = 'artist'
	 * AND ls.item_id = art.artist_id
	 * ) AS like_count
	 * FROM artists art
	 * WHERE art.artist_name LIKE CONCAT('%', :keyword, '%')
	 * 
	 * UNION ALL
	 * 
	 * SELECT
	 * 'song' AS type,
	 * s.song_id AS id,
	 * s.title AS name,
	 * NULL AS release_date,
	 * (SELECT COALESCE(ls.like_count, 0)
	 * FROM likes_statistics ls
	 * WHERE ls.type = 'song'
	 * AND ls.item_id = s.song_id
	 * ) AS like_count
	 * FROM songs s
	 * WHERE s.title LIKE CONCAT(:keyword, '%')
	 * ORDER BY
	 * LENGTH(name), like_count DESC
	 * LIMIT 18 OFFSET :offset """
	 * , nativeQuery = true)
	 * List<Object[]> searchAlbumsAlphabet(@Param("keyword") String
	 * keyword, @Param("offset") int offset);
	 */

	/*
	 * @Query(value = "SELECT 'album' AS type, " +
	 * "a.album_id AS id, " +
	 * "a.album_name AS name, " +
	 * "a.album_release_date AS release_date, " +
	 * "COUNT(al.album_id) AS like_count " +
	 * "FROM albums a " +
	 * "JOIN albums_first_con afc ON a.album_id = afc.album_id " +
	 * "LEFT JOIN album_likes al ON a.album_id = al.album_id " +
	 * "WHERE afc.album_first_con_name LIKE CONCAT('%', :searchTerm, '%') " +
	 * "GROUP BY a.album_id, a.album_name, a.album_release_date " +
	 * 
	 * "UNION ALL " +
	 * 
	 * "SELECT 'song' AS type, " +
	 * "s.song_id AS id, " +
	 * "s.title AS name, " +
	 * "NULL AS release_date, " +
	 * "COUNT(sl.song_id) AS like_count " +
	 * "FROM songs s " +
	 * "JOIN songs_first_con sfc ON s.song_id = sfc.song_id " +
	 * "LEFT JOIN likes sl ON s.song_id = sl.song_id " +
	 * "WHERE sfc.song_first_con_name LIKE CONCAT('%', :searchTerm, '%') " +
	 * "GROUP BY s.song_id, s.title " +
	 * 
	 * "UNION ALL " +
	 * 
	 * "SELECT 'group' AS type, " +
	 * "g.group_id AS id, " +
	 * "g.group_name AS name, " +
	 * "NULL AS release_date, " +
	 * "COUNT(gl.group_id) AS like_count " +
	 * "FROM `groups` g " +
	 * "JOIN groups_first_con gfc ON g.group_id = gfc.group_id " +
	 * "LEFT JOIN group_likes gl ON g.group_id = gl.group_id " +
	 * "WHERE gfc.group_first_con_name LIKE CONCAT('%', :searchTerm, '%') " +
	 * "GROUP BY g.group_id, g.group_name " +
	 * 
	 * "UNION ALL " +
	 * 
	 * "SELECT 'artist' AS type, " +
	 * "art.artist_id AS id, " +
	 * "art.artist_name AS name, " +
	 * "NULL AS release_date, " +
	 * "COUNT(artl.artist_id) AS like_count " +
	 * "FROM artists art " +
	 * "JOIN artists_first_con afc ON art.artist_id = afc.artist_id " +
	 * "LEFT JOIN artist_likes artl ON art.artist_id = artl.artist_id " +
	 * "WHERE afc.artist_first_con_name LIKE CONCAT('%', :searchTerm, '%') " +
	 * "GROUP BY art.artist_id, art.artist_name " +
	 * 
	 * "ORDER BY LENGTH(name), like_count DESC",
	 * nativeQuery = true)
	 * List<Object[]> findChoSeongBySearchTerm(@Param("searchTerm") String
	 * searchTerm);
	 */

	@Query(value = """
			            SELECT DISTINCT
			    'album' AS type,
			    a.album_id AS id,
			    a.album_name AS name,
			    a.album_release_date AS release_date,
			    (SELECT COALESCE(ls.like_count, 0)
			     FROM likes_statistics ls
			     WHERE ls.type = 'album'
			       AND ls.item_id = a.album_id
			    ) AS like_count
			FROM albums a
			JOIN albums_first_con afc ON a.album_id = afc.album_id
			WHERE afc.album_first_con_name LIKE CONCAT('%', :searchTerm, '%')

			UNION ALL

			SELECT DISTINCT
			    'song' AS type,
			    s.song_id AS id,
			    s.title AS name,
			    NULL AS release_date,
			    (SELECT COALESCE(ls.like_count, 0)
			     FROM likes_statistics ls
			     WHERE ls.type = 'song'
			       AND ls.item_id = s.song_id
			    ) AS like_count
			FROM songs s
			JOIN songs_first_con sfc ON s.song_id = sfc.song_id
			WHERE sfc.song_first_con_name LIKE CONCAT( :searchTerm, '%')

			UNION ALL

			SELECT DISTINCT
			    'group' AS type,
			    g.group_id AS id,
			    g.group_name AS name,
			    NULL AS release_date,
			    (SELECT COALESCE(ls.like_count, 0)
			     FROM likes_statistics ls
			     WHERE ls.type = 'group'
			       AND ls.item_id = g.group_id
			    ) AS like_count
			FROM `groups` g
			JOIN groups_first_con gfc ON g.group_id = gfc.group_id
			WHERE gfc.group_first_con_name LIKE CONCAT('%', :searchTerm, '%')

			UNION ALL

			SELECT DISTINCT
			    'artist' AS type,
			    a.artist_id AS id,
			    a.artist_name AS name,
			    NULL AS release_date,
			    (SELECT COALESCE(ls.like_count, 0)
			     FROM likes_statistics ls
			     WHERE ls.type = 'artist'
			       AND ls.item_id = a.artist_id
			    ) AS like_count
			FROM artists a
			JOIN artists_first_con afc ON a.artist_id = afc.artist_id
			WHERE afc.artist_first_con_name LIKE CONCAT('%', :searchTerm, '%')

			ORDER BY
			    LENGTH(name),
			    like_count DESC
			    """
			+ "LIMIT 5"


			            , nativeQuery = true)
	List<Object[]> findChoSeongBySearchTerm(@Param("searchTerm") String searchTerm);
}
