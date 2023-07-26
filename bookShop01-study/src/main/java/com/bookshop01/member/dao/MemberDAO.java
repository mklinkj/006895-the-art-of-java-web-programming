package com.bookshop01.member.dao;

import com.bookshop01.member.vo.MemberVO;
import java.util.Map;
import org.springframework.dao.DataAccessException;

public interface MemberDAO {
  MemberVO login(Map loginMap) throws DataAccessException;

  void insertNewMember(MemberVO memberVO) throws DataAccessException;

  String selectOverlappedID(String id) throws DataAccessException;
}
