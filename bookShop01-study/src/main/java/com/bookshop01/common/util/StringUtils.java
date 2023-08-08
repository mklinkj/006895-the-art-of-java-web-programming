package com.bookshop01.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtils {

  public static boolean isNullOrEmpty(String string) {
    return (string == null) || string.isEmpty();
  }

  public static boolean isNullOrBlank(String string) {
    return (string == null) || string.isBlank();
  }
}
