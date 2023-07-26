package com.bookshop01.admin.goods.service;

import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;
import com.bookshop01.order.vo.OrderVO;
import java.util.List;
import java.util.Map;

public interface AdminGoodsService {
  int addNewGoods(Map newGoodsMap) throws Exception;

  List<GoodsVO> listNewGoods(Map condMap) throws Exception;

  Map goodsDetail(int goods_id) throws Exception;

  List goodsImageFile(int goods_id) throws Exception;

  void modifyGoodsInfo(Map goodsMap) throws Exception;

  void modifyGoodsImage(List<ImageFileVO> imageFileList) throws Exception;

  List<OrderVO> listOrderGoods(Map condMap) throws Exception;

  void modifyOrderGoods(Map orderMap) throws Exception;

  void removeGoodsImage(int image_id) throws Exception;

  void addNewGoodsImage(List imageFileList) throws Exception;
}
