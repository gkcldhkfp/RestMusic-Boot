-- 위 설정 창 Edit - PreFerences ->  SQL Editor -> 아래 쪽 Safe Updates(~~~~~) 체크 해제 후 MySQL 워크벤치 껐다 키고 전체 실행 시키면 됨.


-- 볼빨간 사춘기
-- 1. 안지영으로 업데이트
UPDATE artists
SET artist_name = '안지영', 
    artist_image = '안지영사진.jpg', 
    artist_description = '안지영설명.txt'
WHERE artist_name = '볼빨간사춘기';

-- 2. 우지윤 아티스트 추가(더 많으면 복붙해서 멤버 수대로 추가)
INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('우지윤', '우지윤사진.jpg', '우지윤설명.txt');


-- 3. 볼빨간 사춘기 그룹 추가
INSERT INTO `groups` (group_name)
VALUES ('볼빨간사춘기');

-- 3.5 볼빨간 사춘기 그룹 설명과 이미지 추가: 아직 이미지 컬럼이 없어서 실행이 안되므로 나중에 써야됨.
UPDATE `groups`
SET group_name = '볼빨간사춘기', 
    group_image = '볼빨간사춘기.jpg', 
    group_description = '볼빨간사춘기.txt'
WHERE group_name = '볼빨간사춘기';

-- 4. 그룹 멤버 추가(더 많으면 복붙해서 멤버 수대로 추가)
INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = '볼빨간사춘기'),
    (SELECT artist_id FROM artists WHERE artist_name = '안지영')
);

INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = '볼빨간사춘기'),
    (SELECT artist_id FROM artists WHERE artist_name = '우지윤')
);

-- 여기서 '안지영'은 그룹 이름에서 대체한 멤버임: (1) 과정에서 변경한 멤버를 쓰면 됨
-- 1. 그룹 ID를 찾기 
SET @group_id := (SELECT group_id FROM `groups` WHERE group_name = '볼빨간사춘기');

-- 2. 안지영이 속한 그룹의 음원에 대해 role_id = 10을 추가
INSERT INTO artist_roles (artist_id, group_id, song_id, role_id)
SELECT 
    gm.artist_id,
    @group_id AS group_id,
    ar.song_id,
    10 AS role_id
FROM artist_roles ar
JOIN group_members gm ON gm.group_id = ar.group_id
WHERE ar.artist_id = (SELECT artist_id FROM artists WHERE artist_name = '안지영')
  AND gm.artist_id <> (SELECT artist_id FROM artists WHERE artist_name = '안지영')
  AND gm.group_id = @group_id
  AND ar.song_id NOT IN (
    SELECT song_id
    FROM artist_roles
    WHERE artist_id = gm.artist_id
      AND group_id = @group_id
      AND role_id = 10
  );

-- 5. artist_roles 업데이트
UPDATE artist_roles
SET group_id = (SELECT group_id FROM `groups` WHERE group_name = '볼빨간사춘기')
WHERE role_id = 10
AND artist_id IN (
    SELECT artist_id FROM artists WHERE artist_name IN ('안지영', '우지윤')
);

---------------------------------- 여기까진 그룹 별로 한 번만 작성하면 됨. -----------------------



-- 음원의 작사, 작곡, 편곡 설정: 그룹이 참여한 앨범 개수만 큼 작성되어야 하는 블록.
---------------------- 여기서 부터 음원 별로 수동 작성 해야함. -----------------------------
-- 그룹이 참여한 앨범 개수만큼 작업 해야 함.----------------------------------------------- 

-- RED PLANET에 대한 역할 설정-----------------------------------------------------

-- 6. 새로 추가된 멤버가 작곡, 작사, 편곡에 참여한 경우 데이터 삽입()
-- 작곡 20, 작사 30, 편곡 40
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (
    (SELECT artist_id FROM artists WHERE artist_name = '우지윤'),
    (SELECT song_id FROM songs WHERE title = '우주를 줄게'),
    30
);

-- 작곡 20, 작사 30, 편곡 40
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (
    (SELECT artist_id FROM artists WHERE artist_name = '우지윤'),
    (SELECT song_id FROM songs WHERE title = '초콜릿'),
    30
);

-- 작곡 20, 작사 30, 편곡 40
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (
    (SELECT artist_id FROM artists WHERE artist_name = '우지윤'),
    (SELECT song_id FROM songs WHERE title = '반지'),
    20
);

-- 작곡 20, 작사 30, 편곡 40
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (
    (SELECT artist_id FROM artists WHERE artist_name = '우지윤'),
    (SELECT song_id FROM songs WHERE title = '반지'),
    30
);

-- 잘못 삽입된 데이터 삭제
-- 특정 artist_id를 변수에 저장
SET @artist_id := (SELECT artist_id FROM artists WHERE artist_name = '낯선아이');

-- artist_roles 테이블에서 해당 artist_id 삭제
DELETE FROM artist_roles
WHERE artist_id = @artist_id;

-- artists 테이블에서 해당 artist_id 삭제
DELETE FROM artists
WHERE artist_id = @artist_id;

-- 사춘기집Ⅰ 꽃기운에 대한 역할 설정-----------------------------------------------------
-- 작곡 20, 작사 30, 편곡 40
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (
    (SELECT artist_id FROM artists WHERE artist_name = '우지윤'),
    (SELECT song_id FROM songs WHERE title = '나들이 갈까'),
    30
);

-- 작곡 20, 작사 30, 편곡 40
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (
    (SELECT artist_id FROM artists WHERE artist_name = '우지윤'),
    (SELECT song_id FROM songs WHERE title = '나들이 갈까'),
    20
);


-- 악뮤
-- '이찬혁' 아티스트 중복 제거를 위해 임시 테이블 사용 (아티스트 테이블에 이미 '이찬혁'이 존재)
DELETE a1 FROM artists a1
JOIN artists a2 
ON a1.artist_name = a2.artist_name 
AND a1.artist_id > a2.artist_id
WHERE a1.artist_name = '이찬혁';

-- 1. 이찬혁으로 업데이트
UPDATE artists
SET artist_name = '이찬혁', 
    artist_image = '이찬혁사진.jpg', 
    artist_description = '이찬혁설명.txt'
WHERE artist_name = 'AKMU (악뮤)';

-- 2. 이수현 아티스트 추가
INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('이수현', '이수현사진.jpg', '이수현설명.txt');

-- 3. 악뮤 그룹 추가
INSERT INTO `groups` (group_name)
VALUES ('AKMU (악뮤)');

-- 3.5 악뮤 그룹 설명과 이미지 추가
UPDATE `groups`
SET group_name = 'AKMU (악뮤)', 
    group_image = 'AKMU (악뮤).jpg', 
    group_description = 'AKMU (악뮤).txt'
WHERE group_name = 'AKMU (악뮤)';

-- 4. 그룹 멤버 추가
INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'AKMU (악뮤)' LIMIT 1),
    (SELECT artist_id FROM artists WHERE artist_name = '이찬혁' LIMIT 1)
);

INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'AKMU (악뮤)' LIMIT 1),
    (SELECT artist_id FROM artists WHERE artist_name = '이수현' LIMIT 1)
);

-- 5. 그룹 ID 설정 및 아티스트 역할 업데이트
SET @group_id := (SELECT group_id FROM `groups` WHERE group_name = 'AKMU (악뮤)' LIMIT 1);

INSERT INTO artist_roles (artist_id, group_id, song_id, role_id)
SELECT 
    gm.artist_id,
    @group_id AS group_id,
    ar.song_id,
    10 AS role_id
FROM artist_roles ar
JOIN group_members gm ON gm.group_id = ar.group_id
WHERE ar.artist_id = (SELECT artist_id FROM artists WHERE artist_name = '이찬혁' LIMIT 1)
  AND gm.artist_id <> (SELECT artist_id FROM artists WHERE artist_name = '이찬혁' LIMIT 1)
  AND gm.group_id = @group_id
  AND ar.song_id NOT IN (
    SELECT song_id
    FROM artist_roles
    WHERE artist_id = gm.artist_id
      AND group_id = @group_id
      AND role_id = 10
  );

UPDATE artist_roles
SET group_id = (SELECT group_id FROM `groups` WHERE group_name = 'AKMU (악뮤)' LIMIT 1)
WHERE role_id = 10
AND artist_id IN (
    SELECT artist_id FROM artists WHERE artist_name IN ('이찬혁', '이수현')
);


-- 음원의 작사, 작곡, 편곡 설정
-- SUMMER EPISODE에 대한 역할 설정-----------------------------------------------------
-- 작사 20 작곡 30 편곡 40
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (
    (SELECT artist_id FROM artists WHERE artist_name = '이찬혁' LIMIT 1),
    (SELECT song_id FROM songs WHERE title = 'DINOSAUR' LIMIT 1),
    30
);

-- 작곡 20, 작사 30, 편곡 40
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (
    (SELECT artist_id FROM artists WHERE artist_name = '이찬혁' LIMIT 1),
    (SELECT song_id FROM songs WHERE title = 'DINOSAUR' LIMIT 1),
    20
);

-- 작곡 20, 작사 30, 편곡 40
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (
    (SELECT artist_id FROM artists WHERE artist_name = '이찬혁' LIMIT 1),
    (SELECT song_id FROM songs WHERE title = 'MY DARLING' LIMIT 1),
    30
);

-- 작곡 20, 작사 30, 편곡 40
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (
    (SELECT artist_id FROM artists WHERE artist_name = '이찬혁' LIMIT 1),
    (SELECT song_id FROM songs WHERE title = 'MY DARLING' LIMIT 1),
    20
);

-- Love Lee 앨범
-- 작곡 20, 작사 30, 편곡 40
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (
    (SELECT artist_id FROM artists WHERE artist_name = '이찬혁' LIMIT 1),
    (SELECT song_id FROM songs WHERE title = 'Love Lee' LIMIT 1),
    30
);

-- 작곡 20, 작사 30, 편곡 40
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (
    (SELECT artist_id FROM artists WHERE artist_name = '이찬혁' LIMIT 1),
    (SELECT song_id FROM songs WHERE title = 'Love Lee' LIMIT 1),
    20
);

-- 작곡 20, 작사 30, 편곡 40
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (
    (SELECT artist_id FROM artists WHERE artist_name = '이찬혁' LIMIT 1),
    (SELECT song_id FROM songs WHERE title = '후라이의 꿈' LIMIT 1),
    30
);

-- 작곡 20, 작사 30, 편곡 40
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (
    (SELECT artist_id FROM artists WHERE artist_name = '이찬혁' LIMIT 1),
    (SELECT song_id FROM songs WHERE title = '후라이의 꿈' LIMIT 1),
    20
);









UPDATE artist_roles
SET artist_id = 51
WHERE artist_id = 37
AND song_id=13;

-- 4. 그룹 멤버 추가
INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'AKMU (악뮤)' LIMIT 1),
    (51)
);

DELETE FROM artist_roles WHERE artist_id = 37;
DELETE FROM group_members WHERE artist_id = 37;
DELETE FROM artists WHERE artist_id = 37;







USE test;

-- 뉴진스
-- 1. 해린으로 업데이트 (NewJeans를 해린으로 대체)
UPDATE artists
SET artist_name = '해린', 
    artist_image = '해린사진.jpg', 
    artist_description = '해린설명.txt'
WHERE artist_name = 'NewJeans';

-- 2. 뉴진스 다른 멤버들 추가
INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('민지', '민지사진.jpg', '민지설명.txt'),
       ('하니', '하니사진.jpg', '하니설명.txt'),
       ('다니엘', '다니엘사진.jpg', '다니엘설명.txt'),
       ('혜인', '혜인사진.jpg', '혜인설명.txt');

-- 3. 뉴진스 그룹 추가
INSERT INTO `groups` (group_name)
VALUES ('NewJeans');

-- 3.5 뉴진스 그룹 설명과 이미지 추가 (그룹 이미지 추가는 나중에)
UPDATE `groups`
SET group_name = 'NewJeans', 
    group_image = 'NewJeans.jpg', 
    group_description = 'NewJeans.txt'
WHERE group_name = 'NewJeans';

-- 4. 그룹 멤버 추가(멤버 수대로)
INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'NewJeans'),
    (SELECT artist_id FROM artists WHERE artist_name = '해린')
);

INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'NewJeans'),
    (SELECT artist_id FROM artists WHERE artist_name = '민지')
);

INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'NewJeans'),
    (SELECT artist_id FROM artists WHERE artist_name = '하니')
);

INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'NewJeans'),
    (SELECT artist_id FROM artists WHERE artist_name = '다니엘')
);

INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'NewJeans'),
    (SELECT artist_id FROM artists WHERE artist_name = '혜인')
);

-- 5. 그룹 ID를 설정 (NewJeans 그룹 ID)
SET @group_id := (SELECT group_id FROM `groups` WHERE group_name = 'NewJeans');

-- 6. 해린이 속한 그룹의 음원에 대해 role_id = 10을 추가 (NewJeans의 리더를 해린으로 대체)
INSERT INTO artist_roles (artist_id, group_id, song_id, role_id)
SELECT 
    gm.artist_id,
    @group_id AS group_id,
    ar.song_id,
    10 AS role_id
FROM artist_roles ar
JOIN group_members gm ON gm.group_id = ar.group_id
WHERE ar.artist_id = (SELECT artist_id FROM artists WHERE artist_name = '해린')
  AND gm.artist_id <> (SELECT artist_id FROM artists WHERE artist_name = '해린')
  AND gm.group_id = @group_id
  AND ar.song_id NOT IN (
    SELECT song_id
    FROM artist_roles
    WHERE artist_id = gm.artist_id
      AND group_id = @group_id
      AND role_id = 10
  );

-- 7. artist_roles 업데이트
UPDATE artist_roles
SET group_id = (SELECT group_id FROM `groups` WHERE group_name = 'NewJeans')
WHERE role_id = 10
AND artist_id IN (
    SELECT artist_id FROM artists WHERE artist_name IN ('해린', '민지', '하니', '다니엘', '혜인')
);

---------------------------------- 여기까진 그룹 별로 한 번만 작성하면 됨. -----------------------

-- 음원의 작사, 작곡, 편곡 설정: 그룹이 참여한 앨범 개수만큼 작성되어야 하는 블록
---------------------- 여기서부터 음원별로 수동 작성해야 함. -----------------------------
-- 그룹이 참여한 앨범 개수만큼 작업해야 함.----------------------------------------------- 

-- The 2nd EP <Get Up> 앨범에 대한 역할 설정-----------------------------------------------------
-- 작곡 20, 작사 30, 편곡 40
-- 곡별로 멤버의 작사, 작곡, 편곡 참여 정보 삽입
-- NewJeans
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (
    (SELECT artist_id FROM artists WHERE artist_name = '해린'),
    (SELECT song_id FROM songs WHERE title = 'New Jeans'),
    30 -- 작사
);

-- Super Shy
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (
    (SELECT artist_id FROM artists WHERE artist_name = '다니엘'),
    (SELECT song_id FROM songs WHERE title = 'Super Shy'),
    30 -- 작사
);

-- Cool With You
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (
    (SELECT artist_id FROM artists WHERE artist_name = '다니엘'),
    (SELECT song_id FROM songs WHERE title = 'Cool With You'),
    30 -- 작사
);

-- ASAP
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (
    (SELECT artist_id FROM artists WHERE artist_name = '다니엘'),
    (SELECT song_id FROM songs WHERE title = 'ASAP'),
    30 -- 작사
);

-- KISS OF LIFE
-- 1. 벨로 업데이트 (KISS OF LIFE를 벨로 대체)
UPDATE artists
SET artist_name = '벨', 
    artist_image = '벨사진.jpg', 
    artist_description = '벨설명.txt'
WHERE artist_name = 'kissOfLife';

-- 2. KISS OF LIFE 다른 멤버들 추가
INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('나띠', '나띠사진.jpg', '나띠설명.txt'),
       ('쥴리', '쥴리사진.jpg', '줄리설명.txt'),
       ('하늘', '하늘사진.jpg', '하늘설명.txt');

-- 3. KISS OF LIFE 그룹 추가
INSERT INTO `groups` (group_name)
VALUES ('kissOfLife');

-- 3.5 'kissOfLife' 그룹 설명과 이미지 추가 (그룹 이미지 추가는 나중에)
UPDATE `groups`
SET group_name = 'kissOfLife', 
    group_image = 'kissOfLife.jpg', 
    group_description = 'kissOfLife.txt'
WHERE group_name = 'kissOfLife';

-- 4. 그룹 멤버 추가(멤버 수대로)
INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'kissOfLife'),
    (SELECT artist_id FROM artists WHERE artist_name = '벨')
);

INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'kissOfLife'),
    (SELECT artist_id FROM artists WHERE artist_name = '나띠')
);

INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'kissOfLife'),
    (SELECT artist_id FROM artists WHERE artist_name = '쥴리')
);

INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'kissOfLife'),
    (SELECT artist_id FROM artists WHERE artist_name = '하늘')
);

-- 5. 그룹 ID를 설정 (KISS OF LIFE 그룹 ID)
SET @group_id := (SELECT group_id FROM `groups` WHERE group_name = 'kissOfLife');

-- 6. 벨라가 속한 그룹의 음원에 대해 role_id = 10을 추가 (KISS OF LIFE의 리더를 벨로 대체)
INSERT INTO artist_roles (artist_id, group_id, song_id, role_id)
SELECT 
    gm.artist_id,
    @group_id AS group_id,
    ar.song_id,
    10 AS role_id
FROM artist_roles ar
JOIN group_members gm ON gm.group_id = ar.group_id
WHERE ar.artist_id = (SELECT artist_id FROM artists WHERE artist_name = '벨')
  AND gm.artist_id <> (SELECT artist_id FROM artists WHERE artist_name = '벨')
  AND gm.group_id = @group_id
  AND ar.song_id NOT IN (
    SELECT song_id
    FROM artist_roles
    WHERE artist_id = gm.artist_id
      AND group_id = @group_id
      AND role_id = 10
  );

-- 7. artist_roles 업데이트
UPDATE artist_roles
SET group_id = (SELECT group_id FROM `groups` WHERE group_name = 'kissOfLife')
WHERE role_id = 10
AND artist_id IN (
    SELECT artist_id FROM artists WHERE artist_name IN ('벨', '나띠', '쥴', '하늘')
);

---------------------------------- 여기까진 그룹 별로 한 번만 작성하면 됨. -----------------------

-- 음원의 작사, 작곡, 편곡 설정: 그룹이 참여한 앨범 개수만큼 작성되어야 하는 블록
---------------------- 여기서부터 음원별로 수동 작성해야 함. -----------------------------
-- 그룹이 참여한 앨범 개수만큼 작업해야 함.----------------------------------------------- 

-- KISS OF LIFE 멤버 음원 참여 내역 없음


-- 'babymonster'
-- 1. 아사로 업데이트 (babymonster를 아사로 대체)
UPDATE artists
SET artist_name = '아사', 
    artist_image = '아사사진.jpg', 
    artist_description = '아사설명.txt'
WHERE artist_name = 'babymonster';

-- 2. babymonster 다른 멤버들 추가
INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('루카', '루카사진.jpg', '루카설명.txt'),
       ('파리타', '파리타사진.jpg', '파리타설명.txt'),
       ('아현', '아현사진.jpg', '아현설명.txt'),
       ('라미', '라미사진.jpg', '라미설명.txt'),
       ('로라', '로라사진.jpg', '로라설명.txt'),
       ('치키타', '치키타사진.jpg', '치키타설명.txt');

-- 3. babymonster 그룹 추가
INSERT INTO `groups` (group_name)
VALUES ('babymonster');

-- 3.5 babymonster 그룹 설명과 이미지 추가 (그룹 이미지 추가는 나중에)
UPDATE `groups`
SET group_name = 'babymonster', 
     group_image = 'babymonster.jpg', 
    group_description = 'babymonster.txt'
WHERE group_name = 'babymonster';

-- 4. 그룹 멤버 추가 (멤버 수대로)
INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'babymonster'),
    (SELECT artist_id FROM artists WHERE artist_name = '아사')
);

INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'babymonster'),
    (SELECT artist_id FROM artists WHERE artist_name = '루카')
);

INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'babymonster'),
    (SELECT artist_id FROM artists WHERE artist_name = '파리타')
);

INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'babymonster'),
    (SELECT artist_id FROM artists WHERE artist_name = '아현')
);

INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'babymonster'),
    (SELECT artist_id FROM artists WHERE artist_name = '라미')
);

INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'babymonster'),
    (SELECT artist_id FROM artists WHERE artist_name = '로라')
);

INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'babymonster'),
    (SELECT artist_id FROM artists WHERE artist_name = '치키타')
);

-- 5. 그룹 ID를 설정 (babymonster 그룹 ID)
SET @group_id := (SELECT group_id FROM `groups` WHERE group_name = 'babymonster');

-- 6. 아사가 속한 그룹의 음원에 대해 role_id = 10을 추가 (babymonster의 리더를 아사로 대체)
INSERT INTO artist_roles (artist_id, group_id, song_id, role_id)
SELECT 
    gm.artist_id,
    @group_id AS group_id,
    ar.song_id,
    10 AS role_id
FROM artist_roles ar
JOIN group_members gm ON gm.group_id = ar.group_id
WHERE ar.artist_id = (SELECT artist_id FROM artists WHERE artist_name = '아사')
  AND gm.artist_id <> (SELECT artist_id FROM artists WHERE artist_name = '아사')
  AND gm.group_id = @group_id
  AND ar.song_id NOT IN (
    SELECT song_id
    FROM artist_roles
    WHERE artist_id = gm.artist_id
      AND group_id = @group_id
      AND role_id = 10
  );

-- 7. artist_roles 업데이트
UPDATE artist_roles
SET group_id = (SELECT group_id FROM `groups` WHERE group_name = 'babymonster')
WHERE role_id = 10
AND artist_id IN (
    SELECT artist_id FROM artists WHERE artist_name IN ('아사', '루카', '파리타', '아현', '라미', '로라', '치키타')
);

-- babymonster 멤버들의 곡별 참여 정보 추가
-- 참여 내역 없음

-- illit
-- 1. 윤아로 업데이트 (illit을 윤아로 대체)
UPDATE artists
SET artist_name = '윤아', 
    artist_image = '윤아사진.jpg', 
    artist_description = '윤아설명.txt'
WHERE artist_name = 'illit';

-- 2. illit 다른 멤버들 추가
INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('민주', '민주사진.jpg', '민주설명.txt'),
       ('모카', '모카사진.jpg', '모카설명.txt'),
       ('원희', '원희사진.jpg', '원희설명.txt'),
       ('이로하', '이로하사진.jpg', '이로하설명.txt');

-- 3. illit 그룹 추가
INSERT INTO `groups` (group_name)
VALUES ('illit');

-- 3.5 illit 그룹 설명과 이미지 추가 (그룹 이미지 추가는 나중에)
UPDATE `groups`
SET group_name = 'illit', 
     group_image = 'illit.jpg', 
    group_description = 'illit.txt'
WHERE group_name = 'illit';

-- 4. 그룹 멤버 추가 (멤버 수대로)
INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'illit'),
    (SELECT artist_id FROM artists WHERE artist_name = '윤아')
);

INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'illit'),
    (SELECT artist_id FROM artists WHERE artist_name = '민주')
);

INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'illit'),
    (SELECT artist_id FROM artists WHERE artist_name = '모카')
);

INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'illit'),
    (SELECT artist_id FROM artists WHERE artist_name = '원희')
);

INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'illit'),
    (SELECT artist_id FROM artists WHERE artist_name = '이로하')
);

-- 5. 그룹 ID를 설정 (illit 그룹 ID)
SET @group_id := (SELECT group_id FROM `groups` WHERE group_name = 'illit');

-- 6. 윤아가 속한 그룹의 음원에 대해 role_id = 10을 추가 (illit의 리더를 윤아로 대체)
INSERT INTO artist_roles (artist_id, group_id, song_id, role_id)
SELECT 
    gm.artist_id,
    @group_id AS group_id,
    ar.song_id,
    10 AS role_id
FROM artist_roles ar
JOIN group_members gm ON gm.group_id = ar.group_id
WHERE ar.artist_id = (SELECT artist_id FROM artists WHERE artist_name = '윤아')
  AND gm.artist_id <> (SELECT artist_id FROM artists WHERE artist_name = '윤아')
  AND gm.group_id = @group_id
  AND ar.song_id NOT IN (
    SELECT song_id
    FROM artist_roles
    WHERE artist_id = gm.artist_id
      AND group_id = @group_id
      AND role_id = 10
  );

-- 7. artist_roles 업데이트
UPDATE artist_roles
SET group_id = (SELECT group_id FROM `groups` WHERE group_name = 'illit')
WHERE role_id = 10
AND artist_id IN (
    SELECT artist_id FROM artists WHERE artist_name IN ('윤아', '민주', '모카', '원희', '이로하')
);

---------------------------------- 여기까진 그룹 별로 한 번만 작성하면 됨. -----------------------

-- 음원의 작사, 작곡, 편곡 설정: 그룹이 참여한 앨범 개수만큼 작성되어야 하는 블록
---------------------- 여기서부터 음원별로 수동 작성해야 함. -----------------------------
-- 그룹이 참여한 앨범 개수만큼 작업해야 함.----------------------------------------------- 

-- 1st Mini Album <SUPER REAL ME> 앨범에 대한 역할 설정-----------------------------------------------------
-- 작곡 20, 작사 30, 편곡 40
-- 음원 참여 내역 없음


-- VIVIZ
-- 1. 은하로 업데이트 (VIVIZ를 은하로 대체)
UPDATE artists
SET artist_name = '은하', 
    artist_image = '은하사진.jpg', 
    artist_description = '은하설명.txt'
WHERE artist_name = 'VIVIZ';

-- 2. VIVIZ 다른 멤버들 추가
INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('신비', '신비사진.jpg', '신비설명.txt'),
       ('엄지', '엄지사진.jpg', '엄지설명.txt');

-- 3. VIVIZ 그룹 추가
INSERT INTO `groups` (group_name)
VALUES ('VIVIZ');

-- 3.5 VIVIZ 그룹 설명과 이미지 추가 (그룹 이미지 추가는 나중에)
UPDATE `groups`
SET group_name = 'VIVIZ', 
    group_image = 'VIVIZ.jpg', 
    group_description = 'VIVIZ.txt'
WHERE group_name = 'VIVIZ';

-- 4. 그룹 멤버 추가(멤버 수대로)
INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'VIVIZ'),
    (SELECT artist_id FROM artists WHERE artist_name = '은하')
);

INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'VIVIZ'),
    (SELECT artist_id FROM artists WHERE artist_name = '신비')
);

INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'VIVIZ'),
    (SELECT artist_id FROM artists WHERE artist_name = '엄지')
);

-- 5. 그룹 ID를 설정 (VIVIZ 그룹 ID)
SET @group_id := (SELECT group_id FROM `groups` WHERE group_name = 'VIVIZ');

-- 6. 은하가 속한 그룹의 음원에 대해 role_id = 10을 추가 (VIVIZ의 리더를 은하로 대체)
INSERT INTO artist_roles (artist_id, group_id, song_id, role_id)
SELECT 
    gm.artist_id,
    @group_id AS group_id,
    ar.song_id,
    10 AS role_id
FROM artist_roles ar
JOIN group_members gm ON gm.group_id = ar.group_id
WHERE ar.artist_id = (SELECT artist_id FROM artists WHERE artist_name = '은하')
  AND gm.artist_id <> (SELECT artist_id FROM artists WHERE artist_name = '은하')
  AND gm.group_id = @group_id
  AND ar.song_id NOT IN (
    SELECT song_id
    FROM artist_roles
    WHERE artist_id = gm.artist_id
      AND group_id = @group_id
      AND role_id = 10
  );

-- 7. artist_roles 업데이트
UPDATE artist_roles
SET group_id = (SELECT group_id FROM `groups` WHERE group_name = 'VIVIZ')
WHERE role_id = 10
AND artist_id IN (
    SELECT artist_id FROM artists WHERE artist_name IN ('은하', '신비', '엄지')
);

---------------------------------- 여기까진 그룹 별로 한 번만 작성하면 됨. -----------------------

-- 그룹 참여 내역 없음

-- aespa
-- 1. 카리나로 업데이트 (aespa를 카리나로 대체)
UPDATE artists
SET artist_name = '카리나', 
    artist_image = '카리나사진.jpg', 
    artist_description = '카리나설명.txt'
WHERE artist_name = 'aespa';

-- 2. aespa 다른 멤버들 추가
INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('지젤', '지젤사진.jpg', '지젤설명.txt'),
       ('윈터', '윈터사진.jpg', '윈터설명.txt'),
       ('닝닝', '닝닝사진.jpg', '닝닝설명.txt');

-- 3. aespa 그룹 추가
INSERT INTO `groups` (group_name)
VALUES ('aespa');

-- 3.5 aespa 그룹 설명과 이미지 추가 (그룹 이미지 추가는 나중에)
UPDATE `groups`
SET group_name = 'aespa', 
    group_image = 'aespa.jpg', 
    group_description = 'aespa.txt'
WHERE group_name = 'aespa';

-- 4. 그룹 멤버 추가(멤버 수대로)
INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'aespa'),
    (SELECT artist_id FROM artists WHERE artist_name = '카리나')
);

INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'aespa'),
    (SELECT artist_id FROM artists WHERE artist_name = '지젤')
);

INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'aespa'),
    (SELECT artist_id FROM artists WHERE artist_name = '윈터')
);

INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = 'aespa'),
    (SELECT artist_id FROM artists WHERE artist_name = '닝닝')
);

-- 5. 그룹 ID를 설정 (aespa 그룹 ID)
SET @group_id := (SELECT group_id FROM `groups` WHERE group_name = 'aespa');

-- 6. 카리나가 속한 그룹의 음원에 대해 role_id = 10을 추가 (aespa의 리더를 카리나로 대체)
INSERT INTO artist_roles (artist_id, group_id, song_id, role_id)
SELECT 
    gm.artist_id,
    @group_id AS group_id,
    ar.song_id,
    10 AS role_id
FROM artist_roles ar
JOIN group_members gm ON gm.group_id = ar.group_id
WHERE ar.artist_id = (SELECT artist_id FROM artists WHERE artist_name = '카리나')
  AND gm.artist_id <> (SELECT artist_id FROM artists WHERE artist_name = '카리나')
  AND gm.group_id = @group_id
  AND ar.song_id NOT IN (
    SELECT song_id
    FROM artist_roles
    WHERE artist_id = gm.artist_id
      AND group_id = @group_id
      AND role_id = 10
  );

-- 7. artist_roles 업데이트
UPDATE artist_roles
SET group_id = (SELECT group_id FROM `groups` WHERE group_name = 'aespa')
WHERE role_id = 10
AND artist_id IN (
    SELECT artist_id FROM artists WHERE artist_name IN ('카리나', '지젤', '윈터', '닝닝')
);

---------------------------------- 여기까진 그룹 별로 한 번만 작성하면 됨. -----------------------
-- 그룹 참여 내역 없음


-- 10cm
-- 1. 권정열로 업데이트 (10cm을 권정열로 대체)
UPDATE artists
SET artist_name = '권정열', 
    artist_image = '권정열사진.jpg', 
    artist_description = '권정열설명.txt'
WHERE artist_name = '10cm';

-- 2. 10cm 그룹 추가
INSERT INTO `groups` (group_name)
VALUES ('10cm');

-- 3.5 10cm 그룹 설명과 이미지 추가 (그룹 이미지 추가는 나중에)
UPDATE `groups`
SET group_name = '10cm', 
    group_image = '10cm.jpg', 
    group_description = '10cm.txt'
WHERE group_name = '10cm';

-- 4. 그룹 멤버 추가
INSERT INTO group_members (group_id, artist_id)
VALUES (
    (SELECT group_id FROM `groups` WHERE group_name = '10cm'),
    (SELECT artist_id FROM artists WHERE artist_name = '권정열')
);

-- 5. 그룹 ID를 설정 (10cm 그룹 ID)
SET @group_id := (SELECT group_id FROM `groups` WHERE group_name = '10cm');

-- 6. 권정열이 속한 그룹의 음원에 대해 role_id = 10을 추가 (10cm의 리더를 권정열로 대체)
INSERT INTO artist_roles (artist_id, group_id, song_id, role_id)
SELECT 
    gm.artist_id,
    @group_id AS group_id,
    ar.song_id,
    10 AS role_id
FROM artist_roles ar
JOIN group_members gm ON gm.group_id = ar.group_id
WHERE ar.artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
  AND gm.artist_id <> (SELECT artist_id FROM artists WHERE artist_name = '권정열')
  AND gm.group_id = @group_id
  AND ar.song_id NOT IN (
    SELECT song_id
    FROM artist_roles
    WHERE artist_id = gm.artist_id
      AND group_id = @group_id
      AND role_id = 10
  );

-- 7. artist_roles 업데이트
UPDATE artist_roles
SET group_id = (SELECT group_id FROM `groups` WHERE group_name = '10cm')
WHERE role_id = 10
AND artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열');

---------------------------------- 여기까진 그룹 별로 한 번만 작성하면 됨. -----------------------

-- 음원의 작사, 작곡, 편곡 설정: 그룹이 참여한 앨범 개수만큼 작성되어야 하는 블록
---------------------- 여기서부터 음원별로 수동 작성해야 함. -----------------------------
-- 그룹이 참여한 앨범 개수만큼 작업해야 함.----------------------------------------------- 

-- <4.0>에 대한 역할 설정-----------------------------------------------------

-- 폰서트
-- 작곡 (role_id = 20)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = '폰서트'),
    20 -- 작곡
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = '폰서트')
      AND role_id = 20
);

-- 작사 (role_id = 30)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = '폰서트'),
    30 -- 작사
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = '폰서트')
      AND role_id = 30
);

-- 편곡 (role_id = 40)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = '폰서트'),
    40 -- 편곡
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = '폰서트')
      AND role_id = 40
);

-- Help
-- 작곡 (role_id = 20)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = 'Help'),
    20 -- 작곡
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = 'Help')
      AND role_id = 20
);

-- 작사 (role_id = 30)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = 'Help'),
    30 -- 작사
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = 'Help')
      AND role_id = 30
);

-- 편곡 (role_id = 40)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = 'Help'),
    40 -- 편곡
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = 'Help')
      AND role_id = 40
);

-- Everything
-- 작곡 (role_id = 20)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = 'Everything'),
    20 -- 작곡
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = 'Everything')
      AND role_id = 20
);

-- 작사 (role_id = 30)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = 'Everything'),
    30 -- 작사
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = 'Everything')
      AND role_id = 30
);

-- 편곡 (role_id = 40)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = 'Everything'),
    40 -- 편곡
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = 'Everything')
      AND role_id = 40
);

-- pet
-- 작곡 (role_id = 20)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = 'pet'),
    20 -- 작곡
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = 'pet')
      AND role_id = 20
);

-- 작사 (role_id = 30)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = 'pet'),
    30 -- 작사
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = 'pet')
      AND role_id = 30
);

-- 편곡 (role_id = 40)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = 'pet'),
    40 -- 편곡
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = 'pet')
      AND role_id = 40
);

-- 별자리
-- 작곡 (role_id = 20)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = '별자리'),
    20 -- 작곡
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = '별자리')
      AND role_id = 20
);

-- 작사 (role_id = 30)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = '별자리'),
    30 -- 작사
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = '별자리')
      AND role_id = 30
);

-- 편곡 (role_id = 40)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = '별자리'),
    40 -- 편곡
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = '별자리')
      AND role_id = 40
);

-- Hotel room
-- 작곡 (role_id = 20)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = 'Hotel room'),
    20 -- 작곡
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = 'Hotel room')
      AND role_id = 20
);

-- 작사 (role_id = 30)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = 'Hotel room'),
    30 -- 작사
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = 'Hotel room')
      AND role_id = 30
);

-- 편곡 (role_id = 40)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = 'Hotel room'),
    40 -- 편곡
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = 'Hotel room')
      AND role_id = 40
);

-- Island
-- 작곡 (role_id = 20)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = 'Island'),
    20 -- 작곡
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = 'Island')
      AND role_id = 20
);

-- 작사 (role_id = 30)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = 'Island'),
    30 -- 작사
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = 'Island')
      AND role_id = 30
);

-- 편곡 (role_id = 40)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = 'Island'),
    40 -- 편곡
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = 'Island')
      AND role_id = 40
);

-- 일시정지
-- 작곡 (role_id = 20)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = '일시정지'),
    20 -- 작곡
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = '일시정지')
      AND role_id = 20
);

-- 작사 (role_id = 30)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = '일시정지'),
    30 -- 작사
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = '일시정지')
      AND role_id = 30
);

-- 편곡 (role_id = 40)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = '일시정지'),
    40 -- 편곡
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = '일시정지')
      AND role_id = 40
);


-- 10CM 4.5앨범에 대한 역할-----------------------------------------------------
-- 'Tight'
-- 작곡 (role_id = 20)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = 'Tight'),
    20
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = 'Tight')
      AND role_id = 20
);

-- 작사 (role_id = 30)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = 'Tight'),
    30
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = 'Tight')
      AND role_id = 30
);

-- 편곡 (role_id = 40)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = 'Tight'),
    40
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = 'Tight')
      AND role_id = 40
);

-- 10CM 4.4앨범에 대한 역할-----------------------------------------------------
-- '방에 모기가 있어'
-- 작곡 (role_id = 20)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = '방에 모기가 있어 (Do You Think Of Me)'),
    20
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = '방에 모기가 있어 (Do You Think Of Me)')
      AND role_id = 20
);

-- 작사 (role_id = 30)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = '방에 모기가 있어 (Do You Think Of Me)'),
    30
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = '방에 모기가 있어 (Do You Think Of Me)')
      AND role_id = 30
);

-- 편곡 (role_id = 40)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = '방에 모기가 있어 (Do You Think Of Me)'),
    40
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = '방에 모기가 있어 (Do You Think Of Me)')
      AND role_id = 40
);

-- 10CM 4.1앨범에 대한 역할-----------------------------------------------------
-- '매트리스'
-- 작곡 (role_id = 20)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = '매트리스'),
    20
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = '매트리스')
      AND role_id = 20
);

-- 작사 (role_id = 30)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = '매트리스'),
    30
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = '매트리스')
      AND role_id = 30
);

-- 편곡 (role_id = 40)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = '매트리스'),
    40
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = '매트리스')
      AND role_id = 40
);

-- The 3rd EP 앨범에 대한 역할-----------------------------------------------------
-- '어제 너는 나를 버렸어'
-- 작곡 (role_id = 20)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = '어제 너는 나를 버렸어'),
    20
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = '어제 너는 나를 버렸어')
      AND role_id = 20
);

-- 작사 (role_id = 30)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = '어제 너는 나를 버렸어'),
    30
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = '어제 너는 나를 버렸어')
      AND role_id = 30
);

-- 편곡 (role_id = 40)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = '어제 너는 나를 버렸어'),
    40
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = '어제 너는 나를 버렸어')
      AND role_id = 40
);


-- '가진다는 말은 좀 그렇지'
-- 작곡 (role_id = 20)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = '가진다는 말은 좀 그렇지'),
    20 -- 작곡
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = '가진다는 말은 좀 그렇지')
      AND role_id = 20
);

-- 작사 (role_id = 30)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = '가진다는 말은 좀 그렇지'),
    30 -- 작사
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = '가진다는 말은 좀 그렇지')
      AND role_id = 30
);

-- 편곡 (role_id = 40)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = '가진다는 말은 좀 그렇지'),
    40 -- 편곡
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = '가진다는 말은 좀 그렇지')
      AND role_id = 40
);

-- '열심히 할게'
-- 작곡 (role_id = 20)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = '열심히 할게'),
    20 -- 작곡
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = '열심히 할게')
      AND role_id = 20
);

-- 작사 (role_id = 30)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = '열심히 할게'),
    30 -- 작사
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = '열심히 할게')
      AND role_id = 30
);

-- 편곡 (role_id = 40)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = '열심히 할게'),
    40 -- 편곡
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = '열심히 할게')
      AND role_id = 40
);

-- 'Condition'
-- 작곡 (role_id = 20)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = 'Condition'),
    20 -- 작곡
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = 'Condition')
      AND role_id = 20
);

-- 작사 (role_id = 30)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = 'Condition'),
    30 -- 작사
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = 'Condition')
      AND role_id = 30
);

-- 편곡 (role_id = 40)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = 'Condition'),
    40 -- 편곡
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = 'Condition')
      AND role_id = 40
);

-- 'Please Don't Stop Your Singing'
-- 작곡 (role_id = 20)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = 'Please Don''t Stop Your Singing'),
    20 -- 작곡
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = 'Please Don''t Stop Your Singing')
      AND role_id = 20
);

-- 작사 (role_id = 30)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = 'Please Don''t Stop Your Singing'),
    30 -- 작사
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = 'Please Don''t Stop Your Singing')
      AND role_id = 30
);

-- 편곡 (role_id = 40)
INSERT INTO artist_roles (artist_id, song_id, role_id)
SELECT 
    (SELECT artist_id FROM artists WHERE artist_name = '권정열'),
    (SELECT song_id FROM songs WHERE title = 'Please Don''t Stop Your Singing'),
    40 -- 편곡
WHERE NOT EXISTS (
    SELECT 1 
    FROM artist_roles 
    WHERE artist_id = (SELECT artist_id FROM artists WHERE artist_name = '권정열')
      AND song_id = (SELECT song_id FROM songs WHERE title = 'Please Don''t Stop Your Singing')
      AND role_id = 40
);

COMMIT;