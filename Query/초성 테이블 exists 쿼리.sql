SELECT 
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
WHERE afc.album_first_con_name LIKE CONCAT('%', 'ㅂㅇㄱ', '%')

UNION ALL

SELECT
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
WHERE sfc.song_first_con_name LIKE CONCAT('ㅂㅇㄱ', '%')

UNION ALL

SELECT
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
WHERE gfc.group_first_con_name LIKE CONCAT('%', 'ㅂㅇㄱ', '%')

UNION ALL

SELECT
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
WHERE afc.artist_first_con_name LIKE CONCAT('%', 'ㅂㅇㄱ', '%')

ORDER BY
    LENGTH(name),  -- name(또는 title) 컬럼의 데이터 길이로 정렬
    like_count DESC;  -- 같은 길이일 경우 좋아요 개수 높은 순으로 정렬

