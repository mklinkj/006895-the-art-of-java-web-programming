package com.bookshop01.goods.controller;

import static com.bookshop01.common.util.ObjectMapperHelper.objectMapper;

import com.bookshop01.common.base.BaseController;
import com.bookshop01.goods.service.GoodsService;
import com.bookshop01.goods.vo.GoodsVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping(value = "/goods")
public class GoodsController extends BaseController {
  private final GoodsService goodsService;

  @RequestMapping(value = "/goodsDetail.do", method = RequestMethod.GET)
  public void goodsDetail(
      @RequestParam("goods_id") String goodsId, HttpServletRequest request, Model model)
      throws Exception {
    HttpSession session = request.getSession();
    Map<String, ?> goodsMap = goodsService.goodsDetail(Integer.valueOf(goodsId));
    model.addAttribute("goodsMap", goodsMap);
    GoodsVO goodsVO = (GoodsVO) goodsMap.get("goodsVO");
    addGoodsInQuick(goodsId, goodsVO, session);
  }

  @RequestMapping(
      value = "/keywordSearch.do",
      method = RequestMethod.GET,
      produces = "application/text; charset=utf8")
  @ResponseBody
  public String keywordSearch(@RequestParam("keyword") String keyword) throws Exception {
    if (keyword == null || keyword.isEmpty()) {
      return null;
    }

    keyword = keyword.toUpperCase();
    List<String> keywordList = goodsService.keywordSearch(keyword);

    String jsonInfo = objectMapper().writeValueAsString(Map.of("keyword", keywordList));
    LOGGER.info("jsonInfo: {}", jsonInfo);
    return jsonInfo;
  }

  @RequestMapping(value = "/searchGoods.do", method = RequestMethod.GET)
  public void searchGoods(@RequestParam("searchWord") String searchWord, Model model) {
    List<GoodsVO> goodsList = goodsService.searchGoods(searchWord);
    model.addAttribute("goodsList", goodsList);
  }

  private void addGoodsInQuick(String goods_id, GoodsVO goodsVO, HttpSession session) {
    boolean alreadyExisted = false;
    List<GoodsVO> quickGoodsList; // 최근 본 상품 저장 ArrayList
    quickGoodsList = (List<GoodsVO>) session.getAttribute("quickGoodsList");

    if (quickGoodsList != null) {
      if (quickGoodsList.size() < 4) { // 미리본 상품 리스트에 상품개수가 세개 이하인 경우
        for (int i = 0; i < quickGoodsList.size(); i++) {
          GoodsVO _goodsBean = quickGoodsList.get(i);
          if (goods_id.equals(_goodsBean.getGoods_id())) {
            alreadyExisted = true;
            break;
          }
        }
        if (!alreadyExisted) {
          quickGoodsList.add(goodsVO);
        }
      }

    } else {
      quickGoodsList = new ArrayList<>();
      quickGoodsList.add(goodsVO);
    }
    session.setAttribute("quickGoodsList", quickGoodsList);
    session.setAttribute("quickGoodsListNum", quickGoodsList.size());
  }
}
