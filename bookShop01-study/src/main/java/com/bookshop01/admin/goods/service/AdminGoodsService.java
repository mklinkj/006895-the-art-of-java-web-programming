package com.bookshop01.admin.goods.service;

import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;
import com.bookshop01.order.vo.OrderVO;
import java.util.List;
import java.util.Map;

public interface AdminGoodsService {
  int addNewGoods(Map<String, ?> newGoodsMap);

  List<GoodsVO> listNewGoods(Map<String, ?> condMap);

  Map<String, ?> goodsDetail(int goodsId);

  List<ImageFileVO> goodsImageFile(int goodsId);

  void modifyGoodsInfo(Map<String, ?> goodsMap);

  void modifyGoodsImage(List<ImageFileVO> imageFileList);

  List<OrderVO> listOrderGoods(Map<String, ?> condMap);

  void modifyOrderGoods(Map<String, ?> orderMap);

  void removeGoodsImage(int imageId);

  void addNewGoodsImage(List<ImageFileVO> imageFileList);
}
