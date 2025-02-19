package com.bookshop01.admin.order.service;

import com.bookshop01.common.pagination.PageRequest;
import com.bookshop01.common.pagination.PageResponse;
import com.bookshop01.admin.order.dao.AdminOrderDAO;
import com.bookshop01.member.vo.MemberVO;
import com.bookshop01.order.vo.OrderVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AdminOrderServiceImpl implements AdminOrderService {
  private final AdminOrderDAO adminOrderDAO;

  @Transactional(readOnly = true)
  public PageResponse<OrderVO> listNewOrder(PageRequest pageRequest, Map<String, ?> condMap) {
    List<OrderVO> list = adminOrderDAO.selectNewOrderList(pageRequest, condMap);

    return PageResponse.<OrderVO>withAll()
        .content(list)
        .pageRequest(pageRequest)
        .total(adminOrderDAO.countOrder(condMap))
        .build();
  }

  @Transactional
  @Override
  public void modifyDeliveryState(Map<String, ?> deliveryMap) {
    adminOrderDAO.updateDeliveryState(deliveryMap);
  }

  @Transactional(readOnly = true)
  @Override
  public Map<String, ?> orderDetail(Integer orderId) {
    Map<String, Object> orderMap = new HashMap<>();
    List<OrderVO> orderList = adminOrderDAO.selectOrderDetail(orderId);
    OrderVO deliveryInfo = orderList.get(0);
    String memberId = deliveryInfo.getMember_id();
    MemberVO orderer = adminOrderDAO.selectOrderer(memberId);
    orderMap.put("orderList", orderList);
    orderMap.put("deliveryInfo", deliveryInfo);
    orderMap.put("orderer", orderer);
    return orderMap;
  }
}
