package com.bookshop01.admin.member.dao;

import com.bookshop01.common.pagination.PageRequest;
import com.bookshop01.member.vo.MemberVO;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AdminMemberDAOImpl implements AdminMemberDAO {
  private final SqlSession sqlSession;

  @Override
  public List<MemberVO> listMember(PageRequest pageRequest, Map<String, ?> condMap) {
    return sqlSession.selectList(
        "mapper.admin.member.listMember", Map.of("pageRequest", pageRequest, "condMap", condMap));
  }

  @Override
  public long countMember(Map<String, ?> condMap) {
    return sqlSession.selectOne("mapper.admin.member.countMember", Map.of("condMap", condMap));
  }

  public MemberVO memberDetail(String memberId) {
    return sqlSession.selectOne("mapper.admin.member.memberDetail", memberId);
  }

  public void modifyMemberInfo(Map<String, ?> memberMap) {
    sqlSession.update("mapper.admin.member.modifyMemberInfo", memberMap);
  }
}
