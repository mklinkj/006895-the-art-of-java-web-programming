package com.bookshop01.mypage.controller;

import static com.bookshop01.common.util.DateUtils.calcSearchPeriod;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/mypage")
public class MyPageController extends BaseController {
  private final MyPageService myPageService;

  @RequestMapping(value = "/myPageMain.do", method = RequestMethod.GET)
  public void myPageMain(
      @RequestParam(required = false, value = "message") String message,
      HttpSession session,
      Model model) {

    MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
    String memberId = memberVO.getMember_id();

    List<OrderVO> myOrderList = myPageService.listMyOrderGoods(memberId);

    model.addAttribute("message", message);
    model.addAttribute("myOrderList", myOrderList);
  }

  @RequestMapping(value = "/myOrderDetail.do", method = RequestMethod.GET)
  public void myOrderDetail(
      @RequestParam("order_id") Integer orderId, HttpServletRequest request, Model model) {
    HttpSession session = request.getSession();
    MemberVO orderer = (MemberVO) session.getAttribute("memberInfo");

    List<OrderVO> myOrderList = myPageService.findMyOrderInfo(orderId);
    model.addAttribute("orderer", orderer);
    model.addAttribute("myOrderList", myOrderList);
  }

  @RequestMapping(value = "/listMyOrderHistory.do", method = RequestMethod.GET)
  public void listMyOrderHistory(
      @RequestParam Map<String, String> dateMap, HttpServletRequest request, Model model) {

    HttpSession session = request.getSession();
    MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
    String memberId = memberVO.getMember_id();

    String fixedSearchPeriod = dateMap.get("fixedSearchPeriod");
    String beginDate, endDate;

    String[] tempDate = calcSearchPeriod(fixedSearchPeriod).split(",");
    beginDate = tempDate[0];
    endDate = tempDate[1];
    dateMap.put("beginDate", beginDate);
    dateMap.put("endDate", endDate);
    dateMap.put("member_id", memberId);
    List<OrderVO> myOrderHistList = myPageService.listMyOrderHistory(dateMap);

    String[] beginDate1 = beginDate.split("-"); // 검색일자를 년,월,일로 분리해서 화면에 전달합니다.
    String[] endDate1 = endDate.split("-");
    model.addAttribute("beginYear", beginDate1[0]);
    model.addAttribute("beginMonth", beginDate1[1]);
    model.addAttribute("beginDay", beginDate1[2]);
    model.addAttribute("endYear", endDate1[0]);
    model.addAttribute("endMonth", endDate1[1]);
    model.addAttribute("endDay", endDate1[2]);
    model.addAttribute("myOrderHistList", myOrderHistList);
  }

  @RequestMapping(value = "/cancelMyOrder.do", method = RequestMethod.POST)
  public String cancelMyOrder(@RequestParam("order_id") Integer orderId, Model model) {
    myPageService.cancelOrder(orderId);
    model.addAttribute("message", "cancel_order");
    return "redirect:/mypage/myPageMain.do";
  }

  @RequestMapping(value = "/myDetailInfo.do", method = RequestMethod.GET)
  public void myDetailInfo() {}

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
