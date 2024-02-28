# 이사.zip

## 프로젝트 소개

![KakaoTalk_Photo_2024-02-25-02-04-33](https://github.com/isa-zip/isa.zip-backend/assets/50578785/23465b93-7b7b-4ca8-9b2b-7bcfd332c6ef)


### 서버 담당 파트

- 차현수 : 공인중개사 관련 매물 매칭 관련 기능 구현, DB 설계
- 한수찬 : 일반 회원 관련 매물 매칭 관련 기능 구현, DB 설계
- 유미라 : 회원가입, 로그인, 탈퇴 등 유저 관련 기능 구현, DB 설계
- 장은진 : 일정 관련 기능 구현, CI/CD, DB 설계

### 데이터베이스 설계도


### CI/CD

![Slide 16_9 - 1 (4)](https://github.com/isa-zip/isa.zip-backend/assets/50578785/1c039ed8-7742-4798-bfd4-1157d446a391)
<br>

## 기술 스택

| Spring Data JPA | Spring | Spring Security | Java | MySQL | AWS | Heroku | Discord |
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
|<img src="https://github.com/isa-zip/isa.zip-backend/assets/50578785/a7ef9c41-9a29-468b-8fae-7dfa58e6ab83" width="70" height="70">|<img src="https://github.com/isa-zip/isa.zip-backend/assets/50578785/f1063f2c-1cbc-46c6-b5ab-6f65e17a1d2d" width="70" height="70">|<img src="https://github.com/isa-zip/isa.zip-backend/assets/50578785/4fc51831-2280-48e1-9fc6-a00a1f1abf8b" width="70" height="70">|<img src="https://github.com/isa-zip/isa.zip-backend/assets/50578785/0b521660-9c65-4d04-a6c3-55d79eb4f5db" width="70" height="70">|<img src="https://github.com/isa-zip/isa.zip-backend/assets/50578785/013ad61c-3dfe-4416-9c6e-bd8108315305" width="70" height="70">|<img src="https://github.com/isa-zip/isa.zip-backend/assets/50578785/bb5358a3-92df-4f9e-aee3-90ae9835425d" width="70" height="70">|<img src="https://github.com/isa-zip/isa.zip-backend/assets/50578785/e5af90e7-00d8-4263-963c-1069f878c3d5" width="70" height="70">|<img src="https://github.com/isa-zip/isa.zip-backend/assets/50578785/8ddb830a-7398-4772-9fed-a0a8e52cfe29" width="70" height="70">|


<br>

## 구현 기능 (API 설계도)


| Method | Specific Document            | EndPoint                                                            | Role |
|--------|------------------------------|---------------------------------------------------------------------|------|
|        | 토큰 관련 설명                     |                                                                     |      |
|        |                              | 회원 관리                                                               |      |
| POST   | 카카오 회원가입 및 로그인               | /auth/kakao                                                         | 유미라 |
| POST   | 이메일 회원가입                     | /auth/sign-up                                                       | 유미라 |
| POST   | 이메일 인증번호 요청                  | /auth/code-request                                                  | 유미라 |
| POST   | 이메일 인증번호 확인                  | /auth/code-check                                                    | 유미라 |
| POST   | 로그아웃                         | /users/logout                                                       | 유미라 |
| DELETE | 회원탈퇴                         | /users                                                              | 유미라 |
| POST   | 이메일 로그인                      | /auth/login                                                         | 유미라 |
| GET    | 프로필 조회                       | /users                                                              | 유미라 |
| PUT    | 프로필 수정                       | /users                                                              | 유미라 |
| POST   | 공인중개사 인증                     | /users/auth-broker                                                  | 유미라 |
|        |                              | 도움                                                                  |      |
| POST   | 일정 등록                        | /users/schedule                                                     | 장은진  |
| PUT    | 일정 수정                        | /users/schedule                                                     | 장은진  |
| DELETE | 일정 삭제                        | /users/schedule                                                     | 장은진  |
| GET    | 일정 조회                        | /users/schedule                                                     | 장은진  |
| GET    | 상세 일정 조회                     | /users/events                                                       | 장은진  |
| DELETE | 상세 일정 삭제                     | /users/events/{eventId}                                             | 장은진  |
| PUT    | 상세 일정 수정                     | /users/events/{eventId}                                             | 장은진  |
|        |                              | 공인중개사가 등록한 매물들                                                      |      |
| GET    | 매물 새로 등록하기 전 주소 입력 단계(공인중개사) | /brokers/map?address=도로명주소                                          | 차현수  |
| POST   | 매물 새로 등록하기(공인중개사)            | /brokers/items?address=도로명주소                                        | 차현수  |
| DELETE | 매물 삭제 (공인중개사)                | /brokers/items/{brokerItemId}                                       | 차현수  |
| PUT    | 매물 정보 수정 (공인중개사)             | /brokers/items/{brokerItemId}?address=도로명주소 - >도로명주소는 수정하고 싶을 때만 추가 | 차현수  |
| PATCH  | 매물 SOLD OUT (공인중개사)          | /brokers/items/{brokerItemId}/soldout                               | 차현수  |
| GET    | 공인중개사가 가지고 있는 매물 전체 조회       | /brokers/items/show                                                 | 차현수  |
| GET    | 공인중개사 가지고 있는 매물 단건 조회        | /brokers/items/show/details/{brokerItemId}                          | 차현수  |
| GET    | 특정 지역 매물 조회 (공인중개사)          | /brokers/item?dong=동이름                                              | 차현수  |
|        |                              | 홈                                                                   |      |
| POST   | 매물 탭 메인                      | /main/item                                                          | 한수찬 |
| GET    | 매물 탭 메인                      | /main/item                                                          | 한수찬 |
| GET    | 홉 탭 메인                       | /home                                                               | 유미라 |
|        |                              | 일반유저의 요청정보 및 매칭이후 관리                                                |      |
| POST   | 유저 매물 요청정보 저장                | /users/items                                                        | 한수찬 |
| GET    | 매물 매칭 요청 동별 건수만 조회           | /users/items/dong-count                                             | 한수찬 |
| GET    | 매물 매칭 요청 조회 (공인중개사)          | /users/items                                                        | 한수찬 |
| GET    | 유저사이드 매칭조회                   | /match/users/items                                                  | 한수찬 |
| PATCH  | 매물별매칭상태변경                    | /match/brokers/{matchingId}                                         | 한수찬 |
|        |                              | 매칭                                                                  |      |
| POST   | 매물후보등록 (공인중개사)               | /match/brokers/{userItemId}                                         | 차현수  |
| GET    | 매칭전체조회(공인중개사)                | /match/brokers/items                                                | 차현수  |

<br>
