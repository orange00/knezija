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
<script src="//cdn.ckeditor.com/4.5.8/standard-all/ckeditor.js"></script>
<title>Objava</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>

	<!-- exposing variables to the frontend -->
	<p id="updateAction" style="display: none;">/knezija/collections/${superCollectionId}/update-post-view/${id}/update</p>
	<p id="isUpdate" style="display: none;">${id}</p>

	<div class="container">
		<br>
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-5">
				<c:if test="${not empty contentUpdatedMessage}">
					<h3 class="text-info">${contentUpdatedMessage}</h3>
				</c:if>
				<h3 class="text-center">
					<c:choose>
						<c:when test="${not empty id}">Izmjena objave</c:when>
						<c:otherwise>Stvaranje objave</c:otherwise>
					</c:choose>
				</h3>

				<springForm:form id="form" method="POST"
					action="/knezija/collections/${superCollectionId}/create-post-view/create"
					commandName="postForm">
					<div class="form-group">
						<label>Naslov:</label>
						<springForm:input type="text" class="form-control"
							placeholder="Unesite naslov objave" path="title" />
						<springForm:errors path="title" cssClass="form-error" />
					</div>


					<springForm:textarea path="editorHtml"></springForm:textarea>
					<springForm:errors path="file" cssClass="form-error"></springForm:errors>
					<springForm:input path="contentLocation" value="Datoteka"
						style="display:none;" />
					<script>
						// Enable local plugins
						CKEDITOR.plugins.addExternal('youtube',
								'/knezija/ck/plugins/youtube/', 'plugin.js');
						CKEDITOR.plugins.addExternal('justify',
								'/knezija/ck/plugins/justify/', 'plugin.js');
						CKEDITOR.plugins.addExternal('font',
								'/knezija/ck/plugins/font/', 'plugin.js');
						CKEDITOR.plugins
								.addExternal('colordialog',
										'/knezija/ck/plugins/colordialog/',
										'plugin.js');
						CKEDITOR.plugins.addExternal('html5audio',
								'/knezija/ck/plugins/html5audio/', 'plugin.js');

						CKEDITOR.replace('editorHtml', {
							extraPlugins : 'youtube,justify,font,colordialog,html5audio',
							skin : 'office2013,/knezija/ck/skins/office2013/'
						});
					</script>

					<br>

					<div class="form-group">
						<label>Opis:</label>
						<springForm:input type="text" class="form-control"
							placeholder="Unesite opis objave" path="description" />
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
					<button type="submit" class="btn btn-primary">Spremi</button>
					<a href="/knezija/collections/${superCollectionId}"><button
							type="button" class="btn btn-primary">Prikaži mapu</button></a>
					<a id="deleteButton"
						href="/knezija/collections/${superCollectionId}/delete-content/${id}"><button
							type="button" class="btn btn-danger">Izbriši</button></a>
				</springForm:form>

			</div>
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

		if (!isUpdate) {
			//selects Javna
			$("#publicContent1").click();
			$("#contentLocation1").click();
			$("#deleteButton").hide();
		} else {
			$("[id*=contentLocation][checked*=checked]").click();
		}
	</script>
</body>
</html>
