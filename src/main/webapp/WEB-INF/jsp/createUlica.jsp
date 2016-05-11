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
<title>Blagoslov - upravljanje ulicom</title>
</head>
<body>
	<!-- exposing variables to the frontend -->
	<p id="isUpdate" style="display: none;">${id}</p>

	<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
	<div class="container">
		<h3 class="text-success">${ulicaUpdatedMessage}</h3>
		<br>
		<h3 class="text-center">
			<c:choose>
				<c:when test="${not empty id}">IZMJENA INFORMACIJA O ULICI</c:when>
				<c:otherwise>DODAVANJE NOVE ULICE</c:otherwise>
			</c:choose>
		</h3>
		<br> <br>
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<springForm:form id="form" role="form" method="POST"
					enctype="multipart/form-data" commandName="form" action="">
					<div class="form-group col-md-6">
						<label>Naziv:</label>
						<springForm:input path="nazivUlice" class="form-control" />
						<springForm:errors path="nazivUlice" cssClass="form-error" />
						<br>
						<button type="submit" class="btn btn-primary">Spremi</button>
						<a id="updateButton" href="/knezija/blagoslov/ulice"><button
								type="button" class="btn btn-primary">Ulice i datumi</button></a>
					</div>
					<div class="form-group col-md-6">
						<label>Datum blagoslova:</label>
						<springForm:input type="date" class="form-control" path="datum" />
						<springForm:errors path="datum" cssClass="form-error" />
					</div>
				</springForm:form>
			</div>
			<div class="col-md-2"></div>
		</div>
	</div>

	<script>
		var isUpdateTag = $("#isUpdate");
		var isUpdateText = isUpdateTag.text();
		var isUpdate = isUpdateText != "" && isUpdateText != null;
		if (isUpdate) {
			//is update == true
			$("#form").attr(
					"action",
					"/knezija/blagoslov/ulice/update-ulica-view/"
							+ isUpdateText + "/save");
		} else {
			$("#form").attr("action",
					"/knezija/blagoslov/ulice/create-ulica-view/save");
		}
	</script>
</body>
</html>
