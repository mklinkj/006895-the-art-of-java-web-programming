package com.bookshop01.common.base;

import static com.bookshop01.common.constants.Constants.DATE_FORMAT_YYYY_MM_DD;

import java.time.LocalDate;

public abstract class BaseController {
  protected String calcSearchPeriod(String fixedSearchPeriod) {
    LocalDate endDate = LocalDate.now();
    LocalDate beginDate;

    if (fixedSearchPeriod == null) {
      // switch의 파라미터 값으로 null을 허용하지 않기 때문에 null 이 들어올 수 있다면 별도 처리해야한다.
      beginDate = endDate.minusMonths(4);
    } else {
      beginDate =
          switch (fixedSearchPeriod) {
            case "one_week" -> endDate.minusWeeks(1);
            case "two_week" -> endDate.minusWeeks(2);
            case "one_month" -> endDate.minusMonths(1);
            case "two_month" -> endDate.minusMonths(2);
            case "three_month" -> endDate.minusMonths(3);
            case "four_month" -> endDate.minusMonths(4);
            default -> throw new IllegalStateException("잘못된 기간 검색 타입: " + fixedSearchPeriod);
          };
    }
    return beginDate.format(DATE_FORMAT_YYYY_MM_DD) + "," + endDate.format(DATE_FORMAT_YYYY_MM_DD);
  }
}
