/**
 * 페이지에 직접 접근해서 영향을 미치지 않고, 그저, f(x)=y 같은 순수한 함수들은 여기에 모아두자!
 */

/**
 * 검색 기간 계산 (date-fns 라이브러리가 먼저 선언되야한다.)
 *
 * @param search_period 검색 기간
 * @param baseDate  기준 날짜 yyyy-MM-dd
 * @returns {{beginDate: *, endDate: *}}
 */
function calcPeriod(search_period, baseDate) {
  const endDate = ((baseDate) => {
    if (baseDate) {
      return new Date(baseDate);
    } else {
      return new Date();
    }
  })(baseDate);

  let beginDate;
  if (search_period === 'today') {
    beginDate = endDate;
  } else if (search_period === 'one_week') {
    beginDate = dateFns.subWeeks(endDate, 1);
  } else if (search_period === 'two_week') {
    beginDate = dateFns.subWeeks(endDate, 2);
  } else if (search_period === 'one_month') {
    beginDate = dateFns.subMonths(endDate, 1);
  } else if (search_period === 'two_month') {
    beginDate = dateFns.subMonths(endDate, 2);
  } else if (search_period === 'three_month') {
    beginDate = dateFns.subMonths(endDate, 3);
  } else if (search_period === 'four_month') {
    beginDate = dateFns.subMonths(endDate, 4);
  }
  return {
    beginDate: dateFns.format(beginDate, 'YYYY-MM-DD'),
    endDate: dateFns.format(endDate, 'YYYY-MM-DD')
  };
}