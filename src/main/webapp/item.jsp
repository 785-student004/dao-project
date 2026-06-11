<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome shopping!</title>
</head>
<body>
	<jsp:include page="/menu.jsp" />

	<h3>商品詳細</h3>

	商品番号：
	<b>${items.code}</b>
	<br> 商品名：
	<b>${items.name}</b>
	<br> 価格(税込)：
	<b>${items.price}円</b>
	<br>
</body>
</html>