/** */
package it.toscana.regione.wsods.entity.jpa.api;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author vmaltese
 *
 */
public interface ICertificazioneScartate extends IDbEntity {
	
	long getOldId();
	void setOldId(long oldId);
	
	Timestamp getOldDataAggiornamento();
	void setOldDataAggiornamento(Timestamp oldDataAggiornamento);
	
	Timestamp getOldDataInserimento();
	void setOldDataInserimento(Timestamp oldDataInserimento);
	
	String getAnnoEsenzione();
	void setAnnoEsenzione(String annoEsenzione);
	
	String getCfSogEsente();
	void setCfSogEsente(String cfSogEsente);
	
	String getCodiceEsenzione();
	void setCodiceEsenzione(String codiceEsenzione);
	
	Date getDataFineValid();
	void setDataFineValid(Date dataFineValid);
	
	Date getDataFineValidOld();
	void setDataFineValidOld(Date dataFineValidold);
	
	Date getDataInizioValid();
	void setDataInizioValid(Date dataInizioValid);
	
	Timestamp getDataOrdinamento();
	void setDataOrdinamento(final Timestamp dataOrdinamento);
	
	long getFkRicevutaDownloadSogei();
	void setFkRicevutaDownloadSogei(long fkRicevutaDownloadSogei);
	
	long getNumeroElaborazioni();
	void setNumeroElaborazioni(long numeroElaborazioni);
	
	long getRicevutaIndex();
	void setRicevutaIndex(long ricevutaIndex);
	
	public String getSorgente();
	void setSorgente(final String sorgente);
	
	long getRielabora();
	void setRielabora(final long rielabora);
}
