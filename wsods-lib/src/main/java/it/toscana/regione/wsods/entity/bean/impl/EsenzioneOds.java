package it.toscana.regione.wsods.entity.bean.impl;

import it.toscana.regione.wsods.entity.abs.Entity;
import it.toscana.regione.wsods.entity.bean.api.IEsenzioneOds;
import it.toscana.regione.wsods.entity.jpa.api.IEsenzioniFasce;

import java.sql.Timestamp;
import java.util.Date;

public class EsenzioneOds extends Entity implements IEsenzioneOds {
	
	/** fild serialVersionUID {@like long}*/
	private static final long serialVersionUID = -8196695467855425593L;
	
	private long id;
	private Date dataFine;
	private Date dataFineOld;
	private Date dataInizio;
	private Timestamp dataOrdinamento;
	private Date dataFornitura;
	private String flagTipologia;
	private String idUniDichiarante;
	private String idUniTitolare;
	private String idUniversaleAssistito;
	private String nota;
	private String protocollo;
	private String tipoEsenzione;
	private String titolo;
	private String sorgente;
	private String comuneCentroImpiego;
	private String statoEsenzione;

	public EsenzioneOds() {
		super();
	}
	
	public EsenzioneOds(final IEsenzioniFasce esenzione) {
		super();
		this.id = esenzione.getId();
		this.dataFine = esenzione.getDataFine();
		this.dataFineOld = esenzione.getDataFineOld();
		this.dataInizio = esenzione.getDataInizio();
		this.dataOrdinamento = esenzione.getDataOrdinamento();
		this.dataFornitura = esenzione.getDataFornitura();
		this.flagTipologia = esenzione.getFlagTipologia();
		this.idUniDichiarante = esenzione.getIdUniDichiarante();
		this.idUniTitolare = esenzione.getIdUniTitolare();
		this.idUniversaleAssistito = esenzione.getIdUniversaleAssistito();
		this.nota = esenzione.getNota();
		this.protocollo = esenzione.getProtocollo();
		this.tipoEsenzione = esenzione.getTipoEsenzione();
		this.titolo = esenzione.getTitolo();
		this.sorgente = esenzione.getSorgente();

	}
	
	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public Date getDataFine() {
		return dataFine;
	}

	@Override
	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	@Override
	public Date getDataFineOld() {
		return dataFineOld;
	}

	@Override
	public void setDataFineOld(Date dataFineOld) {
		this.dataFineOld = dataFineOld;
	}

	@Override
	public Date getDataInizio() {
		return dataInizio;
	}

	@Override
	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	@Override
	public Timestamp getDataOrdinamento() {
		return dataOrdinamento;
	}

	@Override
	public void setDataOrdinamento(Timestamp dataOrdinamento) {
		this.dataOrdinamento = dataOrdinamento;
	}

	@Override
	public Date getDataFornitura() {
		return dataFornitura;
	}

	@Override
	public void setDataFornitura(Date dataFornitura) {
		this.dataFornitura = dataFornitura;
	}

	@Override
	public String getFlagTipologia() {
		return flagTipologia;
	}

	@Override
	public void setFlagTipologia(String flagTipologia) {
		this.flagTipologia = flagTipologia;
	}

	@Override
	public String getIdUniDichiarante() {
		return idUniDichiarante;
	}

	@Override
	public void setIdUniDichiarante(String idUniDichiarante) {
		this.idUniDichiarante = idUniDichiarante;
	}

	@Override
	public String getIdUniTitolare() {
		return idUniTitolare;
	}

	@Override
	public void setIdUniTitolare(String idUniTitolare) {
		this.idUniTitolare = idUniTitolare;
	}

	@Override
	public String getIdUniversaleAssistito() {
		return idUniversaleAssistito;
	}

	@Override
	public void setIdUniversaleAssistito(String idUniversaleAssistito) {
		this.idUniversaleAssistito = idUniversaleAssistito;
	}

	@Override
	public String getNota() {
		return nota;
	}

	@Override
	public void setNota(String nota) {
		this.nota = nota;
	}

	@Override
	public String getProtocollo() {
		return protocollo;
	}

	@Override
	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	@Override
	public String getTipoEsenzione() {
		return tipoEsenzione;
	}

	@Override
	public void setTipoEsenzione(String tipoEsenzione) {
		this.tipoEsenzione = tipoEsenzione;
	}

	@Override
	public String getTitolo() {
		return titolo;
	}

	@Override
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	@Override
	public String getSorgente() {
		return sorgente;
	}

	@Override
	public void setSorgente(String sorgente) {
		this.sorgente = sorgente;
	}


	@Override
	public String getComuneCentroImpiego() {
		return comuneCentroImpiego;
	}


	@Override
	public void setComuneCentroImpiego(String comuneCentroImpiego) {
		this.comuneCentroImpiego = comuneCentroImpiego;
	}


	@Override
	public String getStatoEsenzione() {
		return statoEsenzione;
	}


	@Override
	public void setStatoEsenzione(String statoEsenzione) {
		this.statoEsenzione = statoEsenzione;
	}
	
}
