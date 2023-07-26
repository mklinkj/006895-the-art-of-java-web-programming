package com.bookshop01.cart.service;

import com.bookshop01.cart.vo.CartVO;
import java.util.List;
import java.util.Map;

public interface CartService {
  Map<String, List> myCartList(CartVO cartVO) throws Exception;

  boolean findCartGoods(CartVO cartVO) throws Exception;

  void addGoodsInCart(CartVO cartVO) throws Exception;

  boolean modifyCartQty(CartVO cartVO) throws Exception;

  void removeCartGoods(int cart_id) throws Exception;
}
