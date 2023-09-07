<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"
         isELIgnored="false" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <script src="/webjars/date-fns/dist/date_fns.min.js"></script>
  <script src="/resources/js/utils.js"></script>
  <script>
    function search_member(search_period) {
      const baseDate = document.frm_delivery_list.simpleEndDate.value;

      const temp = calcPeriod(search_period, baseDate);
      const beginDate = temp['beginDate'];
      const endDate = temp['endDate'];

      const formObj = document.createElement("form");
      const i_beginDate = document.createElement("input");
      const i_endDate = document.createElement("input");

      i_beginDate.name = "beginDate";
      i_beginDate.value = beginDate;
      i_endDate.name = "endDate";
      i_endDate.value = endDate;

      formObj.appendChild(i_beginDate);
      formObj.appendChild(i_endDate);
      document.body.appendChild(formObj);
      formObj.method = "get";
      formObj.action = "${contextPath}/admin/member/adminMemberMain.do";
      formObj.submit();
    }

    function fn_enable_detail_search(searchType) {
      const frm_delivery_list = document.frm_delivery_list;
      const t_beginDate = frm_delivery_list.beginDate;
      const t_endDate = frm_delivery_list.endDate;

      const s_search_type = frm_delivery_list.s_search_type;
      const t_search_word = frm_delivery_list.t_search_word;
      const btn_search = frm_delivery_list.btn_search;

      if (searchType === 'detail_search') {
        t_beginDate.disabled = false;
        t_endDate.disabled = false;

        s_search_type.disabled = false;
        t_search_word.disabled = false;
        btn_search.disabled = false;
      } else {
        t_beginDate.disabled = true;
        t_endDate.disabled = true;

        s_search_type.disabled = true;
        t_search_word.disabled = true;
        btn_search.disabled = true;
      }
    }

    //상세조회 버튼 클릭 시 수행
    function fn_detail_search() {
      const frm_delivery_list = document.frm_delivery_list;
      const beginDate = frm_delivery_list.beginDate.value;
      const endDate = frm_delivery_list.endDate.value;
      const search_type = frm_delivery_list.s_search_type.value;
      const search_word = frm_delivery_list.t_search_word.value;

      const formObj = document.createElement("form");
      const i_command = document.createElement("input");
      const i_beginDate = document.createElement("input");
      const i_endDate = document.createElement("input");
      const i_search_type = document.createElement("input");
      const i_search_word = document.createElement("input");

      i_command.name = "command";
      i_beginDate.name = "beginDate";
      i_endDate.name = "endDate";
      i_search_type.name = "search_type";
      i_search_word.name = "search_word";

      i_command.value = "detail_search";
      i_beginDate.value = beginDate;
      i_endDate.value = endDate;
      i_search_type.value = search_type;
      i_search_word.value = search_word;

      formObj.appendChild(i_command);
      formObj.appendChild(i_beginDate);
      formObj.appendChild(i_endDate);
      formObj.appendChild(i_search_type);
      formObj.appendChild(i_search_word);
      document.body.appendChild(formObj);
      formObj.method = "get";
      formObj.action = "${contextPath}/admin/member/adminMemberMain.do";
      formObj.submit();

    }
  </script>
</head>
<body>
<H3>회원 조회</H3>
<form name="frm_delivery_list">
  <table cellpadding="10" cellspacing="10">
    <tbody>
    <tr>
      <td>
        <input type="radio" name="r_search_option" value="simple_search"
                <c:if test="${empty command or command eq 'simple_search'}">checked</c:if>
               onClick="fn_enable_detail_search(this.value)"/> 간단조회 &nbsp;&nbsp;&nbsp;
        <input type="radio" name="r_search_option" value="detail_search"
               <c:if test="${command eq 'detail_search'}">checked</c:if>
               onClick="fn_enable_detail_search(this.value)"/> 상세조회 &nbsp;&nbsp;&nbsp;
      </td>
    </tr>
    <tr>
      <td>
        <input name='simpleEndDate' type="date" value="${endDate}"> &nbsp;이전&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="javascript:search_member('today')">
          <img src="${pageContext.request.contextPath}/resources/image/btn_search_one_day.jpg">
        </a>
        <a href="javascript:search_member('one_week')">
          <img src="${pageContext.request.contextPath}/resources/image/btn_search_1_week.jpg">
        </a>
        <a href="javascript:search_member('two_week')">
          <img src="${pageContext.request.contextPath}/resources/image/btn_search_2_week.jpg">
        </a>
        <a href="javascript:search_member('one_month')">
          <img src="${pageContext.request.contextPath}/resources/image/btn_search_1_month.jpg">
        </a>
        <a href="javascript:search_member('two_month')">
          <img src="${pageContext.request.contextPath}/resources/image/btn_search_2_month.jpg">
        </a>
        <a href="javascript:search_member('three_month')">
          <img src="${pageContext.request.contextPath}/resources/image/btn_search_3_month.jpg">
        </a>
        <a href="javascript:search_member('four_month')">
          <img src="${pageContext.request.contextPath}/resources/image/btn_search_4_month.jpg">
        </a>
        &nbsp;까지 조회
      </td>
    </tr>

    <tr>
      <td>
        조회 기간:
        <input name="beginDate" type="date" value="${beginDate}"> ~
        <input name="endDate" type="date" value="${endDate}">
      </td>
    </tr>
    <tr>
      <td>
        <select name="s_search_type" disabled>
          <option value="member_id" <c:if test="${search_type eq 'member_id'}">selected</c:if>>회원아이디</option>
          <option value="member_name" <c:if test="${search_type eq 'member_name'}">selected</c:if>>회원이름</option>
          <option value="member_hp_num" <c:if test="${search_type eq 'member_hp_num'}">selected</c:if>>회원휴대폰번호</option>
          <option value="member_addr" <c:if test="${search_type eq 'member_addr'}">selected</c:if>>회원주소</option>
        </select>
        <input type="text" size="30" name="t_search_word" value="${search_word}" onsubmit="return false;" disabled/>
        <input type="button" value="조회" name="btn_search" onClick="fn_detail_search()" disabled/>
      </td>
    </tr>
    </tbody>
  </table>
  <div class="clear">
  </div>

  <div class="clear"></div>
  <table class="list_view">
    <tbody align=center>
    <tr align=center bgcolor="#ffcc00">
      <td class="fixed">회원아이디</td>
      <td class="fixed">회원이름</td>
      <td>휴대폰번호</td>
      <td>주소</td>
      <td>가입일</td>
      <td>탈퇴여부</td>
    </tr>
    <c:choose>
      <c:when test="${empty memberList}">
        <tr>
          <td colspan=5 class="fixed">
            <strong>조회된 회원이 없습니다.</strong>
          </td>
        </tr>
      </c:when>
      <c:otherwise>
        <c:forEach var="item" items="${memberList}" varStatus="item_num">
          <tr>
            <td width=10%>

              <a href="${pageContext.request.contextPath}/admin/member/memberDetail.do?member_id=${item.member_id}">
                <strong>${item.member_id}</strong>
              </a>
            </td>
            <td width=10%>
              <strong>${item.member_name}</strong><br>
            </td>
            <td width=10%>
              <strong>${item.hp1}-${item.hp2}-${item.hp3}</strong><br>
            </td>
            <td width=50%>
              <strong>${item.roadAddress}</strong><br>
              <strong>${item.jibunAddress}</strong><br>
              <strong>${item.namujiAddress}</strong><br>
            </td>
            <td width=10%>
              <c:set var="join_date" value="${item.joinDate}"/>
              <c:set var="arr" value="${fn:split(join_date,' ')}"/>
              <strong><c:out value="${arr[0]}"/></strong>
            </td>
            <td width=10%>
              <c:choose>
                <c:when test="${item.del_yn=='N' }">
                  <strong>활동중</strong>
                </c:when>
                <c:otherwise>
                  <strong>탈퇴</strong>
                </c:otherwise>
              </c:choose>
            </td>
          </tr>
        </c:forEach>
      </c:otherwise>
    </c:choose>
    <tr>
      <td colspan=8 class="fixed">
        <c:forEach var="page" begin="1" end="10" step="1">
          <c:if test="${section >1 && page==1 }">
            <a href="${pageContext.request.contextPath}/admin/member/adminMemberMain.do?section=${section-1}&pageNum=${(section-1)*10 +1 }">&nbsp;pre
              &nbsp;</a>
          </c:if>
          <a href="${pageContext.request.contextPath}/admin/member/adminMemberMain.do?section=${section}&pageNum=${page}">${(section-1)*10 +page } </a>
          <c:if test="${page ==10 }">
            <a href="${pageContext.request.contextPath}/admin/member/adminMemberMain.do?section=${section+1}&pageNum=${section*10+1}">&nbsp;
              next</a>
          </c:if>
        </c:forEach>
      </td>
    </tr>
    </tbody>
  </table>
</form>
<div class="clear"></div>
<script>
  document.querySelector('input[name="t_search_word"]').addEventListener('keydown', event => {
    if (event.keyCode === 13) {
      event.preventDefault();
      fn_detail_search();
    }
  });
  fn_enable_detail_search(document.querySelector('input[name="r_search_option"]:checked').value)
</script>
</body>
</html>
