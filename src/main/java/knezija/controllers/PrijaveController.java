package knezija.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import knezija.models.Krstenje;
import knezija.models.forms.KrstenjeForm;
import knezija.services.IPrijaveManager;
import knezija.services.IUserManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PrijaveController {
	@Autowired
	private IPrijaveManager prijaveManager;
	@Autowired
	private IUserManager userManager;

	@RequestMapping("/sakramenti/krstenje/prijava")
	public String displayKrstenjePrijavaView(Map<String, Object> model) {
		KrstenjeForm form = new KrstenjeForm();
		model.put("krstenjeForm", form);

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

		populateDisplayKrstenjaView(model);
		return "displayKrstenja";
	}

	private void populateDisplayKrstenjaView(Map<String, Object> model) {
		List<KrstenjeForm> krstenja = prijaveManager.getAllKrstenjaAsForm();
		
		model.put("krstenja", krstenja);
	}

	@RequestMapping("/sakramenti/krstenje/prijava/{id}/delete")
	public String deleteKrstenje(Map<String, Object> model,
			@PathVariable("id") String krstenjeId) {
		Long id = Long.parseLong(krstenjeId);
		prijaveManager.deleteKrstenje(id);

		populateDisplayKrstenjaView(model);
		return "displayKrstenja";
	}

	@RequestMapping(value = "/sakramenti/krstenje/prijava/{id}/save-datum-krstenja", method = RequestMethod.POST)
	public String saveDatumKrstenja(Map<String, Object> model,
			@RequestParam("datumKrstenja") String datumKrstenjaString,
			@PathVariable("id") String prijavaId) {
		Long id = Long.parseLong(prijavaId);
		if (!prijaveManager
				.updateDatumKrstenja(id, datumKrstenjaString)) {
			model.put("dateErrorMessage", "Neispravan format datuma!");
		}

		populateDisplayKrstenjaView(model);
		return "displayKrstenja";
	}
}
