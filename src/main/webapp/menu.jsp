<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<a href="/dao-project/ShowItemServlet?action=top">ようこそ</a>
|
<c:forEach items="${categories}" var="category">
	<a
		href="/dao-project/ShowItemServlet?action=list&code=${category.code}&page=1">${category.name}</a>|
</c:forEach>

<a href="/dao-project/CartServlet?action=show">カートを見る</a>
<br>
<form action="/dao-project/ShowItemServlet" method="get">
	<input type="text" name="keyword" size="30"> <input
		type="hidden" name="action" value="search"><input
		type="hidden" name="page" value="1">
		
	<button>検索</button>
</form>