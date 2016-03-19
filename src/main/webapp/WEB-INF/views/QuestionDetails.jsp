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
<script src="<c:url value="/resources/js/jquery.js"/>"></script>
<script type="text/javascript">

$(document).ready(function() {
	$("#submitAnsBtn").click(function(){
		
		var responseJson = new Object();
		responseJson.question_id = ${questionDetail.question_id};
		responseJson.test_id = $("#test_idHidden").val();
		responseJson.student_id = $("#student_idHidden").val();
		
		var selected = $("#currentOptions input[type='radio']:checked");
		if (selected.length > 0) {
			responseJson.option_no = selected.val();
		}else
		{
			alert("Please select option");
			return false;
		}
		
		$.ajax({
			url : "SubmitResponse",
			type : "post",
			data : JSON.stringify(responseJson),
			success : function(result) {
				//alert(result);
				$("#result").html(result);
				
			}
		}); 
		
	});
});
</script>
</head>
<body>

	<div class="container">
		<fieldset>
			<div class="form-horizontal">
				<div class="form-group">
					<div class="col-lg-12">
						<h3 class="form-signin-heading" id="currentQuestion">${questionDetail.question_txt}</h3>
					</div>
				</div>

				<div class="form-group">
					<div class="col-lg-12" id="currentOptions">
						<c:forEach
							items="${questionDetail.options}"
							var="options">
						
							<c:choose>
							  <c:when test="${options.option_no eq questionDetail.optionChoosed}">
							     <div class="radio">
									<label> <input name="optionsRadios"
										id="optionsRadios_${options.id}" value="${options.option_no}" 
										type="radio" checked="true">${options.option_txt}
									</label>
								</div>
							  </c:when>
							  <c:otherwise>
							     <div class="radio">
									<label> <input name="optionsRadios"
										id="optionsRadios_${options.id}" value="${options.option_no}" 
										type="radio">${options.option_txt}
									</label>
								</div>
							  </c:otherwise>
							</c:choose>
						</c:forEach>
					</div>
				</div>
				<button class="btn btn-primary" id="submitAnsBtn" >Submit Answer</button>
				<label id="result"></label>
				
			</div>
		</fieldset>
	</div>
</body>
</html>