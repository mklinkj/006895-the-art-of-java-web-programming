package com.bookshop01.member.controller;

import com.bookshop01.member.vo.MemberVO;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public interface MemberController {
  ModelAndView login(
      @RequestParam Map<String, String> loginMap,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception;

  ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception;

  ResponseEntity addMember(
      @ModelAttribute("member") MemberVO member,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception;

  ResponseEntity overlapped(
      @RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response)
      throws Exception;
}
