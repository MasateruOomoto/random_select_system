<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actWor" value="${ForwardConst.ACT_WOR.getValue()}" />
<c:set var="actCha" value="${ForwardConst.ACT_CHA.getValue()}" />
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
        <h2>問題集一覧</h2>
        <table id="workbook_list">
            <tbody>
                <tr>
                    <th>科目名</th>
                    <th>問題集名</th>
                    <th>編集</th>
                </tr>
                <c:forEach var="workbook" items="${workbooks}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td>
                            <c:choose>
                                <c:when test="${workbook.workbookFlag == AttributeConst.ROLE_MATH.getValue()}">
                                    数学
                                </c:when>
                                <c:when test="${workbook.workbookFlag == AttributeConst.ROLE_LANGUAGE.getValue()}">
                                    国語
                                </c:when>
                                <c:when test="${workbook.workbookFlag == AttributeConst.ROLE_ENGLISH.getValue()}">
                                    英語
                                </c:when>
                                <c:when test="${workbook.workbookFlag == AttributeConst.ROLE_CHEMISTRY.getValue()}">
                                    化学
                                </c:when>
                                <c:when test="${workbook.workbookFlag == AttributeConst.ROLE_PHYSICS.getValue()}">
                                    物理
                                </c:when>
                                <c:when test="${workbook.workbookFlag == AttributeConst.ROLE_SOCIETY.getValue()}">
                                    社会
                                </c:when>
                            </c:choose>
                        </td>
                        <td>

                            <a href="<c:url value='?action=${actCha}&command=${commIdx}&workbook_id=${workbook.id}' />"><c:out value="${workbook.workbookName}" /></a>
                        </td>
                        <td><a href="<c:url value='?action=${actWor}&command=${commEdit}&workbook_id=${workbook.id}' />">編集する</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${workbook_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((workbooks_count - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='?action=${actWor}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='?action=${actWor}&command=${commNew}&workbook_id="${workbook.id}' />">新規問題集の登録</a></p>

    </c:param>
</c:import>