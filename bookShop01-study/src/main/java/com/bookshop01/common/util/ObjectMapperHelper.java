package com.bookshop01.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/** ObjectMapper 헬퍼 : Object 매퍼의 싱글톤 관리 헬퍼 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectMapperHelper {
  private static final ObjectMapper MAPPER = ObjectMapperHolder.INSTANCE;

  public static ObjectMapper objectMapper() {
    return MAPPER;
  }

  private static class ObjectMapperHolder {
    private static final ObjectMapper INSTANCE;

    static {
      INSTANCE = new ObjectMapper().registerModule(new JavaTimeModule());
    }
  }
}
