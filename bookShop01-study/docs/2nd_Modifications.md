# 2차 이터레이션 수정 / 개선 사항

> 패키지 단위로 Service > Controller 수정을 진행해보자.
>
> ViewNameInterceptor 사용처는 전부 void 컨트롤러 메서드 사용방식으로 바꾸자.



## 1. `admin.goods` 패키지 이하 수정

### 단순 변경

* [x] multipoart 설정이나, web.xml의 CharacterEncodingFilter를 통해 UTF-8 설정이 되어있는데 불필요하게 재선언하는 부분이 있다. > 제거
* [x] ViewNameInterceptor 사용처에 대해 컨트롤러 void 메서드 전환
* [x] System.out.println을 통한 로그 출력이나, 스택 트레이스 출력은 LOGGER로 바꿈.
* [x] 컬랙션과 Map 들에 대해 Generic 타입 명시
* [x] ResponseEntity를 반환하는 메서드에 `@ResponseBody` 붙임
  * 딱히 붙이지 않아도 동작은 하는데... 명시적으로 넣어주는게 보기 좋은 것 같다.



### 단순 변경 후 메뉴 동작 테스트, 에러 확인

> 테스트를 전역적으로 만들기 전에, 동작오류를 먼저 확인하고, 명백한 부분부터 수정해나가자.👍
>
> 메뉴: `어드민 로그인` > `관리자` > `상품 관리`

- [x] 상품 관리 > 특정 상품 선택 > 상품 이미지 탭 

  * 문제 

    * 이미지가 깨져서 보임.

  * 원인

    * 컨트롤러의 경로가 `/download` 인데, View에서 `/download.do`로 호출하고 있음.

    

- [x] 상품 관리 > 특정 상품 선택 > 출판사 상품 평가, 추천사

  * 문제

    * 데이터 입력시 업데이트가 실패함

      ```sql
      UPDATE t_shopping_goods            WHERE     goods_id=?
      ```

      * MyBatis가 만든 쿼리를 보면 UPDATE 중간에 SET 조건이 하나도 안들어가 있음.
      * 추천사를 등록하고 중단점 걸고 정보를 봤을 때... goods_recommendation 가 ""으로 전달되고 있음.

  * 원인

    * `modifyGoodsForm.jsp` 파일에서 `fn_modify_goods()` 함수내에서  form 내부의 이름 속성 접근이 잘못됨

- [x] 상품 관리 > 특정 상품 선택 > 상품 목차등 `<textarea>`로 입력하는 부분들...

  * 문제 
    * 값을 입력하고 저장하면  입력한 값 좌우로 다수의 공백이 저장되는 것 처럼 보인다.
  * 원인
    * `modifyGoodsForm.jsp`에 `<textarea>` 에 공백이 포함되서 값이 들어가게 되어있음.





#### 기타

* [x] `BaseController`의 calcSearchPeriod 메서드는 유틸리티 성 메서드라 상속대신 유틸클래스틀 통해 사용하도록 하는게 나아보인다. > DateUtils로 이동

  





---

## 중간 중간 의견들...

* ...



---

## 2차 테스트 / 수정 최종의견

* ...
