package com.bookshop01.admin.goods.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.bookshop01.common.util.DateUtils;
import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;
import com.bookshop01.order.vo.OrderVO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringJUnitConfig(locations = {"classpath:config/spring/root-context.xml"})
class AdminGoodsDAOTests {

  private static final int TEST_GOODS_ID = 10;

  @Autowired AdminGoodsDAO adminGoodsDAO;

  @Transactional
  @Test
  void insertNewGoods() {
    Map<String, Object> newGoodsMap = new HashMap<>();
    // 상품 번호
    // "goods_id"는 시퀀스로 자동 생성된다.
    // 상품 종류
    newGoodsMap.put("goods_sort", "컴퓨터와 인터넷");
    // 상품 제목
    newGoodsMap.put("goods_title", "스프링 6 & Boot 3 뽀개기");

    // 저자 이름
    newGoodsMap.put("goods_writer", "백앤드 개발자");

    // 출판사 이름
    newGoodsMap.put("goods_publisher", "LearnPub");

    // 상품 정가
    newGoodsMap.put("goods_price", 35_000);

    // 상품 판매 가격
    newGoodsMap.put("goods_sales_price", 32_000);

    // 상품 포인트
    newGoodsMap.put("goods_point", 3200);

    // 상품 출판일
    newGoodsMap.put(
        "goods_published_date", DateUtils.toDate(LocalDateTime.of(2023, 8, 1, 0, 0, 0)));

    // 상품 총 페이지 수
    newGoodsMap.put("goods_total_page", 800);

    // ISBN 번호
    newGoodsMap.put("goods_isbn", "1234567890");

    // 배송비
    newGoodsMap.put("goods_delivery_price", 2_500);

    // 상품 배송일
    newGoodsMap.put(
        "goods_delivery_date", DateUtils.toDate(LocalDateTime.of(2023, 8, 30, 0, 0, 0)));

    // 상품 분류
    // on_sale
    // steadyseller
    // bestseller
    // newbook
    newGoodsMap.put("goods_status", "newbook");

    // 저자 소개
    newGoodsMap.put("goods_writer_intro", "백앤드 개발자");

    // 상품 소개
    newGoodsMap.put("goods_intro", "스프링 6와 스프링 부트 3을 학습 합니다.");
    // 출판사 평
    newGoodsMap.put("goods_publisher_comment", "좋은 책이에요. 😅");
    // 상품 추천사
    newGoodsMap.put("goods_recommendation", "기초 부터 중급 내용까지 커버해줍니다.");
    // 상품 목차
    newGoodsMap.put(
        "goods_contents_order",
        """
        1. 스프링 6
        2. 미니 프로젝트 01
        3. 스프링 부트
        4. 미니 프로젝트 02
        5. 최종 프로젝트
      """);

    int goodsId = adminGoodsDAO.insertNewGoods(newGoodsMap);

    // 시퀀스 생성시 설정한 시작 값이 400부터 이긴함.
    assertThat(goodsId).isGreaterThanOrEqualTo(400);
  }

  @Transactional
  @Test
  void insertGoodsImageFile() {
    // 상품 ID의 300번 미만은 미사용 이므로 테스트 목적으로 쓰자.
    List<ImageFileVO> fileList = new ArrayList<>();

    ImageFileVO imageFile01 = new ImageFileVO();
    // image_id는 시퀀스로 자동생성
    imageFile01.setGoods_id(TEST_GOODS_ID);
    imageFile01.setFileName("스프링6_뽀개기_메인.jpg");
    imageFile01.setFileType("main_image");
    imageFile01.setReg_id("admin");
    fileList.add(imageFile01);

    ImageFileVO imageFile02 = new ImageFileVO();
    // image_id는 시퀀스로 자동생성
    imageFile02.setGoods_id(TEST_GOODS_ID);
    imageFile02.setFileName("스프링6_뽀개기_상세1.jpg");
    imageFile02.setFileType("detail_image1");
    imageFile02.setReg_id("admin");
    fileList.add(imageFile02);

    ImageFileVO imageFile03 = new ImageFileVO();
    // image_id는 시퀀스로 자동생성
    imageFile03.setGoods_id(TEST_GOODS_ID);
    imageFile03.setFileName("스프링6_뽀개기_상세2.jpg");
    imageFile03.setFileType("uploadFile");
    imageFile03.setReg_id("admin");
    fileList.add(imageFile03);

    adminGoodsDAO.insertGoodsImageFile(fileList);
  }

  @Test
  void selectNewGoodsList() {
    Map<String, Object> condMap = new HashMap<>();
    // 상품 생성 기준 시작 일자
    condMap.put("beginDate", "2018-10-02");
    // 상품 생성 기준 종료 일자
    condMap.put("endDate", "2018-10-18");

    // 기본 상태가 상품자체가 몇페이지가 될 정도는 아니여서, 1페이지로 봐보자.
    condMap.put("section", 1);
    condMap.put("pageNum", 1);

    List<GoodsVO> result = adminGoodsDAO.selectNewGoodsList(condMap);

    assertThat(result).isNotEmpty();
  }

  @Test
  void selectGoodsDetail() {
    GoodsVO result = adminGoodsDAO.selectGoodsDetail(397);
    assertThat(result.getGoods_id()).isEqualTo(397);
  }

  @Test
  void selectGoodsImageFileList() {
    int goodsId = 397;
    List<ImageFileVO> result = adminGoodsDAO.selectGoodsImageFileList(goodsId);
    assertThat(result).isNotEmpty();
  }

  /*
    TODO: DB 컬럼을 바뀐 뒤 코드가 고쳐지지 않은 부분들이 보인 부분.
          수정은 했다. 상위 레이어에도 안맞는 부분 있으면 고쳐야할 듯...😓
  */
  @Transactional
  @Test
  void updateGoodsInfo() {
    Map<String, Object> goodsMap = new HashMap<>();

    // 상품 번호 (수정 할 때, 기준 조건)
    goodsMap.put("goods_id", "400");
    // 상품 종류
    goodsMap.put("goods_sort", "컴퓨터와 인터넷");
    // 상품 제목
    goodsMap.put("goods_title", "스프링 6 & Boot 3 뽀개기");

    // 저자 이름
    goodsMap.put("goods_writer", "백앤드 개발자");

    // 출판사 이름
    goodsMap.put("goods_publisher", "LearnPub");

    // 상품 정가
    goodsMap.put("goods_price", 35_000);

    // 상품 출판일
    goodsMap.put("goods_published_date", DateUtils.toDate(LocalDateTime.of(2023, 8, 1, 0, 0, 0)));

    // 상품 판매 가격
    goodsMap.put("goods_sales_price", 32_000);

    // 상품 포인트
    goodsMap.put("goods_point", 3200);

    // 상품 총 페이지 수
    goodsMap.put("goods_total_page", 800);

    // ISBN 번호
    goodsMap.put("goods_isbn", "1234567890");

    // 배송비
    goodsMap.put("goods_delivery_price", 2_500);

    // 상품 배송일
    goodsMap.put("goods_delivery_date", DateUtils.toDate(LocalDateTime.of(2023, 8, 30, 0, 0, 0)));

    // 상품 분류
    // on_sale
    // steadyseller
    // bestseller
    // newbook
    goodsMap.put("goods_status", "newbook");

    // 저자 소개
    goodsMap.put("goods_writer_intro", "백앤드 개발자");

    // 상품 소개
    goodsMap.put("goods_intro", "스프링 6와 스프링 부트 3을 학습 합니다.");

    // 출판사 평
    goodsMap.put("goods_publisher_comment", "좋은 책이에요. 😅");

    // 상품 추천사
    goodsMap.put("goods_recommendation", "기초 부터 중급 내용까지 커버해줍니다.");

    // 상품 목차
    goodsMap.put(
        "goods_contents_order",
        """
        1. 스프링 6
        2. 미니 프로젝트 01
        3. 스프링 부트
        4. 미니 프로젝트 02
        5. 최종 프로젝트
      """);

    adminGoodsDAO.updateGoodsInfo(goodsMap);
  }

  @Transactional
  @Test
  void deleteGoodsImage() {
    int imageId = 400;
    adminGoodsDAO.deleteGoodsImage(imageId);
  }

  /*
   TODO:
     여기도 컬럼이 잘못된 부분이 있었음.
     t_shopping_order 테이블에 orderer_id 라는 컬럼은 없는데...😓
     아마도 member_id를 잘못 사용한 것으로 보임.
  */
  @Test
  void selectOrderGoodsList() {

    Map<String, Object> condMap = new HashMap<>();

    condMap.put("beginDate", "2018-10-22");
    condMap.put("endDate", "2018-10-23");

    condMap.put("search_type", "orderer_id");
    condMap.put("search_word", "lee");

    condMap.put("section", 1);
    condMap.put("pageNum", 1);

    List<OrderVO> result = adminGoodsDAO.selectOrderGoodsList(condMap);

    assertThat(result).hasSize(2);
  }

  @Transactional
  @Test
  void updateOrderGoods() {
    Map<String, Object> orderMap = new HashMap<>();

    // 배송 상태
    // 주문 취소(cancel_order), 배송중 (delivering) ...
    orderMap.put("delivery_state", "delivering");
    orderMap.put(
        "delivery_address",
        "우편번호:13547<br>도로명 주소:경기 성남시 분당구 고기로 25 (동원동)<br>[지번 주소:경기 성남시 분당구 동원동 79-1]<br>럭키빌딩 101호");

    orderMap.put("order_id", "92");
    adminGoodsDAO.updateOrderGoods(orderMap);
  }

  /*
   상품ID와 이미지ID의 기준조건으로 파일 이름만 업데이트함.
   TODO:
     입력은 상품이미지VO List를 받는데, 쿼리단에서는 단일 처리임.
     뭔가 이상함..
     List를 매퍼로 전달해도 INSERT 처럼 쿼리를 한번에 수행할 수 없을 텐데?
     이 부분은 기존 쿼리는 그대로 두고, 상위에서 반복 호출하는 방식으로 수정해야겠다.
  */
  @Transactional
  @Test
  void updateGoodsImage() {
    ImageFileVO imageFile = new ImageFileVO();
    imageFile.setImage_id(305);
    imageFile.setGoods_id(337);
    imageFile.setFileName("케라시_메인_v2.jpg");

    adminGoodsDAO.updateGoodsImage(imageFile);
  }
}
