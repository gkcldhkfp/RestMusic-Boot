---------------------------------------- 앨범 초성 테이블 ------------------------------------
CREATE TABLE
	albums_first_con ( -- 앨범 (컬럼 5개)
		album_first_con_id 		INT AUTO_INCREMENT, -- 앨범 번호 (5자리, 자동 생성)
		album_id 				INT, -- 앨범 번호 (외래 키)
		album_first_con_name	VARCHAR(200), -- 앨범 이름 (200글자)
								PRIMARY KEY (album_first_con_id), -- 앨범 번호 (고유키)
					FOREIGN KEY (album_id) REFERENCES albums(album_id) ON DELETE CASCADE -- 앨범 번호 (외래키)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
    
    ---------------------------------------- 음원 초성 테이블 ------------------------------------
CREATE TABLE
	songs_first_con ( -- 음원 (컬럼 5개)
		song_first_con_id 		INT AUTO_INCREMENT, -- 음원 번호 (5자리, 자동 생성)
		song_id 				INT, -- 음원 번호 (외래 키)
		song_first_con_name	VARCHAR(200), -- 음원 이름 (200글자)
								PRIMARY KEY (song_first_con_id), -- 음원 번호 (고유키)
					FOREIGN KEY (song_id) REFERENCES songs(song_id) ON DELETE CASCADE -- 음원 번호 (외래키)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
    
    ---------------------------------------- 아티스트 초성 테이블 ------------------------------------
CREATE TABLE
	artists_first_con ( -- 앨범 (컬럼 5개)
		artist_first_con_id 		INT AUTO_INCREMENT, -- 앨범 번호 (5자리, 자동 생성)
		artist_id 				INT, -- 앨범 번호 (외래 키)
		artist_first_con_name	VARCHAR(200), -- 앨범 이름 (200글자)
								PRIMARY KEY (artist_first_con_id), -- 앨범 번호 (고유키)
					FOREIGN KEY (artist_id) REFERENCES artists(artist_id) ON DELETE CASCADE -- 앨범 번호 (외래키)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
    
    ---------------------------------------- 그룹 초성 테이블 ------------------------------------
CREATE TABLE
	groups_first_con ( -- 앨범 (컬럼 5개)
		group_first_con_id 		INT AUTO_INCREMENT, -- 앨범 번호 (5자리, 자동 생성)
		group_id 				INT, -- 앨범 번호 (외래 키)
		group_first_con_name	VARCHAR(200), -- 앨범 이름 (200글자)
								PRIMARY KEY (group_first_con_id), -- 앨범 번호 (고유키)
					FOREIGN KEY (group_id) REFERENCES `groups`(group_id) ON DELETE CASCADE -- 앨범 번호 (외래키)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
    