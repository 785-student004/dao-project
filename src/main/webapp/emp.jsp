<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>empテーブル検索</title>
</head>
<body>
	<form action="/dao-project/EmpServlet" method="post">
		年齢の下限(必須) <input type="text" name="minAge" size="8" value="${minAge}">，
		年齢の上限(必須) <input type="text" name="maxAge" size="8" value="${maxAge}"> 
		<input type="hidden" name="action" value="between">
		<button>検索</button>
	</form>
	<form action="/dao-project/EmpServlet" method="post">
		上位<input type="text" name="numPeople" size="8" value="${numPeople}"> 
		<input type="hidden" name="action" value="limit">
		<button>検索</button>
	</form>
	<hr>
	<c:if test="${not empty employees}">
		<table border="1">
			<tr>
				<th>No.</th>
				<th>名前</th>
				<th>年齢</th>
				<th>電話番号</th>
			</tr>
			<c:forEach items="${employees}" var="employee">
				<tr>
					<td>${employee.code}</td>
					<td>${employee.name}</td>
					<td>${employee.age}</td>
					<td>${employee.tel}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>