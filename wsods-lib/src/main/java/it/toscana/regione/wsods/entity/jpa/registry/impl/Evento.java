//package it.toscana.regione.wsods.entity.jpa.registry.impl;
//
//import it.toscana.regione.wsods.entity.jpa.registry.api.IEvento;
//
//import java.sql.Timestamp;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//
//@Entity
//@Table(name = "EVENTO")
//public class Evento implements IEvento  {
//	
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 477547868998094014L;
//
//	public Evento() { super(); }
//
//	
//	public Evento(final String idEvento, final String mittente, final long idEsenzione) {
//		super();
//		this.idEvento = idEvento;
//		this.mittenteEvento = mittente;
//		this.idEsenzione = idEsenzione;
//		this.timestampEvento = new Timestamp(System.currentTimeMillis());
//		this.timestampAnonimizzazione = new Timestamp(System.currentTimeMillis());
//	}
//	
//	//ID_EVENTO					VARCHAR2		NO		<null>	255
//	//MITTENTE_EVENTO			VARCHAR2		NO		<null>	255
//	//TIMESTAMP_EVENTO			TIMESTAMP(9)	NO		9		11
//	//TIMESTAMP_ANONIMIZZAZIONE	TIMESTAMP(9)	YES		9		11
//	//ID_ESENZIONE				NUMBER			YES		0		19
//
//	@Id 
// 	@Column(name = "ID_EVENTO", insertable=true, updatable = false, unique = true, nullable = false, length=255)
//  	private String idEvento;
//
//	@Column(name = "MITTENTE_EVENTO", insertable=true, updatable = false, nullable = false, length=255)
//  	private String mittenteEvento;
//
//	@Column(name = "TIMESTAMP_EVENTO", insertable=true, updatable = false, nullable = true)
//  	private Timestamp timestampEvento;
//
//	@Column(name = "TIMESTAMP_ANONIMIZZAZIONE", insertable=true, updatable = false, nullable = true)
//  	private Timestamp timestampAnonimizzazione;
//	
//	@Column(name = "ID_ESENZIONE", insertable=true, updatable = false, nullable = true)
//  	private long idEsenzione;
//
//	@Override
//	public String getIdEvento() {
//		return idEvento;
//	}
//
//	@Override
//	public void setIdEvento(final String idEvento) {
//		this.idEvento = idEvento;
//	}
//
//	@Override
//	public String getMittenteEvento() {
//		return mittenteEvento;
//	}
//
//	@Override
//	public void setMittenteEvento(final String mittenteEvento) {
//		this.mittenteEvento = mittenteEvento;
//	}
//
//	@Override
//	public long getIdEsenzione() {
//		return idEsenzione;
//	}
//
//	@Override
//	public void setIdEsenzione(final long idEsenzione) {
//		this.idEsenzione = idEsenzione;
//	}
//
//	@Override
//	public Timestamp getTimestampEvento() {
//		return timestampEvento;
//	}
//
//	@Override
//	public void setTimestampEvento(final Timestamp timestampEvento) {
//		this.timestampEvento = timestampEvento;
//	}
//	
//	@Override
//	public Timestamp getTimestampAnonimizzazione() {
//		return timestampAnonimizzazione;
//	}
//
//	@Override
//	public void setTimestampAnonimizzazione(final Timestamp timestampAnonimizzazione) {
//		this.timestampAnonimizzazione = timestampAnonimizzazione;
//	}
//
//	
//}
