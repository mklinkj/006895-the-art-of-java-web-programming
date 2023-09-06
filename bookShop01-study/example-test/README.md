# 예제 테스트 파일

> 자바스크립트 라이브러리 동작 테스트먼저 해보자..
>
> 필요한 기능이 있는지 볼 필요가 있음.
>



## 날짜 처리

* [**moment.js**](https://github.com/moment/moment/) 
  * [monent-js-test.html](datetime/moment-js-test.html)
> moment는 최신 버전도 브라우저에서 moment 객체 생성해서 잘 쓸 수 있음.



* [**date-fns**](https://github.com/date-fns/date-fns)

  * [date-fns-test.html](datetime/date-fns-test.html)

  > 2.0.0 버전부터 브라우저에서 사용하려면, 빌드 도구나 번들러(예: Webpack, Rollup, Parcel 등)를 사용하여 ES6 모듈을 브라우저에서 실행할 수 있는 형식으로 변환해야 함.
  >
  > 결국 이전 버전인 1.30.1 을 사용해야한다.
  >
  > 
  >
  > #### 실제 코드에서 사용
  >
  > 날짜 계산하는 함수에 넣어봤는데.. 확실히 편하다..
  >
  > ```html
  > <script src="https://cdnjs.cloudflare.com/ajax/libs/date-fns/1.30.1/date_fns.min.js"></script>
  > ...
  > <script>
  > ...
  > function calcPeriod(search_period, baseDate) {
  >       const endDate = ((baseDate) => {
  >         if (baseDate) {
  >           return new Date(baseDate);
  >         } else {
  >           return new Date();
  >         }
  >       })(baseDate);
  > 
  >       let beginDate;
  >       if (search_period == 'today') {
  >         beginDate = endDate;
  >       } else if (search_period == 'one_week') {
  >         beginDate = dateFns.subWeeks(endDate, 1);
  >       } else if (search_period == 'two_week') {
  >         beginDate = dateFns.subWeeks(endDate, 2);
  >       } else if (search_period == 'one_month') {
  >         beginDate = dateFns.subMonths(endDate, 1);
  >       } else if (search_period == 'two_month') {
  >         beginDate = dateFns.subMonths(endDate, 2);
  >       } else if (search_period == 'three_month') {
  >         beginDate = dateFns.subMonths(endDate, 3);
  >       } else if (search_period == 'four_month') {
  >         beginDate = dateFns.subMonths(endDate, 4);
  >       }
  >       return {beginDate: dateFns.format(beginDate, 'YYYY-MM-DD'), endDate: dateFns.format(endDate, 'YYYY-MM-DD')};
  >     }
  > ...
  > </script>
  > ```
  >
  > 날짜 계산하는 부분이 너무 복잡했는데, 단순해졌다. 😀👍
  
  
  
  