package com.bookshop01.order.dao;

import com.bookshop01.order.vo.OrderVO;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface OrderDAO {
  List<OrderVO> listMyOrderGoods(OrderVO orderBean) throws DataAccessException;

  void insertNewOrder(List<OrderVO> myOrderList) throws DataAccessException;

  OrderVO findMyOrder(String order_id) throws DataAccessException;

  void removeGoodsFromCart(List<OrderVO> myOrderList) throws DataAccessException;
}
