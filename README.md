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
