package it.toscana.regione.wsods.entity.jpa.api;

public interface ITracciaObsolescenzaIdUni extends IDbEntity {

	public final static String RUOLO_BENEFICIARIO = "BENEFICIARIO";
	public final static String RUOLO_DICHIARANTE = "DICHIARANTE";
	public final static String RUOLO_TITOLARE = "TITOLARE";
	
	
	long getFkIdUniDaAggiornare();

	void setFkIdUniDaAggiornare(final long fkIdUniDaAggiornare);

	long getFkEsenzioniFasce();

	void setFkEsenzioniFasce(final long fkEsenzioniFasce);

	String getRuolo();

	void setRuolo(final String ruolo);

}