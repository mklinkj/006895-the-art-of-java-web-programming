<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"
         isELIgnored="false"
%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<nav>
  <ul>
    <li>
      <h3>주문내역</h3>
      <ul>
        <li><a href="${contextPath}/mypage/listMyOrderHistory.do">주문내역/배송 조회</a></li>
        <li><a href="#">반품/교환 신청 및 조회</a></li>
        <li><a href="#">취소 주문 내역</a></li>
        <li><a href="#">세금 계산서</a></li>
      </ul>
    </li>
    <li>
      <h3>정보내역</h3>
      <ul>
        <li><a href="${contextPath}/mypage/myDetailInfo.do">회원정보관리</a></li>
        <li><a href="#">나의 주소록</a></li>
        <li><a href="#">개인정보 동의내역</a></li>
        <li><a href="#">회원탈퇴</a></li>
      </ul>
    </li>
  </ul>
</nav>