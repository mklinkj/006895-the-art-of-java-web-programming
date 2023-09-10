package com.bookshop01.admin.common.pagination;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedHashMap;
import org.junit.jupiter.api.Test;

class PageUtilsTests {

  @Test
  void additionalParameters() {
    LinkedHashMap<String, Object> paramMap = new LinkedHashMap<>();
    paramMap.put("beginDate", "2023-09-01");
    paramMap.put("endDate", "2023-09-30");
    paramMap.put("search_type", "member_id");
    paramMap.put("search_keyword", "user01");

    String paramString = PageUtils.additionalParameters(paramMap);

    assertThat(paramString)
        .isEqualTo(
            "beginDate=2023-09-01&endDate=2023-09-30&search_type=member_id&search_keyword=user01");

    paramString = PageUtils.additionalParameters(new LinkedHashMap<>());

    assertThat(paramString).isEqualTo("");
  }
}
