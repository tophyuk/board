# board
frontend : Bootstrap, Javascript, jQuery
backend : SpringBoot + SpringSecurity + Thymeleaf + OAuth2.0

## 🖥️ 프로젝트 소개
스프링부트를 이용한 첫 프로젝트로서 기본적인 기능들을 담은 프로젝트입니다.
첫 프로젝트라서 구성이나 코드 양식들이 다소 어수룩 할 수 있습니다.

<br>

## 🕰️ 개발 기간
* 23.01.16일 - 23.01.31일

### 🧑‍🤝‍🧑 맴버구성
 - 팀원1 :tophyuk - 전체 개발 및 설계

### ⚙️ 개발 환경
- `Java 11`
- **IDE** : IntelliJ
- **Framework** : Springboot(3.1)
- **Database** : MySQL
- **ORM** : JPA

## 📌 주요 기능
#### 로그인
- 이메일 및 비밀번호 검증
- 로그인 시 세션(Session) 생성
#### OAuth2.0 로그인
- Session 사용
- Google, Naver, Kakao 인증을 통한 로그인 구현
- 기본 로그인과 타입을 구별하여 로그인
#### 회원가입
- 이메일 중복 체크
- 비밀번호, 비밀번호 확인 매칭 검증
#### 메인 페이지
- 로그인 후 사용자 정보 가져오기
- OAuth 로그인 시에 해당 정보로 사용자 정보 가져오기
#### 게시판 페이지
- JPA Pagable을 이용하여 페이징, 페이지네이션 백엔드에서 구현
- Thymeleaf를 통해 페이지네이션 표출
- 검색조건 유지
