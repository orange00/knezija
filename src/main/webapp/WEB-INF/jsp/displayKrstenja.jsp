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

.no-display {
	display: none;
}

.left-screen-margin-20 {
	margin-left: 20px;
}

.left-screen-margin-15 {
	margin-left: 15px;
}
</style>
<c:choose>
	<c:when test="${isArchive}">
		<title>Arhiva - Krštenje</title>
	</c:when>
	<c:otherwise>
		<title>Zamolbe za krštenje</title>
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
	
	<br>
	<c:if test="${isArchive}">
		<h3 class="text-center">
			<b>Arhiva</b>
		</h3>
	</c:if>
	<h3 class="text-center">
		<b>ZAMOLBE ZA KRŠTENJE</b>
	</h3>
	<div class="row">
		<div class="col-md-5"></div>
		<div class="col-md-2">
			<div class="col-md-1"></div>
			<c:if test="${not isArchive}">
				<div class="col-md-3">
					<!-- 							excel icon -->
					<a
						href="/knezija/sakramenti/krstenje/display-prijave/generate-excel-all"><img
						src="/knezija/collections/11/content/41/image/40"
						title="generiraj excel tablicu sa svim upisnicama"></a>
				</div>
				<div class="col-md-3">
					<!-- 							email icon -->
					<a href="/knezija/sakramenti/krstenje/display-prijave/send-email"><img
						src="/knezija/collections/11/content/42/image/40"
						title="posalji email svima"></a>
				</div>
				<!-- 							archive icon -->
				<div class="col-md-3">
					<a href="/knezija/sakramenti/krstenje/display-prijave/archive"><img
						src="/knezija/collections/11/content/45/image/40"
						title="pogledaj arhivu"></a>
					<div class="col-md-2"></div>
				</div>
			</c:if>
			<c:if test="${isArchive}">
				<div class="col-md-3">
					<!-- 							excel icon -->
					<a
						href="/knezija/sakramenti/krstenje/display-prijave/archive/generate-excel-all"><img
						src="/knezija/collections/11/content/41/image/40"
						title="generiraj excel tablicu sa svim upisnicama"></a>
				</div>
				<div class="col-md-3">
					<!-- 							email icon -->
					<a
						href="/knezija/sakramenti/krstenje/display-prijave/archive/send-email"><img
						src="/knezija/collections/11/content/42/image/40"
						title="posalji email svima"></a>
				</div>
			</c:if>
		</div>
		<div class="col-md-5"></div>
	</div>
	<br>
	<div class="row">
		<div class="row left-screen-margin-20">
			<div class="col-md-8">
				<div class="col-md-2">
					<label><i>Ime djeteta</i></label>
				</div>
				<div class="col-md-2">
					<label><i>Prezime djeteta</i></label>
				</div>
				<div class="col-md-2">
					<label><i>Kontakt telefon</i></label>
				</div>
				<div class="col-md-3">
					<label><i>Datum prijave</i></label>
				</div>
				<div class="col-md-3">
					<label><i>Datum krštenja</i></label>
				</div>
			</div>
			<div class="col-md-4">
				<div class="col-md-6">
					<div class="col-md-6">
						<label><i>Akcije</i></label>
					</div>
				</div>
			</div>
		</div>

		<c:forEach items="${krstenja}" var="krstenje">
			<form role="form" method="POST"
				action="/knezija/sakramenti/krstenje/prijava/${krstenje.id}/save-datum-krstenja">
				<div class="row left-screen-margin-15">
					<div class="col-md-8">
						<div class="col-md-2">
							<input class="form-control"
								style="background:${krstenje.inputColor};" readonly="readonly"
								value="${krstenje.imeDjeteta}">
						</div>
						<div class="col-md-2">
							<input class="form-control"
								style="background:${krstenje.inputColor};" readonly="readonly"
								value="${krstenje.prezimeDjeteta}">
						</div>
						<div class="col-md-2">
							<input class="form-control"
								style="background:${krstenje.inputColor};" readonly="readonly"
								value="${krstenje.brojTelefona}">
						</div>
						<div class="col-md-3">
							<input class="form-control" type="date"
								style="background:${krstenje.inputColor};" readonly="readonly"
								value="${krstenje.datumPrijave}">
						</div>
						<div class="col-md-3">
							<input class="form-control" style="background:${krizma.inputColor};" type="date" name="datumKrstenja"
								value="${krstenje.datumKrstenja}">
						</div>
					</div>
					<div class="col-md-4">
						<div class="col-md-2">
							<button type="submit" class="btn btn-primary btn-sm">Spremi
							</button>
						</div>
						<div class="col-md-3">
							<a href="/knezija/sakramenti/krstenje/prijava/${krstenje.id}"><button
									type="button" class="btn btn-info btn-sm">
									<b>Pregled prijave</b>
								</button></a>
						</div>
						<div class="col-md-5">
							<div class="col-md-4">
								<!-- 							word icon -->
								<a
									href="/knezija/sakramenti/krstenje/prijava/${krstenje.id}/generate-word"><img
									src="/knezija/collections/11/content/29/image/40"
									title="generiraj word dokument"></a>
							</div>
							<div class="col-md-4">
								<!-- 							email icon -->
								<a href="/knezija/send-email/${krstenje.email}"><img
									src="/knezija/collections/11/content/42/image/40"
									title="posalji email"></a>
							</div>
							<c:if test="${not isArchive}">
								<div class="col-md-4">
									<!-- 							send to archive icon -->
									<a
										href="/knezija/sakramenti/krstenje/prijava/${krstenje.id}/send-to-archive"><img
										src="/knezija/collections/11/content/45/image/40"
										title="arhiviraj"></a>
								</div>
							</c:if>
						</div>
						<div class="col-md-1">
							<!-- 							delete document icon -->
							<a
								href="/knezija/sakramenti/krstenje/prijava/${krstenje.id}/delete"><img
								src="/knezija/collections/11/content/46/image/40"
								title="ukloni prijavu"></a>
						</div>
					</div>
				</div>
			</form>
		</c:forEach>
	</div>

	<script>
		var archiveSyncAction = "/knezija/sakramenti/krstenje/display-prijave/archive/synchronize";
		var activeRecordsSyncAction = "/knezija/sakramenti/krstenje/display-prijave/synchronize";
	</script>
	<script src="/knezija/js/syncFormScript.js">
		
	</script>
</body>
</html>
