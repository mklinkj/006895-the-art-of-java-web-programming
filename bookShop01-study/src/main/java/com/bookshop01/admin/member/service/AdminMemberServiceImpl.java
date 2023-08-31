package com.bookshop01.admin.member.service;

import com.bookshop01.admin.member.dao.AdminMemberDAO;
import com.bookshop01.member.vo.MemberVO;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AdminMemberServiceImpl implements AdminMemberService {
  private final AdminMemberDAO adminMemberDAO;

  @Transactional(readOnly = true)
  @Override
  public List<MemberVO> listMember(Map<String, ?> condMap) {
    return adminMemberDAO.listMember(condMap);
  }

  @Transactional(readOnly = true)
  @Override
  public MemberVO memberDetail(String memberId) {
    return adminMemberDAO.memberDetail(memberId);
  }

  @Transactional
  @Override
  public void modifyMemberInfo(Map<String, ?> memberMap) {
    adminMemberDAO.modifyMemberInfo(memberMap);
  }
}
