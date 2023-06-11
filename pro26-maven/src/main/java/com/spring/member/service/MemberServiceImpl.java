package com.spring.member.service;

import com.spring.member.dao.MemberDAO;
import com.spring.member.vo.MemberVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("memberService")
@Transactional(propagation = Propagation.REQUIRED)
public class MemberServiceImpl implements MemberService {
  @Autowired private MemberDAO memberDAO;

  @Override
  public List<MemberVO> listMembers() throws DataAccessException {
    List<MemberVO> membersList = null;
    membersList = memberDAO.selectAllMemberList();
    return membersList;
  }

  @Override
  public int addMember(MemberVO member) throws DataAccessException {
    return memberDAO.insertMember(member);
  }

  @Override
  public int removeMember(String id) throws DataAccessException {
    return memberDAO.deleteMember(id);
  }
}
