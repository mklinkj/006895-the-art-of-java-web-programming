package com.bookshop01.common.base;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

/*
 * 공통 컨트롤러이긴한데, 상속하는 메서드들이 약간 유릴리티 유형이다.
 * temp 경로에 업로드를 하는 메서드가 있긴한데,
 * 이 부분은 사용처가 아래 컨트롤러 밖에없어서 그 컨트롤러로 이동시켰다.
 * - AdminGoodsController
 */
@Slf4j
@SpringJUnitWebConfig(
    locations = {
      "classpath:config/spring/root-context.xml",
      "classpath:config/spring/servlet-context.xml"
    })
class BaseControllerTests {

  @Autowired private WebApplicationContext context;

  private MockMvc mockMvc;

  @BeforeEach
  void beforeEach() {
    mockMvc = webAppContextSetup(context).build();
  }

  /*
    단지 아무 작업 없이 바로 뷰만 바로 리턴하는 공통 메서드 테스트
  */
  @Test
  void viewForm() throws Exception {
    mockMvc
        .perform(get("/admin/goods/addNewGoodsForm.do")) //
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("admin/goods/addNewGoodsForm"));
  }
}
