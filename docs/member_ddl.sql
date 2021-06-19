-- << DATABASE 4���� ���� >> --

-- -- �׽�Ʈ ���̺� 
create table "test" (
	data1 varchar2(10),
	"data2" varchar2(10)
);	

select * from tab;

-- test ���̺� ���� ��ȸ 
desc test; 
-- ���� ����

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

-- create table : �ĺ�Ű member_id, name �����÷�
-- error : �÷������� primary key�� �����÷����� ���� ����
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

-- ���̺� ���� �����÷��� ���� �ĺ�Ű ����
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








-- ������� data dictionary 
-- user_constraints
-- user_cons_columns

desc user_constraints;
DESC user_cons_columns;

-- MEMBER ���̺� ���� ���� ��ȸ
SELECT TABLE_NAME, CONSTRAINT_NAME, CONSTRAINT_TYPE 
FROM USER_CONSTRAINTS
WHERE TABLE_NAME IN ('MEMBER');


-- user_cons_columns ���̺� �̿��ؼ� 
-- MEMBER ���̺��� PK_MEMBER_ID_NAME ���࿡ ���� �÷� ��ȸ
SELECT  TABLE_NAME, CONSTRAINT_NAME, COLUMN_NAME
     FROM USER_CONS_COLUMNS
    WHERE TABLE_NAME = 'MEMBER';

SELECT  TABLE_NAME, CONSTRAINT_NAME, COLUMN_NAME
     FROM USER_CONS_COLUMNS
    WHERE TABLE_NAME = 'MEMBER' AND CONSTRAINT_NAME = 'PK_MEMBER_ID_NAME';


-- �Խñ� ���̺� �����ϱ� - Į������ 

CREATE TABLE notice (
    POST_NO NUMBER(3) PRIMARY KEY,
    TITLE VARCHAR2(100) NOT NULL,
    CONTENTS VARCHAR2(4000),
    MEMBER_ID VARCHAR2(30) REFERENCES MEMBER(MEMBER_ID),
    CREATION_DATE DATE NOT NULL,
    VIEWS NUMBER(6)
);

-- �Խñ� ���̺� �����ϱ� - ���̺��� 
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

-- NOTICE ���̺� ���� ���� ��ȸ
SELECT TABLE_NAME, CONSTRAINT_NAME, CONSTRAINT_TYPE 
FROM USER_CONSTRAINTS
WHERE TABLE_NAME IN ('NOTICE');

-- -- NOTICE ���̺� ���࿡ ���� �÷� ��ȸ
SELECT  TABLE_NAME, CONSTRAINT_NAME, COLUMN_NAME
     FROM USER_CONS_COLUMNS
    WHERE TABLE_NAME = 'NOTICE';
    
-- �θ����̺� MEMBER���̺� ���� 
-- ERROR : �ڽ����̺��� �����ϰ� �ִ� �θ����̺��� ������ ����
DROP TABLE MEMBER;  

-- �ڽ�
DROP TABLE MEMBER CASCADE CONSTRAINTS;

SELECT * FROM TAB;






-- member table ���� : master
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


-- ���� �߰� ����
-- constraint add : pk
alter table member
add constraint pk_memberid primary key(member_Id);

-- constraint add : �޴��� �ߺ��Ұ� 
alter table member
add constraint unique_mobile unique(mobile);


-- notice table ���� - detail 
CREATE TABLE notice (
    POST_NO NUMBER(3),
    TITLE VARCHAR2(100) NOT NULL,
    CONTENTS VARCHAR2(4000),
    MEMBER_ID VARCHAR2(30),
    CREATION_DATE DATE NOT NULL,
    VIEWS NUMBER(6)
);

-- ���� �߰� ����
-- constraint add : pk
alter table notice 
add constraint pk_post_no primary key(post_no);

-- constraint add : fk
alter table notice 
add constraint fk_member_id foreign key(member_id) references member(member_id);

drop table member;
drop table notice;


-- ������ �÷�, ������� 
INSERT INTO MEMBER(MEMBER_ID, MEMBER_PW, NAME, MOBILE, EMAIL, ENTRYDATE, GRADE, MILEAGE)
VALUES( 'user01',	'password01',	'ȫ�浿',	'010-1234-1111',	'user01@work.com','2017.05.05',	'G',75000);


-- ���̺� ��Ű�� ���� ������� : �÷��� �����Ͱ� ���� ��쿡�� ������ ���� �����ϰų� NULL�� �־��ش� 
INSERT INTO MEMBER 
VALUES('user02',	'password02',	'������',	'010-1234-1112',	'user02@work.com',	'2017.05.06',	'G',	95000, NULL	);

INSERT INTO MEMBER
VALUES('user03',	'password03',	'�̼���',	'010-1234-1113',	'user03@work.com',	'2017.05.07',	'G',	3000, NULL		);

INSERT INTO MEMBER 
VALUES('user04',	'password04',	'������',   '010-1234-1114',	'user04@work.com',	'2017.05.08',	'S', NULL, '���߱�');

INSERT INTO MEMBER 
VALUES('user05','password05',	'������',	'010-1234-1115', 'user05@work.com',	'2017.05.09',	'A', NULL, NULL	);

-- ��ü ��� ��ȸ
SELECT * FROM MEMBER;


--error �׽�Ʈ : �ĺ�Ű �ߺ�, �޴��� �ߺ�, �����ʰ�, �ʼ��׸� ����(null)
INSERT INTO MEMBER 
VALUES('test99','password05',	'������',	'010-1234-1115', 'user05@work.com',	'2017.05.09',	null, NULL, NULL	);

-- ������ : �ڵ� �Ϸù�ȣ ���� ��ü
CREATE SEQUENCE SEQ_NO;

-- �Ϸù�ȣ ���� 
SELECT SEQ_NO.NEXTVAL FROM DUAL;

-- ������ ����
DROP SEQUENCE SEQ_NO;

-- ���� �μ����̺� �߰��� �μ��� ��Ͻ�ų �� ����ϱ����� ������ ��ü ����
-- ������ ���۹�ȣ: 50
-- ������ ������: 10
-- �ִ밪 : 90
-- �ݺ� ���� N

-- ���� �ּҺμ���ȣ : 10, �ִ� �μ���ȣ : 40
SELECT MIN(DEPTNO), MAX(DEPTNO) FROM DEPT;

-- ���� �μ���ȣ�� ������ȸ => NUMBER(2),�ĺ�Ű
SELECT DEPTNO FROM DEPT ORDER BY DEPTNO;

-- �μ����̺� ������ȸ, �μ���ȣ � ������ �Ǿ��ִ��� Ȯ�� 
DESC DEPT;




-- ���� �μ����̺� �߰��� �μ��� ��Ͻ�ų �� ����ϱ����� ������ ��ü ����
-- ������ ���۹�ȣ: 50
-- ������ ������: 10
-- �ִ밪 : 90
-- �ݺ� ���� N

-- ���� �ּҺμ���ȣ : 10, �ִ� �μ���ȣ : 40
-- ���� �μ���ȣ�� ������ȸ => NUMBER(2),�ĺ�Ű
-- �μ����̺� ������ȸ, �μ���ȣ � ������ �Ǿ��ִ��� Ȯ�� 

-- ������ ����
CREATE SEQUENCE SEQ_DEPT_DEPTNO
START WITH 50
INCREMENT BY 10
MAXVALUE 90
NOCYCLE;

DROP SEQUENCE  SEQ_DEPT_DEPTNO;

-- �μ����̺� ���ڵ� �߰� : 
-- �μ���ȣ �������ڵ��ο�, �μ��� ����1��, �μ���ġ ���ֵ�
-- �μ���ȣ �������ڵ��ο�, �μ��� ����2��, �μ���ġ �λ�

DESC DEPT;

INSERT INTO DEPT VALUES(SEQ_DEPT_DEPTNO.NEXTVAL,'����1��','���ֵ�');
INSERT INTO DEPT VALUES(SEQ_DEPT_DEPTNO.NEXTVAL,'����2��','�λ�');


SELECT * FROM EMP;
DESC EMP;

-- Ʈ����� : ���� ����
ROLLBACK;

-- ���̺� �����ؼ�, �������̺� 10�� �μ������� �̸�, ���� ������ ���ڵ� �߰�
CREATE TABLE TEST (
    USER_NAME VARCHAR(10),
    USER_JOB VARCHAR(9)
);

-- �������̺� 10�� �μ����� �̸�, ���� ��ȸ	
SELECT ENAME �̸�, JOB ���� FROM EMP WHERE DEPTNO = 10;

-- ���� ���ڵ� �߰� : �����ʹ� SELECT ����� ���
INSERT INTO TEST(USER_NAME , USER_JOB) 
SELECT ENAME �̸�, JOB ���� FROM EMP 
WHERE DEPTNO IN 10;

SELECT * FROM TEST;

-- 10�� �μ������� �μ���ȣ, ���, �̸�, �޿����� ��ȸ
SELECT DEPTNO, EMPNO, ENAME, SAL FROM EMP WHERE DEPTNO IN 10;

-- 10�� �μ������� ���� �� ������ ���� ���̺� ���� : EMP_10
	-- ������ �÷��� ��� 
	CREATE TABLE EMP_10 
    AS 
	SELECT DEPTNO, EMPNO, ENAME, SAL 
	FROM EMP 
	WHERE DEPTNO IN 10;

-- 10�� �μ������� ���� �� ������ ���� ���̺� ���� : EMP_10
-- �÷��� �ٸ��� ���� ���
CREATE TABLE EMP_TEST_SAMPLE (NO, SID, NAME, MILEAGE)
    AS 
	SELECT DEPTNO, EMPNO, ENAME, SAL FROM EMP WHERE DEPTNO IN 10;

SELECT * FROM EMP_10;

-- �������̺��� �������� �����ؼ� ���̺��� ����, �����ʹ� ���� X 
CREATE TABLE NEW_EMP 
    AS 
	SELECT * FROM EMP 
	WHERE 1=2
	;

-- �����ͺ��̽� 4���� ���� ��






-- ������ ���̽� 5���� ���� �غ�
DROP TABLE TEST; 
DROP TABLE NEW_EMP;    
DROP TABLE EMP_10;
SELECT * FROM TAB;
DROP TABLE NOTICE;

-- NOTICE ������ ���� - POST_NO
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

-- �Խ��� ���̺� ������ �Է�
INSERT INTO NOTICE
 VALUES(SEQ_NOTICE_POST_NO1.NEXTVAL ,'�ָ�����',	'ȸ����������DB����',			'user05',	'2020.11.11',		0	);

INSERT INTO NOTICE
 VALUES(SEQ_NOTICE_POST_NO1.NEXTVAL ,'�������', 	'������� �Ұ�',				'user04',	'2020.12.25',		5	);

INSERT INTO NOTICE
 VALUES(SEQ_NOTICE_POST_NO1.NEXTVAL ,'�ָ�����',	'ȭ�����Ǽ�'	,				'user05',	'2021.02.14',		0	);
 
 INSERT INTO NOTICE
 VALUES(SEQ_NOTICE_POST_NO1.NEXTVAL ,'��������',	'�ð�����',					'user05',	'2021.03.01',		15	);
 
 INSERT INTO NOTICE
 VALUES(SEQ_NOTICE_POST_NO1.NEXTVAL ,'WEB����',	'www.w3schools.com',		'user01',	'2021.05.26',		5	);



-- << DATABASE 5���� ���� TCL >> --

-- ���̺� ��� ��ȸ 
-- ȸ�� member ���� ��ȸ 
-- �Խñ� notice ���� ��ȸ 
-- ȸ�� ��ü ���ڵ� ��ȸ 
-- �Խñ� ��ü ���ڵ� ��ȸ 
SELECT * FROM NOTICE;
SELECT * FROM MEMBER;


-- �Ϲ�ȸ�� ��ü ��ȸ 
SELECT * FROM MEMBER WHERE GRADE = 'G';

-- �Ϲ�ȸ���鿡�� ���ϸ��� = ���ϸ��� + 1000 ���� 
UPDATE MEMBER SET MILEAGE = MILEAGE + 1000 WHERE GRADE = 'G';

-- ������ ����
rollback;

-- DB�� ��������
commit;

-- USER01 ȸ���� ����� ���ȸ������ ���ó�� 
-- ����� : ��⿵ 
-- ���ϸ��� : 0 
UPDATE MEMBER SET MILEAGE = 0, GRADE = 'S', MANAGER = '��⿵'
WHERE MEMBER_ID = 'user01';

-- ��ü ȸ�� ����  ERROR : NOTICE �Խñ� ���� ���ڵ� ���� ���� �Ұ�
DELETE MEMBER;

--
delete member WHERE MEMBER_ID = 'user02';

-- �Խñ� �ۼ��� ȸ�����̵� �ߺ������ϰ� ���̵� ������ȸ
SELECT DISTINCT MEMBER_ID FROM NOTICE ORDER BY MEMBER_ID;


-- ��ü �Խù� ����
delete notice;

-- ��ü ȸ���� ����
delete member;

-- TRUCATE TABLE 
-- �Խñ��� �ۼ����� ���� ȸ�� ���� : USER02
-- ERROR : WHERE ���� ���� �Ұ� 
-- 
TRUNCATE TABLE NOTICE
WHERE MEMBERID='user01';

-- TRUNCATE TABLE �Խñ� ��ü ����
-- NOTICE ���� ���� : �ѹ��ص� ���� �ȵ�.
TRUNCATE TABLE NOTICE;


desc member;

select * from member;
