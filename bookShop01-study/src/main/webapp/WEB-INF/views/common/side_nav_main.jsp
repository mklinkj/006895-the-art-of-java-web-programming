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
      <h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;국내외 도서</h3>
      <ul>
        <li><a href="#">IT/인터넷</a></li>
        <li><a href="#">경제/경영</a></li>
        <li><a href="#">대학교재</a></li>
        <li><a href="#">자기계발</a></li>
        <li><a href="#">자연과학/공학</a></li>
        <li><a href="#">역사/인문학</a></li>
      </ul>
    </li>
    <li>
      <h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;음반</h3>
      <ul>
        <li><a href="#">가요</a></li>
        <li><a href="#">록</a></li>
        <li><a href="#">클래식</a></li>
        <li><a href="#">뉴에이지</a></li>
        <li><a href="#">재즈</a></li>
        <li><a href="#">기타</a></li>
      </ul>
    </li>
  </ul>
</nav>
<div class="clear"></div>
<div id="banner">
  <a href="#"><img width="190" height="163" src="${contextPath}/resources/image/n-pay.jpg"> </a>
</div>
<DIV id="notice">
  <H2>공지사항</H2>
  <UL>

    <c:forEach var="i" begin="1" end="5" step="1">
      <li><a href="#">공지사항입니다.${ i}</a></li>
    </c:forEach>
  </ul>
</div>


<div id="banner">
  <a href="#"><img width="190" height="362"
                   src="${contextPath}/resources/image/side_banner1.jpg"></a>
</div>
<div id="banner">
  <a href="#"><img width="190" height="104"
                   src="${contextPath}/resources/image/call_center_logo.jpg"></a>
</div>
<div id="banner">
  <a href="#"><img width="190" height="69" src="${contextPath}/resources/image/QnA_logo.jpg"></a>
</div>
