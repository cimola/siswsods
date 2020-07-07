package it.toscana.regione.wsods.ejb.prorogatore.impl;


import javax.ejb.Stateless;

import it.toscana.regione.common.webservicetool.token.api.IConfSenderSoap;
import it.toscana.regione.wsods.ejb.prorogatore.api.ISenderApsUpload;
import it.toscana.regione.wsods.ejb.sender.abs.Sender;
import it.toscana.regione.wsods.ejb.sender.api.ISender;
import it.toscana.regione.wsods.entity.jpa.api.IWsodsSoapTracking;
import it.toscana.regione.wsods.singleton.Conf;



/**
 * @author cciurli
 *
 */
@Stateless(name = ISenderApsUpload.EJB_REF)
public class SenderApsUpload extends Sender implements ISender, ISenderApsUpload {
	
	
	public SenderApsUpload() {super();}

	/** {@inheritDoc} */ @Override public boolean isTrackerEnabled(){ return Conf.isProrogheTrackingIsEnabled(); }
	/** {@inheritDoc} */ @Override public String getSrc() { return IWsodsSoapTracking.SRC_WSODS; }
	/** {@inheritDoc} */ @Override public String getDest() { return IWsodsSoapTracking.DEST_APS_ESE; }
	

//	/** {@inheritDoc} */ @Override protected String getEndpoint() { return Conf.getApsUploadEndpoint(); }
//	/** {@inheritDoc} */ @Override protected QName getPortName() { return new QName(Conf.getApsUploadNamespace(),Conf.getApsUploadPortname()); }
//	/** {@inheritDoc} */ @Override protected QName getServiceName() { return new QName(Conf.getApsUploadNamespace(),Conf.getApsUploadServicename()); }
//
//	/** {@inheritDoc} */ @Override protected String getUser(){ return Conf.getApsUploadUsr(); }
//	/** {@inheritDoc} */ @Override protected String getPwd(){ return Conf.getApsUploadPwd(); }
//	/** {@inheritDoc} */ @Override protected String getSoapAction(){ return Conf.getApsUploadAction(); }
//	/** {@inheritDoc} */ @Override protected long getWsTimeout() { return Conf.getApsUploadWsTimeout(); }
//
//	/** {@inheritDoc} */ @Override protected boolean getOnJboss() { return Conf.getApsUploadSslOnJboss(); }
//	/** {@inheritDoc} */ @Override protected boolean getSslEnable() { return Conf.getApsUploadSslEnable(); }
//	/** {@inheritDoc} */ @Override protected String getSslKeyStoreType() { return Conf.getApsUploadSslKeyStoreType(); }
//	/** {@inheritDoc} */ @Override protected String getSslKeyStore() { return Conf.getApsUploadSslKeyStore(); }
//	/** {@inheritDoc} */ @Override protected char[] getSslKeyStorePwd() { return Conf.getApsUploadSslKeyStorePwd()==null?new char[0]:Conf.getApsUploadSslKeyStorePwd().toCharArray(); }
//	/** {@inheritDoc} */ @Override protected String getSslCertAlias() { return null; }
//	/** {@inheritDoc} */ @Override protected String getSslTrustStoreType() { return Conf.getApsUploadSslTrustStoreType(); }
//	/** {@inheritDoc} */ @Override protected String getSslTrustStore() { return Conf.getApsUploadSslTrustStore(); }
//	/** {@inheritDoc} */ @Override protected char[] getSslTrustStorePwd() { return Conf.getApsUploadSslTrustStorePwd()==null?new char[0]:Conf.getApsUploadSslTrustStorePwd().toCharArray(); }


	/** {@inheritDoc} */ @Override protected IConfSenderSoap getConf() {
		
		return new IConfSenderSoap() {
			
			@Override public boolean isTrakEnabled() { return Conf.isProrogheTrackingIsEnabled(); }
						
			@Override public String getPortName() { return Conf.getApsUploadPortname(); }
			@Override public String getServiceName() { return Conf.getApsUploadServicename(); }
			@Override public String getNamespaceURI() { return Conf.getApsUploadNamespace(); }
			@Override public String getUrl() { return Conf.getApsUploadEndpoint(); }
			@Override public String getUsername() { return Conf.getApsUploadUsr(); }
			@Override public String getPassword() { return Conf.getApsUploadPwd(); }
			@Override public int getReadTimeout() { return Conf.getApsUploadWsTimeout_int(); }
			@Override public String getJksPwd() { return Conf.getApsUploadSslKeyStorePwd(); }
			@Override public String getJksPath() { return Conf.getApsUploadSslKeyStore(); }
			@Override public String getDescrizione() { return null; }
			@Override public int getConnectTimeout() { return Conf.getApsUploadWsTimeout_int(); }
			@Override public boolean getBaseAuthEnabled() { return getUsername()!=null && getUsername().trim().length()>0; }
			@Override public String getAlias() { return null; }
			@Override public String getSoapAction() { return Conf.getApsUploadAction(); }
		};
	}

}
