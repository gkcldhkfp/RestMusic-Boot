CREATE FULLTEXT INDEX idx_title ON songs (title) WITH PARSER ngram;
CREATE FULLTEXT INDEX idx_album_name ON albums (album_name) WITH PARSER ngram;
CREATE FULLTEXT INDEX idx_artist_name ON artists (artist_name) WITH PARSER ngram;
CREATE FULLTEXT INDEX idx_group_name ON `groups` (group_name) WITH PARSER ngram;

SELECT 
    'album' AS type,
    a.album_id AS id,
    a.album_name AS name,
    a.album_release_date AS release_date,
    COUNT(al.album_id) AS like_count -- 좋아요 개수 COUNT로 계산
FROM albums a
LEFT JOIN album_likes al ON a.album_id = al.album_id -- 좋아요 테이블을 LEFT JOIN
WHERE MATCH(a.album_name) AGAINST('밤양갱' IN BOOLEAN MODE)
GROUP BY a.album_id, a.album_name, a.album_release_date

UNION ALL

SELECT
    'song' AS type,
    s.song_id AS id,
    s.title AS name,
    NULL AS release_date,
    COUNT(sl.song_id) AS like_count -- 곡 좋아요 개수 COUNT로 계산
FROM songs s
LEFT JOIN likes sl ON s.song_id = sl.song_id -- 좋아요 테이블을 LEFT JOIN
WHERE MATCH(s.title) AGAINST('밤양갱' IN BOOLEAN MODE)
GROUP BY s.song_id, s.title

UNION ALL

SELECT
    'group' AS type,
    g.group_id AS id,
    g.group_name AS name,
    NULL AS release_date,
    COUNT(gl.group_id) AS like_count -- 그룹 좋아요 개수 COUNT로 계산
FROM `groups` g
LEFT JOIN group_likes gl ON g.group_id = gl.group_id -- 좋아요 테이블을 LEFT JOIN
WHERE MATCH(g.group_name) AGAINST('밤양갱' IN BOOLEAN MODE)
GROUP BY g.group_id, g.group_name

UNION ALL

SELECT
    'artist' AS type,
    art.artist_id AS id,
    art.artist_name AS name,
    NULL AS release_date,
    COUNT(artl.artist_id) AS like_count -- 아티스트 좋아요 개수 COUNT로 계산
FROM artists art
LEFT JOIN artist_likes artl ON art.artist_id = artl.artist_id -- 좋아요 테이블을 LEFT JOIN
WHERE MATCH(art.artist_name) AGAINST('밤양갱' IN BOOLEAN MODE)
GROUP BY art.artist_id, art.artist_name

ORDER BY
    LENGTH(name),  -- name(또는 title) 컬럼의 데이터 길이로 정렬
    like_count DESC;  -- 같은 길이일 경우 좋아요 개수 높은 순으로 정렬

