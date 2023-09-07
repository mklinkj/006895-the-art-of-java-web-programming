<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"
         isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html >
<html>
<head>
  <meta charset="utf-8">
  <script src="/webjars/date-fns/dist/date_fns.min.js"></script>
  <script src="/resources/js/utils.js"></script>
  <script>
    function search_goods_list(search_period) {
      const baseDate = document.frm_list.simpleEndDate.value;

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
      formObj.action = "${contextPath}/admin/goods/adminGoodsMain.do";
      formObj.submit();
    }

    function fn_enable_detail_search(searchType) {
      const frm_list = document.frm_list;
      const t_beginDate = frm_list.beginDate;
      const t_endDate = frm_list.endDate;

      const s_search_type = frm_list.s_search_type;
      const t_search_word = frm_list.t_search_word;
      const btn_search = frm_list.btn_search;

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
      const frm_list = document.frm_list;
      const beginDate = frm_list.beginDate.value;
      const endDate = frm_list.endDate.value;
      const search_type = frm_list.s_search_type.value;
      const search_word = frm_list.t_search_word.value;

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
      formObj.action = "${contextPath}/admin/goods/adminGoodsMain.do";
      formObj.submit();
    }
  </script>
</head>
<body>
<h3>상품 조회</h3>
<form name="frm_list">
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
        <a href="javascript:search_goods_list('today')">
          <img src="${contextPath}/resources/image/btn_search_one_day.jpg">
        </a>
        <a href="javascript:search_goods_list('one_week')">
          <img src="${contextPath}/resources/image/btn_search_1_week.jpg">
        </a>
        <a href="javascript:search_goods_list('two_week')">
          <img src="${contextPath}/resources/image/btn_search_2_week.jpg">
        </a>
        <a href="javascript:search_goods_list('one_month')">
          <img src="${pageContext.request.contextPath}/resources/image/btn_search_1_month.jpg">
        </a>
        <a href="javascript:search_goods_list('two_month')">
          <img src="${contextPath}/resources/image/btn_search_2_month.jpg">
        </a>
        <a href="javascript:search_goods_list('three_month')">
          <img src="${contextPath}/resources/image/btn_search_3_month.jpg">
        </a>
        <a href="javascript:search_goods_list('four_month')">
          <img src="${contextPath}/resources/image/btn_search_4_month.jpg">
        </a>
        &nbsp;까지 조회
      </td>
    </tr>
    <tr>
      <td>
        <select name="s_search_type" disabled>
          <option value="total" <c:if test="${empty search_type}">selected</c:if>>전체(상품 이름 + 저자)</option>
          <option value="goods_id" <c:if test="${search_type eq 'goods_id'}">selected</c:if>>상품번호</option>
          <option value="goods_title" <c:if test="${search_type eq 'goods_title'}">selected</c:if>>상품이름</option>
          <option value="goods_writer" <c:if test="${search_type eq 'goods_writer'}">selected</c:if>>저자</option>
        </select>
        <input type="text" size="30" name="t_search_word" value="${search_word}" onsubmit="return false;" disabled/>
        <input type="button" value="조회" name="btn_search" onClick="fn_detail_search()" disabled/>
      </td>
    </tr>
    <tr>
      <td>
        조회 기간:
        <input name="beginDate" type="date" value="${beginDate}"> ~
        <input name="endDate" type="date" value="${endDate}">
      </td>
    </tr>
    </tbody>
  </table>
  <div class="clear">
  </div>
</form>
<div class="clear"></div>
<table class="list_view">
  <tbody align=center>
  <tr style="background:#33ff00">
    <td>상품번호</td>
    <td>상품이름</td>
    <td>저자</td>
    <td>출판사</td>
    <td>상품가격</td>
    <td>입고일자</td>
    <td>출판일</td>
  </tr>
  <c:choose>
    <c:when test="${empty newGoodsList }">
      <tr>
        <td colspan=8 class="fixed">
          <strong>조회된 상품이 없습니다.</strong>
        </td>
      </tr>
    </c:when>
    <c:otherwise>
      <c:forEach var="item" items="${newGoodsList }">
        <tr>
          <td>
            <strong>${item.goods_id }</strong>
          </td>
          <td>
            <a href="${pageContext.request.contextPath}/admin/goods/modifyGoodsForm.do?goods_id=${item.goods_id}">
              <strong>${item.goods_title } </strong>
            </a>
          </td>
          <td>
            <strong>${item.goods_writer }</strong>
          </td>
          <td>
            <strong>${item.goods_publisher }</strong>
          </td>
          <td>
            <strong>${item.goods_sales_price }</strong>
          </td>
          <td>
            <strong>${item.goods_credate }</strong>
          </td>
          <td>
            <c:set var="pub_date" value="${item.goods_published_date}"/>
            <c:set var="arr" value="${fn:split(pub_date,' ')}"/>
            <strong>
              <c:out value="${arr[0]}"/>
            </strong>
          </td>
        </tr>
      </c:forEach>
    </c:otherwise>
  </c:choose>
  <tr>
    <td colspan=8 class="fixed">
      <c:forEach var="page" begin="1" end="10" step="1">
      <c:if test="${section >1 && page==1 }">
      <a href="${contextPath}/admin/goods/adminGoodsMain.do?chapter=${section-1}&pageNum=${(section-1)*10 +1 }">&nbsp;
        &nbsp;</a>
      </c:if>
      <a href="${contextPath}/admin/goods/adminGoodsMain.do?chapter=${section}&pageNum=${page}">${(section-1)*10 +page } </a>
      <c:if test="${page ==10 }">
      <a href="${contextPath}/admin/goods/adminGoodsMain.do?chapter=${section+1}&pageNum=${section*10+1}">&nbsp;
        next</a>
      </c:if>
      </c:forEach>
    </td>
  </tr>
  </tbody>
</table>
<div class="clear"></div>
<br><br><br>
<h3>상품등록하기</h3>
<div id="search">
  <form action="${contextPath}/admin/goods/addNewGoodsForm.do">
    <input type="submit" value="상품 등록하기">
  </form>
</div>
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