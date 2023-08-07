package com.bookshop01.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
}
