package com.bookshop01.cart.dao;

import com.bookshop01.cart.vo.CartVO;
import com.bookshop01.goods.vo.GoodsVO;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface CartDAO {
  List<CartVO> selectCartList(CartVO cartVO) throws DataAccessException;

  List<GoodsVO> selectGoodsList(List<CartVO> cartList) throws DataAccessException;

  boolean selectCountInCart(CartVO cartVO) throws DataAccessException;

  void insertGoodsInCart(CartVO cartVO) throws DataAccessException;

  void updateCartGoodsQty(CartVO cartVO) throws DataAccessException;

  void deleteCartGoods(int cart_id) throws DataAccessException;
}
