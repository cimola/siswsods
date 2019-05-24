//package it.toscana.regione.wsods.entity.bean.impl;
//
//import it.toscana.regione.wsods.entity.bean.api.IAutocertificazioneReg;
//import it.toscana.regione.wsods.entity.jpa.api.IEsenzioniFasce;
//
///**
// * The persistent class for the ESENZIONI_FASCE database table.
// * 
// */
//
//public class AutocertificazioneReg extends  EsenzioneOds implements IAutocertificazioneReg  {
//	
//
//	private static final long serialVersionUID = 8909837289465L;
//
//	private String cfAssistito;
//	
//	private String internalIdDichiarante;
//
//	private String internalIdTitolare;
//
//	private String internalIdAssistito;
//	
//	public AutocertificazioneReg() {super();}
//
//	
//	public AutocertificazioneReg(final String cfAssistito, final IEsenzioniFasce esenzione) {
//		super(esenzione);
//		this.cfAssistito = cfAssistito;
//	}
//
//
//	@Override
//	public String getInternalIdDichiarante() {
//		return internalIdDichiarante;
//	}
//
//	
//	@Override
//	public void setInternalIdDichiarante(String internalIdDichiarante) {
//		this.internalIdDichiarante = internalIdDichiarante;
//	}
//
//	
//	@Override
//	public String getInternalIdTitolare() {
//		return internalIdTitolare;
//	}
//
//	
//	@Override
//	public void setInternalIdTitolare(String internalIdTitolare) {
//		this.internalIdTitolare = internalIdTitolare;
//	}
//
//	
//	@Override
//	public String getInternalIdAssistito() {
//		return internalIdAssistito;
//	}
//
//	
//	@Override
//	public void setInternalIdAssistito(String internalIdAssistito) {
//		this.internalIdAssistito = internalIdAssistito;
//	}
//
//	@Override
//	public String getCfAssistito() {
//		return cfAssistito;
//	}
//
//	
//	@Override
//	public void setCfAssistito(String cfAssistito) {
//		this.cfAssistito = cfAssistito;
//	}
//	
//	
//	
//}
