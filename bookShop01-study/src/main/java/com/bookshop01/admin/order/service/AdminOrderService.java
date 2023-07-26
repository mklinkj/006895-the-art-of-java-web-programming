package com.bookshop01.admin.order.service;

import com.bookshop01.order.vo.OrderVO;
import java.util.List;
import java.util.Map;

public interface AdminOrderService {
  List<OrderVO> listNewOrder(Map condMap) throws Exception;

  void modifyDeliveryState(Map deliveryMap) throws Exception;

  Map orderDetail(int order_id) throws Exception;
}
