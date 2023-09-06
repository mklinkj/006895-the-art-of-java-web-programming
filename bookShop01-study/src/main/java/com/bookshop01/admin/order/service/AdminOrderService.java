package com.bookshop01.admin.order.service;

import com.bookshop01.order.vo.OrderVO;
import java.util.List;
import java.util.Map;

public interface AdminOrderService {
  List<OrderVO> listNewOrder(Map<String, ?> condMap);

  void modifyDeliveryState(Map<String, ?> deliveryMap);

  Map<String, ?> orderDetail(Integer orderId);
}
