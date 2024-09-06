SELECT 
    'album' AS type,
    a.album_id AS id,
    a.album_name AS name,
    a.album_release_date AS release_date,
    COALESCE(al.like_count, 0) AS like_count -- 앨범 좋아요 개수 가져오기
FROM albums a
JOIN albums_first_con afc ON a.album_id = afc.album_id
LEFT JOIN album_likes al ON a.album_id = al.album_id -- 좋아요 테이블을 LEFT JOIN
WHERE afc.album_first_con_name LIKE CONCAT('%', 'ㅇㅂ', '%')

UNION ALL

SELECT
    'song' AS type,
    s.song_id AS id,
    s.title AS name,
    NULL AS release_date,
    COALESCE(sl.like_count, 0) AS like_count -- 곡 좋아요 개수 가져오기
FROM songs s
JOIN songs_first_con sfc ON s.song_id = sfc.song_id
LEFT JOIN likes sl ON s.song_id = sl.song_id -- 좋아요 테이블을 LEFT JOIN
WHERE sfc.song_first_con_name LIKE CONCAT('%', 'ㅇㅂ', '%')

UNION ALL

SELECT
    'group' AS type,
    g.group_id AS id,
    g.group_name AS name,
    NULL AS release_date,
    COALESCE(gl.like_count, 0) AS like_count -- 그룹 좋아요 개수 가져오기
FROM `groups` g
JOIN groups_first_con gfc ON g.group_id = gfc.group_id
LEFT JOIN group_likes gl ON g.group_id = gl.group_id -- 좋아요 테이블을 LEFT JOIN
WHERE gfc.group_first_con_name LIKE CONCAT('%', 'ㅇㅂ', '%')

UNION ALL

SELECT
    'artist' AS type,
    art.artist_id AS id,
    art.artist_name AS name,
    NULL AS release_date,
    COALESCE(artl.like_count, 0) AS like_count -- 아티스트 좋아요 개수 가져오기
FROM artists art
JOIN artists_first_con afc ON art.artist_id = afc.artist_id
LEFT JOIN artist_likes artl ON art.artist_id = artl.artist_id -- 좋아요 테이블을 LEFT JOIN
WHERE afc.artist_first_con_name LIKE CONCAT('%', 'ㅇㅂ', '%')

ORDER BY
    LENGTH(name),  -- name(또는 title) 컬럼의 데이터 길이로 정렬
    like_count DESC;  -- 같은 길이일 경우 좋아요 개수 높은 순으로 정렬

