package com.bookshop01.admin.goods.dao;

import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;
import com.bookshop01.order.vo.OrderVO;
import java.util.List;
import java.util.Map;

public interface AdminGoodsDAO {
  int insertNewGoods(Map<String, ?> newGoodsMap);

  List<GoodsVO> selectNewGoodsList(Map<String, ?> condMap);

  GoodsVO selectGoodsDetail(int goodsId);

  List<ImageFileVO> selectGoodsImageFileList(int goodsId);

  void insertGoodsImageFile(List<ImageFileVO> fileList);

  void updateGoodsInfo(Map<String, ?> goodsMap);

  void updateGoodsImage(ImageFileVO imageFileList);

  void deleteGoodsImage(int imageId);

  void deleteGoodsImage(List<ImageFileVO> fileList);

  List<OrderVO> selectOrderGoodsList(Map<String, ?> condMap);

  void updateOrderGoods(Map<String, ?> orderMap);
}
