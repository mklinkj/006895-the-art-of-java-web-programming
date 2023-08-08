package com.bookshop01.order.service;

import com.bookshop01.order.vo.OrderVO;
import java.util.List;

public interface OrderService {
  List<OrderVO> listMyOrderGoods(OrderVO orderVO);

  void addNewOrder(List<OrderVO> myOrderList);

  OrderVO findMyOrder(Integer orderId);
}
