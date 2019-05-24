package it.toscana.regione.wsods.entity.jpa.api;

import java.util.Date;

import javax.persistence.Transient;

public interface IAutocertificazioneEtl  extends IDbEntity {
	
	
	@Transient public static final long STATO_INSERITO = 0L;
	@Transient public static final long STATO_INVIATA_CON_ESITO_POSITIVO = 1L;
	@Transient public static final long STATO_INVIATA_CON_ESITO_NEGATIVO_BLOCCANTE = 2L;
	@Transient public static final long STATO_INVIATA_CON_ESITO_NEGATIVO_REINVIABILE = 3L;
	
	@Transient public static final String ATTIVITA_APRI = "APRI";
	@Transient public static final String ATTIVITA_CHIUDI = "CHIUDI";
	@Transient public static final String ATTIVITA_ALTRO = "ALTRO";
		
	
	
	String getCodiceEsenzione();

	void setCodiceEsenzione(final String codiceEsenzione);

	Date getDataInvio();

	void setDataInvio(Date dataInvio);

	long getFkEsenzioneProrogata();

	void setFkEsenzioneProrogata(final long fkEsenzioneProrogata);

	String getIdUniBeneficiario();

	void setIdUniBeneficiario(final String idUniBeneficiario);

	String getIdUniDichiarante();

	void setIdUniDichiarante(final String idUniDichiarante);

	String getIdUniTitolare();

	void setIdUniTitolare(final String idUniTitolare);

	long getNumeroElaborazioni();

	void setNumeroElaborazioni(final long numeroElaborazioni);

	long getStatoElaborazione();

	void setStatoElaborazione(final long statoElaborazione);

	String getTitolo();

	void setTitolo(final String titolo);

	

	void incrementaElaborazioni();
}
