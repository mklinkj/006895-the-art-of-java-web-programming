# 수정 / 개선 사항

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







---

## 장바구니(cart) 패키지 이하 코드 테스트 / 수정







---

## 회원(member) 패키지 이하 코드 테스트 / 수정







---

## 마이 페이지 (mypage) 패키지 이하 코드 테스트 / 수정







---

## 메인 (main) 패키지 이하 코드 테스트 / 수정







---

## 공통(common) 패키지 이하 코드 테스트 / 수정















---

## 중간중간 의견들...

* 이 작업들을... 질질 끌지말고 해야할텐데... 😂
* 이게 완료 되면, 다른 DDD 예제의 상점 프로젝트도 이해가 잘 될듯 하다.
