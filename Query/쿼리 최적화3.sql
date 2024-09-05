
select
    'album' AS type,
    a.album_id AS id,
    a.album_name AS name,
    a.album_release_date AS release_date,
    COALESCE(
        (SELECT ls.like_count
         FROM likes_statistics ls
         WHERE ls.type = 'album'
           AND ls.item_id = a.album_id
           AND ls.like_count > 0),
        0
    ) AS like_count
FROM albums a
WHERE a.album_name LIKE CONCAT('%', '밤양갱', '%')

UNION ALL

SELECT
'group' AS type,
g.group_id AS id,
g.group_name AS name,
NULL AS release_date,
COALESCE(
	(select ls.like_count
		from likes_statistics ls
		where ls.type = 'group'
			and ls.item_id = g.group_id
AND ls.like_count > 0), 0) AS like_count 
FROM `groups` g
WHERE g.group_name LIKE CONCAT('%', '밤양갱', '%')

UNION ALL

SELECT
'artist' AS type,
art.artist_id AS id,
art.artist_name AS name,
NULL AS release_date,
COALESCE(
	(select ls.like_count
		from likes_statistics ls
        where ls.type = 'artist'
         AND ls.item_id = art.artist_id
         AND ls.like_count > 0), 0) AS like_count 
FROM artists art
WHERE art.artist_name LIKE CONCAT('%', '밤양갱', '%')

UNION ALL

SELECT
'song' AS type,
s.song_id AS id,
s.title AS name,
NULL AS release_date,
COALESCE(ls.like_count, 0) AS like_count
FROM songs s
LEFT JOIN likes_statistics ls
ON ls.type = 'song'
AND ls.item_id = s.song_id
WHERE s.title LIKE CONCAT('밤양갱', '%')
ORDER BY
LENGTH(name),  -- name(또는 title) 컬럼의 데이터 길이로 정렬
like_count DESC;  -- 같은 길이일 경우 좋아요 개수 높은 순으로 정렬