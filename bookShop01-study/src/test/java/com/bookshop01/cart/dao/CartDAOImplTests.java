package com.bookshop01.cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.bookshop01.cart.vo.CartVO;
import com.bookshop01.goods.vo.GoodsVO;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringJUnitConfig(locations = {"classpath:config/spring/root-context.xml"})
class CartDAOImplTests {

  @Autowired private CartDAO cartDAO;

  /*
   회원 ID로 장바구니 목록 조회
  */
  @Test
  void selectCartList() {
    String memberId = "lee";
    List<CartVO> result = cartDAO.selectCartList(memberId);
    assertThat(result).isNotNull();
  }

  /*
   상품 ID로 상품 정보 조회
   장바구니 목록을 출력할 때, 상품 정보를 함깨 보여주기 위해 사용하 것 같다.
  */
  @Test
  void selectGoodsList() {
    List<Integer> goodsIdList = List.of(397, 398, 334);
    List<GoodsVO> result = cartDAO.selectGoodsList(goodsIdList);
    assertThat(result).hasSize(3);
  }

  /*
   특정 상품을 장바구니에 가지고 있는지 조회
  */
  @Test
  void selectCountInCart() {
    CartVO cartVO = new CartVO();
    cartVO.setGoods_id(1);
    cartVO.setMember_id("lee");

    boolean result = cartDAO.selectCountInCart(cartVO);
    assertThat(result).isFalse();
  }

  /*
   장바구니에 상품 추가
  */
  @Transactional
  @Test
  void insertGoodsInCart() {
    CartVO cartVO = new CartVO();
    // cart_id는 DAO메서드에서 설정해준다.
    cartVO.setGoods_id(397);
    cartVO.setMember_id("lee");

    cartDAO.insertGoodsInCart(cartVO);
  }

  @Transactional
  @Test
  void updateCartGoodsQty() {
    CartVO cartVO = new CartVO();

    cartVO.setCart_goods_qty(10);
    cartVO.setMember_id("lee");
    cartVO.setGoods_id(397);

    cartDAO.updateCartGoodsQty(cartVO);
  }

  @Transactional
  @Test
  void deleteCartGoods() {
    int cartId = 2;
    cartDAO.deleteCartGoods(cartId);
  }
}
