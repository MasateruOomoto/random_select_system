<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_NUM.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>

(半角英数字で入力してください)<br/>
<label for="${AttributeConst.FIRST_NUMBER.getValue()}"></label>
<input type="text" name="${AttributeConst.FIRST_NUMBER.getValue()}" value="${first_number}" />
～
<label for="${AttributeConst.LAST_NUMBER.getValue()}"></label>
<input type="text" name="${AttributeConst.LAST_NUMBER.getValue()}" value="${last_number}" />
の問題番号を追加する。<br/><br/>

<input type="hidden" name="${AttributeConst.NUMBER_ID.getValue()}" value="${number.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">追加</button>