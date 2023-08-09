package com.bookshop01.common.base;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mklinkj.test.support.FixedDateTestHelper.changeNowLocalDate;

import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/*
 * 공통 컨트롤러이긴한데, 상속하는 메서드들이 약간 유릴리티 유형이다.
 * temp 경로에 업로드를 하는 메서드가 있긴한데,
 * 이 부분은 사용처가 아래 컨트롤러 밖에없어서 그 컨트롤러로 이동시켰다.
 * - AdminGoodsController
 */
@Slf4j
class BaseControllerTests {
  private final BaseController baseController = new BaseController() {};

  /** 오늘 기준으로 검색 기준 일자 문자열을 반환 */
  @Test
  void calcSearchPeriod() {
    changeNowLocalDate(
        LocalDate.of(2023, 8, 9),
        () -> {
          String result = baseController.calcSearchPeriod(null);
          assertThat(result).isEqualTo("2023-04-09,2023-08-09");

          result = baseController.calcSearchPeriod("one_week");
          assertThat(result).isEqualTo("2023-08-02,2023-08-09");

          result = baseController.calcSearchPeriod("two_week");
          assertThat(result).isEqualTo("2023-07-26,2023-08-09");

          result = baseController.calcSearchPeriod("one_month");
          assertThat(result).isEqualTo("2023-07-09,2023-08-09");

          result = baseController.calcSearchPeriod("two_month");
          assertThat(result).isEqualTo("2023-06-09,2023-08-09");

          result = baseController.calcSearchPeriod("three_month");
          assertThat(result).isEqualTo("2023-05-09,2023-08-09");

          result = baseController.calcSearchPeriod("four_month");
          assertThat(result).isEqualTo("2023-04-09,2023-08-09");
        });
  }
}
