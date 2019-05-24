package it.toscana.regione.wsods.entity.jpa.abs;

import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneEtl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * @author cciurli
 *
 */
@MappedSuperclass
public class AutocertificazioneEtl extends DbEntity implements IAutocertificazioneEtl  {


	private static final long serialVersionUID = 8262397034449700166L;

	@Column(name="FK_ESENZIONE_PROROGATA", nullable=false)
	private long fkEsenzioneProrogata;

	@Column(name="STATO_ELABORAZIONE", nullable=false)
	private long statoElaborazione;
	
	@Column(name="CODICE_ESENZIONE", nullable=false, length=3)
	private String codiceEsenzione;


	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INVIO")
	private Date dataInvio;


	@Column(name="ID_UNI_BENEFICIARIO", nullable=false, length=24)
	private String idUniBeneficiario;

	@Column(name="ID_UNI_DICHIARANTE", nullable=false, length=24)
	private String idUniDichiarante;

	@Column(name="ID_UNI_TITOLARE", nullable=false, length=24)
	private String idUniTitolare;
	

	@Column(nullable=false, length=1)
	private String titolo;

	@Column(name="NUMERO_ELABORAZIONI", nullable=false)
	private long numeroElaborazioni;



	public AutocertificazioneEtl() {
	}

	@Override
	@Transient
	public void incrementaElaborazioni(){
		this.numeroElaborazioni++;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.ii#getCodiceEsenzione()
	 */
	@Override
	public String getCodiceEsenzione() {
		return this.codiceEsenzione;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.ii#setCodiceEsenzione(java.lang.String)
	 */
	@Override
	public void setCodiceEsenzione(String codiceEsenzione) {
		this.codiceEsenzione = codiceEsenzione;
	}


	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.ii#getDataInvio()
	 */
	@Override
	public Date getDataInvio() {
		return this.dataInvio;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.ii#setDataInvio(java.util.Date)
	 */
	@Override
	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.ii#getFkEsenzioneProrogata()
	 */
	@Override
	public long getFkEsenzioneProrogata() {
		return this.fkEsenzioneProrogata;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.ii#setFkEsenzioneProrogata(long)
	 */
	@Override
	public void setFkEsenzioneProrogata(long fkEsenzioneProrogata) {
		this.fkEsenzioneProrogata = fkEsenzioneProrogata;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.ii#getIdUniBeneficiario()
	 */
	@Override
	public String getIdUniBeneficiario() {
		return this.idUniBeneficiario;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.ii#setIdUniBeneficiario(java.lang.String)
	 */
	@Override
	public void setIdUniBeneficiario(String idUniBeneficiario) {
		this.idUniBeneficiario = idUniBeneficiario;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.ii#getIdUniDichiarante()
	 */
	@Override
	public String getIdUniDichiarante() {
		return this.idUniDichiarante;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.ii#setIdUniDichiarante(java.lang.String)
	 */
	@Override
	public void setIdUniDichiarante(String idUniDichiarante) {
		this.idUniDichiarante = idUniDichiarante;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.ii#getIdUniTitolare()
	 */
	@Override
	public String getIdUniTitolare() {
		return this.idUniTitolare;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.ii#setIdUniTitolare(java.lang.String)
	 */
	@Override
	public void setIdUniTitolare(String idUniTitolare) {
		this.idUniTitolare = idUniTitolare;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.ii#getNumeroElaborazioni()
	 */
	@Override
	public long getNumeroElaborazioni() {
		return this.numeroElaborazioni;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.ii#setNumeroElaborazioni(long)
	 */
	@Override
	public void setNumeroElaborazioni(long numeroElaborazioni) {
		this.numeroElaborazioni = numeroElaborazioni;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.ii#getStatoElaborazione()
	 */
	@Override
	public long getStatoElaborazione() {
		return this.statoElaborazione;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.ii#setStatoElaborazione(long)
	 */
	@Override
	public void setStatoElaborazione(long statoElaborazione) {
		this.statoElaborazione = statoElaborazione;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.ii#getTitolo()
	 */
	@Override
	public String getTitolo() {
		return this.titolo;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.abs.ii#setTitolo(java.lang.String)
	 */
	@Override
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

}