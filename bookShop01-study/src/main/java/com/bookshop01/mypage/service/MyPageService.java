package com.bookshop01.mypage.service;

import com.bookshop01.member.vo.MemberVO;
import com.bookshop01.order.vo.OrderVO;
import java.util.List;
import java.util.Map;

public interface MyPageService {
  List<OrderVO> listMyOrderGoods(String memberId);

  List<OrderVO> findMyOrderInfo(Integer orderId);

  List<OrderVO> listMyOrderHistory(Map<String, String> dateMap);

  MemberVO modifyMyInfo(Map<String, String> memberMap);

  void cancelOrder(Integer orderId);

  MemberVO myDetailInfo(String memberId);
}
