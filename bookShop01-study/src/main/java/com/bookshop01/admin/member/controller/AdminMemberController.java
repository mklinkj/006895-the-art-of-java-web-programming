package com.bookshop01.admin.member.controller;

import static com.bookshop01.common.util.DateUtils.calcSearchPeriod;

import com.bookshop01.admin.member.service.AdminMemberService;
import com.bookshop01.common.base.BaseController;
import com.bookshop01.member.vo.MemberVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/admin/member")
public class AdminMemberController extends BaseController {
  private final AdminMemberService adminMemberService;

  @RequestMapping(
      value = "/adminMemberMain.do",
      method = {RequestMethod.POST, RequestMethod.GET})
  public void adminGoodsMain(@RequestParam Map<String, String> paramMap, Model model) {

    String fixedSearchPeriod = paramMap.get("fixedSearchPeriod");
    String section =
        paramMap.get("section"); // TODO: View에서는 chapter로 쓰고 쿼리에서는 section으로 쓰고 있었다. 😓 정리를 해야할 듯..
    String pageNum = paramMap.get("pageNum");
    String beginDate, endDate;

    String[] tempDate = calcSearchPeriod(fixedSearchPeriod).split(",");
    beginDate = tempDate[0];
    endDate = tempDate[1];
    paramMap.put("beginDate", beginDate);
    paramMap.put("endDate", endDate);

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

    val command = paramMap.get("command");
    model.addAttribute("command", command);

    if ("detail_search".equals(command)) {
      val searchType = paramMap.get("search_type");
      val searchWord = paramMap.get("search_word");
      condMap.put("search_type", searchType);
      condMap.put("search_word", searchWord);
      model.addAttribute("search_type", searchType);
      model.addAttribute("search_word", searchWord);
    }

    List<MemberVO> memberList = adminMemberService.listMember(condMap);
    model.addAttribute("member_list", memberList);

    String[] beginDate1 = beginDate.split("-");
    String[] endDate2 = endDate.split("-");
    model.addAttribute("beginYear", beginDate1[0]);
    model.addAttribute("beginMonth", beginDate1[1]);
    model.addAttribute("beginDay", beginDate1[2]);
    model.addAttribute("endYear", endDate2[0]);
    model.addAttribute("endMonth", endDate2[1]);
    model.addAttribute("endDay", endDate2[2]);

    model.addAttribute("section", section);
    model.addAttribute("pageNum", pageNum);
  }

  @RequestMapping(
      value = "/memberDetail.do",
      method = {RequestMethod.POST, RequestMethod.GET})
  public void memberDetail(@RequestParam("member_id") String memberId, Model model) {
    MemberVO memberInfo = adminMemberService.memberDetail(memberId);
    model.addAttribute("member_info", memberInfo);
  }

  @RequestMapping(
      value = "/modifyMemberInfo.do",
      method = {RequestMethod.POST, RequestMethod.GET})
  @ResponseBody
  public ResponseEntity<String> modifyMemberInfo(
      @RequestParam("member_id") String memberId,
      @RequestParam("mod_type") String modType,
      @RequestParam("value") String value) {
    Map<String, String> memberMap = new HashMap<>();
    String[] val;
    switch (modType) {
      case "member_pw" -> memberMap.put("member_pw", value);
      case "member_gender" -> memberMap.put("member_gender", value);
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
    memberMap.put("member_id", memberId);
    adminMemberService.modifyMemberInfo(memberMap);
    return ResponseEntity.ok("mod_success");
  }

  @RequestMapping(
      value = "/deleteMember.do",
      method = {RequestMethod.POST})
  public String deleteMember(
      @RequestParam("member_id") String memberId, //
      @RequestParam("del_yn") String delYn) {
    adminMemberService.modifyMemberInfo(Map.of("del_yn", delYn, "member_id", memberId));
    return "redirect:/admin/member/adminMemberMain.do";
  }
}
