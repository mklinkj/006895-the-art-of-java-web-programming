# 31장. 도서 쇼핑몰 만들기

> 이번 장 같은 경우는, 내가 처음부터 부트스트랩으로 화면을 만들면서 진행하기가 어려울 것 같다.
>
> 저자님 코드의 동작을 확인 먼저하고 리펙토링 하는 식으로 진행하는게 올바를 것 같다.





### Java 소스파일이 부분적으로 EUC-KR로 인코딩 되어있다.

* 일괄로 변환해서 utf8 임시파일을 만드는 스크립트 작성
  * [changeEnc.sh](changeEnc.sh)



## DB별 데이터 입력 스크립트 정리

* [sql-script](sql-script)



## 서버 실행

* Jetty 서버 실행
  ```sh
  mvn clean jetty:run
  ```

* **메인 페이지 URL**
  
  * `http://localhost:8090/main/main.do`


> * 일단 화면 뜨는 것과 몇몇 기능 동작을 확인했다.





## 수정 / 개선 사항

>  수정 / 개선해야할 부분은 이 문서에다 추가해나감.

#### [2차 이터레이션](docs/2nd_Modifications.md)

* 어드민 패키지의 Service > Controller 수정
* 어드민 패키지의 ViewNameInterceptor 사용처는 전부 void 컨트롤러 메서드 사용방식 변경
* 페이지네비게이션 문제 수정

#### [1차 이터레이션](docs/1st_Modifications.md)

* DAO의 경우 모든 쿼리 확인 및 테스트 코드 작성
* 그 이외는 핵심 문제가 아닌 이상 조금 씩만 정리



## 기타

#### 예제 테스트

Javascript 라이브러리 같은 것을 새로 적용할 때... 간단한 Static HTML 파일에서 먼저 확인해보고 적용하자!

* [example-test](example-test)
