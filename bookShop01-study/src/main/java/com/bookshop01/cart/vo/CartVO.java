package com.bookshop01.cart.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartVO {
  /** 상품 번호 */
  private int goods_id;

  /** 회원 아이디 */
  private String member_id;

  /** 장바구니 번호 */
  private int cart_id;

  /** 상품 개수 */
  private int cart_goods_qty;

  /** TODO: 장바구니 생성 일자 맞을까? */
  private String creDate;
}
