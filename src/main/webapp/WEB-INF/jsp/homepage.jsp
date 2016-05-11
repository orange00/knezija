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
<script src="//cdn.ckeditor.com/4.5.8/standard/ckeditor.js"></script>
<title>Objava</title>

<style>
.bg-grey {
	background-color: #D3D3D3;
}

.bg-white {
	background-color: #ffffff;
}

.bg-royal-blue {
	background-color: #4169E1;
}

.bg-light-blue {
	background-color: #97ABC5;
}

.bg-orange {
	background-color: #E68C00;
	color: white;
}

.bg-facebook-blue {
	background-color: #3b5998;
}

.text-white {
	color: white;
}
</style>
</head>
<body class="bg-light-blue" background="${pictureUrl}">
	<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>

	<div class="container">
		<div class="col-md-6">
			<div class="row bg-orange">
				<h2 class="text-center">
					<label>Vijesti</label>
				</h2>
			</div>
			<br>
			<c:forEach items="${posts}" var="post">
				<div class="bg-white row">
					<h3 class="text-center">${post.title}</h3>
					<div class="col-md-3">
						<p>
							<span class="glyphicon glyphicon-time"></span>
							${post.lastUpdated}
						</p>
					</div>
					<div class="col-md-3">
						<p>
							<span class="glyphicon glyphicon-user"></span> ${post.author}
						</p>
					</div>
					<br>
					<div class="well">
						<div id="${post.id}"></div>
					</div>
					<script type="text/javascript">
						$("#${post.id}").html('${post.editorHtml}');
						//document.getElementById(${post.id}).innerHTML='${post.editorHtml}';
					</script>
				</div>
				<br>
			</c:forEach>
		</div>
		<div class="col-md-2"></div>
		<div class="col-md-4">
			<div class="row bg-facebook-blue">
				<h3 class="text-center text-white">
					<label>${parishInfoPost.title}</label>
				</h3>
				<br>
				<div class="well">
					<div id="${parishInfoPost.id}"></div>
				</div>
				<script type="text/javascript">
					$("#${parishInfoPost.id}").html(
							'${parishInfoPost.editorHtml}');
				</script>
			</div>
			<br>
			<div class="bg-facebook-blue row">
				<h3 class="text-center text-white">
					<label>${contactInfoPost.title}</label>
				</h3>
				<br>
				<div class="well">
					<div id="${contactInfoPost.id}"></div>
				</div>
				<script type="text/javascript">
					$("#${contactInfoPost.id}").html(
							'${contactInfoPost.editorHtml}');
				</script>
			</div>
		</div>
	</div>

</body>
</html>
