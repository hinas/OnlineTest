<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Crossover Online Test</title>
<link rel="stylesheet"
	href="<c:url value="/resources/css/bootstrap.min2.css"/>">
</head>
<body>

	<div class="container text-center">

		<h1 class="form-signin-heading">${homepage.testTitle}</h1>
		<h2 class="form-signin-heading">${homepage.testDescription}</h2>
		<form:form action="Login" method="post" commandName="homepage">
			<label for="username" class="sr-only">Username</label>
			<form:input type="text" name="username" id="username" path="username"
				class="form-control" placeholder="Username"></form:input>
			<label for="password" class="sr-only">Password</label>
			<form:input type="password" id="password" name="password"
				path="password" class="form-control" placeholder="Password"></form:input>
			<p class="text-warning">
				<c:out value="${homepage.errorMsg}"></c:out>
			</p>
			<form:hidden path="testID" name="testID" />
			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign
				in</button>
		</form:form>

</div> <!-- /container -->
	<script src="<c:url value="/resources/js/jquery.js"/>"></script>

</body>
</html>