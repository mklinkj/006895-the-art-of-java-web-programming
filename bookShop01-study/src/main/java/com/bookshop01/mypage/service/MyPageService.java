package com.bookshop01.mypage.service;

import com.bookshop01.member.vo.MemberVO;
import com.bookshop01.order.vo.OrderVO;
import java.util.List;
import java.util.Map;

public interface MyPageService {
  List<OrderVO> listMyOrderGoods(String member_id) throws Exception;

  List findMyOrderInfo(String order_id) throws Exception;

  List<OrderVO> listMyOrderHistory(Map dateMap) throws Exception;

  MemberVO modifyMyInfo(Map memberMap) throws Exception;

  void cancelOrder(String order_id) throws Exception;

  MemberVO myDetailInfo(String member_id) throws Exception;
}
