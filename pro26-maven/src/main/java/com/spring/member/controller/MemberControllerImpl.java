package com.spring.member.controller;

import com.spring.member.service.MemberService;
import com.spring.member.vo.MemberVO;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller("memberController")
public class MemberControllerImpl implements MemberController {
  private static final Logger LOGGER = LoggerFactory.getLogger(MemberControllerImpl.class);

  @Autowired private MemberService memberService;

  // TODO: 아래 필드는 불필요해보임.
  @Autowired private MemberVO memberVO;

  @Override
  @RequestMapping(value = "/member/listMembers.do", method = RequestMethod.GET)
  public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    String viewName = getViewName(request);
    List<MemberVO> membersList = memberService.listMembers();
    ModelAndView mav = new ModelAndView(viewName);
    mav.addObject("membersList", membersList);
    return mav;
  }

  @Override
  @RequestMapping(value = "/member/addMember.do", method = RequestMethod.POST)
  public ModelAndView addMember(
      @ModelAttribute("member") MemberVO member,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    request.setCharacterEncoding("utf-8");

    LOGGER.info("### 필드의 memberVO:: {}", memberVO);
    LOGGER.info("### 인자로 전달받은 memberVO:: {}", member);
    LOGGER.info("### 두 VO가 같은지?:: {}", Objects.equals(memberVO, member));

    int result = 0;
    result = memberService.addMember(member);
    ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
    return mav;
  }

  @Override
  @RequestMapping(value = "/member/removeMember.do", method = RequestMethod.GET)
  public ModelAndView removeMember(
      @RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    request.setCharacterEncoding("utf-8");
    memberService.removeMember(id);
    ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
    return mav;
  }

  /*@RequestMapping(value = { "/member/loginForm.do", "/member/memberForm.do" }, method =  RequestMethod.GET)*/
  @RequestMapping(value = "/member/*Form.do", method = RequestMethod.GET)
  public ModelAndView form(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    String viewName = getViewName(request);
    ModelAndView mav = new ModelAndView();
    mav.setViewName(viewName);
    return mav;
  }

  private String getViewName(HttpServletRequest request) throws Exception {
    String contextPath = request.getContextPath();
    String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
    if (uri == null || uri.trim().equals("")) {
      uri = request.getRequestURI();
    }

    int begin = 0;
    if (!((contextPath == null) || ("".equals(contextPath)))) {
      begin = contextPath.length();
    }

    int end;
    if (uri.indexOf(";") != -1) {
      end = uri.indexOf(";");
    } else if (uri.indexOf("?") != -1) {
      end = uri.indexOf("?");
    } else {
      end = uri.length();
    }

    String viewName = uri.substring(begin, end);
    if (viewName.indexOf(".") != -1) {
      viewName = viewName.substring(0, viewName.lastIndexOf("."));
    }
    if (viewName.lastIndexOf("/") != -1) {
      viewName = viewName.substring(viewName.lastIndexOf("/"), viewName.length());
    }
    return viewName;
  }
}
