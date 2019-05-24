/** */
package it.toscana.regione.wsods.entity.jpa.api;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author cciurli
 *
 */
public interface IEsenzioniFasce extends IDbEntity {

	public static final long FLAG_VALUTAZIONE_DA_VALUTARE = 0L;
	public static final long FLAG_VALUTAZIONE_VALUTATO_UN_ELEMENTO_DELLA_SUA_STORIA = 1L;
	public static final long FLAG_VALUTAZIONE_VALUTATO_E_DA_RETTIFICARE_IN_SEGUITO = 2L;
	public static final long FLAG_VALUTAZIONE_VALUTATO_E_DA_RETTIFICARE_INMEDIATAMENTE = 3L;
	public static final long FLAG_VALUTAZIONE_RIVALUTATO = 4L;
	public static final long FLAG_VALUTAZIONE_DA_NON_VALUTARE = 9L;
	
	public static final String FLAG_TIPOLOGIA_CERTIFICAZIONE = "C";

	Date getDataFine();


	void setDataFine(final Date dataFine);


	Date getDataFineErogazione();


	void setDataFineErogazione(final Date dataFineErogazione);


	Date getDataFineOld();


	void setDataFineOld(final Date dataFineOld);


	Date getDataFornitura();


	void setDataFornitura(final Date dataFornitura);


	Date getDataInizio();


	void setDataInizio(final Date dataInizio);


	Date getDataInizioErogazione();


	void setDataInizioErogazione(final Date dataInizioErogazione);

	
	Timestamp getDataOrdinamento();

	
	void setDataOrdinamento(final Timestamp dataLimite);
	
	
	Date getDataNascita();


	void setDataNascita(final Date dataNascita);


	String getFlagTipologia();


	void setFlagTipologia(final String flagTipologia);


	String getIdUniDichiarante();


	void setIdUniDichiarante(final String idUniDichiarante);


	String getIdUniTitolare();


	void setIdUniTitolare(final String idUniTitolare);


	String getIdUniversaleAssistito();


	void setIdUniversaleAssistito(final String idUniversaleAssistito);


	String getNota();


	void setNota(final String nota);


	String getProtocollo();


	void setProtocollo(final String protocollo);


	String getSesso();


	void setSesso(final String sesso);


	String getTipoEsenzione();


	void setTipoEsenzione(final String tipoEsenzione);


	String getTitolo();


	void setTitolo(final String titolo);

	
	public String getSorgente();
	
	void setSorgente(final String sorgente);

	
	
	
	IDownloadAutocertificazione getDownloadInfo();
	
	void setDownloadInfo(final IDownloadAutocertificazione downloadInfo);
	
	IUploadAutocertificazione getUploadInfo();
	
	void setUploadInfo(final IUploadAutocertificazione uploadInfo);
	
	boolean isArricchita();
	
	
	Timestamp getDataUltimaValutazione();
	void setDataUltimaValutazione(final Timestamp dataUltimaValutazione);
	Date getDataProssimaValutazione();
	void setDataProssimaValutazione(final Date dataProssimaValutazione);
	long getFlagValutazione();
	void setFlagValutazione(final long flagValutazione);
	
}
