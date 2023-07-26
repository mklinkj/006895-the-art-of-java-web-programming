package com.bookshop01.order.controller;

import com.bookshop01.order.vo.OrderVO;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public interface OrderController {
  ModelAndView orderEachGoods(
      @ModelAttribute("orderVO") OrderVO _orderVO,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception;

  ModelAndView orderAllCartGoods(
      @RequestParam String[] cart_goods_qty,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception;

  ModelAndView payToOrderGoods(
      @RequestParam Map<String, String> orderMap,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception;
}
