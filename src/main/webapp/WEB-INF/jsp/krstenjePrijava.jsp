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
<title>Prijava za krštenje</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
	<div class="container">
		<h3 class="text-success">${successMessage}</h3>
		<br>
		<div class="row">
			<h3 class="text-center">
				<b>ZAMOLBA ZA KRŠTENJE DJETETA</b>
			</h3>
			<br>
			<div class="col-md-12">
				<springForm:form id="form" role="form" method="POST"
					enctype="multipart/form-data" commandName="krstenjeForm"
					action="/knezija/prijave/krstenje/spremi">
					<label><i>Otac</i></label>
					<div class="form-group row">
						<div class="col-md-2">
							<springForm:input type="text" class="form-control"
								placeholder="Unesite ime oca" path="imeOca" />
							<springForm:errors path="imeOca" cssClass="form-error" />
						</div>
						<div class="col-md-2">
							<springForm:input type="text" class="form-control"
								placeholder="Unesite prezime oca" path="prezimeOca" />
							<springForm:errors path="prezimeOca" cssClass="form-error" />
						</div>
						<div class="col-md-2">
							<springForm:input type="text" class="form-control"
								placeholder="Unesite vjeru oca" path="vjeraOca" />
							<springForm:errors path="vjeraOca" cssClass="form-error" />
						</div>
						<div class="col-md-3">
							<springForm:input type="text" class="form-control"
								placeholder="Unesite narodnost oca" path="narodnostOca" />
							<springForm:errors path="narodnostOca" cssClass="form-error" />
						</div>
						<div class="col-md-3">
							<springForm:input type="text" class="form-control"
								placeholder="Unesite zanimanje oca" path="zanimanjeOca" />
							<springForm:errors path="zanimanjeOca" cssClass="form-error" />
						</div>
					</div>

					<label><i>Majka</i></label>
					<div class="form-group row">
						<div class="col-md-2">
							<springForm:input type="text" class="form-control"
								placeholder="Unesite ime majke" path="imeMajke" />
							<springForm:errors path="imeMajke" cssClass="form-error" />
						</div>
						<div class="col-md-2">
							<springForm:input type="text" class="form-control"
								placeholder="Unesite prezime majke" path="prezimeMajke" />
							<springForm:errors path="prezimeMajke" cssClass="form-error" />
						</div>
						<div class="col-md-2">
							<springForm:input type="text" class="form-control"
								placeholder="Unesite vjeru majke" path="vjeraMajke" />
							<springForm:errors path="vjeraMajke" cssClass="form-error" />
						</div>
						<div class="col-md-3">
							<springForm:input type="text" class="form-control"
								placeholder="Unesite narodnost majke" path="narodnostMajke" />
							<springForm:errors path="narodnostMajke" cssClass="form-error" />
						</div>
						<div class="col-md-3">
							<springForm:input type="text" class="form-control"
								placeholder="Unesite zanimanje majke" path="zanimanjeMajke" />
							<springForm:errors path="zanimanjeMajke" cssClass="form-error" />
						</div>
					</div>

					<label><i>Vjenčanje roditelja</i></label>
					<div class="form-group row">
						<div class="col-md-4">
							<springForm:input type="date" class="form-control"
								placeholder="Unesite datum vjenčanja"
								path="datumCrkvenogVjencanja" />
							<springForm:errors path="datumCrkvenogVjencanja"
								cssClass="form-error" />
						</div>
						<div class="col-md-4">
							<springForm:input type="text" class="form-control"
								placeholder="Unesite crkvu/župu vjenčanja"
								path="zupaCrkvenogVjencanja" />
							<springForm:errors path="zupaCrkvenogVjencanja"
								cssClass="form-error" />
						</div>
						<div class="col-md-4"></div>
					</div>

					<label><i>Adresa stanovanja</i></label>
					<div class="form-group row">
						<div class="col-md-3">
							<springForm:input type="text" class="form-control"
								placeholder="Unesite mjesto" path="mjesto" />
							<springForm:errors path="mjesto" cssClass="form-error" />
						</div>
						<div class="col-md-3">
							<springForm:input type="number" class="form-control"
								placeholder="Unesite postanski broj" path="postanskiBroj" />
							<springForm:errors path="postanskiBroj" cssClass="form-error" />
						</div>
						<div class="col-md-3">
							<springForm:input type="text" class="form-control"
								placeholder="Unesite ulicu" path="ulica" />
							<springForm:errors path="ulica" cssClass="form-error" />
						</div>
						<div class="col-md-3">
							<springForm:input type="number" class="form-control"
								placeholder="Unesite kućni broj" path="kucniBroj" />
							<springForm:errors path="kucniBroj" cssClass="form-error" />
						</div>
					</div>

					<label><i>Kontakt</i></label>
					<div class="form-group row">
						<div class="col-md-4">
							<springForm:input type="number" class="form-control"
								placeholder="Unesite kontakt broj" path="brojTelefona" />
							<springForm:errors path="brojTelefona" cssClass="form-error" />
						</div>
						<div class="col-md-4">
							<springForm:input type="email" class="form-control"
								placeholder="Unesite email" path="email" />
							<springForm:errors path="email" cssClass="form-error" />
						</div>
					</div>
					<div class="col-md-4"></div>
			</div>

			<br> <br>
			<h4 class="text-center">
				<b>Molimo da naše dijete bude kršteno rimokatoličkim obredom:</b>
			</h4>
			<br>
			<div class="form-group row">
				<div class="col-md-3">
					<springForm:input type="text" class="form-control"
						placeholder="Unesite ime djeteta" path="imeDjeteta" />
					<springForm:errors path="imeDjeteta" cssClass="form-error" />
				</div>
				<div class="col-md-3">
					<springForm:input type="text" class="form-control"
						placeholder="Unesite prezime djeteta" path="prezimeDjeteta" />
					<springForm:errors path="prezimeDjeteta" cssClass="form-error" />
				</div>
				<div class="col-md-3">
					<springForm:input type="number" class="form-control"
						placeholder="Unesite OIB djeteta" path="OIBDjeteta" />
					<springForm:errors path="OIBDjeteta" cssClass="form-error" />
				</div>
				<div class="col-md-3">
					<label><b>Spol:</b></label>
					<springForm:radiobutton path="spol" value="M" />
					Muško
					<springForm:radiobutton path="spol" value="F" />
					Žensko
					<springForm:errors path="spol" cssClass="form-error" />
				</div>
			</div>

			<label><i>Rođenje djeteta</i></label>
			<div class="form-group row">
				<div class="col-md-3">
					<springForm:input type="date" class="form-control"
						placeholder="Unesite datum rođenja" path="datumRodjenja" />
					<springForm:errors path="datumRodjenja" cssClass="form-error" />
				</div>
				<div class="col-md-3">
					<springForm:input type="text" class="form-control"
						placeholder="Unesite mjesto rođenja" path="mjestoRodjenja" />
					<springForm:errors path="mjestoRodjenja" cssClass="form-error" />
				</div>
				<div class="col-md-3">
					<springForm:input type="text" class="form-control"
						placeholder="(prvo, drugo,treće,...) dijete u obitelji"
						path="redniBrojDjeteta" />
					<springForm:errors path="redniBrojDjeteta" cssClass="form-error" />
				</div>
				<div class="col-md-3">
					<label><b>Dijete:</b></label>
					<springForm:radiobutton path="zakonitoIliCivilno" value="zakonito" />
					Zakonito
					<springForm:radiobutton path="zakonitoIliCivilno"
						value="civilno zakonito" />
					Civilno zakonito
					<springForm:errors path="spol" cssClass="form-error" />
				</div>
			</div>

			<label><i>Kumovi</i></label>
			<div class="form-group row">
				<label>1.</label>
				<div class="col-md-3">
					<springForm:input type="text" class="form-control"
						placeholder="Unesite ime kuma/kume" path="imeKuma1" />
					<springForm:errors path="imeKuma1" cssClass="form-error" />
				</div>
				<div class="col-md-3">
					<springForm:input type="text" class="form-control"
						placeholder="Unesite prezime kuma/kume" path="prezimeKuma1" />
					<springForm:errors path="prezimeKuma1" cssClass="form-error" />
				</div>
				<div class="col-md-3">
					<springForm:input type="text" class="form-control"
						placeholder="Unesite crkvu/župu kuma/kume" path="zupaKuma1" />
					<springForm:errors path="zupaKuma1" cssClass="form-error" />
				</div>
			</div>

			<div class="form-group row">
				<label>2.</label>
				<div class="col-md-3">
					<springForm:input type="text" class="form-control"
						placeholder="Unesite ime kuma/kume" path="imeKuma2" />
					<springForm:errors path="imeKuma2" cssClass="form-error" />
				</div>
				<div class="col-md-3">
					<springForm:input type="text" class="form-control"
						placeholder="Unesite prezime kuma/kume" path="prezimeKuma2" />
					<springForm:errors path="prezimeKuma2" cssClass="form-error" />
				</div>
				<div class="col-md-3">
					<springForm:input type="text" class="form-control"
						placeholder="Unesite crkvu/župu kuma/kume" path="zupaKuma2" />
					<springForm:errors path="zupaKuma2" cssClass="form-error" />
				</div>
			</div>

			<br>
			<h4 class="text-center">
				Za kumove <b>znamo da su katolici</b>(primili svete sakramente:
				krštenje, potvrdu/krizmu);<br> <b>prakticiraju svoju
					vjeru(mole se, idu nedeljom na svetu misu, ispovjedaju se o Uskrsu
					i Božiću).<br>
				</b> <i>Kumovi koju su <b>oženjeni</b> trebaju biti crkveno vjenčani
					i živjeti u tom braku.
				</i>
			</h4>

			<br>
			<h4 class="text-center">
				<i><b>Spremni smo doći na pripravu za krštenje dijeteta dva
						dana prije krštenja (</b>četvrtak<b>) u 17:30 sati ili po
						dogovoru.</b></i>
			</h4>

			<br>
			<h4 class="text-center">
				<u>P.S. <i><b>Nema takse za krštenje.</i> Darujte nešto za
					crkvu. Hvala. </b></u>
			</h4>

			<br> <label>Izvod iz matice rođenih</label>(fotokopija)
			<div class="form-group">
				<input type="file" name="izvodMaticnaKnjiga" />
			</div>
			<springForm:errors path="izvodMaticnaKnjiga" cssClass="form-error" />

			<label>Posvjedočenje za kumove</label>(od njihovog župnika)
			<div class="form-group">
				<input type="file" name="posvjedocenjeZaKumove" />
			</div>
			<springForm:errors path="posvjedocenjeZaKumove" cssClass="form-error" />

			<label>Suglasnost našeg župnika</label>(ako roditelji ne stanuju u
			župi gdje se dijete krsti)
			<div class="form-group">
				<input type="file" name="posvjedocenjeZaKumove" />
			</div>
			<springForm:errors path="posvjedocenjeZaKumove" cssClass="form-error" />

			<!-- Ako ovako stavite submit button, automatski će raditi -->
			<button type="submit" class="btn btn-primary">Spremi</button>
			</springForm:form>
		</div>
	</div>
	</div>

	<script>
		
	</script>
</body>
</html>