SELECT 
			     g.group_id AS id, 
			     g.group_name AS name, 
			     g.group_image, 
			     COUNT(artl.group_id) AS like_count 
			 FROM `groups` g 
			 LEFT JOIN group_likes artl ON g.group_id = artl.group_id 
			 WHERE g.group_name LIKE CONCAT('%', '빨간', '%') 
			 GROUP BY g.group_id, g.group_name 
			  
			 ORDER BY 
			     LENGTH(name),  
			     like_count DESC 
			 LIMIT 20 OFFSET 0;
             
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
			 WHERE g.group_name LIKE CONCAT('%', '빨간', '%') 
			 GROUP BY g.group_id, g.group_name 
			  
			 ORDER BY 
			     LENGTH(name),  
			     like_count DESC 
			 LIMIT 20 OFFSET 0;
             
             
             
select  
			 g.group_id, 
			 g.group_name, 
			 g.group_image, 			   
             (SELECT COALESCE(ls.like_count, 0)
			     FROM likes_statistics ls
			     WHERE ls.type = 'group'
			       AND ls.item_id = g.group_id
			    ) AS like_count 
			 from `groups` g 
			 where g.group_id = 1
			 group by g.group_id, g.group_name;