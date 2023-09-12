package com.bookshop01.admin.order.dao;

import com.bookshop01.common.pagination.PageRequest;
import com.bookshop01.member.vo.MemberVO;
import com.bookshop01.order.vo.OrderVO;
import java.util.List;
import java.util.Map;

public interface AdminOrderDAO {
  List<OrderVO> selectNewOrderList(PageRequest pageRequest, Map<String, ?> condMap);

  long countOrder(Map<String, ?> condMap);

  void updateDeliveryState(Map<String, ?> deliveryMap);

  List<OrderVO> selectOrderDetail(int orderId);

  MemberVO selectOrderer(String memberId);
}
