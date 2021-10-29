<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>


<c:set var="action" value="${ForwardConst.ACT_NUM.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commDel" value="${ForwardConst.CMD_DESTROY.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>問題番号の編集ページ</h2>

        <form method="POST"
                    action="<c:url value='?action=${action}&command=${commDel}' />">
            <c:forEach var="number" items="${numbers}" varStatus="status">
                <input type="checkbox" id="<c:out value='${number.number}' />" name="${AttributeConst.DELETE_NUMBER.getValue()}" name = "<c:out value='${number.number}' />">
                <label for="<c:out value='${number.number}' />"><c:out value="${number.number}" />,</label>
            </c:forEach>
            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
            <button type="submit">選択した問題番号を削除する</button>
        </form>

    </c:param>
</c:import>