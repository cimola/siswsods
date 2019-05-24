package it.toscana.regione.wsods.entity.jpa.impl;

import it.toscana.regione.wsods.entity.jpa.abs.DbEntity;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneTmp;
import it.toscana.regione.wsods.entity.jpa.api.ICertificazioneTmp;
import it.toscana.regione.wsods.entity.jpa.api.IRicevutaDownloadSogei;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the RICEVUTA_DOWNLOAD_SOGEI database table.
 * 
 */
@Entity
@Table(name = "RICEVUTA_DOWNLOAD_SOGEI")
public class RicevutaDownloadSogei extends DbEntity implements IRicevutaDownloadSogei {
	
	/** fild serialVersionUID {@like long}*/
	private static final long serialVersionUID = 6539364461219739742L;
	
	@Column(name = "DATA_ORA_LIMITE", length = 12)
	private String dataOraLimite;
	
	@Column(name = "DIAGNOSTICO", length = 512)
	private String diagnostico;
	
	@Column(name = "ESITO", length = 4)
	private String esito;
	
	@Column(name = "COD_ASL", length = 3)
	private String codAsl;
	
	@Column(name = "COD_REGIONE", length = 3)
	private String codRegione;
	
	@Column(name = "NUMERO_RECORD_TROVATI")
	private long numeroRecordTrovati;
	
	@Column(name = "NUMERO_RECORD_ELABORATI")
	private long numeroRecordElaborati;
	
	@Column(name = "NUMERO_RECORD_NUOVI")
	private long numeroRecordNuovi;
	
	@Column(name = "TIPO_AUTOCERTIFICAZIONE", length = 1)
	private String tipoAutocertificazione;
	
	@Column(name = "USER_ID", length = 256)
	private String userId;
	
	@Column(name = "UTENTE_TOCKEN", length = 256)
	private String utenteTocken;

	@Column(name = "XML_DOCUMENT", nullable = true)
	private String xmlDocument;
	
	@Column(name = "STATO_ELABORAZIONE", nullable = false)
	private long statoElaborazione;
	
	@Column(name = "PROTOCOLLO", nullable = true, length = 18)
	private String protocollo;

	@Column(name = "TIPOLOGIA_RICEVUTA", nullable = false, length = 32)
	private String tipologiaRicevuta;
	
	@Transient
	private List<IAutocertificazioneTmp> listAutocertificazioneTmp;
	
	@Transient
	private List<ICertificazioneTmp> listCertificazioneTmp;
	
	public RicevutaDownloadSogei() {}
	
	
	/** {@inheritDoc} */
	@Override
	public String getDataOraLimite() {
		return dataOraLimite;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setDataOraLimite(final String dataOraLimite) {
		this.dataOraLimite = dataOraLimite;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getDiagnostico() {
		return diagnostico;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setDiagnostico(final String diagnostico) {
		this.diagnostico = diagnostico;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getEsito() {
		return esito;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setEsito(final String esito) {
		this.esito = esito;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getCodAsl() {
		return codAsl;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setCodAsl(final String idUniAsl) {
		codAsl = idUniAsl;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getCodRegione() {
		return codRegione;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setCodRegione(final String idUniRegione) {
		codRegione = idUniRegione;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public long getNumeroRecordTrovati() {
		return numeroRecordTrovati;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setNumeroRecordTrovati(final long numeroRecordTrovati) {
		this.numeroRecordTrovati = numeroRecordTrovati;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public long getNumeroRecordElaborati() {
		return numeroRecordElaborati;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setNumeroRecordElaborati(final long numeroRecordElaborati) {
		this.numeroRecordElaborati = numeroRecordElaborati;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public long getNumeroRecordNuovi() {
		return numeroRecordNuovi;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setNumeroRecordNuovi(final long numeroRecordNuovi) {
		this.numeroRecordNuovi = numeroRecordNuovi;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getTipoAutocertificazione() {
		return tipoAutocertificazione;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setTipoAutocertificazione(final String tipoAutocertificazione) {
		this.tipoAutocertificazione = tipoAutocertificazione;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getUserId() {
		return userId;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setUserId(final String userId) {
		this.userId = userId;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getUtenteTocken() {
		return utenteTocken;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setUtenteTocken(final String utenteTocken) {
		this.utenteTocken = utenteTocken;
	}


	/** {@inheritDoc} */
	@Override
	public String getXmlDocument() {
		return xmlDocument;
	}



	/** {@inheritDoc} */
	@Override
	public void setXmlDocument(final String xmlDocument) {
		this.xmlDocument = xmlDocument;
	}


	/** {@inheritDoc} */
	@Override
	public long getStatoElaborazione() {
		return statoElaborazione;
	}


	/** {@inheritDoc} */
	@Override
	public void setStatoElaborazione(final long statoElaborazione) {
		this.statoElaborazione = statoElaborazione;
	}


	/** {@inheritDoc} */
	@Override
	public String getProtocollo() {
		return protocollo;
	}


	/** {@inheritDoc} */
	@Override
	public void setProtocollo(final String protocollo) {
		this.protocollo = protocollo;
	}


	/** {@inheritDoc} */
	@Override
	public String getTipologiaRicevuta() {
		return tipologiaRicevuta;
	}



	/** {@inheritDoc} */
	@Override
	public void setTipologiaRicevuta(final String tipologiaRicevuta) {
		this.tipologiaRicevuta = tipologiaRicevuta;
	}
	

	/** {@inheritDoc} */
	@Override @Transient
	public List<IAutocertificazioneTmp> getListAutocertificazioneTmp() {
		if(listAutocertificazioneTmp== null){
			listAutocertificazioneTmp = new ArrayList<IAutocertificazioneTmp>();
		}
		return listAutocertificazioneTmp;
	}

	/** {@inheritDoc} */
	@Override @Transient
	public List<ICertificazioneTmp> getListCertificazioneTmp() {
		if(listCertificazioneTmp== null){
			listCertificazioneTmp = new ArrayList<ICertificazioneTmp>();
		}
		return listCertificazioneTmp;
	}
	
	/** {@inheritDoc} */
	@Override @Transient
	public void addAutocertificazioneTmp(final IAutocertificazioneTmp autocertificazioneTmp) {
		this.getListAutocertificazioneTmp().add(autocertificazioneTmp);
	}

	/** {@inheritDoc} */
	@Override @Transient
	public void addCertificazioneTmp(final ICertificazioneTmp certificazioneTmp) {
		this.getListCertificazioneTmp().add(certificazioneTmp);
	}

	/** {@inheritDoc} */
	@Override @Transient
	public void setNumeroRecordElaborati(){
		setNumeroRecordElaborati(this.getListAutocertificazioneTmp().size());
	}

	/** {@inheritDoc} */
	@Override @Transient
	public void setNumeroRecordElaboratiCert(){
		setNumeroRecordElaborati(this.getListCertificazioneTmp().size());
	}

}
