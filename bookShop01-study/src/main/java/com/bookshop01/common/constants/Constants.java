package com.bookshop01.common.constants;

import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
  /** yyyy-MM-dd 날짜 포맷 */
  public static final DateTimeFormatter DATE_FORMAT_YYYY_MM_DD =
      DateTimeFormatter.ofPattern("yyyy-MM-dd");
}
