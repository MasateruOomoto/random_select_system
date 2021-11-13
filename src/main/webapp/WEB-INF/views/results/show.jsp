<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>


<c:set var="actRes" value="${ForwardConst.ACT_RES.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2><c:out value="${chapter.chapterName}" />の問題番号のランダム表示</h2>

        <p>問題番号<c:out value="${result.number}" /></p>

        <form method="POST"
                    action="<c:url value='?action=${actRes}&command=${commUpd}' />">

            <label for="${AttributeConst.ANSWER_FLAG.getValue()}">解けたかどうか</label><br />
            <select name="${AttributeConst.ANSWER_FLAG.getValue()}">
                <option value="${AttributeConst.ROLE_MARU.getIntegerValue()}"<c:if test="${result.answerFlag == AttributeConst.ROLE_MARU.getIntegerValue()}"> selected</c:if>>解けた</option>
                <option value="${AttributeConst.ROLE_BATSU.getIntegerValue()}"<c:if test="${result.answerFlag == AttributeConst.ROLE_BATSU.getIntegerValue()}"> selected</c:if>>解けていない</option>
            </select>
            <br />

            <button type="submit">再度ランダムで表示する</button>

            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
            <input type="hidden" name="${AttributeConst.RESULT.getValue()}" value="${result}" />

        </form>

        <p>
            <a href="<c:url value='?action=${actRes}&command=${commIdx}' />">問題番号一覧に戻る</a>
        </p>

    </c:param>
</c:import>