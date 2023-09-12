package com.bookshop01.admin.member.service;

import com.bookshop01.common.pagination.PageRequest;
import com.bookshop01.common.pagination.PageResponse;
import com.bookshop01.member.vo.MemberVO;
import java.util.Map;

public interface AdminMemberService {
  PageResponse<MemberVO> listMember(PageRequest pageRequest, Map<String, ?> condMap);

  MemberVO memberDetail(String member_id);

  void modifyMemberInfo(Map<String, ?> memberMap);
}
