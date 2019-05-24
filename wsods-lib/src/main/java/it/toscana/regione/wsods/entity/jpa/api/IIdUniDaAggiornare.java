package it.toscana.regione.wsods.entity.jpa.api;

import java.sql.Timestamp;

public interface IIdUniDaAggiornare  extends IDbEntity {

	public final static long STATO_UTILIZZO_MAI_USATO = 0L;
	public final static long STATO_UTILIZZO_RICERCATO_E_TROVATO = 1L;
	public final static long STATO_UTILIZZO_RICERCATO_MA_NON_USATO = 2L;
		

	
	Timestamp getDataUltimoAggiornamento();

	void setDataUltimoAggiornamento(final Timestamp dataUltimoAggiornamento);

	String getSlave();

	void setSlave(final String slave);

	String getMaster();

	void setMaster(final String master);
	
	long getStatoUtilizzo();
	
	void setStatoUtilizzo(final long statoUtilizzo);

}