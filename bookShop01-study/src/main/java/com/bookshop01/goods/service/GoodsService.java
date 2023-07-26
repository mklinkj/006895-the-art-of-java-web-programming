package com.bookshop01.goods.service;

import com.bookshop01.goods.vo.GoodsVO;
import java.util.List;
import java.util.Map;

public interface GoodsService {

  Map<String, List<GoodsVO>> listGoods() throws Exception;

  Map goodsDetail(String _goods_id) throws Exception;

  List<String> keywordSearch(String keyword) throws Exception;

  List<GoodsVO> searchGoods(String searchWord) throws Exception;
}
