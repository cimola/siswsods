package it.toscana.regione.wsods.entity.jpa.impl;

import it.toscana.regione.wsods.entity.jpa.abs.DbEntity;
import it.toscana.regione.wsods.entity.jpa.api.IWsodsSoapTracking;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;



@Entity
@Table(name = "WSODS_SOAP_TRACKING")
public class WsodsSoapTracking extends DbEntity implements IWsodsSoapTracking {
	

	private static final long serialVersionUID = -1910578309004351379L;

	public WsodsSoapTracking() { super(); }
	
	

	@Column(name = "DATA_REQUEST", insertable = true, updatable = true, nullable = true)
	private Timestamp dataRequest;

	@Column(name = "DATA_RESPONSE", insertable = false, updatable = true, nullable = true)
	private Timestamp dataResponse;
	
	@Column(name = "REQUEST", insertable = true, updatable = true, nullable = true)
	private String request;
	
	@Column(name = "RESPONSE", insertable = false, updatable = true, nullable = true)
	private String response;
	
	@Column(name = "SRC", insertable = true, updatable = false, nullable = false)
	private String src;
	
	@Column(name = "DEST", insertable = true, updatable = false, nullable = false)
	private String dest;
	
	@Column(name = "FK_LINK", insertable = true, updatable = false, nullable = true)
	private long fkLink;
	
	@Column(name = "ESITO", length = 2, insertable = false, updatable = true, nullable = true)
	private String esito;
	
	@Column(name = "STACK_TRACE", insertable = false, updatable = true, nullable = true)
	private String stackTrace;
	

	
	/** {@inheritDoc} */
	@Override
	public Timestamp getDataRequest() {
		return dataRequest;
	}


	
	/** {@inheritDoc} */
	@Override
	public void setDataRequest(Timestamp dataRequest) {
		this.dataRequest = dataRequest;
	}


	
	/** {@inheritDoc} */
	@Override
	public Timestamp getDataResponse() {
		return dataResponse;
	}


	
	/** {@inheritDoc} */
	@Override
	public void setDataResponse(Timestamp dataResponse) {
		this.dataResponse = dataResponse;
	}


	
	/** {@inheritDoc} */
	@Override
	public String getRequest() {
		return request;
	}


	
	/** {@inheritDoc} */
	@Override
	public void setRequest(String request) {
		this.request = request;
	}


	
	/** {@inheritDoc} */
	@Override
	public String getResponse() {
		return response;
	}


	
	/** {@inheritDoc} */
	@Override
	public void setResponse(String response) {
		this.response = response;
	}


	
	/** {@inheritDoc} */
	@Override
	public String getSrc() {
		return src;
	}


	
	/** {@inheritDoc} */
	@Override
	public void setSrc(String src) {
		this.src = src;
	}


	
	/** {@inheritDoc} */
	@Override
	public String getDest() {
		return dest;
	}


	
	/** {@inheritDoc} */
	@Override
	public void setDest(String dest) {
		this.dest = dest;
	}


	
	/** {@inheritDoc} */
	@Override
	public long getFkLink() {
		return fkLink;
	}


	
	/** {@inheritDoc} */
	@Override
	public void setFkLink(long fkLink) {
		this.fkLink = fkLink;
	}


	
	/** {@inheritDoc} */
	@Override
	public String getEsito() {
		return esito;
	}


	
	/** {@inheritDoc} */
	@Override
	public void setEsito(String esito) {
		this.esito = esito;
	}


	
	/** {@inheritDoc} */
	@Override
	public String getStackTrace() {
		return stackTrace;
	}


	
	/** {@inheritDoc} */
	@Override
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
	
	
}
