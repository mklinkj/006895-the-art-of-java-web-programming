package com.bookshop01.goods.dao;

import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;
import java.util.List;

public interface GoodsDAO {
  List<GoodsVO> selectGoodsList(String goodsStatus);

  List<String> selectKeywordSearch(String keyword);

  GoodsVO selectGoodsDetail(Integer goodsId);

  List<ImageFileVO> selectGoodsDetailImage(Integer goodsId);

  List<GoodsVO> selectGoodsBySearchWord(String searchWord);
}
