<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Crossover Online Test</title>
<link rel="stylesheet"
	href="<c:url value="/resources/css/bootstrap.min2.css"/>">
<!-- /container -->
<script src="<c:url value="/resources/js/jquery.js"/>"></script>
<!-- Bootstrap Core JavaScript -->
<script type="text/javascript">
$(document).ready(function(){
	$("#finishTestBtn").click(function(){

		$.ajax({
			url : "FinishTest",
			type : "get",
			success : function(result) {
				//alert(result);
				$("#contentHere").html(result);

			}
		});
	});
});


</script>
</head>
<body>

	<div class="container text-center" id="contentHere">
		<fieldset>
			<legend>You are about to finish the test</legend>
			<div class="form-horizontal">
				<div class="form-group">
					<label>Please review your submission</label>
				</div>

				<div class="form-group">
					<h4>Total Questions: ${result.totalQuestions}</h4>
					<h4>Total Answered: ${result.totalAnswered}</h4>
				</div>
				
				<div class="form-group">
					<button class="btn btn-warning" id="finishTestBtn" onclick="return false;">Finish Test</button>
				</div>
				
				<div class="form-group">
					<form action="goBackToQuestions">
						<button class="btn btn-warning" id="goBackBtn" type="submit">Go Back </button>
					</form>
				</div>
			</div>
		</fieldset>
	</div>
</body>
</html>