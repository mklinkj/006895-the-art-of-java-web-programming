package com.bookshop01.admin.member.controller;

import com.bookshop01.admin.member.service.AdminMemberService;
import com.bookshop01.common.base.BaseController;
import com.bookshop01.member.vo.MemberVO;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/admin/member")
public class AdminMemberController extends BaseController {
  private final AdminMemberService adminMemberService;

  @RequestMapping(
      value = "/adminMemberMain.do",
      method = {RequestMethod.POST, RequestMethod.GET})
  public ModelAndView adminGoodsMain(
      @RequestParam Map<String, String> dateMap, HttpServletRequest request) {
    String viewName = (String) request.getAttribute("viewName");
    ModelAndView mav = new ModelAndView(viewName);

    String fixedSearchPeriod = dateMap.get("fixedSearchPeriod");
    String section =
        dateMap.get("section"); // TODO: View에서는 chapter로 쓰고 쿼리에서는 section으로 쓰고 있었다. 😓 정리를 해야할 듯..
    String pageNum = dateMap.get("pageNum");
    String beginDate, endDate;

    String[] tempDate = calcSearchPeriod(fixedSearchPeriod).split(",");
    beginDate = tempDate[0];
    endDate = tempDate[1];
    dateMap.put("beginDate", beginDate);
    dateMap.put("endDate", endDate);

    HashMap<String, Object> condMap = new HashMap<>();
    if (section == null) {
      section = "1";
    }
    condMap.put("section", section);
    if (pageNum == null) {
      pageNum = "1";
    }
    condMap.put("pageNum", pageNum);
    condMap.put("beginDate", beginDate);
    condMap.put("endDate", endDate);
    List<MemberVO> member_list = adminMemberService.listMember(condMap);
    mav.addObject("member_list", member_list);

    String[] beginDate1 = beginDate.split("-");
    String[] endDate2 = endDate.split("-");
    mav.addObject("beginYear", beginDate1[0]);
    mav.addObject("beginMonth", beginDate1[1]);
    mav.addObject("beginDay", beginDate1[2]);
    mav.addObject("endYear", endDate2[0]);
    mav.addObject("endMonth", endDate2[1]);
    mav.addObject("endDay", endDate2[2]);

    mav.addObject("section", section);
    mav.addObject("pageNum", pageNum);
    return mav;
  }

  @RequestMapping(
      value = "/memberDetail.do",
      method = {RequestMethod.POST, RequestMethod.GET})
  public ModelAndView memberDetail(HttpServletRequest request) {
    String viewName = (String) request.getAttribute("viewName");
    ModelAndView mav = new ModelAndView(viewName);
    String member_id = request.getParameter("member_id");
    MemberVO member_info = adminMemberService.memberDetail(member_id);
    mav.addObject("member_info", member_info);
    return mav;
  }

  @RequestMapping(
      value = "/modifyMemberInfo.do",
      method = {RequestMethod.POST, RequestMethod.GET})
  public void modifyMemberInfo(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    Map<String, String> memberMap = new HashMap<>();
    String[] val;
    PrintWriter pw = response.getWriter();
    String member_id = request.getParameter("member_id");
    String mod_type = request.getParameter("mod_type");
    String value = request.getParameter("value");
    switch (mod_type) {
      case "member_birth" -> {
        val = value.split(",");
        memberMap.put("member_birth_y", val[0]);
        memberMap.put("member_birth_m", val[1]);
        memberMap.put("member_birth_d", val[2]);
        memberMap.put("member_birth_gn", val[3]);
      }
      case "tel" -> {
        val = value.split(",");
        memberMap.put("tel1", val[0]);
        memberMap.put("tel2", val[1]);
        memberMap.put("tel3", val[2]);
      }
      case "hp" -> {
        val = value.split(",");
        memberMap.put("hp1", val[0]);
        memberMap.put("hp2", val[1]);
        memberMap.put("hp3", val[2]);
        memberMap.put("smssts_yn", val[3]);
      }
      case "email" -> {
        val = value.split(",");
        memberMap.put("email1", val[0]);
        memberMap.put("email2", val[1]);
        memberMap.put("emailsts_yn", val[2]);
      }
      case "address" -> {
        val = value.split(",");
        memberMap.put("zipcode", val[0]);
        memberMap.put("roadAddress", val[1]);
        memberMap.put("jibunAddress", val[2]);
        memberMap.put("namujiAddress", val[3]);
      }
    }

    memberMap.put("member_id", member_id);

    adminMemberService.modifyMemberInfo(memberMap);
    pw.print("mod_success");
    pw.close();
  }

  @RequestMapping(
      value = "/deleteMember.do",
      method = {RequestMethod.POST})
  public ModelAndView deleteMember(HttpServletRequest request) {
    ModelAndView mav = new ModelAndView();
    Map<String, String> memberMap = new HashMap<>();
    String member_id = request.getParameter("member_id");
    String del_yn = request.getParameter("del_yn");
    memberMap.put("del_yn", del_yn);
    memberMap.put("member_id", member_id);

    adminMemberService.modifyMemberInfo(memberMap);
    mav.setViewName("redirect:/admin/member/adminMemberMain.do");
    return mav;
  }
}
