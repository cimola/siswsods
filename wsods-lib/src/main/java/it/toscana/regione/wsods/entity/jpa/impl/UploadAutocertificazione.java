package it.toscana.regione.wsods.entity.jpa.impl;

import it.toscana.regione.wsods.entity.jpa.abs.DbEntity;
import it.toscana.regione.wsods.entity.jpa.api.IUploadAutocertificazione;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the UPLOAD_AUTOCERTIFICAZIONE database table.
 * 
 */
@Entity
@Table(name = "UPLOAD_AUTOCERTIFICAZIONE")
//@NamedQuery(name="UploadAutocertificazione.findAll", query="SELECT u FROM UploadAutocertificazione u")
public class UploadAutocertificazione extends DbEntity implements IUploadAutocertificazione {
	
	/** fild serialVersionUID {@like long}*/
	private static final long serialVersionUID = 3927638330612183652L;
	
	
	@Column(nullable = false, length = 9)
	private String attivita;
	
	@Column(name = "COMUNE_CENTRO_IMPIEGO", length = 100)
	private String comuneCentroImpiego;
	
	@Column(name = "DATA_AGG_REC")
	private Timestamp dataAggRec;
	
	@Column(name = "DATA_INS_REC")
	private Timestamp dataInsRec;
	
	@Column(name = "DIAGNOSTICO",length = 512)
	private String diagnostico;
	
	@Column(name = "ESITO", length = 4)
	private String esito;
	
	@Column(name = "FK_ESENZIONI_FASCE", nullable = false)
	private long fkEsenzioniFasce;
	
	@Column(name = "STATO_ESENZIONE", nullable = false, length = 10)
	private String statoEsenzione;
	
	@Column(name = "USER_ID", length = 256)
	private String userId;
	
	@Column(name = "UTENTE_TOCKEN", length = 256)
	private String utenteTocken;
	
	
	public UploadAutocertificazione() {}
	
	
	
	/** {@inheritDoc} */
	@Override
	public String getAttivita() {
		return attivita;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setAttivita(final String attivita) {
		this.attivita = attivita;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getComuneCentroImpiego() {
		return comuneCentroImpiego;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setComuneCentroImpiego(final String comuneCentroImpiego) {
		this.comuneCentroImpiego = comuneCentroImpiego;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public Timestamp getDataAggRec() {
		return dataAggRec;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setDataAggRec(final Timestamp dataAggRec) {
		this.dataAggRec = dataAggRec;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public Timestamp getDataInsRec() {
		return dataInsRec;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setDataInsRec(final Timestamp dataInsRec) {
		this.dataInsRec = dataInsRec;
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
	public long getFkEsenzioniFasce() {
		return fkEsenzioniFasce;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setFkEsenzioniFasce(final long fkEsenzioniFasce) {
		this.fkEsenzioniFasce = fkEsenzioniFasce;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getStatoEsenzione() {
		return statoEsenzione;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setStatoEsenzione(final String statoEsenzione) {
		this.statoEsenzione = statoEsenzione;
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
	
}
