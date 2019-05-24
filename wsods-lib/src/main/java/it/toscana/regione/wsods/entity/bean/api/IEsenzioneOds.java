package it.toscana.regione.wsods.entity.bean.api;

import java.sql.Timestamp;
import java.util.Date;

import it.toscana.regione.wsods.entity.api.IEntity;


public interface IEsenzioneOds extends IEntity {
	
	long getId();
	
	void setId(long id);
	
	Date getDataFine();
	
	
	void setDataFine(Date dataFine);
	
	
	Date getDataFineOld();
	
	
	void setDataFineOld(Date dataFineOld);
	
	
	Date getDataInizio();
	
	
	void setDataInizio(Date dataInizio);
	
	
	Timestamp getDataOrdinamento();
	
	
	void setDataOrdinamento(Timestamp dataOrdinamento);
	
	Date getDataFornitura();
	
	void setDataFornitura(Date dataFornitura);
	
	String getFlagTipologia();
	
	
	void setFlagTipologia(String flagTipologia);
	
	
	String getIdUniDichiarante();
	
	
	void setIdUniDichiarante(String idUniDichiarante);
	
	
	String getIdUniTitolare();
	
	
	void setIdUniTitolare(String idUniTitolare);
	
	
	String getIdUniversaleAssistito();
	
	
	void setIdUniversaleAssistito(String idUniversaleAssistito);

	
	String getNota();
	
	
	void setNota(String nota);
	
	
	String getProtocollo();
	
	
	void setProtocollo(String protocollo);
	
	
	String getTipoEsenzione();
	
	
	void setTipoEsenzione(String tipoEsenzione);
	
	
	String getTitolo();
	
	
	void setTitolo(String titolo);
	
	
	String getSorgente();
	
	
	void setSorgente(String sorgente);
	
	String getComuneCentroImpiego();
	
	void setComuneCentroImpiego(String comuneCentroDelImpiego);
	
	String getStatoEsenzione();
	
	void setStatoEsenzione(String statoEsenzione);
	
}
