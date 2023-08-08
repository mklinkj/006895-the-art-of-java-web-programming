package com.bookshop01.order.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 주문 VO
 *
 * <p>DB 필드가 아닌 것들이 섞여 있어서 좀 해깔린다. 😅 DAO부터 확인하면서, 확실해지면 주석을 바꿔두자.
 */
@Getter
@Setter
@ToString
public class OrderVO {
  // ==================================================
  /*
  TODO: 주문 테이블에 없는 필드
        대부분 서비스에서 처리해서 넣어주거나, 미사용 필드일 경우 같다.
        점진적으로 처리하자!
  */

  /** 최종 주문 수량 */
  private int total_goods_price;

  private int cart_goods_qty; // 장바구니에 담긴 제품 수, TODO: 장바구니 테이블에 있다.

  private String final_total_price;

  private int goods_qty;

  // ==================================================

  /** 주문상품 일련번호: 테이블 ROW 고유 ID사용 목적 : order_seq_num 시퀀스를 사용 */
  private int order_seq_num;

  /**
   * 주문번호 : 별도 시퀀스로 따로 얻음. : seq_order_id 시퀀스를 사용 <br>
   * 주문번호 하나에 여러 개의 상품이 포함될 수 있으므로, 이러한 처리가 들어같 것 같다.
   */
  private int order_id;

  /** 주문자 ID */
  private String member_id;

  /** 상품 번호 */
  private int goods_id;

  /** 주문자 이름 */
  private String orderer_name;

  /** 상품 이름 */
  private String goods_title;

  /** 주문 수량 */
  private int order_goods_qty; // 최종 주문  제품 수

  /** 상품 판매 가격 */
  private int goods_sales_price;

  /** 상품 이미지 파일 이름 */
  private String goods_fileName;

  /** 수령자 이름 */
  private String receiver_name;

  /** 수령자 핸드폰 번호 1 */
  private String receiver_hp1;
  /** 수령자 핸드폰 번호 2 */
  private String receiver_hp2;
  /** 수령자 핸드폰 번호 3 */
  private String receiver_hp3;

  /** 수령자 유선 전화번호 1 */
  private String receiver_tel1;
  /** 수령자 유선 전화번호 2 */
  private String receiver_tel2;
  /** 수령자 유선 전화번호 3 */
  private String receiver_tel3;

  /** 배송 주소 */
  private String delivery_address;
  /** 배송 방법 */
  private String delivery_method;
  /** 부재 시 전달 메시지 */
  private String delivery_message;

  /** 상품 포장 여부 */
  private String gift_wrapping;

  /** 결제 방법 */
  private String pay_method;

  /** 결제 카드 회사 이름 */
  private String card_com_name;

  /** 할부개월수 */
  private String card_pay_month;

  /** 주문자 휴대폰 번호 */
  private String orderer_hp;

  /** 휴대폰 결제 번호 */
  private String pay_orderer_hp_num;

  /** 상품 배송 상태 */
  private String delivery_state;

  /** 결제 시간 */
  private String pay_order_time;
}
