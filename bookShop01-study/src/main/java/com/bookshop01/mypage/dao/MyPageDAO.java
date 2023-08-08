package com.bookshop01.mypage.dao;

import com.bookshop01.member.vo.MemberVO;
import com.bookshop01.order.vo.OrderVO;
import java.util.List;
import java.util.Map;

public interface MyPageDAO {
  List<OrderVO> selectMyOrderGoodsList(String memberId);

  List<OrderVO> selectMyOrderInfo(Integer orderId);

  List<OrderVO> selectMyOrderHistoryList(Map<String, String> dateMap);

  void updateMyInfo(Map<String, String> memberMap);

  MemberVO selectMyDetailInfo(String memberId);

  void updateMyOrderCancel(Integer orderId);
}
