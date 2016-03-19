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

<script type="text/javascript">
	$(document).ready(function() {
		changeQuestion(1); 

		$("#getQuestionBtn").click(function() {
			question_id = $("#questionsCombo").val();
			changeQuestion(question_id);
		});
		
		$("#endTestBtn").click(function(){
			$("#endTestForm").submit();
		});
	});

	function changeQuestion(question_id) {
		var questionJson = new Object();
		questionJson.question_id = question_id;
		questionJson.test_id = ${questions.test_id};
		questionJson.student_id = ${questions.student_id};

		$.ajax({
			url : "ChangeQuestion",
			type : "post",
			data : JSON.stringify(questionJson),
			success : function(result) {
				//alert(result);
				$("#questionAndOptions").html(result);

			}
		});
	}
</script>
</head>
<body>

	<div class="container">
		<fieldset>
			<legend>${questions.testTitle}</legend>
			<div class="form-horizontal">
				<div class="form-group">
					<div class="col-lg-6">
						<h4>Questions</h4>
					</div>
					<div class="col-lg-3">
						<select class="form-control" name="questionsCombo"
							id="questionsCombo">
							<option value="Select">Select</option>
							<c:forEach items="${questions.questionNos}" var="testQuestionID">
								<option value="${testQuestionID}">${testQuestionID}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-lg-2">
						<button class="btn btn-primary" id="getQuestionBtn"
							onclick="return false;">GO</button>
					</div>
					<div class="col-lg-1 timerClock" ></div>

				</div>

				<div id="questionAndOptions"></div>
				
				<div class="form-horizontal">
					<div class="form-group">
						<form action="EndTest" id="endTestForm">
							<button class="col-lg-offset-9 btn btn-warning" id="endTestBtn"
								type="submit" onclick="return false;">End Test</button>
						</form>
					</div>
				</div>

				<form:form commandName="questions">
					<form:hidden path="student_id" id="student_idHidden" />
					<form:hidden path="test_id" id="test_idHidden" />
				</form:form>
			</div>
		</fieldset>
	</div>
</body>
</html>