

--- 코드 마스터 테이블 ---------------------------------------------------------------------------
INSERT INTO code_master (code_id, code_name)
VALUES (1, '아티스트');

INSERT INTO code_master (code_id, code_name)
VALUES (2, '장르');

COMMIT;


--- 역할 코드 테이블 ---------------------------------------------------------------------------
INSERT INTO role_code (code_id, role_name, role_id)
VALUES (1, '가수', 10);

INSERT INTO role_code (code_id, role_name, role_id)
VALUES (1, '작곡가', 20);

INSERT INTO role_code (code_id, role_name, role_id)
VALUES (1, '작사가', 30);

INSERT INTO role_code (code_id, role_name, role_id)
VALUES (1, '편곡가', 40);

COMMIT;


--- 장르 코드 테이블 ---------------------------------------------------------------------------
INSERT INTO genre_code (code_id, genre_name, genre_id)
VALUES (2, '발라드', 10);

INSERT INTO genre_code (code_id, genre_name, genre_id)
VALUES (2, '팝', 20);

INSERT INTO genre_code (code_id, genre_name, genre_id)
VALUES (2, '댄스/일렉', 30);

INSERT INTO genre_code (code_id, genre_name, genre_id)
VALUES (2, '알앤비', 40);

INSERT INTO genre_code (code_id, genre_name, genre_id)
VALUES (2, '힙합', 50);

INSERT INTO genre_code (code_id, genre_name, genre_id)
VALUES (2, '트로트', 60);

INSERT INTO genre_code (code_id, genre_name, genre_id)
VALUES (2, 'OST', 70);

INSERT INTO genre_code (code_id, genre_name, genre_id)
VALUES (2, '인디', 80);

INSERT INTO genre_code (code_id, genre_name, genre_id)
VALUES (2, '포크/블루스', 90);

INSERT INTO genre_code (code_id, genre_name, genre_id)
VALUES (2, '록/메탈', 100);

COMMIT;


--- 앨범---------------------------------------------------------------------------


--  (SELECT album_id FROM albums WHERE album_name = 'armageddon')
--  (SELECT song_id FROM songs WHERE title='maniac')
--  (SELECT artist_id FROM artists WHERE artist_name = 'Kenzi')

INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('What Time!', 
        'what_time_album_image.png', 
        'EP',
        '2023-08-18'
        );

-- 아이유 lilac
INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('IU 5th Album LILAC', 
        'lilac_albumcover.png', 
        '정규',
        '2021-03-25'
        );

-- 천천히 가 앨범
INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('천천히가', 
        'kenzi_천천히가_albumcover.png', 
        '싱글',
        '2021-05-01'
        );				

-- 볼빨간사춘기 RED PLANET 4번앨범
INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('RED PLANET', 
        'bol4_RED PLANET_albumcover.png', 
        '정규',
        '2016-08-29'
        );


--- 	박성준 앨범 5번 앨범 시작
INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('Love poem', 'Love poem.jpg', '미니', '2019-11-18');
----------------------------------------------------------------------------------------------------------------------------
INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('삐삐', '삐삐.jpg', '싱글', '2018-10-10');
----------------------------------------------------------------------------------------------------------------------------
INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('밤편지', '밤편지.jpg', '싱글', '2017-03-24');

INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('사춘기집Ⅰ 꽃기운', '사춘기집Ⅰ 꽃기운.jpg', '미니', '2019-04-02');

INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('SUMMER EPISODE', 'SUMMER EPISODE.jpg', '싱글', '2017-07-20');

INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('Love Lee', 'Love Lee.jpg', '싱글', '2023-08-21');
-- 앨범 10번까지


--- 음원 --------------------------------------------------------------------------------
INSERT INTO songs (album_id, title, song_path, lyrics)
VALUES (1,
        'What time! (Feat.DumbAss)',
        'what_time_what_time_kenzi.mp3',
        'kenzi_WhatTime_lyrics.txt');
        
INSERT INTO songs (album_id, title, song_path, lyrics)
VALUES (1,
        '좋겠어 (feat. 현서)',
        'what_time_i_wish_kenzi.mp3',
        'kenzi_좋겠어_lyrics.txt'
        );
        
INSERT INTO songs (album_id, title, song_path, lyrics)
VALUES (1,
        'Ice (feat. PLHN)',
        'what_time_ice_kenzi.mp3',
        'kenzi_Ice_lyrics.txt'
        );
        
INSERT INTO songs (album_id, title, song_path, lyrics)
VALUES (1,
        'Promise (약속)',
        'what_time_promise_kenzi.mp3',
        'kenzi_약속_lyrics.txt'
        );


-- 음원 정보 5번 시작				
INSERT INTO songs (album_id, title, song_path, lyrics)
VALUES (2,
        '라일락',
        'IU(아이유) - 라일락.mp3',
        '아이유라일락가사.txt');
INSERT INTO songs (album_id, title, song_path, lyrics)
VALUES (2,
        'Flu',
        'Flu.mp3',
        '아이유Flu가사.txt');
INSERT INTO songs (album_id, title, song_path, lyrics)
VALUES (2,
        'Coin',
        'IU- Coin.mp3',
        '아이유Coin가사.txt');
INSERT INTO songs (album_id, title, song_path, lyrics)
VALUES (2,
        '봄 안녕 봄',
        '봄 안녕 봄.mp3',
        '아이유_봄안녕봄가사.txt');
INSERT INTO songs (album_id, title, song_path, lyrics)
VALUES (2,
        'Celebrity',
        '아이유(IU) - Celebrity.mp3',
        '아이유Celebrity가사.txt');
INSERT INTO songs (album_id, title, song_path, lyrics)
VALUES (2,
        '돌림노래 (Feat. DEAN)',
        '돌림노래 (Feat. DEAN).mp3',
        '아이유돌림노래가사.txt');
INSERT INTO songs (album_id, title, song_path, lyrics)
VALUES (2,
        '빈 컵 (Empty Cup)',
        '빈 컵.mp3',
        '아이유빈컵가사.txt');
INSERT INTO songs (album_id, title, song_path, lyrics)
VALUES (2,
        '아이와 나의 바다',
        '아이와 나의 바다.mp3',
        '아이유아이와나의바다가사.txt');
INSERT INTO songs (album_id, title, song_path, lyrics)
VALUES (2,
        '어푸 (Ah puh)',
        'IU 어푸.mp3',
        '아이유어푸가사.txt');
INSERT INTO songs (album_id, title, song_path, lyrics)
VALUES (2,
        '에필로그',
        'IU - 에필로그.mp3',
        '아이유에필로그가사.txt');
-- 음원번호 14번까지				

-- 15번
INSERT INTO songs (album_id, title, song_path, lyrics)
VALUES (3,
        '천천히 가',
        'Kenzi-천천히가.mp3',
        'kenzi_천천히가_lyrics.txt');
        
-- 16번
INSERT INTO songs (album_id, title, song_path, lyrics)
VALUES (3,
        '막달려가 (feat.스카이민혁)',
        'Kenzi - 막달려가 (feat.스카이민혁).mp3',
        'kenzi_막달려가_lyrics.txt'
        );

-- 음원데이터 17번 부터
INSERT INTO songs (album_id, title, song_path, lyrics)
VALUES (4,
        '우주를 줄게',
        'BOL4 - Galaxy(우주를 줄게).mp3',
        'bol4우주를 줄게.txt');
INSERT INTO songs (album_id, title, song_path, lyrics)
VALUES (4,
        '싸운날',
        'BOL4 - 싸운날.mp3',
        'bol4싸운날.txt');
INSERT INTO songs (album_id, title, song_path, lyrics)
VALUES (4,
        'You(=I)',
        'BOL4 - You(=I).mp3',
        'bol4You(=I).txt');
INSERT INTO songs (album_id, title, song_path, lyrics)
VALUES (4,
        '심술',
        'BOL4 - Mean(심술).mp3',
        'bol4심술.txt');
INSERT INTO songs (album_id, title, song_path, lyrics)
VALUES (4,
        '나만 안되는 연애',
        'BOL4 - 나만 안되는 연애.mp3',
        'bol4나만 안되는 연애.txt');
INSERT INTO songs (album_id, title, song_path, lyrics)
VALUES (4,
        '초콜릿',
        'BOL4 - Chocolate Drive(초콜릿).mp3',
        'bol4초콜릿.txt');
INSERT INTO songs (album_id, title, song_path, lyrics)
VALUES (4,
        '프리지아',
        'BOL4 - 프리지아.mp3',
        'bol4프리지아.txt');
INSERT INTO songs (album_id, title, song_path, lyrics)
VALUES (4,
        'X Song',
        'BOL4 - X Song.mp3',
        'bol4X Song.txt');
INSERT INTO songs (album_id, title, song_path, lyrics)
VALUES (4,
        '반지',
        'BOL4 - 반지.mp3',
        'bol4반지.txt');
INSERT INTO songs (album_id, title, song_path, lyrics)
VALUES (4,
        '사랑에 빠졌을 때',
        'BOL4 - 사랑에 빠졌을 때.mp3',
        'bol4사랑에 빠졌을 때.txt');				
-- 26번 까지

-- 박성준 음악 데이터 27번 시작------------
-------------------------------------------------------------------------------------------------------------------- 27
INSERT INTO songs (album_id, title, song_path, lyrics, video_link) -- Love poem 앨범
VALUES (5, 'Blueming',
'Love poem_Blueming_아이유.mp3',
'Love poem_Blueming.txt',
'https://www.youtube.com/embed/D1PvIWdJ8xo?si=ceiPBphESLEJuHOw');

-- 28
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES (5, 'Love poem',
'Love poem_Love poem_아이유',
'Love poem_Love poem.txt',
'https://www.youtube.com/embed/iOKRYIMhaDk?si=GrX2NBvfwTeflTsA');

-- 29
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES (5, 'unlucky',
'Love poem_unlucky_아이유.mp3',
'Love poem_unlucky.txt',
'https://www.youtube.com/embed/l5Rb1pNre40?si=twvr-w2hpTI8UFIv');

-- 30
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES (5, '그 사람',
'Love poem_그 사람_아이유.mp3',
'Love poem_그 사람.txt',
'https://www.youtube.com/embed/y5YmTj8KDWM?si=crUeOIAWK4Htwln9');

-- 31
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES (5, '시간의 바깥',
'Love poem_시간의 바깥_아이유.mp3',
'Love poem_시간의 바깥.txt',
'https://www.youtube.com/embed/R3Fwdnij49o?si=xOzo9VwlR5zYFXAs');

-- 32
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES (5, '자장가',
'Love poem_자장가_아이유.mp3',
'Love poem_자장가.txt',
'https://www.youtube.com/embed/aepREwo5Lio?si=cmKyl_jq9DIWsmgu');
----------------------------------------------------------------------------------------------------------------------------

-- 33
INSERT INTO songs (album_id, title, song_path, lyrics, video_link) -- 삐삐 앨범
VALUES (6, '삐삐',
'삐삐_삐삐_아이유.mp3',
'삐삐_삐삐.txt',
'https://www.youtube.com/embed/nM0xDI5R50E?si=6Ao8ysv8-rNtrG1-');
----------------------------------------------------------------------------------------------------------------------------

-- 34
INSERT INTO songs (album_id, title, song_path, lyrics, video_link) -- 밤편지 앨범
VALUES (7, '밤편지',
'밤편지_밤편지_아이유.mp3',
'밤편지_밤편지.txt',
'https://www.youtube.com/embed/BzYnNdJhZQw?si=T34qb5d_rxZU_KQg');
----------------------------------------------------------------------------------------------------------------------------

-- 35
INSERT INTO songs (album_id, title, song_path, lyrics, video_link) -- 사춘기집 앨범
VALUES (8, '별 보러 갈래?', 
'사춘기집Ⅰ 꽃기운_별 보러 갈래?_볼빨간사춘기.mp3', 
'사춘기집Ⅰ 꽃기운_별 보러 갈래?.txt',
'https://www.youtube.com/embed/8n9wklIG9qU?si=68aDQpG5BiLWldfR');


-- 36
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES (8, '나만, 봄', 
'사춘기집Ⅰ 꽃기운_나만, 봄_볼빨간사춘기.mp3',
'사춘기집Ⅰ 꽃기운_나만, 봄.txt',
'https://www.youtube.com/embed/AsXxuIdpkWM?si=LOEOJ6weXzLYEoZU');

-- 37
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES (8, '나들이 갈까', 
'사춘기집Ⅰ 꽃기운_나들이 갈까_볼빨간사춘기.mp3', 
'사춘기집Ⅰ 꽃기운_나들이 갈까.txt',
'https://www.youtube.com/embed/nDn1T3yRT0k?si=TNvmZqqi1pkV2tx-');


-- 38
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES (8, 'Mermaid', 
'사춘기집Ⅰ 꽃기운_Mermaid_볼빨간사춘기.mp3',
'사춘기집Ⅰ 꽃기운_Mermaid.txt',
'https://www.youtube.com/embed/jDrVkH297iQ?si=QgYNC4MOBr5y_phg');

-- 39
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES (8, 'Seattle Alone', 
'사춘기집Ⅰ 꽃기운_Seattle Alone_볼빨간사춘기.mp3',
'사춘기집Ⅰ 꽃기운_Seattle Alone.txt',
'https://www.youtube.com/embed/soZoplDbCKg?si=395CR1DhCV1C4zP-');
----------------------------------------------------------------------------------------------------------------------------

-- 40
INSERT INTO songs (album_id, title, song_path, lyrics, video_link) -- SUMMER EPISODE 앨범
VALUES (9, 'DINOSAUR', 
'SUMMER EPISODE_DINOSAUR_AKMU (악뮤).mp3',
'SUMMER EPISODE_DINOSAUR.txt',
'https://www.youtube.com/embed/8Oz7DG76ibY?si=v2zM_PO9Hb2ZWCMc');

-- 41
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES (9, 'MY DARLING', 
'SUMMER EPISODE_MY DARLING_AKMU (악뮤)',
'SUMMER EPISODE_MY DARLING.txt',
'https://www.youtube.com/embed/8Oz7DG76ibY?si=v2zM_PO9Hb2ZWCMc');
----------------------------------------------------------------------------------------------------------------------------

-- 42
INSERT INTO songs (album_id, title, song_path, lyrics, video_link) -- Love Lee 앨범
VALUES (10, 'Love Lee',
'Love Lee_Love Lee_AKMU (악뮤).mp3',
'Love Lee_Love Lee.txt',
'https://www.youtube.com/embed/EIz09kLzN9k?si=Fqba3c4bHmEodWpI');


-- 43
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES (10, '후라이의 꿈',
'Love Lee_후라이의 꿈_AKMU (악뮤).mp3',
'Love Lee_후라이의 꿈.txt',
'https://www.youtube.com/embed/3kGAlp_PNUg?si=qCXf56Gns92dR7V6');


---- 장르 ----------------------------------------------------------------------------------
INSERT INTO song_genre (song_id, genre_id)
VALUES (1, 50);

INSERT INTO song_genre (song_id, genre_id)
VALUES (1, 80);

INSERT INTO song_genre (song_id, genre_id)
VALUES (2, 50);
INSERT INTO song_genre (song_id, genre_id)
VALUES (2, 80);

INSERT INTO song_genre (song_id, genre_id)
VALUES (3, 50);
INSERT INTO song_genre (song_id, genre_id)
VALUES (3, 80);

INSERT INTO song_genre (song_id, genre_id)
VALUES (4, 50);
INSERT INTO song_genre (song_id, genre_id)
VALUES (4, 80);

-- 장르설정 랩/힙합, 인디
INSERT INTO song_genre (song_id, genre_id)
VALUES (15, 50);
INSERT INTO song_genre (song_id, genre_id)
VALUES (15, 80);

INSERT INTO song_genre (song_id, genre_id)
VALUES (16, 50);
INSERT INTO song_genre (song_id, genre_id)
VALUES (16, 80);


-- 아이유 lilac장르 설정
INSERT INTO song_genre (song_id, genre_id)
VALUES (5, 30);
INSERT INTO song_genre (song_id, genre_id)
VALUES (6, 30);
INSERT INTO song_genre (song_id, genre_id)
VALUES (7, 40);
INSERT INTO song_genre (song_id, genre_id)
VALUES (8, 10);
INSERT INTO song_genre (song_id, genre_id)
VALUES (9, 30);
INSERT INTO song_genre (song_id, genre_id)
VALUES (10, 40);
INSERT INTO song_genre (song_id, genre_id)
VALUES (11, 40);
INSERT INTO song_genre (song_id, genre_id)
VALUES (12, 10);
INSERT INTO song_genre (song_id, genre_id)
VALUES (13, 30);
INSERT INTO song_genre (song_id, genre_id)
VALUES (14, 10);

-- 볼사 우주를줄게 장르 설정
INSERT INTO song_genre (song_id, genre_id)
VALUES (17, 80);
INSERT INTO song_genre (song_id, genre_id)
VALUES (18, 80);
INSERT INTO song_genre (song_id, genre_id)
VALUES (19, 80);
INSERT INTO song_genre (song_id, genre_id)
VALUES (20, 80);
INSERT INTO song_genre (song_id, genre_id)
VALUES (21, 80);
INSERT INTO song_genre (song_id, genre_id)
VALUES (22, 80);
INSERT INTO song_genre (song_id, genre_id)
VALUES (23, 80);
INSERT INTO song_genre (song_id, genre_id)
VALUES (24, 80);
INSERT INTO song_genre (song_id, genre_id)
VALUES (25, 80);
INSERT INTO song_genre (song_id, genre_id)
VALUES (26, 80);

-- 박성준 곡 장르 데이터 추가(DB 통합할때 album_id, song_id 확인 및 설정 주의!)
INSERT INTO song_genre (song_id, genre_id) -- Love poem 앨범
VALUES (27, 20);

INSERT INTO song_genre (song_id, genre_id)
VALUES (28, 20);

INSERT INTO song_genre (song_id, genre_id)
VALUES (29, 20);

INSERT INTO song_genre (song_id, genre_id)
VALUES (30, 20);

INSERT INTO song_genre (song_id, genre_id)
VALUES (31, 20);

INSERT INTO song_genre (song_id, genre_id)
VALUES (32, 20);

INSERT INTO song_genre (song_id, genre_id) -- 삐삐 앨범
VALUES (33, 40);

INSERT INTO song_genre (song_id, genre_id) -- 밤편지 앨범
VALUES (34, 10);

INSERT INTO song_genre (song_id, genre_id) -- 사춘기집 앨범
VALUES (35, 80);

INSERT INTO song_genre (song_id, genre_id)
VALUES (36, 80);

INSERT INTO song_genre (song_id, genre_id)
VALUES (37, 80);

INSERT INTO song_genre (song_id, genre_id)
VALUES (38, 80);

INSERT INTO song_genre (song_id, genre_id)
VALUES (39, 80);

INSERT INTO song_genre (song_id, genre_id) -- SUMMER EPISODE 앨범
VALUES (40, 30);

INSERT INTO song_genre (song_id, genre_id)
VALUES (41, 30);

INSERT INTO song_genre (song_id, genre_id) -- LoveLee 앨범
VALUES (42, 30);

INSERT INTO song_genre (song_id, genre_id)
VALUES (43, 90);


-- 타이틀 곡 설정--------------------------------------------------------------------------------
INSERT INTO title_songs (album_id, song_id)
VALUES (1, 1); -- what time은 타이틀곡


-- 타이틀곡 설정
-- 라일락(5), 코인(7)
-- 타이틀 곡 설정
INSERT INTO title_songs (album_id, song_id)
VALUES (2, 5); 
INSERT INTO title_songs (album_id, song_id)
VALUES (2, 7); 

-- 타이틀 곡 설정
-- 둘다 타이틀 곡임
-- 천천히가(15) 막달려가(16)
INSERT INTO title_songs (album_id, song_id)
VALUES (3, 15); 
INSERT INTO title_songs (album_id, song_id)
VALUES (3, 16); 


-- 타이틀 곡 설정
-- 우주를 줄게(17) 나만 안되는 연애(21)
INSERT INTO title_songs (album_id, song_id)
VALUES (4, 17); 
INSERT INTO title_songs (album_id, song_id)
VALUES (4, 21); 

-- 박성준 타이틀 곡 데이터 추가(DB 통합할때 album_id, song_id 확인 및 설정 주의!)
INSERT INTO title_songs (album_id, song_id) -- Love poem 앨범 타이틀 곡(Love poem 곡)
VALUES (5, 28);

INSERT INTO title_songs (album_id, song_id) -- 삐삐 앨범 타이틀 곡(삐삐 곡)
VALUES (6, 33);

INSERT INTO title_songs (album_id, song_id) -- 밤편지 앨범 타이틀 곡(밤편지 곡)
VALUES (7, 34);

INSERT INTO title_songs (album_id, song_id) -- 사춘기집 앨범 타이틀 곡(나만, 봄 곡)
VALUES (8, 36);

INSERT INTO title_songs (album_id, song_id) -- SUMMER EPISODE 앨범 타이틀 곡(DINOSAUR 곡)
VALUES (9, 40);

INSERT INTO title_songs (album_id, song_id) -- Love Lee 앨범 타이틀 곡(Love Lee 곡)
VALUES (10, 42);


-- 음원의 참여 아티스트 데이터 삽입----------------------------------------------------------------------
-- 아티스트 1번
INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('Kenzi', '켄지아티스트이미지.jpg', 'kenzi_description.txt');

INSERT INTO artists (artist_name)
VALUES ('PLHN');

INSERT INTO artists (artist_name)
VALUES ('Placcebo Beats');

INSERT INTO artists (artist_name)
VALUES ('DumbAss');

INSERT INTO artists (artist_name)
VALUES ('GAXILLIC');

INSERT INTO artists (artist_name)
VALUES ('현서 (HYUN SEO)');

INSERT INTO artists (artist_name)
VALUES ('Tmlm');

INSERT INTO artists (artist_name)
VALUES ('Wolfgang Pander');

-- 아티스트 7번까지



-- 음원의 참여 아티스트 데이터 삽입
-- 아티스트번호 8번부터
-- INSERT INTO artists (artist_name, artist_image, artist_description)
-- VALUES ('아이유', '아이유아티스트사진.png', '아이유_설명.txt');
INSERT INTO artists (artist_name)
VALUES ('임수호');
INSERT INTO artists (artist_name)
VALUES ('Dr.JO');
INSERT INTO artists (artist_name)
VALUES ('웅킴');
INSERT INTO artists (artist_name)
VALUES ('N!ko');
INSERT INTO artists (artist_name)
VALUES ('Ryan S. Jhun');
INSERT INTO artists (artist_name)
VALUES ('Martin Coogan');
INSERT INTO artists (artist_name)
VALUES ('Madilyn Bailey');
INSERT INTO artists (artist_name)
VALUES ('Zacchariah Palmer');
INSERT INTO artists (artist_name)
VALUES ('London Jackson');
INSERT INTO artists (artist_name)
VALUES ('Jacob Chatelain');
INSERT INTO artists (artist_name)
VALUES ('London Jackson2');
INSERT INTO artists (artist_name)
VALUES ('Ryan S. Jhun2');
INSERT INTO artists (artist_name)
VALUES ('Poptime');
INSERT INTO artists (artist_name)
VALUES ('Kako');
INSERT INTO artists (artist_name)
VALUES ('나얼');
INSERT INTO artists (artist_name)
VALUES ('강화성');
INSERT INTO artists (artist_name)
VALUES ('Jeppe London Bilsby');
INSERT INTO artists (artist_name)
VALUES ('Lauritz Emil Christiansen');
INSERT INTO artists (artist_name)
VALUES ('Chloe Latimer');
INSERT INTO artists (artist_name)
VALUES ('Celine Svanback');
INSERT INTO artists (artist_name)
VALUES ('박우상');
INSERT INTO artists (artist_name)
VALUES ('JUNNY');
INSERT INTO artists (artist_name)
VALUES ('DEAN');
INSERT INTO artists (artist_name)
VALUES ('jane');
INSERT INTO artists (artist_name)
VALUES ('WOOGIE');
INSERT INTO artists (artist_name)
VALUES ('PENOMECO');
INSERT INTO artists (artist_name)
VALUES ('제휘');
INSERT INTO artists (artist_name)
VALUES ('김희원');
INSERT INTO artists (artist_name)
VALUES ('이찬혁');
INSERT INTO artists (artist_name)
VALUES ('PEEJAY');
INSERT INTO artists (artist_name)
VALUES ('심은지');
INSERT INTO artists (artist_name)
VALUES ('SUMIN');
INSERT INTO artists (artist_name)
VALUES ('김수영');
INSERT INTO artists (artist_name)
VALUES ('임금비');

-- 음원의 참여 아티스트 데이터 삽입
-- 아티스트 43번 부터

INSERT INTO artists (artist_name)
VALUES ('ST4NDARD');

INSERT INTO artists (artist_name)
VALUES ('스카이민혁');

INSERT INTO artists (artist_name)
VALUES ('TYRAN');
-- 아티스트 45번까지


-- 음원의 참여 아티스트 데이터 삽입
-- 아티스트 46번
INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('볼빨간사춘기2', '볼빨간사춘기아티스트사진.png', 'bol4_설명.txt');

-- 47
INSERT INTO artists (artist_name)
VALUES ('바닐라맨2');

INSERT INTO artists (artist_name)
VALUES ('황종하');
-- 아티스트 48번 까지

-- 박성준 artists 데이터 추가

-- 49
INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('아이유', '아이유.jpg', '아이유.txt');

-- 50
INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('볼빨간사춘기', '볼빨간사춘기.jpg', '볼빨간사춘기.txt');

-- 51
INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('AKMU (악뮤)', 'AKMU (악뮤).jpg', 'AKMU (악뮤).txt');

-- 52
INSERT INTO artists (artist_name)
VALUES ('이종훈');


-- 53
INSERT INTO artists (artist_name)
VALUES ('이채규');


-- 54
INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('적재', '적재.jpg', '적재.txt');

-- 55
INSERT INTO artists (artist_name)
VALUES ('홍소진');


-- 56
INSERT INTO artists (artist_name, artist_description)
VALUES ('제휘', '제휘.txt');

-- 57
INSERT INTO artists (artist_name, artist_image)
VALUES ('이민수', '이민수.jpg');

-- 58
INSERT INTO artists (artist_name)
VALUES ('김희원2');


-- 59
INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('바닐라맨', '바닐라맨.jpg', '바닐라맨.txt');

-- 60
INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('낯선아이', '낯선아이.jpg', '낯선아이.txt');

-- 61
INSERT INTO artists (artist_name)
VALUES ('로빈(ROVIN)');

-- 62
INSERT INTO artists (artist_name)
VALUES ('MILLENNIUM');

-- 63
INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('시황', '시황.jpg', '시황.txt');


-- 아티스트 역할코드 등록-----------------------------------------------------

-- What time! (Feat.DumbAss)
-- kenzi
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Kenzi'), 1, 10);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Kenzi'), 1, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Kenzi'), 1, 30);

-- DumbAss
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'DumbAss'), 1, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'DumbAss'), 1, 30);

-- GAXILLIC
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'GAXILLIC'), 1, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'GAXILLIC'), 1, 40);

-- 좋겠어 (Feat.HYUN SEO)
-- kenzi
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Kenzi'), 2, 10); 
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Kenzi'), 2, 20); 
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Kenzi'), 2, 30); 

-- 현서 (HYUN SEO)
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '현서 (HYUN SEO)'), 2, 20); 
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '현서 (HYUN SEO)'), 2, 30); 

-- Tmlm
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Tmlm'), 2, 20); 
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Tmlm'), 2, 40); 

-- Ice (Feat.PLHN)
-- kenzi
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Kenzi'), 3, 10); 
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Kenzi'), 3, 20); 
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Kenzi'), 3, 30); 

-- PLHN
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'PLHN'), 3, 20); 

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'PLHN'), 3, 30); 

-- Placcebo Beats
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Placcebo Beats'), 3, 20); 
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Placcebo Beats'), 3, 40); 

-- Promise (약속)
-- kenzi
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Kenzi'), 4, 10); 
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Kenzi'), 4, 20); 
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Kenzi'), 4, 30); 

-- Wolfgang Pander
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Wolfgang Pander'), 4, 20); 
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Wolfgang Pander'), 4, 40); 






-- 아티스트 역할번호 부여
-- 5번 라일락
-- 8번부터
-- 8번이 아이유
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 5, 10);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (9, 5, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (10, 5, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (11, 5, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (12, 5, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 5, 30);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (9, 5, 40);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (11, 5, 40);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (12, 5, 40);

-- 6번 Flu
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 6, 10);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Ryan S. Jhun'), 6, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (14, 6, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (15, 6, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (16, 6, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'London Jackson'), 6, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (18, 6, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (8, 6, 30);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (18, 6, 40);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'London Jackson'), 6, 40);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (13, 6, 40);

-- 7번 Coin
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 7, 10);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (21, 7, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (22, 7, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 7, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 7, 30);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (21, 7, 40);

-- 8번 봄 안녕 봄
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 8, 10);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (23, 8, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 8, 30);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (25, 8, 40);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (26, 8, 40);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (20, 8, 40);

-- 9번 Celebrity
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 9, 10);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'London Jackson'), 9, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (25, 9, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (26, 9, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 9, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (27, 9, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (28, 9, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 9, 30);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (25, 9, 40);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (26, 9, 40);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Ryan S. Jhun'), 9, 40);

-- 10번 돌림노래
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 10, 10);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (31, 10, 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (29, 10, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (30, 10, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (31, 10, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (32, 10, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 10, 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (29, 10, 40);

-- 11번 빈 컵
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 11, 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (33, 11, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (34, 11, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 11, 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (33, 11, 40);

-- 12번 아이와 나의 바다
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 12, 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (35, 12, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김희원'), 12, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 12, 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (35, 12, 40);

-- 13번 어푸
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 13, 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (37, 13, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (38, 13, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 13, 30);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (37, 13, 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (38, 13, 40);

-- 14번 에필로그
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 14, 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (39, 14, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (40, 14, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (41, 14, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (42, 14, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 14, 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (39, 14, 40);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (41, 14, 40);
-- 아티스트번호 42번 까지

-- 천천히가 역할 코드 등록
-- 천천히가 15번
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (1, 15, 10);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (43, 15, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (1, 15, 30);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (43, 15, 40);

-- 막달려가 16번
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (1, 16, 10);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (44, 16, 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (45, 16, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (1, 16, 30);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (44, 16, 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (45, 16, 40);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (1, 16, 40);

-- 볼사 Red Planet
-- 우주를 줄게 17번
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 17, 10);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 17, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '바닐라맨'), 17, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 17, 30);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '바닐라맨'), 17, 40);

-- 싸운날 18번
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 18, 10);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 18, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 18, 30);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '바닐라맨'), 18, 40);

-- You(=I) 19번
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 19, 10);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 19, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 19, 30);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '바닐라맨'), 19, 40);

-- 심술 20번
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 20, 10);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 20, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 20, 30);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '바닐라맨'), 20, 40);

-- 나만 안되는 연애 21번
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 21, 10);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 21, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 21, 30);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '바닐라맨'), 21, 40);

-- 초콜릿 22번
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 22, 10);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 22, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '바닐라맨'), 22, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 22, 30);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '바닐라맨'), 22, 40);

-- 프리지아 23번
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 23, 10);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 23, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 23, 30);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (48, 23, 40);

-- X Song 24번
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 24, 10);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '바닐라맨'), 24, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '바닐라맨'), 24, 30);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '바닐라맨'), 24, 40);

-- 반지 25번
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 25, 10);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 25, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 25, 30);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '바닐라맨'), 25, 40);

-- 사랑에 빠졌을 때 26번
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 26, 10);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 26, 20);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 26, 30);
INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '바닐라맨'), 26, 40);


-- artist_roles 데이터 추가(DB 통합할때 album_id, song_id 확인 및 설정 주의!)
INSERT INTO artist_roles (artist_id, song_id, role_id) -- Love poem 앨범 가수(아이유)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 27, 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 28, 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 29, 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 30, 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 31, 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 32, 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 33, 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 34, 10);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- Love poem 앨범 blueming 작곡(아이유, 이종훈, 이채규)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 27, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (52, 27, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (53, 27, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- Love poem 앨범 blueming 작사(아이유)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 27, 30);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- Love poem 앨범 blueming 편곡(이종훈, 이채규)
VALUES (52, 27, 40);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (53, 27, 40);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- Love poem 앨범 love poem 작곡(이종훈)
VALUES (52, 28, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- Love poem 앨범 love poem 작사(아이유)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 28, 30);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- Love poem 앨범 love poem 편곡(적재, 홍소진)
VALUES (54, 28, 40);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (55, 28, 40);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- Love poem 앨범 unlucky 작곡(제휘)
VALUES (56, 29, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- Love poem 앨범 unlucky 작사(아이유)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 29, 30);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- Love poem 앨범 unlucky 편곡(제휘)
VALUES (56, 29, 40);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- Love poem 앨범 그 사람 작곡(아이유)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 30, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- Love poem 앨범 그 사람 작사(아이유)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 30, 30);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- Love poem 앨범 그 사람 편곡(적재)
VALUES (54, 30, 40);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- Love poem 앨범 시간의 바깥 작곡(이민수)
VALUES (57, 31, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- Love poem 앨범 시간의 바깥 작사(아이유)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 31, 30);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- Love poem 앨범 시간의 바깥 편곡(이민수)
VALUES (57, 31, 40);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- Love poem 앨범 자장가 작곡(김희원)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김희원'), 32, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- Love poem 앨범 자장가 작사(아이유)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 32, 30);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- Love poem 앨범 자장가 편곡(홍소진)
VALUES (55, 32, 40);
----------------------------------------------------------------------------------------------------------------------------
INSERT INTO artist_roles (artist_id, song_id, role_id) -- 삐삐 앨범 삐삐 작곡(이종훈)
VALUES (52, 33, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- 삐삐 앨범 삐삐 작사(아이유)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 33, 30);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- 삐삐 앨범 삐삐 편곡(이채규)
VALUES (53, 33, 40);
----------------------------------------------------------------------------------------------------------------------------
INSERT INTO artist_roles (artist_id, song_id, role_id) -- 밤편지 앨범 밤편지 작곡(김희원, 제휘)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김희원'), 34, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (56, 34, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- 밤편지 앨범 밤편지 작사(아이유)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '아이유'), 34, 30);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- 밤편지 앨범 밤편지 편곡(김희원, 제휘)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김희원'), 34, 40);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (56, 34, 40);
----------------------------------------------------------------------------------------------------------------------------
INSERT INTO artist_roles (artist_id, song_id, role_id) -- 사춘기집Ⅰ 꽃기운 앨범 가수(볼빨간사춘기)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 35, 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 36, 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 37, 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 38, 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 39, 10);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- 사춘기집Ⅰ 꽃기운 앨범 별 보러 갈래 작곡(볼빨간사춘기, 바닐라맨)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 35, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '바닐라맨'), 35, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- 사춘기집Ⅰ 꽃기운 앨범 별 보러 갈래 작사(볼빨간사춘기)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 35, 30);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- 사춘기집Ⅰ 꽃기운 앨범 별 보러 갈래 편곡(바닐라맨)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '바닐라맨'), 35, 40);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- 사춘기집Ⅰ 꽃기운 앨범 나만 봄 작곡(볼빨간사춘기, 바닐라맨)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 36, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '바닐라맨'), 36, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- 사춘기집Ⅰ 꽃기운 앨범 나만 봄 작사(볼빨간사춘기)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 36, 30);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- 사춘기집Ⅰ 꽃기운 앨범 나만 봄 편곡(바닐라맨)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '바닐라맨'), 36, 40);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- 사춘기집Ⅰ 꽃기운 앨범 나들이 갈까 작곡(낯선아이)
VALUES (60, 37, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- 사춘기집Ⅰ 꽃기운 앨범 나들이 갈까 작사(낯선아이)
VALUES (60, 37, 30);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- 사춘기집Ⅰ 꽃기운 앨범 나들이 갈까 편곡(바닐라맨)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '바닐라맨'), 37, 40);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- 사춘기집Ⅰ 꽃기운 앨범 mermaid 작곡(볼빨간사춘기)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 38, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- 사춘기집Ⅰ 꽃기운 앨범 mermaid 작사(볼빨간사춘기)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 38, 30);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- 사춘기집Ⅰ 꽃기운 앨범 mermaid 편곡(바닐라맨)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 38, 40);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- 사춘기집Ⅰ 꽃기운 앨범 Seattle Alone 작곡(볼빨간사춘기, 바닐라맨)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 39, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '바닐라맨'), 39, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- 사춘기집Ⅰ 꽃기운 앨범 Seattle Alone 작사(볼빨간사춘기)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '볼빨간사춘기'), 39, 30);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- 사춘기집Ⅰ 꽃기운 앨범 Seattle Alone 편곡(바닐라맨)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '바닐라맨'), 39, 40);
----------------------------------------------------------------------------------------------------------------------------
INSERT INTO artist_roles (artist_id, song_id, role_id) -- SUMMER EPISODE 앨범 가수(AKMU악뮤)
VALUES (51, 40, 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (51, 41, 10);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- SUMMER EPISODE 앨범 DINOSAUR 작곡(AKMU악뮤)
VALUES (51, 40, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- SUMMER EPISODE 앨범 DINOSAUR 작사(AKMU악뮤)
VALUES (51, 40, 30);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- SUMMER EPISODE 앨범 DINOSAUR 편곡(로빈)
VALUES (61, 40, 40);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- SUMMER EPISODE 앨범 MY DARLING 작곡(AKMU악뮤)
VALUES (51, 41, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- SUMMER EPISODE 앨범 MY DARLING 작사(AKMU악뮤)
VALUES (51, 41, 30);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- SUMMER EPISODE 앨범 MY DARLING 편곡(로빈)
VALUES (61, 41, 40);
----------------------------------------------------------------------------------------------------------------------------
INSERT INTO artist_roles (artist_id, song_id, role_id) -- Love Lee 앨범 가수(AKMU악뮤)
VALUES (51, 42, 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (51, 43, 10);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- Love Lee 앨범 Love Lee 작곡(AKMU악뮤, MILLENNIUM, 시황)
VALUES (51, 42, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (62, 42, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (63, 42, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- Love Lee 앨범 Love Lee 작사(AKMU악뮤)
VALUES (51, 42, 30);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- Love Lee 앨범 후라이의 꿈 작곡(AKMU악뮤)
VALUES (51, 43, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES (61, 43, 20);

INSERT INTO artist_roles (artist_id, song_id, role_id) -- Love Lee 앨범 후라이의 꿈 작사(AKMU악뮤)
VALUES (51, 43, 30);



------------------------- 정윤정 데이터 (서브쿼리작성)
-- 앨범 데이터 삽입
INSERT INTO albums (album_name, album_image, album_type, album_release_date) VALUES ('밤양갱', '밤양갱_cover.jpg', 'EP', '2024-02-13');
INSERT INTO albums (album_name, album_image, album_type, album_release_date) VALUES ('말해주라', '말해주라_cover.jpg', 'Single', '2023-05-10');
INSERT INTO albums (album_name, album_image, album_type, album_release_date) VALUES ('소곡집 I', '잔나비 소곡집 I_cover.jpg', 'EP', '2020-11-06');
INSERT INTO albums (album_name, album_image, album_type, album_release_date) VALUES ('소곡집 II: 초록을 거머쥔 우리는', '잔나비 소곡집 ll.jpg', 'EP', '2022-05-10');
INSERT INTO albums (album_name, album_image, album_type, album_release_date) VALUES ('전설', '전설.jpg', '정규', '2022-10-14');

-- 아티스트 데이터 삽입

INSERT INTO artists (artist_name, artist_image, artist_description) VALUES ('BIBI', 'BIBI.jpg', 'BIBI.txt');
INSERT INTO artists (artist_name) VALUES ('장기하');
INSERT INTO artists (artist_name, artist_image, artist_description) VALUES ('소수빈', '소수빈.jpg', '소수빈.txt');
INSERT INTO artists (artist_name, artist_image, artist_description) VALUES ('잔나비', '잔나비.jpg', '잔나비.txt');
INSERT INTO artists (artist_name, artist_image, artist_description) VALUES ('최정훈', '최정훈.jpg', '최정훈.txt');
INSERT INTO artists (artist_name, artist_image, artist_description) VALUES ('김도형', '김도형.jpg', '김도형.txt');

-- 노래 데이터 삽입
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = '밤양갱'), '밤양갱', '밤양갱_밤양갱_BIBI.mp3', '밤양갱_밤양갱_lyrics.txt');

INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = '말해주라'), '말해주라', '말해주라_말해주라_소수빈.mp3', '말해주라_말해주라_lyrics.txt');

INSERT INTO songs (album_id, title, song_path, lyrics) 
VALUES ((SELECT album_id FROM albums WHERE album_name = '소곡집 I'), '가을밤에 든 생각', '잔나비 소곡집 I - 01. 가을밤에 든 생각.mp3', '잔나비 소곡집 I - 01. 가을밤에 든 생각.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) 
VALUES ((SELECT album_id FROM albums WHERE album_name = '소곡집 I'), '한걸음', '잔나비 소곡집 I - 02. 한걸음.mp3', '잔나비 소곡집 I - 02. 한걸음.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) 
VALUES ((SELECT album_id FROM albums WHERE album_name = '소곡집 I'), '그 밤 그 밤', '잔나비 소곡집 I - 03. 그 밤 그 밤.mp3', '잔나비 소곡집 I - 03. 그 밤 그 밤.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) 
VALUES ((SELECT album_id FROM albums WHERE album_name = '소곡집 I'), '늙은 개', '잔나비 소곡집 I - 04. 늙은 개.mp3', '잔나비 소곡집 I - 04. 늙은 개.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) 
VALUES ((SELECT album_id FROM albums WHERE album_name = '소곡집 I'), '작전명 청춘!', '잔나비 소곡집 I - 05. 작전명 청-춘!.mp3', '잔나비 소곡집 I - 05. 작전명 청-춘!.txt');

INSERT INTO songs (album_id, title, song_path, lyrics) 
VALUES ((SELECT album_id FROM albums WHERE album_name = '소곡집 II: 초록을 거머쥔 우리는'), '레이디버드', '레이디버드.mp3', '레이디버드.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) 
VALUES ((SELECT album_id FROM albums WHERE album_name = '소곡집 II: 초록을 거머쥔 우리는'), '초록을 거머쥔 우리는', '초록을거머쥔우리는.mp3', '초록을거머쥔우리는.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) 
VALUES ((SELECT album_id FROM albums WHERE album_name = '소곡집 II: 초록을 거머쥔 우리는'), '여름가을겨울 봄.', '여름가을겨울봄.mp3', '여름가을겨울봄.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) 
VALUES ((SELECT album_id FROM albums WHERE album_name = '소곡집 II: 초록을 거머쥔 우리는'), '슬픔이여안녕', '슬픔이여안녕.mp3', '슬픔이여안녕.txt');

INSERT INTO songs (album_id, title, song_path, lyrics) 
VALUES ((SELECT album_id FROM albums WHERE album_name = '전설'), '나의 기쁨 나의 노래 (Intro)', '나의 기쁨 나의 노래 (Good Good Night).mp3', '나의 기쁨 나의 노래 (Good Good Night) (Intro).txt');
INSERT INTO songs (album_id, title, song_path, lyrics) 
VALUES ((SELECT album_id FROM albums WHERE album_name = '전설'), '투게더!', '투게더! (TOGETHER!).mp3', '투게더! (TOGETHER!).txt');
INSERT INTO songs (album_id, title, song_path, lyrics) 
VALUES ((SELECT album_id FROM albums WHERE album_name = '전설'), '조이풀 조이풀', '조이풀 조이풀 (joyful joyful).mp3', '조이풀 조이풀 (joyful joyful).txt');
INSERT INTO songs (album_id, title, song_path, lyrics) 
VALUES ((SELECT album_id FROM albums WHERE album_name = '전설'), '거울', '거울 (mirror).mp3', '거울 (mirror).txt');
INSERT INTO songs (album_id, title, song_path, lyrics) 
VALUES ((SELECT album_id FROM albums WHERE album_name = '전설'), '우리 애는요', '우리 애는요 (about a boy).mp3', '우리 애는요 (about a boy).txt');
INSERT INTO songs (album_id, title, song_path, lyrics) 
VALUES ((SELECT album_id FROM albums WHERE album_name = '전설'), 'DOLMARO', 'DOLMARO.mp3', 'DOLMARO.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) 
VALUES ((SELECT album_id FROM albums WHERE album_name = '전설'), '전설', '전설 (LEGEND).mp3', '전설 (LEGEND).txt');
INSERT INTO songs (album_id, title, song_path, lyrics) 
VALUES ((SELECT album_id FROM albums WHERE album_name = '전설'), '주저하는 연인들을 위해', '주저하는 연인들을 위해.mp3', '주저하는 연인들을 위해.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) 
VALUES ((SELECT album_id FROM albums WHERE album_name = '전설'), '신나는 잠', '신나는 잠 (geum ui hwan hyang).mp3', '신나는 잠 (geum ui hwan hyang).txt');
INSERT INTO songs (album_id, title, song_path, lyrics) 
VALUES ((SELECT album_id FROM albums WHERE album_name = '전설'), '나쁜 꿈', '전설 - 10. 나쁜 꿈.mp3', '전설 - 10. 나쁜 꿈.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) 
VALUES ((SELECT album_id FROM albums WHERE album_name = '전설'), '새 어둠 새 눈', '새 어둠 새 눈 (land of night).mp3', '새 어둠 새 눈 (land of night).txt');
INSERT INTO songs (album_id, title, song_path, lyrics) 
VALUES ((SELECT album_id FROM albums WHERE album_name = '전설'), '꿈과 책과 힘과 벽', '꿈과 책과 힘과 벽.mp3', '꿈과 책과 힘과 벽.txt');

-- 곡 장르 정보 삽입
-- 비비
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='밤양갱'), 20);

-- 소수빈
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='말해주라'), 10);

-- 잔나비 1집
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='가을밤에 든 생각'), 80); -- 인디음악
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='한걸음'), 80);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='그 밤 그 밤'), 80);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='늙은 개'), 80);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='작전명 청춘!'), 80);

-- 잔나비 2집
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='레이디버드'), 80); -- 인디음악
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='초록을 거머쥔 우리는'), 80);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='여름가을겨울 봄.'), 80);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='슬픔이여안녕'), 80);

-- 잔나비 전설
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='나의 기쁨 나의 노래 (Intro)'), 80); -- 인디음악
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='투게더!'), 80);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='조이풀 조이풀'), 80);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='거울'), 80);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='우리 애는요'), 80);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='DOLMARO'), 80);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='전설'), 80);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='주저하는 연인들을 위해'), 80);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='신나는 잠'), 80);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='나쁜 꿈'), 80);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='새 어둠 새 눈'), 80);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='꿈과 책과 힘과 벽'), 80);

-- 타이틀 곡 정보 삽입
INSERT INTO title_songs (album_id, song_id) VALUES ((SELECT album_id FROM albums WHERE album_name = '밤양갱'), (SELECT song_id FROM songs WHERE title='밤양갱')); -- 밤양갱 앨범의 타이틀 곡
INSERT INTO title_songs (album_id, song_id) VALUES ((SELECT album_id FROM albums WHERE album_name = '말해주라'), (SELECT song_id FROM songs WHERE title='말해주라')); -- 말해주라 앨범의 타이틀 곡
INSERT INTO title_songs (album_id, song_id) VALUES ((SELECT album_id FROM albums WHERE album_name = '소곡집 I'), (SELECT song_id FROM songs WHERE title= '가을밤에 든 생각')); -- 소곡집 I 앨범의 타이틀 곡
INSERT INTO title_songs (album_id, song_id) VALUES ((SELECT album_id FROM albums WHERE album_name = '소곡집 II: 초록을 거머쥔 우리는'), (SELECT song_id FROM songs WHERE title= '초록을 거머쥔 우리는')); -- 소곡집 II: 초록을 거머쥔 우리는 앨범의 타이틀 곡
INSERT INTO title_songs (album_id, song_id) VALUES ((SELECT album_id FROM albums WHERE album_name = '전설'), (SELECT song_id FROM songs WHERE title= '주저하는 연인들을 위해')); -- 전설 앨범의 타이틀 곡

-- 아티스트 역할 코드 등록

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'BIBI'), (SELECT song_id FROM songs WHERE title='밤양갱'), 10); -- BIBI 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '장기하'), (SELECT song_id FROM songs WHERE title='밤양갱'), 20); -- 장기하 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '장기하'), (SELECT song_id FROM songs WHERE title='밤양갱'), 30); -- 장기하 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '장기하'), (SELECT song_id FROM songs WHERE title='밤양갱'), 40); -- 장기하 편곡가

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '소수빈'), (SELECT song_id FROM songs WHERE title='말해주라'), 10); -- 소수빈 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '소수빈'), (SELECT song_id FROM songs WHERE title='말해주라'), 20); -- 소수빈 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '소수빈'), (SELECT song_id FROM songs WHERE title='말해주라'), 30); -- 소수빈 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '소수빈'), (SELECT song_id FROM songs WHERE title='말해주라'), 40); -- 소수빈 편곡가


 -- 11 잔나비 가수 (SELECT artist_id FROM artists WHERE artist_name = '잔나비')
 -- 12 최정훈 작사 (SELECT artist_id FROM artists WHERE artist_name = '최정훈')
 -- 13 김도형 작곡 (SELECT artist_id FROM artists WHERE artist_name = '김도형')
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '잔나비'), (SELECT song_id FROM songs WHERE title='가을밤에 든 생각'), 10);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='가을밤에 든 생각'), 30); 
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='가을밤에 든 생각'), 20); 
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김도형'), (SELECT song_id FROM songs WHERE title='가을밤에 든 생각'), 20); 
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='가을밤에 든 생각'), 40);

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '잔나비'), (SELECT song_id FROM songs WHERE title='한걸음'), 10);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='한걸음'), 30);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='한걸음'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김도형'), (SELECT song_id FROM songs WHERE title='한걸음'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='한걸음'), 40);

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '잔나비'), (SELECT song_id FROM songs WHERE title='그 밤 그 밤'), 10);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='그 밤 그 밤'), 30);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='그 밤 그 밤'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김도형'), (SELECT song_id FROM songs WHERE title='그 밤 그 밤'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김도형'), (SELECT song_id FROM songs WHERE title='그 밤 그 밤'), 40); 

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '잔나비'), (SELECT song_id FROM songs WHERE title='늙은 개'), 10);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='늙은 개'), 30);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='늙은 개'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김도형'), (SELECT song_id FROM songs WHERE title='늙은 개'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김도형'), (SELECT song_id FROM songs WHERE title='늙은 개'), 40);

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '잔나비'), (SELECT song_id FROM songs WHERE title='작전명 청춘!'), 10);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='작전명 청춘!'), 30);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='작전명 청춘!'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김도형'), (SELECT song_id FROM songs WHERE title='작전명 청춘!'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김도형'), (SELECT song_id FROM songs WHERE title='작전명 청춘!'), 40);

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '잔나비'), (SELECT song_id FROM songs WHERE title='레이디버드'), 10);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='레이디버드'), 30);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='레이디버드'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김도형'), (SELECT song_id FROM songs WHERE title='레이디버드'), 20); 
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='레이디버드'), 40); 

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '잔나비'), (SELECT song_id FROM songs WHERE title='초록을 거머쥔 우리는'), 10);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='초록을 거머쥔 우리는'), 30);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='초록을 거머쥔 우리는'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김도형'), (SELECT song_id FROM songs WHERE title='초록을 거머쥔 우리는'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='초록을 거머쥔 우리는'), 40);

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '잔나비'), (SELECT song_id FROM songs WHERE title='여름가을겨울 봄.'), 10);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='여름가을겨울 봄.'), 30);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='여름가을겨울 봄.'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김도형'), (SELECT song_id FROM songs WHERE title='여름가을겨울 봄.'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='여름가을겨울 봄.'), 40);

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '잔나비'), (SELECT song_id FROM songs WHERE title='슬픔이여안녕'), 10);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='슬픔이여안녕'), 30);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='슬픔이여안녕'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김도형'), (SELECT song_id FROM songs WHERE title='슬픔이여안녕'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='슬픔이여안녕'), 40);


INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '잔나비'), (SELECT song_id FROM songs WHERE title='나의 기쁨 나의 노래 (Intro)'), 10); 
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='나의 기쁨 나의 노래 (Intro)'), 30);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='나의 기쁨 나의 노래 (Intro)'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김도형'), (SELECT song_id FROM songs WHERE title='나의 기쁨 나의 노래 (Intro)'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='나의 기쁨 나의 노래 (Intro)'), 40);

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '잔나비'), (SELECT song_id FROM songs WHERE title='투게더!'), 10);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='투게더!'), 30);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='투게더!'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김도형'), (SELECT song_id FROM songs WHERE title='투게더!'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='투게더!'), 40);

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '잔나비'), (SELECT song_id FROM songs WHERE title='조이풀 조이풀'), 10);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='조이풀 조이풀'), 30);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='조이풀 조이풀'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김도형'), (SELECT song_id FROM songs WHERE title='조이풀 조이풀'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='조이풀 조이풀'), 40);

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '잔나비'), (SELECT song_id FROM songs WHERE title='거울'), 10);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='거울'), 30);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='거울'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김도형'), (SELECT song_id FROM songs WHERE title='거울'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='거울'), 40);

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '잔나비'), (SELECT song_id FROM songs WHERE title='우리 애는요'), 10);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='우리 애는요'), 30);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='우리 애는요'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김도형'), (SELECT song_id FROM songs WHERE title='우리 애는요'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='우리 애는요'), 40);

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '잔나비'), (SELECT song_id FROM songs WHERE title='DOLMARO'), 10);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='DOLMARO'), 30);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='DOLMARO'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김도형'), (SELECT song_id FROM songs WHERE title='DOLMARO'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='DOLMARO'), 40);

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '잔나비'), (SELECT song_id FROM songs WHERE title='전설'), 10);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='전설'), 30);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='전설'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김도형'), (SELECT song_id FROM songs WHERE title='전설'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='전설'), 40);

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '잔나비'), (SELECT song_id FROM songs WHERE title='주저하는 연인들을 위해'), 10);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='주저하는 연인들을 위해'), 30);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='주저하는 연인들을 위해'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김도형'), (SELECT song_id FROM songs WHERE title='주저하는 연인들을 위해'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='주저하는 연인들을 위해'), 40);

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '잔나비'), (SELECT song_id FROM songs WHERE title='신나는 잠'), 10);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='신나는 잠'), 30);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='신나는 잠'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김도형'), (SELECT song_id FROM songs WHERE title='신나는 잠'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='신나는 잠'), 40);

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '잔나비'), (SELECT song_id FROM songs WHERE title='나쁜 꿈'), 10);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='나쁜 꿈'), 30);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='나쁜 꿈'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김도형'), (SELECT song_id FROM songs WHERE title='나쁜 꿈'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='나쁜 꿈'), 40);

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '잔나비'), (SELECT song_id FROM songs WHERE title='새 어둠 새 눈'), 10);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='새 어둠 새 눈'), 30);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='새 어둠 새 눈'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김도형'), (SELECT song_id FROM songs WHERE title='새 어둠 새 눈'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='새 어둠 새 눈'), 40);

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '잔나비'), (SELECT song_id FROM songs WHERE title='꿈과 책과 힘과 벽'), 10);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='꿈과 책과 힘과 벽'), 30);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='꿈과 책과 힘과 벽'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '김도형'), (SELECT song_id FROM songs WHERE title='꿈과 책과 힘과 벽'), 20);
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '최정훈'), (SELECT song_id FROM songs WHERE title='꿈과 책과 힘과 벽'), 40);

-- 뉴진스 노래 데이터 삽입

-- 앨범 정보 삽입
INSERT INTO albums (album_name, album_image, album_type, album_release_date) VALUES ('GET UP', 'GET_UP_cover.jpg', 'EP', '2023-07-21');

-- 새로 생성된 album_id를 4로 가정

-- 곡 정보 삽입
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'GET UP'), 'New Jeans', 'NewJeans - New Jeans.mp3', 'NewJeans - New Jeans.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'GET UP'), 'Super Shy', 'NewJeans - Super Shy.mp3', 'NewJeans - Super Shy.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'GET UP'), 'ETA', 'NewJeans - ETA.mp3', 'NewJeans - ETA.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'GET UP'), 'Cool With You', 'NewJeans - Cool With You.mp3', 'NewJeans - Cool With You.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'GET UP'), 'Get Up', 'NewJeans - Get Up.mp3', 'NewJeans - Get Up.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'GET UP'), 'ASAP', 'NewJeans - ASAP.mp3', 'NewJeans - ASAP.txt');

-- 타이틀 곡 정보 삽입
-- NewJeans 뉴진스	GET UP
INSERT INTO title_songs (album_id, song_id) VALUES ((SELECT album_id FROM albums WHERE album_name = 'GET UP'), (SELECT song_id FROM songs WHERE title='Super Shy'));
INSERT INTO title_songs (album_id, song_id) VALUES ((SELECT album_id FROM albums WHERE album_name = 'GET UP'), (SELECT song_id FROM songs WHERE title='ETA'));
INSERT INTO title_songs (album_id, song_id) VALUES ((SELECT album_id FROM albums WHERE album_name = 'GET UP'), (SELECT song_id FROM songs WHERE title='Cool With You'));


-- 새로 생성된 song_id를 7로 가정

-- 곡 장르 정보 삽입
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='New Jeans'), 30);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='Super Shy'), 30);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='ETA'), 30);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='Cool With You'), 30);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='Get Up'), 30);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='ASAP'), 30);

-- 아티스트 정보 삽입 (뉴진스와 작곡가, 작사가, 편곡가 추가)
INSERT INTO artists (artist_name, artist_image, artist_description) VALUES ('NewJeans', 'NewJeans.jpg', 'NewJeans.txt');
INSERT INTO artists (artist_name) VALUES ('FRNK');
INSERT INTO artists (artist_name) VALUES ('Gigi');
INSERT INTO artists (artist_name) VALUES ('FrankieScoca');
INSERT INTO artists (artist_name) VALUES ('250');
INSERT INTO artists (artist_name) VALUES ('임성빈');
INSERT INTO artists (artist_name) VALUES ('freekind');



-- 새로 생성된 artist_id가 11 NewJeans, 12 FRNK, 13 Gigi, 14 FrankieScoca, 15 250, 16 임성빈, 17 freekind


-- 아티스트 역할 정보 삽입
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'NewJeans'), (SELECT song_id FROM songs WHERE title='New Jeans'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'FRNK'), (SELECT song_id FROM songs WHERE title='New Jeans'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Gigi'), (SELECT song_id FROM songs WHERE title='New Jeans'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'FrankieScoca'), (SELECT song_id FROM songs WHERE title='New Jeans'), 40); -- 편곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'NewJeans'), (SELECT song_id FROM songs WHERE title='Super Shy'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'FrankieScoca'), (SELECT song_id FROM songs WHERE title='Super Shy'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Gigi'), (SELECT song_id FROM songs WHERE title='Super Shy'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'FrankieScoca'), (SELECT song_id FROM songs WHERE title='Super Shy'), 40); -- 편곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'NewJeans'), (SELECT song_id FROM songs WHERE title='ETA'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '250'), (SELECT song_id FROM songs WHERE title='ETA'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '임성빈'), (SELECT song_id FROM songs WHERE title='ETA'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '250'), (SELECT song_id FROM songs WHERE title='ETA'), 40); -- 편곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'NewJeans'), (SELECT song_id FROM songs WHERE title='Cool With You'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'FRNK'), (SELECT song_id FROM songs WHERE title='Cool With You'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Gigi'), (SELECT song_id FROM songs WHERE title='Cool With You'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'FRNK'), (SELECT song_id FROM songs WHERE title='Cool With You'), 40); -- 편곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'NewJeans'), (SELECT song_id FROM songs WHERE title='Get Up'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '250'), (SELECT song_id FROM songs WHERE title='Get Up'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'freekind'), (SELECT song_id FROM songs WHERE title='Get Up'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '250'), (SELECT song_id FROM songs WHERE title='Get Up'), 40); -- 편곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'NewJeans'), (SELECT song_id FROM songs WHERE title='ASAP'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '250'), (SELECT song_id FROM songs WHERE title='ASAP'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Gigi'), (SELECT song_id FROM songs WHERE title='ASAP'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '250'), (SELECT song_id FROM songs WHERE title='ASAP'), 40); -- 편곡가




-- 키오라 노래 데이터 삽입

-- 앨범 정보 삽입
-- [싱글] 댄스,알앤비
-- 수록곡 2 midas touch-댄스, nothing-알앤비



INSERT INTO albums (album_name, album_image, album_type, album_release_date) VALUES ('midas touch', 'midas touch.jpg', '싱글', '2024-04-03');

-- 새로 생성된 album_id를 101로 가정

-- 곡 정보 삽입
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'midas touch'), 'midas touch', 'kiss of life - midas touch.mp3', 'kiss of life - midas touch.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'midas touch'), 'nothing', 'kiss of life - nothing.mp3', 'kiss of life - nothing.txt');

-- 타이틀 곡 정보 삽입

-- kissOfLife 키스오브라이프	midas touch
INSERT INTO title_songs (album_id, song_id) VALUES ((SELECT album_id FROM albums WHERE album_name = 'midas touch'), (SELECT song_id FROM songs WHERE title='midas touch'));



-- 새로 생성된 song_id를 101부터 가정
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='midas touch'), 30);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='nothing'), 40);

-- 아티스트 정보 삽입 (키오라와 작사가, 작곡가, 편곡가 추가)
INSERT INTO artists (artist_name, artist_image, artist_description) VALUES ('kissOfLife', 'kissOfLife.jpg', 'kissOfLife.txt');
INSERT INTO artists (artist_name) VALUES ('미아');
INSERT INTO artists (artist_name) VALUES ('조윤경');
INSERT INTO artists (artist_name) VALUES ('Strawberrybananaclub');
INSERT INTO artists (artist_name) VALUES ('Ayushy (THE HUB)');
INSERT INTO artists (artist_name) VALUES ('P.K (NU_BOUNCE)');




-- 새로 생성된 artist_id가 101 kissOfLife, 102 미아, 103 조윤경, 104 Strawberrybananaclub, 105 Ayushy (THE HUB), 106 P.K (NU_BOUNCE)

-- 아티스트 역할 정보 삽입 songId 101부터 102
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'kissOfLife'), (SELECT song_id FROM songs WHERE title='midas touch'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '미아'), (SELECT song_id FROM songs WHERE title='midas touch'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Strawberrybananaclub'), (SELECT song_id FROM songs WHERE title='midas touch'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Strawberrybananaclub'), (SELECT song_id FROM songs WHERE title='midas touch'), 40); -- 편곡가

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'kissOfLife'), (SELECT song_id FROM songs WHERE title='nothing'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Ayushy (THE HUB)'), (SELECT song_id FROM songs WHERE title='nothing'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'P.K (NU_BOUNCE)'), (SELECT song_id FROM songs WHERE title='nothing'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'P.K (NU_BOUNCE)'), (SELECT song_id FROM songs WHERE title='nothing'), 40); -- 편곡가




-- 베이비몬스터 노래 데이터 삽입
-- 앨범 정보 삽입
-- [EP] 발라드,댄스,알앤비,힙합
-- 수록곡 7 monsters_intro-댄스, sheesh-알앤비, like that-알앤비, stuck in the middle-발라드, batter up-힙합, dream-발라드, stuck in the middle_remix-댄스

INSERT INTO albums (album_name, album_image, album_type, album_release_date) VALUES ('babymons7er', 'babymons7er.jpg', 'EP', '2024-04-01');

-- 새로 생성된 album_id를 102로 가정

-- 곡 정보 삽입
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'babymons7er'), 'sheesh', 'babymonster - sheesh.mp3', 'babymonster - sheesh.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'babymons7er'), 'batter up', 'babymonster - batter up.mp3', 'babymonster - batter up.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'babymons7er'), 'dream', 'babymonster - dream.mp3', 'babymonster - dream.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'babymons7er'), 'like that', 'babymonster - like that.mp3', 'babymonster - like that.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'babymons7er'), 'monsters_intro', 'babymonster - monsters_intro.mp3', 'babymonster - monsters_intro.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'babymons7er'), 'stuck in the middle', 'babymonster - stuck in the middle.mp3', 'babymonster - stuck in the middle.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'babymons7er'), 'stuck in the middle_remix', 'babymonster - stuck in the middle_remix.mp3', 'babymonster - stuck in the middle_remix.txt');


-- 새로 생성된 song_id를 103부터 가정
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='sheesh'), 40);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='batter up'), 50);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='dream'), 10);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='like that'), 40);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='monsters_intro'), 30);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='stuck in the middle'), 10);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='stuck in the middle_remix'), 30);

-- 타이틀 곡 정보 삽입

-- babymonster 베이비몬스터	babymons7er
INSERT INTO title_songs (album_id, song_id) VALUES ((SELECT album_id FROM albums WHERE album_name = 'babymons7er'), (SELECT song_id FROM songs WHERE title='sheesh'));

-- 아티스트 정보 삽입 (베이비몬스터와 작사가 작곡가 편곡가)
INSERT INTO artists (artist_name, artist_image, artist_description) VALUES ('babymonster', 'babymonster.jpg', 'babymonster.txt');
INSERT INTO artists (artist_name) VALUES ('Jared Lee');
INSERT INTO artists (artist_name) VALUES ('ICE');
INSERT INTO artists (artist_name) VALUES ('DEE.P');
INSERT INTO artists (artist_name) VALUES ('CHOICE37');
INSERT INTO artists (artist_name) VALUES ('LIL G');
INSERT INTO artists (artist_name, artist_image, artist_description) VALUES ('Charlie Puth', 'Charlie Puth.jpg', 'Charlie Puth.txt');
INSERT INTO artists (artist_name) VALUES ('Dan Whittemore');

-- 새로 생성된 artist_id가  107 babymonster, 108 Jared Lee, 109 ICE, 110 DEE.P, 111 CHOICE37, 112 LIL G, 113 Charlie Puth, 114 Dan Whittemore


-- 아티스트 역할 정보 삽입 songId 103부터 109
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'babymonster'), (SELECT song_id FROM songs WHERE title='sheesh'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'CHOICE37'), (SELECT song_id FROM songs WHERE title='sheesh'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'LIL G'), (SELECT song_id FROM songs WHERE title='sheesh'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'DEE.P'), (SELECT song_id FROM songs WHERE title='sheesh'), 40); -- 편곡가

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'babymonster'), (SELECT song_id FROM songs WHERE title='batter up'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Jared Lee'), (SELECT song_id FROM songs WHERE title='batter up'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'DEE.P'), (SELECT song_id FROM songs WHERE title='batter up'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'DEE.P'), (SELECT song_id FROM songs WHERE title='batter up'), 40); -- 편곡가

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'babymonster'), (SELECT song_id FROM songs WHERE title='dream'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Jared Lee'), (SELECT song_id FROM songs WHERE title='dream'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'LIL G'), (SELECT song_id FROM songs WHERE title='dream'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Dan Whittemore'), (SELECT song_id FROM songs WHERE title='dream'), 40); -- 편곡가

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'babymonster'), (SELECT song_id FROM songs WHERE title='like that'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Charlie Puth'), (SELECT song_id FROM songs WHERE title='like that'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Charlie Puth'), (SELECT song_id FROM songs WHERE title='like that'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Charlie Puth'), (SELECT song_id FROM songs WHERE title='like that'), 40); -- 편곡가

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'babymonster'), (SELECT song_id FROM songs WHERE title='monsters_intro'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Jared Lee'), (SELECT song_id FROM songs WHERE title='monsters_intro'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'ICE'), (SELECT song_id FROM songs WHERE title='monsters_intro'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'DEE.P'), (SELECT song_id FROM songs WHERE title='monsters_intro'), 40); -- 편곡가

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'babymonster'), (SELECT song_id FROM songs WHERE title='stuck in the middle'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Jared Lee'), (SELECT song_id FROM songs WHERE title='stuck in the middle'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'DEE.P'), (SELECT song_id FROM songs WHERE title='stuck in the middle'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'DEE.P'), (SELECT song_id FROM songs WHERE title='stuck in the middle'), 40); -- 편곡가

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'babymonster'), (SELECT song_id FROM songs WHERE title='stuck in the middle_remix'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Jared Lee'), (SELECT song_id FROM songs WHERE title='stuck in the middle_remix'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'DEE.P'), (SELECT song_id FROM songs WHERE title='stuck in the middle_remix'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Dan Whittemore'), (SELECT song_id FROM songs WHERE title='stuck in the middle_remix'), 40); -- 편곡가



-- 아일릿 노래 데이터 삽입
-- 앨범 정보 삽입
-- [EP] 댄스
-- 수록곡4 magnetic, my world, midnight fiction, lucky girl syndrome

INSERT INTO albums (album_name, album_image, album_type, album_release_date) VALUES ('super real me', 'super real me.jpg', 'EP', '2024-03-25');

-- 새로 생성된 album_id를 103로 가정

INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'super real me'), 'magnetic', 'illit - magnetic.mp3', 'illit - magnetic.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'super real me'), 'my world', 'illit - my world.mp3', 'illit - my world.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'super real me'), 'midnight fiction', 'illit - midnight fiction.mp3', 'illit - midnight fiction.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'super real me'), 'lucky girl syndrome', 'illit - lucky girl syndrome.mp3', 'illit - lucky girl syndrome.txt');

-- 새로 생성된 song_id를 110부터 가정
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='magnetic'), 30);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='my world'), 30);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='midnight fiction'), 30);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='lucky girl syndrome'), 30);

-- 타이틀 곡 정보 삽입

-- illit 아일릿 	super real me
INSERT INTO title_songs (album_id, song_id) VALUES ((SELECT album_id FROM albums WHERE album_name = 'super real me'), (SELECT song_id FROM songs WHERE title='magnetic'));


-- 아티스트 정보 삽입 (아일릿과 작사가 작곡가 편곡가)
INSERT INTO artists (artist_name, artist_image, artist_description) VALUES ('illit', 'illit.jpg', 'illit.txt');
INSERT INTO artists (artist_name) VALUES ('Albin Tengblad');
INSERT INTO artists (artist_name) VALUES ('Lara Andersson');
INSERT INTO artists (artist_name) VALUES ('Slow Rabbit');
INSERT INTO artists (artist_name) VALUES ('salem ilese');
INSERT INTO artists (artist_name) VALUES ('노주환');
INSERT INTO artists (artist_name) VALUES ('Stint');
INSERT INTO artists (artist_name) VALUES ('Alna Hofmeyr');

-- 새로 생성된 artist_id가  115 illit, 116 Albin Tengblad, 117 Lara Andersson, 118 Slow Rabbit, 119 salem ilese, 120 노주환, 121 Stint, 122 Alna Hofmeyr





-- 아티스트 역할 정보 삽입 songId 110부터 113
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'illit'), (SELECT song_id FROM songs WHERE title='magnetic'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Slow Rabbit'), (SELECT song_id FROM songs WHERE title='magnetic'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'salem ilese'), (SELECT song_id FROM songs WHERE title='magnetic'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'salem ilese'), (SELECT song_id FROM songs WHERE title='magnetic'), 40); -- 편곡가

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'illit'), (SELECT song_id FROM songs WHERE title='my world'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Albin Tengblad'), (SELECT song_id FROM songs WHERE title='my world'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Lara Andersson'), (SELECT song_id FROM songs WHERE title='my world'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Lara Andersson'), (SELECT song_id FROM songs WHERE title='my world'), 40); -- 편곡가

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'illit'), (SELECT song_id FROM songs WHERE title='midnight fiction'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '노주환'), (SELECT song_id FROM songs WHERE title='midnight fiction'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '노주환'), (SELECT song_id FROM songs WHERE title='midnight fiction'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '노주환'), (SELECT song_id FROM songs WHERE title='midnight fiction'), 40); -- 편곡가

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'illit'), (SELECT song_id FROM songs WHERE title='lucky girl syndrome'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Stint'), (SELECT song_id FROM songs WHERE title='lucky girl syndrome'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Alna Hofmeyr'), (SELECT song_id FROM songs WHERE title='lucky girl syndrome'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Alna Hofmeyr'), (SELECT song_id FROM songs WHERE title='lucky girl syndrome'), 40); -- 편곡가



-- 비비지 노래 데이터 삽입
-- 앨범 정보 삽입
-- [EP] 알앤비,댄스
-- 수록곡5 maniac, untie, overflow, 한걸음, up 2 me

INSERT INTO albums (album_name, album_image, album_type, album_release_date) VALUES ('versus', 'versus.jpg', 'EP', '2023-11-02');

-- 새로 생성된 album_id를 104로 가정
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'versus'), 'maniac', 'viviz - maniac.mp3', 'viviz - maniac.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'versus'), 'untie', 'viviz - untie.mp3', 'viviz - untie.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'versus'), 'overflow', 'viviz - overflow.mp3', 'viviz - overflow.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'versus'), 'viviz - 한걸음', 'viviz - 한걸음.mp3', 'viviz - 한걸음.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'versus'), 'up 2 me', 'viviz - up 2 me.mp3', 'viviz - up 2 me.txt');

-- 새로 생성된 song_id를 114부터118
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='maniac'), 30);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='untie'), 30);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='overflow'), 30);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='viviz - 한걸음'), 30);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='up 2 me'), 30);

-- 타이틀 곡 정보 삽입

-- viviz 비비지		versus
INSERT INTO title_songs (album_id, song_id) VALUES ((SELECT album_id FROM albums WHERE album_name = 'versus'), (SELECT song_id FROM songs WHERE title='maniac'));


-- 아티스트 정보 삽입 (비비지와 작사가 작곡가 편곡가)
INSERT INTO artists (artist_name, artist_image, artist_description) VALUES ('viviz', 'viviz.jpg', 'viviz.txt');
INSERT INTO artists (artist_name) VALUES ('구여름');
INSERT INTO artists (artist_name) VALUES ('Jack Brady');
INSERT INTO artists (artist_name) VALUES ('The Wavys');
INSERT INTO artists (artist_name) VALUES ('Ellie Suh');
INSERT INTO artists (artist_name) VALUES ('라이언 전');
INSERT INTO artists (artist_name) VALUES ('BYMORE');
INSERT INTO artists (artist_name) VALUES ('정하리');
INSERT INTO artists (artist_name) VALUES ('Freek Mulder');
INSERT INTO artists (artist_name) VALUES ('danke');
INSERT INTO artists (artist_name) VALUES ('YOUNGWOO');

-- 새로 생성된 artist_id가  123 viviz, 124 구여름, 125 Jack Brady, 126 The Wavys, 127 Ellie Suh, 128 라이언 전, 129 BYMORE, 130 정하리, 131 Freek Mulder, 132 danke, 133 YOUNGWOO


-- 아티스트 역할 정보 삽입 songId 114부터 118
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'viviz'), (SELECT song_id FROM songs WHERE title='maniac'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '구여름'), (SELECT song_id FROM songs WHERE title='maniac'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Jack Brady'), (SELECT song_id FROM songs WHERE title='maniac'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'The Wavys'), (SELECT song_id FROM songs WHERE title='maniac'), 40); -- 편곡가

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'viviz'), (SELECT song_id FROM songs WHERE title='untie'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Ellie Suh'), (SELECT song_id FROM songs WHERE title='untie'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '라이언 전'), (SELECT song_id FROM songs WHERE title='untie'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '라이언 전'), (SELECT song_id FROM songs WHERE title='untie'), 40); -- 편곡가

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'viviz'), (SELECT song_id FROM songs WHERE title='overflow'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'BYMORE'), (SELECT song_id FROM songs WHERE title='overflow'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'BYMORE'), (SELECT song_id FROM songs WHERE title='overflow'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'BYMORE'), (SELECT song_id FROM songs WHERE title='overflow'), 40); -- 편곡가

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'viviz'), (SELECT song_id FROM songs WHERE title='viviz - 한걸음'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '정하리'), (SELECT song_id FROM songs WHERE title='viviz - 한걸음'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Freek Mulder'), (SELECT song_id FROM songs WHERE title='viviz - 한걸음'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Freek Mulder'), (SELECT song_id FROM songs WHERE title='viviz - 한걸음'), 40); -- 편곡가

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'viviz'), (SELECT song_id FROM songs WHERE title='up 2 me'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'danke'), (SELECT song_id FROM songs WHERE title='up 2 me'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'YOUNGWOO'), (SELECT song_id FROM songs WHERE title='up 2 me'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'YOUNGWOO'), (SELECT song_id FROM songs WHERE title='up 2 me'), 40); -- 편곡가



-- 에스파 노래 데이터 삽입
-- 앨범 정보 삽입
-- [정규] 댄스,발라드,알앤비
-- 수록곡10 supernova, amargeddon, set the tone, mine, licorice, bahama, long chat, prologue, live my life, 목소리

INSERT INTO albums (album_name, album_image, album_type, album_release_date) VALUES ('armageddon', 'armageddon.jpg', '정규', '2024-05-27');

-- 새로 생성된 album_id를 105로 가정
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'armageddon'), 'supernova', 'aespa - supernova.mp3', 'aespa - supernova.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'armageddon'), 'armageddon', 'aespa - armageddon.mp3', 'aespa - armageddon.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'armageddon'), 'set the tone', 'aespa - set the tone.mp3', 'viviz - set the tone.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'armageddon'), 'mine', 'aespa - mine.mp3', 'aespa - mine.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'armageddon'), 'licorice', 'aespa - licorice.mp3', 'aespa - licorice.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'armageddon'), 'bahama', 'aespa - bahama.mp3', 'aespa - bahama.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'armageddon'), 'long chat', 'aespa - long chat.mp3', 'aespa - long chat.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'armageddon'), 'prologue', 'aespa - prologue.mp3', 'aespa - prologue.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'armageddon'), 'live my life', 'aespa - live my life.mp3', 'aespa - live my life.txt');
INSERT INTO songs (album_id, title, song_path, lyrics) VALUES ((SELECT album_id FROM albums WHERE album_name = 'armageddon'), '목소리', 'aespa - 목소리.mp3', 'aespa - 목소리.txt');

-- 타이틀 곡 정보 삽입

-- aespa 에스파		armageddon
INSERT INTO title_songs (album_id, song_id) VALUES ((SELECT album_id FROM albums WHERE album_name = 'armageddon'), (SELECT song_id FROM songs WHERE title='supernova'));
INSERT INTO title_songs (album_id, song_id) VALUES ((SELECT album_id FROM albums WHERE album_name = 'armageddon'), (SELECT song_id FROM songs WHERE title='armageddon'));

-- 새로 생성된 song_id를 119부터128
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='supernova'), 30);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='armageddon'), 30);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='set the tone'), 30);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='mine'), 30);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='licorice'), 30);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='bahama'), 30);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='long chat'), 30);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='prologue'), 40);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='live my life'), 30);
INSERT INTO song_genre (song_id, genre_id) VALUES ((SELECT song_id FROM songs WHERE title='목소리'), 10);

-- 아티스트 정보 삽입 (에스파와 작사가 작곡가 편곡가)
INSERT INTO artists (artist_name, artist_image, artist_description) VALUES ('aespa', 'aespa.jpg', 'aespa.txt');
INSERT INTO artists (artist_name) VALUES ('KENZIE');
INSERT INTO artists (artist_name) VALUES ('Dem Jointz');
INSERT INTO artists (artist_name) VALUES ('방혜현');
INSERT INTO artists (artist_name) VALUES ('EJAE');
INSERT INTO artists (artist_name) VALUES ('No Identity');
INSERT INTO artists (artist_name) VALUES ('조윤경2');
INSERT INTO artists (artist_name) VALUES ('Ludwig Lindell');
INSERT INTO artists (artist_name) VALUES ('Caesar, Loui');
INSERT INTO artists (artist_name) VALUES ('이은화');
INSERT INTO artists (artist_name) VALUES ('Mike Daley');
INSERT INTO artists (artist_name) VALUES ('강은정');
INSERT INTO artists (artist_name) VALUES ('Daniel Davidsen');
INSERT INTO artists (artist_name) VALUES ('PhD');
INSERT INTO artists (artist_name) VALUES ('문설리');
INSERT INTO artists (artist_name) VALUES ('Stian Nyhammer Olsen');
INSERT INTO artists (artist_name) VALUES ('Mola');
INSERT INTO artists (artist_name) VALUES ('Gil Lewis');
INSERT INTO artists (artist_name) VALUES ('Leslie');
INSERT INTO artists (artist_name) VALUES ('Edvard Forre Erfjord');
INSERT INTO artists (artist_name) VALUES ('이오늘');
INSERT INTO artists (artist_name) VALUES ('밍지션');


--  (SELECT album_id FROM albums WHERE album_name = 'armageddon')
--  (SELECT song_id FROM songs WHERE title='maniac')

--  (SELECT artist_id FROM artists WHERE artist_name = '조윤경')

-- 새로 생성된 artist_id가  134 aespa, 135 KENZIE, 136 Dem Jointz, 137 방혜현, 138 EJAE, 139 No Identity, 140 조윤경, 141 Ludwig Lindell, 142 Caesar, Loui, 143 이은화, 144 Mike Daley
-- 145 강은정 146 Daniel Davidsen 147 PhD 148 문설리  149 Stian Nyhammer Olsen   150 Mola    151 Gil Lewis    152 Leslie    153 Edvard Forre Erfjord   154 이오늘   155 밍지션



-- 아티스트 역할 정보 삽입 songId 119부터 128
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'aespa'), (SELECT song_id FROM songs WHERE title='supernova'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'KENZIE'), (SELECT song_id FROM songs WHERE title='supernova'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'KENZIE'), (SELECT song_id FROM songs WHERE title='supernova'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Dem Jointz'), (SELECT song_id FROM songs WHERE title='supernova'), 40); -- 편곡가

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'aespa'), (SELECT song_id FROM songs WHERE title='armageddon'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '방혜현'), (SELECT song_id FROM songs WHERE title='armageddon'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'EJAE'), (SELECT song_id FROM songs WHERE title='armageddon'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'No Identity'), (SELECT song_id FROM songs WHERE title='armageddon'), 40); -- 편곡가

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'aespa'), (SELECT song_id FROM songs WHERE title='set the tone'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '조윤경'), (SELECT song_id FROM songs WHERE title='set the tone'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Ludwig Lindell'), (SELECT song_id FROM songs WHERE title='set the tone'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Caesar, Loui'), (SELECT song_id FROM songs WHERE title='set the tone'), 40); -- 편곡가

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'aespa'), (SELECT song_id FROM songs WHERE title='mine'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '이은화'), (SELECT song_id FROM songs WHERE title='mine'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Mike Daley'), (SELECT song_id FROM songs WHERE title='mine'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Mike Daley'), (SELECT song_id FROM songs WHERE title='mine'), 40); -- 편곡가

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'aespa'), (SELECT song_id FROM songs WHERE title='licorice'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '강은정'), (SELECT song_id FROM songs WHERE title='licorice'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Daniel Davidsen'), (SELECT song_id FROM songs WHERE title='licorice'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'PhD'), (SELECT song_id FROM songs WHERE title='licorice'), 40); -- 편곡가


INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'aespa'), (SELECT song_id FROM songs WHERE title='bahama'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'KENZIE'), (SELECT song_id FROM songs WHERE title='bahama'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'KENZIE'), (SELECT song_id FROM songs WHERE title='bahama'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'KENZIE'), (SELECT song_id FROM songs WHERE title='bahama'), 40); -- 편곡가

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'aespa'), (SELECT song_id FROM songs WHERE title='long chat'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '문설리'), (SELECT song_id FROM songs WHERE title='long chat'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Stian Nyhammer Olsen'), (SELECT song_id FROM songs WHERE title='long chat'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Stian Nyhammer Olsen'), (SELECT song_id FROM songs WHERE title='long chat'), 40); -- 편곡가

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'aespa'), (SELECT song_id FROM songs WHERE title='prologue'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Mola'), (SELECT song_id FROM songs WHERE title='prologue'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Gil Lewis'), (SELECT song_id FROM songs WHERE title='prologue'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Gil Lewis'), (SELECT song_id FROM songs WHERE title='prologue'), 40); -- 편곡가

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'aespa'), (SELECT song_id FROM songs WHERE title='live my life'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Leslie'), (SELECT song_id FROM songs WHERE title='live my life'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Edvard Forre Erfjord'), (SELECT song_id FROM songs WHERE title='live my life'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Edvard Forre Erfjord'), (SELECT song_id FROM songs WHERE title='live my life'), 40); -- 편곡가

INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'aespa'), (SELECT song_id FROM songs WHERE title='목소리'), 10); -- 가수
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '이오늘'), (SELECT song_id FROM songs WHERE title='목소리'), 30); -- 작사가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '밍지션'), (SELECT song_id FROM songs WHERE title='목소리'), 20); -- 작곡가
INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES ((SELECT artist_id FROM artists WHERE artist_name = '밍지션'), (SELECT song_id FROM songs WHERE title='목소리'), 40); -- 편곡가


--------- 동환이꺼

--------------- 아티스트
INSERT INTO artists (artist_image, artist_name, artist_description) 
    VALUES ('권정열.jpg', '10cm', '권정열.txt');
    
INSERT INTO artists (artist_image, artist_name, artist_description) 
    VALUES ('이요한.jpg', '이요한', '이요한.txt');

INSERT INTO artists (artist_image, artist_name, artist_description) 
    VALUES ('성수용.jpg', '성수용', '성수용.txt');

INSERT INTO artists (artist_image, artist_name, artist_description) 
    VALUES ('이윤혁.jpg', '이윤혁', '이윤혁.txt');
    
INSERT INTO artists (artist_image, artist_name, artist_description) 
    VALUES ('박기훈.jpg', '박기훈', '박기훈.txt');
    
INSERT INTO artists (artist_image, artist_name, artist_description) 
    VALUES ('인재.jpg', '인재', '인재.txt');
    
INSERT INTO artists (artist_image, artist_name, artist_description) 
    VALUES ('박준규.jpg', '박준규', '박준규.txt');
    
INSERT INTO artists (artist_image, artist_name, artist_description) 
    VALUES ('방인재.jpg', '방인재', '방인재.txt'); 
    
INSERT INTO artists (artist_image, artist_name, artist_description) 
    VALUES ('NODAY.jpg', 'NODAY', 'NODAY.txt');

INSERT INTO artists (artist_image, artist_name, artist_description) 
    VALUES ('이아일.jpg', '이아일', '이아일.txt');

INSERT INTO artists (artist_image, artist_name, artist_description) 
    VALUES ('박문치.jpg', '박문치', '박문치.txt');

INSERT INTO artists (artist_image, artist_name, artist_description) 
    VALUES ('달총 (CHEEZE).jpg', '달총 (CHEEZE)', '달총 (CHEEZE).txt');
    
    

INSERT INTO artists (artist_image, artist_name, artist_description) 
    VALUES ('장범준.jpg', '장범준', '장범준.txt');

INSERT INTO artists (artist_image, artist_name, artist_description) 
    VALUES ('이규형.jpg', '이규형', '이규형.txt');
    
INSERT INTO artists (artist_image, artist_name, artist_description) 
    VALUES ('전영호.jpg', '전영호', '전영호.txt');

INSERT INTO artists (artist_image, artist_name, artist_description) 
    VALUES ('황인현.jpg', '황인현', '황인현.txt');


--------------- 앨범   
INSERT INTO albums (album_name, album_image, album_type, album_release_date) 
	VALUES ('4.0', '10cm-4.0.jpg', '정규', '2017-09-01');
 
 INSERT INTO albums (album_name, album_image, album_type, album_release_date) 
    VALUES ('4.5', '10cm-tight.jpg', '싱글', '2020-09-22');
 
INSERT INTO albums (album_name, album_image, album_type, album_release_date) 
    VALUES ('방에 모기가 있어 (4.4)', '방에 모기가 있어 (4.4).jpg', '싱글', '2019-08-06');

INSERT INTO albums (album_name, album_image, album_type, album_release_date) 
    VALUES ('4.1', '10cm-4.1.jpg', '싱글', '2018-08-23');
    
INSERT INTO albums (album_name, album_image, album_type, album_release_date) 
    VALUES ('The 3rd EP', '10cm-The3rdEP.jpg', '미니', '2021-11-11');
 
INSERT INTO albums (album_name, album_image, album_type, album_release_date) 
	VALUES ('장범준 3집', '장범준3집.jpg', '정규', '2019-03-21');
    
    
--------------- 노래들
-- 10cm-4.0 앨범 노래
    
INSERT INTO songs (album_id, title, song_path, lyrics, video_link) -- title song
    VALUES ((SELECT album_id FROM albums WHERE album_name = '4.0'),
    '폰서트', '폰서트.mp3', '폰서트.txt', 
    'https://www.youtube.com/embed/mOo8bVzN9M8?si=kA9lzRqtUi8VRJzj');
    
INSERT INTO songs (album_id, title, song_path, lyrics, video_link) -- title song
    VALUES ((SELECT album_id FROM albums WHERE album_name = '4.0'),
    'Help', '10cm-Help.mp3', '10cm-Help.txt', 
    'https://www.youtube.com/embed/LnT9LmmlLXw?si=HI_5GlY2XMUlw1ym');
    
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
    VALUES ((SELECT album_id FROM albums WHERE album_name = '4.0'),
    'Everything', 'Everything.mp3', 'Everything.txt', 
    'https://www.youtube.com/embed/DOFojbxXShY?si=qCgbxJbQQjcro_Iy');
    
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
    VALUES ((SELECT album_id FROM albums WHERE album_name = '4.0'),
    'pet', 'pet.mp3', 'pet.txt', 
    'https://www.youtube.com/embed/F4K1mMDlsj0?si=QtKvLNKyVp7d_Qm5');
    
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
    VALUES ((SELECT album_id FROM albums WHERE album_name = '4.0'),
    '별자리', '별자리.mp3', '별자리.txt', 
    'https://www.youtube.com/embed/Pq6ImHFTBro?si=MxUgZawvdvZkov61');
    
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
    VALUES ((SELECT album_id FROM albums WHERE album_name = '4.0'),
    'Hotel room', 'Hotel room.mp3', 'Hotel room.txt', 
    'https://www.youtube.com/embed/wGTekU9hHyE?si=vgkZfEb8PaQwLp1Z');
    
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
    VALUES ((SELECT album_id FROM albums WHERE album_name = '4.0'),
    'Island', '10cm-Island.mp3', '10cm-Island.txt', 
    'https://www.youtube.com/embed/NhOv5blQn8w?si=0HhMJbJmVB-3dfEi');
    
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
    VALUES ((SELECT album_id FROM albums WHERE album_name = '4.0'),
    '일시정지', 'pause.mp3', 'pause.txt', 
    'X');

-- 10cm 4.5 앨범 노래
INSERT INTO songs (album_id, title, song_path, lyrics, video_link) -- title song
    VALUES ((SELECT album_id FROM albums WHERE album_name = '4.5'),
    'Tight', '10cm-Tight.mp3', '10cm-Tight.txt', 
    'https://www.youtube.com/embed/-xm5GpFwHw4?si=2dCUVlQWCZsl1kip');

-- 10cm 4.4 앨범 노래
INSERT INTO songs (album_id, title, song_path, lyrics, video_link) -- title song
    VALUES ((SELECT album_id FROM albums WHERE album_name = '방에 모기가 있어 (4.4)'),
    '방에 모기가 있어 (Do You Think Of Me)', '방에 모기가 있어 (Do You Think Of Me).mp3', 
    '방에 모기가 있어 (Do You Think Of Me).txt', 
    'https://www.youtube.com/embed/d5cV86Sa6k0?si=CobQrt8ZujUkF8II');
    
-- 10cm 4.1 앨범 노래
INSERT INTO songs (album_id, title, song_path, lyrics, video_link) -- title song
    VALUES ((SELECT album_id FROM albums WHERE album_name = '4.1'),
    '매트리스', '매트리스.mp3', '매트리스.txt', 
    'https://www.youtube.com/embed/seNNCbiXTSY?si=1u-8OsNLZ7GGP6wA');
    
        
-- 10cm The 3rd ep 노래
INSERT INTO songs (album_id, title, song_path, lyrics, video_link) -- title song
    VALUES ((SELECT album_id FROM albums WHERE album_name = 'The 3rd EP'),
    '어제 너는 나를 버렸어', '어제 너는 나를 버렸어.mp3', '어제 너는 나를 버렸어.txt', 
    'https://www.youtube.com/embed/bYleMOXKggY?si=U-YAaOLfe36tCmvM');

INSERT INTO songs (album_id, title, song_path, lyrics, video_link) -- title song
    VALUES ((SELECT album_id FROM albums WHERE album_name = 'The 3rd EP'),
    '가진다는 말은 좀 그렇지', '가진다는 말은 좀 그렇지.mp3', '가진다는 말은 좀 그렇지.txt', 
    'https://www.youtube.com/embed/TUo_XGiPmvI?si=BAdD_bPQWcC5_oaj');
    
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
    VALUES ((SELECT album_id FROM albums WHERE album_name = 'The 3rd EP'),
    '열심히 할게', '열심히 할게.mp3', '열심히 할게.txt', 
    'https://www.youtube.com/embed/z2WRu2hLngg?si=ywsZi8utEu1mlZUP');

    
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
    VALUES ((SELECT album_id FROM albums WHERE album_name = 'The 3rd EP'),
    'Condition', 'Condition.mp3', 'Condition.txt', 
    'https://www.youtube.com/embed/1FVySfPnLIU?si=ofooK0NzjkygXbFB');
    
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
    VALUES ((SELECT album_id FROM albums WHERE album_name = 'The 3rd EP'),
    'Please Don''t Stop Your Singing', 'Please Don''t Stop Your Singing.mp3', 'Please Don''t Stop Your Singing.txt', 
    'https://www.youtube.com/embed/z2WRu2hLngg?si=dr70QcJvbL1VMsxc');
    
    
-- 장범준 3집 노래
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)  -- title song
    VALUES ((SELECT album_id FROM albums WHERE album_name = '장범준 3집'),
    '당신과는 천천히', '당신과는 천천히.mp3', '당신과는 천천히.txt', 
    'https://www.youtube.com/embed/EAVp84B888U?si=Z1sEAFJ9_BubgKxp');

INSERT INTO songs (album_id, title, song_path, lyrics, video_link)  -- title song
    VALUES ((SELECT album_id FROM albums WHERE album_name = '장범준 3집'),
    '노래방에서', '노래방에서.mp3', '노래방에서.txt', 
    'x');
    
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)  
    VALUES ((SELECT album_id FROM albums WHERE album_name = '장범준 3집'),
    '일산으로', '일산으로.mp3', '일산으로.txt', 
    'x');
    
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)  
    VALUES ((SELECT album_id FROM albums WHERE album_name = '장범준 3집'),
    '그모습 그대로', '그모습 그대로.mp3', '그모습 그대로.txt', 
    'x');
    
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)  
    VALUES ((SELECT album_id FROM albums WHERE album_name = '장범준 3집'),
    '엄마 용돈 좀 보내주세요', '엄마 용돈 좀 보내주세요.mp3', '엄마 용돈 좀 보내주세요.txt', 
    'x');
    
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)  
    VALUES ((SELECT album_id FROM albums WHERE album_name = '장범준 3집'),
    '이밤', '이밤.mp3', '이밤.txt', 
    'x');
    
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)  
    VALUES ((SELECT album_id FROM albums WHERE album_name = '장범준 3집'),
    '왜', '왜.mp3', '왜.txt', 
    'x');

INSERT INTO songs (album_id, title, song_path, lyrics, video_link)  
    VALUES ((SELECT album_id FROM albums WHERE album_name = '장범준 3집'),
    '상상속에서', '상상속에서.mp3', '상상속에서.txt', 
    'x');
    
--------------- 타이틀 송 
-- 10cm 4.0
INSERT INTO title_songs(album_id, song_id) 
VALUES((SELECT album_id FROM albums WHERE album_name = '4.0'),
    (SELECT song_id FROM songs WHERE title='폰서트'));
INSERT INTO title_songs(album_id, song_id) 
VALUES((SELECT album_id FROM albums WHERE album_name = '4.0'),
    (SELECT song_id FROM songs WHERE title='Help'));

-- 10cm 4.5
INSERT INTO title_songs(album_id, song_id) 
VALUES((SELECT album_id FROM albums WHERE album_name = '4.5'),
    (SELECT song_id FROM songs WHERE title='Tight'));
    
-- 10cm 4.4
INSERT INTO title_songs(album_id, song_id) 
VALUES((SELECT album_id FROM albums WHERE album_name = '방에 모기가 있어 (4.4)'),
    (SELECT song_id FROM songs WHERE title='방에 모기가 있어 (Do You Think Of Me)'));
    
-- 10cm 4.1
INSERT INTO title_songs(album_id, song_id) 
VALUES((SELECT album_id FROM albums WHERE album_name = '4.1'),
    (SELECT song_id FROM songs WHERE title='매트리스'));
    
-- 10cm The 3rd ep
INSERT INTO title_songs(album_id, song_id) 
VALUES((SELECT album_id FROM albums WHERE album_name = 'The 3rd EP'),
    (SELECT song_id FROM songs WHERE title='가진다는 말은 좀 그렇지'));
    
INSERT INTO title_songs(album_id, song_id) 
VALUES((SELECT album_id FROM albums WHERE album_name = 'The 3rd EP'),
    (SELECT song_id FROM songs WHERE title='어제 너는 나를 버렸어'));
    
-- 장범준 3집
INSERT INTO title_songs(album_id, song_id) 
VALUES((SELECT album_id FROM albums WHERE album_name = '장범준 3집'),
    (SELECT song_id FROM songs WHERE title='당신과는 천천히'));

INSERT INTO title_songs(album_id, song_id) 
VALUES((SELECT album_id FROM albums WHERE album_name = '장범준 3집'),
    (SELECT song_id FROM songs WHERE title='노래방에서'));

--------------- 노래 장르
-- 10cm 4.0 장르
DELIMITER //

CREATE PROCEDURE INSERT_song_genres()
BEGIN
    DECLARE v_album_id INT;
    DECLARE done INT DEFAULT FALSE;
    DECLARE v_song_id INT;
    DECLARE cur CURSOR FOR SELECT song_id FROM songs WHERE album_id = v_album_id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    -- 특정 앨범 타이틀의 album_id를 가져옵니다.
    SELECT album_id INTO v_album_id FROM albums WHERE album_name = '4.0';

    -- 해당 album_id를 가진 모든 노래에 대해 song_genre 테이블에 데이터를 삽입합니다.
    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO v_song_id;
        IF done THEN
            LEAVE read_loop;
        END IF;
        INSERT INTO song_genre (song_id, genre_id) VALUES (v_song_id, 90);
        INSERT INTO song_genre (song_id, genre_id) VALUES (v_song_id, 80);
    END LOOP;
    CLOSE cur;
END //

DELIMITER ;

-- 프로시저 실행
CALL INSERT_song_genres();

-- DELETE 문은 거의 동일하게 사용할 수 있습니다.
DELETE FROM song_genre 
WHERE song_id = (SELECT song_id FROM songs WHERE title='Help') 
  AND genre_id = 80;

-- 10cm 4.5 장르
INSERT INTO song_genre (song_id, genre_id)
        VALUES ((SELECT song_id FROM songs WHERE title='Tight'), 80);
INSERT INTO song_genre (song_id, genre_id)
        VALUES ((SELECT song_id FROM songs WHERE title='Tight'), 90);

-- 10cm 4.4 장르
INSERT INTO song_genre (song_id, genre_id)
        VALUES ((SELECT song_id FROM songs WHERE title='방에 모기가 있어 (Do You Think Of Me)'), 80);
INSERT INTO song_genre (song_id, genre_id)
        VALUES ((SELECT song_id FROM songs WHERE title='방에 모기가 있어 (Do You Think Of Me)'), 90);

-- 10cm 4.1 장르
INSERT INTO song_genre (song_id, genre_id)
        VALUES ((SELECT song_id FROM songs WHERE title='매트리스'), 80);
INSERT INTO song_genre (song_id, genre_id)
        VALUES ((SELECT song_id FROM songs WHERE title='매트리스'), 90);

-- 10cm The 3rd ep 장르
DELIMITER //

CREATE PROCEDURE INSERT_song_genres_for_album()
BEGIN
    DECLARE v_album_id INT;
    DECLARE done INT DEFAULT FALSE;
    DECLARE v_song_id INT;
    DECLARE cur CURSOR FOR SELECT song_id FROM songs WHERE album_id = v_album_id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    -- 특정 앨범 타이틀의 album_id를 가져옵니다.
    SELECT album_id INTO v_album_id FROM albums WHERE album_name = 'The 3rd EP';

    -- 해당 album_id를 가진 모든 노래에 대해 song_genre 테이블에 데이터를 삽입합니다.
    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO v_song_id;
        IF done THEN
            LEAVE read_loop;
        END IF;
        INSERT INTO song_genre (song_id, genre_id) VALUES (v_song_id, 10);
        INSERT INTO song_genre (song_id, genre_id) VALUES (v_song_id, 80);
    END LOOP;
    CLOSE cur;
END //

DELIMITER ;

-- 프로시저 실행
CALL INSERT_song_genres_for_album();

-- 장범준 3집 장르
DELIMITER //

CREATE PROCEDURE INSERT_song_genres_for_album_3rd()
BEGIN
    DECLARE v_album_id INT;
    DECLARE done INT DEFAULT FALSE;
    DECLARE v_song_id INT;
    DECLARE cur CURSOR FOR SELECT song_id FROM songs WHERE album_id = v_album_id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    -- 특정 앨범 타이틀의 album_id를 가져옵니다.
    SELECT album_id INTO v_album_id FROM albums WHERE album_name = '장범준 3집';

    -- 해당 album_id를 가진 모든 노래에 대해 song_genre 테이블에 데이터를 삽입합니다.
    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO v_song_id;
        IF done THEN
            LEAVE read_loop;
        END IF;
        INSERT INTO song_genre (song_id, genre_id) VALUES (v_song_id, 100);
    END LOOP;
    CLOSE cur;
END //

DELIMITER ;

-- 프로시저 실행
CALL INSERT_song_genres_for_album_3rd();

--------------- 노래별 아티스트 역할 추가 
-- 10cm 4.0앨범 아티스트 역할
DELIMITER //

CREATE PROCEDURE INSERT_artist_roles_for_album_4_0()
BEGIN
    DECLARE v_album_id INT;
    DECLARE done INT DEFAULT FALSE;
    DECLARE v_song_id INT;
    DECLARE v_artist_id INT;
    DECLARE cur CURSOR FOR SELECT song_id FROM songs WHERE album_id = v_album_id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    -- 특정 앨범 타이틀의 album_id를 가져옵니다.
    SELECT album_id INTO v_album_id FROM albums WHERE album_name = '4.0';

    -- 커서를 열어 데이터를 가져옵니다.
    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO v_song_id;
        IF done THEN
            LEAVE read_loop;
        END IF;

        -- '10cm' 아티스트 ID를 가져옵니다.
        SELECT artist_id INTO v_artist_id FROM artists WHERE artist_name = '10cm';

        -- '10cm' 아티스트의 여러 역할을 삽입합니다.
        INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES (v_artist_id, v_song_id, 10);
        INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES (v_artist_id, v_song_id, 20);
        INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES (v_artist_id, v_song_id, 30);
        INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES (v_artist_id, v_song_id, 40);

        -- '이요한' 아티스트 ID를 가져옵니다.
        SELECT artist_id INTO v_artist_id FROM artists WHERE artist_name = '이요한';
        INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES (v_artist_id, v_song_id, 40);

        -- '성수용' 아티스트 ID를 가져옵니다.
        SELECT artist_id INTO v_artist_id FROM artists WHERE artist_name = '성수용';
        INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES (v_artist_id, v_song_id, 40);

        -- '이윤혁' 아티스트 ID를 가져옵니다.
        SELECT artist_id INTO v_artist_id FROM artists WHERE artist_name = '이윤혁';
        INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES (v_artist_id, v_song_id, 40);

    END LOOP;
    CLOSE cur;
END //

DELIMITER ;

-- 프로시저 실행
CALL INSERT_artist_roles_for_album_4_0();


INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '박기훈'),
    (SELECT song_id FROM songs WHERE title = 'pet'),
    40);

INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '인재'),
    (SELECT song_id FROM songs WHERE title = '별자리'),
    40);

INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '인재'),
    (SELECT song_id FROM songs WHERE title = 'Help'),
    40);

INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '박준규'),
    (SELECT song_id FROM songs WHERE title = 'Hotel room'),
    40);
    
INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '인재'),
    (SELECT song_id FROM songs WHERE title = 'Island'),
    40);
    
-- 10cm 4.5앨범 아티스트 역할
INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '10cm'),
    (SELECT song_id FROM songs WHERE title = 'Tight'),
    10);
    
INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '10cm'),
    (SELECT song_id FROM songs WHERE title = 'Tight'),
    30);

INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'NODAY'),
    (SELECT song_id FROM songs WHERE title = 'Tight'),
    30);

INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '이아일'),
    (SELECT song_id FROM songs WHERE title = 'Tight'),
    30);
    
INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '박문치'),
    (SELECT song_id FROM songs WHERE title = 'Tight'),
    30);    
    
INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '달총 (CHEEZE)'),
    (SELECT song_id FROM songs WHERE title = 'Tight'),
    30);
    
INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '이아일'),
    (SELECT song_id FROM songs WHERE title = 'Tight'),
    20);

INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '박문치'),
    (SELECT song_id FROM songs WHERE title = 'Tight'),
    20);

INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'NODAY'),
    (SELECT song_id FROM songs WHERE title = 'Tight'),
    20);
    
INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '박문치'),
    (SELECT song_id FROM songs WHERE title = 'Tight'),
    40);

-- 10cm 4.4 앨범 아티스트 역할
INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '10cm'),
    (SELECT song_id FROM songs WHERE title = '방에 모기가 있어 (Do You Think Of Me)'),
    10);
    
INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '10cm'),
    (SELECT song_id FROM songs WHERE title = '방에 모기가 있어 (Do You Think Of Me)'),
    20);

INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '10cm'),
    (SELECT song_id FROM songs WHERE title = '방에 모기가 있어 (Do You Think Of Me)'),
    30);

INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '10cm'),
    (SELECT song_id FROM songs WHERE title = '방에 모기가 있어 (Do You Think Of Me)'),
    40);
    
INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '이요한'),
    (SELECT song_id FROM songs WHERE title = '방에 모기가 있어 (Do You Think Of Me)'),
    40);    
    
INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '성수용'),
    (SELECT song_id FROM songs WHERE title = '방에 모기가 있어 (Do You Think Of Me)'),
    40);
    
INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '이윤혁'),
    (SELECT song_id FROM songs WHERE title = '방에 모기가 있어 (Do You Think Of Me)'),
    40);

INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '방인재'),
    (SELECT song_id FROM songs WHERE title = '방에 모기가 있어 (Do You Think Of Me)'),
    40);

-- 10cm 4.1 앨범 아티스트 역할
INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '10cm'),
    (SELECT song_id FROM songs WHERE title = '매트리스'),
    10);
    
INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '10cm'),
    (SELECT song_id FROM songs WHERE title = '매트리스'),
    20);

INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '10cm'),
    (SELECT song_id FROM songs WHERE title = '매트리스'),
    30);

INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '10cm'),
    (SELECT song_id FROM songs WHERE title = '매트리스'),
    40);
    
INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '이요한'),
    (SELECT song_id FROM songs WHERE title = '매트리스'),
    40);    
    
INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '성수용'),
    (SELECT song_id FROM songs WHERE title = '매트리스'),
    40);
    
INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '이윤혁'),
    (SELECT song_id FROM songs WHERE title = '매트리스'),
    40);

INSERT INTO artist_roles (artist_id, song_id, role_id) 
    VALUES ((SELECT artist_id FROM artists WHERE artist_name = '방인재'),
    (SELECT song_id FROM songs WHERE title = '매트리스'),
    40);
    

-- 10cm The 3rd ep앨범 아티스트 역할
DELIMITER //

CREATE PROCEDURE INSERT_artist_roles_for_album_3rd_EP()
BEGIN
    DECLARE v_album_id INT;
    DECLARE done INT DEFAULT FALSE;
    DECLARE v_song_id INT;
    DECLARE v_artist_id INT;
    DECLARE cur CURSOR FOR SELECT song_id FROM songs WHERE album_id = v_album_id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    -- 특정 앨범 타이틀의 album_id를 가져옵니다.
    SELECT album_id INTO v_album_id FROM albums WHERE album_name = 'The 3rd EP';

    -- 커서를 열어 데이터를 가져옵니다.
    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO v_song_id;
        IF done THEN
            LEAVE read_loop;
        END IF;

        -- '10cm' 아티스트 ID를 가져옵니다.
        SELECT artist_id INTO v_artist_id FROM artists WHERE artist_name = '10cm';

        -- '10cm' 아티스트의 여러 역할을 삽입합니다.
        INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES (v_artist_id, v_song_id, 10);
        INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES (v_artist_id, v_song_id, 20);
        INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES (v_artist_id, v_song_id, 30);
        INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES (v_artist_id, v_song_id, 40);

        -- '이요한' 아티스트 ID를 가져옵니다.
        SELECT artist_id INTO v_artist_id FROM artists WHERE artist_name = '이요한';
        INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES (v_artist_id, v_song_id, 40);

        -- '성수용' 아티스트 ID를 가져옵니다.
        SELECT artist_id INTO v_artist_id FROM artists WHERE artist_name = '성수용';
        INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES (v_artist_id, v_song_id, 40);

        -- '이윤혁' 아티스트 ID를 가져옵니다.
        SELECT artist_id INTO v_artist_id FROM artists WHERE artist_name = '이윤혁';
        INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES (v_artist_id, v_song_id, 40);

        -- '방인재' 아티스트 ID를 가져옵니다.
        SELECT artist_id INTO v_artist_id FROM artists WHERE artist_name = '방인재';
        INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES (v_artist_id, v_song_id, 40);

    END LOOP;
    CLOSE cur;
END //

DELIMITER ;

-- 프로시저 실행
CALL INSERT_artist_roles_for_album_3rd_EP();


-- 장범준 3집 앨범 아티스트 역할
DELIMITER //

CREATE PROCEDURE INSERT_artist_roles_for_album_Jang_Bum_Jun_3rd()
BEGIN
    DECLARE v_album_id INT;
    DECLARE done INT DEFAULT FALSE;
    DECLARE v_song_id INT;
    DECLARE v_artist_id INT;
    DECLARE cur CURSOR FOR SELECT song_id FROM songs WHERE album_id = v_album_id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    -- 특정 앨범 타이틀의 album_id를 가져옵니다.
    SELECT album_id INTO v_album_id FROM albums WHERE album_name = '장범준 3집';

    -- 커서를 열어 데이터를 가져옵니다.
    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO v_song_id;
        IF done THEN
            LEAVE read_loop;
        END IF;

        -- '장범준' 아티스트 ID를 가져옵니다.
        SELECT artist_id INTO v_artist_id FROM artists WHERE artist_name = '장범준';
        
        -- '장범준' 아티스트의 여러 역할을 삽입합니다.
        INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES (v_artist_id, v_song_id, 10);
        INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES (v_artist_id, v_song_id, 20);
        INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES (v_artist_id, v_song_id, 30);
        INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES (v_artist_id, v_song_id, 40);

        -- '이규형' 아티스트 ID를 가져옵니다.
        SELECT artist_id INTO v_artist_id FROM artists WHERE artist_name = '이규형';
        INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES (v_artist_id, v_song_id, 40);

        -- '전영호' 아티스트 ID를 가져옵니다.
        SELECT artist_id INTO v_artist_id FROM artists WHERE artist_name = '전영호';
        INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES (v_artist_id, v_song_id, 40);

        -- '황인현' 아티스트 ID를 가져옵니다.
        SELECT artist_id INTO v_artist_id FROM artists WHERE artist_name = '황인현';
        INSERT INTO artist_roles (artist_id, song_id, role_id) VALUES (v_artist_id, v_song_id, 40);

    END LOOP;
    CLOSE cur;
END //

DELIMITER ;

-- 프로시저 실행
CALL INSERT_artist_roles_for_album_Jang_Bum_Jun_3rd();



-- 현우형 꺼

-------------------------------------- ALBUMS 테이블----------------------------------------------------------

INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('24K Magic', '24K Magic_cover.jpg', '싱글', '2017-04-20');

INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('1000 Forms Of Fear', '1000 Forms Of Fear_cover.jpg', '정규', '2015-05-04');

INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('1989', '1989_cover.jpg', '정규', '2014-10-27');

INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('A Thousand Years', 'A Thousand Years_cover.jpg', '싱글', '2011-10-18');

INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('Aladdin', 'Aladdin_cover.jpg', 'OST', '2019-05-22');

INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('Arrival', 'Arrival_cover.jpg', '정규', '1976-08-15');

INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('Baby One More Time', 'Baby One More Time_cover.jpg', '정규', '1999-01-12');

INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('Breakaway', 'Breakaway_cover.jpg', '정규', '2016-04-22');

INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('Emotion', 'Emotion_cover.jpg', '정규', '2015-08-21');

INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('Flashlight', 'Flashlight_cover.jpg', '싱글', '2015-04-23');

INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('Frozen 2', 'Frozen 2_cover.jpg', '정규', '2019-11-15');

INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('Kiss', 'Kiss_cover.jpg', '정규', '2012-09-17');

INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('Lemonade Mouth', 'Lemonade Mouth_cover.jpg', '정규', '2011-04-12');

INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('Lover', 'Lover_cover.jpg', '정규', '2019-08-23');

INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('My Everything', 'My Everything_cover.jpg', '정규', '2014-08-25');

INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('Outside', 'Outside_cover.jpg', '싱글', '2014-10-20');

INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('Speak Your Mind', 'Speak Your Mind_cover.jpg', '정규', '2018-04-27');

INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('The Midsummer Station', 'The Midsummer Station_cover.jpg', '정규', '2012-08-21');

INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('Voicenotes', 'Voicenotes_cover.jpg', '정규', '2018-05-11');

INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('Whatever', 'Whatever_cover.jpg', '싱글', '2024-01-19');

INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('When I Get Old', 'When I Get Old_cover.jpg', '싱글', '2022-10-20');

INSERT INTO albums (album_name, album_image, album_type, album_release_date)
VALUES ('Who You Are', 'Who You Are_cover.jpg', '정규', '2011-11-14');



-------------------------------------- SONGS 테이블----------------------------------------------------------
------------------------- That’s What I Like----------------------------------------------------------------------------
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES ((SELECT album_id FROM albums WHERE album_name = '24K Magic'), 'That’s What I Like', '24K Magic_That’s What I Like_Bruno Mars.mp3', '24K Magic_That’s What I Like_lyrics.txt', 'https://www.youtube.com/watch?v=PMivT7MJ41M');

------------------------- Chandelier----------------------------------------------------------------------------
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES ((SELECT album_id FROM albums WHERE album_name = '1000 Forms Of Fear'), 'Chandelier', '1000 Forms Of Fear_Chandelier_Sia.mp3', '1000 Forms Of Fear_Chandelier_lyrics.txt', 'https://www.youtube.com/watch?v=2vjPBrBU-TM');

------------------------- Blank Space----------------------------------------------------------------------------
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES ((SELECT album_id FROM albums WHERE album_name = '1989'), 'Blank Space', '1989_Blank Space_Taylor Swift.mp3', '1989_Blank Space_lyrics.txt', 'https://www.youtube.com/watch?v=e-ORhEE9VVg');

------------------------- A Thousand Years----------------------------------------------------------------------------
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'A Thousand Years'), 'A Thousand Years', 'A Thousand Years_A Thousand Years_Christina Perri.mp3', 'A Thousand Years_A Thousand Years_lyrics.txt', 'https://www.youtube.com/watch?v=rtOvBOTyX00');

------------------------- A Whole New World (End Title)----------------------------------------------------------------
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Aladdin'), 'A Whole New World (End Title)', 'Aladdin_A Whole New World (End Title)_ZAYN, Zhavia.mp3', 'Aladdin_A Whole New World (End Title)_lyrics.txt', 'https://www.youtube.com/watch?v=eitDnP0_83k');

------------------------- Speechless----------------------------------------------------------------------------
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Aladdin'), 'Speechless', 'Aladdin_Speechless_Naomi Scott.mp3', 'Aladdin_Speechless_lyrics.txt', 'https://www.youtube.com/watch?v=mw5VIEIvuMI');

------------------------- Dancing Queen----------------------------------------------------------------------------
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Arrival'), 'Dancing Queen', 'Arrival_Dancing Queen_ABBA.mp3', 'Arrival_Dancing Queen_lyrics.txt', 'https://www.youtube.com/watch?v=xFrGuyw1V8s');

------------------------- Baby One More Time----------------------------------------------------------------------------
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Baby One More Time'), 'Baby One More Time', 'Baby One More Time_Baby One More Time_Britney Spears.mp3', 'Baby One More Time_Baby One More Time_lyrics.txt', 'https://www.youtube.com/watch?v=C-u5WLJ9Yk4');

------------------------- Because of You----------------------------------------------------------------------------
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Breakaway'), 'Because of You', 'Breakaway_Because of You_Kelly Clarkson.mp3', 'Breakaway_Because of You_lyrics.txt', 'https://www.youtube.com/watch?v=Ra-Om7UMSJc');

------------------------- I Really Like You----------------------------------------------------------------------------
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Emotion'), 'I Really Like You', 'Emotion_I Really Like You_Carly Rae Jepsen.mp3', 'Emotion_I Really Like You_lyrics.txt', 'https://www.youtube.com/watch?v=qV5lzRHrGeg');

------------------------- Flashlight----------------------------------------------------------------------------
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Flashlight'), 'Flashlight', 'Flashlight_Flashlight_Jessie J.mp3', 'Flashlight_Flashlight_lyrics.txt', 'https://www.youtube.com/watch?v=DzwkcbTQ7ZE');

------------------------- INTO the Unknown----------------------------------------------------------------------------
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Frozen 2'), 'INTO the Unknown', 'Frozen 2_INTO the Unknown_Idina Menzel, AURORA.mp3', 'Frozen 2_INTO the Unknown_Idina Menzel_lyrics.txt', 'https://www.youtube.com/watch?v=gIOyB9ZXn8s');

------------------------- Show Yourself----------------------------------------------------------------------------
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Frozen 2'), 'Show Yourself', 'Frozen 2_Show Yourself_Idina Menzel, Evan Rachel Wood.mp3', 'Frozen 2_Show Yourself_lyrics.txt', 'https://www.youtube.com/watch?v=nrZxwPwmgrw');

------------------------- Call Me Maybe----------------------------------------------------------------------------
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Kiss'), 'Call Me Maybe', 'Kiss_Call Me Maybe_Carly Rae Jepsen.mp3', 'Kiss_Call Me Maybe_lyrics.txt', 'https://www.youtube.com/watch?v=fWNaR-rxAic');

------------------------- She''s So Gone----------------------------------------------------------------------------
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Lemonade Mouth'), 'She''s So Gone', 'Lemonade Mouth_She''s So Gone_Naomi Scott.mp3', 'Lemonade Mouth_She''s So Gone_lyrics.txt', 'https://www.youtube.com/watch?v=xnAc-rgvJTA');

------------------------- Cruel Summer----------------------------------------------------------------------------
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Lover'), 'Cruel Summer', 'Lover_Cruel Summer_Taylor Swift.mp3', 'Lover_Cruel Summer_lyrics.txt', 'https://www.youtube.com/watch?v=ic8j13piAhQ');

------------------------- One Last Time----------------------------------------------------------------------------
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'My Everything'), 'One Last Time', 'My Everything_One Last Time_Ariana Grande.mp3', 'My Everything_One Last Time_lyrics.txt', 'https://www.youtube.com/watch?v=Wg92RrNhB8s');

------------------------- Problem----------------------------------------------------------------------------
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'My Everything'), 'Problem', 'My Everything_Problem_Ariana Grande.mp3', 'My Everything_Problem_lyrics.txt', 'https://www.youtube.com/watch?v=iS1g8G_njx8');

------------------------- Outside----------------------------------------------------------------------------
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Outside'), 'Outside', 'Outside_Outside_Calvin Harris, Ellie Goulding.mp3', 'Outside_Outside_lyrics.txt', 'https://www.youtube.com/watch?v=J9NQFACZYEU');

------------------------- 2002 ----------------------------------------------------------------------------
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Speak Your Mind'), '2002', 'Speak Your Mind_2002_Anne Marie.mp3', 'Speak Your Mind_2002_lyrics.txt', 'https://www.youtube.com/watch?v=Il-an3K9pjg');

------------------------- Good Time----------------------------------------------------------------------------
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'The Midsummer Station'), 'Good Time', 'The Midsummer Station_Good Time_Owl City.mp3', 'The Midsummer Station_Good Time_lyrics.txt', 'https://www.youtube.com/watch?v=H7HmzwI67ec');

------------------------- Attention----------------------------------------------------------------------------
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Voicenotes'), 'Attention', 'Voicenotes_Attention_Charlie Puth.mp3', 'Voicenotes_Attention_lyrics.txt', 'https://www.youtube.com/watch?v=nfs8NYg7yQM');

------------------------- Whatever----------------------------------------------------------------------------
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Whatever'), 'Whatever', 'Whatever_Whatever_Kygo, Ava Max.mp3', 'Whatever_Whatever_lyrics.txt', 'https://www.youtube.com/watch?v=ZDZiXmCl4pk');

------------------------- When I Get Old----------------------------------------------------------------------------
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'When I Get Old'), 'When I Get Old', 'When I Get Old_When I Get Old_Christopher, 청하.mp3', 'When I Get Old_When I Get Old_lyrics.txt', 'https://www.youtube.com/watch?v=Ua3aNDJE_Cg');

------------------------- Domino----------------------------------------------------------------------------
INSERT INTO songs (album_id, title, song_path, lyrics, video_link)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Who You Are'), 'Domino', 'Who You Are_Domino_Jessie J.mp3', 'Who You Are_Domino_lyrics.txt', 'https://www.youtube.com/watch?v=UJtB55MaoD0');


-------------------------------------- TITLE_SONGS 테이블----------------------------------------------------------
------------------------- That’s What I Like----------------------------------------------------------------------------
INSERT INTO title_songs (album_id, song_id)
VALUES ((SELECT album_id FROM albums WHERE album_name = '24K Magic'), (SELECT song_id FROM songs WHERE title = 'That’s What I Like'));

------------------------- Chandelier----------------------------------------------------------------------------
INSERT INTO title_songs (album_id, song_id)
VALUES ((SELECT album_id FROM albums WHERE album_name = '1000 Forms Of Fear'), (SELECT song_id FROM songs WHERE title = 'Chandelier'));

------------------------- Blank Space----------------------------------------------------------------------------
INSERT INTO title_songs (album_id, song_id)
VALUES ((SELECT album_id FROM albums WHERE album_name = '1989'), (SELECT song_id FROM songs WHERE title = 'Blank Space'));

------------------------- A Thousand Years----------------------------------------------------------------------------
INSERT INTO title_songs (album_id, song_id)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'A Thousand Years'), (SELECT song_id FROM songs WHERE title = 'A Thousand Years'));

------------------------- A Whole New World (End Title)------------------------------------------------------------------
INSERT INTO title_songs (album_id, song_id)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Aladdin'), (SELECT song_id FROM songs WHERE title = 'A Whole New World (End Title)'));

------------------------- Speechless----------------------------------------------------------------------------
INSERT INTO title_songs (album_id, song_id)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Aladdin'), (SELECT song_id FROM songs WHERE title = 'Speechless'));

------------------------- Dancing Queen----------------------------------------------------------------------------
INSERT INTO title_songs (album_id, song_id)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Arrival'), (SELECT song_id FROM songs WHERE title = 'Dancing Queen'));

------------------------- Baby One More Time----------------------------------------------------------------------------
INSERT INTO title_songs (album_id, song_id)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Baby One More Time'), (SELECT song_id FROM songs WHERE title = 'Baby One More Time'));

------------------------- Because of You----------------------------------------------------------------------------
INSERT INTO title_songs (album_id, song_id)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Breakaway'), (SELECT song_id FROM songs WHERE title = 'Because of You'));

------------------------- I Really Like You----------------------------------------------------------------------------
INSERT INTO title_songs (album_id, song_id)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Emotion'), (SELECT song_id FROM songs WHERE title = 'I Really Like You'));

------------------------- Flashlight----------------------------------------------------------------------------
INSERT INTO title_songs (album_id, song_id)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Flashlight'), (SELECT song_id FROM songs WHERE title = 'Flashlight'));

------------------------- INTO the Unknown----------------------------------------------------------------------------
INSERT INTO title_songs (album_id, song_id)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Frozen 2'), (SELECT song_id FROM songs WHERE title = 'INTO the Unknown'));

------------------------- Show Yourself----------------------------------------------------------------------------
INSERT INTO title_songs (album_id, song_id)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Frozen 2'), (SELECT song_id FROM songs WHERE title = 'Show Yourself'));

------------------------- Call Me Maybe----------------------------------------------------------------------------
INSERT INTO title_songs (album_id, song_id)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Kiss'), (SELECT song_id FROM songs WHERE title = 'Call Me Maybe'));

------------------------- She''s So Gone----------------------------------------------------------------------------
INSERT INTO title_songs (album_id, song_id)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Lemonade Mouth'), (SELECT song_id FROM songs WHERE title = 'She''s So Gone'));

------------------------- Cruel Summer----------------------------------------------------------------------------
INSERT INTO title_songs (album_id, song_id)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Lover'), (SELECT song_id FROM songs WHERE title = 'Cruel Summer'));

------------------------- One Last Time----------------------------------------------------------------------------
INSERT INTO title_songs (album_id, song_id)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'My Everything'), (SELECT song_id FROM songs WHERE title = 'One Last Time'));

------------------------- Problem----------------------------------------------------------------------------
INSERT INTO title_songs (album_id, song_id)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'My Everything'), (SELECT song_id FROM songs WHERE title = 'Problem'));

------------------------- Outside----------------------------------------------------------------------------
INSERT INTO title_songs (album_id, song_id)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Outside'), (SELECT song_id FROM songs WHERE title = 'Outside'));

------------------------- 2002----------------------------------------------------------------------------
INSERT INTO title_songs (album_id, song_id)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Speak Your Mind'), (SELECT song_id FROM songs WHERE title = '2002'));

------------------------- Good Time----------------------------------------------------------------------------
INSERT INTO title_songs (album_id, song_id)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'The Midsummer Station'), (SELECT song_id FROM songs WHERE title = 'Good Time'));

------------------------- Attention----------------------------------------------------------------------------
INSERT INTO title_songs (album_id, song_id)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Voicenotes'), (SELECT song_id FROM songs WHERE title = 'Attention'));

------------------------- Whatever----------------------------------------------------------------------------
INSERT INTO title_songs (album_id, song_id)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Whatever'), (SELECT song_id FROM songs WHERE title = 'Whatever'));

------------------------- When I Get Old----------------------------------------------------------------------------
INSERT INTO title_songs (album_id, song_id)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'When I Get Old'), (SELECT song_id FROM songs WHERE title = 'When I Get Old'));

------------------------- Domino----------------------------------------------------------------------------
INSERT INTO title_songs (album_id, song_id)
VALUES ((SELECT album_id FROM albums WHERE album_name = 'Who You Are'), (SELECT song_id FROM songs WHERE title = 'Domino'));


-------------------------------------- ARTISTS 테이블----------------------------------------------------------
------------------------- That’s What I Like----------------------------------------------------------------------------

INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('Bruno Mars', 'Bruno Mars.jpg', 'Bruno Mars.txt');

INSERT INTO artists (artist_name)
VALUES ('James Fauntleroy');

INSERT INTO artists (artist_name)
VALUES ('Philip Lawrence');

INSERT INTO artists (artist_name)
VALUES ('Jeremy Reeves');

INSERT INTO artists (artist_name)
VALUES ('Jonathan Yip');

INSERT INTO artists (artist_name)
VALUES ('Ray Romulus');

--------------------------- Chandelier--------------------------------------------------------------------------

INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('Sia', 'Sia.jpg', 'Sia.txt');

INSERT INTO artists (artist_name)
VALUES ('Jesse Shatkin');

--------------------------- Blank Space--------------------------------------------------------------------------

INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('Taylor Swift', 'Taylor Swift.jpg', 'Taylor Swift.txt');

INSERT INTO artists (artist_name)
VALUES ('Max Martin');

INSERT INTO artists (artist_name)
VALUES ('Shellback');

--------------------------- A Thousand Years--------------------------------------------------------------------------

INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('Christina Perri', 'Christina Perri.jpg', 'Christina Perri.txt');

INSERT INTO artists (artist_name)
VALUES ('David Hodges');

-------------------------- A Whole New World (End Title)---------------------------------------------------------------------------

INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('ZAYN', 'ZAYN.jpg', 'ZAYN.txt');

INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('Zhavia', 'Zhavia.jpg', 'Zhavia.txt');

INSERT INTO artists (artist_name)
VALUES ('Alan Menken');

INSERT INTO artists (artist_name)
VALUES ('Tim Rice');

INSERT INTO artists (artist_name)
VALUES ('Steve Wright');

INSERT INTO artists (artist_name)
VALUES ('Chandru');

------------------------------ Speechless-----------------------------------------------------------------------

INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('Naomi Scott', 'Naomi Scott.jpg', 'Naomi Scott.txt');

INSERT INTO artists (artist_name)
VALUES ('Benj Pasek');

INSERT INTO artists (artist_name)
VALUES ('Justin Paul');

INSERT INTO artists (artist_name)
VALUES ('Michael Kosarin');

------------------------------ Dancing Queen-----------------------------------------------------------------------

INSERT INTO artists (artist_name, artist_image)
VALUES ('Bjorn Ulvaeus', 'Bjorn Ulvaeus.jpg');

INSERT INTO artists (artist_name, artist_image)
VALUES ('Benny Andersson', 'Benny Andersson.jpg');

INSERT INTO artists (artist_name, artist_image)
VALUES ('Agnetha Faltskog', 'Agnetha Faltskog.jpg');

INSERT INTO artists (artist_name, artist_image)
VALUES ('Frida', 'Frida.jpg');

INSERT INTO artists (artist_name)
VALUES ('Sven Olof Walldoff');

------------------------------- Baby One More Time----------------------------------------------------------------------

INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('Britney Spears', 'Britney Spears.jpg', 'Britney Spears.txt');

------------------------------- Because of You----------------------------------------------------------------------

INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('Kelly Clarkson', 'Kelly Clarkson.jpg', 'Kelly Clarkson.txt');

INSERT INTO artists (artist_name)
VALUES ('Ben Moody');

INSERT INTO artists (artist_name)
VALUES ('David Campbell');

---------------------------------- I Really Like You-------------------------------------------------------------------

INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('Carly Rae Jepsen', 'Carly Rae Jepsen.jpg', 'Carly Rae Jepsen.txt');

INSERT INTO artists (artist_name)
VALUES ('Peter Svensson');

INSERT INTO artists (artist_name)
VALUES ('Jacob Kasher');

---------------------------------- Flashlight-------------------------------------------------------------------

INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('Jessie J', 'Jessie J.jpg', 'Jessie J.txt');

INSERT INTO artists (artist_name)
VALUES ('Sam Smith');

INSERT INTO artists (artist_name)
VALUES ('Jason Moore');

INSERT INTO artists (artist_name)
VALUES ('Christian Guzman');

--------------------------------- INTO the Unknown--------------------------------------------------------------------

INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('Idina Menzel', 'Idina Menzel.jpg', 'Idina Menzel.txt');

INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('AURORA', 'AURORA.jpg', 'AURORA.txt');

INSERT INTO artists (artist_name)
VALUES ('Robert Lopez');

---------------------------------- Show Yourself-------------------------------------------------------------------

INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('Evan Rachel Wood', 'Evan Rachel Wood.jpg', 'Evan Rachel Wood.txt');

INSERT INTO artists (artist_name)
VALUES ('Dave Metzger');

INSERT INTO artists (artist_name)
VALUES ('Stephen Oremus');

-------------------------------- Call Me Maybe---------------------------------------------------------------------

INSERT INTO artists (artist_name)
VALUES ('Josh Ramsay');

INSERT INTO artists (artist_name)
VALUES ('Tavish Crowe');

------------------------------ She''s So Gone-----------------------------------------------------------------------

INSERT INTO artists (artist_name)
VALUES ('Matthew Tishler');

INSERT INTO artists (artist_name)
VALUES ('Maria Christensen');

INSERT INTO artists (artist_name)
VALUES ('Shane Stevens');

----------------------------- Cruel Summer------------------------------------------------------------------------

INSERT INTO artists (artist_name)
VALUES ('Jack Antonoff');

INSERT INTO artists (artist_name)
VALUES ('Annie Clark');

--------------------------- One Last Time--------------------------------------------------------------------------

INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('Ariana Grande', 'Ariana Grande.jpg', 'Ariana Grande.txt');

INSERT INTO artists (artist_name)
VALUES ('David Guetta');

INSERT INTO artists (artist_name)
VALUES ('Carl Falk');

INSERT INTO artists (artist_name)
VALUES ('Giorgio Tuinfort');

INSERT INTO artists (artist_name)
VALUES ('Rami Yacoub');

INSERT INTO artists (artist_name)
VALUES ('Savan Kotecha');

----------------------------- Problem------------------------------------------------------------------------

INSERT INTO artists (artist_name)
VALUES ('Ilya');

INSERT INTO artists (artist_name)
VALUES ('Iggy Azalea');

--------------------------------- Outside--------------------------------------------------------------------

INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('Calvin Harris', 'Calvin Harris.jpg', 'Calvin Harris.txt');

INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('Ellie Goulding', 'Ellie Goulding.jpg', 'Ellie Goulding.txt');

--------------------------- 2002--------------------------------------------------------------------------

INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('Anne Marie', 'Anne Marie.jpg', 'Anne Marie.txt');

INSERT INTO artists (artist_name)
VALUES ('Ed Sheeran');

INSERT INTO artists (artist_name)
VALUES ('Julia Michaels');

INSERT INTO artists (artist_name)
VALUES ('Steve Mac');

INSERT INTO artists (artist_name)
VALUES ('Benjamin Levin');

--------------------------- Good Time--------------------------------------------------------------------------

INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('Owl City', 'Owl City.jpg', 'Owl City.txt');

INSERT INTO artists (artist_name)
VALUES ('Matthew Thiessen');

INSERT INTO artists (artist_name)
VALUES ('Adam Young');

INSERT INTO artists (artist_name)
VALUES ('Brian Lee');

--------------------------- Attention--------------------------------------------------------------------------

-- INSERT INTO artists (artist_name, artist_image, artist_description)
-- VALUES ('Charlie Puth', 'Charlie Puth.jpg', 'Charlie Puth.txt');

--------------------------- Whatever--------------------------------------------------------------------------

INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('Kygo', 'Kygo.jpg', 'Kygo.txt');

INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('Ava Max', 'Ava Max.jpg', 'Ava Max.txt');

INSERT INTO artists (artist_name)
VALUES ('Shakira');

--------------------------- When I Get Old--------------------------------------------------------------------------

INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('Christopher', 'Christopher.jpg', 'Christopher.txt');

INSERT INTO artists (artist_name, artist_image, artist_description)
VALUES ('청하', '청하.jpg', '청하.txt');

INSERT INTO artists (artist_name)
VALUES ('Dag Holtan Hartwig');

INSERT INTO artists (artist_name)
VALUES ('Jeppe Nissen');

--------------------------- Domino--------------------------------------------------------------------------

INSERT INTO artists (artist_name)
VALUES ('Claude Kelly');

INSERT INTO artists (artist_name)
VALUES ('Jessica Cornish');

INSERT INTO artists (artist_name)
VALUES ('Lukasz Gottwald');

INSERT INTO artists (artist_name)
VALUES ('Henry Walter');



-------------------------------------- GROUPS 테이블----------------------------------------------------------
------------------------- Dancing Queen----------------------------------------------------------------------------
INSERT INTO `groups` (group_name, group_image, group_description)
VALUES ('ABBA', 'ABBA.jpg', 'ABBA.text');



-------------------------------------- GROUP_MEMBERS 테이블----------------------------------------------------------
------------------------- Dancing Queen----------------------------------------------------------------------------
INSERT INTO group_members(group_id, artist_id)
VALUES ((SELECT group_id FROM `groups` WHERE group_name = 'ABBA'), (SELECT artist_id FROM artists WHERE artist_name = 'Bjorn Ulvaeus'));

INSERT INTO group_members(group_id, artist_id)
VALUES ((SELECT group_id FROM `groups` WHERE group_name = 'ABBA'), (SELECT artist_id FROM artists WHERE artist_name = 'Benny Andersson'));

INSERT INTO group_members(group_id, artist_id)
VALUES ((SELECT group_id FROM `groups` WHERE group_name = 'ABBA'), (SELECT artist_id FROM artists WHERE artist_name = 'Agnetha Faltskog'));

INSERT INTO group_members(group_id, artist_id)
VALUES ((SELECT group_id FROM `groups` WHERE group_name = 'ABBA'), (SELECT artist_id FROM artists WHERE artist_name = 'Frida'));

INSERT INTO group_members(group_id, artist_id)
VALUES ((SELECT group_id FROM `groups` WHERE group_name = 'ABBA'), (SELECT artist_id FROM artists WHERE artist_name = 'Sven Olof Walldoff'));



-------------------------------------- ARTIST_ROLES 테이블----------------------------------------------------------
------------------------- That’s What I Like----------------------------------------------------------------------------

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Bruno Mars'), (SELECT song_id FROM songs WHERE title = 'That’s What I Like'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'James Fauntleroy'), (SELECT song_id FROM songs WHERE title = 'That’s What I Like'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'James Fauntleroy'), (SELECT song_id FROM songs WHERE title = 'That’s What I Like'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Philip Lawrence'), (SELECT song_id FROM songs WHERE title = 'That’s What I Like'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Philip Lawrence'), (SELECT song_id FROM songs WHERE title = 'That’s What I Like'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Jeremy Reeves'), (SELECT song_id FROM songs WHERE title = 'That’s What I Like'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Jeremy Reeves'), (SELECT song_id FROM songs WHERE title = 'That’s What I Like'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Jonathan Yip'), (SELECT song_id FROM songs WHERE title = 'That’s What I Like'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Jonathan Yip'), (SELECT song_id FROM songs WHERE title = 'That’s What I Like'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Ray Romulus'), (SELECT song_id FROM songs WHERE title = 'That’s What I Like'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Ray Romulus'), (SELECT song_id FROM songs WHERE title = 'That’s What I Like'), 30);

------------------------- Chandelier----------------------------------------------------------------------------

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Sia'), (SELECT song_id FROM songs WHERE title = 'Chandelier'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Sia'), (SELECT song_id FROM songs WHERE title = 'Chandelier'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Sia'), (SELECT song_id FROM songs WHERE title = 'Chandelier'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Jesse Shatkin'), (SELECT song_id FROM songs WHERE title = 'Chandelier'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Jesse Shatkin'), (SELECT song_id FROM songs WHERE title = 'Chandelier'), 30);

------------------------- Blank Space----------------------------------------------------------------------------

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Taylor Swift'), (SELECT song_id FROM songs WHERE title = 'Blank Space'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Taylor Swift'), (SELECT song_id FROM songs WHERE title = 'Blank Space'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Taylor Swift'), (SELECT song_id FROM songs WHERE title = 'Blank Space'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Max Martin'), (SELECT song_id FROM songs WHERE title = 'Blank Space'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Max Martin'), (SELECT song_id FROM songs WHERE title = 'Blank Space'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Shellback'), (SELECT song_id FROM songs WHERE title = 'Blank Space'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Shellback'), (SELECT song_id FROM songs WHERE title = 'Blank Space'), 30);

------------------------- A Thousand Years----------------------------------------------------------------------------

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Christina Perri'), (SELECT song_id FROM songs WHERE title = 'A Thousand Years'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Christina Perri'), (SELECT song_id FROM songs WHERE title = 'A Thousand Years'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Christina Perri'), (SELECT song_id FROM songs WHERE title = 'A Thousand Years'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'David Hodges'), (SELECT song_id FROM songs WHERE title = 'A Thousand Years'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'David Hodges'), (SELECT song_id FROM songs WHERE title = 'A Thousand Years'), 30);

------------------------- A Whole New World (End Title)------------------------------------------------------------------

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'ZAYN'), (SELECT song_id FROM songs WHERE title = 'A Whole New World (End Title)'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Zhavia'), (SELECT song_id FROM songs WHERE title = 'A Whole New World (End Title)'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Alan Menken'), (SELECT song_id FROM songs WHERE title = 'A Whole New World (End Title)'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Tim Rice'), (SELECT song_id FROM songs WHERE title = 'A Whole New World (End Title)'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Steve Wright'), (SELECT song_id FROM songs WHERE title = 'A Whole New World (End Title)'), 40);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Chandru'), (SELECT song_id FROM songs WHERE title = 'A Whole New World (End Title)'), 40);

------------------------- Speechless----------------------------------------------------------------------------

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Naomi Scott'), (SELECT song_id FROM songs WHERE title = 'Speechless'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Alan Menken'), (SELECT song_id FROM songs WHERE title = 'Speechless'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Benj Pasek'), (SELECT song_id FROM songs WHERE title = 'Speechless'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Benj Pasek'), (SELECT song_id FROM songs WHERE title = 'Speechless'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Justin Paul'), (SELECT song_id FROM songs WHERE title = 'Speechless'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Justin Paul'), (SELECT song_id FROM songs WHERE title = 'Speechless'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Michael Kosarin'), (SELECT song_id FROM songs WHERE title = 'Speechless'), 40);

------------------------- Dancing Queen----------------------------------------------------------------------------

INSERT INTO artist_roles (artist_id, group_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Bjorn Ulvaeus'), (SELECT group_id FROM `groups` WHERE group_name = 'ABBA'), (SELECT song_id FROM songs WHERE title = 'Dancing Queen'), 10);

INSERT INTO artist_roles (artist_id, group_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Bjorn Ulvaeus'), (SELECT group_id FROM `groups` WHERE group_name = 'ABBA'), (SELECT song_id FROM songs WHERE title = 'Dancing Queen'), 20);

INSERT INTO artist_roles (artist_id, group_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Bjorn Ulvaeus'), (SELECT group_id FROM `groups` WHERE group_name = 'ABBA'), (SELECT song_id FROM songs WHERE title = 'Dancing Queen'), 30);

INSERT INTO artist_roles (artist_id, group_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Bjorn Ulvaeus'), (SELECT group_id FROM `groups` WHERE group_name = 'ABBA'), (SELECT song_id FROM songs WHERE title = 'Dancing Queen'), 40);

INSERT INTO artist_roles (artist_id, group_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Benny Andersson'), (SELECT group_id FROM `groups` WHERE group_name = 'ABBA'), (SELECT song_id FROM songs WHERE title = 'Dancing Queen'), 10);

INSERT INTO artist_roles (artist_id, group_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Benny Andersson'), (SELECT group_id FROM `groups` WHERE group_name = 'ABBA'), (SELECT song_id FROM songs WHERE title = 'Dancing Queen'), 20);

INSERT INTO artist_roles (artist_id, group_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Benny Andersson'), (SELECT group_id FROM `groups` WHERE group_name = 'ABBA'), (SELECT song_id FROM songs WHERE title = 'Dancing Queen'), 30);

INSERT INTO artist_roles (artist_id, group_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Benny Andersson'), (SELECT group_id FROM `groups` WHERE group_name = 'ABBA'), (SELECT song_id FROM songs WHERE title = 'Dancing Queen'), 40);

INSERT INTO artist_roles (artist_id, group_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Agnetha Faltskog'), (SELECT group_id FROM `groups` WHERE group_name = 'ABBA'), (SELECT song_id FROM songs WHERE title = 'Dancing Queen'), 10);

INSERT INTO artist_roles (artist_id, group_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Frida'), (SELECT group_id FROM `groups` WHERE group_name = 'ABBA'), (SELECT song_id FROM songs WHERE title = 'Dancing Queen'), 10);

INSERT INTO artist_roles (artist_id, group_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Sven Olof Walldoff'), (SELECT group_id FROM `groups` WHERE group_name = 'ABBA'), (SELECT song_id FROM songs WHERE title = 'Dancing Queen'), 40);


------------------------- Baby One More Time----------------------------------------------------------------------------

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Britney Spears'), (SELECT song_id FROM songs WHERE title = 'Baby One More Time'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Max Martin'), (SELECT song_id FROM songs WHERE title = 'Baby One More Time'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Max Martin'), (SELECT song_id FROM songs WHERE title = 'Baby One More Time'), 30);

------------------------- Because of You----------------------------------------------------------------------------

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Kelly Clarkson'), (SELECT song_id FROM songs WHERE title = 'Because of You'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Kelly Clarkson'), (SELECT song_id FROM songs WHERE title = 'Because of You'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Kelly Clarkson'), (SELECT song_id FROM songs WHERE title = 'Because of You'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'David Hodges'), (SELECT song_id FROM songs WHERE title = 'Because of You'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'David Hodges'), (SELECT song_id FROM songs WHERE title = 'Because of You'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'David Hodges'), (SELECT song_id FROM songs WHERE title = 'Because of You'), 40);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Ben Moody'), (SELECT song_id FROM songs WHERE title = 'Because of You'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Ben Moody'), (SELECT song_id FROM songs WHERE title = 'Because of You'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'David Campbell'), (SELECT song_id FROM songs WHERE title = 'Because of You'), 40);

------------------------- I Really Like You----------------------------------------------------------------------------

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Carly Rae Jepsen'), (SELECT song_id FROM songs WHERE title = 'I Really Like You'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Carly Rae Jepsen'), (SELECT song_id FROM songs WHERE title = 'I Really Like You'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Carly Rae Jepsen'), (SELECT song_id FROM songs WHERE title = 'I Really Like You'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Peter Svensson'), (SELECT song_id FROM songs WHERE title = 'I Really Like You'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Peter Svensson'), (SELECT song_id FROM songs WHERE title = 'I Really Like You'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Jacob Kasher'), (SELECT song_id FROM songs WHERE title = 'I Really Like You'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Jacob Kasher'), (SELECT song_id FROM songs WHERE title = 'I Really Like You'), 30);

------------------------- Flashlight----------------------------------------------------------------------------

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Jessie J'), (SELECT song_id FROM songs WHERE title = 'Flashlight'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Sam Smith'), (SELECT song_id FROM songs WHERE title = 'Flashlight'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Sam Smith'), (SELECT song_id FROM songs WHERE title = 'Flashlight'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Jason Moore'), (SELECT song_id FROM songs WHERE title = 'Flashlight'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Jason Moore'), (SELECT song_id FROM songs WHERE title = 'Flashlight'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Sia'), (SELECT song_id FROM songs WHERE title = 'Flashlight'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Sia'), (SELECT song_id FROM songs WHERE title = 'Flashlight'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Christian Guzman'), (SELECT song_id FROM songs WHERE title = 'Flashlight'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Christian Guzman'), (SELECT song_id FROM songs WHERE title = 'Flashlight'), 30);

------------------------- INTO the Unknown----------------------------------------------------------------------------

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Idina Menzel'), (SELECT song_id FROM songs WHERE title = 'INTO the Unknown'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'AURORA'), (SELECT song_id FROM songs WHERE title = 'INTO the Unknown'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Robert Lopez'), (SELECT song_id FROM songs WHERE title = 'INTO the Unknown'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Robert Lopez'), (SELECT song_id FROM songs WHERE title = 'INTO the Unknown'), 30);

------------------------- Show Yourself----------------------------------------------------------------------------

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Idina Menzel'), (SELECT song_id FROM songs WHERE title = 'Show Yourself'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Evan Rachel Wood'), (SELECT song_id FROM songs WHERE title = 'Show Yourself'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Robert Lopez'), (SELECT song_id FROM songs WHERE title = 'Show Yourself'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Robert Lopez'), (SELECT song_id FROM songs WHERE title = 'Show Yourself'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Dave Metzger'), (SELECT song_id FROM songs WHERE title = 'Show Yourself'), 40);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Stephen Oremus'), (SELECT song_id FROM songs WHERE title = 'Show Yourself'), 40);

------------------------- Call Me Maybe----------------------------------------------------------------------------

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Carly Rae Jepsen'), (SELECT song_id FROM songs WHERE title = 'Call Me Maybe'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Carly Rae Jepsen'), (SELECT song_id FROM songs WHERE title = 'Call Me Maybe'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Carly Rae Jepsen'), (SELECT song_id FROM songs WHERE title = 'Call Me Maybe'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Josh Ramsay'), (SELECT song_id FROM songs WHERE title = 'Call Me Maybe'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Josh Ramsay'), (SELECT song_id FROM songs WHERE title = 'Call Me Maybe'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Tavish Crowe'), (SELECT song_id FROM songs WHERE title = 'Call Me Maybe'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Tavish Crowe'), (SELECT song_id FROM songs WHERE title = 'Call Me Maybe'), 30);

------------------------- She''s So Gone----------------------------------------------------------------------------

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Naomi Scott'), (SELECT song_id FROM songs WHERE title = 'She''s So Gone'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Matthew Tishler'), (SELECT song_id FROM songs WHERE title = 'She''s So Gone'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Matthew Tishler'), (SELECT song_id FROM songs WHERE title = 'She''s So Gone'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Maria Christensen'), (SELECT song_id FROM songs WHERE title = 'She''s So Gone'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Maria Christensen'), (SELECT song_id FROM songs WHERE title = 'She''s So Gone'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Shane Stevens'), (SELECT song_id FROM songs WHERE title = 'She''s So Gone'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Shane Stevens'), (SELECT song_id FROM songs WHERE title = 'She''s So Gone'), 30);

------------------------- Cruel Summer----------------------------------------------------------------------------

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Taylor Swift'), (SELECT song_id FROM songs WHERE title = 'Cruel Summer'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Taylor Swift'), (SELECT song_id FROM songs WHERE title = 'Cruel Summer'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Taylor Swift'), (SELECT song_id FROM songs WHERE title = 'Cruel Summer'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Jack Antonoff'), (SELECT song_id FROM songs WHERE title = 'Cruel Summer'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Jack Antonoff'), (SELECT song_id FROM songs WHERE title = 'Cruel Summer'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Annie Clark'), (SELECT song_id FROM songs WHERE title = 'Cruel Summer'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Annie Clark'), (SELECT song_id FROM songs WHERE title = 'Cruel Summer'), 30);

------------------------- One Last Time----------------------------------------------------------------------------

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Ariana Grande'), (SELECT song_id FROM songs WHERE title = 'One Last Time'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'David Guetta'), (SELECT song_id FROM songs WHERE title = 'One Last Time'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'David Guetta'), (SELECT song_id FROM songs WHERE title = 'One Last Time'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Carl Falk'), (SELECT song_id FROM songs WHERE title = 'One Last Time'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Carl Falk'), (SELECT song_id FROM songs WHERE title = 'One Last Time'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Giorgio Tuinfort'), (SELECT song_id FROM songs WHERE title = 'One Last Time'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Giorgio Tuinfort'), (SELECT song_id FROM songs WHERE title = 'One Last Time'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Rami Yacoub'), (SELECT song_id FROM songs WHERE title = 'One Last Time'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Rami Yacoub'), (SELECT song_id FROM songs WHERE title = 'One Last Time'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Savan Kotecha'), (SELECT song_id FROM songs WHERE title = 'One Last Time'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Savan Kotecha'), (SELECT song_id FROM songs WHERE title = 'One Last Time'), 30);

------------------------- Problem----------------------------------------------------------------------------

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Ariana Grande'), (SELECT song_id FROM songs WHERE title = 'Problem'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Max Martin'), (SELECT song_id FROM songs WHERE title = 'Problem'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Max Martin'), (SELECT song_id FROM songs WHERE title = 'Problem'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Savan Kotecha'), (SELECT song_id FROM songs WHERE title = 'Problem'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Savan Kotecha'), (SELECT song_id FROM songs WHERE title = 'Problem'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Ilya'), (SELECT song_id FROM songs WHERE title = 'Problem'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Ilya'), (SELECT song_id FROM songs WHERE title = 'Problem'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Iggy Azalea'), (SELECT song_id FROM songs WHERE title = 'Problem'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Iggy Azalea'), (SELECT song_id FROM songs WHERE title = 'Problem'), 30);

------------------------- Outside----------------------------------------------------------------------------

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Calvin Harris'), (SELECT song_id FROM songs WHERE title = 'Outside'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Calvin Harris'), (SELECT song_id FROM songs WHERE title = 'Outside'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Calvin Harris'), (SELECT song_id FROM songs WHERE title = 'Outside'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Ellie Goulding'), (SELECT song_id FROM songs WHERE title = 'Outside'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Ellie Goulding'), (SELECT song_id FROM songs WHERE title = 'Outside'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Ellie Goulding'), (SELECT song_id FROM songs WHERE title = 'Outside'), 30);

------------------------- 2002----------------------------------------------------------------------------

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Anne Marie'), (SELECT song_id FROM songs WHERE title = '2002'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Anne Marie'), (SELECT song_id FROM songs WHERE title = '2002'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Anne Marie'), (SELECT song_id FROM songs WHERE title = '2002'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Ed Sheeran'), (SELECT song_id FROM songs WHERE title = '2002'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Ed Sheeran'), (SELECT song_id FROM songs WHERE title = '2002'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Julia Michaels'), (SELECT song_id FROM songs WHERE title = '2002'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Julia Michaels'), (SELECT song_id FROM songs WHERE title = '2002'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Steve Mac'), (SELECT song_id FROM songs WHERE title = '2002'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Steve Mac'), (SELECT song_id FROM songs WHERE title = '2002'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Benjamin Levin'), (SELECT song_id FROM songs WHERE title = '2002'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Benjamin Levin'), (SELECT song_id FROM songs WHERE title = '2002'), 30);

------------------------- Good Time----------------------------------------------------------------------------

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Owl City'), (SELECT song_id FROM songs WHERE title = 'Good Time'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Matthew Thiessen'), (SELECT song_id FROM songs WHERE title = 'Good Time'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Matthew Thiessen'), (SELECT song_id FROM songs WHERE title = 'Good Time'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Adam Young'), (SELECT song_id FROM songs WHERE title = 'Good Time'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Adam Young'), (SELECT song_id FROM songs WHERE title = 'Good Time'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Brian Lee'), (SELECT song_id FROM songs WHERE title = 'Good Time'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Brian Lee'), (SELECT song_id FROM songs WHERE title = 'Good Time'), 30);

------------------------- Attention----------------------------------------------------------------------------

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Charlie Puth'), (SELECT song_id FROM songs WHERE title = 'Attention'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Charlie Puth'), (SELECT song_id FROM songs WHERE title = 'Attention'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Charlie Puth'), (SELECT song_id FROM songs WHERE title = 'Attention'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Jacob Kasher'), (SELECT song_id FROM songs WHERE title = 'Attention'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Jacob Kasher'), (SELECT song_id FROM songs WHERE title = 'Attention'), 30);

------------------------- Whatever----------------------------------------------------------------------------

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Kygo'), (SELECT song_id FROM songs WHERE title = 'Whatever'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Kygo'), (SELECT song_id FROM songs WHERE title = 'Whatever'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Kygo'), (SELECT song_id FROM songs WHERE title = 'Whatever'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Ava Max'), (SELECT song_id FROM songs WHERE title = 'Whatever'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Shakira'), (SELECT song_id FROM songs WHERE title = 'Whatever'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Shakira'), (SELECT song_id FROM songs WHERE title = 'Whatever'), 30);

------------------------- When I Get Old----------------------------------------------------------------------------

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Christopher'), (SELECT song_id FROM songs WHERE title = 'When I Get Old'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Christopher'), (SELECT song_id FROM songs WHERE title = 'When I Get Old'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = '청하'), (SELECT song_id FROM songs WHERE title = 'When I Get Old'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Dag Holtan Hartwig'), (SELECT song_id FROM songs WHERE title = 'When I Get Old'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Jeppe Nissen'), (SELECT song_id FROM songs WHERE title = 'When I Get Old'), 30);

------------------------- Domino----------------------------------------------------------------------------

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Jessie J'), (SELECT song_id FROM songs WHERE title = 'Domino'), 10);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Max Martin'), (SELECT song_id FROM songs WHERE title = 'Domino'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Max Martin'), (SELECT song_id FROM songs WHERE title = 'Domino'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Claude Kelly'), (SELECT song_id FROM songs WHERE title = 'Domino'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Claude Kelly'), (SELECT song_id FROM songs WHERE title = 'Domino'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Jessica Cornish'), (SELECT song_id FROM songs WHERE title = 'Domino'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Jessica Cornish'), (SELECT song_id FROM songs WHERE title = 'Domino'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Lukasz Gottwald'), (SELECT song_id FROM songs WHERE title = 'Domino'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Lukasz Gottwald'), (SELECT song_id FROM songs WHERE title = 'Domino'), 30);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Henry Walter'), (SELECT song_id FROM songs WHERE title = 'Domino'), 20);

INSERT INTO artist_roles (artist_id, song_id, role_id)
VALUES ((SELECT artist_id FROM artists WHERE artist_name = 'Henry Walter'), (SELECT song_id FROM songs WHERE title = 'Domino'), 30);



-------------------------------------- SONG_GENRE 테이블----------------------------------------------------------
------------------------- That’s What I Like----------------------------------------------------------------------------
INSERT INTO song_genre (song_id, genre_id)
VALUES ((SELECT song_id FROM songs WHERE title = 'That’s What I Like'), 20);

------------------------- Chandelier----------------------------------------------------------------------------
INSERT INTO song_genre (song_id, genre_id)
VALUES ((SELECT song_id FROM songs WHERE title = 'Chandelier'), 20);

------------------------- Blank Space----------------------------------------------------------------------------
INSERT INTO song_genre (song_id, genre_id)
VALUES ((SELECT song_id FROM songs WHERE title = 'Blank Space'), 20);

------------------------- A Thousand Years----------------------------------------------------------------------------
INSERT INTO song_genre (song_id, genre_id)
VALUES ((SELECT song_id FROM songs WHERE title = 'A Thousand Years'), 20);

------------------------- A Whole New World (End Title)------------------------------------------------------------------
INSERT INTO song_genre (song_id, genre_id)
VALUES ((SELECT song_id FROM songs WHERE title = 'A Whole New World (End Title)'), 70);

------------------------- Speechless----------------------------------------------------------------------------
INSERT INTO song_genre (song_id, genre_id)
VALUES ((SELECT song_id FROM songs WHERE title = 'Speechless'), 70);

------------------------- Dancing Queen----------------------------------------------------------------------------
INSERT INTO song_genre (song_id, genre_id)
VALUES ((SELECT song_id FROM songs WHERE title = 'Dancing Queen'), 20);

------------------------- Baby One More Time----------------------------------------------------------------------------
INSERT INTO song_genre (song_id, genre_id)
VALUES ((SELECT song_id FROM songs WHERE title = 'Baby One More Time'), 20);

------------------------- Because of You----------------------------------------------------------------------------
INSERT INTO song_genre (song_id, genre_id)
VALUES ((SELECT song_id FROM songs WHERE title = 'Because of You'), 20);

------------------------- I Really Like You----------------------------------------------------------------------------
INSERT INTO song_genre (song_id, genre_id)
VALUES ((SELECT song_id FROM songs WHERE title = 'I Really Like You'), 20);

------------------------- Flashlight----------------------------------------------------------------------------
INSERT INTO song_genre (song_id, genre_id)
VALUES ((SELECT song_id FROM songs WHERE title = 'Flashlight'), 70);

------------------------- INTO the Unknown----------------------------------------------------------------------------
INSERT INTO song_genre (song_id, genre_id)
VALUES ((SELECT song_id FROM songs WHERE title = 'INTO the Unknown'), 70);

------------------------- Show Yourself----------------------------------------------------------------------------
INSERT INTO song_genre (song_id, genre_id)
VALUES ((SELECT song_id FROM songs WHERE title = 'Show Yourself'), 70);

------------------------- Call Me Maybe----------------------------------------------------------------------------
INSERT INTO song_genre (song_id, genre_id)
VALUES ((SELECT song_id FROM songs WHERE title = 'Call Me Maybe'), 20);

------------------------- She''s So Gone ----------------------------------------------------------------------------
INSERT INTO song_genre (song_id, genre_id)
VALUES ((SELECT song_id FROM songs WHERE title = 'She''s So Gone'), 70);

------------------------- Cruel Summer----------------------------------------------------------------------------
INSERT INTO song_genre (song_id, genre_id)
VALUES ((SELECT song_id FROM songs WHERE title = 'Cruel Summer'), 20);

------------------------- One Last Time----------------------------------------------------------------------------
INSERT INTO song_genre (song_id, genre_id)
VALUES ((SELECT song_id FROM songs WHERE title = 'One Last Time'), 20);

------------------------- Problem----------------------------------------------------------------------------
INSERT INTO song_genre (song_id, genre_id)
VALUES ((SELECT song_id FROM songs WHERE title = 'Problem'), 20);

------------------------- Outside----------------------------------------------------------------------------
INSERT INTO song_genre (song_id, genre_id)
VALUES ((SELECT song_id FROM songs WHERE title = 'Outside'), 20);

------------------------- 2002----------------------------------------------------------------------------
INSERT INTO song_genre (song_id, genre_id)
VALUES ((SELECT song_id FROM songs WHERE title = '2002'), 20);

------------------------- Good Time----------------------------------------------------------------------------
INSERT INTO song_genre (song_id, genre_id)
VALUES ((SELECT song_id FROM songs WHERE title = 'Good Time'), 20);

------------------------- Attention----------------------------------------------------------------------------
INSERT INTO song_genre (song_id, genre_id)
VALUES ((SELECT song_id FROM songs WHERE title = 'Attention'), 20);

------------------------- Whatever----------------------------------------------------------------------------
INSERT INTO song_genre (song_id, genre_id)
VALUES ((SELECT song_id FROM songs WHERE title = 'Whatever'), 20);

------------------------- When I Get Old----------------------------------------------------------------------------
INSERT INTO song_genre (song_id, genre_id)
VALUES ((SELECT song_id FROM songs WHERE title = 'When I Get Old'), 20);

------------------------- Domino----------------------------------------------------------------------------
INSERT INTO song_genre (song_id, genre_id)
VALUES ((SELECT song_id FROM songs WHERE title = 'Domino'), 20);

COMMIT;

