<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品一覧</title>
</head>
<body>

	<jsp:include page="/menu.jsp" />

	<h3>商品一覧</h3>
	<br>
	<p>${count}件の商品が存在しました</p>

	<c:forEach items="${items}" var="item">
		<form action="/dao-project/CartServlet?action=add" method="post">
			<input type="hidden" name="item_code" value="${item.code}">
			商品番号：<b>${item.code}</b><br> 商品名：<b>${item.name}</b><br>
			価格(税込)：<b>${item.price}円</b><br> 個数： <select name="quantity">
				<option value="1">1
				<option value="2">2
				<option value="3">3
				<option value="4">4
				<option value="5">5
			</select> 個<br> <a
				href="/dao-project/ShowItemServlet?action=detail&code=${item.code}">詳細</a><br>
			<button>カートに追加</button>
		</form>
	</c:forEach>
	<c:if test="${not empty categoryCode}">
		<c:forEach begin="1" end="${maxPage}" var="pagecount">
			<a
				href="/dao-project/ShowItemServlet?action=list&code=${categoryCode}&page=${pagecount}">${pagecount}</a>
		</c:forEach>
	</c:if>
	<c:if test="${not empty keyword}">
		<c:forEach begin="1" end="${maxPage}" var="pagecount">
			<a
				href="/dao-project/ShowItemServlet?action=search&keyword=${keyword}&page=${pagecount}">${pagecount}</a>
		</c:forEach>
	</c:if>

</body>
</html>