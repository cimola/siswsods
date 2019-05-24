/** */
package it.toscana.regione.wsods.ejb.dao.api;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.RuoloSogettoType;
import it.toscana.regione.wsods.entity.jpa.api.IEsenzioniFasce;
import it.toscana.regione.wsods.exception.DaoException;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


/**
 * @author cciurli
 *
 */
@Local
public interface IEsenzioniFasceDao extends IDao<IEsenzioniFasce> {
	
	final static String EJB_REF = IDao.EJB_REF_ESENZIONI_FASCE;
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	List<IEsenzioniFasce> autocertificazioniAssociate(final String idUni) throws DaoException;
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	List<IEsenzioniFasce> autocertificazioniAppartenenti(final String idUni) throws DaoException;
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	List<IEsenzioniFasce> esenzioniAssociate(final String idUni, final List<RuoloSogettoType> listaRuoli) throws DaoException;
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	boolean notExist(final IEsenzioniFasce entity);
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	boolean existNewRecord(final String idUniversaleAssistito, final Date dataDaValutare);
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	boolean existEsenzioneSuccessiva(final String tipoEsenzione, final String idUniversaleAssistito, final Date dataInizio, final Date dataDaValutare);
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	List<IEsenzioniFasce> storicoAutocertificazioni(final String tipoEsenzione, final String idAssistito, final Date dataInizio);

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	List<IEsenzioniFasce> storicoEsenzioniCERD(final String tipoEsenzione, final String idAssistito/*, final Date dataInizio*/);
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	List<IEsenzioniFasce> esenzioniDaValutare(final int size, final String tipoEsenzione);
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	List<IEsenzioniFasce> esenzioniDaRettificare(final int size, final String tipoEsenzione);
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	List<IEsenzioniFasce> getAutocertificazioniXTipo(final String tipoEsenzione, final String idUniversaleAssistito);

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	void updateDataAggiornamentoSotricoAutocertificazione(final String tipoEsenzione, final String idUniversaleAssistito, final Date dataInizio);
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	List<IEsenzioniFasce> selectFromIdUniBeneficiario(final String idUniSlave);
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	List<IEsenzioniFasce> selectFromIdUniTitolare(final String idUniSlave);
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	List<IEsenzioniFasce> selectFromIdUniDichiarante(final String idUniSlave);
	

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	List<IEsenzioniFasce> selectFromProtocollo(final String protocollo);
//	
//	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
//	String selectTitoloFromSubject(final String idUniBeneficiario, final String idUniTitolare, final String idUniDichiarante);
}
