-- 단일 인덱스
CREATE INDEX index_album_name
ON albums (album_name);
CREATE INDEX index_group_name
ON `groups` (group_name);
CREATE INDEX index_artist_name
ON artists (artist_name);
CREATE INDEX index_song_title
ON songs (title);

-- 복합 인덱스
CREATE INDEX idx_album_name_id ON albums(album_name, album_id);
create index index_likes_statistics_type_item on likes_statistics(type, item_id);

drop index idx_album_name_id on albums;
drop index index_album_name on albums;
drop index index_group_name on `groups`;
drop index index_artist_name on artists;


-- 통계 테이블 생성 후 사용할 쿼리
-- explain 
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
WHERE a.album_name LIKE CONCAT('%', '밤양갱', '%')

UNION ALL

SELECT
    'group' AS type,
    g.group_id AS id,
    g.group_name AS name,
    NULL AS release_date,
    COALESCE(ls.like_count, 0) AS like_count -- 그룹 좋아요 개수 가져오기
FROM `groups` g
IGNORE INDEX (idx_group_name)
LEFT JOIN likes_statistics ls
    ON ls.type = 'group'
    AND ls.item_id = g.group_id
WHERE g.group_name LIKE CONCAT('%', '밤양갱', '%')

UNION ALL

SELECT
    'artist' AS type,
    art.artist_id AS id,
    art.artist_name AS name,
    NULL AS release_date,
    COALESCE(ls.like_count, 0) AS like_count -- 아티스트 좋아요 개수 가져오기
FROM artists art
IGNORE INDEX (idx_artist_name)
LEFT JOIN likes_statistics ls
    ON ls.type = 'artist'
    AND ls.item_id = art.artist_id
WHERE art.artist_name LIKE CONCAT('%', '밤양갱', '%')

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
WHERE s.title LIKE CONCAT('밤양갱', '%')
ORDER BY
    LENGTH(name),  -- name(또는 title) 컬럼의 데이터 길이로 정렬
    like_count DESC;  -- 같은 길이일 경우 좋아요 개수 높은 순으로 정렬
    
SELECT
    'album' AS type,
    a.album_id AS id,
    a.album_name AS name,
    a.album_release_date AS release_date,
    COALESCE(ls.like_count, 0) AS like_count
FROM albums a
LEFT JOIN likes_statistics ls
ON ls.type = 'album'
AND ls.item_id = a.album_id
AND EXISTS (
    SELECT 1
    FROM likes_statistics ls2
    WHERE ls2.type = 'album'
    AND ls2.item_id = a.album_id
    AND ls2.like_count > 0
)
where a.album_name LIKE CONCAT('%', '밤양갱', '%');






SELECT
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
WHERE a.album_name LIKE CONCAT('%', '밤양갱', '%');







    
select
'album' AS type,
a.album_id AS id,
a.album_name AS name,
a.album_release_date AS release_date,
COALESCE(ls.like_count, 0) AS like_count -- 밤양갱 좋아요 개수 가져오기
FROM albums a
LEFT JOIN likes_statistics ls
ON ls.type = 'album'
AND ls.item_id = a.album_id
WHERE EXISTS (
SELECT 1
FROM likes_statistics ls2
WHERE ls2.type = 'album'
AND ls2.item_id = a.album_id
AND ls2.like_count > 0
)
AND a.album_name LIKE CONCAT('%', '밤양갱', '%')

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
WHERE EXISTS (
SELECT 1
FROM likes_statistics ls2
WHERE ls2.type = 'group'
AND ls2.item_id = g.group_id
AND ls2.like_count > 0
)
AND g.group_name LIKE CONCAT('%', '밤양갱', '%')

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
WHERE EXISTS (
SELECT 1
FROM likes_statistics ls2
WHERE ls2.type = 'artist'
AND ls2.item_id = art.artist_id
AND ls2.like_count > 0
)
AND art.artist_name LIKE CONCAT('%', '밤양갱', '%')

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
WHERE s.title LIKE CONCAT('밤양갱', '%')
ORDER BY
LENGTH(name),  -- name(또는 title) 컬럼의 데이터 길이로 정렬
like_count DESC;  -- 같은 길이일 경우 좋아요 개수 높은 순으로 정렬