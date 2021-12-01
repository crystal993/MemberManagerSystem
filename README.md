# MemberManagerSystem_jdbc - 회원 관리 시스템_디비연동


추후 웹어플리케이션 개발에서 사용하기 위한 회원관리시스템을 개발하고자 한다.
콘솔버전에서 JDBC 연동

<br><br>
## 💁 프로젝트 소개

1. 회원은 일반회원, 우수회원, 관리자회원으로 구분한다.

2. 일반회원에게는 마일리지 정책에 따라 마일리지를 부여한다.

3. 우수회원에게는 전용 담당자를 배정한다.

4. 관리자 회원은 전체회원들의 정보를 관리한다.

5. 일반회원의 마일리지가 100,000 이상이 되면 우수회원으로 등업처리한다.

6. 우수회원 등업처리 정책에 따라 자동등업, 사용자 요청 등업, 관리자 등업 등 다양한 방법으로 처리할 수 있다.

7. 마일리지 정책을 세우고 구현한다.

8. 우수회원 등업처리 정책을 세우고 구현한다.

9. 회원은 가입후에 로그인을 통해서 내정보조회, 비밀번호 변경, 내정보전체변경등의 기능을 사용할 수 있다.

10. 관리자 회원은 초기화 데이터를 통해서 관리자 회원으로 등록(생성)하여 사용한다.

11. 관리자 회원이 로그인을 하면 본인의 정보조회, 변경등을 할 수 있으며, 전체회원의 정보를 조회할 수 있다.
    단, 관리자 회원이 전체회원의 정보를 조회할때는 회원들의 정보중에서 비밀번호는
    앞자리 2자리만 보여주고 나머지는 *문자로 대체하여 조회한다.

12. 회원은 가입시 현재 날짜를 가입일로 시스템에서 자동 부여한다.

13. 회원의 아이디/비밀번호찾기시에 회원 가입시에 입력받은 휴대폰과 이메일정보를 사용한다.

14. 회원의 인증은 아이디/비밀번호를 통해서 회원 여부를 검증한다.



<br><br><br>
## 👫 회원 관리 시스템 프로젝트 관계도 설정
 
<br>
 
 
 ![회원관리_상속](https://user-images.githubusercontent.com/72599761/120098124-14d58400-c16f-11eb-80fd-8f32db97e210.png)


<br><br><br>
## 📜 이 프로젝트의 기술 구현 방식

<br>

1. 전체 회원 조회 : 관리자(actor)
		>> (보안) 비밀번호 변경처리 : 앞 2자리만 보여주고 
		     나머지는 * 문자 대체 
		(이름,휴대폰,비밀번호 공통 사용기능 분리설계 - 유틸리티 기능)

2. 우수회원 등업 : (정책) 일반 회원의 마일리지가 100,000 이상

3. 마일리지 추가(변경)
		>> 마일리지 정책 : 글쓰기, 댓글
		>> 출석 (로그인) : 매번 호그인마다 부여, 로그인 당일에 처음 로그인시 (1일 1번)
4. 회원 가입  
5. 로그인 
6. 내정보조회 
7. 비밀번호변경 
8. 내정보전체변경 
9. 초기화 회원 등록 기능(관리자) 
10. 아이디 찾기 
11. 비밀번호 찾기
