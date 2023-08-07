package com.bookshop01.goods.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Slf4j
@SpringJUnitConfig(locations = {"classpath:config/spring/root-context.xml"})
class GoodsDAOImplTests {

  @Autowired private GoodsDAO goodsDAO;

  /*
   상품과 상세 이미지를 조인해서 최대 15개 까지만 등록기준 내림차순으로 가져오는데,
   조인 조건에 상세이미자가 main_image인 조건이 있음.
  */
  @Test
  void selectGoodsList() {
    String goodsStatus = "bestseller";
    List<GoodsVO> result = goodsDAO.selectGoodsList(goodsStatus);
    assertThat(result).isNotEmpty();
  }

  /*
    책 제목을 기준으로 키워드 검색, - 제목만 가져옴.
  */
  @Test
  void selectKeywordSearch() {
    String keyword = "자바";
    List<String> result = goodsDAO.selectKeywordSearch(keyword);

    assertThat(result).isNotEmpty();
    result.forEach(title -> assertThat(title).contains("자바"));
  }

  /*
    키워드로 책 제목을 검색하는데, 결과를 상품 정보로 받음.
  */
  @Test
  void selectGoodsBySearchWord() {
    String keyword = "자바";
    List<GoodsVO> result = goodsDAO.selectGoodsBySearchWord(keyword);

    assertThat(result).isNotEmpty();
    result.forEach(goodsVO -> assertThat(goodsVO.getGoods_title()).contains("자바"));
  }

  /*
    상품 상세 조회
  */
  @Test
  void selectGoodsDetail() {
    int goodsId = 397;
    GoodsVO result = goodsDAO.selectGoodsDetail(goodsId);
    assertThat(result) //
        .hasFieldOrPropertyWithValue("goods_title", "리액트를 다루는 기술");
  }

  /*
    상품 상세 이미지 조회
    특정 상품의 메인 이미지 (main_image)가 아닌 이미지 목록을 가져옴
  */
  @Test
  void selectGoodsDetailImage() {
    int goodsId = 397;
    List<ImageFileVO> result = goodsDAO.selectGoodsDetailImage(goodsId);
    assertThat(result).isNotEmpty();

    result.forEach(imageFileVO -> assertThat(imageFileVO.getFileType()).isNotEqualTo("main_image"));
  }
}
