package com.bookshop01.common.util;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class ObjectMapperHelperTests {

  private final ObjectMapper objectMapper = ObjectMapperHelper.objectMapper();
  /*
   Java 8 날짜를 어떦게 직렬화 하는지 정도만 확인해 보자.
  */
  @Test
  void objectMapper() throws JsonProcessingException {
    LocalDateTime dateTime = LocalDateTime.of(2023, 8, 9, 1, 2, 3);

    String result = objectMapper.writeValueAsString(Map.of("dateTime", dateTime));

    LOGGER.info(result);

    // 쌍따옴표 이스케이프하기 귀찮아서 """ 썻는데 잘 인식된다. """ 다음의 첫 개행과 공백은 문자열에 포함되지 않는구나 👍.
    assertThat(result).isEqualTo("""
    {"dateTime":[2023,8,9,1,2,3]}""");
  }
}
