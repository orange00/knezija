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
.image-wrap {
	display: inline-block;
	position: relative;
	text-align: center;
}

.upper-right {
	position: absolute;
	top: 0;
	right: 0;
}

.upper-left-center {
	position: absolute;
	top: 0;
	right: 50%;
}

.upper-half-right {
	position: absolute;
	top: 0;
	left: 20%;
}

.title-bottom {
	position: absolute;
	bottom: 0;
	left: 5%;
	color: white;
}

.bellow-30 {
	position: relative;
	bottom: -50px;
}

.bottom-right {
	position: absolute;
	right: 0;
	bottom: 0;
}
.bottom-left {
	position: absolute;
	left: 0;
	bottom: 0;
}

.btn-white-black-text {
	color: black;
}

.gallery-element {
	margin-bottom: 7.5px;
	margin-top: 7.5px;
	margin-left: 7.5px;
	margin-right: 7.5px;
}

body {
	background-color: #D3D3D3;
}

.left-margin {
	margin-left: 100px;
}

.content-type-description {
	position: absolute;
	top: 2%;
	left: 5%;
	color: graytext;
	font-size: large;
	font-family: cursive;
}
</style>
<title>Pregled mape</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
	<div class="left-margin">
		<div class="row">
			<div class="col-md-9">
				<h2>${collection.title}</h2>
			</div>
			<div class="col-md-1">
				<a href="/knezija/collections/${id}/create-content-view"><button
						class="btn btn-info">Dodaj sadržaj..</button></a>
			</div>
			<div class="col-md-2">
				<a href="/knezija/collections/${id}/create"><button
						class="btn btn-primary">Dodaj podmapu..</button></a>
			</div>
		</div>
		<!-- The Bootstrap Image Gallery lightbox, should be a child element of the document body -->
		<div id="blueimp-gallery" class="blueimp-gallery"
			data-use-bootstrap-modal="false">
			<!-- The container for the modal slides -->
			<div class="slides"></div>
			<!-- Controls for the borderless lightbox -->
			<h3 class="title"></h3>
			<a class="prev">‹</a> <a class="next">›</a> <a class="close">×</a> <a
				class="play-pause"></a>
			<ol class="indicator"></ol>
			<!-- The modal dialog, which will be used to wrap the lightbox content -->
			<div class="modal fade">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" aria-hidden="true">&times;</button>
							<h4 class="modal-title"></h4>
						</div>
						<div class="modal-body next"></div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default pull-left prev">
								<i class="glyphicon glyphicon-chevron-left"></i> Previous
							</button>
							<button type="button" class="btn btn-primary next">
								Next <i class="glyphicon glyphicon-chevron-right"></i>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div id="links">
			<c:forEach items="${subCollectionsList}" var="subCollection">
				<a href="/knezija/collections/${subCollection.id}"
					title="${subCollection.title}">
					<div class="image-wrap gallery-element">
						<img src="/knezija/images/folder-image"
							alt="${subCollection.title}"> <a
							href="/knezija/collections/${id}/update/${subCollection.id}"><button
								name="showHide" class="btn btn-primary upper-left-center">
								<b>Uredi</b>
							</button></a>
						<h6>
							<b>${subCollection.title}</b>
						</h6>
					</div>
				</a>
			</c:forEach>

			<c:forEach items="${contentList}" var="content">
				<c:choose>
					<c:when test="${content.type.typeName == 'Slika'}">
						<a href="/knezija/collections/${id}/content/${content.id}/binary"
							title="${content.title}" data-gallery>
							<div class="image-wrap gallery-element">
								<img
									src="/knezija/collections/${id}/content/${content.id}/image/300"
									alt="${content.title}"> <a
									href="/knezija/collections/${id}/update-content-view/${content.id}"><button
										name="showHide" class="btn btn-primary upper-right">
										<b>Uredi</b>
									</button></a>
								<h3 class="title-bottom text-center">
									<b>${content.title}</b>
								</h3>
							</div>
						</a>
					</c:when>
					<c:when test="${content.type.typeName == 'Video'}">
						<a href="${content.url}" type="text/html"
							data-youtube="${content.extraParam}"
							data-poster="/knezija/collections/${id}/content/${content.id}/image"
							title="${content.title}" data-gallery>
							<div class="image-wrap gallery-element">
								<img
									src="/knezija/collections/${id}/content/${content.id}/image/300"
									alt="${content.title}"> <a
									href="/knezija/collections/${id}/update-content-view/${content.id}"><button
										name="showHide" class="btn btn-primary upper-right">
										<b>Uredi</b>
									</button></a>
								<h3 class="title-bottom text-center">
									<b>${content.title}</b>
								</h3>
								<label class="content-type-description"> <i><b>Video</b></i>
								</label>
							</div>
						</a>
					</c:when>
					<c:when test="${content.type.typeName == 'Audio'}">
						<a title="${content.title}">
							<div class="image-wrap gallery-element">
								<audio controls>
									<source
										src="/knezija/collections/${id}/content/${content.id}/binary"
										type="${content.mimeType}">
								</audio>
								<a
									href="/knezija/collections/${id}/update-content-view/${content.id}"><button
										name="showHide" class="btn btn-primary bottom-right">
										<b>Uredi</b>
									</button></a> 
								<h5>
									<b>${content.title}</b>
								</h5>
							</div>
						</a>
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<!-- Documents are displayed at the bottom -->
			<c:forEach items="${contentList}" var="content">
				<c:if test="${content.type.typeName == 'Dokument'}">
					<a href="/knezija/collections/${id}/content/${content.id}/binary"
						title="${content.title}">
						<div class="image-wrap gallery-element bellow-30">
							<img
								src="/knezija/collections/${id}/content/${content.id}/image/100"
								alt="${content.title}"> <a
								href="/knezija/collections/${id}/update-content-view/${content.id}"><button
									name="showHide" class="btn btn-primary upper-half-right">
									<b>Uredi</b>
								</button></a>
							<h6>
								<b>${content.title}</b>
							</h6>
						</div>
					</a>
				</c:if>
			</c:forEach>
		</div>
	</div>
	<script>
		//treba selektirat sve slike u links
		$("div[class*='image-wrap']").mouseenter(function() {
			$(this).find("[name*='showHide']").show();
		});
		$("div[class*='image-wrap']").mouseleave(function() {
			$(this).find("[name*='showHide']").hide();
		});
		$("[name*='showHide']").hide();
	</script>
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script
		src="//blueimp.github.io/Gallery/js/jquery.blueimp-gallery.min.js"></script>
	<script src="/js/bootstrap-image-gallery.min.js"></script>
</body>
</html>
