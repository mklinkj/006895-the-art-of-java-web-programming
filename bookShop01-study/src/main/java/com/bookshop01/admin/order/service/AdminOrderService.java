package com.bookshop01.admin.order.service;

import com.bookshop01.admin.common.pagination.PageRequest;
import com.bookshop01.admin.common.pagination.PageResponse;
import com.bookshop01.order.vo.OrderVO;
import java.util.Map;

public interface AdminOrderService {
  PageResponse<OrderVO> listNewOrder(PageRequest pageRequest, Map<String, ?> condMap);

  void modifyDeliveryState(Map<String, ?> deliveryMap);

  Map<String, ?> orderDetail(Integer orderId);
}
