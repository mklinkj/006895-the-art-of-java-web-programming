package com.bookshop01.admin.goods.service;

import com.bookshop01.admin.goods.dao.AdminGoodsDAO;
import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;
import com.bookshop01.order.vo.OrderVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("adminGoodsService")
@Transactional(propagation = Propagation.REQUIRED)
public class AdminGoodsServiceImpl implements AdminGoodsService {
  @Autowired private AdminGoodsDAO adminGoodsDAO;

  @Override
  public int addNewGoods(Map newGoodsMap) {
    int goods_id = adminGoodsDAO.insertNewGoods(newGoodsMap);
    List<ImageFileVO> imageFileList = (ArrayList) newGoodsMap.get("imageFileList");
    for (ImageFileVO imageFileVO : imageFileList) {
      imageFileVO.setGoods_id(goods_id);
    }
    adminGoodsDAO.insertGoodsImageFile(imageFileList);
    return goods_id;
  }

  @Override
  public List<GoodsVO> listNewGoods(Map condMap) throws Exception {
    return adminGoodsDAO.selectNewGoodsList(condMap);
  }

  @Override
  public Map goodsDetail(int goods_id) throws Exception {
    Map goodsMap = new HashMap();
    GoodsVO goodsVO = adminGoodsDAO.selectGoodsDetail(goods_id);
    List imageFileList = adminGoodsDAO.selectGoodsImageFileList(goods_id);
    goodsMap.put("goods", goodsVO);
    goodsMap.put("imageFileList", imageFileList);
    return goodsMap;
  }

  @Override
  public List goodsImageFile(int goods_id) throws Exception {
    List imageList = adminGoodsDAO.selectGoodsImageFileList(goods_id);
    return imageList;
  }

  @Override
  public void modifyGoodsInfo(Map goodsMap) throws Exception {
    adminGoodsDAO.updateGoodsInfo(goodsMap);
  }

  @Override
  public void modifyGoodsImage(List<ImageFileVO> imageFileList) throws Exception {
    for (ImageFileVO imageFile : imageFileList) {
      adminGoodsDAO.updateGoodsImage(imageFile);
    }
  }

  @Override
  public List<OrderVO> listOrderGoods(Map condMap) throws Exception {
    return adminGoodsDAO.selectOrderGoodsList(condMap);
  }

  @Override
  public void modifyOrderGoods(Map orderMap) throws Exception {
    adminGoodsDAO.updateOrderGoods(orderMap);
  }

  @Override
  public void removeGoodsImage(int image_id) throws Exception {
    adminGoodsDAO.deleteGoodsImage(image_id);
  }

  @Override
  public void addNewGoodsImage(List imageFileList) throws Exception {
    adminGoodsDAO.insertGoodsImageFile(imageFileList);
  }
}
