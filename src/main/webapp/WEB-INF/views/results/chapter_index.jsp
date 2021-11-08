<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actWor" value="${ForwardConst.ACT_WOR.getValue()}" />
<c:set var="actCha" value="${ForwardConst.ACT_CHA.getValue()}" />
<c:set var="actNum" value="${ForwardConst.ACT_NUM.getValue()}" />
<c:set var="actRes" value="${ForwardConst.ACT_RES.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commIdxStu" value="${ForwardConst.CMD_INDEX_STUDENT.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2><c:out value="${workbook.workbookName}" />のチャプター一覧</h2>
        <table id="chapter_list">
            <tbody>
                <tr>
                    <th>単元名</th>
                </tr>
                <c:forEach var="chapter" items="${chapters}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td>
                            <a href="<c:url value='?action=${actRes}&command=${commIdx}&chapter_id=${chapter.id}' />"><c:out value="${chapter.chapterName}" /></a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${chapter_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((chapters_count - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='?action=${actNum}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p>
            <a href="<c:url value='?action=${actWor}&command=${commIdxStu}' />">問題集一覧に戻る</a>
        </p>

    </c:param>
</c:import>