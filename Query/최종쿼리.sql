-- 통계 테이블 생성 후 사용할 쿼리
SELECT 
    'album' AS type,
    a.album_id AS id,
    a.album_name AS name,
    a.album_release_date AS release_date,
    COALESCE(ls.like_count, 0) AS like_count -- 밤양갱 좋아요 개수 가져오기
FROM albums a
LEFT JOIN likes_statistics ls
    ON ls.type = 'album'
    AND ls.item_id = a.album_id
WHERE MATCH(a.album_name) AGAINST('밤양갱' IN BOOLEAN MODE)



UNION ALL

SELECT
    'song' AS type,
    s.song_id AS id,
    s.title AS name,
    NULL AS release_date,
    COALESCE(ls.like_count, 0) AS like_count -- 곡 좋아요 개수 가져오기
FROM songs s
LEFT JOIN likes_statistics ls
    ON ls.type = 'song'
    AND ls.item_id = s.song_id
WHERE MATCH(s.title) AGAINST('밤양갱' IN BOOLEAN MODE)

UNION ALL

SELECT
    'group' AS type,
    g.group_id AS id,
    g.group_name AS name,
    NULL AS release_date,
    COALESCE(ls.like_count, 0) AS like_count -- 그룹 좋아요 개수 가져오기
FROM `groups` g
LEFT JOIN likes_statistics ls
    ON ls.type = 'group'
    AND ls.item_id = g.group_id
WHERE MATCH(g.group_name) AGAINST('밤양갱' IN BOOLEAN MODE)

UNION ALL


SELECT
    'artist' AS type,
    art.artist_id AS id,
    art.artist_name AS name,
    NULL AS release_date,
    COALESCE(ls.like_count, 0) AS like_count -- 아티스트 좋아요 개수 가져오기
FROM artists art
LEFT JOIN likes_statistics ls
    ON ls.type = 'artist'
    AND ls.item_id = art.artist_id
WHERE MATCH(art.artist_name) AGAINST('밤양갱' IN BOOLEAN MODE)

ORDER BY
    LENGTH(name),  -- name(또는 title) 컬럼의 데이터 길이로 정렬
    like_count DESC;  -- 같은 길이일 경우 좋아요 개수 높은 순으로 정렬

