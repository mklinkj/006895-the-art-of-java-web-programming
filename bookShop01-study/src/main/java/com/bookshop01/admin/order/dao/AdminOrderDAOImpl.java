package com.bookshop01.admin.order.dao;

import com.bookshop01.common.pagination.PageRequest;
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

  // TODO: 왜 메서드 이름 중에 왜 New가 붙었을까? 생각을 해봤는데..
  //   아무래도 새로운 주문이라서 그런게 아니고, 이전에 만들어진 코드가 정리되지 않고,
  //   New를 붙여서 메서드를 새로 만든 것 같다.
  //   미사용 코드는 지우고 메서드 이름을 정리하자!
  public List<OrderVO> selectNewOrderList(PageRequest pageRequest, Map<String, ?> condMap) {

    return sqlSession.selectList(
        "mapper.admin.order.selectNewOrderList",
        Map.of("pageRequest", pageRequest, "condMap", condMap));
  }

  @Override
  public long countOrder(Map<String, ?> condMap) {
    return sqlSession.selectOne("mapper.admin.order.countOrder", Map.of("condMap", condMap));
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
