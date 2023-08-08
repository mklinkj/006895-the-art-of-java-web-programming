package com.bookshop01.mypage.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.bookshop01.member.vo.MemberVO;
import com.bookshop01.order.vo.OrderVO;
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
class MyPageDAOImplTests {

  @Autowired private MyPageDAO myPageDAO;

  /*
   회원 ID로 모든 주문 정보 조회
  */
  @Test
  void selectMyOrderGoodsList() {
    String memberId = "lee";
    List<OrderVO> result = myPageDAO.selectMyOrderGoodsList(memberId);
    assertThat(result).isNotEmpty();
  }

  /*
    주문 아이디로 주문정보 조회
    주문 아아디 한개에 여러 상품 주문이 포함될 수 있다. 그러니 결과가 다수가 될 수 있음.
  */
  @Test
  void selectMyOrderInfo() {
    int orderId = 92;
    List<OrderVO> result = myPageDAO.selectMyOrderInfo(orderId);
    assertThat(result).isNotEmpty();
  }

  /*
    selectMyOrderGoodsList 와는 거의 같은 쿼리인데 차이는...
    주문일자 조건이 붙음.
  */
  @Test
  void selectMyOrderHistoryList() {
    Map<String, String> dateMap = new HashMap<>();
    dateMap.put("member_id", "lee");
    dateMap.put("beginDate", "2018-10-22");
    dateMap.put("endDate", "2018-10-23");
    List<OrderVO> result = myPageDAO.selectMyOrderHistoryList(dateMap);
    assertThat(result).isNotEmpty();
  }

  @Transactional
  @Test
  void updateMyInfo() {
    Map<String, String> memberMap = new HashMap<>();
    // 업데이트 기준 속성 : 회원 아이디
    memberMap.put("member_id", "lee");

    memberMap.put("member_pw", "1234");

    memberMap.put("member_birth_y", "2000");
    memberMap.put("member_birth_m", "2");
    memberMap.put("member_birth_d", "10");

    memberMap.put("tel1", "02");
    memberMap.put("tel2", "5555");
    memberMap.put("tel3", "7777");

    memberMap.put("hp1", "010");
    memberMap.put("hp2", "8888");
    memberMap.put("hp3", "9999");

    memberMap.put("smssts_yn", "N");

    memberMap.put("email1", "test");
    memberMap.put("email2", "test.com");
    memberMap.put("emailsts_yn", "Y");

    memberMap.put("zipcode", "13524");
    memberMap.put("roadAddress", "경기 성남시 분당구 대왕판교로606번길 45 (삼평동)");
    memberMap.put("jibunAddress", "경기 성남시 분당구 삼평동 653");
    memberMap.put("namujiAddress", "테스트 나머지 주소");

    myPageDAO.updateMyInfo(memberMap);
  }

  /*
    회원의 상세 정보 조회
  */
  @Test
  void selectMyDetailInfo() {
    String memberId = "lee";
    MemberVO result = myPageDAO.selectMyDetailInfo(memberId);

    assertThat(result) //
        .isNotNull()
        .hasFieldOrPropertyWithValue("member_id", "lee");
  }

  /*
    특정 주문의 주문 상태를 취소로 바꿈.
  */
  @Transactional
  @Test
  void updateMyOrderCancel() {
    int orderId = 92;
    myPageDAO.updateMyOrderCancel(orderId);
  }
}
