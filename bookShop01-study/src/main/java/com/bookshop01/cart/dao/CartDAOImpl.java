package com.bookshop01.cart.dao;

import com.bookshop01.cart.vo.CartVO;
import com.bookshop01.goods.vo.GoodsVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CartDAOImpl implements CartDAO {
  private final SqlSession sqlSession;

  public List<CartVO> selectCartList(String memberId) {
    return sqlSession.selectList("mapper.cart.selectCartList", memberId);
  }

  public List<GoodsVO> selectGoodsList(List<Integer> goodsIdList) {
    return sqlSession.selectList("mapper.cart.selectGoodsList", goodsIdList);
  }

  public boolean selectCountInCart(CartVO cartVO) {
    int count = sqlSession.selectOne("mapper.cart.selectCountInCart", cartVO);
    return count > 0;
  }

  public void insertGoodsInCart(CartVO cartVO) {
    final int cart_id = selectMaxCartId();
    cartVO.setCart_id(cart_id);
    sqlSession.insert("mapper.cart.insertGoodsInCart", cartVO);
  }

  public void updateCartGoodsQty(CartVO cartVO) {
    sqlSession.insert("mapper.cart.updateCartGoodsQty", cartVO);
  }

  public void deleteCartGoods(int cartId) {
    sqlSession.delete("mapper.cart.deleteCartGoods", cartId);
  }

  private int selectMaxCartId() {
    return sqlSession.selectOne("mapper.cart.selectMaxCartId");
  }
}
