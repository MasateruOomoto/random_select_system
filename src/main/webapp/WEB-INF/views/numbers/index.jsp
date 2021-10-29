<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actWor" value="${ForwardConst.ACT_WOR.getValue()}" />
<c:set var="actCha" value="${ForwardConst.ACT_CHA.getValue()}" />
<c:set var="actNum" value="${ForwardConst.ACT_NUM.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2><c:out value="${chapter.chapterName}" />の問題番号一覧</h2>
        <table id="number_list">
            <tbody>
                <c:forEach var="number" items="${numbers}" varStatus="status">
                    <c:out value="${number.number}" />,
                </c:forEach>
            </tbody>
        </table>

        <a href="<c:url value='?action=${actNum}&command=${commEdit}' />">編集する</a>

        <div id="pagination">
            （全 ${number_count} 件）<br />
        </div>
        <p><a href="<c:url value='?action=${actNum}&command=${commNew}' />">新規問題番号の登録</a></p>

    </c:param>
</c:import>