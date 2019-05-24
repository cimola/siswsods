//package it.toscana.regione.wsods.ejb.dao.registry.anag.impl;
//
//import java.util.Date;
//import java.util.List;
//
//import javax.ejb.Stateless;
//import javax.ejb.TransactionAttribute;
//import javax.ejb.TransactionAttributeType;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//
//import it.toscana.regione.wsods.ejb.dao.registry.anag.api.IRegistryConverter;
//import it.toscana.regione.wsods.exception.WsOdsException;
//import it.toscana.regione.wsods.type.Code;
//
//@Stateless(name=IRegistryConverter.EJB_REF)
//public class RegistryConverter implements IRegistryConverter {
//	
//	
//	
//	public RegistryConverter() {super();}
//	
//	@PersistenceContext(unitName="registry-anag-reader")
//	private EntityManager manager;
//	
//	@Override 
//	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
//	public String getInternalIdFromCode(final String extension, final String root) throws WsOdsException {
//		if(extension == null || root == null) { return null; }
//		final String jpql = "SELECT t.internalId FROM RimEntityId t WHERE root= :root  AND extension = :extension AND dataInizio <= :dataInizio  AND dataFine > :dataFine";
//		final Query query;
//		try {
//			query = manager.createQuery(jpql);
//		}catch(RuntimeException e){
//			throw new WsOdsException(Code.DAO_CREATE_QUERY,"impossiblie creare la query sul registry-anag-reader ["+jpql+"]",e);
//		}
//		query.setParameter("root", root);
//		query.setParameter("extension", extension);
//		query.setParameter("dataInizio", new Date());
//		query.setParameter("dataFine", new Date());
//		
//		final List< ? > rowResults;
//		try {
//			rowResults = query.getResultList();
//		}catch(RuntimeException e){
//			throw new WsOdsException(Code.DAO_EXECUTE_SELECT,"impossiblie eseguire la query sul registry-anag-reader ["+jpql+"]",e);
//		}
//		final String result;
//		if(rowResults == null || rowResults.isEmpty()){
//			result = null;
// 		} else {
// 			final Object rowResult =  rowResults.get(0);
// 			if (rowResult instanceof String){
// 				result = (String)rowResult;
// 			} else {
// 				result = null;
// 			}
// 		} 
//		if(rowResults.size() > 0 ) {
//			//
//		}
//		
//		return result;
//	}
//
//}
