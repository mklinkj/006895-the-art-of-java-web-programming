package com.bookshop01.main;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitWebConfig(
    locations = {
      "classpath:config/spring/root-context.xml",
      "classpath:config/spring/servlet-context.xml"
    })
class MainControllerTests {
  @Autowired private WebApplicationContext context;

  private MockMvc mockMvc;

  @BeforeEach
  public void beforeEach() {
    mockMvc = webAppContextSetup(context).build();
  }

  @Test
  void testMain() throws Exception {
    MvcResult result =
        mockMvc
            .perform(get("/main/main.do"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("goodsMap"))
            .andExpect(view().name("main/main"))
            .andReturn();

    HttpSession session = result.getRequest().getSession();

    assertThat(session).isNotNull();

    String sideMenu = (String) session.getAttribute("side_menu");
    assertThat(sideMenu).isEqualTo("user");
  }
}
