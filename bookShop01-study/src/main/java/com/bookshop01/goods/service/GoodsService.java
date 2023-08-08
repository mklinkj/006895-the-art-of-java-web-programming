package com.bookshop01.goods.service;

import com.bookshop01.goods.vo.GoodsVO;
import java.util.List;
import java.util.Map;

public interface GoodsService {

  Map<String, List<GoodsVO>> listGoods();

  Map<String, ?> goodsDetail(Integer goodsId);

  List<String> keywordSearch(String keyword);

  List<GoodsVO> searchGoods(String searchWord);
}
