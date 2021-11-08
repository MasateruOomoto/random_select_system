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
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2><c:out value="${chapter.chapterName}" />の問題番号一覧</h2>
        <table id="result_list">
            <tbody>
                <tr>
                    <th>解けた問題</th>
                    <th>解けてない問題</th>
                </tr>
                <tr class="row${status.count % 2}">
                    <td>
                        <c:forEach var="result" items="${results}" varStatus="status">
                            <c:if test="${result.answerFlag == 0}">
                                <c:out value="${result.number}" />,
                            </c:if>
                        </c:forEach>
                    </td>

                    <td>
                        <c:forEach var="result" items="${results}" varStatus="status">
                            <c:if test="${result.answerFlag == 1}">
                                <c:out value="${result.number}" />,
                            </c:if>
                        </c:forEach>
                    </td>
                </tr>
            </tbody>
        </table>

        <a href="<c:url value='?action=${actRes}&command=${commEdit}' />">編集する</a>

        <div id="pagination">
            （全 ${result_count} 件）<br />
        </div>
        <p>
            <a href="<c:url value='?action=${actRes}&command=${commShow}' />">ランダムで表示する</a><br />
            <a href="<c:url value='?action=${actCha}&command=${commIdx}' />">チャプター一覧に戻る</a>
        </p>

    </c:param>
</c:import>