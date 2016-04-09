<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>

<%@ page isELIgnored="false"%>
<!-- Java EL -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="springForm"
	uri="http://www.springframework.org/tags/form"%>


<html>
<head>
<%@ include file="/html/include.html"%>
<title>Prijava korisnika</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
	<div class="container">
		<br> <br>
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-5">
				<h3 class="text-danger">${loginFailedMessage}</h3>
				<br>
				<h4>Prijava</h4>
				<springForm:form id="form" role="form" method="POST"
					commandName="userLoginForm"
					action="/knezija//users/login-view/login">
					<div class="form-group">
						<label>Korisničko ime:</label>
						<springForm:input type="text" class="form-control"
							id="usernameRegistration" placeholder="Unesite korisničko ime"
							path="username" />
						<springForm:errors path="username" cssClass="form-error" />
					</div>
					<div class="form-group">
						<label>Lozinka:</label>
						<springForm:password class="form-control"
							placeholder="Unesite lozinku" path="password" />
						<springForm:errors path="password" cssClass="form-error" />
					</div>

					<!-- Ako ovako stavite submit button, automatski će raditi -->
					<button type="submit" class="btn btn-primary">Prijava</button>
				</springForm:form>

			</div>
			<div class="col-md-4"></div>
		</div>
	</div>

	<script>
		var isUpdate = $("#isUpdate");
		var isUpdateText = isUpdate.text();
		if (isUpdateText != "" && isUpdateText != null) {
			//is update == true
			$("#form").attr("action", $("#updateAction").text());
			$("input[id*='contentTypesCheckboxes']").attr("disabled",
					"disabled");

			$("input[id*='hiddenCheckboxes']").attr("type", "hidden");
			$("label[for*='hiddenCheckboxes']")
					.attr("style", "display : none;");
		}
	</script>
</body>
</html>
