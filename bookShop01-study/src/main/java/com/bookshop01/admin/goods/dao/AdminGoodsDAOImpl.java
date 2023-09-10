package com.bookshop01.admin.goods.dao;

import com.bookshop01.admin.common.pagination.PageRequest;
import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
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
  public List<GoodsVO> selectNewGoodsList(PageRequest pageRequest, Map<String, ?> condMap) {
    return sqlSession.selectList(
        "mapper.admin.goods.selectNewGoodsList",
        Map.of("pageRequest", pageRequest, "condMap", condMap));
  }

  @Override
  public long countNewGoods(Map<String, ?> condMap) {
    return sqlSession.selectOne("mapper.admin.goods.countNewGoods", Map.of("condMap", condMap));
  }

  @Override
  public GoodsVO selectGoodsDetail(int goodsId) {
    return sqlSession.selectOne("mapper.admin.goods.selectGoodsDetail", goodsId);
  }

  @Override
  public List<ImageFileVO> selectGoodsImageFileList(int goodsId) {
    return sqlSession.selectList("mapper.admin.goods.selectGoodsImageFileList", goodsId);
  }

  @Override
  public void updateGoodsInfo(Map<String, ?> goodsMap) {
    sqlSession.update("mapper.admin.goods.updateGoodsInfo", goodsMap);
  }

  @Override
  public void deleteGoodsImage(int imageId) {
    sqlSession.delete("mapper.admin.goods.deleteGoodsImage", imageId);
  }

  @Override
  public void deleteGoodsImage(List<ImageFileVO> fileList) {
    for (ImageFileVO bean : fileList) {
      sqlSession.delete("mapper.admin.goods.deleteGoodsImage", bean.getImage_id());
    }
  }

  @Override
  public void updateOrderGoods(Map<String, ?> orderMap) {
    sqlSession.update("mapper.admin.goods.updateOrderGoods", orderMap);
  }

  @Override
  public void updateGoodsImage(ImageFileVO imageFile) {
    sqlSession.update("mapper.admin.goods.updateGoodsImage", imageFile);
  }
}
