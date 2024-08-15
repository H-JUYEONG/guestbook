-- --------------------------------------------
# guestbook 계정에서 실행
-- --------------------------------------------
-- db 선택(guestbook 계정에서 실행)
use guestbook_db;

-- table 삭제
drop table guest;

-- person 테이블 생성(phonebook 계정에서 실행)
create table guest(
	no integer primary key auto_increment,
    name varchar(80) not null,
    password varchar(20),
    content text,
	reg_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 조회
select * from guest;

select 	no,
		name,
        password,
        content,
        reg_date
from guest;

-- 등록
insert into guest (name, password, content) 
values ('유재석', 'yjs', 'This is a guest entry.');

insert into guest (name, password, content) 
values ('강호동', 'khd', 'hello~');

-- 삭제
delete from guest 
where password = 'qqp' 
and no = 3;