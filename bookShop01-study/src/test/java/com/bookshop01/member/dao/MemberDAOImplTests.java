package com.bookshop01.member.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.bookshop01.member.vo.MemberVO;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringJUnitConfig(locations = {"classpath:config/spring/root-context.xml"})
class MemberDAOImplTests {

  @Autowired private MemberDAO memberDAO;

  @Test
  void login() {
    Map<String, String> loginMap = new HashMap<>();
    loginMap.put("member_id", "lee");
    loginMap.put("member_pw", "1212");

    MemberVO result = memberDAO.login(loginMap);

    assertThat(result) //
        .isNotNull()
        .hasFieldOrPropertyWithValue("member_id", "lee");
  }

  @Transactional
  @Test
  void insertNewMember() {
    MemberVO memberVO = new MemberVO();

    memberVO.setMember_id("honggd");
    memberVO.setMember_pw("1212");
    memberVO.setMember_name("홍길동");

    memberVO.setMember_gender("101");

    memberVO.setMember_birth_y("1999");
    memberVO.setMember_birth_m("1");
    memberVO.setMember_birth_d("2");
    memberVO.setMember_birth_gn("1");

    memberVO.setTel1("02");
    memberVO.setTel2("1111");
    memberVO.setTel3("2222");

    memberVO.setHp1("010");
    memberVO.setHp2("3333");
    memberVO.setHp3("4444");

    memberVO.setSmssts_yn("Y");

    memberVO.setEmail1("honggildong");
    memberVO.setEmail2("test.com");
    memberVO.setEmailsts_yn("N");

    memberVO.setZipcode("13547");
    memberVO.setRoadAddress("서울 강남구 강남대로 298 (역삼동)");
    memberVO.setJibunAddress("서울 강남구 역삼동 838");
    memberVO.setNamujiAddress("럭키빌딩 101호");

    memberDAO.insertNewMember(memberVO);
  }

  @Test
  void selectOverlappedID() {
    String id = "lee";
    boolean result = memberDAO.selectOverlappedID(id);
    assertThat(result).isTrue();
  }
}
