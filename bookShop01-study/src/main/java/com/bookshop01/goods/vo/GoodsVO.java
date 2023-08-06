package com.bookshop01.goods.vo;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoodsVO {
  private int goods_id;
  private String goods_sort;
  private String goods_title;

  private String goods_writer;
  private String goods_publisher;
  private int goods_price;

  private int goods_sales_price;
  private int goods_point;
  private Date goods_published_date;

  private int goods_total_page;
  private String goods_isbn;
  private String goods_delivery_price;

  private Date goods_delivery_date;
  private String goods_status;
  private String goods_intro;

  private String goods_writer_intro;
  private String goods_publisher_comment;
  private String goods_recommendation;

  private String goods_contents_order;
  private Date goods_credate;

  // T_SHOPPING_GOODS 테이블에 컬럼으로 있지 않음.
  private String goods_fileName;
}
