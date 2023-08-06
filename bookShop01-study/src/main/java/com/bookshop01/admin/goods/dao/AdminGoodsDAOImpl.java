package com.bookshop01.admin.goods.dao;

import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;
import com.bookshop01.order.vo.OrderVO;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AdminGoodsDAOImpl implements AdminGoodsDAO {
  private final SqlSession sqlSession;

  @Override
  public int insertNewGoods(Map<String, ?> newGoodsMap) {
    sqlSession.insert("mapper.admin.goods.insertNewGoods", newGoodsMap);
    return (Integer) newGoodsMap.get("goods_id");
  }

  @Override
  public void insertGoodsImageFile(List<ImageFileVO> fileList) {
    for (ImageFileVO file : fileList) {
      sqlSession.insert("mapper.admin.goods.insertGoodsImageFile", file);
    }
  }

  @Override
  public List<GoodsVO> selectNewGoodsList(Map<String, ?> condMap) throws DataAccessException {
    return sqlSession.selectList("mapper.admin.goods.selectNewGoodsList", condMap);
  }

  @Override
  public GoodsVO selectGoodsDetail(int goodsId) throws DataAccessException {
    return sqlSession.selectOne("mapper.admin.goods.selectGoodsDetail", goodsId);
  }

  @Override
  public List<ImageFileVO> selectGoodsImageFileList(int goodsId) throws DataAccessException {
    return sqlSession.selectList("mapper.admin.goods.selectGoodsImageFileList", goodsId);
  }

  @Override
  public void updateGoodsInfo(Map<String, ?> goodsMap) throws DataAccessException {
    sqlSession.update("mapper.admin.goods.updateGoodsInfo", goodsMap);
  }

  @Override
  public void deleteGoodsImage(int imageId) throws DataAccessException {
    sqlSession.delete("mapper.admin.goods.deleteGoodsImage", imageId);
  }

  @Override
  public void deleteGoodsImage(List<ImageFileVO> fileList) throws DataAccessException {
    for (ImageFileVO bean : fileList) {
      sqlSession.delete("mapper.admin.goods.deleteGoodsImage", bean.getImage_id());
    }
  }

  @Override
  public List<OrderVO> selectOrderGoodsList(Map<String, ?> condMap) {
    return sqlSession.selectList("mapper.admin.goods.selectOrderGoodsList", condMap);
  }

  @Override
  public void updateOrderGoods(Map<String, ?> orderMap) {
    sqlSession.update("mapper.admin.goods.updateOrderGoods", orderMap);
  }

  @Override
  public void updateGoodsImage(ImageFileVO imageFile) throws DataAccessException {
    sqlSession.update("mapper.admin.goods.updateGoodsImage", imageFile);
  }
}
