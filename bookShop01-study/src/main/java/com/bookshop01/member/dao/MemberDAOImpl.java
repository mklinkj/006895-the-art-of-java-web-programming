package com.bookshop01.member.dao;

import com.bookshop01.member.vo.MemberVO;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberDAOImpl implements MemberDAO {
  private final SqlSession sqlSession;

  @Override
  public MemberVO login(Map<String, String> loginMap) {
    return sqlSession.selectOne("mapper.member.login", loginMap);
  }

  @Override
  public void insertNewMember(MemberVO memberVO) {
    sqlSession.insert("mapper.member.insertNewMember", memberVO);
  }

  @Override
  public boolean selectOverlappedID(String id) {
    return ((Integer) sqlSession.selectOne("mapper.member.selectOverlappedID", id)) > 0;
  }
}
