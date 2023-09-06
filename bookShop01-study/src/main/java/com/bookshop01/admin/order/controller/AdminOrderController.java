package com.bookshop01.admin.order.controller;

import static com.bookshop01.common.constants.Constants.DATE_FORMAT_YYYY_MM_DD;

import com.bookshop01.admin.order.service.AdminOrderService;
import com.bookshop01.common.base.BaseController;
import com.bookshop01.order.vo.OrderVO;
import java.time.LocalDate;
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
@RequestMapping(value = "/admin/order")
public class AdminOrderController extends BaseController {
  private final AdminOrderService adminOrderService;

  @RequestMapping(
      value = "/adminOrderMain.do",
      method = {RequestMethod.GET, RequestMethod.POST})
  public void adminOrderMain(@RequestParam Map<String, String> paramMap, Model model) {

    String section = paramMap.get("section");
    String pageNum = paramMap.get("pageNum");
    String beginDate = paramMap.get("beginDate");
    String endDate = paramMap.get("endDate");

    if (beginDate == null || endDate == null) {
      LocalDate today = LocalDate.now();
      beginDate = today.minusMonths(4).format(DATE_FORMAT_YYYY_MM_DD);
      endDate = today.format(DATE_FORMAT_YYYY_MM_DD);
    }

    Map<String, Object> condMap = new HashMap<>();
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

    List<OrderVO> newOrderList = adminOrderService.listNewOrder(condMap);
    model.addAttribute("newOrderList", newOrderList);

    model.addAttribute("beginDate", beginDate);
    model.addAttribute("endDate", endDate);

    model.addAttribute("section", section);
    model.addAttribute("pageNum", pageNum);
  }

  @RequestMapping(
      value = "/modifyDeliveryState.do",
      method = {RequestMethod.POST})
  @ResponseBody
  public ResponseEntity<String> modifyDeliveryState(@RequestParam Map<String, String> deliveryMap) {
    adminOrderService.modifyDeliveryState(deliveryMap);
    return ResponseEntity.ok("mod_success");
  }

  @RequestMapping(
      value = "/orderDetail.do",
      method = {RequestMethod.GET, RequestMethod.POST})
  public void orderDetail(@RequestParam("order_id") Integer orderId, Model model) {
    Map<String, ?> orderMap = adminOrderService.orderDetail(orderId);
    model.addAttribute("orderMap", orderMap);
  }
}
