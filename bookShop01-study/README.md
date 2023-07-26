# 31장. 도서 쇼핑몰 만들기

> 이번 장 같은 경우는, 내가 처음부터 부트스트랩으로 화면을 만들면서 진행하기가 어려울 것 같다.
>
> 저자님 코드의 동작을 확인 먼저하고 리펙토링 하는 식으로 진행하는게 올바를 것 같다.





### Java 소스파일이 부분적으로 EUC-KR로 인코딩 되어있다.

* 일괄로 변환해서 utf8 임시파일을 만드는 스크립트 작성
  * [changeEnc.sh](changeEnc.sh)



## Oracle DB 데이터 입력

* [oracle-script](oracle-script)



## 서버 실행

* Jetty 서버 실행
  ```
  mvn clean jetty:run
  ```

* **메인 페이지 URL**
  * `http://localhost:8090/main/main.do`


> * 일단 화면 뜨는 것과 몇몇 기능 동작을 확인했다.