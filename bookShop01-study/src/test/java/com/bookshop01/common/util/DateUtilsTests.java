package com.bookshop01.common.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;
import org.junit.jupiter.api.Test;

class DateUtilsTests {

  @Test
  void toDate() {
    LocalDateTime localDateTime = LocalDateTime.of(2023, 8, 10, 1, 2, 3);
    Date date = DateUtils.toDate(localDateTime);

    // 대단 👍 처음엔 SimpleDateFormat 으로 문자열로 바꿔서 검증하려고 했는데, assertThat에 비교해주는 검증 메서드가 내장되어있다.👍👍👍
    assertThat(date)
        .hasYear(2023)
        .hasMonth(8)
        .hasDayOfMonth(10)
        .hasHourOfDay(1)
        .hasMinute(2)
        .hasSecond(3);
  }

  @Test
  void toLocalDate() {
    LocalDate result = DateUtils.toLocalDate("2023-08-10", "yyyy-MM-dd");

    assertThat(result) //
        .hasYear(2023)
        .hasMonth(Month.AUGUST) // 어? 얘는 왜 enum으로 받지? 😅
        .hasDayOfMonth(10);
  }
}
