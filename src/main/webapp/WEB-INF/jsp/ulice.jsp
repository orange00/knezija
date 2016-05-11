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
<title>Blagoslov - upravljanje ulicama</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
	<div class="container">
		<br>
		<h3 class="text-center">
			<b>ULICE ZA BLAGOSLOV</b>
		</h3>
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<h3 class="text-success">${ulicaUpdatedMessage}</h3>
				<br> <br>
				<div class="form-group col-md-6">
					<label><i>Naziv:</i></label> <select id="ulicaSelect" class="form-control"
						onchange="ulicaSelected()">
						<c:forEach items="${ulice}" var="ulica">
							<option onclick="ulicaSelected" id="${ulica.id}"
								value="${ulica.datum}">${ulica.nazivUlice}</option>
						</c:forEach>
					</select> <br> <a id="updateButton" href=""><button type="button"
							class="btn btn-primary">Uredi ulicu</button></a> <a
						href="/knezija/blagoslov/ulice/create-ulica-view"><button
							type="button" class="btn btn-primary">Dodaj novu ulicu</button></a>
				</div>
				<div class="form-group col-md-6">
					<label><i>Datum blagoslova:</i></label> <input id="datumInput"
						class="form-control" disabled="disabled" /> <br> <a
						id="deleteButton" href=""><button type="button"
							class="btn btn-danger">Ukloni ulicu</button></a>
				</div>
			</div>
			<div class="col-md-2"></div>
		</div>
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-6">
				<div class="row">
					<div class="col-md-1"></div>

				</div>
			</div>
			<div class="col-md-3"></div>
		</div>
	</div>

	<script>
		function ulicaSelected() {
			var id = $("#ulicaSelect option:selected").attr("id");
			var date = $( "#ulicaSelect option:selected" ).attr("value");
			$("#datumInput").attr("value", date);
			$("#deleteButton").attr("href",
					"/knezija/blagoslov/ulice/" + id + "/delete");
			$("#updateButton").attr("href",
					"/knezija/blagoslov/ulice/update-ulica-view/" + id);
		}
		
		ulicaSelected();
	</script>
</body>
</html>
