package com.bookshop01.member.service;

import com.bookshop01.member.vo.MemberVO;
import java.util.Map;

public interface MemberService {
  MemberVO login(Map loginMap) throws Exception;

  void addMember(MemberVO memberVO) throws Exception;

  String overlapped(String id) throws Exception;
}
