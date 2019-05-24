package it.toscana.regione.wsods.entity.jpa.api;


public interface IEsitoInvioEtl extends IDbEntity {

	String getEsitoCod();

	void setEsitoCod(final String erroreCod);

	String getEsitoDesc();

	void setEsitoDesc(final String erroreDesc);

	String getEsito();

	void setEsito(final String esito);

	long getFkAutocertificazioneEtl();

	void setFkAutocertificazioneEtl(final long fkAutocertificazioneApertureEtl);


	long getFkWsodsSoapTracking();

	void setFkWsodsSoapTracking(final long fkWsodsSoapTracking);
	
	String getStackTrace();

	void setStackTrace(final String stackTrace);

}