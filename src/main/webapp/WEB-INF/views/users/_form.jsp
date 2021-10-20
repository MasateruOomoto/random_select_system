<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_USER.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>

<label for="${AttributeConst.USER_USER_ID.getValue()}">ユーザーID</label><br />
<input type="text" name="${AttributeConst.USER_USER_ID.getValue()}" value="${user.userId}" />
<br /><br />
<label for="${AttributeConst.USER_PASS.getValue()}">パスワード</label><br />
<input type="password" name="${AttributeConst.USER_PASS.getValue()}" />
<br /><br />

<label for="${AttributeConst.USER_ADMIN_FLG.getValue()}">権限</label><br />
<select name="${AttributeConst.USER_ADMIN_FLG.getValue()}">
    <option value="${AttributeConst.ROLE_GENERAL.getIntegerValue()}"<c:if test="${user.adminFlag == AttributeConst.ROLE_GENERAL.getIntegerValue()}"> selected</c:if>>一般</option>
    <option value="${AttributeConst.ROLE_ADMIN.getIntegerValue()}"<c:if test="${user.adminFlag == AttributeConst.ROLE_ADMIN.getIntegerValue()}"> selected</c:if>>管理者</option>
</select>
<br /><br />

<input type="hidden" name="${AttributeConst.USER_ID.getValue()}" value="${user.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">投稿</button>