# HSQLDB 스키마 생성 및 데이터 입력

> HSQLDB로 사용할 때의 큰 장점은, 스터디 프로젝트에서 아주 큰데 ...
>
> 쉽게 Embedded 모드로 실행시킬수 있어서 동작을 바로 확인할 수 있다는점..
>
> 그리고 Oracle과 호환성이 좋은 편이다.



### 테이블명, 컬럼명의 대소문자 처리 확인

HSQLDB도 대소문자 처리는 Oracle 처럼 `"`로 감싸지 않는 이상은 대소문자 구분을 하지 않는데, 따옴표는 빼두자..



### 시퀀스

* https://www.hsqldb.org/doc/guide/databaseobjects-chapt.html#dbc_sequence_creation

  > 명명된 시퀀스 생성기를 정의합니다. SEQUENCE 객체는 지정된 규칙에 따라 정수의 시퀀스를 생성합니다. 옵션이 없는 간단한 정의는 1에서 시작하여 1씩 증가하는 INTEGER 타입의 숫자 시퀀스를 정의합니다. 기본적으로 CYCLE 속성이 설정되어 있으며 최소 및 최대 한계는 반환되는 값 유형의 최소 및 최대 한계입니다. 시퀀스의 다양한 속성을 변경하는 데는 설명이 필요 없는 옵션이 있습니다. MAXVALUE 및 MINVALUE는 상한과 하한을 지정합니다. CYCLE을 지정하면 시퀀스가 범위에서 가장 높은 값 또는 가장 낮은 값을 반환한 후 다음 값은 각각 범위에서 가장 낮은 값 또는 가장 높은 값이 됩니다. CYCLE이 지정되지 않은 경우 시퀀스 생성기를 사용하면 한계에 도달하면 오류가 발생합니다.
  >
  > 정수 유형: 시퀀스 유형으로 SMALLINT, INTEGER, BIGINT, DECIMAL 및 NUMERIC을 사용할 수 있습니다. DECIMAL 및 NUMERIC 유형은 스케일이 0이고 정밀도가 18을 초과하지 않아야 합니다.

  

* **각 옵션 대응**

  | Oracle         | HSQLDB         |
  | -------------- | -------------- |
  | NOCYCLE        | **NO CYCLE**   |
  | MINVALUE       | MINVALUE       |
  | MAXVALUE       | MAXVALUE       |
  | START WITH 400 | START WITH 400 |
  | INCREMENT BY 1 | INCREMENT BY 1 |
  | CACHE / NOCACHE | **대응 옵션 없음** |
  | ORDER / NOORDER | **대응 옵션 없음** |

  기본적인 설정은 그대로 동일하고, CACHE, ORDER 옵션이 HSQLDB에는 없다. 없어도 동작에 지장은 없을 것 같다.



### 날짜함수

* MySQL 
  * `STR_TO_DATE('18/10/16','%Y/%m/%d')`
* HSQLDB
  * `TO_DATE('18/10/16','YY/mm/dd')`
