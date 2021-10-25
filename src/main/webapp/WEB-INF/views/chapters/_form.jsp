<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_CHA.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>

<label for="${AttributeConst.CHAPTER_NAME.getValue()}">チャプター名</label><br />
<input type="text" name="${AttributeConst.CHAPTER_NAME.getValue()}" value="${chapter.chapterName}" />
<br /><br />

<label for="${AttributeConst.CHAPTER_SORT.getValue()}">ソート(半角数字で入力)</label><br />
<input type="text" name="${AttributeConst.CHAPTER_SORT.getValue()}" value="${chapter.sort}" />
<br /><br />

<input type="hidden" name="${AttributeConst.CHAPTER_ID.getValue()}" value="${chapter.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">投稿</button>