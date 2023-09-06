<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"
         isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
  <meta charset="utf-8">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/date-fns/1.30.1/date_fns.min.js"></script>
  <c:choose>
    <c:when test='${not empty order_goods_list}'>
      <script type="text/javascript">
        window.onload = function () {
          init();
        }

        //화면이 표시되면서  각각의 주문건에 대한 배송 상태를 표시한다.
        function init() {
          const frm_delivery_list = document.frm_delivery_list;
          const h_delivery_state = frm_delivery_list.h_delivery_state;
          const s_delivery_state = frm_delivery_list.s_delivery_state;

          if (h_delivery_state.length == undefined) {
            s_delivery_state.value = h_delivery_state.value; //조회된 주문 정보가 1건인 경우
          } else {
            for (var i = 0; s_delivery_state.length; i++) {
              s_delivery_state[i].value = h_delivery_state[i].value;//조회된 주문 정보가 여러건인 경우
            }
          }
        }
      </script>
    </c:when>
  </c:choose>
  <script>
    function search_order_history(search_period) {
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
      formObj.action = "${contextPath}/admin/order/adminOrderMain.do";
      formObj.submit();
    }

    function calcPeriod(search_period, baseDate) {
      const endDate = ((baseDate) => {
        if (baseDate) {
          return new Date(baseDate);
        } else {
          return new Date();
        }
      })(baseDate);

      let beginDate;
      if (search_period === 'today') {
        beginDate = endDate;
      } else if (search_period === 'one_week') {
        beginDate = dateFns.subWeeks(endDate, 1);
      } else if (search_period === 'two_week') {
        beginDate = dateFns.subWeeks(endDate, 2);
      } else if (search_period === 'one_month') {
        beginDate = dateFns.subMonths(endDate, 1);
      } else if (search_period === 'two_month') {
        beginDate = dateFns.subMonths(endDate, 2);
      } else if (search_period === 'three_month') {
        beginDate = dateFns.subMonths(endDate, 3);
      } else if (search_period === 'four_month') {
        beginDate = dateFns.subMonths(endDate, 4);
      }
      return {
        beginDate: dateFns.format(beginDate, 'YYYY-MM-DD'),
        endDate: dateFns.format(endDate, 'YYYY-MM-DD')
      };
    }

    function fn_modify_order_state(order_id, select_id) {
      const s_delivery_state = document.getElementById(select_id);
      const index = s_delivery_state.selectedIndex;
      const value = s_delivery_state[index].value;

      $.ajax({
        type: "post",
        async: false,
        url: "${contextPath}/admin/order/modifyDeliveryState.do",
        data: {
          order_id: order_id,
          "delivery_state": value
        },
        success: function (data) {
          if (data.trim() === 'mod_success') {
            alert("주문 정보를 수정했습니다.");
            location.href = "${contextPath}/admin/order/adminOrderMain.do";
          } else if (data.trim() === 'failed') {
            alert("다시 시도해 주세요.");
          }
        },
        error: function (data) {
          alert("에러가 발생했습니다." + data);
        },
        complete: function (data, textStatus) {
          //alert("작업을완료 했습니다");

        }
      }); //end ajax
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

    function fn_detail_order(order_id) {
      const formObj = document.createElement("form");
      const i_order_id = document.createElement("input");

      i_order_id.name = "order_id";
      i_order_id.value = order_id;

      formObj.appendChild(i_order_id);
      document.body.appendChild(formObj);
      formObj.method = "get";
      formObj.action = "${contextPath}/admin/order/orderDetail.do";
      formObj.submit();

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
      formObj.action = "${contextPath}/admin/order/adminOrderMain.do";
      formObj.submit();
    }
  </script>
</head>
<body>
<H3>주문 조회</H3>
<form name="frm_delivery_list" action="${contextPath}/admin/admin.do" method="post">
  <table>
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
        <a href="javascript:search_order_history('today')">
          <img src="${contextPath}/resources/image/btn_search_one_day.jpg">
        </a>
        <a href="javascript:search_order_history('one_week')">
          <img src="${contextPath}/resources/image/btn_search_1_week.jpg">
        </a>
        <a href="javascript:search_order_history('two_week')">
          <img src="${contextPath}/resources/image/btn_search_2_week.jpg">
        </a>
        <a href="javascript:search_order_history('one_month')">
          <img src="${contextPath}/resources/image/btn_search_1_month.jpg">
        </a>
        <a href="javascript:search_order_history('two_month')">
          <img src="${contextPath}/resources/image/btn_search_2_month.jpg">
        </a>
        <a href="javascript:search_order_history('three_month')">
          <img src="${contextPath}/resources/image/btn_search_3_month.jpg">
        </a>
        <a href="javascript:search_order_history('four_month')">
          <img src="${contextPath}/resources/image/btn_search_4_month.jpg">
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
          <option value="orderer_id" <c:if test="${search_type eq 'orderer_id'}">selected</c:if>>
            주문자아이디
          </option>
          <option value="orderer_name"
                  <c:if test="${search_type eq 'orderer_name'}">selected</c:if>>주문자이름
          </option>
          <option value="orderer_hp" <c:if test="${search_type eq 'orderer_hp'}">selected</c:if>>
            주문자휴대폰번호
          </option>
          <option value="goods_title" <c:if test="${search_type eq 'goods_title'}">selected</c:if>>
            주문상품품명
          </option>
        </select>
        <input type="text" size="30" name="t_search_word" value="${search_word}"
               onsubmit="return false;" disabled/>
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
    <tr style="background:#33ff00">
      <td class="fixed">주문번호</td>
      <td class="fixed">주문일자</td>
      <td>주문내역</td>
      <td>배송상태</td>
      <td>배송수정</td>
    </tr>
    <c:choose>
      <c:when test="${empty newOrderList}">
        <tr>
          <td colspan=5 class="fixed">
            <strong>주문한 상품이 없습니다.</strong>
          </td>
        </tr>
      </c:when>
      <c:otherwise>
        <c:forEach var="item" items="${newOrderList}" varStatus="i">
          <c:choose>
            <c:when test="${item.order_id != pre_order_id }">
              <c:choose>
                <c:when test="${item.delivery_state=='delivery_prepared' }">
                  <tr bgcolor="lightgreen">
                </c:when>
                <c:when test="${item.delivery_state=='finished_delivering' }">
                  <tr bgcolor="lightgray">
                </c:when>
                <c:otherwise>
                  <tr bgcolor="orange">
                </c:otherwise>
              </c:choose>
              <td width=10%>
                <a href="javascript:fn_detail_order('${item.order_id}')">
                  <strong>${item.order_id}</strong>
                </a>
              </td>
              <td width=20%>
                <strong>${item.pay_order_time }</strong>
              </td>
              <td width=50% align=left>
                <strong>주문자:${item.orderer_name}</strong><br>
                <strong>주문자 번화번호:${item.orderer_hp}</strong><br>
                <strong>수령자:${item.receiver_name}</strong><br>
                <strong>수령자
                  번화번호:${item.receiver_hp1}-${item.receiver_hp2}-${item.receiver_hp3}</strong><br>
                <strong>주문상품명(수량):${item.goods_title}(${item.order_goods_qty})</strong><br>
                <c:forEach var="item2" items="${newOrderList}" varStatus="j">
                  <c:if test="${j.index > i.index }">
                    <c:if test="${item.order_id ==item2.order_id}">
                      <strong>주문상품명(수량):${item2.goods_title}(${item2.order_goods_qty})</strong><br>
                    </c:if>
                  </c:if>
                </c:forEach>
              </td>
              <td width=10%>
                <select name="s_delivery_state${i.index }" id="s_delivery_state${i.index }">
                  <c:choose>
                    <c:when test="${item.delivery_state=='delivery_prepared' }">
                      <option value="delivery_prepared" selected>배송준비중</option>
                      <option value="delivering">배송중</option>
                      <option value="finished_delivering">배송완료</option>
                      <option value="cancel_order">주문취소</option>
                      <option value="returning_goods">반품</option>
                    </c:when>
                    <c:when test="${item.delivery_state=='delivering' }">
                      <option value="delivery_prepared">배송준비중</option>
                      <option value="delivering" selected>배송중</option>
                      <option value="finished_delivering">배송완료</option>
                      <option value="cancel_order">주문취소</option>
                      <option value="returning_goods">반품</option>
                    </c:when>
                    <c:when test="${item.delivery_state=='finished_delivering' }">
                      <option value="delivery_prepared">배송준비중</option>
                      <option value="delivering">배송중</option>
                      <option value="finished_delivering" selected>배송완료</option>
                      <option value="cancel_order">주문취소</option>
                      <option value="returning_goods">반품</option>
                    </c:when>
                    <c:when test="${item.delivery_state=='cancel_order' }">
                      <option value="delivery_prepared">배송준비중</option>
                      <option value="delivering">배송중</option>
                      <option value="finished_delivering">배송완료</option>
                      <option value="cancel_order" selected>주문취소</option>
                      <option value="returning_goods">반품</option>
                    </c:when>
                    <c:when test="${item.delivery_state=='returning_goods' }">
                      <option value="delivery_prepared">배송준비중</option>
                      <option value="delivering">배송중</option>
                      <option value="finished_delivering">배송완료</option>
                      <option value="cancel_order">주문취소</option>
                      <option value="returning_goods" selected>반품</option>
                    </c:when>
                  </c:choose>
                </select>
              </td>
              <td width=10%>
                <input type="button" value="배송수정"
                       onClick="fn_modify_order_state('${item.order_id}','s_delivery_state${i.index}')"/>
              </td>
              </tr>
            </c:when>
          </c:choose>
          <c:set var="pre_order_id" value="${item.order_id }"/>
        </c:forEach>
      </c:otherwise>
    </c:choose>
    <tr>
      <td colspan=8 class="fixed">
        <c:forEach var="page" begin="1" end="10" step="1">
          <c:if test="${section >1 && page==1 }">
            <a href="${contextPath}/admin/order/adminOrderMain.do?chapter=${section-1}&pageNum=${(section-1)*10 +1 }">&nbsp;&nbsp;</a>
          </c:if>
          <a href="${contextPath}/admin/order/adminOrderMain.do?chapter=${section}&pageNum=${page}">${(section-1)*10 +page } </a>
          <c:if test="${page ==10 }">
            <a href="${contextPath}/admin/order/adminOrderMain.do?chapter=${section+1}&pageNum=${section*10+1}">&nbsp;
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

