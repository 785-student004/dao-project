<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン</title>
</head>
<body>
	<h1>ログイン</h1>

	<c:if test="${not empty message}">
		<p>${message}</p>
	</c:if>

	<form action="/dao-project/LoginServlet" method="post">
		メールアドレス：<input type="text" name="email" size="25"><br>
		パスワード：<input type="password" name="password" size="25"><br> <input
			type="hidden" name="action" value="login">
		<button>ログイン</button>
	</form>
</body>
</html>