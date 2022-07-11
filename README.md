# Triple Mileage Service - Assignment
- 해당 프로젝트는 트리플 Tour&Activity 백엔드 엔지니어 부문 과제를 수행한 내용입니다.

## 1. 사용 스택
- Spring Boot & JPA
- MySQL
- Docker

## 2. API 구성
- 유저 추가 API `(/users)`
- 장소 추가 API `(/places)`
    - 원활한 실행을 위해서 유저와 장소를 반드시 추가해주시고 Event API를 실행해주세요!
- Event API `(/events)`
    - 리뷰 작성/수정/삭제가 가능함
- 리뷰 id로 내용 조회 api `(/reviews/:reviewId)`
    - 리뷰 아이디를 path variable에 추가하면 리뷰 내용을 조회할 수 있습니다.
- 유저 포인트 조회 `(/users/:userId/total-point)`
    - 현재까지 포인트이력을 조회해서 유저 포인트의 총합을 계산합니다.
 <br>
- API 테스트를 할 수 있도록 구성해둔 Postman 환경을 확인하시려면 [이 링크](https://www.postman.com/solar-resonance-194082/workspace/triple-mileage/collection/19267267-73b1ca0d-18e8-4b0e-9dec-ed383beeaf14?action=share&creator=19267267) 를 클릭해주세요!     
- API 설명, 사용방법, request/response 구성, 실행 예시는 [API 명세서](https://documenter.getpostman.com/view/19267267/UzJPMajr) 를 참고해주세요!

## 3. ER Diagram
![image](https://user-images.githubusercontent.com/65891711/178157283-ab0afad8-4ff5-4007-ad8d-928eb08ece1c.png)


## 4. 실행방법
** 반드시 순서대로 실행해야 하며, 모든 과정은 도커가 설치되어있다는 가정 하에 진행합니다.
1. 터미널을 통해서 도커의 MySQL 이미지를 다운받습니다.
    ```
    Docker pull MySQL
   ```

2. MySQL 컨테이너를 생성하고 실행합니다.
   ```
    docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=1234 -d -p 3306:3306 mysql:latest
    ```
3. 컨테이너와 MySQL 쉘에 접속합니다. password는 1234로 설정했습니다.
   ```
   docker exec -it mysql-container bash
   mysql -u root -p
   ```
4. 테이블을 생성하기 위해 아래 DDL을 실행시켜 줍니다.
    ```
   create database mileage;
    
    use mileage;
    
    CREATE TABLE mileage.place
    (
        `place_id`    BINARY(16)     NOT NULL, 
        `place_name`  VARCHAR(45)    NOT NULL, 
         PRIMARY KEY (place_id)
    );
    
    
    CREATE TABLE mileage.user
    (
        `user_id`    BINARY(16)     NOT NULL, 
        `user_name`  VARCHAR(10)    NOT NULL, 
         PRIMARY KEY (user_id)
    );
    
    
    
    CREATE TABLE mileage.review
    (
        `review_id`      BINARY(16)    NOT NULL, 
        `content`        TEXT          NOT NULL, 
        `user_id`        BINARY(16)    NULL, 
        `place_id`       BINARY(16)    NULL, 
        `content_point`  TINYINT       NOT NULL, 
        `photo_point`    TINYINT       NOT NULL, 
        `bonus_point`    TINYINT       NOT NULL, 
        `date`           TIMESTAMP     NOT NULL, 
         PRIMARY KEY (review_id)
    );
    
    ALTER TABLE mileage.review
        ADD CONSTRAINT FK_review_user_id_user_user_id FOREIGN KEY (user_id)
            REFERENCES mileage.user (user_id);
    
    ALTER TABLE mileage.review
        ADD CONSTRAINT FK_review_place_id_place_place_id FOREIGN KEY (place_id)
            REFERENCES mileage.place (place_id);
    
    
    CREATE TABLE mileage.photo
    (
        `photo_id`   BINARY(16)    NOT NULL, 
        `review_id`  BINARY(16)    NOT NULL, 
         PRIMARY KEY (photo_id)
    );
    
    ALTER TABLE mileage.photo
        ADD CONSTRAINT FK_photo_review_id_review_review_id FOREIGN KEY (review_id)
            REFERENCES mileage.review (review_id);
    
    
    CREATE TABLE mileage.point_history
    (
        `point_id`         BIGINT        NOT NULL    AUTO_INCREMENT, 
        `changed_point`    INT           NOT NULL, 
        `point_situation`  INT           NOT NULL, 
        `date`             TIMESTAMP     NOT NULL, 
        `user_id`          BINARY(16)    NOT NULL, 
         PRIMARY KEY (point_id)
    );
    
    ALTER TABLE mileage.point_history
        ADD CONSTRAINT FK_point_history_user_id_user_user_id FOREIGN KEY (user_id)
            REFERENCES mileage.user (user_id);
    ```
   
5. exit 명령어를 통해 mysql shell에서 나오고, bash 부분에서 ctrl+p,ctrl+q를 연달아 누르면서 mysql 컨테이너를 실행한 상태로 터미널로 돌아갑니다.
  <br> ![image](https://user-images.githubusercontent.com/65891711/178160316-5b599a0a-1d56-4414-9947-be62f16e8d1b.png)

   
6. 아래 명령어를 실행시켜 해당 프로젝트의 도커 이미지를 다운받고 컨테이너를 만들어 실행합니다.
    ````
    docker pull skyrider/triple-assignment:0.0.1
    docker run -d --name triple-container -p 8080:8080 --link mysql-container skyrider1811/triple-assignment:0.0.1
    ````
7. localhost:8080/{uri}를 통해 API를 테스트합니다.
 <br>  ![image](https://user-images.githubusercontent.com/65891711/178160347-122f6637-d0af-46f7-b60e-12009e0d1fd4.png)

8. 컨테이너를 멈추고 추후 재실행 시킬때는 아래 명령어를 실행합니다.
    ```
    docker stop triple-container
    docker start triple-container
    ```