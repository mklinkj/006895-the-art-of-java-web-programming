package com.bookshop01.cart.service;

import com.bookshop01.cart.dao.CartDAO;
import com.bookshop01.cart.vo.CartVO;
import com.bookshop01.goods.vo.GoodsVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CartServiceImpl implements CartService {
  private final CartDAO cartDAO;

  public Map<String, List<?>> myCartList(CartVO cartVO) {
    Map<String, List<?>> cartMap = new HashMap<>();
    List<CartVO> myCartList = cartDAO.selectCartList(cartVO.getMember_id());
    if (myCartList.isEmpty()) { // 카트에 저장된 상품이 없는 경우
      return null;
    }
    List<GoodsVO> myGoodsList =
        cartDAO.selectGoodsList(myCartList.stream().map(CartVO::getGoods_id).toList());
    cartMap.put("myCartList", myCartList);
    cartMap.put("myGoodsList", myGoodsList);
    return cartMap;
  }

  public boolean findCartGoods(CartVO cartVO) {
    return cartDAO.selectCountInCart(cartVO);
  }

  public void addGoodsInCart(CartVO cartVO) {
    cartDAO.insertGoodsInCart(cartVO);
  }

  public boolean modifyCartQty(CartVO cartVO) {
    boolean result = true;
    cartDAO.updateCartGoodsQty(cartVO);
    return result;
  }

  public void removeCartGoods(int cartId) {
    cartDAO.deleteCartGoods(cartId);
  }
}
