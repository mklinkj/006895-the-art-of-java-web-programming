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
      <h3>주요기능</h3>
      <ul>
        <li><a href="${contextPath}/admin/goods/adminGoodsMain.do">상품관리</a></li>
        <li><a href="${contextPath}/admin/order/adminOrderMain.do">주문관리</a></li>
        <li><a href="${contextPath}/admin/member/adminMemberMain.do">회원관리</a></li>
        <li><a href="#">배송관리</a></li>
        <li><a href="#">게시판관리</a></li>
      </ul>
    </li>
  </ul>
</nav>