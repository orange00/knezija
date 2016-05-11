package knezija.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import knezija.models.Krizma;
import knezija.models.Krstenje;
import knezija.models.forms.BlagoslovForm;
import knezija.models.forms.KrizmaForm;
import knezija.models.forms.KrstenjeForm;
import knezija.services.IPrijaveManager;
import knezija.services.IUserManager;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PrijaveController {
	@Autowired
	private IPrijaveManager prijaveManager;
	@Autowired
	private IUserManager userManager;

	// ///
	/*
	 * KRŠTENJE
	 */
	// ///
	@RequestMapping("/sakramenti/krstenje/prijava")
	public String displayKrstenjePrijavaView(Map<String, Object> model) {
		KrstenjeForm form = new KrstenjeForm();
		model.put("krstenjeForm", form);

		return "krstenjePrijava";
	}

	@RequestMapping("/sakramenti/krstenje/prijava/{id}")
	public String displayKrstenjePrijava(Map<String, Object> model,
			@PathVariable("id") String krstenjeId) {
		long id = Long.parseLong(krstenjeId);
		KrstenjeForm form = prijaveManager.createFromKrstenje(prijaveManager
				.getKrstenjeById(id));
		model.put("krstenjeForm", form);
		model.put("isFormDisabled", true);

		return "krstenjePrijava";
	}

	@RequestMapping(value = "/sakramenti/krstenje/prijava/save", method = RequestMethod.POST)
	public String saveKrstenjePrijava(Map<String, Object> model,
			@Valid @ModelAttribute("krstenjeForm") KrstenjeForm form,
			BindingResult result) {

		if (prijaveManager.checkValidOnCreate(form, result)) {
			Krstenje krstenje = prijaveManager.createFromForm(form);
			model.put("successMessage",
					"Uspješno ste prijavili dijete za krštenje.");
			model.put("isFormDisabled", true);
		}

		return "krstenjePrijava";
	}

	/**
	 * Pristupa isključivo admin.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/sakramenti/krstenje/display-prijave")
	public String displayKrstenja(Map<String, Object> model) {

		populateDisplayKrstenjaView(model, false);
		return "displayKrstenja";
	}

	@RequestMapping("/sakramenti/krstenje/display-prijave/archive")
	public String displayKrstenjaArchive(Map<String, Object> model) {

		populateDisplayKrstenjaView(model, true);
		return "displayKrstenja";
	}

	private void populateDisplayKrstenjaView(Map<String, Object> model,
			boolean isArchive) {
		List<KrstenjeForm> krstenja = prijaveManager
				.getAllKrstenjaAsForm(isArchive);

		model.put("krstenja", krstenja);
		model.put("isArchive", isArchive);
	}

	@RequestMapping("/sakramenti/krstenje/prijava/{id}/delete")
	public String deleteKrstenje(Map<String, Object> model,
			@PathVariable("id") String krstenjeId) {
		Long id = Long.parseLong(krstenjeId);

		boolean isArchive = prijaveManager.isArchiveKrstenje(id);
		String urlAddition = isArchive ? "/archive" : "";

		prijaveManager.deleteKrstenje(id);

		return "redirect:/sakramenti/krstenje/display-prijave" + urlAddition;
	}

	@RequestMapping("/sakramenti/krstenje/prijava/{id}/send-to-archive")
	public String archiveKrstenje(Map<String, Object> model,
			@PathVariable("id") String krstenjeId) {
		Long id = Long.parseLong(krstenjeId);
		prijaveManager.archiveKrstenje(id);

		return "redirect:/sakramenti/krstenje/display-prijave";
	}

	@RequestMapping(value = "/sakramenti/krstenje/prijava/{id}/save-datum-krstenja", method = RequestMethod.POST)
	public String saveDatumKrstenja(Map<String, Object> model,
			@RequestParam("datumKrstenja") String datumKrstenjaString,
			@PathVariable("id") String prijavaId) {
		Long id = Long.parseLong(prijavaId);
		if (!prijaveManager.updateDatumKrstenja(id, datumKrstenjaString)) {
			model.put("dateErrorMessage", "Neispravan format datuma!");
		}

		boolean isArchive = prijaveManager.isArchiveKrstenje(id);

		populateDisplayKrstenjaView(model, isArchive);
		return "displayKrstenja";
	}

	@RequestMapping("/sakramenti/krstenje/display-prijave/generate-excel-all")
	public void generateExcelAllKrstenje(Map<String, Object> model,
			HttpServletResponse resp) {
		XSSFWorkbook workbook = prijaveManager
				.generateWorkbookKrstenjeAll(prijaveManager
						.getAllKrstenjaAsForm(false));
		prijaveManager.respondWithExcel(workbook, resp,
				"AktivneUpisniceZaKrstenje.xlsx");
	}

	@RequestMapping("/sakramenti/krstenje/display-prijave/archive/generate-excel-all")
	public void generateExcelAllKrstenjeArchive(Map<String, Object> model,
			HttpServletResponse resp) {
		XSSFWorkbook workbook = prijaveManager
				.generateWorkbookKrstenjeAll(prijaveManager
						.getAllKrstenjaAsForm(true));
		prijaveManager.respondWithExcel(workbook, resp,
				"ArhivaUpisnicaZaKrstenje.xlsx");
	}

	@RequestMapping(value = "/sakramenti/krstenje/display-prijave/synchronize", method = RequestMethod.POST)
	public String synchronizeKrstenjeFromExcel(Map<String, Object> model,
			HttpServletResponse resp,
			@RequestParam("excelFile") MultipartFile excelFile) {
		return synchronizeKrstenje(model, excelFile, false);
	}

	@RequestMapping(value = "/sakramenti/krstenje/display-prijave/archive/synchronize", method = RequestMethod.POST)
	public String synchronizeKrstenjeArchiveFromExcel(
			Map<String, Object> model, HttpServletResponse resp,
			@RequestParam("excelFile") MultipartFile excelFile) {
		return synchronizeKrstenje(model, excelFile, true);
	}

	private String synchronizeKrstenje(Map<String, Object> model,
			MultipartFile excelFile, boolean isArchive) {
		List<Long> sinkroniziraniBlagoslovi = new ArrayList<>();
		List<Long> nesinkroniziraniBlagoslovi = new ArrayList<>();
		try {
			prijaveManager.synchronizeKrstenje(isArchive, excelFile,
					sinkroniziraniBlagoslovi, nesinkroniziraniBlagoslovi);
			syncSuccessMessage(model, sinkroniziraniBlagoslovi, nesinkroniziraniBlagoslovi);
		} catch (IllegalArgumentException exc) {
			model.put("excelFileError", exc.getMessage());
		}

		populateDisplayKrstenjaView(model, isArchive, sinkroniziraniBlagoslovi,
				nesinkroniziraniBlagoslovi);
		return "displayKrstenja";
	}

	private void populateDisplayKrstenjaView(Map<String, Object> model,
			boolean isArchive, List<Long> sinkroniziraniBlagoslovi,
			List<Long> nesinkroniziraniBlagoslovi) {
		populateDisplayKrstenjaView(model, isArchive);
		@SuppressWarnings("unchecked")
		List<KrstenjeForm> krstenja = (List<KrstenjeForm>) model
				.get("krstenja");
		BlagoslovController.synchronizationSuccessVsError(krstenja,
				sinkroniziraniBlagoslovi, nesinkroniziraniBlagoslovi);
	}

	// ///
	/*
	 * KRIZMA
	 */
	// ///
	@RequestMapping("/sakramenti/krizma/prijava")
	public String displayKrizmaPrijavaView(Map<String, Object> model) {

		doDisplayKrizmaIliPricestPrijavaView(model);

		return "krizmaPrijava";
	}

	@RequestMapping("/sakramenti/krizma/prijava/{id}")
	public String displayKrizmaPrijava(Map<String, Object> model,
			@PathVariable("id") String krizmaId) {

		doDisplayKrizmaIliPricestPrijava(model, krizmaId);

		return "krizmaPrijava";
	}

	@RequestMapping(value = "/sakramenti/krizma/prijava/save", method = RequestMethod.POST)
	public String saveKrizmaPrijava(Map<String, Object> model,
			@Valid @ModelAttribute("form") KrizmaForm form, BindingResult result) {

		doSaveKrizmaIliPricestPrijava(model, form, result);

		return "krizmaPrijava";
	}

	/**
	 * Pristupa isključivo admin.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/sakramenti/krizma/display-prijave")
	public String displayKrizme(Map<String, Object> model) {

		populateDisplayKrizmeView(model, false, false);
		return "displayKrizme";
	}

	@RequestMapping("/sakramenti/krizma/display-prijave/archive")
	public String displayKrizmeArchive(Map<String, Object> model) {

		populateDisplayKrizmeView(model, true, false);
		return "displayKrizme";
	}

	private void populateDisplayKrizmeView(Map<String, Object> model,
			boolean isArchive, boolean isPricest) {
		List<KrizmaForm> krizme = prijaveManager.getAllKrizmeAsForm(isArchive,
				isPricest);

		model.put("stavke", krizme);
		model.put("isArchive", isArchive);
	}

	@RequestMapping("/sakramenti/krizma/prijava/{id}/delete")
	public String deleteKrizmu(Map<String, Object> model,
			@PathVariable("id") String krizmaId) {

		String urlAddition = doDeleteKrizmuIliPricest(model, krizmaId);

		return "redirect:/sakramenti/krizma/display-prijave" + urlAddition;
	}

	@RequestMapping("/sakramenti/krizma/prijava/{id}/send-to-archive")
	public String archiveKrizmu(Map<String, Object> model,
			@PathVariable("id") String krizmaId) {

		doArchiveKrizmaIliPricest(model, krizmaId);

		return "redirect:/sakramenti/krizma/display-prijave";
	}

	@RequestMapping(value = "/sakramenti/krizma/prijava/{id}/save-datum-krizme", method = RequestMethod.POST)
	public String saveDatumKrizme(Map<String, Object> model,
			@RequestParam("datumKrizme") String datumKrizmeString,
			@PathVariable("id") String prijavaId) {

		doSaveDatumKrizmaIliPricest(model, datumKrizmeString, prijavaId, false);

		return "displayKrizme";
	}

	@RequestMapping("/sakramenti/krizma/display-prijave/generate-excel-all")
	public void generateExcelAllKrizma(Map<String, Object> model,
			HttpServletResponse resp) {
		prijaveManager.generateExcelAllKrizmaOrPricest(false, false, resp,
				"AktivneUpisniceZaKrizmu.xlsx");
	}

	@RequestMapping("/sakramenti/krizma/display-prijave/archive/generate-excel-all")
	public void generateExcelAllKrizmaArchive(Map<String, Object> model,
			HttpServletResponse resp) {
		prijaveManager.generateExcelAllKrizmaOrPricest(true, false, resp,
				"ArhivaUpisnicaZaKrizmu.xlsx");
	}

	@RequestMapping(value = "/sakramenti/krizma/display-prijave/synchronize", method = RequestMethod.POST)
	public String synchronizeKrizmaFromExcel(Map<String, Object> model,
			HttpServletResponse resp,
			@RequestParam("excelFile") MultipartFile excelFile) {
		return synchronizeKrizma(model, excelFile, false, false);
	}

	@RequestMapping(value = "/sakramenti/krizma/display-prijave/archive/synchronize", method = RequestMethod.POST)
	public String synchronizeKrizmaArchiveFromExcel(Map<String, Object> model,
			HttpServletResponse resp,
			@RequestParam("excelFile") MultipartFile excelFile) {
		return synchronizeKrizma(model, excelFile, true, false);
	}

	private String synchronizeKrizma(Map<String, Object> model,
			MultipartFile excelFile, boolean isArchive, boolean isPricest) {
		List<Long> sinkroniziraniBlagoslovi = new ArrayList<>();
		List<Long> nesinkroniziraniBlagoslovi = new ArrayList<>();
		try {
			prijaveManager.synchronizeKrizma(isArchive,isPricest, excelFile,
					sinkroniziraniBlagoslovi, nesinkroniziraniBlagoslovi);
			
			syncSuccessMessage(model, sinkroniziraniBlagoslovi, nesinkroniziraniBlagoslovi);
		} catch (IllegalArgumentException exc) {
			model.put("excelFileError", exc.getMessage());
		}

		populateDisplayKrizmeView(model, isArchive, isPricest,
				sinkroniziraniBlagoslovi, nesinkroniziraniBlagoslovi);
		return isPricest ? "displayPricesti" : "displayKrizme";
	}

	public static void syncSuccessMessage(Map<String, Object> model, List<Long> sinkroniziraniBlagoslovi, List<Long> nesinkroniziraniBlagoslovi) {
		model.put("syncSuccessMessage", "Uspješno ažurirano: "
				+ sinkroniziraniBlagoslovi.size()
				+ " zapisa, Neuspješno ažurirano: "
				+ nesinkroniziraniBlagoslovi.size() + " zapisa");
	}

	private void populateDisplayKrizmeView(Map<String, Object> model,
			boolean isArchive, boolean isPricest,
			List<Long> sinkroniziraniBlagoslovi,
			List<Long> nesinkroniziraniBlagoslovi) {
		populateDisplayKrizmeView(model, isArchive, isPricest);
		@SuppressWarnings("unchecked")
		List<KrizmaForm> krizme = (List<KrizmaForm>) model
				.get("stavke");
		BlagoslovController.synchronizationSuccessVsError(krizme,
				sinkroniziraniBlagoslovi, nesinkroniziraniBlagoslovi);
	}

	// ///
	/*
	 * Prva Pričest
	 */
	// ///
	@RequestMapping("/sakramenti/pricest/prijava")
	public String displayPricestPrijavaView(Map<String, Object> model) {
		doDisplayKrizmaIliPricestPrijavaView(model);

		model.put("isPricest", true);
		return "krizmaPrijava";
	}

	private void doDisplayKrizmaIliPricestPrijavaView(Map<String, Object> model) {
		KrizmaForm form = new KrizmaForm();
		model.put("form", form);
	}

	@RequestMapping("/sakramenti/pricest/prijava/{id}")
	public String displayPricestPrijava(Map<String, Object> model,
			@PathVariable("id") String pricestId) {

		doDisplayKrizmaIliPricestPrijava(model, pricestId);

		model.put("isPricest", true);
		return "krizmaPrijava";
	}

	private void doDisplayKrizmaIliPricestPrijava(Map<String, Object> model,
			String pricestId) {
		long id = Long.parseLong(pricestId);
		KrizmaForm form = prijaveManager.createFromKrizma(prijaveManager
				.getKrizmaById(id));
		model.put("form", form);
		model.put("isFormDisabled", true);
	}

	@RequestMapping(value = "/sakramenti/pricest/prijava/save", method = RequestMethod.POST)
	public String savePricestPrijava(Map<String, Object> model,
			@Valid @ModelAttribute("form") KrizmaForm form, BindingResult result) {

		doSaveKrizmaIliPricestPrijava(model, form, result);

		model.put("isPricest", true);
		return "krizmaPrijava";
	}

	private void doSaveKrizmaIliPricestPrijava(Map<String, Object> model,
			KrizmaForm form, BindingResult result) {
		if (prijaveManager.checkValidOnCreate(form, result)) {
			Krizma krizma = prijaveManager.createFromForm(form);
			model.put("successMessage", "Uspješno ste prijavili pristupnika.");
			model.put("isFormDisabled", true);
		}
	}

	/**
	 * Pristupa isključivo admin.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/sakramenti/pricest/display-prijave")
	public String displayPricest(Map<String, Object> model) {

		populateDisplayKrizmeView(model, false, true);
		return "displayPricesti";
	}

	@RequestMapping("/sakramenti/pricest/display-prijave/archive")
	public String displayPricestArchive(Map<String, Object> model) {

		populateDisplayKrizmeView(model, true, true);
		return "displayPricesti";
	}

	@RequestMapping("/sakramenti/pricest/prijava/{id}/delete")
	public String deletePricest(Map<String, Object> model,
			@PathVariable("id") String pricestId) {

		String urlAddition = doDeleteKrizmuIliPricest(model, pricestId);

		return "redirect:/sakramenti/pricest/display-prijave" + urlAddition;
	}

	/**
	 * Returns url addition denoting whether it is archive or not.
	 * 
	 * @param model
	 * @param pricestId
	 * @return
	 */
	private String doDeleteKrizmuIliPricest(Map<String, Object> model,
			String pricestId) {
		Long id = Long.parseLong(pricestId);

		boolean isArchive = prijaveManager.isArchiveKrizma(id);
		String urlAddition = isArchive ? "/archive" : "";

		prijaveManager.deleteKrizmu(id);

		return urlAddition;
	}

	@RequestMapping("/sakramenti/pricest/prijava/{id}/send-to-archive")
	public String archivePricest(Map<String, Object> model,
			@PathVariable("id") String pricestId) {

		doArchiveKrizmaIliPricest(model, pricestId);

		return "redirect:/sakramenti/pricest/display-prijave";
	}

	private void doArchiveKrizmaIliPricest(Map<String, Object> model,
			String pricestId) {
		Long id = Long.parseLong(pricestId);
		prijaveManager.archiveKrizmu(id);
	}

	@RequestMapping(value = "/sakramenti/pricest/prijava/{id}/save-datum-pricesti", method = RequestMethod.POST)
	public String saveDatumPricest(Map<String, Object> model,
			@RequestParam("datumPricesti") String datumPricestiString,
			@PathVariable("id") String prijavaId) {

		doSaveDatumKrizmaIliPricest(model, datumPricestiString, prijavaId, true);

		return "displayPricesti";
	}

	private void doSaveDatumKrizmaIliPricest(Map<String, Object> model,
			String datumKrizmeString, String prijavaId, boolean isPricest) {
		Long id = Long.parseLong(prijavaId);
		if (!prijaveManager.updateDatumKrizme(id, datumKrizmeString)) {
			model.put("dateErrorMessage", "Neispravan format datuma!");
		}

		boolean isArchive = prijaveManager.isArchiveKrizma(id);

		populateDisplayKrizmeView(model, isArchive, isPricest);
	}

	@RequestMapping("/sakramenti/pricest/display-prijave/generate-excel-all")
	public void generateExcelAllPricest(Map<String, Object> model,
			HttpServletResponse resp) {
		prijaveManager.generateExcelAllKrizmaOrPricest(false, true, resp,
				"AktivneUpisniceZaPrvuPricest.xlsx");
	}

	@RequestMapping("/sakramenti/pricest/display-prijave/archive/generate-excel-all")
	public void generateExcelAllPricestArchive(Map<String, Object> model,
			HttpServletResponse resp) {
		prijaveManager.generateExcelAllKrizmaOrPricest(true, true, resp,
				"ArhivaUpisnicaZaPrvuPricest.xlsx");
	}
	
	@RequestMapping(value = "/sakramenti/pricest/display-prijave/synchronize", method = RequestMethod.POST)
	public String synchronizePricestFromExcel(Map<String, Object> model,
			HttpServletResponse resp,
			@RequestParam("excelFile") MultipartFile excelFile) {
		return synchronizeKrizma(model, excelFile, false, true);
	}

	@RequestMapping(value = "/sakramenti/pricest/display-prijave/archive/synchronize", method = RequestMethod.POST)
	public String synchronizePricestArchiveFromExcel(Map<String, Object> model,
			HttpServletResponse resp,
			@RequestParam("excelFile") MultipartFile excelFile) {
		return synchronizeKrizma(model, excelFile, true, true);
	}
}
