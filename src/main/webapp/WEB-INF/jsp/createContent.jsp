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
<title>Upravljanje sadržajem</title>
</head>
<body>
	<!-- exposing variables to the frontend -->
	<p id="updateAction" style="display: none;">/knezija/collections/${superCollectionId}/update-content-view/${id}/update</p>
	<p id="isUpdate" style="display: none;">${id}</p>

	<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
	<div class="container">
		<br>
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-5">
				<h3 class="text-info">${contentUpdatedMessage}</h3>
				<br>
				<h3 class="text-center">
					<c:choose>
						<c:when test="${not empty id}">Izmjena sadržaja</c:when>
						<c:otherwise>Stvaranje sadržaja</c:otherwise>
					</c:choose>
				</h3>

				<springForm:form id="form" role="form" method="POST"
					enctype="multipart/form-data" commandName="contentForm"
					action="/knezija/collections/${superCollectionId}/create-content-view/create">
					<div class="form-group">
						<label>Naziv:</label>
						<springForm:input type="text" class="form-control"
							placeholder="Unesite naziv sadržaja" path="title" />
						<springForm:errors path="title" cssClass="form-error" />
					</div>
					<div class="form-group">
						<label>Opis:</label>
						<springForm:input type="text" class="form-control"
							placeholder="Unesite opis sadržaja" path="description" />
						<springForm:errors path="description" cssClass="form-error" />
					</div>
					<div class="form-group">
						<label>Autor:</label>
						<springForm:input type="text" class="form-control" path="author"
							readonly="true" />
					</div>

					<div class="form-group">
						<label>Javnost: </label>
						<springForm:radiobuttons id="publicContent" path="publicContent"
							items="${publicPrivateNames}" />
						<springForm:errors path="publicContent" cssClass="form-error" />
					</div>

					<!-- izbor između urla i datoteke u obliku radio buttona, prikaže se input za jedno ili drugo -->
					<div class="form-group">
						<label>Odaberite lokaciju sadržaja:</label>
						<springForm:radiobuttons id="contentLocation"
							items="${urlOrFileNames}" path="contentLocation" />
					</div>

					<div class="form-group">
						<label id="urlLabel">Url:</label>
						<springForm:input id="urlInput" type="text" class="form-control"
							placeholder="Unesite url sadržaja" path="url" />
						<p id="urlVideoWarning" class="text-warning">
							Za video unesite "Dijeli" link sa youtubea</a>
						</p>
					</div>
					<springForm:errors path="url" cssClass="form-error" />

					<div class="form-group">
						<label id="fileLabel">Odaberite datoteku: </label> <input
							id="fileInput" type="file" name="file" />
					</div>
					<springForm:errors path="file" cssClass="form-error" />

					<!-- Ako ovako stavite submit button, automatski će raditi -->
					<button type="submit" class="btn btn-primary">Spremi</button>
					<a href="/knezija/collections/${superCollectionId}"><button
							type="button" class="btn btn-primary">Prikaži mapu</button></a>
					<a id="downloadButton"
						href="/knezija/collections/${superCollectionId}/content/${id}/binary"><button
							type="button" class="btn btn-success">
							<b>Preuzimanje</b>
						</button></a>
					<a id="deleteButton"
						href="/knezija/collections/${superCollectionId}/delete-content/${id}"><button
							type="button" class="btn btn-danger">Izbriši</button></a>
				</springForm:form>

			</div>
			<div class="col-md-4"></div>
		</div>
	</div>

	<script>
		var isUpdateTag = $("#isUpdate");
		var isUpdateText = isUpdateTag.text();
		var isUpdate = isUpdateText != "" && isUpdateText != null;
		if (isUpdate) {
			//is update == true
			$("#form").attr("action", $("#updateAction").text());
		}

		//hides and shows url and file box, 
		//after pressing on the radiobutton
		$("#contentLocation1").click(function() {
			$("[id*=file]").hide();
			$("[id*=url]").show();
		});
		$("#contentLocation2").click(function() {
			$("[id*=url]").hide();
			$("[id*=file]").show();
		});

		if (!isUpdate) {
			//selects Javna
			$("#publicContent1").click();
			$("#contentLocation1").click();
			$("#deleteButton").hide();
			$("#downloadButton").hide();
		} else {
			$("[id*=contentLocation][checked*=checked]").click();
		}
	</script>
</body>
</html>
