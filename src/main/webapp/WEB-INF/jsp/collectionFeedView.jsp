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

<style>
.bg-light-blue {
	background-color: #97ABC5;
}

.bg-white {
	background-color: #ffffff;
}

.bg-orange {
	background-color: #E68C00;
}

.text-white {
	color: white;
}

.text-black {
	color: black;
}

</style>
</head>
<body class="bg-light-blue">
	<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>

	<div class="container">
		<div class="col-md-12">
			<c:forEach items="${allContent}" var="content">
				<div class="bg-orange text-white row">
					<h3 class="text-center">${content.title}</h3>
					<br>

					<div class="well text-black">
						<c:choose>
							<c:when test="${content.typeName == 'Objava'}">
								<div id="${content.id}"></div>
								<script type="text/javascript">
									$("#${content.id}").html(
											'${content.editorHtml}');
									//document.getElementById(${post.id}).innerHTML='${post.editorHtml}';
								</script>
							</c:when>
							<c:when test="${content.typeName == 'Slika'}">
								<img
									src="/knezija/collections/${id}/content/${content.id}/image/500"
									alt="${content.title}">
							</c:when>
							<c:when test="${content.typeName == 'Video'}">
								<iframe width="560" height="315"
									src="https://www.youtube.com/embed/${content.extraParam}"
									frameborder="0" allowfullscreen></iframe>
							</c:when>
							<c:when test="${content.typeName == 'Audio'}">
								<audio controls>
									<source
										src="/knezija/collections/${id}/content/${content.id}/binary"
										type="${content.mimeType}">
								</audio>
							</c:when>
							<c:when test="${content.typeName == 'Dokument'}">
								<a
									href="/knezija/collections/${id}/content/${content.id}/binary"
									title="${content.title}"> <img
									src="/knezija/collections/${id}/content/${content.id}/image/250"
									alt="${content.title}">
								</a>
							</c:when>
						</c:choose>
					</div>
				</div>
				<br>
			</c:forEach>
			<c:if test="${not empty subCollections}">
			</c:if>
		</div>
	</div>

	<script>
		
	</script>
</body>
</html>