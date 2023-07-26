package com.bookshop01.admin.goods.dao;

import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;
import com.bookshop01.order.vo.OrderVO;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;

public interface AdminGoodsDAO {
  int insertNewGoods(Map newGoodsMap) throws DataAccessException;

  List<GoodsVO> selectNewGoodsList(Map condMap) throws DataAccessException;

  GoodsVO selectGoodsDetail(int goods_id) throws DataAccessException;

  List selectGoodsImageFileList(int goods_id) throws DataAccessException;

  void insertGoodsImageFile(List fileList) throws DataAccessException;

  void updateGoodsInfo(Map goodsMap) throws DataAccessException;

  void updateGoodsImage(List<ImageFileVO> imageFileList) throws DataAccessException;

  void deleteGoodsImage(int image_id) throws DataAccessException;

  void deleteGoodsImage(List fileList) throws DataAccessException;

  List<OrderVO> selectOrderGoodsList(Map condMap) throws DataAccessException;

  void updateOrderGoods(Map orderMap) throws DataAccessException;
}
