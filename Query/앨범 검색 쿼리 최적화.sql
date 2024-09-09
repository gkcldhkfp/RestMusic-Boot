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
        
        GROUP BY s.album_id, participant_name, participant_id, participant_type 
        
    ) AS tmp 
    WHERE tmp.rn = 1 
) mp ON a.album_id = mp.album_id 
WHERE a.album_name LIKE CONCAT('%', '소곡집', '%')
GROUP BY a.album_id, a.album_name, a.album_image, a.album_type, a.album_release_date,  
         mp.participant_name, mp.participant_id, mp.participant_type 
ORDER BY a.album_name, like_count DESC 
LIMIT 18 OFFSET 0;

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
			              AND a_inner.album_name LIKE CONCAT('%', '밤양갱', '%')
			        ) 
			        GROUP BY s.album_id, participant_name, participant_id, participant_type
			    ) AS tmp
			    WHERE tmp.rn = 1
			) mp ON a.album_id = mp.album_id
			WHERE a.album_name LIKE CONCAT('%', '밤양갱', '%')
			GROUP BY a.album_id, a.album_name, a.album_image, a.album_type, a.album_release_date,
			         mp.participant_name, mp.participant_id, mp.participant_type
			ORDER BY LENGTH(a.album_name), like_count DESC
			LIMIT 18 OFFSET 0;
