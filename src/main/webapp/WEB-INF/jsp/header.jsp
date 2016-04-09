<%@ page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" session="true" %>

<%@ page isELIgnored="false" %> <!-- Java EL -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<nav class="navbar navbar-default">
        <div class="container-fluid" style="background-color: #2C3E50;">

                <div class="navbar-header">
                        <a href="/opp-webapp/" class="navbar-brand"><strong>Sustav za upravljanje anketama</strong></a>
                </div>

            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

                    <ul class="nav navbar-nav navbar-left">
                        <li><a href="/opp-webapp/naslovnaStranica">Naslovna stranica</a></li>
                        <c:choose>
                          <c:when test="${sessionScope.korisnik.admin == true}">
                            <li><a href="/opp-webapp/admin">Upravljanje sustavom</a></li>
                          </c:when>
                        </c:choose>
                    </ul>



                        <ul class="nav navbar-nav navbar-right">
                        <c:choose>
                                <c:when test="${sessionScope.korisnik != null}">
                                        <li><p class="navbar-text" style="color: white;">Korisnik: <b>${sessionScope.korisnik.ime}</b></p></li>
                                        <li><a href="/opp-webapp/korisnik/profil?korisnickoIme=${sessionScope.korisnik.korisnickoIme}">Profil</a></li>
                                        <li>
                                                <form method="post" action="/opp-webapp/odjava">
                                                <button name="button" value="signout" class="btn btn-default navbar-btn" id="odjavaRight">Odjava</button>
                                                </form>
                                        </li>
                                </c:when>
                                <c:otherwise>
                                    <li><a href="/opp-webapp/stranicaZaPrijavu">Prijava / Registracija</a></li>
                                </c:otherwise>
                        </c:choose>
                        </ul>
        </div>
        </div>
</nav>
