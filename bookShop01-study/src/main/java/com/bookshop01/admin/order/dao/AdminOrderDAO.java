package com.bookshop01.admin.order.dao;

import com.bookshop01.member.vo.MemberVO;
import com.bookshop01.order.vo.OrderVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;

public interface AdminOrderDAO {
  List<OrderVO> selectNewOrderList(Map<String, ?> condMap);

  void updateDeliveryState(Map<String, ?> deliveryMap);

  List<OrderVO> selectOrderDetail(int orderId);

  MemberVO selectOrderer(String memberId);
}
