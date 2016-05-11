package knezija.services;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import knezija.models.Blagoslov;
import knezija.models.Krizma;
import knezija.models.Krstenje;
import knezija.models.Prijava;
import knezija.models.Record;
import knezija.models.Subscription;
import knezija.models.Ulica;
import knezija.models.forms.BlagoslovForm;
import knezija.models.forms.ContentForm;
import knezija.models.forms.KrizmaForm;
import knezija.models.forms.KrstenjeForm;
import knezija.models.forms.UlicaForm;
import knezija.persistence.IDao;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import scala.annotation.implicitNotFound;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.ParseLocation;
import com.joestelmach.natty.Parser;

@Service
public class PrijaveManager implements IPrijaveManager {
	@Autowired
	private IDao dao;

	private Parser dateParser = new Parser();

	// ///
	/*
	 * KRSTENJE
	 */
	// ///
	@Override
	public boolean checkValidOnCreate(KrstenjeForm form, BindingResult result) {
		if (result.hasErrors()) {
			return false;
		}

		return true;
	}

	@Override
	public Krstenje createFromForm(KrstenjeForm form) {
		Krstenje krstenje = createFromFormDontPersist(form);
		krstenje.setDatumPrijave(new Date());
	
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
		if (dateString == null || dateString.equals("")) {
			return null;
		}

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
			return formatter.parse(dateString);
		} catch (ParseException exc) {
			List<DateGroup> dateGroups = dateParser.parse(dateString);
			if (dateGroups.isEmpty()) {
				return null;
			}
			return dateGroups.get(0).getDates().get(0);
		}
	}

	@Override
	public List<Krstenje> getAllKrstenja(boolean isArchive) {
		return dao.findAll(Krstenje.class, "isArchive", isArchive);
	}

	@Override
	public List<KrstenjeForm> getAllKrstenjaAsForm(boolean isArchive) {
		return getAllKrstenja(isArchive).stream()
				.map((krstenje) -> createFromKrstenje(krstenje))
				.collect(Collectors.toList());
	}

	@Override
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
		krstenje.setArchive(form.isArchive());

		return krstenje;
	}

	private String dateToString(Date datum, String simpleDateFormat) {
		if (datum == null) {
			return "";
		}
		SimpleDateFormat formatter = new SimpleDateFormat(simpleDateFormat);
		return formatter.format(datum);
	}

	/**
	 * Returns a date string in ISO format. If the given date is null returns an
	 * empty String.
	 * 
	 * @param datum
	 * @return
	 */
	private String dateToString(Date datum) {
		return dateToString(datum, "yyyy-MM-dd");
	}

	private String dateToStringYearless(Date datum) {
		return dateToString(datum, "dd.MM.");
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

	@Override
	public Krstenje archiveKrstenje(Long id) {
		Krstenje krstenje = dao.findById(Krstenje.class, id);
		krstenje.setArchive(true);
		return dao.update(krstenje);
	}

	@Override
	public Krstenje getKrstenjeById(long krstenjeId) {
		return dao.findById(Krstenje.class, krstenjeId);
	}

	// ///
	/*
	 * KRIZMA
	 */
	// ///
	@Override
	public boolean checkValidOnCreate(KrizmaForm form, BindingResult result) {
		if (result.hasErrors()) {
			return false;
		}

		return true;
	}

	@Override
	public Krizma createFromForm(KrizmaForm form) {
		Krizma krizma = createFromFormDontPersist(form);
		krizma.setDatumPrijave(new Date());

		subscribeIfNotSubscribed(form.getEmail());

		return dao.update(krizma);
	}

	

	@Override
	public List<Krizma> getAllKrizme(boolean isArchive, boolean isPricest) {
		Map<String, Object> valueByName = new HashMap<>();
		valueByName.put("isArchive", isArchive);
		valueByName.put("isPricest", isPricest);
		return dao.findAll(Krizma.class, valueByName);
	}

	@Override
	public List<KrizmaForm> getAllKrizmeAsForm(boolean isArchive,
			boolean isPricest) {
		return getAllKrizme(isArchive, isPricest).stream()
				.map((krizma) -> createFromKrizma(krizma))
				.collect(Collectors.toList());
	}

	@Override
	public KrizmaForm createFromKrizma(Krizma form) {
		KrizmaForm krizma = new KrizmaForm();
		krizma.setId(form.getId());
		krizma.setAdresaStanovanja(form.getAdresaStanovanja());
		krizma.setArchive(false);
		krizma.setBrojTelefona(form.getBrojTelefona());
		krizma.setCrkvenoVjencani(form.getCrkvenoVjencani());
		krizma.setDatumPrijave(dateToString(form.getDatumPrijave()));
		krizma.setDatumRodjenja(dateToString(form.getDatumRodjenja()));
		krizma.setDjevojackoPrezimeMajke(form.getDjevojackoPrezimeMajke());
		krizma.setEmail(form.getEmail());
		krizma.setImeDjeteta(form.getImeDjeteta());
		krizma.setImeMajke(form.getImeMajke());
		krizma.setImeOca(form.getImeOca());
		krizma.setKrizmanikZahtjevaPosebnuPaznju(form
				.getKrizmanikZahtjevaPosebnuPaznju());
		krizma.setMjestoRodjenja(form.getMjestoRodjenja());
		krizma.setPohadjaSkolskiVjeronauk(form.getPohadjaSkolskiVjeronauk());
		krizma.setPrezimeDjeteta(form.getPrezimeDjeteta());
		krizma.setPrezimeOca(form.getPrezimeOca());
		krizma.setRazred(form.getRazred());
		krizma.setSkola(form.getSkola());
		krizma.setSvecenikDolaziNaBlagoslov(form.getSvecenikDolaziNaBlagoslov());
		krizma.setZupaKrstenja(form.getZupaKrstenja());

		krizma.setDatumKrizme(dateToString(form.getDatumKrizme()));

		krizma.setIsPricest(String.valueOf(form.isPricest()));

		krizma.setDatumKrstenja(dateToString(form.getDatumKrstenja()));

		return krizma;
	}

	@Override
	public void deleteKrizmu(long id) {
		dao.removeById(Krizma.class, id);
	}

	@Override
	public boolean updateDatumKrizme(Long id, String datumKrizmeString) {
		Date datumKrizme = parseDate(datumKrizmeString);
		if (datumKrizme != null) {
			Krizma krizma = dao.findById(Krizma.class, id);
			krizma.setDatumKrizme(datumKrizme);
			dao.update(krizma);

			return true;
		}

		return false;
	}

	@Override
	public Krizma archiveKrizmu(Long id) {
		Krizma krizma = dao.findById(Krizma.class, id);
		krizma.setArchive(true);
		return dao.update(krizma);
	}

	@Override
	public Krizma getKrizmaById(long krizmaId) {
		return dao.findById(Krizma.class, krizmaId);
	}

	@Override
	public boolean isArchiveKrizma(Long id) {
		return isArchive(Krizma.class, id);
	}

	private <T extends Prijava> boolean isArchive(Class<T> prijavaClass, long id) {
		return dao.findById(prijavaClass, id).isArchive();
	}

	@Override
	public boolean isArchiveKrstenje(Long id) {
		return isArchive(Krstenje.class, id);
	}

	@Override
	public List<Ulica> getAllUlice() {
		return dao.findAll(Ulica.class);
	}

	@Override
	public Ulica createFromForm(UlicaForm form) {
		Ulica ulica = new Ulica();

		return ulicaChanged(ulica, form);
	}

	@Override
	public Ulica findUlicaById(long id) {
		return dao.findById(Ulica.class, id);
	}

	@Override
	public UlicaForm createFromUlica(Ulica ulica, boolean yearless) {
		UlicaForm form = new UlicaForm();
		form.setDatum(yearless ? dateToStringYearless(ulica.getDatum())
				: dateToString(ulica.getDatum()));
		form.setId(ulica.getId());
		form.setNazivUlice(ulica.getNazivUlice());

		return form;
	}

	@Override
	public Ulica updateFromForm(UlicaForm form, long id) {
		Ulica ulica = findUlicaById(id);
		return ulicaChanged(ulica, form);
	}

	private Ulica ulicaChanged(Ulica ulica, UlicaForm form) {
		ulica.setDatum(parseDate(form.getDatum()));
		ulica.setNazivUlice(form.getNazivUlice());

		return dao.update(ulica);
	}

	@Override
	public void deleteUlica(long id) {
		dao.removeById(Ulica.class, id);
	}

	@Override
	public List<UlicaForm> getAllUliceAsForm(boolean yearless) {
		return getAllUlice().stream()
				.map((ulica) -> createFromUlica(ulica, yearless))
				.collect(Collectors.toList());
	}

	@Override
	public Blagoslov getBlagoslovById(long id) {
		return dao.findById(Blagoslov.class, id);
	}

	@Override
	public BlagoslovForm createFromBlagoslov(Blagoslov blagoslov) {
		BlagoslovForm form = new BlagoslovForm();
		form.setBrojTelefona(blagoslov.getBrojTelefona());
		form.setEmail(blagoslov.getEmail());
		form.setId(blagoslov.getId());
		form.setIme(blagoslov.getIme());
		form.setImeUlice(blagoslov.getUlicaIDatum().getNazivUlice());
		form.setDatum(dateToStringYearless(blagoslov.getUlicaIDatum()
				.getDatum()));
		form.setKat(String.valueOf(blagoslov.getKat()));
		form.setKucniBroj(String.valueOf(blagoslov.getKucniBroj()));
		form.setNapomene(blagoslov.getNapomene());
		form.setPrezime(blagoslov.getPrezime());
		form.setPrijePoslijeBozica(blagoslov.getPrijePoslijeBozica());
		form.setVrijemeBlagoslova(blagoslov.getVrijemeBlagoslova());

		form.setBrojStana(String.valueOf(blagoslov.getBrojStana()));

		form.setVrijemeBlagoslova(blagoslov.getVrijemeBlagoslova());
		form.setVrijemeBlagoslovaOstalo(blagoslov.getVrijemeBlagoslovaOstalo());

		return form;
	}

	@Override
	public boolean checkValidOnCreate(BlagoslovForm form, BindingResult result) {
		if (result.hasErrors()) {
			return false;
		}

		return true;
	}

	@Override
	public Blagoslov createFromForm(BlagoslovForm form) {
		Blagoslov blagoslov = createFromFormDontPersist(form);
		
		subscribeIfNotSubscribed(form.getEmail());

		return dao.update(blagoslov);
	}
	
	private Krstenje createFromFormDontPersist(KrstenjeForm form) {
		Krstenje krstenje = new Krstenje();
		krstenje.setBrojTelefona(form.getBrojTelefona());
		krstenje.setDatumCrkvenogVjencanja(parseDate(form
				.getDatumCrkvenogVjencanja()));
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
		
		krstenje.setDatumKrstenja(parseDate(form.getDatumKrstenja()));
		
		krstenje.setDatumPrijave(parseDate(form.getDatumPrijave()));
		
		return krstenje;
	}
	
	private Krizma createFromFormDontPersist(KrizmaForm form) {
		Krizma krizma = new Krizma();
		krizma.setAdresaStanovanja(form.getAdresaStanovanja());
		krizma.setArchive(false);
		krizma.setBrojTelefona(form.getBrojTelefona());
		krizma.setCrkvenoVjencani(form.getCrkvenoVjencani());
		krizma.setDatumRodjenja(parseDate(form.getDatumRodjenja()));
		krizma.setDjevojackoPrezimeMajke(form.getDjevojackoPrezimeMajke());
		krizma.setEmail(form.getEmail());
		krizma.setImeDjeteta(form.getImeDjeteta());
		krizma.setImeMajke(form.getImeMajke());
		krizma.setImeOca(form.getImeOca());
		krizma.setKrizmanikZahtjevaPosebnuPaznju(form
				.getKrizmanikZahtjevaPosebnuPaznju());
		krizma.setMjestoRodjenja(form.getMjestoRodjenja());
		krizma.setPohadjaSkolskiVjeronauk(form.getPohadjaSkolskiVjeronauk());
		krizma.setPosvjedocenjeZaKumove(getBytes(form
				.getPosvjedocenjeZaKumove()));
		krizma.setPotvrdaOKrstenju(getBytes(form.getPotvrdaOKrstenju()));
		krizma.setPrezimeDjeteta(form.getPrezimeDjeteta());
		krizma.setPrezimeOca(form.getPrezimeOca());
		krizma.setRazred(form.getRazred());
		krizma.setSkola(form.getSkola());
		krizma.setSvecenikDolaziNaBlagoslov(form.getSvecenikDolaziNaBlagoslov());
		krizma.setZupaKrstenja(form.getZupaKrstenja());

		krizma.setPricest(Boolean.valueOf((form.getIsPricest())));

		krizma.setDatumKrstenja(parseDate(form.getDatumKrstenja()));
		
		krizma.setDatumKrizme(parseDate(form.getDatumKrizme()));
		
		krizma.setDatumPrijave(parseDate(form.getDatumPrijave()));
		
		return krizma;
	}
	
	private Blagoslov createFromFormDontPersist(BlagoslovForm form) {
		Blagoslov blagoslov = new Blagoslov();
		blagoslov.setBrojTelefona(form.getBrojTelefona());
		blagoslov.setEmail(form.getEmail());
		blagoslov.setIme(form.getIme());
		blagoslov.setUlicaIDatum(getUlicaByNaziv(form.getImeUlice()));
		blagoslov.setKat(Integer.parseInt(form.getKat()));
		blagoslov.setKucniBroj(Integer.parseInt(form.getKucniBroj()));
		blagoslov.setNapomene(form.getNapomene());
		blagoslov.setPrezime(form.getPrezime());
		blagoslov.setPrijePoslijeBozica(form.getPrijePoslijeBozica());

		blagoslov.setBrojStana(Integer.parseInt(form.getBrojStana()));

		String vrijemeBlagoslova = form.getVrijemeBlagoslova();
		blagoslov.setVrijemeBlagoslova(vrijemeBlagoslova);
		if (vrijemeBlagoslova.equalsIgnoreCase("ostalo")) {
			blagoslov.setVrijemeBlagoslovaOstalo(form
					.getVrijemeBlagoslovaOstalo());
		}
		
		return blagoslov;
	}

	private Ulica getUlicaByNaziv(String imeUlice) {
		return dao.find(Ulica.class, "nazivUlice", imeUlice);
	}

	@Override
	public List<BlagoslovForm> getAllBlagosloviAsForm(boolean isArchive) {
		return getAllBlagoslovi(isArchive).stream()
				.map((blagoslov) -> createFromBlagoslov(blagoslov))
				.collect(Collectors.toList());
	}

	/**
	 * Sortira blagoslove po: ulica, vrijeme blagoslova, kucni broj, kat, stan
	 * 
	 * @param isArchive
	 * @return
	 */
	private List<Blagoslov> getAllBlagoslovi(boolean isArchive) {
		String sortString = " ORDER BY ulicaIDatum, vrijemeBlagoslova DESC, kucniBroj, kat, brojStana";
		return dao.findAllWithAddition(Blagoslov.class, "isArchive", isArchive,
				sortString);
	}

	@Override
	public boolean isArchiveBlagoslov(Long id) {
		return isArchive(Blagoslov.class, id);
	}

	@Override
	public void deleteBlagoslov(Long id) {
		dao.removeById(Blagoslov.class, id);
	}

	@Override
	public void archiveBlagoslov(Long id) {
		archive(Blagoslov.class, id);
	}

	private <T extends Prijava> void archive(Class<T> prijavaClass, Long id) {
		Prijava prijava = dao.findById(prijavaClass, id);
		prijava.setArchive(true);
		dao.update(prijava);
	}

	@Override
	public boolean updateDatumBlagoslova(Long id, String datumBlagoslovaString) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public XSSFWorkbook generateWorkbookKrstenjeAll(List<KrstenjeForm> krstenja) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();
		XSSFRow rowForName = sheet.createRow(0);
		rowForName.createCell(0).setCellValue("IME OCA");
		rowForName.createCell(1).setCellValue("PREZIME OCA");
		rowForName.createCell(2).setCellValue("VJERA OCA");
		rowForName.createCell(3).setCellValue("NARODNOST OCA");
		rowForName.createCell(4).setCellValue("ZANIMANJE OCA");
		rowForName.createCell(5).setCellValue("IME MAJKE");
		rowForName.createCell(6).setCellValue("PREZIME MAJKE");
		rowForName.createCell(7).setCellValue("VJERA MAJKE");
		rowForName.createCell(8).setCellValue("NARODNOST MAJKE");
		rowForName.createCell(9).setCellValue("ZANIMANJE MAJKE");
		rowForName.createCell(10).setCellValue(
				"DATUM CRKVENOG VJENČANJA RODITELJA");
		rowForName.createCell(11).setCellValue(
				"ŽUPA CRKVENOG VJENČANJA RODITELJA");
		rowForName.createCell(12).setCellValue("MJESTO");
		rowForName.createCell(13).setCellValue("POŠTANSKI BROJ");
		rowForName.createCell(14).setCellValue("ULICA");
		rowForName.createCell(15).setCellValue("KUĆNI BROJ");
		rowForName.createCell(16).setCellValue("TELEFON");
		rowForName.createCell(17).setCellValue("EMAIL");
		rowForName.createCell(18).setCellValue("IME DJETETA");
		rowForName.createCell(19).setCellValue("PREZIME DJETETA");
		rowForName.createCell(20).setCellValue("OIB DJETETA");
		rowForName.createCell(21).setCellValue("SPOL");
		rowForName.createCell(22).setCellValue("DATUM ROĐENJA");
		rowForName.createCell(23).setCellValue("MJESTO ROĐENJA");
		rowForName.createCell(24).setCellValue("REDNI BROJ U OBITELJI");
		rowForName.createCell(25).setCellValue("ZAKONITOST DJETETA");
		rowForName.createCell(26).setCellValue("IME PRVOG KUMA");
		rowForName.createCell(27).setCellValue("PREZIME PRVOG KUMA");
		rowForName.createCell(28).setCellValue("ŽUPA PRVOG KUMA");
		rowForName.createCell(29).setCellValue("IME DRUGOG KUMA");
		rowForName.createCell(30).setCellValue("PREZIME DRUGOG KUMA");
		rowForName.createCell(31).setCellValue("ŽUPA DRUGOG KUMA");
		rowForName.createCell(32).setCellValue("DATUM PRIJAVE");
		rowForName.createCell(33).setCellValue("DATUM KRŠTENJA");
		rowForName.createCell(34).setCellValue("id(NE UREĐIVATI!)");

		for (KrstenjeForm form : krstenja) {
			XSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
			row.createCell(0).setCellValue(form.getImeOca());
			row.createCell(1).setCellValue(form.getPrezimeOca());
			row.createCell(2).setCellValue(form.getVjeraOca());
			row.createCell(3).setCellValue(form.getNarodnostOca());
			row.createCell(4).setCellValue(form.getZanimanjeOca());
			row.createCell(5).setCellValue(form.getImeMajke());
			row.createCell(6).setCellValue(form.getPrezimeMajke());
			row.createCell(7).setCellValue(form.getVjeraMajke());
			row.createCell(8).setCellValue(form.getNarodnostMajke());
			row.createCell(9).setCellValue(form.getZanimanjeMajke());
			row.createCell(10).setCellValue(
					formatDateForCell(form.getDatumCrkvenogVjencanja()));
			row.createCell(11).setCellValue(form.getZupaCrkvenogVjencanja());
			row.createCell(12).setCellValue(form.getMjesto());
			numberToCell(row.createCell(13), form.getPostanskiBroj());
			row.createCell(14).setCellValue(form.getUlica());
			numberToCell(row.createCell(15), form.getKucniBroj());
			row.createCell(16).setCellValue(form.getBrojTelefona());
			row.createCell(17).setCellValue(form.getEmail());
			row.createCell(18).setCellValue(form.getImeDjeteta());
			row.createCell(19).setCellValue(form.getPrezimeDjeteta());
			row.createCell(20).setCellValue(form.getOIBDjeteta());
			row.createCell(21).setCellValue(form.getSpol());
			row.createCell(22).setCellValue(
					formatDateForCell(form.getDatumRodjenja()));
			row.createCell(23).setCellValue(form.getMjestoRodjenja());
			row.createCell(24).setCellValue(form.getRedniBrojDjeteta());
			row.createCell(25).setCellValue(form.getZakonitoIliCivilno());
			row.createCell(26).setCellValue(form.getImeKuma1());
			row.createCell(27).setCellValue(form.getPrezimeKuma1());
			row.createCell(28).setCellValue(form.getZupaKuma1());
			row.createCell(29).setCellValue(form.getImeKuma2());
			row.createCell(30).setCellValue(form.getPrezimeKuma2());
			row.createCell(31).setCellValue(form.getZupaKuma2());
			row.createCell(32).setCellValue(
					formatDateForCell(form.getDatumPrijave()));
			row.createCell(33).setCellValue(
					formatDateForCell(form.getDatumKrstenja()));
			row.createCell(34).setCellValue(form.getId());
			lockCell(row.getCell(34), workbook, sheet);
		}

		return workbook;
	}

	/**
	 * Returns the given date string represented in Croatian date format. If the
	 * given string is null, returns an empty string. We use String instead of
	 * Date because Date cells cannot be set as empty.
	 * 
	 * @param dateString
	 * @return
	 */
	private String formatDateForCell(String dateString) {
		Date date = parseDate(dateString);
		String formatted = dateToString(date, "dd.MM.yyyy");
		return formatted;
	}

	@Override
	public void synchronizeKrstenje(boolean isArchive, MultipartFile excelFile,
			List<Long> synchronizedIds, List<Long> synchronizedWithErrorIds)
			throws IllegalArgumentException {
		try {

			XSSFWorkbook workbook = new XSSFWorkbook(excelFile.getInputStream());
			if (workbook.getNumberOfSheets() != 1) {
				throw new IllegalArgumentException();
			}
			XSSFSheet sheet = workbook.getSheetAt(0);
			if ((sheet.getLastRowNum() + 1) < 1) {
				throw new IllegalArgumentException();
			}
			Iterator<Row> rowIterator = sheet.rowIterator();
			// skip the first row with names
			rowIterator.next();

			while (rowIterator.hasNext()) {
				Krstenje krstenje = null;
				try {
					Row row = rowIterator.next();
					String idString = getCellValueNullAsBlank(row, 34);
					if (idString == null || idString.isEmpty()) {
						krstenje = new Krstenje();
						krstenje.setDatumPrijave(new Date());
					} else {
						long id = parseCellLong(idString);
						krstenje = dao.findById(Krstenje.class, id);
					}
					
					if(krstenje==null) {
						krstenje=new Krstenje();
						krstenje.setDatumPrijave(new Date());
					}
					
					Krstenje oldKrstenje = copy(krstenje);
					
					krstenje.setImeOca(getCellValueNullAsBlank(row, 0));
					krstenje.setPrezimeOca(getCellValueNullAsBlank(row, 1));
					krstenje.setVjeraOca(getCellValueNullAsBlank(row, 2));
					krstenje.setNarodnostOca(getCellValueNullAsBlank(row, 3));
					krstenje.setZanimanjeOca(getCellValueNullAsBlank(row, 4));

					krstenje.setImeMajke(getCellValueNullAsBlank(row, 5));
					krstenje.setPrezimeMajke(getCellValueNullAsBlank(row, 6));
					krstenje.setVjeraMajke(getCellValueNullAsBlank(row, 7));
					krstenje.setNarodnostMajke(getCellValueNullAsBlank(row, 8));
					krstenje.setZanimanjeMajke(getCellValueNullAsBlank(row, 9));

					krstenje.setDatumCrkvenogVjencanja(parseDate(getCellValueNullAsBlank(
							row, 10)));
					krstenje.setZupaCrkvenogVjencanja(getCellValueNullAsBlank(
							row, 11));

					krstenje.setMjesto(getCellValueNullAsBlank(row, 12));
					krstenje.setPostanskiBroj(parseCellLong(getCellValueNullAsBlank(
							row, 13)));
					krstenje.setUlica(getCellValueNullAsBlank(row, 14));
					krstenje.setKucniBroj(parseCellLong(getCellValueNullAsBlank(
							row, 15)));

					krstenje.setBrojTelefona(getCellValueNullAsBlank(row, 16));
					krstenje.setEmail(getCellValueNullAsBlank(row, 17));

					krstenje.setImeDjeteta(getCellValueNullAsBlank(row, 18));
					krstenje.setPrezimeDjeteta(getCellValueNullAsBlank(row, 19));
					krstenje.setOIBDjeteta(getCellValueNullAsBlank(row, 20));
					krstenje.setSpol(getCellValueNullAsBlank(row, 21));

					krstenje.setDatumRodjenja(parseDate(getCellValueNullAsBlank(
							row, 22)));
					krstenje.setMjestoRodjenja(getCellValueNullAsBlank(row, 23));

					krstenje.setRedniBrojDjeteta(getCellValueNullAsBlank(row,
							24));
					krstenje.setZakonitoIliCivilno(getCellValueNullAsBlank(row,
							25));

					krstenje.setImeKuma1(getCellValueNullAsBlank(row, 26));
					krstenje.setPrezimeKuma1(getCellValueNullAsBlank(row, 27));
					krstenje.setZupaKuma1(getCellValueNullAsBlank(row, 28));

					krstenje.setImeKuma2(getCellValueNullAsBlank(row, 29));
					krstenje.setPrezimeKuma2(getCellValueNullAsBlank(row, 30));
					krstenje.setZupaKuma2(getCellValueNullAsBlank(row, 31));

					krstenje.setDatumPrijave(parseDate(getCellValueNullAsBlank(
							row, 32)));
					krstenje.setDatumKrstenja(parseDate(getCellValueNullAsBlank(
							row, 33)));

					if (hasRecordChanged(oldKrstenje, krstenje)) {
						krstenje = dao.update(krstenje);
						synchronizedIds.add(krstenje.getId());
					}
				} catch (Exception exc) {
					synchronizedWithErrorIds.add(krstenje.getId());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(
					"Predani .xlsx dokument je neispravan!");
		} catch (Exception ex) {
			throw new IllegalArgumentException(
					"Neispravan format .xlsx dokumenta! Probajte generirati jedan dokument ako ne znate koji je ispravan format.");
		}
	}

	/**
	 * 
	 * @param oldBlagoslov
	 *            the old id or (null or empty) if there is no old record(cell
	 *            value)
	 * @param blagoslov
	 *            the new record T must be a database record.
	 * @return
	 */
	private <T extends Record<T>> boolean hasRecordChanged(T oldRecord,
			T newRecord) {

		return !newRecord.logicallyEquals(oldRecord);
	}

	@Override
	public void generateExcelAllKrizmaOrPricest(boolean isArchive,
			boolean isPricest, HttpServletResponse resp, String fileName) {
		List<KrizmaForm> krizme = getAllKrizmeAsForm(isArchive, isPricest);
		XSSFWorkbook workbook = generateWorkbookFromKrizmaForms(krizme);

		respondWithExcel(workbook, resp, fileName);
	}

	@Override
	public void respondWithExcel(XSSFWorkbook workbook,
			HttpServletResponse resp, String fileName) {
		try {
			resp.setHeader("Content-Disposition", "inline; filename="
					+ fileName);
			// Make sure to set the correct content type
			resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			workbook.write(resp.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public XSSFWorkbook generateWorkbookFromKrizmaForms(List<KrizmaForm> krizme) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		for (KrizmaForm form : krizme) {
			String skolaRazred = form.getSkola() + " - " + form.getRazred();
			XSSFSheet sheet = workbook.getSheet(skolaRazred);
			if (sheet == null) {
				sheet = workbook.createSheet(skolaRazred);
				XSSFRow row = sheet.createRow(0);
				row.createCell(0).setCellValue("IME");
				row.createCell(1).setCellValue("PREZIME");
				row.createCell(2).setCellValue("ADRESA");
				row.createCell(3).setCellValue("DATUM ROĐENJA");
				row.createCell(4).setCellValue("MJESTO");
				row.createCell(5).setCellValue("DATUM KRŠTENJA");
				row.createCell(6).setCellValue("ŽUPA KRŠTENJA");
				row.createCell(7).setCellValue("IME OCA");
				row.createCell(8).setCellValue("IME MAJKE");
				row.createCell(9).setCellValue("DJEV.PREZIME MAJKE");
				row.createCell(10).setCellValue("crkveno vjenčani");
				row.createCell(11).setCellValue("telefon");
				row.createCell(12).setCellValue("e-mail");
				row.createCell(13).setCellValue("svećenik dolazi na blagoslov");
				row.createCell(14).setCellValue("pohađa vjeronauk u školi");
				row.createCell(15).setCellValue("id(NE UREĐIVATI!)");
			}

			XSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
			row.createCell(0).setCellValue(form.getImeDjeteta());
			row.createCell(1).setCellValue(form.getPrezimeDjeteta());
			row.createCell(2).setCellValue(form.getAdresaStanovanja());
			row.createCell(3).setCellValue(form.getDatumRodjenja());
			row.createCell(4).setCellValue(form.getMjestoRodjenja());
			row.createCell(5).setCellValue(form.getDatumKrstenja());
			row.createCell(6).setCellValue(form.getZupaKrstenja());
			row.createCell(7).setCellValue(form.getImeOca());
			row.createCell(8).setCellValue(form.getImeMajke());
			row.createCell(9).setCellValue(form.getDjevojackoPrezimeMajke());
			row.createCell(10).setCellValue(form.getCrkvenoVjencani());
			row.createCell(11).setCellValue(form.getBrojTelefona());
			row.createCell(12).setCellValue(form.getEmail());
			row.createCell(13)
					.setCellValue(form.getSvecenikDolaziNaBlagoslov());
			row.createCell(14).setCellValue(form.getPohadjaSkolskiVjeronauk());
			row.createCell(15).setCellValue(form.getId());
			lockCell(row.getCell(15), workbook, sheet);
		}

		return workbook;
	}

	@Override
	public void synchronizeKrizma(boolean isArchive, boolean isPricest,
			MultipartFile excelFile, List<Long> synchronizedIds,
			List<Long> synchronizedWithErrorIds)
			throws IllegalArgumentException {
		try {

			XSSFWorkbook workbook = new XSSFWorkbook(excelFile.getInputStream());
			for (Sheet sheet : workbook) {
				if ((sheet.getLastRowNum() + 1) < 1) {
					throw new IllegalArgumentException();
				}
				Iterator<Row> rowIterator = sheet.rowIterator();
				// skip the first row with names
				rowIterator.next();

				while (rowIterator.hasNext()) {
					Krizma krizma = null;
					try {
						Row row = rowIterator.next();
						String idString = getCellValueNullAsBlank(row, 15);
						if (idString == null || idString.isEmpty()) {
							krizma = new Krizma();
							krizma.setDatumPrijave(new Date());
						} else {
							long id = parseCellLong(idString);
							krizma = dao.findById(Krizma.class, id);
						}
						
						if(krizma==null) {
							krizma = new Krizma();
							krizma.setDatumPrijave(new Date());
						}
						
						Krizma oldKrizma = copy(krizma);

						String sheetName = sheet.getSheetName();
						String[] skolaIRazred = sheetName.split("\\-");
						krizma.setSkola(skolaIRazred[0].trim());
						krizma.setRazred(skolaIRazred[1].trim());

						krizma.setImeDjeteta(getCellValueNullAsBlank(row, 0));
						krizma.setPrezimeDjeteta(getCellValueNullAsBlank(row, 1));
						krizma.setAdresaStanovanja(getCellValueNullAsBlank(row,
								2));

						krizma.setDatumRodjenja(parseDate(getCellValueNullAsBlank(
								row, 3)));
						krizma.setMjestoRodjenja(getCellValueNullAsBlank(row, 4));

						krizma.setDatumKrstenja(parseDate(getCellValueNullAsBlank(
								row, 5)));
						krizma.setZupaKrstenja(getCellValueNullAsBlank(row, 6));

						krizma.setImeOca(getCellValueNullAsBlank(row, 7));
						krizma.setImeMajke(getCellValueNullAsBlank(row, 8));
						krizma.setDjevojackoPrezimeMajke(getCellValueNullAsBlank(
								row, 9));
						krizma.setCrkvenoVjencani(getCellValueNullAsBlank(row,
								10));

						krizma.setBrojTelefona(getCellValueNullAsBlank(row, 11));
						krizma.setEmail(getCellValueNullAsBlank(row, 12));

						krizma.setSvecenikDolaziNaBlagoslov(getCellValueNullAsBlank(
								row, 13));
						krizma.setPohadjaSkolskiVjeronauk(getCellValueNullAsBlank(
								row, 14));
						krizma.setPricest(isPricest);
						
						//datumPrijave se trenutno ne može iz excela
						//ažurirat, jer stari format to ne podržava

						if (hasRecordChanged(oldKrizma, krizma)) {
							krizma = dao.update(krizma);
							synchronizedIds.add(krizma.getId());
						}
					} catch (Exception exc) {
						synchronizedWithErrorIds.add(krizma.getId());
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(
					"Predani .xlsx dokument je neispravan!");
		} catch (Exception ex) {
			throw new IllegalArgumentException(
					"Neispravan format .xlsx dokumenta! Probajte generirati jedan dokument ako ne znate koji je ispravan format.");
		}
	}

	@Override
	public XSSFWorkbook generateWorkbookBlagoslovAll(
			List<BlagoslovForm> blagoslovi) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();
		XSSFRow rowForName = sheet.createRow(0);
		rowForName.createCell(0).setCellValue("IME");
		rowForName.createCell(1).setCellValue("PREZIME");
		rowForName.createCell(2).setCellValue("IME ULICE");
		rowForName.createCell(3)
				.setCellValue("DATUM BLAGOSLOVA(NE UREĐIVATI!)");
		rowForName.createCell(4).setCellValue("KUĆNI BROJ");
		rowForName.createCell(5).setCellValue("KAT");
		rowForName.createCell(6).setCellValue("BROJ STANA");
		rowForName.createCell(7).setCellValue("VRIJEME BLAGOSLOVA");
		rowForName.createCell(8).setCellValue("VRIJEME BLAGOSLOVA-NAPOMENE");
		rowForName.createCell(9).setCellValue("EMAIL");
		rowForName.createCell(10).setCellValue("BROJ TELEFONA");
		rowForName.createCell(11).setCellValue("NAPOMENE");
		rowForName.createCell(12).setCellValue("id(NE UREĐIVATI!)");

		for (BlagoslovForm form : blagoslovi) {
			XSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
			row.createCell(0).setCellValue(form.getIme());
			row.createCell(1).setCellValue(form.getPrezime());
			row.createCell(2).setCellValue(form.getImeUlice());
			row.createCell(3).setCellValue(form.getDatum());
			lockCell(row.getCell(3), workbook, sheet);
			numberToCell(row.createCell(4), form.getKucniBroj());
			numberToCell(row.createCell(5), form.getKat());
			numberToCell(row.createCell(6), form.getBrojStana());
			row.createCell(7).setCellValue(form.getVrijemeBlagoslova());
			row.createCell(8).setCellValue(form.getVrijemeBlagoslovaOstalo());
			row.createCell(9).setCellValue(form.getEmail());
			row.createCell(10).setCellValue(form.getBrojTelefona());
			row.createCell(11).setCellValue(form.getNapomene());
			row.createCell(12).setCellValue(form.getId());
			lockCell(row.getCell(12), workbook, sheet);
		}

		return workbook;
	}

	private void numberToCell(XSSFCell cell, String number) {
		if (number == null || number.isEmpty()) {
			cell.setCellValue("");
		} else {
			cell.setCellValue(Integer.parseInt(number));
		}
	}

	/**
	 * Makes the cell non editable.
	 * 
	 * @param cell
	 * @param workbook
	 * @param sheet
	 */
	private void lockCell(XSSFCell cell, XSSFWorkbook workbook, XSSFSheet sheet) {
		// this has no effect?
		CellStyle unlockedCellStyle = workbook.createCellStyle();
		// unlockedCellStyle.setLocked(true); //true or false based on the cell.
		unlockedCellStyle
				.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		unlockedCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cell.setCellStyle(unlockedCellStyle);
		// sheet.protectSheet("notEditable");

		// //comment the cell as not editable
		// Drawing drawing = cell.getSheet().createDrawingPatriarch();
		// CreationHelper factory =
		// cell.getSheet().getWorkbook().getCreationHelper();
		// ClientAnchor anchor = factory.createClientAnchor();
		// anchor.setCol1(cell.getColumnIndex());
		// anchor.setCol2(cell.getColumnIndex());
		// anchor.setRow1(cell.getRowIndex());
		// anchor.setRow2(cell.getRowIndex());
		// Comment comment = drawing.createCellComment(anchor);
		// RichTextString commentText =
		// factory.createRichTextString("Nije za uređivanje!");
		// comment.setString(commentText);
		// comment.setVisible(false);
		// cell.setCellComment(comment);
	}

	@Override
	public void synchronizeBlagoslov(boolean isArchive,
			MultipartFile excelFile, List<Long> synchronizedIds,
			List<Long> synchronizedWithErrorIds)
			throws IllegalArgumentException {
		try {

			XSSFWorkbook workbook = new XSSFWorkbook(excelFile.getInputStream());
			if (workbook.getNumberOfSheets() != 1) {
				throw new IllegalArgumentException();
			}
			XSSFSheet sheet = workbook.getSheetAt(0);
			if ((sheet.getLastRowNum() + 1) < 1) {
				throw new IllegalArgumentException();
			}
			Iterator<Row> rowIterator = sheet.rowIterator();
			// skip the first row with names
			rowIterator.next();

			while (rowIterator.hasNext()) {
				Blagoslov blagoslov = null;
				try {
					Row row = rowIterator.next();
					String idString = getCellValueNullAsBlank(row, 12);
					if (idString == null || idString.isEmpty()) {
						blagoslov = new Blagoslov();
					} else {
						long id = parseCellLong(idString);
						blagoslov = dao.findById(Blagoslov.class, id);
					}
					
					//id not found, then create new
					if(blagoslov==null) {
						blagoslov = new Blagoslov();
					}
					
					Blagoslov oldBlagoslov = copy(blagoslov);
					
					blagoslov.setIme(getCellValueNullAsBlank(row, 0));
					blagoslov.setPrezime(getCellValueNullAsBlank(row, 1));
					blagoslov
							.setUlicaIDatum(getUlicaByNaziv(getCellValueNullAsBlank(
									row, 2)));
					blagoslov
							.setKucniBroj(parseCellInt(getCellValueNullAsBlank(
									row, 4)));
					blagoslov.setKat(parseCellInt(getCellValueNullAsBlank(row,
							5)));
					blagoslov
							.setBrojStana(parseCellInt(getCellValueNullAsBlank(
									row, 6)));
					blagoslov.setVrijemeBlagoslova(getCellValueNullAsBlank(row,
							7));
					blagoslov
							.setVrijemeBlagoslovaOstalo(getCellValueNullAsBlank(
									row, 8));
					blagoslov.setEmail(getCellValueNullAsBlank(row, 9));
					blagoslov.setBrojTelefona(getCellValueNullAsBlank(row, 10));
					blagoslov.setNapomene(getCellValueNullAsBlank(row, 11));

					blagoslov.setArchive(isArchive);

					if (hasRecordChanged(oldBlagoslov, blagoslov)) {
						blagoslov = dao.update(blagoslov);
						synchronizedIds.add(blagoslov.getId());
					}
				} catch (Exception exc) {
					synchronizedWithErrorIds.add(blagoslov.getId());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(
					"Predani .xlsx dokument je neispravan!");
		} catch (Exception ex) {
			throw new IllegalArgumentException(
					"Neispravan format .xlsx dokumenta! Probajte generirati jedan dokument ako ne znate koji je ispravan format.");
		}
	}

	private Blagoslov copy(Blagoslov blagoslov) {
		return createFromFormDontPersist(createFromBlagoslov(blagoslov));
	}
	
	private Krstenje copy(Krstenje krstenje) {
		return createFromFormDontPersist(createFromKrstenje(krstenje));
	}
	
	private Krizma copy(Krizma krizma) {
		return createFromFormDontPersist(createFromKrizma(krizma));
	}

	private int parseCellInt(String number) {
		return parseCellDouble(number).intValue();
	}

	private long parseCellLong(String number) {
		return parseCellDouble(number).longValue();
	}

	private Double parseCellDouble(String number) {
		return Double.parseDouble(number);
	}

	private String getCellValueNullAsBlank(Row row, int cellIndex) {
		return getCellNullAsBlank(row, cellIndex).toString();
	}

	private Cell getCellNullAsBlank(Row row, int cellIndex) {
		return row.getCell(cellIndex, Row.CREATE_NULL_AS_BLANK);
	}
}
