<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"
         isELIgnored="false"
%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
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
