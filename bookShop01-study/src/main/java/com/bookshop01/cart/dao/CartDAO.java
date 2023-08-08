package com.bookshop01.cart.dao;

import com.bookshop01.cart.vo.CartVO;
import com.bookshop01.goods.vo.GoodsVO;
import java.util.List;

public interface CartDAO {
  List<CartVO> selectCartList(String memberId);

  List<GoodsVO> selectGoodsList(List<Integer> goodsIdList);

  boolean selectCountInCart(CartVO cartVO);

  void insertGoodsInCart(CartVO cartVO);

  void updateCartGoodsQty(CartVO cartVO);

  void deleteCartGoods(int cartId);
}
