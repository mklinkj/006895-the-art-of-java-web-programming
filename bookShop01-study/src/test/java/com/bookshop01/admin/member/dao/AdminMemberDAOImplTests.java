package com.bookshop01.admin.member.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.bookshop01.admin.common.pagination.PageRequest;
import com.bookshop01.member.vo.MemberVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringJUnitConfig(locations = {"classpath:config/spring/root-context.xml"})
class AdminMemberDAOImplTests {

  @Autowired private AdminMemberDAO adminMemberDAO;

  @Test
  void listMember() {
    Map<String, Object> condMap = new HashMap<>();

    condMap.put("beginDate", "2023-08-01");
    condMap.put("endDate", "2023-08-10");
    condMap.put("search_type", "member_id");
    condMap.put("search_word", "admin");

    PageRequest pageRequest = PageRequest.builder().page(1).size(10).build();

    List<MemberVO> result = adminMemberDAO.listMember(pageRequest, condMap);

    assertThat(result).isNotNull();
  }

  @Test
  void memberDetail() {
    final String memberId = "admin";
    MemberVO result = adminMemberDAO.memberDetail(memberId);
    assertThat(result).hasFieldOrPropertyWithValue("member_id", "admin");
  }

  @Transactional
  @Test
  void modifyMemberInfo() {
    final String memberId = "lee";

    Map<String, Object> memberMap = new HashMap<>();
    // 회원 아이디 : 업데이트 기준 컬럼
    memberMap.put("member_id", memberId);

    // 회원 암호
    memberMap.put("member_pw", "1212");

    // 성별 (TODO: 101이 남자인가?)
    memberMap.put("member_gender", "101");

    // 생년월일 - 년
    memberMap.put("member_birth_y", "2000");

    // 생년월일 - 월
    memberMap.put("member_birth_m", "5");

    // 생년월일 - 일
    memberMap.put("member_birth_d", "10");

    // member_birth_gn 이란 컬럼은 수정하지 않지만, 양음력 여부를 나타냄.
    // TODO: 2가 양력인가?

    // 유선전화번호1 - 아마도 지역번호 (DB의 예제 데이터를 봤을 때...)
    memberMap.put("tel1", "02");

    // 유선전화번호2 - 지역번호 다음 자리
    memberMap.put("tel2", "1111");

    // 유선전화번호3 - 마지막 자리
    memberMap.put("tel3", "2222");

    // 휴대폰번호1 - 휴대폰 지역번호
    memberMap.put("hp1", "010");

    // 휴대폰번호2 - 지역번호 다음 자리
    memberMap.put("hp2", "1111");

    // 휴대폰번호3 - 마지막 자리
    memberMap.put("hp3", "2222");

    // SMS 메시지 수신 여부
    memberMap.put("smssts_yn", "Y");

    // 이메일1 : ID 영역
    memberMap.put("email1", "lee");

    // 이메일2 : 도메인 주소 영역
    memberMap.put("email2", "test.com,non"); // TODO: 예제 데이터에 있어서 그대로 사용했는데 non이 뭐지?

    // 우편번호
    memberMap.put("zipcode", "13547");

    // 도로명 주소
    memberMap.put("roadAddress", "경기 성남시 분당구 고기로 25 (동원동)");

    // 지번 주소
    memberMap.put("jibunAddress", "경기 성남시 분당구 동원동 79-1");

    // 나머지 주소
    memberMap.put("namujiAddress", "럭키빌딩 101호");

    // 회원 탈퇴 유무
    memberMap.put("del_yn", "N");

    adminMemberDAO.modifyMemberInfo(memberMap);
  }
}
