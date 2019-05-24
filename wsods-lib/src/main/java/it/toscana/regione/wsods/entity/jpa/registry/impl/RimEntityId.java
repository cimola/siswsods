//package it.toscana.regione.wsods.entity.jpa.registry.impl;
//
//import it.toscana.regione.wsods.entity.jpa.registry.abs.RegistryEntity;
//import it.toscana.regione.wsods.entity.jpa.registry.api.IRimEntityId;
//
//import java.sql.Timestamp;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "RIM_ENTITY_ID")
//public class RimEntityId extends RegistryEntity implements IRimEntityId {
//	
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -2708031725781048711L;
//
//	public RimEntityId() { super(); }
//	
//	
//	@Id
//	@Column(name = "ID", insertable=false, updatable = false, unique = true, nullable = false)
//	private long id; 
//
//	@Column(name = "INTERNALID", insertable=false, updatable = false, nullable = false)
//	private String internalId;
//	
//	@Column(name = "ROOT", insertable=false, updatable = false, nullable = false)
//	private String root;
//	
//	@Column(name = "EXTENSION", insertable=false, updatable = false, nullable = false)
//	private String extension;
//	
//	@Column(name = "DATAINIZIO", insertable=false, updatable = false, nullable = false)
//	private Timestamp dataInizio;
//	
//	@Column(name = "DATAFINE", insertable=false, updatable = false, nullable = false)
//	private Timestamp dataFine;
//
//
//	//SELECT INTERNALID FROM REGRT.RIM_ENTITY_ID WHERE ROOT='DBSIS_ID' AND EXTENSION='REG999999200400002227827' AND DATAFINE> '2014-03-07'
//
//
//	@Override
//	public long getId() {
//		return id;
//	}
//
//	
//
//	@Override
//	public void setId(long id) {
//		this.id = id;
//	}
//
//	
//
//	@Override
//	public String getInternalId() {
//		return internalId;
//	}
//
//	
//
//	@Override
//	public void setInternalId(String internalId) {
//		this.internalId = internalId;
//	}
//
//	
//
//	@Override
//	public String getRoot() {
//		return root;
//	}
//
//	
//
//	@Override
//	public void setRoot(String root) {
//		this.root = root;
//	}
//
//	
//
//	@Override
//	public String getExtension() {
//		return extension;
//	}
//
//	
//
//	@Override
//	public void setExtension(String extension) {
//		this.extension = extension;
//	}
//
//	
//
//	@Override
//	public Timestamp getDataInizio() {
//		return dataInizio;
//	}
//
//	
//
//	@Override
//	public void setDataInizio(Timestamp dataInizio) {
//		this.dataInizio = dataInizio;
//	}
//
//	
//
//	@Override
//	public Timestamp getDataFine() {
//		return dataFine;
//	}
//
//	
//
//	@Override
//	public void setDataFine(Timestamp dataFine) {
//		this.dataFine = dataFine;
//	}
//	
//	
//	
//}
