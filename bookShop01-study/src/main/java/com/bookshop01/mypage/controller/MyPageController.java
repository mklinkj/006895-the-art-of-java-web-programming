package com.bookshop01.mypage.controller;

import com.bookshop01.common.base.BaseController;
import com.bookshop01.member.vo.MemberVO;
import com.bookshop01.mypage.service.MyPageService;
import com.bookshop01.order.vo.OrderVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/mypage")
public class MyPageController extends BaseController {
  private final MyPageService myPageService;

  @RequestMapping(value = "/myPageMain.do", method = RequestMethod.GET)
  public ModelAndView myPageMain(
      @RequestParam(required = false, value = "message") String message,
      HttpServletRequest request,
      HttpSession session) {
    String viewName = (String) request.getAttribute("viewName");
    ModelAndView mav = new ModelAndView(viewName);
    MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
    String member_id = memberVO.getMember_id();

    List<OrderVO> myOrderList = myPageService.listMyOrderGoods(member_id);

    mav.addObject("message", message);
    mav.addObject("myOrderList", myOrderList);

    return mav;
  }

  @RequestMapping(value = "/myOrderDetail.do", method = RequestMethod.GET)
  public ModelAndView myOrderDetail(
      @RequestParam("order_id") Integer orderId, HttpServletRequest request) {
    String viewName = (String) request.getAttribute("viewName");
    ModelAndView mav = new ModelAndView(viewName);
    HttpSession session = request.getSession();
    MemberVO orderer = (MemberVO) session.getAttribute("memberInfo");

    List<OrderVO> myOrderList = myPageService.findMyOrderInfo(orderId);
    mav.addObject("orderer", orderer);
    mav.addObject("myOrderList", myOrderList);
    return mav;
  }

  @RequestMapping(value = "/listMyOrderHistory.do", method = RequestMethod.GET)
  public ModelAndView listMyOrderHistory(
      @RequestParam Map<String, String> dateMap, HttpServletRequest request) {
    String viewName = (String) request.getAttribute("viewName");
    ModelAndView mav = new ModelAndView(viewName);
    HttpSession session = request.getSession();
    MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
    String member_id = memberVO.getMember_id();

    String fixedSearchPeriod = dateMap.get("fixedSearchPeriod");
    String beginDate, endDate;

    String[] tempDate = calcSearchPeriod(fixedSearchPeriod).split(",");
    beginDate = tempDate[0];
    endDate = tempDate[1];
    dateMap.put("beginDate", beginDate);
    dateMap.put("endDate", endDate);
    dateMap.put("member_id", member_id);
    List<OrderVO> myOrderHistList = myPageService.listMyOrderHistory(dateMap);

    String[] beginDate1 = beginDate.split("-"); // 검색일자를 년,월,일로 분리해서 화면에 전달합니다.
    String[] endDate1 = endDate.split("-");
    mav.addObject("beginYear", beginDate1[0]);
    mav.addObject("beginMonth", beginDate1[1]);
    mav.addObject("beginDay", beginDate1[2]);
    mav.addObject("endYear", endDate1[0]);
    mav.addObject("endMonth", endDate1[1]);
    mav.addObject("endDay", endDate1[2]);
    mav.addObject("myOrderHistList", myOrderHistList);
    return mav;
  }

  @RequestMapping(value = "/cancelMyOrder.do", method = RequestMethod.POST)
  public ModelAndView cancelMyOrder(@RequestParam("order_id") Integer orderId) {
    ModelAndView mav = new ModelAndView();
    myPageService.cancelOrder(orderId);
    mav.addObject("message", "cancel_order");
    mav.setViewName("redirect:/mypage/myPageMain.do");
    return mav;
  }

  @RequestMapping(value = "/myDetailInfo.do", method = RequestMethod.GET)
  public ModelAndView myDetailInfo(HttpServletRequest request) {
    String viewName = (String) request.getAttribute("viewName");
    return new ModelAndView(viewName);
  }

  @RequestMapping(value = "/modifyMyInfo.do", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<String> modifyMyInfo(
      @RequestParam("attribute") String attribute,
      @RequestParam("value") String value,
      HttpServletRequest request) {
    Map<String, String> memberMap = new HashMap<>();
    String[] val;
    HttpSession session = request.getSession();
    MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
    String member_id = memberVO.getMember_id();
    switch (attribute) {
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
      default -> memberMap.put(attribute, value);
    }

    memberMap.put("member_id", member_id);

    // 수정된 회원 정보를 다시 세션에 저장한다.
    memberVO = myPageService.modifyMyInfo(memberMap);
    session.removeAttribute("memberInfo");
    session.setAttribute("memberInfo", memberVO);

    String message = "mod_success";
    return new ResponseEntity<>(message, HttpStatus.OK);
  }
}
