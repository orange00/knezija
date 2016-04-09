<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>

<%@ page isELIgnored="false"%>
<!-- Java EL -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form"%>


<html>
<head>
<%@ include file="/html/include.html"%>
<title>Izmjena korisnika</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
	<div class="container">
		<br> <br>
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-5">
				<h3 class="text-info">${userUpdatedMessage}</h3>
				<br>								
				<h4>Izmijenite korisnika</h4>
				<springForm:form role="form" method="POST"
					action="/knezija/users/manage/update/${id}/save" commandName="userForm">
					<div class="form-group">
						<label>Korisničko ime:</label>
						<springForm:input type="text" class="form-control"
							id="usernameRegistration" placeholder="Unesite korisničko ime"
							path="username" readonly="true"/>
						<springForm:errors path="username" cssClass="form-error" />
					</div>
					<div class="form-group">
						<label>Lozinka:</label>
						<springForm:password class="form-control" placeholder="Unesite lozinku"
							path="password" />
						<springForm:errors path="password" cssClass="form-error" />
					</div>
					<div class="form-group">
						<label>Ime:</label>
						<springForm:input type="text" class="form-control"
							placeholder="Unesite ime korisnika" path="firstName" />
						<springForm:errors path="firstName" cssClass="form-error" />
					</div>
					<div class="form-group">
						<label>Prezime:</label>
						<springForm:input type="text" class="form-control"
							placeholder="Unesite prezime korisnika" path="lastName" />
						<springForm:errors path="lastName" cssClass="form-error" />
					</div>
					<div class="form-group">
						<label>Titula:</label>
						<springForm:input type="text" class="form-control"
							placeholder="Unesite titulu korisnika, npr: Župnik" path="title" />
						<springForm:errors path="title" cssClass="form-error" />
					</div>
					<div class="form-group">
						<label>Opis:</label>
						<springForm:input type="text" class="form-control"
							placeholder="Unesite kratki opis korisnika" path="description" />
						<springForm:errors path="description" cssClass="form-error" />
					</div>
					<div class="form-group">
						<label>Prava pristupa:</label>
						<springForm:select class="form-control"
							placeholder="Unesite ime korisnika" path="role">
							<c:forEach var="role" items="${roles}">
								<option>${role.roleName}</option>
							</c:forEach>
						</springForm:select>
						<springForm:errors path="role" cssClass="form-error" />
					</div>


					<input type="text" value="true" name="adminreg"
						style="display: none;">

					<!-- Ako ovako stavite submit button, automatski će raditi -->
					<button type="submit" class="btn btn-primary">Spremi</button>
				</springForm:form>
			</div>
			<div class="col-md-4"></div>
		</div>
	</div>
</body>
</html>
