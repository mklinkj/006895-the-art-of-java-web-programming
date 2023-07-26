package com.bookshop01.admin.order.dao;

import com.bookshop01.member.vo.MemberVO;
import com.bookshop01.order.vo.OrderVO;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.dao.DataAccessException;

public interface AdminOrderDAO {
  ArrayList<OrderVO> selectNewOrderList(Map condMap) throws DataAccessException;

  void updateDeliveryState(Map deliveryMap) throws DataAccessException;

  ArrayList<OrderVO> selectOrderDetail(int order_id) throws DataAccessException;

  MemberVO selectOrderer(String member_id) throws DataAccessException;
}
