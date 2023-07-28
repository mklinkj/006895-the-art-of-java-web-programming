# MySQL 스키마 생성 및 데이터 입력

> ERD를 Visual Paradigm 쓰려다가 MySQL Workbench 사용해보는 것도 괜찮을 것 같아서...
>
> MySQL 8 기준으로 테이블 생성 및 데이터 입력을 해보자.



### 테이블명, 컬럼명의 대소문자 처리 확인

지금 쓰는 것은 도커로 받은 MySQL 8인데, 대소문자 처리가 어떻게 되는지 확인해보았다.

```sql
SHOW VARIABLES LIKE 'lower_case_table_names';
```

| Variable_name          | Value |
| ---------------------- | ----- |
| lower_case_table_names | 0     |

결과는 0이였다.**현재 상태는 대소문자를 구분한다.**

> ✨ Oracle은 `"`로 묶지 않는 이상 대소문자를 구분하지 않음.

* 그런데.. 저자님 코드를 보니... 쌍따옴표(`"`)로 묶여있다.
* MyBatis의 Snake Case To Camel Case 자동변환 기능을 쓰려면 제대로 될지 모르겠다. 🤔
  * ResultMap을 의무적으로 쓰게되면 머리아파서 😂
* 그냥 소문자로 기준으로 바꿔보고 싶긴하지만... **점진적인 수정**을 하는게 중요하므로 대문자 이름 그대로 해서 진행을 먼저 하자.



### VARCHAR에 BYTE 단위 지정해서 사용

Oracle에서 바이트 크기로 지정한 VARCHAR2를 아래 처럼 사용할 수 있는데,

```sql
MEMBER_ID    VARCHAR2(20 BYTE) PRIMARY KEY, 
```

MySQL에서는 

```sql
MEMBER_ID    VARCHAR(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin,
```

위처럼 사용할 수 있긴한데... 글자 20자가 가능하다는 의미로 단순하게 아래 처럼 쓰는게 나을 것 같다.

```sql
MEMBER_ID    VARCHAR(20), 
```



### 로그인 암호 컬럼 길이

* 나중에 회원 관리로 Spring Security를 적용하게 될지는 모르겠지만.. Bcyrpt로 암호화를 해서 저장을 하면 길이가 충분해야하니.. 

  ```sql
  MEMBER_PW               VARCHAR(100), -- 100자로 늘림. 60자 이상 필요.
  ```



### CLOB 컬럼은 TEXT로 사용

Oracle 스크립트에서 상품 상세 설명이 CLOB로 되어있었는데, MySQL에서는 TEXT 타입을 사용함.



### 날짜 함수

* Oracle
  * `TO_DATE('18/10/17','RR/MM/DD')`
* MySQL
  * `STR_TO_DATE('18/10/17','%Y/%m/%d')`



## 👺 알림

#### ✨ 잠깐 중단..

MySQL로 하려다가 MySQL 8을 사용하더라도 SEQUENCE를 만들 수 가 없다.

Auto Increment를 사용하거나, SEQUENCE를 관리하는 테이블을 만들고 시퀀스를 가져오는 프로시저를 만들어야하는데... 아직은 그렇게 까지는 할 필요가 없어보여서, MySQL은 쓰지 말자.

다른 DB로 다 완료되고 해보는게 낫겠다.
