# 수정 / 개선 사항

> 보이는대로 수정 및 개선해보자..



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

* 싱글톤으로 만들어진 CartVO, MemberVO를 Autowired로 받음

  ```java
  @Autowired private CartVO cartVO;
  @Autowired private MemberVO memberVO;
  ```

  위의 필드를 컨트롤러 메서드들에서 사용하고 있음.

  👺 이러면 유저마다 다른 메서들을 호출할 때, 동시성 문제가 생길 텐데, 관련 부분은 메서드내에서 새로 객체 생성하도록 수정했다.

* MemberController에도 DB에서 불러온 MemberVO 객체를 컨트롤러 필드에 저장하는 코드가 있음. 😅

* OrderController, MyPageController에는 세션에서 불러온 값을 필드에 저장 😅

  

 **TODO:**

* [x] VO에 붙은 `@Component` 어노테이션 제거
* [x] Controller에서 VO객체를 필드로 사용하는 관련 부분 정리

