package com.bookshop01.mypage.dao;

import com.bookshop01.member.vo.MemberVO;
import com.bookshop01.order.vo.OrderVO;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MyPageDAOImpl implements MyPageDAO {
  private final SqlSession sqlSession;

  public List<OrderVO> selectMyOrderGoodsList(String memberId) {
    return sqlSession.selectList("mapper.mypage.selectMyOrderGoodsList", memberId);
  }

  public List<OrderVO> selectMyOrderInfo(Integer orderId) {
    return sqlSession.selectList("mapper.mypage.selectMyOrderInfo", orderId);
  }

  public List<OrderVO> selectMyOrderHistoryList(Map<String, String> dateMap) {
    return sqlSession.selectList("mapper.mypage.selectMyOrderHistoryList", dateMap);
  }

  public void updateMyInfo(Map<String, String> memberMap) {
    sqlSession.update("mapper.mypage.updateMyInfo", memberMap);
  }

  public MemberVO selectMyDetailInfo(String memberId) {
    return sqlSession.selectOne("mapper.mypage.selectMyDetailInfo", memberId);
  }

  public void updateMyOrderCancel(Integer orderId) {
    sqlSession.update("mapper.mypage.updateMyOrderCancel", orderId);
  }
}
