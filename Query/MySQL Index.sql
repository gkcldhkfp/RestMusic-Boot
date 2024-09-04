
USE restboot; -- 공용 DB 스키마
USE test; -- 개인 DB 스키마

-- score를 위해 필요

-- Title 컬럼에 텍스트 인덱스 생성
-- CREATE INDEX song_title_idx ON songs(title) USING FULLTEXT;
ALTER TABLE songs ADD FULLTEXT INDEX song_title_idx (title);

-- 기존 인덱스 삭제
-- DROP INDEX song_title_idx; 
DROP INDEX song_title_idx ON songs;

-- 불용어 처리를 하지 않는 FULLTEXT 인덱스 생성은 MySQL에서는 기본적으로 지원하지 않음.
-- 불용어 리스트를 수정하고 싶다면 MySQL 설정 파일에서 `ft_stopword_file` 옵션을 설정해야 함.
-- 필요시 다음과 같은 방법으로 수동으로 설정 가능:
-- SET GLOBAL innodb_ft_enable_stopword=OFF;
-- 그런 후에 인덱스를 생성:
SET GLOBAL innodb_ft_enable_stopword=OFF;
ALTER TABLE songs ADD FULLTEXT INDEX song_title_idx (title);

-- Artist_name 컬럼에 텍스트 인덱스 생성
ALTER TABLE artists ADD FULLTEXT INDEX artist_name_idx (artist_name);

-- Album_name 컬럼에 텍스트 인덱스 생성
ALTER TABLE albums ADD FULLTEXT INDEX album_name_idx (album_name);

-- 검색이 안될 경우 인덱스를 업데이트할 필요는 없으나, 테이블 데이터가 변경되면 인덱스를 재생성할 수 있음:
-- 일반적으로는 MySQL에서 인덱스가 자동으로 관리됨.
-- 필요시 수동으로 테이블 분석:
ANALYZE TABLE songs;
ANALYZE TABLE artists;
ANALYZE TABLE albums;

