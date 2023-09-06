# 2차 이터레이션 수정 / 개선 사항

> 패키지 단위로 Service > Controller 수정을 진행해보자.
>
> ViewNameInterceptor 사용처는 전부 void 컨트롤러 메서드 사용방식으로 바꾸자.
>
> ✨ 제일 중요한건.. 테스트 코드에 매달려서 ... 너무 질질 끌면 안됌, 동작하지 않는 필수 기능 수정 위주로 먼저 봐야한다.



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

- [x] 상품 관리 > 상단 검색 조건 메뉴들이 정상 동작하지 않음.
  * 문제
    * `[당일]`, `[1주]` ... 이런 버튼이 동작하지 않음
    * 등록일로 조회, 상세조회 토글이 되지 않음
  * 원인 및 해결
    * `[당일]`, `[1주]`.. 버튼의 경우, 함수는 구현이 되어있긴한데.. 인자전달이 잘못된 상태, `today`에 대한 분기도 Java단에 추가함.
    * [x] 상세조회 분기에 대한 구현
      * [x] `등록일로 조회`, `상세 조회`를 라디오버튼으로 토글할 필요가 없어져서 구역을 분리함.
      * [x] 상세 조회할 때는 HTML 테그의 기본 name속성으로 폼전송 되도록 수정



#### 기타

* [x] `BaseController`의 calcSearchPeriod 메서드는 유틸리티 성 메서드라 상속대신 유틸클래스틀 통해 사용하도록 하는게 나아보인다. > DateUtils로 이동

* [x] 이미지 다운로드가 일어날 때, Tomcat에서 경고

  ```
  경고 [http-nio-8090-exec-6] org.apache.coyote.http11.Http11Processor.prepareResponse 값이 [attachment; fileName=리액트.png]인 HTTP 응답 헤더 [Content-Disposition](이)가 유효하지 않은 값이므로 응답에서 제거되었습니다.
  	java.lang.IllegalArgumentException: code point [50,976]에 위치한 유니코드 문자 [리]은(는), 0에서 255까지의 허용 범위 바깥에 있으므로 인코딩될 수 없습니다.
    ...
  ```
  
  * Content-Disposition을 설정할 때..  다음 처럼 설정
  
    ```java
        response.setHeader(
            HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename*=UTF-8''" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    ```
  
    * [HTTP - Content-Disposition - HTTP 콘텐츠 처리 헤더는 브라우저가 응답 페이로드를 처리하는 방법을 정의하는 데 사용됩니다. (runebook.dev)](https://runebook.dev/ko/docs/http/headers/content-disposition)
    * 조금 해깔린 점이 있었는데.. URL인코딩을 하면 공백도 치환문자로 변경되서 따옴표로 묶을 일은 없을 것 같다.

* IntelliJ에서 MyBatis의 중첩 `<if>`를 정확하게 알아채지 못하는 것 같다.
  * 편집기 상에서만 구문 문제가 있다고 경고가 노출됨. 나중에 한번 이슈 올려보자..

---

## 2. `admin.member` 패키지 이하 수정

### 코드 스타일 변경

- [x] ViewName 인터셉터 제거와 ModelAndView를 Model로 전환

- [x] 읽기만하는 트랜젝션은 `readOnly = true` 옵션을 붙여보자!

  ```java
  @Transactional(readOnly = true)
  ```

  * 트랜젝션 전파 속성은 `Propagation.REQUIRED` 는 기본 값이니 일부러 명시하진 말자!

- [x] PrintWriter로 직접 응답을 String을 쓰는 부분은 `ResponseEntity<String>`으로 반환하자!

  ```java
  ...
  @ResponseBody
  public ResponseEntity<String> modifyMemberInfo( ... ) {
    ...
    return ResponseEntity.ok("mod_success");
  }
  ```

  

- [x] HttpServletRequest 로 파라미터 받는 부분은 아래와 같은 스타일로 바꾸자!

  ```java
  @RequestParam("member_id") String memberId
  ```

  

### ✨ 기능 문제

#### `관리자` > `회원관리` 의 목록 페이지

- [x] 검색 조건의 `[당일]`~`[4개월]`버튼이 정상 동작하지 않음

  * 원인: URL에 ContextPath 가 동적으로 설정되어있지 않음

- [x] 검색 조건의 상세조회의 `[조회]` 가 정상동작하지 않음

  * 원인: 쿼리는 조건절은 있긴한데.. view > Controller 로 의 파라미터 전달등의 처리가 안되있다.

    > ##### ✨기타
    >
    > view의 Select Box의 검색 조건에 ''전체''라는 항목이 있는데.. 이부분은 제거하는 것이 낫겠다.
    >
    > 검색 쿼리에 OR로 회원아이디, 이름, 휴대폰번호, 회원주소를 묶어서 쿼리를 할 수는 있겠지만 별 의미가 없어보인다. (성능에도 좋지 않을 것 같음. 😅)

- [x] 검색 조건 상세조회의 input form에서 엔터를 누르면 그대로 submit이 일어남

  * 원인: form에 input 테그 하나만 있는 상태라면 엔터누를때 submit이 일어날 수 있음

  * 해결:  해당 input에 엔터 누를떼 submit 이벤트 막고 검색 함수 실행

    ```javascript
    // jQuery
    $('input[name="t_search_word"]').keydown(function(event) {
        if (event.keyCode === 13) {
          event.preventDefault();
          fn_detail_search();
        }
      });
    ```

    ```javascript
    // ES6
    document.querySelector('input[name="t_search_word"]').addEventListener('keydown', event => {
        if (event.keyCode === 13) {
            event.preventDefault();
            fn_detail_search();
        }
    });
    ```

    

- [x] `관리자` > `회원관리` 에서 조회되는 회원이 없음

  * 문제 아님

    * 초기 접근은 오늘 부터 2개월 전 사이에 가입한 회원만 보여줌.

  * 페이징도 확인해야하니... PL/SQL코드로 `110`명 회원 가입 (2023년 8월 31일자로...)

    ```sql
    DECLARE
        i NUMBER := 1;
    BEGIN
        WHILE(i <= 110)
            LOOP
                INSERT INTO T_SHOPPING_MEMBER (MEMBER_ID, MEMBER_PW, MEMBER_NAME, MEMBER_GENDER, TEL1,
                                               TEL2, TEL3,
                                               HP1, HP2, HP3, SMSSTS_YN, EMAIL1, EMAIL2, EMAILSTS_YN,
                                               ZIPCODE,
                                               ROADADDRESS, JIBUNADDRESS, NAMUJIADDRESS, MEMBER_BIRTH_Y,
                                               MEMBER_BIRTH_M, MEMBER_BIRTH_D, MEMBER_BIRTH_GN,
                                               JOINDATE, DEL_YN)
                VALUES ('mem' || i, '1212', '회원' || i, '101', '02', '1111', '2222', '010', '2222',
                        '3333', 'Y', 'mem' || i,
                        'test.com', 'Y', '13547', '도로명주소', '지번주소', '나머지주소',
                        '2000', '5', '10', '2', TO_DATE('23/08/31', 'RR/MM/DD'), 'N');
                i := i + 1;
            END LOOP;
    END;
    COMMIT;
    
    -- 입력한 회원의 정리가 필요할 때는... admin과 lee 회원만 제외하고 삭제
    DELETE
      FROM T_SHOPPING_MEMBER
     WHERE NOT MEMBER_ID IN ('admin', 'lee');
    ```

- [ ] **✨ TODO: `관리자` > `회원 관리` 에서  회원이 100명을 넘을 때.. 페이지 네비게이션이 정상 동작하지 않음.**

  * 조회 결과가 110명일 때, 100 ~ 110명 범위의 회원을 조회할 수 없음, 11페이지 부터 동작하지 않음.
  * ✨ TODO: 페이징 네비게이션 관련 기능은 일괄로 정리해서 바꿔야겠다. 😈
    * 페이징 코드들이 난해한 부분이 좀 있음..😂

  

#### `관리자` > `회원관리` > `특정 회원의 상세 페이지`

- [x] 수정 가능한 항목에 대해 수정을 시도할 때... 에러 발생 얼럿이 노출됨
  - 원인
    - [x] 성별, 비밀번호 외의 항목은 ContextPath 가 동적으로 설정되지 않아 생겼던 문제.
    - [x] 비밀번호와 성별 바꿀 때 오류는 다른 문제..
      * 어드민 컨트롤러에 파라미터 전달 구현이 안되어있었다. 

- [x] 양력  / 음력 수정이 안됨.
  * 쿼리에 조건이 누락되었음.
- [x] 이메일 직접 입력 동작 수정
  * 회원 가입시 수정했던 식으로 바꿔야겠다.

- [x] 회원 탈퇴 버튼 눌렀을 때.. 404
  * 원인: URL에 ContextPath 가 동적으로 설정되어있지 않음



---

## 3. `admin.order` 패키지 이하 수정

### 코드 스타일 변경

* [x] `admin.member` 패키지에서 한 방식대로 변경



### ✨ 기능 문제

#### `관리자` > `주문관리` > `주문 목록 페이지`

* [ ] **TODO: 페이지 네비게이션 문제 - 이후 일괄로 바꾸자!**
* [x] 상세 조회할 때.. 조회버튼을 눌러 조회하면 404페이지
  * ContextPath 문제 같다.
* [x] 상세 조회 기능 문제 확인
  * 전체 조건은 제거하자..  
    * 키워드를 빈체로 조회하면 날짜기준으로만 전체 조회 되는 것으로 간주..
  * 회원 관리 상세 조회 수정했을 때 처럼 바꿔주면 되겠다.

- [x] 조회에서 날짜 조작하는 코드가 너무 복잡하다.. 일반적인 `input date`를 사용하고 `date-fns`의 사용법을 확인해서 그것을 최대한 활용하도록 바꾸는 것이 좋겠다.
  * [GitHub - date-fns/date-fns at v1.30.1](https://github.com/date-fns/date-fns/tree/v1.30.1)
    * 최신버전(2.30.x)은 일반적인 방법으로 JSP에서 사용할 수 없음.  😂 
    * 빌드 도구나 번들러를 써야한다는데.. 잘모르겠음.. 이전버전 써보니 잘 동작한다.



### 기타 사항

#### `관리자` > `주문관리` > `주문 목록 페이지`

- [x] 주문 목록에서 특정 주문을 클릭해서 주문 상세 페이지로 이동시 post 접근 대신 get이 낫겠다.
  * 조회성 페이지여서 GET 요청이 낫고, GET으로 바꾸면 관리자가 주문 상세에서 브라우저 새로고침을 하더라도 문제가 없어짐.

#### `관리자` > `주문관리` > `특정 주문 상세 페이지`

- [x] 이 페이지에서는 문제점은 보이지 않는데... 하단의 `쇼핑 계속하기` 버튼을 `주문 목록 페이지`로 돌아가기 버튼으로 바꾸는게 좋겠다.







---

## 중간 중간 의견들...

* ...



---

## 2차 테스트 / 수정 최종의견

* ...
