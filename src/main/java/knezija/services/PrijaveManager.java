package knezija.services;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import knezija.models.Krstenje;
import knezija.models.Subscription;
import knezija.models.forms.KrstenjeForm;
import knezija.persistence.IDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

@Service
public class PrijaveManager implements IPrijaveManager {
	@Autowired
	private IDao dao;

	private Parser dateParser = new Parser();

	@Override
	public boolean checkValidOnCreate(KrstenjeForm form, BindingResult result) {
		if (result.hasErrors()) {
			return false;
		}

		return true;
	}

	@Override
	public Krstenje createFromForm(KrstenjeForm form) {
		Krstenje krstenje = new Krstenje();
		krstenje.setBrojTelefona(form.getBrojTelefona());
		krstenje.setDatumCrkvenogVjencanja(parseDate(form
				.getDatumCrkvenogVjencanja()));
		krstenje.setDatumPrijave(new Date());
		krstenje.setDatumRodjenja(parseDate(form.getDatumRodjenja()));
		krstenje.setEmail(form.getEmail());
		krstenje.setImeDjeteta(form.getImeDjeteta());
		krstenje.setImeKuma1(form.getImeKuma1());
		krstenje.setImeKuma2(form.getImeKuma2());
		krstenje.setImeMajke(form.getImeMajke());
		krstenje.setImeOca(form.getImeOca());
		krstenje.setIzvodMaticnaKnjiga(getBytes(form.getIzvodMaticnaKnjiga()));
		krstenje.setKucniBroj(Long.parseLong(form.getKucniBroj()));
		krstenje.setMjesto(form.getMjesto());
		krstenje.setMjestoRodjenja(form.getMjestoRodjenja());
		krstenje.setNarodnostMajke(form.getNarodnostMajke());
		krstenje.setNarodnostOca(form.getNarodnostOca());
		krstenje.setOIBDjeteta(form.getOIBDjeteta());
		krstenje.setPostanskiBroj(Long.parseLong(form.getPostanskiBroj()));
		krstenje.setPosvjedocenjeZaKumove(getBytes(form
				.getPosvjedocenjeZaKumove()));
		krstenje.setPrezimeDjeteta(form.getPrezimeDjeteta());
		krstenje.setPrezimeKuma1(form.getPrezimeKuma1());
		krstenje.setPrezimeKuma2(form.getPrezimeKuma2());
		krstenje.setPrezimeMajke(form.getPrezimeMajke());
		krstenje.setPrezimeOca(form.getPrezimeOca());
		krstenje.setRedniBrojDjeteta(form.getRedniBrojDjeteta());
		krstenje.setSpol(form.getSpol());
		krstenje.setSuglasnostNasegZupnika(getBytes(form
				.getSuglasnostNasegZupnika()));
		krstenje.setUlica(form.getUlica());
		krstenje.setVjeraMajke(form.getVjeraMajke());
		krstenje.setVjeraOca(form.getVjeraOca());
		krstenje.setZakonitoIliCivilno(form.getZakonitoIliCivilno());
		krstenje.setZanimanjeMajke(form.getZanimanjeMajke());
		krstenje.setZanimanjeOca(form.getZanimanjeOca());
		krstenje.setZupaCrkvenogVjencanja(form.getZupaCrkvenogVjencanja());
		krstenje.setZupaKuma1(form.getZupaKuma1());
		krstenje.setZupaKuma2(form.getZupaKuma2());

		subscribeIfNotSubscribed(form.getEmail());

		return dao.update(krstenje);
	}

	private void subscribeIfNotSubscribed(String email) {
		if (email == null || email.isEmpty()) {
			return;
		}

		Subscription subscription = dao
				.find(Subscription.class, "email", email);
		if (subscription == null) {
			subscription = new Subscription();
			subscription.setEmail(email);
			dao.update(subscription);
		}
	}

	private byte[] getBytes(MultipartFile izvodMaticnaKnjiga) {
		try {
			return izvodMaticnaKnjiga == null ? new byte[0]
					: izvodMaticnaKnjiga.getBytes();
		} catch (IOException e) {
			return new byte[0];
		}
	}

	/**
	 * Returns null if no date was succesufully parsed.
	 * 
	 * @param dateString
	 * @return
	 */
	@Override
	public Date parseDate(String dateString) {
		List<DateGroup> dateGroups = dateParser.parse(dateString);
		if (dateGroups.isEmpty()) {
			return null;
		}
		return dateGroups.get(0).getDates().get(0);
	}

	@Override
	public List<Krstenje> getAllKrstenja() {
		return dao.findAll(Krstenje.class);
	}

	@Override
	public List<KrstenjeForm> getAllKrstenjaAsForm() {
		return getAllKrstenja().stream()
				.map((krstenje) -> createFromKrstenje(krstenje))
				.collect(Collectors.toList());
	}

	public KrstenjeForm createFromKrstenje(Krstenje form) {
		KrstenjeForm krstenje = new KrstenjeForm();

		krstenje.setBrojTelefona(form.getBrojTelefona());
		krstenje.setDatumCrkvenogVjencanja(dateToString(form
				.getDatumCrkvenogVjencanja()));
		krstenje.setDatumRodjenja(dateToString(form.getDatumRodjenja()));
		krstenje.setEmail(form.getEmail());
		krstenje.setImeDjeteta(form.getImeDjeteta());
		krstenje.setImeKuma1(form.getImeKuma1());
		krstenje.setImeKuma2(form.getImeKuma2());
		krstenje.setImeMajke(form.getImeMajke());
		krstenje.setImeOca(form.getImeOca());
		krstenje.setKucniBroj(String.valueOf(form.getKucniBroj()));
		krstenje.setMjesto(form.getMjesto());
		krstenje.setMjestoRodjenja(form.getMjestoRodjenja());
		krstenje.setNarodnostMajke(form.getNarodnostMajke());
		krstenje.setNarodnostOca(form.getNarodnostOca());
		krstenje.setOIBDjeteta(form.getOIBDjeteta());
		krstenje.setPostanskiBroj(String.valueOf(form.getPostanskiBroj()));
		krstenje.setPrezimeDjeteta(form.getPrezimeDjeteta());
		krstenje.setPrezimeKuma1(form.getPrezimeKuma1());
		krstenje.setPrezimeKuma2(form.getPrezimeKuma2());
		krstenje.setPrezimeMajke(form.getPrezimeMajke());
		krstenje.setPrezimeOca(form.getPrezimeOca());
		krstenje.setRedniBrojDjeteta(form.getRedniBrojDjeteta());
		krstenje.setSpol(form.getSpol());
		krstenje.setUlica(form.getUlica());
		krstenje.setVjeraMajke(form.getVjeraMajke());
		krstenje.setVjeraOca(form.getVjeraOca());
		krstenje.setZakonitoIliCivilno(form.getZakonitoIliCivilno());
		krstenje.setZanimanjeMajke(form.getZanimanjeMajke());
		krstenje.setZanimanjeOca(form.getZanimanjeOca());
		krstenje.setZupaCrkvenogVjencanja(form.getZupaCrkvenogVjencanja());
		krstenje.setZupaKuma1(form.getZupaKuma1());
		krstenje.setZupaKuma2(form.getZupaKuma2());

		krstenje.setDatumPrijave(dateToString(form.getDatumPrijave()));
		krstenje.setDatumKrstenja(dateToString(form.getDatumKrstenja()));

		krstenje.setId(form.getId());

		return krstenje;
	}

	/**
	 * Ovdje bi moglo biti problema, zbog toga što se uvijek koristi hrvatski
	 * locale format, a trebao bi se koristit format od klijenta.
	 * 
	 * @param datum
	 * @return
	 */
	private String dateToString(Date datum) {
		if (datum == null) {
			return "";
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(datum);
		// return DateFormat.getDateInstance(DateFormat.MEDIUM,
		// Locale.).format(datum);
	}

	@Override
	public void deleteKrstenje(long id) {
		dao.removeById(Krstenje.class, id);
	}

	@Override
	public boolean updateDatumKrstenja(Long id, String datumKrstenjaString) {
		Date datumKrstenja = parseDate(datumKrstenjaString);
		if (datumKrstenja != null) {
			Krstenje krstenje = dao.findById(Krstenje.class, id);
			krstenje.setDatumKrstenja(datumKrstenja);
			dao.update(krstenje);

			return true;
		}

		return false;
	}
}
