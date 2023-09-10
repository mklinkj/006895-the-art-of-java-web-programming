package com.bookshop01.admin.order.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.bookshop01.admin.common.pagination.PageRequest;
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
class AdminOrderDAOImplTests {

  @Autowired private AdminOrderDAO adminOrderDAO;

  @Test
  void selectNewOrderList() {
    Map<String, Object> condMap = new HashMap<>();

    condMap.put("beginDate", "2018-10-22");
    condMap.put("endDate", "2018-10-24");

    condMap.put("search_type", "orderer_id");
    condMap.put("search_word", "lee");

    PageRequest pageRequest = PageRequest.builder().page(1).size(1).build();

    List<OrderVO> result = adminOrderDAO.selectNewOrderList(pageRequest, condMap);

    assertThat(result).isNotEmpty();
  }

  @Test
  void countOrder() {
    Map<String, Object> condMap = new HashMap<>();

    condMap.put("beginDate", "2018-10-22");
    condMap.put("endDate", "2018-10-24");

    condMap.put("search_type", "orderer_id");
    condMap.put("search_word", "lee");

    long result = adminOrderDAO.countOrder(condMap);

    assertThat(result).isGreaterThan(0);
  }

  @Transactional
  @Test
  void updateDeliveryState() {
    Map<String, Object> deliveryMap = new HashMap<>();
    deliveryMap.put("order_id", 93);
    deliveryMap.put("delivery_state", "cancel_order");

    adminOrderDAO.updateDeliveryState(deliveryMap);
  }

  @Test
  void selectOrderDetail() {
    final int orderId = 92;
    /*
     TODO: 주문 테이블에서 ORDER_ID가 유일한 행으로 처리되는 줄 알았는데, 그렇지는 않은가보다.
       주문 ID로 조회한 값이 여러 행이 될 수 있음. 이 부분은 진행하면서 확인해보자!
       해당 태이블의 기본키는 시퀀스로 얻는 ORDER_SEQ_NUM 이다.
    */
    List<OrderVO> result = adminOrderDAO.selectOrderDetail(orderId);
    assertThat(result).isNotEmpty();
  }

  @Test
  void selectOrderer() {
    String memberId = "lee";
    MemberVO result = adminOrderDAO.selectOrderer(memberId);
    assertThat(result).hasFieldOrPropertyWithValue("member_id", "lee");
  }
}
