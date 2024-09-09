package com.itwill.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itwill.rest.domain.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {
	
	@Query(value = "SELECT "
			+ "    art.artist_id AS id, "
			+ "    art.artist_name AS name, "
			+ "    art.artist_image, "
			+ "    COUNT(artl.artist_id) AS like_count "
			+ "FROM artists art "
			+ "LEFT JOIN artist_likes artl ON art.artist_id = artl.artist_id "
			+ "WHERE art.artist_name LIKE CONCAT('%', :keyword, '%') "
			+ "GROUP BY art.artist_id, art.artist_name "
			+ " "
			+ "ORDER BY "
			+ "    LENGTH(name),  "
			+ "    like_count DESC "
			+ "LIMIT 5", 
			nativeQuery = true)
	public List<Object[]> searchAllArtist(@Param("keyword") String keyword);
	
	@Query(value = "SELECT "
			+ "    art.artist_id AS id, "
			+ "    art.artist_name AS name, "
			+ "    art.artist_image, "
			+ "    COUNT(artl.artist_id) AS like_count "
			+ "FROM artists art "
			+ "LEFT JOIN artist_likes artl ON art.artist_id = artl.artist_id "
			+ "WHERE art.artist_name LIKE CONCAT('%', :keyword, '%') "
			+ "GROUP BY art.artist_id, art.artist_name "
			+ " "
			+ "ORDER BY "
			+ "    LENGTH(name),  "
			+ "    like_count DESC "
			+ "LIMIT 20 OFFSET :offset", 
			nativeQuery = true)
	public List<Object[]> searchArtistAccuracy(@Param("keyword") String keyword, @Param("offset") int offset);
	
	@Query(value = "SELECT "
			+ "    art.artist_id AS id, "
			+ "    art.artist_name AS name, "
			+ "    art.artist_image, "
			+ "    COUNT(artl.artist_id) AS like_count "
			+ "FROM artists art "
			+ "LEFT JOIN artist_likes artl ON art.artist_id = artl.artist_id "
			+ "WHERE art.artist_name LIKE CONCAT('%', :keyword, '%') "
			+ "GROUP BY art.artist_id, art.artist_name "
			+ " "
			+ "ORDER BY "
			+ "    name,  "
			+ "    like_count DESC "
			+ "LIMIT 20 OFFSET :offset", 
			nativeQuery = true)
	public List<Object[]> searchArtistAlphabet(@Param("keyword") String keyword, @Param("offset") int offset);
	
	@Query(value = "select  "
			+ "art.artist_id, "
			+ "art.artist_name, "
			+ "art.artist_image, "
			+ "count(artl.artist_id) "
			+ "from artists art "
			+ "left join artist_likes artl on artl.artist_id = art.artist_id "
			+ "where art.artist_id = :id  "
			+ "group by art.artist_id, art.artist_name", nativeQuery = true)
	public List<Object[]> artistAndLikefindById(@Param("id") int id);
	
}