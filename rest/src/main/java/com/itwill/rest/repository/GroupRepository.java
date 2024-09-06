package com.itwill.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itwill.rest.domain.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {
	
	
	@Query(value = "SELECT "
            + "    g.group_id AS id, "
            + "    g.group_name AS name, "
            + "    g.group_image, "
            + "    COUNT(gl.group_id) AS like_count "
            + "FROM `groups` g "
            + "LEFT JOIN group_likes gl ON g.group_id = gl.group_id  "
            + "WHERE MATCH(g.group_name) AGAINST(:keyword IN BOOLEAN MODE) "
            + "GROUP BY g.group_id, g.group_name "
            + "ORDER BY LENGTH(g.group_name), like_count DESC "
            + "LIMIT 5", nativeQuery = true)
	public List<Object[]> searchAllGroup(@Param("keyword") String keyword);

	
	@Query(value = "SELECT "
            + "    g.group_id AS id, "
            + "    g.group_name AS name, "
            + "    g.group_image, "
            + "    COUNT(gl.group_id) AS like_count "
            + "FROM `groups` g "
            + "LEFT JOIN group_likes gl ON g.group_id = gl.group_id  "
            + "WHERE MATCH(g.group_name) AGAINST(:keyword IN BOOLEAN MODE) "
            + "GROUP BY g.group_id, g.group_name "
            + "ORDER BY LENGTH(g.group_name), like_count DESC "
            + "LIMIT 15 OFFSET :offset", nativeQuery = true)
	public List<Object[]> searchGroupAccuracy(@Param("keyword") String keyword, @Param("offset") int offset);
	
	@Query(value = "SELECT "
            + "    g.group_id AS id, "
            + "    g.group_name AS name, "
            + "    g.group_image, "
            + "    COUNT(gl.group_id) AS like_count "
            + "FROM `groups` g "
            + "LEFT JOIN group_likes gl ON g.group_id = gl.group_id  "
            + "WHERE MATCH(g.group_name) AGAINST(:keyword IN BOOLEAN MODE) "
            + "GROUP BY g.group_id, g.group_name "
            + "ORDER BY g.group_name, like_count DESC "
            + "LIMIT 15 OFFSET :offset", nativeQuery = true)
	public List<Object[]> searchGroupAlphabet(@Param("keyword") String keyword, @Param("offset") int offset);
	
}
