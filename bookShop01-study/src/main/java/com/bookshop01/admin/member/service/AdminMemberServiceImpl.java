package com.bookshop01.admin.member.service;

import com.bookshop01.admin.member.dao.AdminMemberDAO;
import com.bookshop01.member.vo.MemberVO;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AdminMemberServiceImpl implements AdminMemberService {
  private final AdminMemberDAO adminMemberDAO;

  @Override
  public List<MemberVO> listMember(Map<String, ?> condMap) {
    return adminMemberDAO.listMember(condMap);
  }

  @Override
  public MemberVO memberDetail(String memberId) {
    return adminMemberDAO.memberDetail(memberId);
  }

  @Override
  public void modifyMemberInfo(Map<String, ?> memberMap) {
    adminMemberDAO.modifyMemberInfo(memberMap);
  }
}
