package com.bookshop01.order.service;

import com.bookshop01.order.dao.OrderDAO;
import com.bookshop01.order.vo.OrderVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class OrderServiceImpl implements OrderService {
  private final OrderDAO orderDAO;

  public List<OrderVO> listMyOrderGoods(OrderVO orderVO) {
    return orderDAO.listMyOrderGoods(orderVO);
  }

  public void addNewOrder(List<OrderVO> myOrderList) {
    orderDAO.insertNewOrder(myOrderList);
    // 카트에서 주문 상품 제거한다.
    for (OrderVO myOrder : myOrderList) {
      orderDAO.removeGoodsFromCart(myOrder.getGoods_id());
    }
  }

  public OrderVO findMyOrder(Integer orderId) {
    return orderDAO.findMyOrder(orderId);
  }
}
