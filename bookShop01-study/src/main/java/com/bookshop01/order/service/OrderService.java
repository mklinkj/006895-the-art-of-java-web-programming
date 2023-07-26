package com.bookshop01.order.service;

import com.bookshop01.order.vo.OrderVO;
import java.util.List;

public interface OrderService {
  List<OrderVO> listMyOrderGoods(OrderVO orderVO) throws Exception;

  void addNewOrder(List<OrderVO> myOrderList) throws Exception;

  OrderVO findMyOrder(String order_id) throws Exception;
}
