package com.bookshop01.member.service;

import com.bookshop01.member.vo.MemberVO;
import java.util.Map;

public interface MemberService {
  MemberVO login(Map<String, String> loginMap);

  void addMember(MemberVO memberVO);

  boolean overlapped(String id);
}
