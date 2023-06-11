package com.spring.member.service;

import com.spring.member.vo.MemberVO;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface MemberService {
  List<MemberVO> listMembers() throws DataAccessException;

  int addMember(MemberVO memberVO) throws DataAccessException;

  int removeMember(String id) throws DataAccessException;
}
