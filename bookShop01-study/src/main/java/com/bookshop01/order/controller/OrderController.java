package com.bookshop01.order.controller;

import com.bookshop01.common.base.BaseController;
import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.member.vo.MemberVO;
import com.bookshop01.order.service.OrderService;
import com.bookshop01.order.vo.OrderVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/order")
public class OrderController extends BaseController {
  private final OrderService orderService;

  @RequestMapping(value = "/orderEachGoods.do", method = RequestMethod.POST)
  public String orderEachGoods(
      @ModelAttribute("orderVO") OrderVO _orderVO, HttpServletRequest request, Model model) {

    HttpSession session = request.getSession();

    Boolean isLogOn = (Boolean) session.getAttribute("isLogOn");
    String action = (String) session.getAttribute("action");
    // 로그인 여부 체크
    // 이전에 로그인 상태인 경우는 주문과정 진행
    // 로그아웃 상태인 경우 로그인 화면으로 이동
    OrderVO orderVO;
    if (isLogOn == null || isLogOn == false) {
      session.setAttribute("orderInfo", _orderVO);
      session.setAttribute("action", "/order/orderEachGoods.do");
      return "redirect:/member/loginForm.do";
    } else {
      if (action != null && action.equals("/order/orderEachGoods.do")) {
        orderVO = (OrderVO) session.getAttribute("orderInfo");
        session.removeAttribute("action");
      } else {
        orderVO = _orderVO;
      }
    }

    List<OrderVO> myOrderList = new ArrayList<>();
    myOrderList.add(orderVO);

    MemberVO memberInfo = (MemberVO) session.getAttribute("memberInfo");

    session.setAttribute("myOrderList", myOrderList);
    session.setAttribute("orderer", memberInfo);
    // TODO: 확인 필요
    return "order/orderEachGoods";
  }

  @RequestMapping(value = "/orderAllCartGoods.do", method = RequestMethod.POST)
  public void orderAllCartGoods(
      @RequestParam("cart_goods_qty") String[] cartGoodsQty, HttpServletRequest request) {

    HttpSession session = request.getSession();
    Map cartMap = (Map) session.getAttribute("cartMap");
    List myOrderList = new ArrayList<OrderVO>();

    List<GoodsVO> myGoodsList = (List<GoodsVO>) cartMap.get("myGoodsList");
    MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");

    for (int i = 0; i < cartGoodsQty.length; i++) {
      String[] cart_goods = cartGoodsQty[i].split(":");
      for (int j = 0; j < myGoodsList.size(); j++) {
        GoodsVO goodsVO = myGoodsList.get(j);
        int goods_id = goodsVO.getGoods_id();
        if (goods_id == Integer.parseInt(cart_goods[0])) {
          OrderVO _orderVO = new OrderVO();
          String goods_title = goodsVO.getGoods_title();
          int goods_sales_price = goodsVO.getGoods_sales_price();
          String goods_fileName = goodsVO.getGoods_fileName();
          _orderVO.setGoods_id(goods_id);
          _orderVO.setGoods_title(goods_title);
          _orderVO.setGoods_sales_price(goods_sales_price);
          _orderVO.setGoods_fileName(goods_fileName);
          _orderVO.setOrder_goods_qty(Integer.parseInt(cart_goods[1]));
          myOrderList.add(_orderVO);
          break;
        }
      }
    }
    session.setAttribute("myOrderList", myOrderList);
    session.setAttribute("orderer", memberVO);
  }

  @RequestMapping(value = "/payToOrderGoods.do", method = RequestMethod.POST)
  public void payToOrderGoods(
      @RequestParam Map<String, String> receiverMap, HttpServletRequest request, Model model) {

    HttpSession session = request.getSession();
    MemberVO memberVO = (MemberVO) session.getAttribute("orderer");
    String member_id = memberVO.getMember_id();
    String orderer_name = memberVO.getMember_name();
    String orderer_hp = memberVO.getHp1() + "-" + memberVO.getHp2() + "-" + memberVO.getHp3();
    List<OrderVO> myOrderList = (List<OrderVO>) session.getAttribute("myOrderList");

    for (int i = 0; i < myOrderList.size(); i++) {
      OrderVO orderVO = myOrderList.get(i);
      orderVO.setMember_id(member_id);
      orderVO.setOrderer_name(orderer_name);
      orderVO.setReceiver_name(receiverMap.get("receiver_name"));

      orderVO.setReceiver_hp1(receiverMap.get("receiver_hp1"));
      orderVO.setReceiver_hp2(receiverMap.get("receiver_hp2"));
      orderVO.setReceiver_hp3(receiverMap.get("receiver_hp3"));
      orderVO.setReceiver_tel1(receiverMap.get("receiver_tel1"));
      orderVO.setReceiver_tel2(receiverMap.get("receiver_tel2"));
      orderVO.setReceiver_tel3(receiverMap.get("receiver_tel3"));

      orderVO.setDelivery_address(receiverMap.get("delivery_address"));
      orderVO.setDelivery_message(receiverMap.get("delivery_message"));
      orderVO.setDelivery_method(receiverMap.get("delivery_method"));
      orderVO.setGift_wrapping(receiverMap.get("gift_wrapping"));
      orderVO.setPay_method(receiverMap.get("pay_method"));
      orderVO.setCard_com_name(receiverMap.get("card_com_name"));
      orderVO.setCard_pay_month(receiverMap.get("card_pay_month"));
      orderVO.setPay_orderer_hp_num(receiverMap.get("pay_orderer_hp_num"));
      orderVO.setOrderer_hp(orderer_hp);
      myOrderList.set(i, orderVO); // 각 orderVO에 주문자 정보를 세팅한 후 다시 myOrderList에 저장한다.
    } // end for

    orderService.addNewOrder(myOrderList);
    model.addAttribute("myOrderInfo", receiverMap); // OrderVO로 주문결과 페이지에  주문자 정보를 표시한다.
    model.addAttribute("myOrderList", myOrderList);
  }
}
