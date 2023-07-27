# SQL*Plus 실행관련 이슈



## 다운로드 

* https://download.oracle.com/otn_software/nt/instantclient/1919000/instantclient-basic-windows.x64-19.19.0.0.0dbru.zip
* https://download.oracle.com/otn_software/nt/instantclient/1919000/instantclient-sqlplus-windows.x64-19.19.0.0.0dbru.zip
* https://download.oracle.com/otn_software/nt/instantclient/1919000/instantclient-tools-windows.x64-19.19.0.0.0dbru.zip

위의 3개 압축 파일 받아다 한곳에 풀었음.

그러면 SQL*Plus를 사용할 수 있음.



## Oracle XE 18c DB가 UTF-8환경이라서 해줘야했던 일들..

```
C:\sqlplus> CHCP 65001
C:\sqlplus> SET NLS_LANG=KOREAN_KOREA.AL32UTF8
C:\sqlplus> sqlplus scott@localvmdb.oracle_xe_18c:1521
```

1. 실행전에 NLS_LANG을 설정해줘야했음.
2. DB도 UTF-8이고, sql스크립트도 UTF-8로 컨버팅 했기 때문에, CMD의 문자셋을CHCP명령어를 실행해서 UTF-8로 변경했음.



### 스크립트 실행

```
SQL> @@C:\git\006895-the-art-of-java-web-programming\bookShop01-study\oracle-script\shopping_table_schema.sql
```

접속완료 한후 위와 같은 식으로 스크립트의 절대경로를 입력해서 실행 시켜줄 수 있음.



### 스크립트 변경사항

JDBC 드라이버를 통해서 스크립트를 실행하면 드라이버가 알아서 처리해주는 것이 있을 텐데, SQLPlus에서 실행하려면 뭔가 옵션들이 더 필요함.

#### 1. 값에 공백행이 포함되었을 때.

```sql
-- 공백 행이 있을 때, SP2-0734가 발생해서 추가
SET SQLBLANKLINES ON 
```



#### 2. SQL 구문 한줄이 3000자 넘어갈 때의 문제

```sql
-- SQL 구문 자체가 3000자를 넘어가면 경고가 나온다.
-- 이때는 가능하면 컬럼들 사이의 ',' 구분자 기준으로 개행을 해주면 쉽게해결된다. 
...
```



#### 3. 값에 #이 들어갔을 때..

```sql
-- 아래 처럼 이스케이프 처리를 해주자..
'\# 하루 15분! 자바스크립트 기초부터 웹 프런트엔드까지!'
...
```



#### 기타 사항으로...

이미지 ID(346)가 중복된 행이 하나 있어서 주석으로 바꿈. 어차피 기본키 제약조건 오류로 입력되지 않는 행..

```sql
INSERT INTO T_GOODS_DETAIL_IMAGE (IMAGE_ID,GOODS_ID,FILENAME,REG_ID,FILETYPE,CREDATE) VALUES (346,356,'마인_상세1.jpg','admin','detail_image1',TO_DATE('18/10/23','RR/MM/DD'));
```


#### SQL 스크립트를 JDBC 연결을 통해 실행시킬 수 있는 도구가 이미 있다면.. 
그것을 사용하는게 편할 것 같다.
SQL*Plus에서 사용하는 구문을 제거한 sql파일도 별도로 추가했다.

* [shopping_table_schema-jdbc.sql](shopping_table_schema-jdbc.sql)