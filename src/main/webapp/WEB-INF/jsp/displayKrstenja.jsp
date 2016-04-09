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
.left-screen-margin-20 {
	margin-left: 20px;
}

.left-screen-margin-15 {
	margin-left: 15px;
}
</style>
<title>Prijave za krštenja</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
	<h4 class="text-center text-danger">${dateErrorMessage}</h4>
	<br>
	<h3 class="text-center">
		<b>ZAMOLBE ZA KRŠTENJE</b>
	</h3>
	<div class="row">
		<div class="col-md-5"></div>
		<div class="col-md-2">
			<div class="col-md-2"></div>
			<div class="col-md-4">
				<!-- 							excel icon -->
				<a href="/knezija/sakramenti/prijave/krstenje/generate-excel-all"><img
					src="/knezija/collections/11/content/41/image/40"
					title="generiraj excel tablicu sa svim zamolbama"></a>
			</div>
			<div class="col-md-4">
				<!-- 							email icon -->
				<a href="/knezija/send-email/krstenje"><img
					src="/knezija/collections/11/content/42/image/40"
					title="posalji email svima"></a>
			</div>
			<div class="col-md-2"></div>
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
					<label><i>OIB djeteta</i></label>
				</div>
				<div class="col-md-3">
					<label><i>Datum prijave</i></label>
				</div>
				<div class="col-md-3">
					<label><i>Datum krštenja</i></label>
				</div>
			</div>
			<div class="col-md-4">
				<div class="col-md-3">
					<label><i>Akcije</i></label>
				</div>
			</div>
		</div>

		<c:forEach items="${krstenja}" var="krstenje">
			<form role="form" method="POST"
				action="/knezija/sakramenti/krstenje/prijava/${krstenje.id}/save-datum-krstenja">
				<div class="row left-screen-margin-15">
					<div class="col-md-8">
						<div class="col-md-2">
							<input class="form-control" disabled="disabled"
								value="${krstenje.imeDjeteta}">
						</div>
						<div class="col-md-2">
							<input class="form-control" disabled="disabled"
								value="${krstenje.prezimeDjeteta}">
						</div>
						<div class="col-md-2">
							<input class="form-control" disabled="disabled"
								value="${krstenje.OIBDjeteta}">
						</div>
						<div class="col-md-3">
							<input class="form-control" type="date" disabled="disabled"
								value="${krstenje.datumPrijave}">
						</div>
						<div class="col-md-3">
							<input class="form-control" type="date" name="datumKrstenja"
								value="${krstenje.datumKrstenja}">
						</div>
					</div>
					<div class="col-md-4">
						<div class="col-md-3">
							<button type="submit" class="btn btn-primary">Spremi
								datum</button>
						</div>
						<div class="col-md-4">
							<a href="/knezija/sakramenti/prijave/krstenje/${krstenje.id}"><button
									type="button" class="btn btn-info">
									<b>Pregled prijave</b>
								</button></a>
						</div>
						<div class="col-md-1">
							<!-- 							word icon -->
							<a
								href="/knezija/sakramenti/prijave/krstenje/${krstenje.id}/generate-word"><img
								src="/knezija/collections/11/content/29/image/40"
								title="generiraj word dokument"></a>
						</div>
						<div class="col-md-1">
							<!-- 							email icon -->
							<a href="/knezija/send-email/${krstenje.email}"><img
								src="/knezija/collections/11/content/42/image/40"
								title="posalji email"></a>
						</div>
					</div>
				</div>
			</form>
		</c:forEach>
	</div>
</body>
</html>
