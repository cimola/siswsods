/** */
package it.toscana.regione.wsods.entity.jpa.abs;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import it.toscana.regione.wsods.entity.abs.Entity;
import it.toscana.regione.wsods.entity.jpa.api.IDbEntity;



/**
 * @author cciurli
 *
 */
@MappedSuperclass
public abstract class DbEntity  extends Entity implements IDbEntity {

	/** fild serialVersionUID {@like long}*/
	private static final long	serialVersionUID	= 2803675469011612308L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", insertable=false, updatable = false, unique = true, nullable = false)
	private long id;

	@Column(name = "DATA_AGGIORNAMENTO", insertable = false, updatable = true, nullable = false)
	private Timestamp dataAggiornamento;

	@Column(name = "DATA_INSERIMENTO", insertable = false, updatable = false, nullable = false)
	private Timestamp dataInserimento;
	
	
	public DbEntity() { }

	/** {@inheritDoc} */
	@Override
	public long getId() {
		return id;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setId(final long id) {
		this.id = id;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public Timestamp getDataAggiornamento() {
		return dataAggiornamento;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setDataAggiornamento(final Timestamp dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public Timestamp getDataInserimento() {
		return dataInserimento;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setDataInserimento(final Timestamp dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
}
