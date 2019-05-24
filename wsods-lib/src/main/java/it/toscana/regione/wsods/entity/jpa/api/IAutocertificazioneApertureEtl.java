package it.toscana.regione.wsods.entity.jpa.api;

import java.util.Date;

public interface IAutocertificazioneApertureEtl extends IAutocertificazioneEtl{

	Date getDataFine();

	void setDataFine(final Date dataFine);

}