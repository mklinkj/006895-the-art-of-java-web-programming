package com.spring.member.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@ContextConfiguration(
    locations = {
      "file:src/main/webapp/WEB-INF/action-servlet.xml",
      "classpath:config/action-mybatis.xml"
    })
@WebAppConfiguration
@Slf4j
public class MemberControllerImplTests {

  @Autowired private WebApplicationContext context;

  private MockMvc mockMvc;

  @Before
  public void beforeEach() {
    mockMvc = webAppContextSetup(context).build();
  }

  @Transactional
  @Rollback
  @Test
  public void testAddMemberDo_01() throws Exception {
    mockMvc
        .perform(
            post("/member/addMember.do")
                .param("id", "mklinkj")
                .param("pwd", "4321")
                .param("name", "정션링크")
                .param("email", "mklinkj@github.com"))
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/member/listMembers.do"));
  }
}
