SELECT 
			     art.artist_id AS id, 
			     art.artist_name AS name, 
			     art.artist_image, 
			     COUNT(artl.artist_id) AS like_count 
			 FROM artists art 
			 LEFT JOIN artist_likes artl ON art.artist_id = artl.artist_id 
			 WHERE art.artist_name LIKE CONCAT('%', '아이', '%') 
			 GROUP BY art.artist_id, art.artist_name 
			  
			 ORDER BY 
			     LENGTH(name),  
			     like_count DESC 
			 LIMIT 20 OFFSET 0;
             
             SELECT 
			     art.artist_id AS id, 
			     art.artist_name AS name, 
			     art.artist_image, 
			    (SELECT COALESCE(ls.like_count, 0)
			     FROM likes_statistics ls
			     WHERE ls.type = 'artist'
			       AND ls.item_id = art.artist_id
			    ) AS like_count
			 FROM artists art 
			 WHERE art.artist_name LIKE CONCAT('%', '아이', '%') 
			 GROUP BY art.artist_id, art.artist_name 
			  
			 ORDER BY 
			     LENGTH(name),  
			     like_count DESC 
			 LIMIT 20 OFFSET 0;
             
             
             
select  
			 art.artist_id, 
			 art.artist_name, 
			 art.artist_image, 			   
             (SELECT COALESCE(ls.like_count, 0)
			     FROM likes_statistics ls
			     WHERE ls.type = 'artist'
			       AND ls.item_id = art.artist_id
			    ) AS like_count 
			 from artists art 
			 left join artist_likes artl on artl.artist_id = art.artist_id 
			 where art.artist_id = :id  
			 group by art.artist_id, art.artist_name