# Unity Game Backend API Server

Unity 클라이언트와 연동되는 게임 백엔드 API 서버입니다.
본 프로젝트는 수업 과제 형식으로 진행되며, Unity 게임 클라이언트가 HTTP API를 통해 유저 정보, 인증, 아이템, 인벤토리, 지갑, 친구, NPC 상점 데이터를 주고받을 수 있도록 구현하는 것을 목표로 합니다.

---

## 프로젝트 개요

이 프로젝트는 Unity 게임 실행 시 필요한 게임 데이터를 서버에서 관리하기 위한 백엔드 서버입니다.

클라이언트는 Unity로 제작된 게임이며, 백엔드 서버는 REST API를 제공하여 다음과 같은 기능을 처리합니다.

* 유저 회원가입 및 로그인
* JWT 기반 인증
* Access Token 재발급
* 유저 계정 정보 조회
* 아이템 목록 조회
* 유저 인벤토리 조회 및 관리
* 유저 지갑 조회
* NPC 목록 및 NPC 상점 조회
* NPC 상점 아이템 구매
* 친구 요청, 수락, 거절, 취소, 삭제

단순 데이터 조회뿐 아니라, 유저의 골드 차감, 아이템 지급, 인벤토리 수량 변경과 같은 게임 상태 변경 로직을 서버에서 안정적으로 처리하는 것을 목표로 합니다.

---

## 프로젝트 목표

### 필수 목표

* API 명세서에 정의된 엔드포인트 구현
* DB를 통한 유저 및 게임 데이터 관리
* Unity 클라이언트와 `localhost` 기반 통신
* 공통 응답 형식 제공
* 인증이 필요한 API에 JWT 인증 적용

### 도전 목표

* 관리자 대시보드 구현
* 유저 초기 보유금 및 아이템 설정 기능
* 서버 배포
* Unity WebGL 배포 환경과 API 서버 연동
* 이벤트 로그 및 간단한 분석 대시보드 구현

---

## 주요 기능

### 인증

* 이메일 로그인
* JWT Access Token 발급
* Refresh Token 발급 및 갱신
* 로그아웃
* 인증이 필요한 API에서 현재 로그인 유저 식별

### 유저

* 회원가입
* 내 계정 정보 조회
* 내 전체 게임 데이터 조회

  * 계정 정보
  * 프로필
  * 지갑
  * 인벤토리
  * 친구 수

### 아이템

* 전체 아이템 목록 조회
* 아이템 상세 조회
* Unity 리소스와 연결하기 위한 `rId` 제공

### 인벤토리

* 내 인벤토리 조회
* 아이템 획득
* 아이템 버리기
* 보유 수량 관리

### 지갑

* 골드 조회
* 보석 조회
* NPC 상점 구매 시 골드 차감

### NPC / 상점

* NPC 목록 조회
* NPC 상세 조회
* NPC별 상점 아이템 조회
* NPC 상점 아이템 구매
* 구매 시 골드 차감 및 인벤토리 아이템 추가

### 친구

* 친구 목록 조회
* 받은 친구 요청 목록 조회
* 친구 요청 전송
* 친구 요청 수락
* 친구 요청 거절
* 친구 요청 취소
* 친구 삭제

---

## 기술 스택

### Backend

* Java
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Security
* JWT

### Database

* MySQL 또는 H2
* JPA / Hibernate

### Client

* Unity
* Unity WebGL
* HTTP 통신

### Tools

* Git
* GitHub
* GitHub Issues
* GitHub Projects
* Postman 또는 Swagger

---

## API 공통 응답 형식

모든 API는 다음과 같은 공통 응답 구조를 사용합니다.

```json
{
  "success": true,
  "message": "요청이 성공적으로 처리되었습니다.",
  "data": {}
}
```

에러 응답 예시는 다음과 같습니다.

```json
{
  "success": false,
  "message": "에러 메시지",
  "data": null
}
```

---

## 주요 API 목록

### Auth API

| 기능     | Method | Endpoint               | 인증  |
| ------ | ------ | ---------------------- | --- |
| 로그인    | POST   | `/api/v1/auth/login`   | 불필요 |
| 토큰 재발급 | POST   | `/api/v1/auth/refresh` | 불필요 |
| 로그아웃   | POST   | `/api/v1/auth/logout`  | 필요  |

### User API

| 기능          | Method | Endpoint                 | 인증  |
| ----------- | ------ | ------------------------ | --- |
| 회원가입        | POST   | `/api/v1/users/register` | 불필요 |
| 내 계정 정보 조회  | GET    | `/api/v1/users/me`       | 필요  |
| 내 전체 데이터 조회 | GET    | `/api/v1/users/me/data`  | 필요  |

### Item API

| 기능           | Method | Endpoint             | 인증  |
| ------------ | ------ | -------------------- | --- |
| 전체 아이템 목록 조회 | GET    | `/api/v1/items`      | 불필요 |
| 아이템 단건 조회    | GET    | `/api/v1/items/{id}` | 불필요 |

### Inventory API

| 기능      | Method | Endpoint                                      | 인증 |
| ------- | ------ | --------------------------------------------- | -- |
| 인벤토리 조회 | GET    | `/api/v1/users/me/inventory`                  | 필요 |
| 아이템 획득  | POST   | `/api/v1/users/me/inventory/pickup`           | 필요 |
| 아이템 버리기 | DELETE | `/api/v1/users/me/inventory/{itemId}/discard` | 필요 |

### Profile / Wallet API

| 기능     | Method | Endpoint                   | 인증 |
| ------ | ------ | -------------------------- | -- |
| 프로필 조회 | GET    | `/api/v1/users/me/profile` | 필요 |
| 지갑 조회  | GET    | `/api/v1/users/me/wallet`  | 필요 |

### NPC API

| 기능            | Method | Endpoint                                                   | 인증  |
| ------------- | ------ | ---------------------------------------------------------- | --- |
| NPC 목록 조회     | GET    | `/api/v1/npcs`                                             | 불필요 |
| NPC 단건 조회     | GET    | `/api/v1/npcs/{id}`                                        | 불필요 |
| NPC 상점 아이템 구매 | POST   | `/api/v1/users/me/npcs/{npcId}/items/{npcItemId}/purchase` | 필요  |

### Friend API

| 기능             | Method | Endpoint                                                | 인증 |
| -------------- | ------ | ------------------------------------------------------- | -- |
| 친구 목록 조회       | GET    | `/api/v1/users/me/friends`                              | 필요 |
| 받은 친구 요청 목록 조회 | GET    | `/api/v1/users/me/friends/requests`                     | 필요 |
| 친구 요청 전송       | POST   | `/api/v1/users/me/friends/requests`                     | 필요 |
| 친구 요청 수락       | POST   | `/api/v1/users/me/friends/requests/{requestId}/accept`  | 필요 |
| 친구 요청 거절       | POST   | `/api/v1/users/me/friends/requests/{requestId}/decline` | 필요 |
| 친구 요청 취소       | DELETE | `/api/v1/users/me/friends/requests/{requestId}`         | 필요 |
| 친구 삭제          | DELETE | `/api/v1/users/me/friends/{friendUserId}`               | 필요 |

---

## 핵심 비즈니스 로직

### NPC 상점 아이템 구매

NPC 상점 구매 API는 이 프로젝트의 핵심 기능입니다.

구매 요청이 들어오면 서버는 다음 흐름을 처리합니다.

1. 현재 로그인 유저를 식별한다.
2. NPC가 존재하는지 확인한다.
3. NPC 상점 아이템이 존재하는지 확인한다.
4. 구매 수량이 올바른지 검증한다.
5. 유저가 충분한 골드를 보유하고 있는지 확인한다.
6. 유저의 골드를 차감한다.
7. 인벤토리에 아이템을 추가하거나 기존 수량을 증가시킨다.
8. 변경된 지갑 정보와 획득한 아이템 정보를 반환한다.

이 과정은 하나의 트랜잭션으로 처리되어야 합니다.
골드 차감과 인벤토리 지급 중 하나만 성공하는 상황이 발생하지 않도록 관리합니다.

---

## 예상 ERD 구성

```text
User
 ├─ UserProfile
 ├─ Wallet
 ├─ UserItem
 ├─ RefreshToken
 └─ FriendRequest

Item
 └─ UserItem

Npc
 └─ NpcShopItem
      └─ Item
```

---

## 주요 Entity

### User

* 유저 계정 정보를 관리합니다.
* 이메일, 비밀번호, 닉네임, 권한, 상태 등을 저장합니다.

### RefreshToken

* 로그인 유지 및 Access Token 재발급을 위한 Refresh Token을 저장합니다.

### Item

* 게임 내 전체 아이템 정보를 관리합니다.
* Unity 리소스와 연결하기 위한 `rId` 값을 포함합니다.

### UserItem

* 특정 유저가 보유한 아이템 정보를 관리합니다.
* 아이템 수량, 장착 여부, 획득 시각 등을 저장합니다.

### UserProfile

* 유저의 게임 진행 정보를 관리합니다.
* 레벨, 경험치, 총 플레이 시간을 저장합니다.

### Wallet

* 유저의 재화 정보를 관리합니다.
* 골드와 보석을 저장합니다.

### FriendRequest

* 친구 요청 및 친구 관계 상태를 관리합니다.
* PENDING, ACCEPTED, DECLINED, CANCELED 등의 상태를 가질 수 있습니다.

### Npc

* 게임 내 NPC 정보를 관리합니다.

### NpcShopItem

* NPC가 판매하는 아이템 정보를 관리합니다.

---

## 프로젝트 구조 예시

```text
src/main/java
└── com.example.game
    ├── common
    │   ├── response
    │   └── exception
    ├── auth
    │   ├── controller
    │   ├── service
    │   ├── dto
    │   ├── entity
    │   └── repository
    ├── user
    │   ├── controller
    │   ├── service
    │   ├── dto
    │   ├── entity
    │   └── repository
    ├── item
    ├── inventory
    ├── friend
    ├── profile
    ├── wallet
    └── npc
```

---

## 로컬 실행 방법

### 1. Repository Clone

```bash
git clone https://github.com/your-organization/your-repository.git
cd your-repository
```

### 2. 환경 설정

`application.yml` 또는 `application.properties`에 DB 정보를 설정합니다.

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/game_db
    username: root
    password: password

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### 3. 서버 실행

```bash
./gradlew bootRun
```

또는 Windows 환경에서는 다음 명령어를 사용합니다.

```bash
gradlew.bat bootRun
```

### 4. 서버 접속 확인

```text
http://localhost:8080
```

---

## Unity 연동

Unity 클라이언트는 로컬 개발 환경에서 다음 API 서버 주소를 사용합니다.

```text
http://localhost:8080
```

Unity에서 인증이 필요한 API를 호출할 때는 로그인 후 발급받은 Access Token을 Authorization 헤더에 포함해야 합니다.

```http
Authorization: Bearer {accessToken}
```

예시:

```http
GET /api/v1/users/me
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

## 개발 규칙

### Branch Naming

```text
feat/inf-unity-001-login
feat/inf-unity-008-item-list
fix/login-token-error
docs/update-api-readme
chore/project-setting
```

### Commit Message

```text
feat: 로그인 API 구현
feat: 아이템 목록 조회 API 구현
fix: JWT 인증 실패 처리 수정
docs: README API 목록 추가
chore: 프로젝트 초기 설정
```

### Issue Naming

```text
[FEATURE] INF_UNITY_001 이메일 로그인 API 구현
[FEATURE] INF_UNITY_008 전체 아이템 목록 조회 API 구현
[CHORE] DB 연결 및 JPA 설정 구성
[DOCS] API 명세 기반 README 정리
```

---

## 구현 제외 또는 확인 필요 API

다음 API는 명세서 기준으로 폐기되었거나 상세 명세 확인이 필요합니다.

### 폐기 API

| API ID        | 기능            |
| ------------- | ------------- |
| INF_UNITY_002 | Google 소셜 로그인 |
| INF_UNITY_018 | 아이템 장착        |

### 확인 필요 API

| API ID        | 기능        |
| ------------- | --------- |
| INF_UNITY_019 | 아이템 장착 해제 |
| INF_UNITY_020 | 아이템 사용    |

해당 API는 Unity 클라이언트에서 실제 호출 여부와 상세 Request/Response 명세를 확인한 뒤 구현 여부를 결정합니다.

---

## 향후 개선 방향

* 관리자 대시보드 구현
* 유저 초기 골드 및 아이템 설정 기능
* 아이템 지급 및 회수 기능
* 이벤트 로그 저장
* 가입자 수, 접속자 수, 구매 횟수 통계 API
* Unity WebGL 배포 환경 연동
* 서버 배포
* Swagger 기반 API 문서 자동화

---

## 팀 협업 방식

본 프로젝트는 GitHub Issues와 GitHub Projects를 기반으로 작업을 관리합니다.

1. API 명세서를 기준으로 이슈를 생성한다.
2. 이슈 단위로 브랜치를 생성한다.
3. 기능 구현 후 Pull Request를 생성한다.
4. 코드 리뷰 후 dev 브랜치에 병합한다.
5. Unity 클라이언트와 API 연동 테스트를 진행한다.

---

## 프로젝트 상태

현재 프로젝트는 수업 과제 요구사항을 기반으로 개발 중입니다.
API 명세서에 정의된 필수 기능 구현을 우선으로 하며, 이후 관리자 기능과 분석 대시보드 등 도전 기능을 확장할 예정입니다.
