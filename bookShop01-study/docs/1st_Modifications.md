# 1차 이터레이션 수정 / 개선 사항

> 보이는대로 수정 및 개선해보자..
>
> 한 이슈마다 커밋을 한번씩 하는게 나을 것 같다. 몰아서 하는 것보단 그게 알아보기 편할 듯..👍



## 1. Controller 인터페이스는 제거하고 구현 클래스로만 사용하자

Service 또는 Repository라면 필요에 따라 인터페이스와 구현체로 나누면 좋을 때가 있긴한데, 몇몇 Controller에 대해 인터페이스가 만들어져있다.

* **TODO:**
  * [x] 컨트롤러에 대한 인터페이스 클래스들은 제거하고, 컨트롤러 구현체 이름의 Impl 접미사를 제거한다.

그런데 이부분은 뭔가 모델링 툴로 먼저 구조를 잡아서 존재했을 수도 있을 것 같긴한데, 이부분은 취향 차이일 수도 있음. 😅



---

## 2. 동시성 문제를 발생시키는 컨트롤러 필드 변수 사용 코드 정리

```java
@Component("cartVO")
```

무의미하게 VO객체가 싱글톤 빈으로 선언된다. 사용하지도 않게되지만 ...😅

그런데 왜 빈으로 선언되어있는지 이유를 이후 알게되었는데.. 싱글톤 빈으로 만든 VO 객체를 컨트롤러 필드 변수로 사용하고 있었다. 

**CartController 가 정말 이상하게 되어있는데..**

* 몇몇 컨트롤러에서 싱글톤으로 만들어진 CartVO, MemberVO를 Autowired로 받음

  ```java
  @Autowired private CartVO cartVO;
  @Autowired private MemberVO memberVO;
  ```

  위의 필드를 컨트롤러 메서드들에서 사용하고 있음.

  👺 이러면 유저마다 다른 메서드 들을 호출하는 상황일 때는, 동시성 문제가 반드시 생길 텐데, 관련 부분은 메서드내에서 새로 객체 생성하도록 수정했다.

* MemberController에도 DB에서 불러온 MemberVO 객체를 컨트롤러 필드에 저장하는 코드가 있음. 😅

* OrderController, MyPageController에는 세션에서 불러온 값을 필드에 저장 😅

  

 **TODO:**

* [x] VO에 붙은 `@Component` 어노테이션 제거
* [x] Controller에서 VO객체를 필드로 사용하는 관련 부분 정리



---

## 테스트가 쉽도록 스프링 컨텍스트 XML 설정파일들은 resources 이하로 옮김

* JUnit으로 실행할 때 편의를 위해서, 스프링 컨텍스트 설정 XML파일들을 `src/main/resources` 이하로 옮긴다.

**TODO:**

- [x] mybatis-context.xml 을 root-context.xml 으로 이름을 바꿔서 src/main/resources/ 이하 경로로 이동 
  - servlet-context.xml 도 resources로 옮기자!
- [x] servlet-context.xml에서 일괄적으로 컴포넌트 스캔을 하는데, 
  * servlet-context.xml에서는 `@Controller` 만 스캔
  * root-context.xml에서는 `@Controller` 만 제외하고 스캔
- [x] AdminGoodsDAO에 대한 단순 테스트 메서드 추가하여 실행확인



---

## AdminGoodsDAOImpl 관련 클래스 정리

**TODO:** 

* [x] DataAccessException 를 던지는 선언은 모두 지운다.
  * 어차피 런타임 예외라 일부러 메서드 선언에 명시할 필요가 없음.
* [x] 일단은 제네릭 파라미터를 모두 추가하자!
  * 어떤 타입인지 명확한 것은 기입하도록하고, 알수없는 것은 ?로 기입.
  * 🤔 메서드 중에 Map을 파라미터 인자로 전달하는 부분들이 있는데, 이미 GoodsVO가 있기 때문에 그걸 활용하면 될 것 같은데... 이 부분은 점진적으로 바꾸자.
  
* [x] AdminGoodsDAOImpl에 있는 메서드들의 간단한 테스트 코드는 전부 추가해보자.



---

## AdminMemberDAOImpl 관련 클래스 정리

### TODO: 

- [x] admin_member.xml 정리
  - 이 파일도 테이블 명이 갱신되지 않은 부분이 있다. 😓, 진짜 IDE의 자동 감지 도움 없으면 찾기 힘들것 같음...
  - mybatis 조건 검사할 때.. null을 먼저 검사하고 and로 이어서 ''를 검사하는게 맞을 것 같은데... ''를 먼저 검사하는 부분이 있음.

- [x] AdminMemberDAOImpl 메서드의 테스트 코드 작성
- [x] section, chapter를 혼용되게 쓴부분이 있음. 그래서 회원목록을 볼 수 없음. section 기준으로 맞춤.
- [x] 관련 컨트롤러, 서비스, 기타 등등 정리



---

## AdminOrderDAOImpl 관련 클래스 정리

### TODO: 

- [x] AdminOrderDAOImpl 테스트 코드 작성

  - 기타 관련 상위 코드 수정

- [x] 관리자의 주문 조회 목록 페이지에서 배송수정을 수행하면 데이터는 잘 바뀌지만 최종 리다이렉트가 잘못된 주소로 가는 문제 수정 

  * 확인해보니 JSP 파일의 리다이렉트 코드에 슬래시가 하나 더 포함되어있었다.

    ```js
    location.href = "${contextPath}//admin/order/adminOrderMain.do";
    ```

- [x] 미사용 css제거 및 정리

  * 부분 주제와는 관련은 없으나, 콘솔에 404오류 뜨는게 어지러워서... 수정하도록 하자!

    ```
    smartphone.css
    tablet.css  
    ```

    위의 css파일도 프로젝트에 존재하지 않음 > 제거함

  * `webapp/resources/imgs`에  나눔고딕 폰트가 있어야했으나... 없다 주석처리했다.

    ```css
    @font-face {
    	font-family: NanumGothic;
    	src: local("?");
    	/*
    		TODO: 프로젝트에 나눔고딕 폰트가 포함되어있지는 않다.
    	    시스템에 설치하고 있어서 일부로 프로젝트에 포함할 필요는 없을 것 같음.
    	*/
    	/*src: local("?"), url(../imgs/nanum.woff) format("woff"), url(../imgs/nanum.ttf) format("truetype");*/
    }
    ```

    그런데 나는 시스템에 나눔고딕을 설치해두고 있어서, 폰트가 나눔고딕으로 정상 출력되고 있다.

- [ ] 포함되지 않은 이미지들... (그냥 두자.. 수정없음)

  ```
  GET http://localhost:8090/resources/imgs/body_back.gif 404 (Not Found)
  main.css:1     GET http://localhost:8090/resources/imgs/vline_grey.gif 404 (Not Found)
  main.css:1     GET http://localhost:8090/resources/imgs/nav_back.jpg 404 (Not Found)
  main.css:1     GET http://localhost:8090/resources/imgs/sticky_line.gif 404 (Not Found)
  main.css:1     GET http://localhost:8090/resources/imgs/notice_h2.gif 404 (Not Found)
  main.css:1     GET http://localhost:8090/resources/imgs/notice_li.gif 404 (Not Found)
  main.css:1     GET http://localhost:8090/resources/imgs/foot_line.gif 404 (Not Found)
  main.css:1     GET http://localhost:8090/resources/imgs/foo_back.gif 404 (Not Found)
  favicon.ico:1     GET http://localhost:8090/favicon.ico 404 (Not Found)
  ```

  `favicon.ico`는 그냥 넣으면 되긴하는데..  프로젝트 자체에 imgs라는 디렉토리가 `webapp/resources` 이하에 없다.

  이 부분의 404 오류는 그냥 냅둬야겠다.

---

## 상품(goods) 패키지 이하 코드 테스트 / 수정

### TODO:

- [x] 좌측 메뉴목록의 `IT/인터넷` 클릭시 /goods/goodsList.do 로 이동하려하는데, 컨트롤러 메서드 및 JSP 페이지가 없다. 이부분은 링크를 제거해두자. 

  * side.jsp
    ```jsp
    <li><a href="${contextPath}/goods/goodsList.do">IT/인터넷</a></li>
    ```

- [x] 상품 아이디가 DB타입이 Oracle Number인데.. Controller > Service > Repository 까지 String으로 줄줄이 쓰는 부분이 있음.

  * ID가 분명히 숫자만 들어가야하니, DB타입에 맞게 쓰는게 나을 것 같은데...
  * 임시로 컨트롤러에서 서비스 호출하는 부분에만 Integer로 변환하는 코드를 넣어주자, 컨트롤러는 좀 나중에 변경하고...

- [x] GoodsDAOImpl 테스트 코드 작성

  

---

## 주문(order) 패키지 이하 코드 테스트 / 수정

### TODO:

- [x] 대략적인 코드 정리

- [x] OrderDAOImpl 테스트 코드 작성

  >  listMyOrderGoods()의 경우 오늘 주문한 내용을 쿼리하는데, 조회기준시간이 SYSDATE로 쿼리에 들어가 있다.
  >
  > 이러면 테스트 하기가 어렵긴한데... 기준시간을 잠시 바꿔서 하게하려면 Java 코드에서 기준시간을 전달해야함.
  >
  > * LocalDateTime.now()을 쿼리로 전달하게 변경
  > * Mockito로 테스트 실행시점에 현재 시간을 다른 변경해서 실행.

  * removeGoodsFromCart가 리스트를 받는 것이 있고, 단일 주문을 받는 것이 있는데, 단일 주문을 받는 것만 남기고 반복은 서비스에서 처리하자!

    

---

## 장바구니(cart) 패키지 이하 코드 테스트 / 수정

### TODO: 

- [x] 대략적인 코드 정리

- [x] CartDAOImpl 테스트 코드 작성

  - [x] selectCountInCart의 결과를 바로 boolean으로 받을 수 있게 수정해보자!  > 그냥 count 받고 DAO에서 boolean 반환하게 최종 수정.

    * resultType을 `boolean`으로 하고 `true`일 경우 `1`, `false`일 경우 `0`을 반환하도록 맞춰주면 된다.
    * https://mybatis.org/mybatis-3/ko/configuration.html#typealiases
    * `SELECT DECODE(COUNT(*), 0, 0, 1)`

    그런데 나중에 생각해보면.. 그냥 카운트 결과를 받아서 DAO에서 `return count > 0;` 이렇게 반환해버리면 될 것 같은데... 이렇게 하자.. 😅

  - [x] 그런데 파라미터가 단 하나인데, 모델에 담아서 전달하는 부분은 하나로 하는게 나을까?
    * `DAO -> Mapper` 수준에서는 미리 그렇게 바꿔보는 것도 괜찮을 것 같다.

- [x] 로그인 하지 않은 상태에서 장바구니 눌렀을 때. 오류 얼럿 뜨는 현상?

  * 로그인 된 상태에서는 동작에 이상없음.
  * [x] 세션에 값이 없으면, 로그인 페이지로 리다이렉트 해야하는 처리가 필요할 것 같다.

- [x] 기타 변경

  `/`  URL 로 접근할 때... `/main/main.do`로 리다이렉트 되게하자~



---

## 회원(member) 패키지 이하 코드 테스트 / 수정

- [x] 대략적인 코드 정리

- [x] MemberDAOImpl 테스트 코드 작성

- [x] 이메일 도메인 정보 끝에 `,non` 이 붙는 이유는?

  * 이메일 도메인 부분을 저장하는 input text와, 이메일 도메인을 선택하는 select box의 name 속성이 `email2`로 동일하게 되어있다. 

  * 일단 단순하게 수정했다. (가입할 때만 사용하는 JSP: `memberForm.jsp`)

    * select box에 있는 name 속성은 제거하고, input text의 name만 유지한다.

    * 해당 select가 바뀔 때..  직접 입력이 아니면 그 값을 자동으로 input text에 넣고 input text의 readonly를 true 설정한다.

      ```js
        const $emailDomainObj = $('form').find('input[name=email2]')
        $('.email_domain_select_box').change(function () {
          const domain = $(this).val();
          if (domain !== 'non') {
            $emailDomainObj.val(domain);
            $emailDomainObj.attr('readonly', true);
          } else {
            $emailDomainObj.val('');
            $emailDomainObj.removeAttr('readonly');
          }
        });
      ```

      ```js
      // jQuery 사용하지 않는다면...
      const emailDomainObj = document.querySelector('form input[name=email2]');
        document.querySelector('.email_domain_select_box').addEventListener('change', function () {
          const domain = this.value;
          if (domain !== 'non') {
            emailDomainObj.value = domain;
            emailDomainObj.setAttribute('readonly', true);
          } else {
            emailDomainObj.value = '';
            emailDomainObj.removeAttribute('readonly');
          }
        });
      ```

      생각나는데로 막적어서... 좀 잘모르겠다.

- [x] 이메일, 핸드폰 수신여부 동작이 일관적이지 않다. Y, N 코드를 사용하려한 것 같은데... 
  * 오류가 나므로 기본 상태가 반드시 수신을 한 상태로 회원 가입을 해야함 😅
  * 선택을 안하면 `smssts_yn=null`,  `emailsts_yn=null`  이 속성이 null 인채로 DB 저장을 하려해서 오류가 생김.
    * 선택을 안한 상태를 'N' 으로 처리되도록 해줘야할 것 같은데...
    * 이거는 DB의 DEFAULT 처리 값을 'N'으로 테이블 정의를 수정할까 하다가... MemberServiceImpl의 addMember 메서드에 조건을 넣었음.
      - [ ] 좀 더 개선 하는 것은 좀 나중에 하자....😅



---

## 마이 페이지 (mypage) 패키지 이하 코드 테스트 / 수정

- [x] 대략적인 코드 정리

- [x] MyPageDAOImpl 테스트 코드 작성

- [x] 이메일 수신여부, SMS 수신 여부에 대해... 수정을 할때는 true, false로 처리됨.

  ```js
  value_smssts_yn = smssts_yn.checked;
  ...
  value_emailsts_yn = emailsts_yn.checked;
  ```
  위처럼 그냥 checked 값을 그냥 넣는게 아니고, Y, N으로 변환되게 전달되도록 변경
  ```js
  value_smssts_yn = (smssts_yn.checked) ? 'Y' : 'N';
  ...
  value_emailsts_yn = (emailsts_yn.checked) ? 'Y' : 'N';
  ```

- [x] 회원정보 관리 페이지에 들어왔을 때...  이메일 수신여부, SMS 수신 여부가 DB 저장된 기준으로 표시되지 않음.

  * JSP 코드를 확인하니 이부분은 true / false 기준으로 검사하고 있음 ...😓 Y, N 기준으로 처리되도록 수정.

### 기타 사항

> 내 상세정보 옆에 주제마다 수정하기가 붙어있어서, 어떻게 처리되는지 확인해보았다.
>
> 1. 자바스크립트에서 컨트롤러에 데이터를 폼의 데이터를 선택적으로 보냄
> 2. 컨트롤러에서도 attribute 값을 체크해서 선택적으로 업데이트함.



---

## 메인 (main) 패키지 이하 코드 테스트 / 수정

> DAO가 따로 없고 상품 서비스를 주입받아 사용함.

- [x] 대략 적인 코드 정리
  - [x] ModelAndView 대신에 Model 로 사용
  - [x] void 메서드를 사용하면 딱히 ViewName을 계산하는 인터셉터를 사용할 필요가없다.
    * 요청이 `/main/main.do` 일 때 void 메서드로 반환 값을 안주면 뷰이름을 `main/main` 으로 간주한다. 그냥 이 처리에 맞게 tiles 템플릿의 경로도 맞춰서 바꿔주면 쉽게 해결된다.

- [x] MainController 테스트 코드 작성

### 해깔리는 부분? ( 😂 다음 이터레이션으로 넘겨야할 것 같다. 컨트롤러 테스트 확실히 하면서 바꾸는게 낫겠다. )

- [x] side_menu 분기를 위해서 세션에다가 user 또는 admin_user를 저장하는데...  그냥 model에 addAttribute 해도 문제가 없을까?.
  * 모델에다 넣으면 request 에다 넣는거고..
  * 세션에다 넣으면 session인데.. 우선순위는 request 가 더 앞이긴한데... 
  * URL Path기반으로 사이드 메뉴 분기를 설정해주는게 나을 것 같기도한데...
  
  **이 문제 tiles 설정을 수정하여 쉽게 해결했다. 👍**
  
  * side 영역을 nav와 공통 영역으로 나누고, nav를 tiles 설정에 따라 어드민, 마이페이지, 기본(메인)으로 구분되게 하면 side_menu를 세션으로 관리할 필요가 없어졌다.
  
    > ✨ 내가 중요하지 않은 정보를 세션에서 제거하고 싶은 이유는...
    >
    > 보통 운영서버에서는 웹서버가 1대가 아니고 여러대로 운영하는데, 세션을 사용하게되면 여러 웹서버간 세션 공유를 어떻게 할지... 고민이 생기게된다...😂
  
    - [x] side_menu 제어 값을 세션에서 제거
  
      



### tiles 경고 로그 수정

```
WARN  org.apache.tiles.request.locale.PostfixedApplicationResource - No supported matching language for locale "admin_goods". Using file:/{레파지토리 루트}/bookShop01-study/target/bookshop-study-1.0.0-BUILD-SNAPSHOT/WEB-INF/classes/tiles/tiles_admin_goods.xml as a non-localized resource path. see TILES-571
```

* tiles XML 설정 파일 이름에 `_`이 포함될 경우 마지막 밑줄 다음의 문자를 locale 처럼 인식하려는 동작을 보인다.
* [x] tiles XML 파일 명에 포함된 밑줄을 다른 문자로 바꿔주면 간단하게 해결됨.
  * `tiles_admin_goods.xml` 를  `tiles-admin-goods.xml`으로 파일 이름 변경





---

## 공통(common) 패키지 이하 코드 테스트 / 수정

- [x] 대략적인 코드 정리

### BaseController

* viewForm()
  * [x] 제거
    * `/*.do` 로 매핑 되어있고, 내용은 그냥 ViewName 얻어서 반환하는 내용인데, 컨트롤러에서 void 메서드 사용한 것과 별 차이가 없어보인다. 제거하자..

* CURR_IMAGE_REPO_PATH
  * [x] 경로값 프로퍼티 파일에 저장해두고 로드하는 방식으로 수정

- [x] 테스트 코드 작성
  - [x] upload의 경우는 사용처가 AdminGoodsController 밖에 없어서 거기로 옮겨둠.
    - [ ] AdminGoodsController 클래스의 내을 보니 엄청나게 복잡한데... 다음 이터레이션에서 정리하면서 서비스 단으로 내려야할 것 같다.


### FileDownloadController

- [x] 테스트 코드 작성

  

### ViewNameInterceptor

* [ ] 뷰이름을 얻는 인터셉터는 컨트롤러 들이 정리되고 테스트가 추가되면 완전히 제거할 예정.

### LoggingAdvice

* 특별히 고칠 부분은 없다. 로그만 출력하는 어드바이스라서 테스트도 따로 안만들어도 될 것 같다.





---

## 중간 중간 의견들...

* 이 작업들을... 질질 끌지말고 해야할텐데... 😂
* 이게 완료 되면, 다른 DDD 예제의 상점 프로젝트도 이해가 잘 될듯 하다.



---

## 1차 테스트 / 수정 최종의견

* 정리를 많이 하면서 보았을 때, 잘못된 부분도 보이고 코드가 꽤 어지러웠다..😓
  * VO를 컴포넌트 빈으로 설정하고 컨트롤러의 맴버 필드로 사용한 내용은 정말 고쳐져야할 듯하다. 👺

* **2차 테스트 / 수정**부터는 천천히 하도록하고, 서비스하고 컨트롤러 위주로 테스트를 추가해가며 수정해보자.
