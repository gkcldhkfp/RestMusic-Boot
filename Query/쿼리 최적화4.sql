SELECT
    CASE WHEN a.album_id IS NOT NULL THEN 'album'
         WHEN g.group_id IS NOT NULL THEN 'group'
         WHEN art.artist_id IS NOT NULL THEN 'artist'
         ELSE 'song'
    END AS type,
	COALESCE(a.album_id, g.group_id, art.artist_id, s.song_id) AS id,
    COALESCE(a.album_name, g.group_name, art.artist_name, s.title) AS name,
    a.album_release_date AS release_date,
    COALESCE(ls.like_count, 0) AS like_count
FROM albums a
LEFT JOIN likes_statistics ls ON ls.type = 'album' AND ls.item_id = a.album_id
LEFT JOIN `groups` g ON ls.type = 'group' AND ls.item_id = g.group_id
LEFT JOIN artists art ON ls.type = 'artist' AND ls.item_id = art.artist_id
LEFT JOIN songs s ON ls.type = 'song' AND ls.item_id = s.song_id
WHERE (a.album_name LIKE CONCAT('%', '밤양갱', '%') OR g.group_name LIKE CONCAT('%', '밤양갱', '%') OR art.artist_name LIKE CONCAT('%', '밤양갱', '%') OR s.title LIKE CONCAT('밤양갱', '%'))
ORDER BY like_count DESC;