
-- USE restboot; -- 공용 DB 스키마
USE test; -- 개인 DB 스키마

/*
 * 테이블 20개 (컬럼 개수)
 *      albums (5개),		songs (6개),			code_master (2개),	role_code (3개),			genre_code (3개),
 *      song_genre (2개),	title_songs (2개),	artists (4개),		groups (3개),			group_members (2개)
 *		artist_roles (4개),	users (11개),		user_roles(2개),		del_users (2개),			likes (2개),		
 *      album_likes (2개),	comments (6개),		playlists (3개),		playlist_songs (3개),    pur_users (2개)  
 *                
 * 고유키(PK) 10개
 *      album_id (albums 테이블),      song_id (songs 테이블),        code_id (code_master 테이블),
 *      role_id (role_code 테이블),    genre_id (genre_code 테이블),  artist_id (artists 테이블),
 *		group_id (groups 테이블), 	id (users 테이블), 	c_id (comments 테이블), 	p_list_id (playlists 테이블) 
 *                                 
 */

-- ENGINE=InnoDB: MySQL 스토리지 엔진을 InnoDB로 설정 (데이터 무결성 보장, 외래 키 지원)
-- DEFAULT CHARSET=utf8mb4: 기본 문자 집합을 UTF-8로 설정 (이모지를 포함한 넓은 범위의 유니코드 문자 지원)
-- COLLATE=utf8mb4_unicode_ci: 대소문자를 구분하지 않는 유니코드 정렬 규칙 사용

---------------------------------------- 앨범 테이블 ----------------------------------------
CREATE TABLE
	albums ( -- 앨범 (컬럼 5개)
		album_id 			INT AUTO_INCREMENT, -- 앨범 번호 (5자리, 자동 생성)
		album_name 			VARCHAR(200), -- 앨범 이름 (200글자)
		album_image 		VARCHAR(200), -- 앨범 표지 이미지 (200바이트)
		album_type 			VARCHAR(200) NOT NULL, -- 앨범 종류 (200글자, NOT NULL)
		album_release_date 	DATE, -- 발매일 (날짜 - 시분초 표시 안 함)
							PRIMARY KEY (album_id) -- 앨범 번호 (고유키)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

---------------------------------------- 음악 테이블 ----------------------------------------
CREATE TABLE
	songs ( -- 음악 (컬럼 6개)
		song_id 				INT AUTO_INCREMENT, -- 음악 번호 (5자리, 자동 생성)
		album_id 				INT, -- 앨범 번호 (5자리)
		title 					VARCHAR(200) NOT NULL, -- 음악 제목 (200글자, 낫 널)
		song_path 				VARCHAR(200) NOT NULL, -- 음악 파일 경로 (200바이트, 낫 널)
		lyrics 					VARCHAR(200) NOT NULL, -- 음악 가사 (200바이트, 낫 널)
		video_link 				VARCHAR(200), -- 뮤비 링크 (200바이트)
								PRIMARY KEY (song_id), -- 음악 번호 (고유키)
		FOREIGN KEY (album_id) REFERENCES albums(album_id) ON DELETE CASCADE -- 앨범 번호 (외래키)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

---------------------------------------- 코드 마스터 테이블 ----------------------------------------
CREATE TABLE
	code_master ( -- 코드 마스터 (컬럼 2개)
		code_id 	INT, -- 코드 번호 (5자리)
		code_name 	VARCHAR(200) NOT NULL, -- 코드 네임 (200글자, 낫 널)
						-- code_id: 1, code_name:roles
						-- code_id: 2, code_name:genre
					PRIMARY KEY (code_id) -- 역할 번호 (고유키)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

---------------------------------------- 역할 코드 테이블 ----------------------------------------
CREATE TABLE
	role_code ( -- 역할 코드 (컬럼 3개)
		code_id 				INT, -- 코드 번호 (5자리)
		role_name 				VARCHAR(200) NOT NULL, -- 역할 이름 (200글자, 낫 널)
		role_id 				INT, -- 역할 번호 (5자리)
		FOREIGN KEY (code_id) 	REFERENCES code_master(code_id), -- 코드 번호 (외래키)
								PRIMARY KEY (role_id) -- 역할 번호 (고유키)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

---------------------------------------- 장르 코드 테이블 ----------------------------------------
CREATE TABLE
	genre_code ( -- 장르 코드 (컬럼 3개)
		code_id 				INT, -- 코드 번호 (5자리)
		genre_name 				VARCHAR(200) NOT NULL, -- 장르 이름 (200글자, 낫 널)
		genre_id 				INT, -- 장르 번호 (5자리)
		FOREIGN KEY (code_id) 	REFERENCES code_master(code_id), -- 코드 번호 (외래키)
								PRIMARY KEY (genre_id) -- 장르 번호 (고유키)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

---------------------------------------- 음악 장르 테이블 ----------------------------------------
CREATE TABLE
	song_genre ( -- 음악 장르 (컬럼 2개)
		song_id 				INT, -- 음악 번호 (5자리)
		genre_id 				INT, -- 장르 번호 (5자리)
		FOREIGN KEY (song_id) 	REFERENCES songs(song_id) ON DELETE CASCADE, -- 음악 번호 (외래키)
		FOREIGN KEY (genre_id) 	REFERENCES genre_code(genre_id) ON DELETE CASCADE -- 장르 번호 (외래키)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

---------------------------------------- 타이틀 곡 테이블 ----------------------------------------
CREATE TABLE
	title_songs ( -- 타이틀 곡 (컬럼 2개)
		album_id 				INT, -- 앨범 번호 (5자리)
		song_id 				INT, -- 음악 번호 (5자리)
		FOREIGN KEY (album_id) 	REFERENCES albums(album_id) ON DELETE CASCADE, -- 앨범 번호 (외래키)
		FOREIGN KEY (song_id) 	REFERENCES songs(song_id) ON DELETE CASCADE -- 음악 번호 (외래키)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

---------------------------------------- 아티스트 테이블 ----------------------------------------
CREATE TABLE
	artists ( -- 아티스트 (컬럼 4개)
		artist_id 			INT AUTO_INCREMENT, -- 아티스트 번호 (5자리, 자동 생성)
		artist_name 		VARCHAR(200) NOT NULL, -- 아티스트 이름 (200글자, 낫 널)
		artist_image 		VARCHAR(200), -- 아티스트 이미지 (200바이트)
		artist_description 	VARCHAR(200), -- 아티스트 설명 (200바이트) 이 적혀져있는 txt파일 경로
							PRIMARY KEY (artist_id) -- 아티스트 번호 (고유키)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--------------------------------- 그룹 정보 테이블 ------------------------
CREATE TABLE
	`groups` ( -- 그룹 정보 (컬럼 3개)
		group_id 			INT AUTO_INCREMENT, -- 그룹 번호 (5자리, 자동 생성)
		group_name 			VARCHAR(200) NOT NULL, -- 그룹 이름 (200글자, 낫 널))
		group_description 	VARCHAR(200), -- 그룹 설명 (200바이트)
							PRIMARY KEY (group_id) -- 그룹 번호 (고유키)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

---------------------------------- 그룹 멤버 테이블 -------------------------------
CREATE TABLE
	group_members ( -- 그룹 멤버 (컬럼 2개)
		group_id 					INT, -- 그룹 번호 (5자리)
		artist_id 					INT, -- 아티스트 번호 (5자리)
									PRIMARY KEY (group_id, artist_id), -- 복합키 (고유키)
		FOREIGN KEY (group_id) 		REFERENCES `groups`(group_id) ON DELETE CASCADE, -- 그룹 번호 (외래키)
		FOREIGN KEY (artist_id) 	REFERENCES artists(artist_id) ON DELETE CASCADE -- 아티스트 번호 (외래키)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

---------------------------------------- 아티스트 참여 음원 테이블 ----------------------------------------
CREATE TABLE
	artist_roles ( -- 아티스트 참여 음원 (컬럼 4개)
		artist_id 				INT, -- 아티스트 번호 (5자리)
		group_id 				INT NULL, -- 그룹 번호 (5자리, NULL 가능) (nullable, 솔로 활동 시 null 가능)
		song_id 				INT, -- 음악 번호 (5자리)
		role_id 				INT, -- 역할 번호 (5자리)
								PRIMARY KEY (artist_id, song_id, role_id), -- 복합키 (고유키)
		FOREIGN KEY (artist_id) REFERENCES artists(artist_id) ON DELETE CASCADE, -- 아티스트 번호 (외래키)
		FOREIGN KEY (group_id) 	REFERENCES `groups`(group_id) ON DELETE CASCADE, -- 그룹 번호 (외래키), NULL 가능
		FOREIGN KEY (song_id) 	REFERENCES songs(song_id) ON DELETE CASCADE, -- 음악 번호 (외래키)
		FOREIGN KEY (role_id) 	REFERENCES role_code(role_id) ON DELETE CASCADE -- 역할 번호 (외래키)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

---------------------------------------- 회원 테이블 ----------------------------------------
CREATE TABLE
	users ( -- 회원 (컬럼 11개)
		id 					INT AUTO_INCREMENT, -- 회원 번호 (5자리, 자동 생성)
		user_name 			VARCHAR(200) NOT NULL, -- 회원 이름 (200글자, 낫 널)
		user_id 			VARCHAR(200) NOT NULL, -- 회원 아이디 (200바이트, 낫 널)
		password 			VARCHAR(200) NOT NULL, -- 비밀번호 (200바이트, 낫 널)
		email 				VARCHAR(200) NOT NULL, -- 이메일 (200바이트, 낫 널)
		nickname 			VARCHAR(200) NOT NULL, -- 닉네임 (200글자, 낫 널)
		user_profile 		VARCHAR(200), -- 회원 프로필 (200바이트)
		hint_question 		VARCHAR(200), -- 힌트질문 (200글자)
		hint_answer 		VARCHAR(200), -- 힌트답변 (200글자)
		is_active 			INT DEFAULT 1, -- 활성화 번호 (5자리, 기본값 1)
		deactivated_until 	DATE, -- 비활성 만료일 (날짜 - 시분초 표시 안 함)
							PRIMARY KEY (id), -- 회원 번호 (고유키)
							UNIQUE (user_id), -- 회원 아이디 (유니크)
							UNIQUE (email), -- 이메일 (유니크)
							UNIQUE (nickname) -- 닉네임 (유니크)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

---------------------------------------- 회원 역할 테이블 ----------------------------------------
CREATE TABLE
	user_roles ( -- 회원 역할 (컬럼 2개)
		user_id 				INT, -- 회원 번호 (5자리)
		roles 					VARCHAR(200), -- 회원 역할 (200자)
		FOREIGN KEY (user_id) 	REFERENCES users(id) ON DELETE CASCADE -- 회원 번호 (외래키)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


---------------------------------------- 탈퇴 회원 테이블 ----------------------------------------
CREATE TABLE
	del_users ( -- 탈퇴 회원 (컬럼 2개)
		id 						INT, -- 회원 번호 (5자리)
		deleted_time 			TIMESTAMP, -- 회원 탈퇴 시간 (현재 시간 - 시분초까지 표시함)
				-- default systimestamp 쓰지 않고 BaseTimeEntity 클래스에서 설정.
		FOREIGN KEY (id) 		REFERENCES users(id) ON DELETE CASCADE -- 회원 번호 (외래키)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

---------------------------------------- 좋아요 테이블 ----------------------------------------
CREATE TABLE
	likes ( -- 좋아요 (컬럼 2개)
		song_id 				INT, -- 음악 번호 (5자리)
		id 						INT, -- 회원 번호 (5자리)
		FOREIGN KEY (song_id) 	REFERENCES songs(song_id) ON DELETE CASCADE, -- 음악 번호 (외래키)
		FOREIGN KEY (id) 		REFERENCES users(id) ON DELETE CASCADE -- 회원 번호 (외래키)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

---------------------------------------- 앨범 좋아요 테이블 ----------------------------------------
CREATE TABLE
	album_likes ( -- 앨범 좋아요 (컬럼 2개)
		album_id 				INT, -- 앨범 번호 (5자리)
		id 						INT, -- 회원 번호 (5자리)
		FOREIGN KEY (album_id)	REFERENCES albums(album_id) ON DELETE CASCADE, -- 앨범 번호 (외래키)
		FOREIGN KEY (id) 		REFERENCES users(id) ON DELETE CASCADE -- 회원 번호 (외래키)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

------------------------------------ 아티스트 좋아요 ---------------------------------------
    CREATE TABLE
	artist_likes ( -- 아티스트 좋아요 (컬럼 2개)
		artist_id 				INT, -- 앨범 번호 (5자리)
		id 						INT, -- 회원 번호 (5자리)
		FOREIGN KEY (artist_id)	REFERENCES artists(artist_id) ON DELETE CASCADE, -- 앨범 번호 (외래키)
		FOREIGN KEY (id) 		REFERENCES users(id) ON DELETE CASCADE -- 회원 번호 (외래키)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

------------------------------------ 그룹 좋아요 ---------------------------------------
    CREATE TABLE
	group_likes ( -- 그룹 좋아요 (컬럼 2개)
		group_id 				INT, -- 앨범 번호 (5자리)
		id 						INT, -- 회원 번호 (5자리)
		FOREIGN KEY (group_id)	REFERENCES `groups`(group_id) ON DELETE CASCADE, -- 앨범 번호 (외래키)
		FOREIGN KEY (id) 		REFERENCES users(id) ON DELETE CASCADE -- 회원 번호 (외래키)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

---------------------------------------- 댓글 테이블 ----------------------------------------
CREATE TABLE
	comments ( -- 댓글 (컬럼 6개)
		c_id 					INT AUTO_INCREMENT, -- 댓글 번호 (5자리, 자동 생성)
		song_id 				INT, -- 음악 번호 (5자리)
		id 						INT, -- 회원 번호 (5자리)
		c_text 					VARCHAR(500) NOT NULL, -- 댓글 내용 (500글자, 낫 널)
		created_time 			TIMESTAMP, -- 작성 시간 (현재 시간 - 시분초까지 표시함)
				-- default systimestamp 쓰지 않고 BaseTimeEntity 클래스에서 설정.
		modified_time 			TIMESTAMP, -- 수정 시간 (현재 시간 - 시분초까지 표시함)
				-- default systimestamp 쓰지 않고 BaseTimeEntity 클래스에서 설정.
		PRIMARY KEY (c_id), -- 댓글 번호 (고유키)
		FOREIGN KEY (song_id) 	REFERENCES songs(song_id) ON DELETE CASCADE, -- 음악 번호 (외래키)
		FOREIGN KEY (id) 		REFERENCES users(id) ON DELETE CASCADE -- 회원 번호 (외래키)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

---------------------------------------- 플레이리스트 테이블 ----------------------------------------
CREATE TABLE
	playlists ( -- 플레이리스트 (컬럼 3개)
		p_list_id 			INT AUTO_INCREMENT, -- 플리 번호 (5자리, 자동 생성)
		id 					INT, -- 회원 번호 (5자리)
		p_list_name 		VARCHAR(200) NOT NULL, -- 플리 이름 (200글자, 낫 널)
							PRIMARY KEY (p_list_id), -- 플리 번호 (고유키)
		FOREIGN KEY (id) 	REFERENCES users(id) ON DELETE CASCADE -- 회원 번호 (외래키)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

---------------------------------------- 플레이리스트 저장 곡 테이블 ----------------------------------------
CREATE TABLE
	playlist_songs ( -- 플레이리스트 저장 곡 (컬럼 3개)
		p_list_id 				INT, -- 플리 번호 (5자리)
		song_id 				INT, -- 음악 번호 (5자리)
		created_time 			TIMESTAMP, -- 플리 저장 시간 (현재 시간 - 시분초까지 표시함)
				-- default systimestamp 쓰지 않고 BaseTimeEntity 클래스에서 설정.
		FOREIGN KEY (p_list_id) REFERENCES playlists(p_list_id) ON DELETE CASCADE, -- 플리 번호 (외래키)
		FOREIGN KEY (song_id) 	REFERENCES songs(song_id) ON DELETE CASCADE -- 음악 번호 (외래키)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

---------------------------------------- 결제 회원 테이블 ----------------------------------------
CREATE TABLE
	pur_users ( -- 결제 회원 (컬럼 2개)
		id 					INT, -- 회원 번호 (5자리)
		expiration_date 	DATE, -- 만료일 (날짜 - 시분초 표시 안 함)
		FOREIGN KEY (id) 	REFERENCES users(id) ON DELETE CASCADE -- 회원 번호 (외래키)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

