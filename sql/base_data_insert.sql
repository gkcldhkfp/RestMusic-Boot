-- 코드마스터 테이블에 장르 설정
insert into code_master (code_id, code_name)
values (2, '장르');

-- 장르 종류를 설정
insert into genre_code (code_id, genre_name, genre_id)
values (2, '발라드', 10);

insert into genre_code (code_id, genre_name, genre_id)
values (2, '팝', 20);

insert into genre_code (code_id, genre_name, genre_id)
values (2, '댄스/일렉', 30);

insert into genre_code (code_id, genre_name, genre_id)
values (2, '알앤비', 40);

insert into genre_code (code_id, genre_name, genre_id)
values (2, '힙합', 50);

insert into genre_code (code_id, genre_name, genre_id)
values (2, '트로트', 60);

insert into genre_code (code_id, genre_name, genre_id)
values (2, 'OST', 70);

insert into genre_code (code_id, genre_name, genre_id)
values (2, '인디', 80);

insert into genre_code (code_id, genre_name, genre_id)
values (2, '포크/블루스', 90);

insert into genre_code (code_id, genre_name, genre_id)
values (2, '록/메탈', 100);



-- 코드마스터 데이터 삽입
insert into code_master (code_id, code_name)
values (1, '아티스트');

-- 역할 코드 데이터 삽입
insert into role_code (code_id, role_name, role_id)
values (1, '가수', 10);

insert into role_code (code_id, role_name, role_id)
values (1, '작곡가', 20);

insert into role_code (code_id, role_name, role_id)
values (1, '작사가', 30);

insert into role_code (code_id, role_name, role_id)
values (1, '편곡가', 40);

select * from role_code;
