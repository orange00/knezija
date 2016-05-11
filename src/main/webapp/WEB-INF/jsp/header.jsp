<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>

<%@ page isELIgnored="false"%>
<!-- Java EL -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
.dropdown-submenu {
	position: relative;
}

.dropdown-submenu>.dropdown-menu {
	top: 0;
	left: 100%;
	margin-top: -6px;
	margin-left: -1px;
	-webkit-border-radius: 0 6px 6px 6px;
	-moz-border-radius: 0 6px 6px;
	border-radius: 0 6px 6px 6px;
}

.dropdown-submenu:hover>.dropdown-menu {
	display: block;
}

.dropdown-submenu>a:after {
	display: block;
	content: " ";
	float: right;
	width: 0;
	height: 0;
	border-color: transparent;
	border-style: solid;
	border-width: 5px 0 5px 5px;
	border-left-color: #ccc;
	margin-top: 5px;
	margin-right: -10px;
}

.dropdown-submenu:hover>a:after {
	border-left-color: #fff;
}

.dropdown-submenu.pull-left {
	float: none;
}

.dropdown-submenu.pull-left>.dropdown-menu {
	left: -100%;
	margin-left: 10px;
	-webkit-border-radius: 6px 0 6px 6px;
	-moz-border-radius: 6px 0 6px 6px;
	border-radius: 6px 0 6px 6px;
}
</style>

<nav class="navbar navbar-default">
	<div class="container-fluid" style="background-color: #2C3E50;">

		<div class="navbar-header">
			<a href="/opp-webapp/" class="navbar-brand"><strong>Župni
					portal</strong></a>
		</div>

		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">

			<ul class="nav navbar-nav navbar-left">
				<li><a href="/knezija/homepage">Naslovna stranica</a></li>
				<c:choose>
					<c:when test="${sessionScope.korisnik.admin == true}">
						<li><a href="/opp-webapp/admin">Upravljanje sustavom</a></li>
					</c:when>
				</c:choose>

				<ui:composition xmlns="http://www.w3.org/1999/xhtml"
					xmlns:ui="http://java.sun.com/jsf/facelets">
					<ui:include src="/WEB-INF/jsp/recursive.jsp">
						<ui:param name="collection" value="${tabsCollection}" />
					</ui:include>
				</ui:composition>


				<c:forEach items="${tabs}" var="tab">
					<c:choose>
						<c:when test="${not empty tab.subCollections}">
							<li class="dropdown"><a id="dLabel" data-toggle="dropdown"
								data-target="#" href=""> ${tab.title} <span class="caret"></span>
							</a>
								<ul class="dropdown-menu" role="menu"
									aria-labelledby="dropdownMenu">
									<c:forEach items="${tab.subCollections}" var="subTab">
										<li><a href="/knezija/collections/${subTab.id}/feed">${subTab.title}</a></li>
									</c:forEach>
								</ul></li>
						</c:when>
						<c:otherwise>
							<li><a id="dLabel" href="/knezija/collections/${tab.id}/feed">
									${tab.title} </a>
								<ul class="dropdown-menu" role="menu"
									aria-labelledby="dropdownMenu">
									<c:forEach items="${tab.subCollections}" var="subTab">
										<li><a href="/knezija/collections/${subTab.id}">${subTab.title}</a></li>
									</c:forEach>
								</ul></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>



					<li class="dropdown"><a id="dLabel" data-toggle="dropdown"
						data-target="#" href="/page.html"> Dropdown <span
							class="caret"></span>
					</a>
						<ul class="dropdown-menu multi-level" role="menu"
							aria-labelledby="dropdownMenu">
							<li><a href="#">Some action</a></li>
							<li><a href="#">Some other action</a></li>
							<li class="divider"></li>
							<li class="dropdown-submenu"><a tabindex="-1"
								href="/knezija/homepage">Naslovna</a>
								<ul class="dropdown-menu">
									<li><a tabindex="-1" href="#">Second level</a></li>
									<li class="dropdown-submenu"><a href="#">Even More..</a>
										<ul class="dropdown-menu">
											<li><a href="#">3rd level</a></li>
											<li><a href="#">3rd level</a></li>
										</ul></li>
									<li><a href="#">Second level</a></li>
									<li><a href="#">Second level</a></li>
								</ul></li>
						</ul></li>
			</ul>





			<ul class="nav navbar-nav navbar-right">
				<c:choose>
					<c:when test="${sessionScope.korisnik != null}">
						<li><p class="navbar-text" style="color: white;">
								Korisnik: <b>${sessionScope.korisnik.ime}</b>
							</p></li>
						<li><a
							href="/opp-webapp/korisnik/profil?korisnickoIme=${sessionScope.korisnik.korisnickoIme}">Profil</a></li>
						<li>
							<form method="post" action="/opp-webapp/odjava">
								<button name="button" value="signout"
									class="btn btn-default navbar-btn" id="odjavaRight">Odjava</button>
							</form>
						</li>
					</c:when>
					<c:otherwise>
						<li><a href="/opp-webapp/stranicaZaPrijavu">Kontakt</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
</nav>
