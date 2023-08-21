package com.bookshop01.admin.goods.service;

import com.bookshop01.admin.goods.dao.AdminGoodsDAO;
import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;
import com.bookshop01.order.vo.OrderVO;
import java.util.ArrayList;
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
public class AdminGoodsServiceImpl implements AdminGoodsService {
  private final AdminGoodsDAO adminGoodsDAO;

  @Override
  public int addNewGoods(Map<String, ?> newGoodsMap) {
    int goodsId = adminGoodsDAO.insertNewGoods(newGoodsMap);
    List<ImageFileVO> imageFileList = (ArrayList) newGoodsMap.get("imageFileList");
    for (ImageFileVO imageFileVO : imageFileList) {
      imageFileVO.setGoods_id(goodsId);
    }
    adminGoodsDAO.insertGoodsImageFile(imageFileList);
    return goodsId;
  }

  @Override
  public List<GoodsVO> listNewGoods(Map<String, ?> condMap) {
    return adminGoodsDAO.selectNewGoodsList(condMap);
  }

  @Override
  public Map<String, ?> goodsDetail(int goodsId) {
    Map<String, Object> goodsMap = new HashMap<>();
    GoodsVO goodsVO = adminGoodsDAO.selectGoodsDetail(goodsId);
    List<ImageFileVO> imageFileList = adminGoodsDAO.selectGoodsImageFileList(goodsId);
    goodsMap.put("goods", goodsVO);
    goodsMap.put("imageFileList", imageFileList);
    return goodsMap;
  }

  @Override
  public List<ImageFileVO> goodsImageFile(int goodsId) {
    return adminGoodsDAO.selectGoodsImageFileList(goodsId);
  }

  @Override
  public void modifyGoodsInfo(Map<String, ?> goodsMap) {
    adminGoodsDAO.updateGoodsInfo(goodsMap);
  }

  @Override
  public void modifyGoodsImage(List<ImageFileVO> imageFileList) {
    for (ImageFileVO imageFile : imageFileList) {
      adminGoodsDAO.updateGoodsImage(imageFile);
    }
  }

  @Override
  public List<OrderVO> listOrderGoods(Map<String, ?> condMap) {
    return adminGoodsDAO.selectOrderGoodsList(condMap);
  }

  @Override
  public void modifyOrderGoods(Map<String, ?> orderMap) {
    adminGoodsDAO.updateOrderGoods(orderMap);
  }

  @Override
  public void removeGoodsImage(int imageId) {
    adminGoodsDAO.deleteGoodsImage(imageId);
  }

  @Override
  public void addNewGoodsImage(List<ImageFileVO> imageFileList) {
    adminGoodsDAO.insertGoodsImageFile(imageFileList);
  }
}
