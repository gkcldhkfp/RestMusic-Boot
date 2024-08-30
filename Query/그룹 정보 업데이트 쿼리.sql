-- 위 설정 창 Edit - PreFerences ->  SQL Editor -> 아래 쪽 Safe Updates(~~~~~) 체크 해제 후 MySQL 워크벤치 껐다 키고 전체 실행 시키면 됨.

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
    group_image = '볼빨간사춘기사진.jpg', 
    group_description = '볼빨간사춘기설명.txt'
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
SELECT artist_id FROM artists WHERE artist_name = '우지윤';
-- 작곡 20, 작사 30, 편곡 40
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (
    (SELECT artist_id FROM artists WHERE artist_name = '우지윤'),
    (SELECT song_id FROM songs WHERE title = '나들이 갈까'),
    20
);

commit;

