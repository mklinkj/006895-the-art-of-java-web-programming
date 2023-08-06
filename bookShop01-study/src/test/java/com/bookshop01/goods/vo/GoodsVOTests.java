package com.bookshop01.goods.vo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GoodsVOTests {
  GoodsVO goodsVO;

  @BeforeEach
  void beforeEach() {
    goodsVO = new GoodsVO();
  }

  @DisplayName("snake case 필드도 Lombok 예상 대로 잘 처리 된다.")
  @Test
  void test() {
    goodsVO.getGoods_id();
    goodsVO.getGoods_title();
    goodsVO.getGoods_publisher_comment();
  }
}
