package knezija.controllers;

import java.nio.channels.Pipe.SinkChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import knezija.models.Blagoslov;
import knezija.models.Content;
import knezija.models.Krstenje;
import knezija.models.Ulica;
import knezija.models.forms.BlagoslovForm;
import knezija.models.forms.ContentForm;
import knezija.models.forms.KrstenjeForm;
import knezija.models.forms.SynchronizableForm;
import knezija.models.forms.UlicaForm;
import knezija.services.IPrijaveManager;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BlagoslovController {
	@Autowired
	private IPrijaveManager prijaveManager;

	//
	/*
	 * ULICE
	 */
	//

	@RequestMapping("/blagoslov/ulice")
	public String displayUlice(Map<String, Object> model) {

		populateUliceView(model);
		return "ulice";
	}

	private void populateUliceView(Map<String, Object> model) {
		model.put("ulice", prijaveManager.getAllUliceAsForm(true));
	}

	@RequestMapping("/blagoslov/ulice/create-ulica-view")
	public String displayCreateUlicaView(Map<String, Object> model,
			HttpServletRequest req) {
		UlicaForm form = new UlicaForm();
		model.put("form", form);

		return "createUlica";
	}

	@RequestMapping(value = "/blagoslov/ulice/create-ulica-view/save", method = RequestMethod.POST)
	public String createUlica(Map<String, Object> model,
			@Valid @ModelAttribute("form") UlicaForm form, BindingResult result) {
		if (!result.hasErrors()) {
			Ulica ulica = prijaveManager.createFromForm(form);
			return "redirect:/blagoslov/ulice";
		} else {
			return "createUlica";
		}
	}

	@RequestMapping("/blagoslov/ulice/update-ulica-view/{id}")
	public String displayUpdateUlicaView(Map<String, Object> model,
			@PathVariable("id") String ulicaId, HttpServletRequest req) {
		long id = Long.parseLong(ulicaId);

		Ulica ulica = prijaveManager.findUlicaById(id);
		UlicaForm form = prijaveManager.createFromUlica(ulica, false);
		model.put("form", form);

		model.put("id", id);
		return "createUlica";
	}

	@RequestMapping(value = "/blagoslov/ulice/update-ulica-view/{id}/save", method = RequestMethod.POST)
	public String updateContent(Map<String, Object> model,
			@Valid @ModelAttribute("form") UlicaForm form,
			BindingResult result, @PathVariable("id") String ulicaId,
			HttpServletRequest req) {

		long id = Long.parseLong(ulicaId);

		if (!result.hasErrors()) {
			prijaveManager.updateFromForm(form, id);
			model.put("ulicaUpdatedMessage",
					"Uspješno ste izmijenili podatke o ulici za blagoslov.");
		}

		model.put("id", id);
		return "createUlica";
	}

	@RequestMapping(value = "/blagoslov/ulice/{id}/delete", method = RequestMethod.GET)
	public String deleteContent(Map<String, Object> model,
			@PathVariable("id") String ulicaId) {

		long id = Long.parseLong(ulicaId);
		prijaveManager.deleteUlica(id);

		return "redirect:/blagoslov/ulice";
	}

	//
	/**
	 * BLAGOSLOV
	 */
	//
	@RequestMapping("/blagoslov/prijava")
	public String displayBlagoslovPrijavaView(Map<String, Object> model) {
		BlagoslovForm form = new BlagoslovForm();
		model.put("form", form);

		populateBlagoslovPrijavaView(model);
		return "blagoslovPrijava";
	}

	private void populateBlagoslovPrijavaView(Map<String, Object> model) {
		model.put("ulice", prijaveManager.getAllUliceAsForm(true));
	}

	@RequestMapping("/blagoslov/prijava/{id}")
	public String displayBlagoslovPrijava(Map<String, Object> model,
			@PathVariable("id") String blagoslovId) {
		long id = Long.parseLong(blagoslovId);
		BlagoslovForm form = prijaveManager.createFromBlagoslov(prijaveManager
				.getBlagoslovById(id));
		model.put("form", form);
		model.put("isFormDisabled", true);

		populateBlagoslovPrijavaView(model);
		return "blagoslovPrijava";
	}

	@RequestMapping(value = "/blagoslov/prijava/save", method = RequestMethod.POST)
	public String saveBlagoslovPrijava(Map<String, Object> model,
			@Valid @ModelAttribute("form") BlagoslovForm form,
			BindingResult result) {

		if (prijaveManager.checkValidOnCreate(form, result)) {
			Blagoslov blagoslov = prijaveManager.createFromForm(form);
			model.put("successMessage",
					"Uspješno ste se prijavili za blagoslov obitelji.");
			model.put("isFormDisabled", true);
		}

		populateBlagoslovPrijavaView(model);
		return "blagoslovPrijava";
	}

	/**
	 * Pristupa isključivo admin.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/blagoslov/display-prijave")
	public String displayBlagoslove(Map<String, Object> model) {

		populateDisplayBlagosloveView(model, false);
		return "displayBlagoslove";
	}

	@RequestMapping("/blagoslov/display-prijave/archive")
	public String displayBlagosloveArchive(Map<String, Object> model) {

		populateDisplayBlagosloveView(model, true);
		return "displayBlagoslove";
	}

	private void populateDisplayBlagosloveView(Map<String, Object> model,
			boolean isArchive) {
		List<BlagoslovForm> blagoslovi = prijaveManager
				.getAllBlagosloviAsForm(isArchive);

		model.put("blagoslovi", blagoslovi);
		model.put("isArchive", isArchive);
	}

	@RequestMapping("/blagoslov/prijava/{id}/delete")
	public String deleteBlagoslov(Map<String, Object> model,
			@PathVariable("id") String blagoslovId) {
		Long id = Long.parseLong(blagoslovId);

		boolean isArchive = prijaveManager.isArchiveBlagoslov(id);
		String urlAddition = isArchive ? "/archive" : "";

		prijaveManager.deleteBlagoslov(id);

		return "redirect:/blagoslov/display-prijave" + urlAddition;
	}

	@RequestMapping("/blagoslov/prijava/{id}/send-to-archive")
	public String archiveBlagoslov(Map<String, Object> model,
			@PathVariable("id") String blagoslovId) {
		Long id = Long.parseLong(blagoslovId);
		prijaveManager.archiveBlagoslov(id);

		return "redirect:/blagoslov/display-prijave";
	}

	// @RequestMapping(value = "/blagoslov/prijava/{id}/save-datum-blagoslova",
	// method = RequestMethod.POST)
	// public String saveDatumBlagoslova(Map<String, Object> model,
	// @RequestParam("datumBlagoslova") String datumBlagoslovaString,
	// @PathVariable("id") String prijavaId) {
	// Long id = Long.parseLong(prijavaId);
	// if (!prijaveManager.updateDatumBlagoslova(id, datumBlagoslovaString)) {
	// model.put("dateErrorMessage", "Neispravan format datuma!");
	// }
	//
	// boolean isArchive = prijaveManager.isArchiveBlagoslov(id);
	//
	// populateDisplayBlagosloveView(model, isArchive);
	// return "displayKrstenja";
	// }

	@RequestMapping("/blagoslov/display-prijave/generate-excel-all")
	public void generateExcelAllBlagoslov(Map<String, Object> model,
			HttpServletResponse resp) {
		XSSFWorkbook workbook = prijaveManager
				.generateWorkbookBlagoslovAll(prijaveManager
						.getAllBlagosloviAsForm(false));
		prijaveManager.respondWithExcel(workbook, resp,
				"AktivnePrijaveZaBlagoslov.xlsx");
	}

	@RequestMapping("/blagoslov/display-prijave/archive/generate-excel-all")
	public void generateExcelAllBlagoslovArchive(Map<String, Object> model,
			HttpServletResponse resp) {
		XSSFWorkbook workbook = prijaveManager
				.generateWorkbookBlagoslovAll(prijaveManager
						.getAllBlagosloviAsForm(true));
		prijaveManager.respondWithExcel(workbook, resp,
				"ArhivaPrijavaZaBlagoslov.xlsx");
	}

	@RequestMapping(value = "/blagoslov/display-prijave/synchronize", method = RequestMethod.POST)
	public String synchronizeBlagoslovFromExcel(Map<String, Object> model,
			HttpServletResponse resp,
			@RequestParam("excelFile") MultipartFile excelFile) {
		return synchronizeBlagoslov(model, excelFile, false);
	}

	@RequestMapping(value = "/blagoslov/display-prijave/archive/synchronize", method = RequestMethod.POST)
	public String synchronizeBlagoslovArchiveFromExcel(
			Map<String, Object> model, HttpServletResponse resp,
			@RequestParam("excelFile") MultipartFile excelFile) {
		return synchronizeBlagoslov(model, excelFile, true);
	}

	private String synchronizeBlagoslov(Map<String, Object> model,
			MultipartFile excelFile, boolean isArchive) {
		List<Long> sinkroniziraniBlagoslovi = new ArrayList<>();
		List<Long> nesinkroniziraniBlagoslovi = new ArrayList<>();
		try {
			prijaveManager.synchronizeBlagoslov(isArchive, excelFile,
					sinkroniziraniBlagoslovi, nesinkroniziraniBlagoslovi);
			PrijaveController.syncSuccessMessage(model,
					sinkroniziraniBlagoslovi, nesinkroniziraniBlagoslovi);
		} catch (IllegalArgumentException exc) {
			model.put("excelFileError", exc.getMessage());
		}

		populateDisplayBlagosloveView(model, isArchive,
				sinkroniziraniBlagoslovi, nesinkroniziraniBlagoslovi);
		return "displayBlagoslove";
	}

	private void populateDisplayBlagosloveView(Map<String, Object> model,
			boolean isArchive, List<Long> sinkroniziraniBlagoslovi,
			List<Long> nesinkroniziraniBlagoslovi) {
		populateDisplayBlagosloveView(model, isArchive);
		@SuppressWarnings("unchecked")
		List<BlagoslovForm> blagoslovi = (List<BlagoslovForm>) model
				.get("blagoslovi");
		synchronizationSuccessVsError(blagoslovi, sinkroniziraniBlagoslovi,
				nesinkroniziraniBlagoslovi);
	}

	/**
	 * Call this method after synchronization completes.
	 * 
	 * @param synchronizableForms
	 *            eg., blagosloviForms, krstenjaForms,...
	 * @param synchronizedWithSuccess
	 * @param synchronizedWithError
	 */
	public static <T extends SynchronizableForm> void synchronizationSuccessVsError(
			List<T> synchronizableForms, List<Long> synchronizedWithSuccess,
			List<Long> synchronizedWithError) {
		for (T form : synchronizableForms) {
			if (synchronizedWithSuccess.contains(form.getId())) {
				form.setInputColor("green");
			} else if (synchronizedWithError.contains(form.getId())) {
				form.setInputColor("red");
			} else {
				form.setInputColor("");
			}
		}
	}
}
