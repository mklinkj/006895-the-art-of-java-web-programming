package com.bookshop01.admin.order.controller;

import static com.bookshop01.common.util.DateUtils.calcSearchPeriod;

import com.bookshop01.admin.order.service.AdminOrderService;
import com.bookshop01.common.base.BaseController;
import com.bookshop01.order.vo.OrderVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping(value = "/admin/order")
public class AdminOrderController extends BaseController {
  private final AdminOrderService adminOrderService;

  @RequestMapping(
      value = "/adminOrderMain.do",
      method = {RequestMethod.GET, RequestMethod.POST})
  public ModelAndView adminOrderMain(
      @RequestParam Map<String, String> dateMap, HttpServletRequest request) throws Exception {
    String viewName = (String) request.getAttribute("viewName");
    ModelAndView mav = new ModelAndView(viewName);

    String fixedSearchPeriod = dateMap.get("fixedSearchPeriod");
    String section = dateMap.get("section");
    String pageNum = dateMap.get("pageNum");
    String beginDate, endDate;

    String[] tempDate = calcSearchPeriod(fixedSearchPeriod).split(",");
    beginDate = tempDate[0];
    endDate = tempDate[1];
    dateMap.put("beginDate", beginDate);
    dateMap.put("endDate", endDate);

    HashMap<String, Object> condMap = new HashMap<String, Object>();
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
    List<OrderVO> newOrderList = adminOrderService.listNewOrder(condMap);
    mav.addObject("newOrderList", newOrderList);

    String beginDate1[] = beginDate.split("-");
    String endDate2[] = endDate.split("-");
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
      value = "/modifyDeliveryState.do",
      method = {RequestMethod.POST})
  @ResponseBody // TODO: 이 어노테이션을 써주지 않아도 동작은 하지만 써줘야 명확한 것 같은데...
  public ResponseEntity modifyDeliveryState(@RequestParam Map<String, String> deliveryMap)
      throws Exception {
    adminOrderService.modifyDeliveryState(deliveryMap);

    String message = "mod_success";
    return new ResponseEntity(message, HttpStatus.OK);
  }

  @RequestMapping(
      value = "/orderDetail.do",
      method = {RequestMethod.GET, RequestMethod.POST})
  public ModelAndView orderDetail(
      @RequestParam("order_id") int order_id, HttpServletRequest request) throws Exception {
    String viewName = (String) request.getAttribute("viewName");
    ModelAndView mav = new ModelAndView(viewName);
    Map<String, ?> orderMap = adminOrderService.orderDetail(order_id);
    mav.addObject("orderMap", orderMap);
    return mav;
  }
}
