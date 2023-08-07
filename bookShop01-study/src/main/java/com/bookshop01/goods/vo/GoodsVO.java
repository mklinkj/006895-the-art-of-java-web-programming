package com.bookshop01.goods.vo;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoodsVO {
  /** 상품 번호 */
  private int goods_id;
  /** 상품 종류 */
  private String goods_sort;
  /** 상품 제목 */
  private String goods_title;

  /** 저자 이름 */
  private String goods_writer;
  /** 출판사 이름 */
  private String goods_publisher;
  /** 상품 정가 */
  private int goods_price;
  /** 상품 판매 가격 */
  private int goods_sales_price;
  /** 상품 포인트 */
  private int goods_point;
  /** 상품 출판일 */
  private Date goods_published_date;

  /** 상품 총 페이지 수 */
  private int goods_total_page;
  /** isbn 번호 */
  private String goods_isbn;
  /** 배송비 */
  private String goods_delivery_price;

  /** 상품 배송일 */
  private Date goods_delivery_date;
  /** 상품 분류 */
  private String goods_status;
  /** 상품 소개 */
  private String goods_intro;

  /** 저자 소개 */
  private String goods_writer_intro;
  /** 출판사 평 */
  private String goods_publisher_comment;

  /** 상품 추천 사 */
  private String goods_recommendation;

  /** 상품 목차 */
  private String goods_contents_order;
  /** 상품 입고일 */
  private Date goods_credate;

  // T_SHOPPING_GOODS 테이블에 컬럼으로 있지 않음.
  private String goods_fileName;
}
