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

<style type="text/css">

.synchronized-with-success {
	background: green;
}

.synchronized-with-error {
	background-color: red;
}

.image-upload>input {
	display: none;
}

.left-screen-margin-50 {
	margin-left: 50px;
}

.left-screen-margin-40 {
	margin-left: 40px;
}

.left-screen-margin-15 {
	margin-left: 15px;
}

.nopadding {
	padding: 0 !important;
	margin: 0 !important;
}

.no-display {
	display: none;
}

.image-upload>input {
	display: none;
}
</style>
<c:choose>
	<c:when test="${isArchive}">
		<title>Arhiva - Blagoslov</title>
	</c:when>
	<c:otherwise>
		<title>Prijave za blagoslov</title>
	</c:otherwise>
</c:choose>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
	<p id="isArchive" class="no-display">${isArchive}</p>

	<div class="col-md-11"></div>
	<div class="col-md-1">
		<form id="syncForm" enctype="multipart/form-data" method="POST"
			action="">
			<div class="image-upload">
				<a id="syncAnnotation" href=""
					title="ažuriraj bazu iz excel(.xlsx) tablice"><img
					id="syncImage" src="/knezija/collections/11/content/49/image/40" /></a>
				<input id="file-input" type="file" name="excelFile" />
			</div>
		</form>
	</div>

	<c:if test="${not empty dateErrorMessage}">
		<h4 class="text-center text-danger">${dateErrorMessage}</h4>
		<br>
	</c:if>
	<c:if test="${not empty syncSuccessMessage}">
		<h4 class="text-center text-success">${syncSuccessMessage}</h4>
		<br>
	</c:if>
	<c:if test="${not empty excelFileError}">
		<h4 class="text-center text-danger">${excelFileError}</h4>
		<br>
	</c:if>
	<c:if test="${isArchive}">
		<h3 class="text-center">
			<b>Arhiva</b>
		</h3>
	</c:if>
	<h3 class="text-center">
		<b>PRIJAVE ZA BLAGOSLOV</b>
	</h3>
	<div class="row">
		<div class="col-md-5"></div>
		<div class="col-md-2">
			<div class="col-md-1"></div>
			<c:if test="${not isArchive}">
				<div class="col-md-3">
					<!-- 							excel icon -->
					<a href="/knezija/blagoslov/display-prijave/generate-excel-all"><img
						src="/knezija/collections/11/content/41/image/40"
						title="generiraj excel tablicu sa svim prijavama"></a>
				</div>
				<div class="col-md-3">
					<!-- 							email icon -->
					<a href="/knezija/blagoslov/display-prijave/send-email"><img
						src="/knezija/collections/11/content/42/image/40"
						title="posalji email svima"></a>
				</div>
				<!-- 							archive icon -->
				<div class="col-md-3">
					<a href="/knezija/blagoslov/display-prijave/archive"><img
						src="/knezija/collections/11/content/45/image/40"
						title="pogledaj arhivu"></a>
					<div class="col-md-2 nopadding"></div>
				</div>
			</c:if>
			<c:if test="${isArchive}">
				<div class="col-md-3">
					<!-- 							excel icon -->
					<a href="/knezija/blagoslov/display-prijave/archive/generate-excel-all"><img
						src="/knezija/collections/11/content/41/image/40"
						title="generiraj excel tablicu sa svim prijavama"></a>
				</div>
				<div class="col-md-3">
					<!-- 							email icon -->
					<a href="/knezija/blagoslov/display-prijave/archive/send-email"><img
						src="/knezija/collections/11/content/42/image/40"
						title="posalji email svima"></a>
				</div>
			</c:if>
		</div>
		<div class="col-md-5"></div>
	</div>
	<br>
	<div class="row">
		<div class="row">
			<div class="col-md-8">
				<div class="row left-screen-margin-50">
					<div class="col-md-1 nopadding">
						<label><i>Ime</i></label>
					</div>
					<div class="col-md-1 nopadding">
						<label><i>Prezime</i></label>
					</div>
					<div class="col-md-2 nopadding">
						<label><i>Telefon</i></label>
					</div>
					<div class="col-md-2 nopadding">
						<label><i>Ulica</i></label>
					</div>
					<div class="col-md-1 nopadding">
						<label><i>Datum</i></label>
					</div>
					<div class="col-md-2 nopadding">
						<label><i>Vrijeme</i></label>
					</div>
					<div class="col-md-1 nopadding">
						<label><i>Kućni broj</i></label>
					</div>
					<div class="col-md-1 nopadding">
						<label><i>Kat</i></label>
					</div>
					<div class="col-md-1 nopadding">
						<label><i>Broj stana</i></label>
					</div>
				</div>
			</div>
			<div class="col-md-4 row">
				<div class="row left-screen-margin-15">
					<div class="col-md-1"></div>
					<div class="col-md-2">
						<label><i>Akcije</i></label>
					</div>
				</div>
			</div>
		</div>

		<c:forEach items="${blagoslovi}" var="blagoslov">
			<div class="row">
				<div class="col-md-8">
					<div class="row left-screen-margin-40">
						<div class="col-md-1 nopadding">
							<input class="form-control input-sm"
								style="background:${blagoslov.inputColor};" readonly="readonly"
								value="${blagoslov.ime}">
						</div>
						<div class="col-md-1 nopadding">
							<input class="form-control input-sm"
								style="background:${blagoslov.inputColor};" readonly="readonly"
								value="${blagoslov.prezime}">
						</div>
						<div class="col-md-2 nopadding">
							<input class="form-control input-sm"
								style="background:${blagoslov.inputColor};" readonly="readonly"
								value="${blagoslov.brojTelefona}">
						</div>
						<div class="col-md-2 nopadding">
							<input class="form-control input-sm"
								style="background:${blagoslov.inputColor};" readonly="readonly"
								value="${blagoslov.imeUlice}">
						</div>
						<div class="col-md-1 nopadding">
							<input class="form-control input-sm"
								style="background:${blagoslov.inputColor};" readonly="readonly"
								value="${blagoslov.datum}">
						</div>
						<div class="col-md-2 nopadding">
							<input class="form-control input-sm"
								style="background:${blagoslov.inputColor};" readonly="readonly"
								value="${blagoslov.vrijemeBlagoslova}">
						</div>
						<div class="col-md-1 nopadding">
							<input class="form-control input-sm"
								style="background:${blagoslov.inputColor};" readonly="readonly"
								value="${blagoslov.kucniBroj}">
						</div>
						<div class="col-md-1 nopadding">
							<input class="form-control input-sm"
								style="background:${blagoslov.inputColor};" readonly="readonly"
								value="${blagoslov.kat}">
						</div>
						<div class="col-md-1 nopadding">
							<input class="form-control input-sm"
								style="background:${blagoslov.inputColor};" readonly="readonly"
								value="${blagoslov.brojStana}">
						</div>
					</div>
				</div>
				<div class="col-md-4 nopadding">
					<div class="col-md-1"></div>
					<div class="col-md-2">
						<a href="/knezija/blagoslov/prijava/${blagoslov.id}"><button
								type="button" class="btn btn-info btn-sm">
								<b>Pregled prijave</b>
							</button></a>
					</div>
					<div class="col-md-1"></div>
					<div class="col-md-1">
						<!-- 							word icon -->
						<a href="/knezija/blagoslov/prijava/${blagoslov.id}/generate-word"><img
							src="/knezija/collections/11/content/29/image/40"
							title="generiraj word dokument"></a>
					</div>
					<div class="col-md-1">
						<!-- 							email icon -->
						<a href="/knezija/send-email/${blagoslov.email}"><img
							src="/knezija/collections/11/content/42/image/40"
							title="posalji email"></a>
					</div>
					<c:if test="${not isArchive}">
						<div class="col-md-1">
							<!-- 							send to archive icon -->
							<a
								href="/knezija/blagoslov/prijava/${blagoslov.id}/send-to-archive"><img
								src="/knezija/collections/11/content/45/image/40"
								title="arhiviraj"></a>
						</div>
					</c:if>
					<div class="col-md-1"></div>
					<div class="col-md-1">
						<!-- 							delete document icon -->
						<a href="/knezija/blagoslov/prijava/${blagoslov.id}/delete"><img
							src="/knezija/collections/11/content/46/image/40"
							title="ukloni prijavu"></a>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>

	<script>
		var archiveSyncAction = "/knezija/blagoslov/display-prijave/archive/synchronize";
		var activeRecordsSyncAction = "/knezija/blagoslov/display-prijave/synchronize";
	</script>
	<script src="/knezija/js/syncFormScript.js">
		
	</script>

</body>
</html>
