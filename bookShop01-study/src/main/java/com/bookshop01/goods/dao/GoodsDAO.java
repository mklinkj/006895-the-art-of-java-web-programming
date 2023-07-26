package com.bookshop01.goods.dao;

import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface GoodsDAO {
  List<GoodsVO> selectGoodsList(String goodsStatus) throws DataAccessException;

  List<String> selectKeywordSearch(String keyword) throws DataAccessException;

  GoodsVO selectGoodsDetail(String goods_id) throws DataAccessException;

  List<ImageFileVO> selectGoodsDetailImage(String goods_id) throws DataAccessException;

  List<GoodsVO> selectGoodsBySearchWord(String searchWord) throws DataAccessException;
}
