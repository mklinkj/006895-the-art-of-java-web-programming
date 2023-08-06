package com.bookshop01.member.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberVO {
  /** 회원 아이디 */
  private String member_id;

  /** 비밀번호 */
  private String member_pw;

  /** 회원 이름 */
  private String member_name;

  /** 성별 */
  private String member_gender;

  /** 생년월일 - 년도 */
  private String member_birth_y;
  /** 생년월일 - 월 */
  private String member_birth_m;
  /** 생년월일 - 일 */
  private String member_birth_d;
  /** 생년월일 양음력 여부 */
  private String member_birth_gn;

  /** 유선전화번호1 - 지역번호 */
  private String tel1;
  /** 유선전화번호2 - 앞자리 전화번호 */
  private String tel2;
  /** 유선전화번호3 - 뒷자리 전화번호 */
  private String tel3;

  /** 휴대폰번호1 - 휴대폰 서비스 번호 */
  private String hp1;
  /** 휴대폰번호2 - 휴대폰 앞자리 번호 */
  private String hp2;
  /** 휴대폰번호3 - 휴대폰 뒷자리 번호 */
  private String hp3;

  /** SMS메시지 수신여부 */
  private String smssts_yn;

  /** 이메일 주소1 - 아이디 영역 */
  private String email1;
  /** 이메일 주소2 - 도메인 영역 */
  private String email2;
  /** 이메일 수신 여부 */
  private String emailsts_yn;

  /** 우편번호 */
  private String zipcode;
  /** 도로명 주소 */
  private String roadAddress;
  /** 지번 주소 */
  private String jibunAddress;
  /** 나머지 주소 */
  private String namujiAddress;

  /** 회원 가입일자 */
  private String joinDate;

  /** 회원 탈퇴 유무 */
  private String del_yn;
}
