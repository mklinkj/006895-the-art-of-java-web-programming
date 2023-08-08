package com.bookshop01.member.service;

import static com.bookshop01.common.util.StringUtils.isNullOrBlank;

import com.bookshop01.member.dao.MemberDAO;
import com.bookshop01.member.vo.MemberVO;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class MemberServiceImpl implements MemberService {
  private final MemberDAO memberDAO;

  @Override
  public MemberVO login(Map<String, String> loginMap) {
    return memberDAO.login(loginMap);
  }

  @Override
  public void addMember(MemberVO memberVO) {
    // TODO: Y, N을 enum 처리할지? 생각을 해보자...😅😅😅
    if (isNullOrBlank(memberVO.getSmssts_yn())) {
      memberVO.setSmssts_yn("N");
    }

    if (isNullOrBlank(memberVO.getEmailsts_yn())) {
      memberVO.setEmailsts_yn("N");
    }
    memberDAO.insertNewMember(memberVO);
  }

  @Override
  public boolean overlapped(String id) {
    return memberDAO.selectOverlappedID(id);
  }
}
