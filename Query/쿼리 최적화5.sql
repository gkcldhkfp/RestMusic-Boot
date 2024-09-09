SET profiling = 1;
SHOW profile;
set profiling = 0;


select
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
WHERE a.album_name LIKE CONCAT('%', '밤양갱', '%')

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
WHERE g.group_name LIKE CONCAT('%', '밤양갱', '%')

UNION ALL

SELECT
'artist' AS type,
art.artist_id AS id,
art.artist_name AS name,
NULL AS release_date,
    (SELECT COALESCE(ls.like_count, 0)
     FROM likes_statistics ls
     WHERE ls.type = 'artist'
       AND ls.item_id = art.artist_id
    ) AS like_count
FROM artists art
WHERE art.artist_name LIKE CONCAT('%', '밤양갱', '%')

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
WHERE s.title LIKE CONCAT('밤양갱', '%')
ORDER BY
LENGTH(name),  -- name(또는 title) 컬럼의 데이터 길이로 정렬
like_count DESC;  -- 같은 길이일 경우 좋아요 개수 높은 순으로 정렬