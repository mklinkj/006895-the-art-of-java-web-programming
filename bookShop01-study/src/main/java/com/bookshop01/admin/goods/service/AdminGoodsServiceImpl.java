package com.bookshop01.admin.goods.service;

import com.bookshop01.admin.common.pagination.PageRequest;
import com.bookshop01.admin.common.pagination.PageResponse;
import com.bookshop01.admin.goods.dao.AdminGoodsDAO;
import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;
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

    @SuppressWarnings("unchecked")
    List<ImageFileVO> imageFileList = (List<ImageFileVO>) newGoodsMap.get("imageFileList");
    for (ImageFileVO imageFileVO : imageFileList) {
      imageFileVO.setGoods_id(goodsId);
    }
    adminGoodsDAO.insertGoodsImageFile(imageFileList);
    return goodsId;
  }

  @Override
  public PageResponse<GoodsVO> listNewGoods(PageRequest pageRequest, Map<String, ?> condMap) {
    List<GoodsVO> list = adminGoodsDAO.selectNewGoodsList(pageRequest, condMap);

    return PageResponse.<GoodsVO>withAll()
        .content(list)
        .pageRequest(pageRequest)
        .total(adminGoodsDAO.countNewGoods(condMap))
        .build();
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
