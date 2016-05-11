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
<c:choose>
	<c:when test="${isPricest}">
		<title>Prijava za prvu pričest</title>
	</c:when>
	<c:otherwise>
		<title>Prijava za krizmu</title>
	</c:otherwise>
</c:choose>
</head>

<style type="text/css">
</style>

<body>
	<p id="isDisabled" style="display: none;">${isFormDisabled}</p>
	<p id="isPricest" style="display: none;">${isPricest}</p>

	<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
	<div class="container">
		<h3 class="text-success">${successMessage}</h3>
		<br>
		<div class="row">
			<c:choose>
				<c:when test="${isPricest}">
					<h3 class="text-center">
						<b>UPISNICA ZA PRVU PRIČEST</b>
					</h3>
					<h4 class="text-center">
						<i>Upisnicu ispunjavaju roditelji</i>
					</h4>
				</c:when>
				<c:otherwise>
					<h3 class="text-center">
						<b>UPISNICA ZA KRIZMU</b>
					</h3>
					<h4 class="text-center">
						<i>Upisnicu ispunjavaju roditelji s krizmanikom</i>
					</h4>
				</c:otherwise>
			</c:choose>
			<br>
			<springForm:form id="form" role="form" method="POST"
				enctype="multipart/form-data" commandName="form" action="">
				<div class="col-md-12">
					<h4 class="text-center">
						<b>RODITELJI</b>
					</h4>
					<div class="form-group row">
						<div class="col-md-2">
							<label><i>Otac</i></label><br>
							<springForm:input type="text" class="form-control"
								placeholder="Unesite ime oca" path="imeOca" />
							<springForm:errors path="imeOca" cssClass="form-error" />
						</div>
						<div class="col-md-2">
							<label></label><br> <br>
							<springForm:input type="text" class="form-control"
								placeholder="Unesite prezime oca" path="prezimeOca" />
							<springForm:errors path="prezimeOca" cssClass="form-error" />
						</div>
						<div class="col-md-2">
							<label><i>Majka</i></label><br>
							<springForm:input type="text" class="form-control"
								placeholder="Unesite ime majke" path="imeMajke" />
							<springForm:errors path="imeMajke" cssClass="form-error" />
						</div>
						<div class="col-md-3">
							<label></label><br> <br>
							<springForm:input type="text" class="form-control"
								placeholder="DJEVOJAČKO prezime majke"
								path="djevojackoPrezimeMajke" />
							<springForm:errors path="djevojackoPrezimeMajke"
								cssClass="form-error" />
						</div>
						<div class="col-md-3">
							<label><i>Roditelji crkveno vjenčani:</i></label><br>
							<springForm:radiobutton path="crkvenoVjencani" value="DA" />
							DA
							<springForm:radiobutton path="crkvenoVjencani" value="NE" />
							NE
							<springForm:errors path="crkvenoVjencani" cssClass="form-error" />
						</div>
					</div>
					<br>

					<div class="form-group row">
						<div class="col-md-3">
							<label><i>Adresa</i></label><br>
							<springForm:input type="text" class="form-control"
								placeholder="Unesite adresu stanovanja" path="adresaStanovanja" />
							<springForm:errors path="adresaStanovanja" cssClass="form-error" />
						</div>
						<div class="col-md-3">
							<label><i>Svecenik nam dolazi na blagoslov obitelji:</i></label><br>
							<springForm:radiobutton path="svecenikDolaziNaBlagoslov"
								value="DA" />
							DA
							<springForm:radiobutton path="svecenikDolaziNaBlagoslov"
								value="NE" />
							NE
							<springForm:errors path="svecenikDolaziNaBlagoslov"
								cssClass="form-error" />
						</div>
					</div>

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
					<div class="col-md-4"></div>
					<br>

					<c:choose>
						<c:when test="${isPricest}">
							<h4 class="text-center">
								<b>PRVOPRIČESNIK</b>
							</h4>
						</c:when>
						<c:otherwise>
							<h4 class="text-center">
								<b>KRIZMANIK</b>
							</h4>
						</c:otherwise>
					</c:choose>
					<br>
					<div class="form-group row">
						<div class="col-md-3">
							<springForm:input type="text" class="form-control"
								placeholder="Unesite ime" path="imeDjeteta" />
							<springForm:errors path="imeDjeteta" cssClass="form-error" />
						</div>
						<div class="col-md-3">
							<springForm:input type="text" class="form-control"
								placeholder="Unesite prezime" path="prezimeDjeteta" />
							<springForm:errors path="prezimeDjeteta" cssClass="form-error" />
						</div>
					</div>

					<div class="form-group row">
						<div class="col-md-3">
							<label><i>Rođenje</i></label><br>
							<springForm:input type="date" class="form-control"
								placeholder="Unesite datum rođenja" path="datumRodjenja" />
							<springForm:errors path="datumRodjenja" cssClass="form-error" />
						</div>
						<div class="col-md-3">
							<label></label><br> <br>
							<springForm:input type="text" class="form-control"
								placeholder="Unesite mjesto rođenja" path="mjestoRodjenja" />
							<springForm:errors path="mjestoRodjenja" cssClass="form-error" />
						</div>
						<div class="col-md-3">
							<label><i>Krštenje</i></label><br>
							<springForm:input type="date" class="form-control"
								placeholder="Unesite datum krštenja" path="datumKrstenja" />
							<springForm:errors path="datumKrstenja" cssClass="form-error" />
						</div>
						<div class="col-md-3">
							<label></label><br> <br>
							<springForm:input type="text" class="form-control"
								placeholder="Unesite župu krštenja" path="zupaKrstenja" />
							<springForm:errors path="zupaKrstenja" cssClass="form-error" />
						</div>
					</div>
					<br>

					<div class="form-group row">
						<div class="col-md-3">
							<label><i>Škola</i></label><br>
							<springForm:input type="text" class="form-control"
								placeholder="Unesite naziv škole" path="skola" />
							<springForm:errors path="skola" cssClass="form-error" />
						</div>
						<div class="col-md-3">
							<label></label><br> <br>
							<springForm:input type="text" class="form-control"
								placeholder="Unesite razred" path="razred" />
							<springForm:errors path="razred" cssClass="form-error" />
						</div>
						<div class="col-md-2">
							<label><i>Pohađa školski vjeronauk:</i></label><br>
							<springForm:radiobutton path="pohadjaSkolskiVjeronauk" value="DA" />
							DA
							<springForm:radiobutton path="pohadjaSkolskiVjeronauk" value="NE" />
							NE
							<springForm:errors path="pohadjaSkolskiVjeronauk"
								cssClass="form-error" />
						</div>
						<div class="col-md-4">
							<label><i>Zahtjeva posebnu pažnju</i></label><br>
							<springForm:textarea type="text" class="form-control"
								placeholder="hiperaktivnost, prilagođeni program, alergije, zdravstveni problemi, napomene..."
								path="krizmanikZahtjevaPosebnuPaznju" />
							<springForm:errors path="krizmanikZahtjevaPosebnuPaznju"
								cssClass="form-error" />
						</div>
					</div>
					<br>
				</div>
				<br>
				<br>

				<c:if test="${not isFormDisabled}">
					<h4>
						VAŽNO: <b>dostaviti do 30. studenoga:</b>
					</h4>
					<label>Potvrda o krštenju pristupnika</label>(Vadi se isključivo u župnom uredu župe krštenja)
			<div class="form-group">
						<input type="file" name="potvrdaOKrstenju" />
						<springForm:errors path="potvrdaOKrstenju" cssClass="form-error" />
					</div>

					<c:if test="${not isPricest}">
						<label>Posvjedočenje za kumove</label>(Vadi se u župi trenutnog boravka kumova)
			<div class="form-group">
							<input type="file" name="posvjedocenjeZaKumove" />
							<springForm:errors path="posvjedocenjeZaKumove"
								cssClass="form-error" />
						</div>
					</c:if>

					<!-- Ako ovako stavite submit button, automatski će raditi -->
					<button type="submit" class="btn btn-primary">Spremi</button>
				</c:if>

				<!-- Submit whether it is pricest -->
				<c:if test="${isPricest}">
					<input style="display: none;" name="isPricest" value="true">
				</c:if>
			</springForm:form>
		</div>
	</div>

	<script>
		var isDisabledTag = $("#isDisabled");
		var isDisabledText = isDisabledTag.text();
		var isDisabled = isDisabledText != "" && isDisabledText != null;
		if (isDisabled) {
			$("input").attr("disabled", "disabled");
			$("form").attr("action", "");
			$("textarea").attr("disabled", "disabled");
			$("textarea").attr("placeholder", "");
		}

		var isPricestTag = $("#isPricest");
		var isPricestText = isPricestTag.text();
		var isPricest = isPricestText != "" && isPricestText != null;
		if (isPricest) {
			$("#form").attr("action",
					"/knezija/sakramenti/pricest/prijava/save");
		} else {
			$("#form")
					.attr("action", "/knezija/sakramenti/krizma/prijava/save");
		}
	</script>
</body>
</html>