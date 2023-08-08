package com.bookshop01.order.dao;

import com.bookshop01.order.vo.OrderVO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class OrderDAOImpl implements OrderDAO {
  private final SqlSession sqlSession;

  @Override
  public List<OrderVO> listMyOrderGoods(OrderVO orderVO) {
    return sqlSession.selectList(
        "mapper.order.selectMyOrderList",
        Map.of("orderVO", orderVO, "baseTime", LocalDateTime.now()));
  }

  @Override
  public void insertNewOrder(List<OrderVO> myOrderList) {
    // 나의 주문들에 대해 새로운 주문 ID를 부여
    final int newOrderId = selectOrderID();
    for (OrderVO myOrder : myOrderList) {
      myOrder.setOrder_id(newOrderId);
      sqlSession.insert("mapper.order.insertNewOrder", myOrder);
    }
  }

  @Override
  public OrderVO findMyOrder(Integer orderId) {
    return sqlSession.selectOne("mapper.order.selectMyOrder", orderId);
  }

  @Override
  public void removeGoodsFromCart(Integer goodsId) {
    sqlSession.delete("mapper.order.deleteGoodsFromCart", goodsId);
  }

  private int selectOrderID() {
    return sqlSession.selectOne("mapper.order.selectOrderID");
  }
}
