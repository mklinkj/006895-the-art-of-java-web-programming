package com.bookshop01.order.dao;

import com.bookshop01.order.vo.OrderVO;
import java.util.List;

public interface OrderDAO {
  List<OrderVO> listMyOrderGoods(OrderVO orderBean);

  void insertNewOrder(List<OrderVO> myOrderList);

  OrderVO findMyOrder(Integer orderId);

  void removeGoodsFromCart(Integer goodsId);
}
