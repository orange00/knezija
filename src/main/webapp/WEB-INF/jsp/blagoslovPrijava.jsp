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
<title>Prijava za blagoslov</title>
</head>

<style type="text/css">
</style>

<body>
	<p id="isDisabled" style="display: none;">${isFormDisabled}</p>

	<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
	<div class="container">
		<h3 class="text-success">${successMessage}</h3>
		<br>
		<div class="row">
			<h3 class="text-center">
				<b>PRIJAVA ZA BLAGOSLOV OBITELJI</b>
			</h3>
			<br>
			<springForm:form id="form" role="form" method="POST"
				enctype="multipart/form-data" commandName="form"
				action="/knezija/blagoslov/prijava/save">
				<div class="col-md-2"></div>
				<div class="col-md-10">
					<label><i>Osobni podaci</i></label>
					<div class="form-group row">
						<div class="col-md-4">
							<springForm:input type="text" class="form-control"
								placeholder="Unesite svoje ime" path="ime" />
							<springForm:errors path="ime" cssClass="form-error" />
						</div>
						<div class="col-md-4">
							<springForm:input type="text" class="form-control"
								placeholder="Unesite svoje prezime" path="prezime" />
							<springForm:errors path="prezime" cssClass="form-error" />
						</div>
					</div>
					<br>

					<div class="form-group row">
						<div class="col-md-4">
							<label><i>Ulica</i></label><br>
							<springForm:select id="ulicaSelect" path="imeUlice"
								class="form-control" onchange="ulicaSelected()">
								<c:forEach items="${ulice}" var="ulica">
									<option onclick="ulicaSelected" id="${ulica.datum}"
										value="${ulica.nazivUlice}">${ulica.nazivUlice}</option>
								</c:forEach>
							</springForm:select>
						</div>
						<div class="col-md-3">
							<label><i>Datum blagoslova</i></label><br> <input
								id="datumInput" class="form-control" disabled="disabled" />
						</div>
						<div class="col-md-3">
							<label><i>Kućni broj</i></label><br>
							<springForm:input type="number" class="form-control"
								placeholder="Unesite kućni broj" path="kucniBroj" />
							<springForm:errors path="kucniBroj" cssClass="form-error" />
						</div>
					</div>

					<div class="form-group row">
						<div class="col-md-3">
							<label><i>Kat</i></label><br>
							<springForm:input type="number" class="form-control"
								placeholder="Unesite kat" path="kat" />
							<springForm:errors path="kat" cssClass="form-error" />
						</div>
						<div class="col-md-3">
							<label><i>Broj stana</i></label><br>
							<springForm:input type="number" class="form-control"
								placeholder="Unesite broj stana" path="brojStana" />
							<springForm:errors path="brojStana" cssClass="form-error" />
						</div>
						<div class="col-md-6">
							<label><b>Vrijeme blagoslova:</b></label><br>
							<springForm:radiobutton path="vrijemeBlagoslova"
								value="prije podne" />
							Prije podne (9-13h)
							<springForm:radiobutton path="vrijemeBlagoslova"
								value="poslije podne" />
							Poslije podne (15:30-19h)<br> 
							<springForm:radiobutton path="vrijemeBlagoslova" value="ostalo"/>Ostalo:
							<springForm:input path="vrijemeBlagoslovaOstalo" />
							<springForm:errors path="vrijemeBlagoslova" cssClass="form-error" />
						</div>
					</div>
					<br>

					<div class="form-group row">
						<div class="col-md-4">
							<label><i>Kontakt</i></label><br>
							<springForm:input type="number" class="form-control"
								placeholder="Unesite kontakt telefon" path="brojTelefona" />
							<springForm:errors path="brojTelefona" cssClass="form-error" />
						</div>
						<div class="col-md-4">
							<label></label><br> <br>
							<springForm:input type="email" class="form-control"
								placeholder="Unesite email" path="email" />
							<springForm:errors path="email" cssClass="form-error" />
						</div>
					</div>
					<br>

					<div class="form-group row">
						<div class="col-md-6">
							<label><i>Napomene</i></label><br>
							<springForm:textarea type="text" class="form-control"
								placeholder="Unesite posebne napomene za blagoslov"
								path="napomene" />
							<springForm:errors path="napomene" cssClass="form-error" />
						</div>
					</div>

					<c:if test="${not isFormDisabled}">
						<!-- Ako ovako stavite submit button, automatski će raditi -->
						<button type="submit" class="btn btn-primary">Spremi</button>
					</c:if>
				</div>
			</springForm:form>
		</div>
	</div>

	<script>
		function ulicaSelected() {
			var date = $("#ulicaSelect option:selected").attr("id");
			$("#datumInput").attr("value", date);
		}

		var isDisabledTag = $("#isDisabled");
		var isDisabledText = isDisabledTag.text();
		var isDisabled = isDisabledText != "" && isDisabledText != null;
		if (isDisabled) {
			$("input").attr("disabled", "disabled");
			$("select").attr("disabled", "disabled");
			$("form").attr("action", "");
			$("textarea").attr("disabled", "disabled");
			$("textarea").attr("placeholder", "");
		}

		ulicaSelected();
	</script>
</body>
</html>