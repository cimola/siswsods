/** */
package it.toscana.regione.wsods.ejb.rielaboratore.api;

import it.toscana.regione.wsods.exception.WsOdsRuntimeException;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


/**
 * @author cciurli
 *
 */
@Local
public interface IManagerRielaboratore {
	
	public final static String 	EJB_REF	= "managerRielaboratore";
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void rielaborarecord(final long numeroTimer, final long discriminante) throws WsOdsRuntimeException;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void rielaborarecordCert(final long numeroTimer, final long discriminante) throws WsOdsRuntimeException;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int recuperaRecord() throws WsOdsRuntimeException;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int recuperaRecordCert() throws WsOdsRuntimeException;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int recuperaRecordGianov4() throws WsOdsRuntimeException;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int recuperaRecordCertGianov4() throws WsOdsRuntimeException;
	
}
