package com.bookshop01.main;

import com.bookshop01.common.base.BaseController;
import com.bookshop01.goods.service.GoodsService;
import com.bookshop01.goods.vo.GoodsVO;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@RequiredArgsConstructor
@Controller
@EnableAspectJAutoProxy
public class MainController extends BaseController {
  private final GoodsService goodsService;

  @RequestMapping(
      value = "/main/main.do",
      method = {RequestMethod.GET, RequestMethod.POST})
  public void main(Model model, HttpSession session) {
    session.setAttribute("side_menu", "user"); // TODO: 세션 사용하지 않게 수정해야함.

    Map<String, List<GoodsVO>> goodsMap = goodsService.listGoods();
    model.addAttribute("goodsMap", goodsMap);
  }
}
