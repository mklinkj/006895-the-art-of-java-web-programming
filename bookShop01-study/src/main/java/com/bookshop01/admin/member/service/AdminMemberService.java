package com.bookshop01.admin.member.service;

import com.bookshop01.member.vo.MemberVO;
import java.util.ArrayList;
import java.util.HashMap;

public interface AdminMemberService {
  ArrayList<MemberVO> listMember(HashMap condMap) throws Exception;

  MemberVO memberDetail(String member_id) throws Exception;

  void modifyMemberInfo(HashMap memberMap) throws Exception;
}
