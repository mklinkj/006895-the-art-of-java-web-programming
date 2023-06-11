package com.spring.member.controller;

import com.spring.member.vo.MemberVO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public interface MemberController {
  ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response)
      throws Exception;

  ModelAndView addMember(
      @ModelAttribute("info") MemberVO memberVO,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception;

  ModelAndView removeMember(
      @RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response)
      throws Exception;
}
