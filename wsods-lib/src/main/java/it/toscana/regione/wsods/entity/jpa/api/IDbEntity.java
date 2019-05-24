/** */
package it.toscana.regione.wsods.entity.jpa.api;

import java.sql.Timestamp;

import it.toscana.regione.wsods.entity.api.IEntity;


/**
 * @author cciurli
 *
 */
public interface IDbEntity extends IEntity {


	long getId();

	void setId(final long id);
	
	Timestamp getDataAggiornamento();

	void setDataAggiornamento(Timestamp dataAggiornamento);	
	
	Timestamp getDataInserimento();

	void setDataInserimento(Timestamp dataInserimento);

}

