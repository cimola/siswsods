package it.toscana.regione.wsods.entity.jpa.api;

import java.util.Date;

public interface IAutocertificazioneChiusureEtl extends IAutocertificazioneEtl {

	Date getDataFineOld();

	void setDataFineOld(Date dataFineOld);

	Date getDataInizio();

	void setDataInizio(Date dataInizio);

	String getProtocollo();

	void setProtocollo(final String protocollo);

}