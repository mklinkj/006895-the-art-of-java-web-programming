package com.bookshop01.admin.member.dao;

import com.bookshop01.member.vo.MemberVO;
import java.util.List;
import java.util.Map;

public interface AdminMemberDAO {
  List<MemberVO> listMember(Map<String, ?> condMap);

  MemberVO memberDetail(String memberId);

  void modifyMemberInfo(Map<String, ?> memberMap);
}
