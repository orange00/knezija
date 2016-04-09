package knezija.utilities;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	private static boolean validirajEmail(String email) {

		final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);

		return matcher.matches();
	}

	public static Map<String, String> provjeriRegistraciju(
			String korisnickoIme, String lozinka, String email, String ime,
			String prezime, String dob, String spol, String grad) {

		Map<String, String> greska = new HashMap<>();

		if (korisnickoIme == null || korisnickoIme.isEmpty()) {
			greska.put("korisnickoIme", "Korisničko ime je prazno");
		} else {
			Korisnik korisnik = DAOProvider.getDao().findKorisnikByUsername(korisnickoIme);
			if (korisnik != null) {
				greska.put("korisnickoIme", "Korisničko ime je zauzeto");
			}
		}
		if (lozinka == null || lozinka.length() < 8) {
			greska.put("lozinka", "Lozinka mora imati barem 8 znakova");
		}
		if (email == null || !validirajEmail(email)) {
			greska.put("email", "Email nije valjan");
		}
		if (ime == null || ime.length() == 0) {
			greska.put("ime", "Ime je prazno");
		}
		if (prezime == null || prezime.length() == 0) {
			greska.put("prezime", "Prezime je prazno");
		}
		if (dob == null || dob.isEmpty()) {
			greska.put("dob", "Dob nije valjana");
		}

		try {
			Integer.parseInt(dob);
		} catch (NumberFormatException exc) {
			greska.put("dob", "Dob mora biti broj");
		}

		if (spol == null || spol.isEmpty()) {
			greska.put("spol", "Spol nije izabran");
		}
		if (grad == null || grad.isEmpty()) {
			greska.put("grad", "Grad nije upisan");
		}
		
		return greska;
	}

	public static Map<String, String> provjeriFormuPrijave(
			String korisnickoIme, String lozinka) {

		Map<String, String> greska = new HashMap<>();

		if (korisnickoIme == null || korisnickoIme.isEmpty()) {
			greska.put("korisnickoIme", "Korisnicko ime je prazno");
		}

		if (lozinka == null || lozinka.length() < 8) {
			greska.put("Lozinka", "Lozinka mora imati barem 8 znakova");
		}

		Korisnik korisnik = DAOProvider.getDao().findKorisnikByUsername(
				korisnickoIme);
		if (korisnik == null) {
			greska.put("korisnickoime",
					"Ne postoji korisnik s predanim korisničkim imenom");
		} else
			try {
				if (!PasswordHash.validatePassword(lozinka,
						korisnik.getLozinka())) {
					greska.put("Lozinka", "Pogrešna lozinka");
				}
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return greska;
	}

	public static Map<String, String> provjeriFormuAnkete(String naziv,
			String datumPocetka, String datumZavrsetka, String pocela,
			String zavrsila, String rezultatiObjavljeni, String autor,
			String vrstaAnkete) {

		Map<String, String> greska = new HashMap<>();

		if (naziv == null || naziv.isEmpty()) {
			greska.put("opis", "Naziv je prazan");
		}
		if (datumPocetka == null || datumPocetka.isEmpty()) {
			greska.put("opis", "Datum pocetka je prazan");
		}
		if (datumZavrsetka == null || datumZavrsetka.isEmpty()) {
			greska.put("opis", "Datum zavrsetka je prazan");
		}
		if (pocela == null || pocela.isEmpty()) {
			greska.put("opis", "Polje pocela je prazno");
		}
		if (zavrsila == null || zavrsila.isEmpty()) {
			greska.put("opis", "Polje zavrsila je prazno");
		}
		if (rezultatiObjavljeni == null || rezultatiObjavljeni.isEmpty()) {
			greska.put("opis", "Polje s rezultatima je prazno");
		}
		if (autor == null || autor.isEmpty()) {
			greska.put("opis", "Autor je prazan");
		}
		if (vrstaAnkete == null || vrstaAnkete.isEmpty()) {
			greska.put("opis", "Vrsta ankete je prazna");
		}
		return greska;

	}
	
	public static boolean isParameterEmpty(String parameter) {
		return parameter == null || parameter.isEmpty()
				|| parameter.equals("null");
	}

	public static boolean provjeriTipPitanja(String vrstaPitanja,
			List<BrojacOdgovora> odgovori) {
		switch (vrstaPitanja) {
		case "daNe":
			if (odgovori.size() == 2
					&& odgovori.get(0).getOdgovor().equalsIgnoreCase("da")
					&& odgovori.get(1).getOdgovor().equalsIgnoreCase("ne")) {
				return true;
			}
			break;
		case "radiobutton":
			if (odgovori.size() >= 1) {
				return true;
			}
			break;
		case "checkbox":
			if (odgovori.size() >= 2) {
				return true;
			}
			break;
		default:
			return true;
		}
		
		return false;
	}

}
