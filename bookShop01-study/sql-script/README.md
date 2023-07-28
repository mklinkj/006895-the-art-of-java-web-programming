# DB별 데이터 입력 스크립트



## [Oracle](oracle)

* 내가 사용중인 OracleXE 18c는 UTF-8로 동작하고 있어서 스크립트를 약간 수정할 필요가 있었다.
* SQL*Plus로 데이터 입력시 몇가지 주의할 점이 있었다. 문서 상세 참고.

## [HSQLDB](hsqldb)

* 테이블 생성 / 데이터 입력 / 시퀀스 생성 전부 잘 되었다.
* HSQLDB를 사용하면 Embedded로 쉽게 전환을 할 수 있어서, 추가해봤다.

## [MySQL](mysql)

* SEQUENCE 지원이 안되서 Auto Increment나, 시퀀스 관리 테이블 + 가져오기 프로시저를 만들어야하는데, 이건 맨 마지막에 생각해봐야겠다.  지금 할 내용은 아닌 것 같음. 😅