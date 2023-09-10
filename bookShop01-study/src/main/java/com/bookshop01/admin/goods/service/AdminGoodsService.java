package com.bookshop01.admin.goods.service;

import com.bookshop01.admin.common.pagination.PageRequest;
import com.bookshop01.admin.common.pagination.PageResponse;
import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;
import java.util.List;
import java.util.Map;

public interface AdminGoodsService {
  int addNewGoods(Map<String, ?> newGoodsMap);

  PageResponse<GoodsVO> listNewGoods(PageRequest pageRequest, Map<String, ?> condMap);

  Map<String, ?> goodsDetail(int goodsId);

  List<ImageFileVO> goodsImageFile(int goodsId);

  void modifyGoodsInfo(Map<String, ?> goodsMap);

  void modifyGoodsImage(List<ImageFileVO> imageFileList);

  void modifyOrderGoods(Map<String, ?> orderMap);

  void removeGoodsImage(int imageId);

  void addNewGoodsImage(List<ImageFileVO> imageFileList);
}
