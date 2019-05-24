package it.toscana.regione.wsods.entity.jpa.abs;

import it.toscana.regione.wsods.entity.jpa.api.IEsitoInvioEtl;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

/**
 * @author cciurli
 *
 */
@MappedSuperclass
public abstract class EsitoInvioEtl extends DbEntity implements IEsitoInvioEtl {

	private static final long serialVersionUID = 4800649169066823874L;
	
	@Column(name = "ESITO_COD", length = 16)
	private String esitoCod;
	@Column(name = "ESITO_DESC", length = 512)
	private String esitoDesc;
	@Column(length = 2)
	private String esito;
	@Column(name = "FK_AUTOCERTIFICAZIONE_ETL", nullable = false)
	private long fkAutocertificazioneEtl;
	@Column(name = "FK_WSODS_SOAP_TRACKING", nullable = true)
	private long fkWsodsSoapTracking;
	@Lob
	@Column(name = "STACK_TRACE")
	private String stackTrace;

	public EsitoInvioEtl() {
		super();
	}




	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.IEsitoInvioEtl#getErroreCod()
	 */
	@Override
	public String getEsitoCod() {
		return this.esitoCod;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.IEsitoInvioEtl#setErroreCod(java.lang.String)
	 */
	@Override
	public void setEsitoCod(String esitoCod) {
		this.esitoCod = esitoCod;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.IEsitoInvioEtl#getErroreDesc()
	 */
	@Override
	public String getEsitoDesc() {
		return this.esitoDesc;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.IEsitoInvioEtl#setErroreDesc(java.lang.String)
	 */
	@Override
	public void setEsitoDesc(String esitoDesc) {
		this.esitoDesc = esitoDesc;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.IEsitoInvioEtl#getEsito()
	 */
	@Override
	public String getEsito() {
		return this.esito;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.IEsitoInvioEtl#setEsito(java.lang.String)
	 */
	@Override
	public void setEsito(String esito) {
		this.esito = esito;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.IEsitoInvioEtl#getFkAutocertificazioneApertureEtl()
	 */
	@Override
	public long getFkAutocertificazioneEtl() {
		return this.fkAutocertificazioneEtl;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.IEsitoInvioEtl#setFkAutocertificazioneApertureEtl(long)
	 */
	@Override
	public void setFkAutocertificazioneEtl(long fkAutocertificazioneEtl) {
		this.fkAutocertificazioneEtl = fkAutocertificazioneEtl;
	}


	public long getFkWsodsSoapTracking() {
		return fkWsodsSoapTracking;
	}


	public void setFkWsodsSoapTracking(long fkWsodsSoapTracking) {
		this.fkWsodsSoapTracking = fkWsodsSoapTracking;
	}




	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.IEsitoInvioEtl#getStackTrace()
	 */
	@Override
	public String getStackTrace() {
		return this.stackTrace;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.IEsitoInvioEtl#setStackTrace(java.lang.String)
	 */
	@Override
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

}