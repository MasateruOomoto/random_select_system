<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>


<c:set var="action" value="${ForwardConst.ACT_RES.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commDel" value="${ForwardConst.CMD_DESTROY.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">


        <h2>問題番号の編集ページ</h2>

        <form method="POST"
                    action="<c:url value='?action=${action}&command=${commCrt}' />">

            <c:if test="${errors != null}">
                <div id="flush_error">
                    入力内容にエラーがあります。<br />
                    <c:forEach var="error" items="${errors}">
                        ・<c:out value="${error}" /><br />
                    </c:forEach>

                </div>
            </c:if>






            <c:forEach var="result" items="${results}" varStatus="status">
  　            問題番号<c:out value="${result.number}" /><br />
                <input type="radio" id="radio-<c:out value='${result.number}-ok' />" <c:if test="${result.answerFlag == AttributeConst.ROLE_MARU.getIntegerValue()}"> checked</c:if> name="${AttributeConst.ANSWER_FLAG.getValue()}-${result.number}" value = "0">解けた

                <input type="radio" id="radio-<c:out value='${result.number}-ng' />" <c:if test="${result.answerFlag == AttributeConst.ROLE_BATSU.getIntegerValue()}"> checked</c:if> name="${AttributeConst.ANSWER_FLAG.getValue()}-${result.number}" value = "1">解けなかった

                <input type="radio" id="radio-<c:out value='${result.number}-ng' />" <c:if test="${result.answerFlag == AttributeConst.ROLE_MADA.getIntegerValue()}"> checked</c:if> name="${AttributeConst.ANSWER_FLAG.getValue()}-${result.number}" value = "2">まだ解いていない
                <br />
            </c:forEach>














            <br />
            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
            <button type="submit">選択した問題番号を登録する</button>
        </form>

    </c:param>
</c:import>