package com.bookshop01.admin.goods.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.bookshop01.goods.vo.GoodsVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Slf4j
@SpringJUnitConfig(locations = {"classpath:config/spring/root-context.xml"})
class AdminGoodsDAOTests {

  @Autowired AdminGoodsDAO adminGoodsDAO;

  @Test
  void selectGoodsDetail() {
    GoodsVO result = adminGoodsDAO.selectGoodsDetail(397);
    assertThat(result.getGoods_id()).isEqualTo(397);
    LOGGER.info("### result: {}", result);
  }
}
