package com.itwill.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itwill.rest.domain.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {
	
	
	@Query(value = """
                     SELECT 
			     g.group_id AS id, 
			     g.group_name AS name, 
			     g.group_image, 
			    (SELECT COALESCE(ls.like_count, 0)
			     FROM likes_statistics ls
			     WHERE ls.type = 'group'
			       AND ls.item_id = g.group_id
			    ) AS like_count
			 FROM `groups` g 
			 WHERE g.group_name LIKE CONCAT('%', :keyword, '%') 
			 GROUP BY g.group_id, g.group_name 
			  
			 ORDER BY 
			     LENGTH(name),  
			     like_count DESC 
           """
           + "LIMIT 5 "
      , nativeQuery = true)
	public List<Object[]> searchAllGroup(@Param("keyword") String keyword);

	
	@Query(value = """
                     SELECT 
			     g.group_id AS id, 
			     g.group_name AS name, 
			     g.group_image, 
			    (SELECT COALESCE(ls.like_count, 0)
			     FROM likes_statistics ls
			     WHERE ls.type = 'group'
			       AND ls.item_id = g.group_id
			    ) AS like_count
			 FROM `groups` g 
			 WHERE g.group_name LIKE CONCAT('%', :keyword, '%') 
			 GROUP BY g.group_id, g.group_name 
			  
			 ORDER BY 
			     LENGTH(name),  
			     like_count DESC 
           """
           + "LIMIT 15 OFFSET :offset;"
      , nativeQuery = true)
	public List<Object[]> searchGroupAccuracy(@Param("keyword") String keyword, @Param("offset") int offset);
	
	@Query(value = """
                     SELECT 
			     g.group_id AS id, 
			     g.group_name AS name, 
			     g.group_image, 
			    (SELECT COALESCE(ls.like_count, 0)
			     FROM likes_statistics ls
			     WHERE ls.type = 'group'
			       AND ls.item_id = g.group_id
			    ) AS like_count
			 FROM `groups` g 
			 WHERE g.group_name LIKE CONCAT('%', :keyword, '%') 
			 GROUP BY g.group_id, g.group_name 
			  
			 ORDER BY 
			     name,  
			     like_count DESC 
           """
           + "LIMIT 15 OFFSET :offset;"
      , nativeQuery = true)
	public List<Object[]> searchGroupAlphabet(@Param("keyword") String keyword, @Param("offset") int offset);
	
}
