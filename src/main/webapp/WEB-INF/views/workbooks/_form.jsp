<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_WOR.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>

<label for="${AttributeConst.WORKBOOK_FLAG.getValue()}">科目名</label><br />
<select name="${AttributeConst.WORKBOOK_FLAG.getValue()}">
    <option value="${AttributeConst.ROLE_MATH.getValue()}"<c:if test="${workbook.workbookFlag == AttributeConst.ROLE_MATH.getValue()}"> selected</c:if>>数学</option>
    <option value="${AttributeConst.ROLE_LANGUAGE.getValue()}"<c:if test="${workbook.workbookFlag == AttributeConst.ROLE_LANGUAGE.getValue()}"> selected</c:if>>国語</option>
    <option value="${AttributeConst.ROLE_ENGLISH.getValue()}"<c:if test="${workbook.workbookFlag == AttributeConst.ROLE_ENGLISH.getValue()}"> selected</c:if>>英語</option>
    <option value="${AttributeConst.ROLE_CHEMISTRY.getValue()}"<c:if test="${workbook.workbookFlag == AttributeConst.ROLE_CHEMISTRY.getValue()}"> selected</c:if>>化学</option>
    <option value="${AttributeConst.ROLE_PHYSICS.getValue()}"<c:if test="${workbook.workbookFlag == AttributeConst.ROLE_PHYSICS.getValue()}"> selected</c:if>>物理</option>
    <option value="${AttributeConst.ROLE_SOCIETY.getValue()}"<c:if test="${workbook.workbookFlag == AttributeConst.ROLE_SOCIETY.getValue()}"> selected</c:if>>社会</option>
</select>
<br /><br />

<label for="${AttributeConst.WORKBOOK_NAME.getValue()}">問題集名</label><br />
<input type="text" name="${AttributeConst.WORKBOOK_NAME.getValue()}" value="${workbook.workbookName}" />
<br /><br />

<input type="hidden" name="${AttributeConst.WORKBOOK_ID.getValue()}" value="${workbook.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">投稿</button>