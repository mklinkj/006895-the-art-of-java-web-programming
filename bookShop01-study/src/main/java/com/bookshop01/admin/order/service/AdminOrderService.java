package com.bookshop01.admin.order.service;

import com.bookshop01.order.vo.OrderVO;
import java.util.List;
import java.util.Map;

public interface AdminOrderService {
  List<OrderVO> listNewOrder(Map<String, ?> condMap) throws Exception;

  void modifyDeliveryState(Map<String, ?> deliveryMap) throws Exception;

  Map<String, ?> orderDetail(int orderId) throws Exception;
}
