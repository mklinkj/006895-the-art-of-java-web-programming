package com.bookshop01.goods.dao;

import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class GoodsDAOImpl implements GoodsDAO {
  private final SqlSession sqlSession;

  @Override
  public List<GoodsVO> selectGoodsList(String goodsStatus) {
    return sqlSession.selectList("mapper.goods.selectGoodsList", goodsStatus);
  }

  @Override
  public List<String> selectKeywordSearch(String keyword) {
    return sqlSession.selectList("mapper.goods.selectKeywordSearch", keyword);
  }

  @Override
  public List<GoodsVO> selectGoodsBySearchWord(String searchWord) {
    return sqlSession.selectList("mapper.goods.selectGoodsBySearchWord", searchWord);
  }

  @Override
  public GoodsVO selectGoodsDetail(Integer goodsId) {
    return sqlSession.selectOne("mapper.goods.selectGoodsDetail", goodsId);
  }

  @Override
  public List<ImageFileVO> selectGoodsDetailImage(Integer goodsId) {
    return sqlSession.selectList("mapper.goods.selectGoodsDetailImage", goodsId);
  }
}
