package com.bookshop01.mypage.dao;

import com.bookshop01.member.vo.MemberVO;
import com.bookshop01.order.vo.OrderVO;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;

public interface MyPageDAO {
  List<OrderVO> selectMyOrderGoodsList(String member_id) throws DataAccessException;

  List selectMyOrderInfo(String order_id) throws DataAccessException;

  List<OrderVO> selectMyOrderHistoryList(Map dateMap) throws DataAccessException;

  void updateMyInfo(Map memberMap) throws DataAccessException;

  MemberVO selectMyDetailInfo(String member_id) throws DataAccessException;

  void updateMyOrderCancel(String order_id) throws DataAccessException;
}
