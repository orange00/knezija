package knezija.services;

import java.util.Date;
import java.util.List;

import knezija.models.Krstenje;
import knezija.models.forms.KrstenjeForm;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public interface IPrijaveManager {

	boolean checkValidOnCreate(KrstenjeForm form, BindingResult result);

	/**
	 * Also, subscribes the user if not already subscribed.
	 * @param form
	 * @return
	 */
	Krstenje createFromForm(KrstenjeForm form);

	List<Krstenje> getAllKrstenja();

	void deleteKrstenje(long id);

	Date parseDate(String dateString);

	boolean updateDatumKrstenja(Long id, String datumKrstenjaString);

	List<KrstenjeForm> getAllKrstenjaAsForm();

}
