package knezija.utilities;

import javax.servlet.http.HttpServletRequest;

/**
 * Razred opisuje odnos trenutno prijavljenog korisnika i ankete kojoj se
 * pristupa, tj. opisuje koja prava pristupa toj anketi ima trenutno prijavljeni
 * korisnik.
 * 
 * @author Mate-1
 *
 */
public class PravaPristupa {
	private boolean ispuniAnketu, urediAnketu, statistickiRezultati,
			preuzimanjeRezultata;

	public PravaPristupa(HttpServletRequest req, Anketa anketa) {
		Korisnik korisnik = (Korisnik) req.getSession()
				.getAttribute("korisnik");

		boolean jeAutor = false;
		if (korisnik == null) {
			urediAnketu = false;
			preuzimanjeRezultata = false;

			if (anketa.jeAktivna() && !anketa.isAnonimna()) {
				ispuniAnketu = true;
			} else {
				ispuniAnketu = false;
			}
		} else {
			if (anketa.getKorisnik().getKorisnickoIme().equals(korisnik.getKorisnickoIme())) {
				urediAnketu = preuzimanjeRezultata = jeAutor = true;
			} else {
				urediAnketu = preuzimanjeRezultata = false;
			}

			if (anketa.jeAktivna()) {
				ispuniAnketu = true;
			} else {
				ispuniAnketu = false;
			}
		}

		if (jeAutor || anketa.getJesuLiObjavljeniRezultati()) {
			statistickiRezultati = true;
		} else {
			statistickiRezultati = false;
		}
	}

	public boolean isIspuniAnketu() {
		return ispuniAnketu;
	}

	public boolean isUrediAnketu() {
		return urediAnketu;
	}

	public boolean isStatistickiRezultati() {
		return statistickiRezultati;
	}

	public boolean isPreuzimanjeRezultata() {
		return preuzimanjeRezultata;
	}
}
