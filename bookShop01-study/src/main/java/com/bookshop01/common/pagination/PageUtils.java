package com.bookshop01.common.pagination;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.StringJoiner;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageUtils {
  private static final DateTimeFormatter DATE_FORMAT_YYYY_MM_DD =
      DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private static final DateTimeFormatter DATE_FORMAT_YYYY_MM_DD_HH_MM_SS =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public static String additionalParameters(LinkedHashMap<String, Object> searchParams) {
    StringJoiner joiner = new StringJoiner("&");
    searchParams.forEach(
        (key, value) ->
            joiner.add(
                String.format(
                    "%s=%s", key, URLEncoder.encode(getString(value), StandardCharsets.UTF_8))));
    return joiner.toString();
  }

  private static String getString(Object value) {
    if (value instanceof String s) {
      return s;
    } else if (value instanceof Integer || value instanceof Long) {
      return value.toString();
    } else if (value instanceof LocalDate localDate) {
      return localDate.format(DATE_FORMAT_YYYY_MM_DD);
    } else if (value instanceof LocalDateTime localDateTime) {
      return localDateTime.format(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
    } else {
      throw new IllegalArgumentException("String 변환을 지원 하지 않는 타입: %s".formatted(value.getClass()));
    }
  }
}
