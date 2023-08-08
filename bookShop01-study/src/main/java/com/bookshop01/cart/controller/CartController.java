package com.bookshop01.cart.controller;

import com.bookshop01.cart.service.CartService;
import com.bookshop01.cart.vo.CartVO;
import com.bookshop01.common.base.BaseController;
import com.bookshop01.member.vo.MemberVO;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/cart")
public class CartController extends BaseController {
  private final CartService cartService;

  @RequestMapping(value = "/myCartList.do", method = RequestMethod.GET)
  public ModelAndView myCartMain(HttpServletRequest request) {
    String viewName = (String) request.getAttribute("viewName");
    ModelAndView mav = new ModelAndView(viewName);
    HttpSession session = request.getSession();
    MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
    String member_id = memberVO.getMember_id();

    CartVO cartVO = new CartVO();
    cartVO.setMember_id(member_id);
    Map<String, List<?>> cartMap = cartService.myCartList(cartVO);
    session.setAttribute("cartMap", cartMap); // 장바구니 목록 화면에서 상품 주문 시 사용하기 위해서 장바구니 목록을 세션에 저장한다.
    // mav.addObject("cartMap", cartMap);
    return mav;
  }

  @RequestMapping(
      value = "/addGoodsInCart.do",
      method = RequestMethod.POST,
      produces = "application/text; charset=utf8")
  @ResponseBody
  public String addGoodsInCart(@RequestParam("goods_id") int goods_id, HttpServletRequest request) {
    HttpSession session = request.getSession();
    MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
    if (memberVO == null) {
      return "required_login";
    }

    String member_id = memberVO.getMember_id();
    CartVO cartVO = new CartVO();
    cartVO.setMember_id(member_id);
    // 카트 등록전에 이미 등록된 제품인지 판별한다.
    cartVO.setGoods_id(goods_id);
    cartVO.setMember_id(member_id);
    boolean isAlreadyExisted = cartService.findCartGoods(cartVO);
    LOGGER.info("isAlreadyExisted: ,{}", isAlreadyExisted);
    if (isAlreadyExisted) {
      return "already_existed";
    } else {
      cartService.addGoodsInCart(cartVO);
      return "add_success";
    }
  }

  @RequestMapping(value = "/modifyCartQty.do", method = RequestMethod.POST)
  public @ResponseBody String modifyCartQty(
      @RequestParam("goods_id") int goods_id,
      @RequestParam("cart_goods_qty") int cart_goods_qty,
      HttpServletRequest request) {
    HttpSession session = request.getSession();
    MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
    String member_id = memberVO.getMember_id();

    CartVO cartVO = new CartVO();
    cartVO.setGoods_id(goods_id);
    cartVO.setMember_id(member_id);
    cartVO.setCart_goods_qty(cart_goods_qty);
    boolean result = cartService.modifyCartQty(cartVO);

    if (result) {
      return "modify_success";
    } else {
      return "modify_failed";
    }
  }

  @RequestMapping(value = "/removeCartGoods.do", method = RequestMethod.POST)
  public ModelAndView removeCartGoods(@RequestParam("cart_id") int cartId) {
    ModelAndView mav = new ModelAndView();
    cartService.removeCartGoods(cartId);
    mav.setViewName("redirect:/cart/myCartList.do");
    return mav;
  }
}
