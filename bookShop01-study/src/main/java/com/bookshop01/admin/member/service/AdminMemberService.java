package com.bookshop01.admin.member.service;

import com.bookshop01.member.vo.MemberVO;
import java.util.List;
import java.util.Map;

public interface AdminMemberService {
  List<MemberVO> listMember(Map<String, ?> condMap);

  MemberVO memberDetail(String member_id);

  void modifyMemberInfo(Map<String, ?> memberMap);
}
