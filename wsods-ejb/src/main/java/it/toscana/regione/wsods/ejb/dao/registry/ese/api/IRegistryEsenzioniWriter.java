//package it.toscana.regione.wsods.ejb.dao.registry.ese.api;
//
//import it.toscana.regione.wsods.entity.bean.api.IAutocertificazioneReg;
//import it.toscana.regione.wsods.exception.WsOdsRuntimeException;
//
//import javax.ejb.Local;
//import javax.ejb.TransactionAttribute;
//import javax.ejb.TransactionAttributeType;
//
//@Local
//public interface IRegistryEsenzioniWriter {
//	
//	public static final String EJB_REF = "registryEsenzioniWriter";
//
//	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//	int writerOnRegistry(final IAutocertificazioneReg esenzione) throws WsOdsRuntimeException;
//	
//}
