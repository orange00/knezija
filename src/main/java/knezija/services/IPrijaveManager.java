package knezija.services;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import knezija.models.Blagoslov;
import knezija.models.Krizma;
import knezija.models.Krstenje;
import knezija.models.Ulica;
import knezija.models.forms.BlagoslovForm;
import knezija.models.forms.KrizmaForm;
import knezija.models.forms.KrstenjeForm;
import knezija.models.forms.UlicaForm;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface IPrijaveManager {

	boolean checkValidOnCreate(KrstenjeForm form, BindingResult result);

	// ///
	/*
	 * KRSTENJE
	 */
	// ///
	/**
	 * Also, subscribes the user if not already subscribed.
	 * 
	 * Note: Does not insert datumKrstenja from the given form.
	 * @param form
	 * @return
	 */
	Krstenje createFromForm(KrstenjeForm form);

	List<Krstenje> getAllKrstenja(boolean isArchive);

	void deleteKrstenje(long id);

	Date parseDate(String dateString);

	boolean updateDatumKrstenja(Long id, String datumKrstenjaString);

	List<KrstenjeForm> getAllKrstenjaAsForm(boolean isArchive);

	Krstenje archiveKrstenje(Long id);

	KrstenjeForm createFromKrstenje(Krstenje form);

	Krstenje getKrstenjeById(long krstenjeId);
	

	// ///
	/*
	 * KRIZMA
	 */
	// ///
	/**
	 * Also, subscribes the user if not already subscribed.
	 * 
	 * Note: Does not insert datumKrizme from the given form.
	 * @param form
	 * @return
	 */
	Krizma createFromForm(KrizmaForm form);

	List<Krizma> getAllKrizme(boolean isArchive, boolean isPricest);

	void deleteKrizmu(long id);

	boolean updateDatumKrizme(Long id, String datumKrizmeString);

	List<KrizmaForm> getAllKrizmeAsForm(boolean isArchive, boolean isPricest);

	Krizma archiveKrizmu(Long id);

	KrizmaForm createFromKrizma(Krizma form);

	Krizma getKrizmaById(long krizmaId);

	boolean checkValidOnCreate(KrizmaForm form, BindingResult result);

	boolean isArchiveKrizma(Long id);

	boolean isArchiveKrstenje(Long id);

	List<Ulica> getAllUlice();

	Ulica createFromForm(UlicaForm form);

	Ulica findUlicaById(long id);

	UlicaForm createFromUlica(Ulica ulica, boolean yearless);

	Ulica updateFromForm(UlicaForm form, long id);

	void deleteUlica(long id);

	List<UlicaForm> getAllUliceAsForm(boolean yearless);

	Blagoslov getBlagoslovById(long id);

	BlagoslovForm createFromBlagoslov(Blagoslov blagoslovById);

	boolean checkValidOnCreate(BlagoslovForm form, BindingResult result);

	Blagoslov createFromForm(BlagoslovForm form);

	List<BlagoslovForm> getAllBlagosloviAsForm(boolean isArchive);

	boolean isArchiveBlagoslov(Long id);

	void deleteBlagoslov(Long id);

	void archiveBlagoslov(Long id);

	boolean updateDatumBlagoslova(Long id, String datumBlagoslovaString);

	XSSFWorkbook generateWorkbookFromKrizmaForms(List<KrizmaForm> krizme);

	void respondWithExcel(XSSFWorkbook workbook, HttpServletResponse resp,
			String fileName);

	void generateExcelAllKrizmaOrPricest(boolean isArchive, boolean isPricest,
			HttpServletResponse resp, String fileName);

	XSSFWorkbook generateWorkbookKrstenjeAll(List<KrstenjeForm> krstenja);

	XSSFWorkbook generateWorkbookBlagoslovAll(
			List<BlagoslovForm> blagoslovi);

	void synchronizeBlagoslov(boolean isArchive, MultipartFile excelFile, List<Long> sinkroniziraniBlagoslovi, List<Long> nesinkroniziraniBlagoslovi) throws IllegalArgumentException;

	void synchronizeKrstenje(boolean isArchive, MultipartFile excelFile,
			List<Long> synchronizedIds, List<Long> synchronizedWithErrorIds)
			throws IllegalArgumentException;

	void synchronizeKrizma(boolean isArchive, boolean isPricest,
			MultipartFile excelFile, List<Long> synchronizedIds,
			List<Long> synchronizedWithErrorIds)
			throws IllegalArgumentException;

}
