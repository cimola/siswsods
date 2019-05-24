//package it.toscana.regione.wsods.entity.jpa.registry.impl;
//
//import it.toscana.regione.wsods.costanti.Varie;
//import it.toscana.regione.wsods.entity.bean.api.IAutocertificazioneReg;
//import it.toscana.regione.wsods.entity.jpa.registry.api.IEsenzione;
//import it.toscana.regione.wsods.exception.ConvertitoreException;
//import it.toscana.regione.wsods.lib.Convertitore;
//
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//
//@Entity
//@Table(name = "ESENZIONE")
//public class Esenzione implements IEsenzione {
//	
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 4777710557974324014L;
//
//	public Esenzione() { super(); }
//
//	public static final String CLASS_CODE = "FAER";
//	
//	public Esenzione(final String nce, final IAutocertificazioneReg src) throws ConvertitoreException {
//		super();
//		this.classCode = CLASS_CODE;
//		this.protocollo = src.getProtocollo();
//		this.autocertificatoreEntityId = src.getInternalIdDichiarante();
//		this.titolareEntityId = src.getInternalIdTitolare();
//		this.assistitoEntityId = src.getInternalIdAssistito();
//		this.codEsenzione = src.getTipoEsenzione();
//		this.nce = nce;
//		if(src.getNota()==null){
//			this.nota = null;
//		} else if(src.getNota().length()<=255){
//			this.nota = src.getNota().trim();
//		} else {
//			this.nota = src.getNota().trim().substring(0,255);
//		}
//		this.dataInizio = src.getDataInizio()==null?null:new Date(Convertitore.convertiTimeInizioGiornata(src.getDataInizio()));
//		this.dataFine = src.getDataFine()==null?null:new Date(Convertitore.convertiTimeFineGiornata(src.getDataFine(),Varie.TIME_SECONDO));;
//		this.dataFineOld = src.getDataFineOld()==null?null:new Date(Convertitore.convertiTimeFineGiornata(src.getDataFineOld(),Varie.TIME_SECONDO));;
//	}
//
//	@Id
//    @GeneratedValue(generator="seqGenEsenzione", strategy = GenerationType.AUTO) 
//    //@SequenceGenerator(name="seqGenEsenzione", sequenceName="SEQ_ESENZIONE", allocationSize=1) //SI e' messo sull'orm per consentirne la configurabilita' per ambiente.
//	@Column(name = "ID", insertable=true, updatable = false, unique = true, nullable = false)
//  	private long id;
//
//	@Column(name = "CLASSCODE", insertable=true, updatable = false, nullable = false)
//  	private String classCode;
//
//	@Column(name = "PROTOCOLLO", insertable=true, updatable = false)
//  	private String protocollo;
//
//	@Column(name = "AUTOCERTIFICATORE_ENTITYID", insertable=true, updatable = false)
//  	private String autocertificatoreEntityId;
//
//	@Column(name = "TITOLARE_ENTITYID", insertable=true, updatable = false)
//  	private String titolareEntityId;
//
//	@Column(name = "ASSISTITO_ENTITYID", insertable=true, updatable = false, nullable = false)
//  	private String assistitoEntityId;
//
//	@Column(name = "CODESENZIONE", insertable=true, updatable = false, nullable = false)
//  	private String codEsenzione;
//
//	@Column(name = "NCE", insertable=true, updatable = false)
//  	private String nce;
//
//	@Column(name = "NOTA", insertable=true, updatable = false)
//  	private String nota;
//
//	@Column(name = "DATAINIZIO", insertable=true, updatable = false, nullable = false)
//  	private Date dataInizio;
//
//	@Column(name = "DATAFINE", insertable=true, updatable = false)
//  	private Date dataFine;
//
//	@Column(name = "DATAFINE_OLD", insertable=true, updatable = false)
//  	private Date dataFineOld;
//
//	
//	@Override
//	public long getId() {
//		return id;
//	}
//
//	
//	@Override
//	public void setId(long id) {
//		this.id = id;
//	}
//
//	
//	@Override
//	public String getClassCode() {
//		return classCode;
//	}
//
//	
//	@Override
//	public void setClassCode(String classCode) {
//		this.classCode = classCode;
//	}
//
//	
//	@Override
//	public String getProtocollo() {
//		return protocollo;
//	}
//
//	
//	@Override
//	public void setProtocollo(String protocollo) {
//		this.protocollo = protocollo;
//	}
//
//	
//	@Override
//	public String getAutocertificatoreEntityId() {
//		return autocertificatoreEntityId;
//	}
//
//	
//	@Override
//	public void setAutocertificatoreEntityId(String autocertificatoreEntityId) {
//		this.autocertificatoreEntityId = autocertificatoreEntityId;
//	}
//
//	
//	@Override
//	public String getTitolareEntityId() {
//		return titolareEntityId;
//	}
//
//	
//	@Override
//	public void setTitolareEntityId(String titolareEntityId) {
//		this.titolareEntityId = titolareEntityId;
//	}
//
//	
//	@Override
//	public String getAssistitoEntityId() {
//		return assistitoEntityId;
//	}
//
//	
//	@Override
//	public void setAssistitoEntityId(String assistitoEntityId) {
//		this.assistitoEntityId = assistitoEntityId;
//	}
//
//	
//	@Override
//	public String getCodEsenzione() {
//		return codEsenzione;
//	}
//
//	
//	@Override
//	public void setCodEsenzione(String codEsenzione) {
//		this.codEsenzione = codEsenzione;
//	}
//
//	
//	@Override
//	public String getNce() {
//		return nce;
//	}
//
//	
//	@Override
//	public void setNce(String nce) {
//		this.nce = nce;
//	}
//
//	
//	@Override
//	public String getNota() {
//		return nota;
//	}
//
//	
//	@Override
//	public void setNota(String nota) {
//		this.nota = nota;
//	}
//
//	
//	@Override
//	public Date getDataInizio() {
//		return dataInizio;
//	}
//
//	
//	@Override
//	public void setDataInizio(Date dataInizio) {
//		this.dataInizio = dataInizio;
//	}
//
//	
//	@Override
//	public Date getDataFine() {
//		return dataFine;
//	}
//
//	
//	@Override
//	public void setDataFine(Date dataFine) {
//		this.dataFine = dataFine;
//	}
//
//	
//	@Override
//	public Date getDataFineOld() {
//		return dataFineOld;
//	}
//
//	
//	@Override
//	public void setDataFineOld(Date dataFineOld) {
//		this.dataFineOld = dataFineOld;
//	}
//
//	
//}
