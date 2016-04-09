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
<title>Upravljanje mapama</title>
</head>
<body>
	<!-- exposing variables to the frontend -->
	<p id="updateAction" style="display: none;">/knezija/collections/${superCollectionId}/update/${id}/save</p>
	<p id="isUpdate" style="display: none;">${id}</p>

	<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
	<div class="container">
		<br> <br>
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-5">
				<h3 class="text-info">${collectionUpdatedMessage}</h3>
				<br>
				<h4>
					<c:choose>
						<c:when test="${not empty id}">Izmjena mape</c:when>
						<c:otherwise>Stvaranje mape</c:otherwise>
					</c:choose>
				</h4>

				<springForm:form id="form" role="form" method="POST"
					commandName="collectionForm"
					action="/knezija/collections/${superCollectionId}/create/save">
					<div class="form-group">
						<label>Naziv mape:</label>
						<springForm:input type="text" class="form-control"
							placeholder="Unesite naziv mape" path="title" />
						<springForm:errors path="title" cssClass="form-error" />
					</div>
					<div class="form-group">
						<label>Opis:</label>
						<springForm:input type="text" class="form-control"
							placeholder="Unesite opis mape" path="description" />
						<springForm:errors path="description" cssClass="form-error" />
					</div>
					<div class="form-group">
						<label>Autor:</label>
						<springForm:input type="text" class="form-control" path="author"
							readonly="true" />
					</div>
					<div class="form-group">
						<label>Tip sadržaja: </label>
						<springForm:checkboxes id="contentTypesCheckboxes"
							placeholder="Odaberite tipove sadržaja mape"
							path="collectionContentTypes" items="${allContentTypes}" />
						<springForm:errors path="collectionContentTypes"
							cssClass="form-error" />
					</div>
					<c:if test="${not empty id}">
						<springForm:checkboxes id="hiddenCheckboxes"
							placeholder="Odaberite tipove sadržaja mape"
							path="collectionContentTypes" items="${allContentTypes}" />
					</c:if>

					<div class="form-group">
						<label>Javnost: </label>
						<springForm:radiobuttons placeholder="Odaberite javnost mape"
							path="publicCollection" items="${publicPrivateNames}" />
						<springForm:errors path="publicCollection" cssClass="form-error" />
					</div>

					<!-- Ako ovako stavite submit button, automatski će raditi -->
					<button type="submit" class="btn btn-primary">Spremi</button>
					<c:if test="${not empty id}">
						<a href="/knezija/collections/${id}"><button type="button"
								class="btn btn-primary">Prikaži mapu</button></a>
					</c:if>
					<a id="deleteButton"
						href="/knezija/collections/${superCollectionId}/delete/${id}"><button
							type="button" class="btn btn-danger">Izbriši</button></a>
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
			//disabled makes the input not send value through form, 
			//so a hidden input is used
			$("input[id*='contentTypesCheckboxes']").attr("disabled",
					"disabled");

			//LABEL1

			//hide the "fake -  hidden" inputs used to send the values trough forms
			$("input[id*='hiddenCheckboxes']").attr("type", "hidden");
			$("label[for*='hiddenCheckboxes']")
					.attr("style", "display : none;");
		} else {
			//selects Javna
			$("#publicCollection1").click();
			$("#deleteButton").hide();

			//when creating a new collection, 
			//only allow types of the super collection
			//by hiding others
			//LABEL1
		}

		//LABEL1
		//hide the non checked inputs and labels
		var nonCheckedInputs = $("input[id*='contentTypesCheckboxes'][checked!='checked']");
		nonCheckedInputs.hide();
		//hide the labels, next siblings of inputs
		nonCheckedInputs.next().hide();
	</script>
</body>
</html>
