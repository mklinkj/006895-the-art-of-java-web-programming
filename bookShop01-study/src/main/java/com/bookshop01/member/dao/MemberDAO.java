package com.bookshop01.member.dao;

import com.bookshop01.member.vo.MemberVO;
import java.util.Map;

public interface MemberDAO {
  MemberVO login(Map<String, String> loginMap);

  void insertNewMember(MemberVO memberVO);

  boolean selectOverlappedID(String id);
}
