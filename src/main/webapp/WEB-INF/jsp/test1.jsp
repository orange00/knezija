<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>

<%@ page isELIgnored="false"%>
<!-- Java EL -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>

<c:import url="/html/include.html"></c:import>
<title>Stvori anketu</title>
</head>
<body>
     <jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
	<div class="container">
		<br>
		<form method="POST" action="/opp-webapp/api/stvoriAnketu">
		      <legend>Kreiranje ankete</legend>
			<div class="form-group">
				<label>Naziv</label> <input type="text" class="form-control"
					name="naziv">
			</div>
			<div class="form-group">
				<label>Datum otvaranja</label> <input type="date"
					class="form-control" name="datumPocetka">
			</div>
			<div class="form-group">
				<label>Datum zatvaranja</label> <input type="date"
					class="form-control" name="datumZavrsetka">
			</div>
			<div class="form-group">
				<label>Zapoceta</label> <input type="text" class="form-control"
					name="pocela" value="false" readonly>
			</div>
			<div class="form-group">
				<label>Zatvorena</label> <input type="text" class="form-control"
					name="zavrsila" value="false" readonly>
			</div>
			<div class="form-group">
<!-- 				<label>Rezultati objavljeni</label> <input type="text" -->
<!-- 					class="form-control" name="rezultatiObjavljeni" value="false" -->
<!-- 					readonly> -->
			        <select name="rezultatiObjavljeni"
                                        class="form-control">
                                        <option>true</option>
                                        <option>false</option>
                                </select>
			</div>
			<div class="form-group">
				<label>Autor</label> <input type="text" class="form-control"
					name="autor" value="" readonly>
			</div>
			<div class="form-group">
				<label>Vrsta ankete</label> <select name="vrstaAnkete"
					class="form-control">
					<option>Javna</option>
					<option>Anonimna</option>
				</select>
			</div>

			<br>
			<div class="col-md-5"></div>
			<div class="col-md-2">
				<button class="btn btn-primary btn-lg" type="submit">Spremi</button>
			</div>
			<div class="col-md-5"></div>
		</form>
	</div>
</body>

</html>