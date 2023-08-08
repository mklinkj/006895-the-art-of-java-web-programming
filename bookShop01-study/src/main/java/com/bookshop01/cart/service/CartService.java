package com.bookshop01.cart.service;

import com.bookshop01.cart.vo.CartVO;
import java.util.List;
import java.util.Map;

public interface CartService {
  Map<String, List<?>> myCartList(CartVO cartVO);

  boolean findCartGoods(CartVO cartVO);

  void addGoodsInCart(CartVO cartVO);

  boolean modifyCartQty(CartVO cartVO);

  void removeCartGoods(int cartId);
}
