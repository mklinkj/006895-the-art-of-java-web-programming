package com.bookshop01.goods.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ImageFileVO {
  /** 이미지 번호 */
  private int image_id;
  /** 상품 번호 */
  private int goods_id;
  /** 이미지 파일명 */
  private String fileName;
  /** 이미지 파일 종류 */
  private String fileType;
  /** 등록자 아이디 */
  private String reg_id;
}
