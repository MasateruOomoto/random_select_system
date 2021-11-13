<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>


<c:set var="action" value="${ForwardConst.ACT_NUM.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commDel" value="${ForwardConst.CMD_DESTROY.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">


        <h2>問題番号の編集ページ</h2>

        <form method="POST"
                    action="<c:url value='?action=${action}&command=${commDel}' />">

            <c:if test="${errors != null}">
                <div id="flush_error">
                    入力内容にエラーがあります。<br />
                    <c:forEach var="error" items="${errors}">
                        ・<c:out value="${error}" /><br />
                    </c:forEach>

                </div>
            </c:if>

            <c:forEach var="number" items="${numbers}" varStatus="status">
                <input type="checkbox" id="<c:out value='${number.id}' />" name="${AttributeConst.DELETE_NUMBER_ID.getValue()}" value = "<c:out value='${number.id}' />">
                <label for="<c:out value='${number.id}' />"><c:out value="${number.number}" />,</label>
            </c:forEach>
            <br />
            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
            <button type="submit">選択した問題番号を削除する</button>
        </form>

        <p>
            <a href="<c:url value='?action=${action}&command=${commIdx}' />">一覧に戻る</a>
        </p>
    </c:param>
</c:import>