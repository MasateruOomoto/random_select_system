<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actTop" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="actUser" value="${ForwardConst.ACT_USER.getValue()}" />
<c:set var="actWor" value="${ForwardConst.ACT_WOR.getValue()}" />
<c:set var="actAuth" value="${ForwardConst.ACT_AUTH.getValue()}" />
<c:set var="actRan" value="${ForwardConst.ACT_RAN.getValue()}" />

<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commOut" value="${ForwardConst.CMD_LOGOUT.getValue()}" />

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
    <title><c:out value="ランダムセレクトシステム" /></title>
    <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
    <link rel="stylesheet" href="<c:url value='/css/style.css' />">
</head>
<body>
    <div id="wrapper">
        <div id="header">
            <div id="header_menu">
                <h1><a href="<c:url value='/?action=${actTop}&command=${commIdx}' />">ランダムセレクトシステム</a></h1>&nbsp;&nbsp;&nbsp;
                <c:if test="${sessionScope.login_user != null}">
                    <c:if test="${sessionScope.login_user.adminFlag == AttributeConst.ROLE_ADMIN.getIntegerValue()}">
                        <a href="<c:url value='?action=${actUser}&command=${commIdx}' />">ユーザー管理</a>&nbsp;
                        <a href="<c:url value='?action=${actWor}&command=${commIdx}' />">問題集管理</a>&nbsp;
                    </c:if>
                    <a href="<c:url value='?action=${actRan}&command=${commIdx}' />">問題管理</a>&nbsp;
                </c:if>
            </div>
            <c:if test="${sessionScope.login_user != null}">
                <div id="employee_name">
                    <c:out value="${sessionScope.login_user.userId}" />
                    &nbsp;さん&nbsp;&nbsp;&nbsp;
                    <a href="<c:url value='?action=${actAuth}&command=${commOut}' />">ログアウト</a>
                </div>
            </c:if>
        </div>
        <div id="content">${param.content}</div>
        <div id="footer">by Masateru Omoto.</div>
    </div>
</body>
</html>