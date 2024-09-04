-- 통계 테이블 생성 후 사용할 쿼리
SELECT 
    'album' AS type,
    a.album_id AS id,
    a.album_name AS name,
    a.album_release_date AS release_date,
    COALESCE(al.like_count, 0) AS like_count -- 앨범 좋아요 개수 가져오기
FROM albums a
LEFT JOIN album_likes al ON a.album_id = al.album_id -- 좋아요 테이블을 LEFT JOIN
WHERE a.album_name LIKE CONCAT('%', '앨범', '%')

UNION ALL

SELECT
    'song' AS type,
    s.song_id AS id,
    s.title AS name,
    NULL AS release_date,
    COALESCE(sl.like_count, 0) AS like_count -- 곡 좋아요 개수 가져오기
FROM songs s
LEFT JOIN likes sl ON s.song_id = sl.song_id -- 좋아요 테이블을 LEFT JOIN
WHERE s.title LIKE CONCAT('%', '앨범', '%')

UNION ALL

SELECT
    'group' AS type,
    g.group_id AS id,
    g.group_name AS name,
    NULL AS release_date,
    COALESCE(gl.like_count, 0) AS like_count -- 그룹 좋아요 개수 가져오기
FROM `groups` g
LEFT JOIN group_likes gl ON g.group_id = gl.group_id -- 좋아요 테이블을 LEFT JOIN
WHERE g.group_name LIKE CONCAT('%', '앨범', '%')

UNION ALL

SELECT
    'artist' AS type,
    art.artist_id AS id,
    art.artist_name AS name,
    NULL AS release_date,
    COALESCE(artl.like_count, 0) AS like_count -- 아티스트 좋아요 개수 가져오기
FROM artists art
WHERE art.artist_name LIKE CONCAT('%', '앨범', '%')

ORDER BY
    LENGTH(name),  -- name(또는 title) 컬럼의 데이터 길이로 정렬
    like_count DESC;  -- 같은 길이일 경우 좋아요 개수 높은 순으로 정렬

