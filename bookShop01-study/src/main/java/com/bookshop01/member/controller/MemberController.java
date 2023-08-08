package com.bookshop01.member.controller;

import com.bookshop01.common.base.BaseController;
import com.bookshop01.member.service.MemberService;
import com.bookshop01.member.vo.MemberVO;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/member")
public class MemberController extends BaseController {
  private final MemberService memberService;

  @RequestMapping(value = "/login.do", method = RequestMethod.POST)
  public ModelAndView login(
      @RequestParam Map<String, String> loginMap, HttpServletRequest request) {
    ModelAndView mav = new ModelAndView();
    MemberVO memberVO = memberService.login(loginMap);
    if (memberVO != null && memberVO.getMember_id() != null) {
      HttpSession session = request.getSession();
      session.setAttribute("isLogOn", true);
      session.setAttribute("memberInfo", memberVO);

      String action = (String) session.getAttribute("action");
      if (action != null && action.equals("/order/orderEachGoods.do")) {
        mav.setViewName("forward:" + action);
      } else {
        mav.setViewName("redirect:/main/main.do");
      }

    } else {
      String message = "아이디나  비밀번호가 틀립니다. 다시 로그인해주세요";
      mav.addObject("message", message);
      mav.setViewName("/member/loginForm");
    }
    return mav;
  }

  @RequestMapping(value = "/logout.do", method = RequestMethod.GET)
  public ModelAndView logout(HttpServletRequest request) {
    ModelAndView mav = new ModelAndView();
    HttpSession session = request.getSession();
    session.setAttribute("isLogOn", false);
    session.removeAttribute("memberInfo");
    mav.setViewName("redirect:/main/main.do");
    return mav;
  }

  @RequestMapping(value = "/addMember.do", method = RequestMethod.POST)
  public ResponseEntity<String> addMember(
      @ModelAttribute("memberVO") MemberVO _memberVO,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    response.setContentType("text/html; charset=UTF-8");
    request.setCharacterEncoding("utf-8");
    String message;

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.add("Content-Type", "text/html; charset=utf-8");
    try {
      memberService.addMember(_memberVO);
      message = "<script>";
      message += " alert('회원 가입을 마쳤습니다.로그인창으로 이동합니다.');";
      message += " location.href='" + request.getContextPath() + "/member/loginForm.do';";
      message += " </script>";

    } catch (Exception e) {
      message = "<script>";
      message += " alert('작업 중 오류가 발생했습니다. 다시 시도해 주세요');";
      message += " location.href='" + request.getContextPath() + "/member/memberForm.do';";
      message += " </script>";
      LOGGER.error(e.getMessage(), e);
    }
    return new ResponseEntity<>(message, responseHeaders, HttpStatus.OK);
  }

  @RequestMapping(value = "/overlapped.do", method = RequestMethod.POST)
  public ResponseEntity<Boolean> overlapped(@RequestParam("id") String id) {
    boolean result = memberService.overlapped(id);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
