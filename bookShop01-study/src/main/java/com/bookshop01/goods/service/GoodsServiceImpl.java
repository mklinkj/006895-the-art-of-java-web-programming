package com.bookshop01.goods.service;

import com.bookshop01.goods.dao.GoodsDAO;
import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class GoodsServiceImpl implements GoodsService {
  private final GoodsDAO goodsDAO;

  public Map<String, List<GoodsVO>> listGoods() {
    Map<String, List<GoodsVO>> goodsMap = new HashMap<>();
    List<GoodsVO> goodsList = goodsDAO.selectGoodsList("bestseller");
    goodsMap.put("bestseller", goodsList);
    goodsList = goodsDAO.selectGoodsList("newbook");
    goodsMap.put("newbook", goodsList);

    goodsList = goodsDAO.selectGoodsList("steadyseller");
    goodsMap.put("steadyseller", goodsList);
    return goodsMap;
  }

  public Map<String, ?> goodsDetail(Integer goodsId) throws Exception {
    Map<String, Object> goodsMap = new HashMap<>();
    GoodsVO goodsVO = goodsDAO.selectGoodsDetail(goodsId);
    goodsMap.put("goodsVO", goodsVO);
    List<ImageFileVO> imageList = goodsDAO.selectGoodsDetailImage(goodsId);
    goodsMap.put("imageList", imageList);
    return goodsMap;
  }

  public List<String> keywordSearch(String keyword) {
    return goodsDAO.selectKeywordSearch(keyword);
  }

  public List<GoodsVO> searchGoods(String searchWord) {
    return goodsDAO.selectGoodsBySearchWord(searchWord);
  }
}
