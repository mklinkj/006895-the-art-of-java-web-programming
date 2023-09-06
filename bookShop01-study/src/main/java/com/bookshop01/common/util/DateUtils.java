package com.bookshop01.common.util;

import static com.bookshop01.common.constants.Constants.DATE_FORMAT_YYYY_MM_DD;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/** 날짜 타입 변환 유틸리티 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {
  public static Date toDate(LocalDateTime localDateTime) {
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }

  public static LocalDate toLocalDate(String dateString, String pattern) {
    return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(pattern));
  }

  public static String calcSearchPeriod(String fixedSearchPeriod) {
    return calcSearchPeriod(fixedSearchPeriod, null);
  }

  public static String calcSearchPeriod(String fixedSearchPeriod, String endDateStr) {
    Function<String, LocalDate> endDateFunc =
        (es) -> (es == null ? LocalDate.now() : LocalDate.parse(es, DATE_FORMAT_YYYY_MM_DD));
    LocalDate endDate = endDateFunc.apply(endDateStr);
    LocalDate beginDate;

    if (fixedSearchPeriod == null) {
      // switch의 파라미터 값으로 null을 허용하지 않기 때문에 null 이 들어올 수 있다면 별도 처리해야한다.
      beginDate = endDate.minusMonths(4);
    } else {
      beginDate =
          switch (fixedSearchPeriod) {
            case "today" -> endDate;
            case "one_week" -> endDate.minusWeeks(1);
            case "two_week" -> endDate.minusWeeks(2);
            case "one_month" -> endDate.minusMonths(1);
            case "two_month" -> endDate.minusMonths(2);
            case "three_month" -> endDate.minusMonths(3);
            case "four_month" -> endDate.minusMonths(4);
            default -> throw new IllegalStateException(
                "잘못된 기간 검색 타입: %s".formatted(fixedSearchPeriod));
          };
    }
    return beginDate.format(DATE_FORMAT_YYYY_MM_DD) + "," + endDate.format(DATE_FORMAT_YYYY_MM_DD);
  }
}
