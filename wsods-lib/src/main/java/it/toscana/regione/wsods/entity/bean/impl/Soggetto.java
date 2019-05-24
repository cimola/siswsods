/** */
package it.toscana.regione.wsods.entity.bean.impl;

import it.toscana.regione.wsods.entity.abs.Entity;
import it.toscana.regione.wsods.entity.bean.api.ISoggetto;



/**
 * @author cciurli
 *
 */
public class Soggetto extends Entity implements ISoggetto {

	/** fild serialVersionUID {@like long}*/
	private static final long	serialVersionUID	= -7263824401663237982L;

	private String idUni;
	private String codiceFiscale;
	private String cognome;
	private String nome;
	private String comuneDiNascita;
	private long dataDiNascita;
	private String sesso;
	
	
	
	public Soggetto() {
		super();
	}
	
	public Soggetto(final String idUni,final String codiceFiscale,final  String cognome,final  String nome,final  String comuneDiNascita,final  long dataDiNascita,final  String sesso) {
		super();
		this.idUni = idUni;
		this.codiceFiscale = codiceFiscale;
		this.cognome = cognome;
		this.nome = nome;
		this.comuneDiNascita = comuneDiNascita;
		this.dataDiNascita = dataDiNascita;
		this.sesso = sesso;
	}

	/** {@inheritDoc} */ @Override public String getIdUni() { return idUni; }
	/** {@inheritDoc} */ @Override public String getCodiceFiscale() { return codiceFiscale; }
	/** {@inheritDoc} */ @Override public String getCognome() { return cognome; }
	/** {@inheritDoc} */ @Override public String getNome() { return nome; }
	/** {@inheritDoc} */ @Override public String getComuneDiNascita() { return comuneDiNascita; }
	/** {@inheritDoc} */ @Override public long getDataDiNascita() { return dataDiNascita; }
	/** {@inheritDoc} */ @Override public String getSesso() { return sesso; }

	/** {@inheritDoc} */ @Override public void setIdUni(final String idUni) { this.idUni = idUni; }
	/** {@inheritDoc} */ @Override public void setCodiceFiscale(final String codiceFiscale) { this.codiceFiscale = codiceFiscale; }
	/** {@inheritDoc} */ @Override public void setCognome(final String cognome) { this.cognome = cognome; }
	/** {@inheritDoc} */ @Override public void setNome(final String nome) { this.nome = nome; }
	/** {@inheritDoc} */ @Override public void setComuneDiNascita(final String comuneDiNascita) { this.comuneDiNascita = comuneDiNascita; }
	/** {@inheritDoc} */ @Override public void setDataDiNascita(final long dataDiNascita) { this.dataDiNascita = dataDiNascita; }
	/** {@inheritDoc} */ @Override public void setSesso(final String sesso) { this.sesso = sesso; }

	/** {@inheritDoc} */ @Override public boolean equals(Object obj) {
		if(obj != null && obj instanceof Soggetto){
			Soggetto other = (Soggetto)obj;
			return 
					this.idUni!=null && this.idUni.equals(other.idUni)
					&&
					this.codiceFiscale!=null && this.codiceFiscale.equals(other.codiceFiscale)
					&&
					this.cognome!=null && this.cognome.equals(other.cognome)
					&&
					this.nome!=null && this.nome.equals(other.nome)
					&&
					this.comuneDiNascita!=null && this.comuneDiNascita.equals(other.comuneDiNascita)
					&&
					this.dataDiNascita == other.dataDiNascita 
					&&
					this.sesso!=null && this.sesso.equals(other.sesso);
		} else {
			return false;
		}
	}
	
}
