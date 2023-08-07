package com.bookshop01.admin.order.dao;

import com.bookshop01.member.vo.MemberVO;
import com.bookshop01.order.vo.OrderVO;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AdminOrderDAOImpl implements AdminOrderDAO {
  private final SqlSession sqlSession;

  public List<OrderVO> selectNewOrderList(Map<String, ?> condMap) {
    return sqlSession.selectList("mapper.admin.order.selectNewOrderList", condMap);
  }

  public void updateDeliveryState(Map<String, ?> deliveryMap) {
    sqlSession.update("mapper.admin.order.updateDeliveryState", deliveryMap);
  }

  public List<OrderVO> selectOrderDetail(int orderId) {
    return sqlSession.selectList("mapper.admin.order.selectOrderDetail", orderId);
  }

  public MemberVO selectOrderer(String memberId) {
    return sqlSession.selectOne("mapper.admin.order.selectOrderer", memberId);
  }
}
