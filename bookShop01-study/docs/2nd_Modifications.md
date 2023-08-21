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

> 메뉴: `어드민 로그인` > `관리자` > `상품 관리`

- [ ] 





#### 기타

* [x] `BaseController`의 calcSearchPeriod 메서드는 유틸리티 성 메서드라 상속대신 유틸클래스틀 통해 사용하도록 하는게 나아보인다. > DateUtils로 이동

  





---

## 중간 중간 의견들...

* ...



---

## 2차 테스트 / 수정 최종의견

* ...
  
