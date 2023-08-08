package com.bookshop01.mypage.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MyPageVO {
  /** 회원 아이디 */
  private String member_id;
  /** */
  private String beginDate;
  /** */
  private String endDate;
}
