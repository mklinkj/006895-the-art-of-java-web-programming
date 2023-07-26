package com.bookshop01.admin.member.dao;

import com.bookshop01.member.vo.MemberVO;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.dao.DataAccessException;

public interface AdminMemberDAO {
  ArrayList<MemberVO> listMember(HashMap condMap) throws DataAccessException;

  MemberVO memberDetail(String member_id) throws DataAccessException;

  void modifyMemberInfo(HashMap memberMap) throws DataAccessException;
}
