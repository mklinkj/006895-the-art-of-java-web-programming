package com.bookshop01.admin.goods.dao;

import com.bookshop01.common.pagination.PageRequest;
import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;
import java.util.List;
import java.util.Map;

public interface AdminGoodsDAO {
  int insertNewGoods(Map<String, ?> newGoodsMap);

  List<GoodsVO> selectNewGoodsList(PageRequest pageRequest, Map<String, ?> condMap);

  long countNewGoods(Map<String, ?> condMap);

  GoodsVO selectGoodsDetail(int goodsId);

  List<ImageFileVO> selectGoodsImageFileList(int goodsId);

  void insertGoodsImageFile(List<ImageFileVO> fileList);

  void updateGoodsInfo(Map<String, ?> goodsMap);

  void updateGoodsImage(ImageFileVO imageFileList);

  void deleteGoodsImage(int imageId);

  void deleteGoodsImage(List<ImageFileVO> fileList);

  void updateOrderGoods(Map<String, ?> orderMap);
}
