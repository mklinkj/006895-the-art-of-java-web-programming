package com.spring.member.dao;

import com.spring.member.vo.MemberVO;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface MemberDAO {
  List<MemberVO> selectAllMemberList() throws DataAccessException;

  int insertMember(MemberVO memberVO) throws DataAccessException;

  int deleteMember(String id) throws DataAccessException;
}
