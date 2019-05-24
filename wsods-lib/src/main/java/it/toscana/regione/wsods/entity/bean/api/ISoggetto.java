/** */
package it.toscana.regione.wsods.entity.bean.api;

import it.toscana.regione.wsods.entity.api.IEntity;


/**
 * @author cciurli
 *
 */
public interface ISoggetto extends IEntity {

	String getIdUni();
	
	String getCodiceFiscale();


	String getCognome();


	String getNome();


	String getComuneDiNascita();


	long getDataDiNascita();


	String getSesso();

	void setIdUni(final String idUni);
	
	void setCodiceFiscale(final String codiceFiscale);


	void setCognome(final String cognome);


	void setNome(final String nome);


	void setComuneDiNascita(final String comuneDiNascita);


	void setDataDiNascita(final long dataDiNascita);


	void setSesso(final String sesso);
	
	boolean equals(final Object obj);

}
