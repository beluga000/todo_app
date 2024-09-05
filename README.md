# 1. 앱에 대한 설명

이 애플리케이션은 사용자가 할일(To-Do)을 관리할 수 있도록 도와주는 RESTful API 기반의 웹 애플리케이션입니다.
사용자는 할일을 생성하고, 수정하며, 삭제할 수 있고, 다른 사용자와 할일을 공유할 수도 있습니다.
각 할일은 ‘완료’ 또는 ‘미완료’ 상태로 업데이트할 수 있으며, 사용자는 본인이 작성한 할일뿐만 아니라 다른 사용자와 공유된 할일도 조회할 수 있습니다.

### 주요 기능

	•	할일 생성/수정/삭제
	•	할일 상태(완료/미완료) 업데이트
	•	특정 사용자와 할일 공유
	•	사용자가 작성한 할일 및 공유받은 할일 조회

# 2. 소스 빌드 및 실행 방법 메뉴얼(DB 스키마 포함)

	•	Java Development Kit (JDK) 11 이상 설치
	•	Maven 3.6 이상 설치
	•	MySQL 8.0 이상 설치 및 실행
	•	MySQL 데이터베이스 설정

### 2.1 MySQL 데이터베이스 설정
```
CREATE DATABASE todo_app;
CREATE USER 'todo_user'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON todo_app.* TO 'todo_user'@'localhost';
FLUSH PRIVILEGES;
```

### 2.2 소스 빌드 및 실행 방법

#### 2.2.1 소스코드 다운로드 및 빌드 
프로젝트를 클론하거나 다운로드 후, 프로젝트의 루트 디렉터리로 이동합니다.
Maven을 사용하여 의존성을 설치하고 애플리케이션을 빌드합니다.
```
mvn clean install
```

#### 2.2.2 설정 파일 수정
src/main/resources/application.properties 파일을 열어 데이터베이스 설정 및 Swagger 설정을 수정합니다.
```
spring.datasource.url=jdbc:mysql://localhost:3306/todo_app
spring.datasource.username=todo_user
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
springdoc.api-docs.enabled=true
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.enabled=true
```

#### 2.2.3 애플리케이션 실행
다음 명령어를 실행하여 애플리케이션을 시작합니다.
```
mvn spring-boot:run
```
애플리케이션 실행 확인은 브라우저에서 URL : localhost:8080 로 이동하여 화면에 ToDo App 텍스트가 확인된다면 애플리케이션 정상 실행중 입니다.

#### 2.2.4 Swagger UI 확인
애플리케이션이 실행되면, 브라우저에서 Swagger UI로 이동하여 API를 테스트할 수 있습니다.
URL : http://localhost:8080/swagger-ui.html


#### 2.2.5 DB 스키마
```
CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL,
  email VARCHAR(100) NOT NULL,
  password VARCHAR(100) NOT NULL
);

CREATE TABLE todos (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  completed BOOLEAN NOT NULL DEFAULT false,
  owner_id BIGINT NOT NULL,
  FOREIGN KEY (owner_id) REFERENCES users(id)
);

CREATE TABLE todo_shared_users (
  todo_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  PRIMARY KEY (todo_id, user_id),
  FOREIGN KEY (todo_id) REFERENCES todos(id),
  FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE todo_shares (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  to_do_id BIGINT NOT NULL,
  owner_id BIGINT NOT NULL,
  shared_with_user_id BIGINT NOT NULL,
  FOREIGN KEY (to_do_id) REFERENCES todos(id),
  FOREIGN KEY (owner_id) REFERENCES users(id),
  FOREIGN KEY (shared_with_user_id) REFERENCES users(id)
);
```

# 3. 주력으로 사용한 컴포넌트에 대한 설명 및 사용 이유

1️⃣ Spring Boot

Spring Boot는 애플리케이션을 빠르고 쉽게 설정할 수 있도록 해줍니다. 내장 서버 제공, 자동 설정, 의존성 관리와 같은 기능 덕분에 REST API를 구축하는 데 있어 매우 적합한 선택입니다.

2️⃣ Spring Data JPA

Spring Data JPA는 데이터베이스 접근을 위한 간단하고 일관된 추상화를 제공합니다. 이 애플리케이션에서는 MySQL 데이터베이스와 상호작용하기 위해 사용했습니다. JPA의 CRUD 메서드 제공, 관계 매핑 등 다양한 기능을 통해 코드의 양을 크게 줄였습니다.

3️⃣ MySQL

애플리케이션의 데이터를 안정적으로 저장하고 관리하기 위해 관계형 데이터베이스인 MySQL을 선택했습니다. 데이터 정합성 유지와 복잡한 관계형 데이터 관리를 쉽게 처리할 수 있기 때문에 선택했습니다.

4️⃣ Swagger

API 문서화를 위해 Swagger를 사용했습니다. Swagger는 RESTful API의 명세를 자동으로 생성하고, 이를 통해 쉽게 API를 테스트할 수 있는 UI도 제공합니다. 이를 통해 개발자는 자신이 작성한 API를 보다 직관적으로 이해하고 사용할 수 있습니다.

# 4. API 명세(Swagger 사용)

Swagger UI는 애플리케이션이 실행될 때 자동으로 생성됩니다. 모든 API 엔드포인트는 http://localhost:8080/swagger-ui.html에서 확인할 수 있습니다. 아래는 주요 API와 설명입니다.

1️⃣ POST /api/todos: 새로운 할일 생성<br>
Request Body (JSON)
```
{
  "title": "할일 제목",
  "description": "할일 설명",
  "completed": false,
  "owner": {
    "id": 1
  }
}
```

2️⃣ PUT /api/todos/{id}: 할일 수정<br>
Request Body (JSON)
```
{
  "title": "수정된 제목",
  "description": "수정된 설명",
  "completed": true,
  "owner": {
    "id": 1
  }
}
```

3️⃣ PATCH /api/todos/{id}/status: 할일 상태 변경<br>
Request Parameters <br>
completed : true 또는 false

4️⃣ POST /api/todos/share: To-Do 공유<br>
Request Parameters<br>
toDoId : 공유할 To-Do의 ID<br>
ownerId : To-Do 소유자의 ID<br>
sharedWithUserId : 공유받을 사용자의 ID<br>

5️⃣ POST /api/users : 새로운 사용자 생성<br>
Request Body (JSON)
```
{
  "username": "사용자 이름",
  "email": "이메일",
  "password": "비밀번호"
}
```

6️⃣ GET /api/todos/owner/{ownerid} : 특정 사용자의 작성 및 공유받은 할일 조회<br>
Request Parameters<br>
{ownerid} : 사용자 ID

7️⃣ GET /api/todos/share/{userid} : 특정 사용자가 공유 받은 할일 조회<br>
Request Parameters<br>
{userid} : 사용자 ID
