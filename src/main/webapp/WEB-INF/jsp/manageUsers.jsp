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
<title>Admin</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
	<div class="container">
		<br>
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-6">
				<h3 class="text-info">${message}</h3>
			</div>
			<div class="col-md-3"></div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-5">
				<h4>Administriranje korisnika</h4>
				<form method="post" action="/knezija/users/manage/update">
					<div class="form-group">
						<label>Korisnici</label> <select name="chosenUsername"
							class="form-control">
							<c:forEach var="user" items="${users}">
								<option value="${user.username}">${user.username}</option>
							</c:forEach>
						</select> <br>
						<button type="submit" name="service" value="deleteUser"
							class="btn btn-primary">Izbriši korisnika</button>
						<button type="submit" name="service" value="updateData"
							class="btn btn-primary">Izmijeni podatke korisnika</button>
					</div>
				</form>
			</div>
			<div class="col-md-1"></div>
			<div class="col-md-5">
				<h4>Stvorite novog korisnika</h4>
				<springForm:form role="form" method="POST"
					action="/knezija/users/manage/create" commandName="userForm">
					<div class="form-group">
						<label>Korisničko ime:</label>
						<springForm:input type="text" class="form-control"
							id="usernameRegistration" placeholder="Unesite korisničko ime"
							path="username" />
						<!-- if the input is invalid the old input will be remembered and given as User attribute with name "form" -->
						<springForm:errors path="username" cssClass="form-error" />
					</div>
					<div class="form-group">
						<label>Lozinka:</label>
						<springForm:password class="form-control"
							placeholder="Unesite lozinku" path="password" />
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
					<button type="submit" class="btn btn-primary">Registracija</button>
				</springForm:form>
			</div>
			<div class="col-md-1"></div>
		</div>
	</div>
</body>
</html>
