package com.bookshop01.admin.order.controller;

import static com.bookshop01.admin.common.ControllerUtils.processList;

import com.bookshop01.admin.order.service.AdminOrderService;
import com.bookshop01.common.base.BaseController;
import com.bookshop01.order.vo.OrderVO;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
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
    List<OrderVO> resultList = processList(adminOrderService::listNewOrder, paramMap, model);
    model.addAttribute("newOrderList", resultList);
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
