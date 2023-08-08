package com.bookshop01.mypage.service;

import com.bookshop01.member.vo.MemberVO;
import com.bookshop01.mypage.dao.MyPageDAO;
import com.bookshop01.order.vo.OrderVO;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class MyPageServiceImpl implements MyPageService {
  private final MyPageDAO myPageDAO;

  public List<OrderVO> listMyOrderGoods(String memberId) {
    return myPageDAO.selectMyOrderGoodsList(memberId);
  }

  public List<OrderVO> findMyOrderInfo(Integer orderId) {
    return myPageDAO.selectMyOrderInfo(orderId);
  }

  public List<OrderVO> listMyOrderHistory(Map<String, String> dateMap) {
    return myPageDAO.selectMyOrderHistoryList(dateMap);
  }

  public MemberVO modifyMyInfo(Map<String, String> memberMap) {
    String memberId = memberMap.get("member_id");
    myPageDAO.updateMyInfo(memberMap);
    return myPageDAO.selectMyDetailInfo(memberId);
  }

  public void cancelOrder(Integer orderId) {
    myPageDAO.updateMyOrderCancel(orderId);
  }

  public MemberVO myDetailInfo(String memberId) {
    return myPageDAO.selectMyDetailInfo(memberId);
  }
}
