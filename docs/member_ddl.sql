-- << DATABASE 4일차 수업 >> --

-- -- 테스트 테이블 
create table "test" (
	data1 varchar2(10),
	"data2" varchar2(10)
);	

select * from tab;

-- test 테이블 구조 조회 
desc test; 
-- 오류 나옴

desc "test";

drop table "test";



create table MEMBER(
    MEMBER_ID varchar2(30) primary key,
    MEMBER_PW varchar2(20) not null,
    NAME varchar2(20) not null,
    MOBILE varchar2(13) not null,
    EMAIL varchar2(30) not null,
    ENTRYDATE varchar2(10) not null,
    GRADE varchar2(1) not null,
    MILEAGE number(6),
    MANAGER varchar2(20) 
);

-- create table : 식별키 member_id, name 다중컬럼
-- error : 컬럼레벨로 primary key는 단일컬럼에만 적용 가능
create table MEMBER(
    MEMBER_ID varchar2(30) primary key,
    MEMBER_PW varchar2(20) not null,
    NAME varchar2(20) not null primary key,
    MOBILE varchar2(13) not null,
    EMAIL varchar2(30) not null,
    ENTRYDATE varchar2(10) not null,
    GRADE varchar2(1) not null,
    MILEAGE number(6),
    MANAGER varchar2(20) 
);

-- 테이블 레벨 다중컬럼에 대한 식별키 지정
create table MEMBER(
    MEMBER_ID varchar2(30) ,
    MEMBER_PW varchar2(20) not null,
    NAME varchar2(20) ,
    MOBILE varchar2(13) not null,
    EMAIL varchar2(30) not null,
    ENTRYDATE varchar2(10) not null,
    GRADE varchar2(1) not null,
    MILEAGE number(6),
    MANAGER varchar2(20),
    CONSTRAINT PK_MEMBER_ID_NAME PRIMARY KEY (MEMBER_ID, NAME)
);

drop table MEMBER;

select * from MEMBER;

desc MEMBER;








-- 제약관련 data dictionary 
-- user_constraints
-- user_cons_columns

desc user_constraints;
DESC user_cons_columns;

-- MEMBER 테이블에 대한 제약 조회
SELECT TABLE_NAME, CONSTRAINT_NAME, CONSTRAINT_TYPE 
FROM USER_CONSTRAINTS
WHERE TABLE_NAME IN ('MEMBER');


-- user_cons_columns 테이블 이용해서 
-- MEMBER 테이블의 PK_MEMBER_ID_NAME 제약에 대한 컬럼 조회
SELECT  TABLE_NAME, CONSTRAINT_NAME, COLUMN_NAME
     FROM USER_CONS_COLUMNS
    WHERE TABLE_NAME = 'MEMBER';

SELECT  TABLE_NAME, CONSTRAINT_NAME, COLUMN_NAME
     FROM USER_CONS_COLUMNS
    WHERE TABLE_NAME = 'MEMBER' AND CONSTRAINT_NAME = 'PK_MEMBER_ID_NAME';


-- 게시글 테이블 생성하기 - 칼럼레벨 

CREATE TABLE notice (
    POST_NO NUMBER(3) PRIMARY KEY,
    TITLE VARCHAR2(100) NOT NULL,
    CONTENTS VARCHAR2(4000),
    MEMBER_ID VARCHAR2(30) REFERENCES MEMBER(MEMBER_ID),
    CREATION_DATE DATE NOT NULL,
    VIEWS NUMBER(6)
);

-- 게시글 테이블 생성하기 - 테이블레벨 
CREATE TABLE notice (
    POST_NO NUMBER(3),
    TITLE VARCHAR2(100) NOT NULL,
    CONTENTS VARCHAR2(4000),
    MEMBER_ID VARCHAR2(30),
    CREATION_DATE DATE NOT NULL,
    VIEWS NUMBER(6),
    CONSTRAINTS NOTICE_POST_NO PRIMARY KEY (POST_NO),
    CONSTRAINTS NOTICE_MEMBER_ID FOREIGN KEY(MEMBER_ID) REFERENCES MEMBER(MEMBER_ID)
);

DROP TABLE NOTICE;

DESC NOTICE;

-- NOTICE 테이블에 대한 제약 조회
SELECT TABLE_NAME, CONSTRAINT_NAME, CONSTRAINT_TYPE 
FROM USER_CONSTRAINTS
WHERE TABLE_NAME IN ('NOTICE');

-- -- NOTICE 테이블 제약에 대한 컬럼 조회
SELECT  TABLE_NAME, CONSTRAINT_NAME, COLUMN_NAME
     FROM USER_CONS_COLUMNS
    WHERE TABLE_NAME = 'NOTICE';
    
-- 부모테이블 MEMBER테이블 삭제 
-- ERROR : 자식테이블이 참고하고 있는 부모테이블은 삭제시 오류
DROP TABLE MEMBER;  

-- 자식
DROP TABLE MEMBER CASCADE CONSTRAINTS;

SELECT * FROM TAB;






-- member table 생성 : master
create table MEMBER(
    MEMBER_ID varchar2(30) ,
    MEMBER_PW varchar2(20) not null,
    NAME varchar2(20) ,
    MOBILE varchar2(13),
    EMAIL varchar2(30) not null,
    ENTRYDATE varchar2(10) not null,
    GRADE varchar2(1) not null,
    MILEAGE number(6),
    MANAGER varchar2(20)
);


-- 제약 추가 변경
-- constraint add : pk
alter table member
add constraint pk_memberid primary key(member_Id);

-- constraint add : 휴대폰 중복불가 
alter table member
add constraint unique_mobile unique(mobile);


-- notice table 생성 - detail 
CREATE TABLE notice (
    POST_NO NUMBER(3),
    TITLE VARCHAR2(100) NOT NULL,
    CONTENTS VARCHAR2(4000),
    MEMBER_ID VARCHAR2(30),
    CREATION_DATE DATE NOT NULL,
    VIEWS NUMBER(6)
);

-- 제약 추가 변경
-- constraint add : pk
alter table notice 
add constraint pk_post_no primary key(post_no);

-- constraint add : fk
alter table notice 
add constraint fk_member_id foreign key(member_id) references member(member_id);

drop table member;
drop table notice;


-- 지정한 컬럼, 순서대로 
INSERT INTO MEMBER(MEMBER_ID, MEMBER_PW, NAME, MOBILE, EMAIL, ENTRYDATE, GRADE, MILEAGE)
VALUES( 'user01',	'password01',	'홍길동',	'010-1234-1111',	'user01@work.com','2017.05.05',	'G',75000);


-- 테이블 스키마 구조 순서대로 : 컬럼에 데이터가 없는 경우에는 임의의 값을 지정하거나 NULL을 넣어준다 
INSERT INTO MEMBER 
VALUES('user02',	'password02',	'강감찬',	'010-1234-1112',	'user02@work.com',	'2017.05.06',	'G',	95000, NULL	);

INSERT INTO MEMBER
VALUES('user03',	'password03',	'이순신',	'010-1234-1113',	'user03@work.com',	'2017.05.07',	'G',	3000, NULL		);

INSERT INTO MEMBER 
VALUES('user04',	'password04',	'김유신',   '010-1234-1114',	'user04@work.com',	'2017.05.08',	'S', NULL, '송중기');

INSERT INTO MEMBER 
VALUES('user05','password05',	'유관순',	'010-1234-1115', 'user05@work.com',	'2017.05.09',	'A', NULL, NULL	);

-- 전체 멤버 조회
SELECT * FROM MEMBER;


--error 테스트 : 식별키 중복, 휴대폰 중복, 길이초과, 필수항목 누락(null)
INSERT INTO MEMBER 
VALUES('test99','password05',	'유관순',	'010-1234-1115', 'user05@work.com',	'2017.05.09',	null, NULL, NULL	);

-- 시퀀스 : 자동 일련번호 제공 객체
CREATE SEQUENCE SEQ_NO;

-- 일련번호 추출 
SELECT SEQ_NO.NEXTVAL FROM DUAL;

-- 시퀀스 삭제
DROP SEQUENCE SEQ_NO;

-- 현재 부서테이블에 추가로 부서를 등록시킬 때 사용하기위한 시퀀스 객체 생성
-- 시퀀스 시작번호: 50
-- 시퀀스 증감값: 10
-- 최대값 : 90
-- 반복 여부 N

-- 현재 최소부서번호 : 10, 최대 부서번호 : 40
SELECT MIN(DEPTNO), MAX(DEPTNO) FROM DEPT;

-- 현재 부서번호만 정렬조회 => NUMBER(2),식별키
SELECT DEPTNO FROM DEPT ORDER BY DEPTNO;

-- 부서테이블 구조조회, 부서번호 어떤 구조로 되어있는지 확인 
DESC DEPT;




-- 현재 부서테이블에 추가로 부서를 등록시킬 때 사용하기위한 시퀀스 객체 생성
-- 시퀀스 시작번호: 50
-- 시퀀스 증감값: 10
-- 최대값 : 90
-- 반복 여부 N

-- 현재 최소부서번호 : 10, 최대 부서번호 : 40
-- 현재 부서번호만 정렬조회 => NUMBER(2),식별키
-- 부서테이블 구조조회, 부서번호 어떤 구조로 되어있는지 확인 

-- 시퀀스 생성
CREATE SEQUENCE SEQ_DEPT_DEPTNO
START WITH 50
INCREMENT BY 10
MAXVALUE 90
NOCYCLE;

DROP SEQUENCE  SEQ_DEPT_DEPTNO;

-- 부서테이블 레코드 추가 : 
-- 부서번호 시퀀스자동부여, 부서명 개발1팀, 부서위치 제주도
-- 부서번호 시퀀스자동부여, 부서명 개발2팀, 부서위치 부산

DESC DEPT;

INSERT INTO DEPT VALUES(SEQ_DEPT_DEPTNO.NEXTVAL,'개발1팀','제주도');
INSERT INTO DEPT VALUES(SEQ_DEPT_DEPTNO.NEXTVAL,'개발2팀','부산');


SELECT * FROM EMP;
DESC EMP;

-- 트랜잭션 : 원상 복구
ROLLBACK;

-- 테이블 생성해서, 직원테이블 10번 부서원들의 이름, 직무 정보를 레코드 추가
CREATE TABLE TEST (
    USER_NAME VARCHAR(10),
    USER_JOB VARCHAR(9)
);

-- 직원테이블 10번 부서원의 이름, 직무 조회	
SELECT ENAME 이름, JOB 직무 FROM EMP WHERE DEPTNO = 10;

-- 다중 레코드 추가 : 데이터는 SELECT 결과를 사용
INSERT INTO TEST(USER_NAME , USER_JOB) 
SELECT ENAME 이름, JOB 직무 FROM EMP 
WHERE DEPTNO IN 10;

SELECT * FROM TEST;

-- 10번 부서원들의 부서번호, 사번, 이름, 급여정보 조회
SELECT DEPTNO, EMPNO, ENAME, SAL FROM EMP WHERE DEPTNO IN 10;

-- 10번 부서원들의 정보 및 구조를 갖는 테이블 생성 : EMP_10
	-- 동일한 컬럼명 사용 
	CREATE TABLE EMP_10 
    AS 
	SELECT DEPTNO, EMPNO, ENAME, SAL 
	FROM EMP 
	WHERE DEPTNO IN 10;

-- 10번 부서원들의 정보 및 구조를 갖는 테이블 생성 : EMP_10
-- 컬럼명 다르게 지정 사용
CREATE TABLE EMP_TEST_SAMPLE (NO, SID, NAME, MILEAGE)
    AS 
	SELECT DEPTNO, EMPNO, ENAME, SAL FROM EMP WHERE DEPTNO IN 10;

SELECT * FROM EMP_10;

-- 직원테이블의 구조만을 참조해서 테이블을 생성, 데이터는 존재 X 
CREATE TABLE NEW_EMP 
    AS 
	SELECT * FROM EMP 
	WHERE 1=2
	;

-- 데이터베이스 4일차 수업 끝






-- 데이터 베이스 5일차 수업 준비
DROP TABLE TEST; 
DROP TABLE NEW_EMP;    
DROP TABLE EMP_10;
SELECT * FROM TAB;
DROP TABLE NOTICE;

-- NOTICE 시퀀스 생성 - POST_NO
DESC NOTICE;
CREATE SEQUENCE SEQ_NOTICE_POST_NO 
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 5 
    NOCYCLE;
    
DESC NOTICE;
CREATE SEQUENCE SEQ_NOTICE_POST_NO1 
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 5 
    NOCYCLE;    

-- 게시판 테이블에 데이터 입력
INSERT INTO NOTICE
 VALUES(SEQ_NOTICE_POST_NO1.NEXTVAL ,'주말과제',	'회원도서관리DB설계',			'user05',	'2020.11.11',		0	);

INSERT INTO NOTICE
 VALUES(SEQ_NOTICE_POST_NO1.NEXTVAL ,'형상관리', 	'형상관리 소개',				'user04',	'2020.12.25',		5	);

INSERT INTO NOTICE
 VALUES(SEQ_NOTICE_POST_NO1.NEXTVAL ,'주말과제',	'화면정의서'	,				'user05',	'2021.02.14',		0	);
 
 INSERT INTO NOTICE
 VALUES(SEQ_NOTICE_POST_NO1.NEXTVAL ,'과제제출',	'시간엄수',					'user05',	'2021.03.01',		15	);
 
 INSERT INTO NOTICE
 VALUES(SEQ_NOTICE_POST_NO1.NEXTVAL ,'WEB참고',	'www.w3schools.com',		'user01',	'2021.05.26',		5	);



-- << DATABASE 5일차 수업 TCL >> --

-- 테이블 목록 조회 
-- 회원 member 구조 조회 
-- 게시글 notice 구조 조회 
-- 회원 전체 레코드 조회 
-- 게시글 전체 레코드 조회 
SELECT * FROM NOTICE;
SELECT * FROM MEMBER;


-- 일반회원 전체 조회 
SELECT * FROM MEMBER WHERE GRADE = 'G';

-- 일반회원들에게 마일리지 = 마일리지 + 1000 변경 
UPDATE MEMBER SET MILEAGE = MILEAGE + 1000 WHERE GRADE = 'G';

-- 원상태 복구
rollback;

-- DB에 영속저장
commit;

-- USER01 회원의 등급을 우수회원으로 등업처리 
-- 담당자 : 김기영 
-- 마일리지 : 0 
UPDATE MEMBER SET MILEAGE = 0, GRADE = 'S', MANAGER = '김기영'
WHERE MEMBER_ID = 'user01';

-- 전체 회원 삭제  ERROR : NOTICE 게시글 참조 레코드 존재 삭제 불가
DELETE MEMBER;

--
delete member WHERE MEMBER_ID = 'user02';

-- 게시글 작성한 회원아이디를 중복제거하고 아이디 정렬조회
SELECT DISTINCT MEMBER_ID FROM NOTICE ORDER BY MEMBER_ID;


-- 전체 게시물 삭제
delete notice;

-- 전체 회원의 삭제
delete member;

-- TRUCATE TABLE 
-- 게시글을 작성하지 않은 회원 삭제 : USER02
-- ERROR : WHERE 조건 삭제 불가 
-- 
TRUNCATE TABLE NOTICE
WHERE MEMBERID='user01';

-- TRUNCATE TABLE 게시글 전체 삭제
-- NOTICE 영구 삭제 : 롤백해도 복원 안됨.
TRUNCATE TABLE NOTICE;


desc member;

select * from member;
