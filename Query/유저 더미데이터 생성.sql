CALL generate_dummy_users(1, 500); -- 유저 더미데이터 생성(시작 번호, 끝 번호)
CALL insert_album_likes(90, 90); -- 앨범 좋아요 생성기(앨범번호, 좋아요 개수)
CALL insert_song_likes(90, 90); -- 음원 좋아요 생성기(음원 번호, 좋아요 개수)
CALL insert_group_likes(90, 90); -- 그룹 좋아요 생성기(그룹 번호, 좋아요 개수)
CALL insert_artist_likes(90, 90); -- 그룹 좋아요 생성기(그룹 번호, 좋아요 개수)

-- 유저 더미데이터 생성 프로시저
DELIMITER $$

CREATE PROCEDURE generate_dummy_users(IN start_num INT, IN end_num INT)
BEGIN
    DECLARE i INT;

    SET i = start_num;

    WHILE i <= end_num DO
        INSERT INTO users (
            user_name,
            user_id,
            password,
            email,
            nickname,
            user_profile,
            hint_question,
            hint_answer,
            is_active,
            deactivated_until
        )
        VALUES (
            CONCAT('테스트', i), -- user_name
            CONCAT('test', i),   -- user_id
            '$2a$12$rcesHpV.7pAkXOlDgJaRNenB2gtoiMTfDFqn1u1VTN47MLFu75zIu', -- password (암호화된 값)
            CONCAT(i, '@example.com'), -- email
            CONCAT(i), -- nickname
            i, -- user_profile
            i, -- hint_question
            i, -- hint_answer
            1, -- is_active (항상 1)
            NULL -- deactivated_until (비활성 만료일, NULL로 기본값 설정)
        );
        
        SET i = i + 1;
    END WHILE;
END $$

DELIMITER ;

-- 앨범 좋아요 생성 프로시저
DELIMITER $$

CREATE PROCEDURE insert_album_likes(IN album_id INT, IN num_likes INT)
BEGIN
    DECLARE i INT;

    -- 초기 값 설정
    SET i = 1;

    -- 반복문을 사용하여 데이터 삽입
    WHILE i <= num_likes DO
        INSERT INTO album_likes (album_id, id)
        VALUES (album_id, i);

        -- i 값 증가
        SET i = i + 1;
    END WHILE;
END $$

DELIMITER ;

DELIMITER ;

-- 음원 좋아요 생성 프로시저
DELIMITER $$

CREATE PROCEDURE insert_song_likes(IN song_id INT, IN num_likes INT)
BEGIN
    DECLARE i INT;

    -- 초기 값 설정
    SET i = 1;

    -- 반복문을 사용하여 데이터 삽입
    WHILE i <= num_likes DO
        INSERT INTO likes (song_id, id)
        VALUES (song_id, i);

        -- i 값 증가
        SET i = i + 1;
    END WHILE;
END $$

DELIMITER ;


-- 그룹 좋아요 생성 프로시저
DELIMITER $$

CREATE PROCEDURE insert_group_likes(IN group_id INT, IN num_likes INT)
BEGIN
    DECLARE i INT;

    -- 초기 값 설정
    SET i = 1;

    -- 반복문을 사용하여 데이터 삽입
    WHILE i <= num_likes DO
        INSERT INTO group_likes (group_id, id)
        VALUES (group_id, i);

        -- i 값 증가
        SET i = i + 1;
    END WHILE;
END $$

DELIMITER ;

-- 아티스트 좋아요 생성 프로시저
DELIMITER $$

CREATE PROCEDURE insert_artist_likes(IN artist_id INT, IN num_likes INT)
BEGIN
    DECLARE i INT;

    -- 초기 값 설정
    SET i = 1;

    -- 반복문을 사용하여 데이터 삽입
    WHILE i <= num_likes DO
        INSERT INTO artist_likes (artist_id, id)
        VALUES (artist_id, i);

        -- i 값 증가
        SET i = i + 1;
    END WHILE;
END $$

DELIMITER ;