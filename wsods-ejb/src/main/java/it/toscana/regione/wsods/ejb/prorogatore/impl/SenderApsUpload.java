package it.toscana.regione.wsods.ejb.prorogatore.impl;


import it.toscana.regione.wsods.ejb.prorogatore.api.ISenderApsUpload;
import it.toscana.regione.wsods.ejb.sender.abs.Sender;
import it.toscana.regione.wsods.ejb.sender.api.ISender;
import it.toscana.regione.wsods.entity.jpa.api.IWsodsSoapTracking;
import it.toscana.regione.wsods.singleton.Conf;

import javax.ejb.Stateless;
import javax.xml.namespace.QName;



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
	

	/** {@inheritDoc} */ @Override protected String getEndpoint() { return Conf.getApsUploadEndpoint(); }
	/** {@inheritDoc} */ @Override protected QName getPortName() { return new QName(Conf.getApsUploadNamespace(),Conf.getApsUploadPortname()); }
	/** {@inheritDoc} */ @Override protected QName getServiceName() { return new QName(Conf.getApsUploadNamespace(),Conf.getApsUploadServicename()); }

	/** {@inheritDoc} */ @Override protected String getUser(){ return Conf.getApsUploadUsr(); }
	/** {@inheritDoc} */ @Override protected String getPwd(){ return Conf.getApsUploadPwd(); }
	/** {@inheritDoc} */ @Override protected String getSoapAction(){ return Conf.getApsUploadAction(); }
	/** {@inheritDoc} */ @Override protected long getWsTimeout() { return Conf.getApsUploadWsTimeout(); }

	/** {@inheritDoc} */ @Override protected boolean getOnJboss() { return Conf.getApsUploadSslOnJboss(); }
	/** {@inheritDoc} */ @Override protected boolean getSslEnable() { return Conf.getApsUploadSslEnable(); }
	/** {@inheritDoc} */ @Override protected String getSslKeyStoreType() { return Conf.getApsUploadSslKeyStoreType(); }
	/** {@inheritDoc} */ @Override protected String getSslKeyStore() { return Conf.getApsUploadSslKeyStore(); }
	/** {@inheritDoc} */ @Override protected char[] getSslKeyStorePwd() { return Conf.getApsUploadSslKeyStorePwd()==null?new char[0]:Conf.getApsUploadSslKeyStorePwd().toCharArray(); }
	/** {@inheritDoc} */ @Override protected String getSslTrustStoreType() { return Conf.getApsUploadSslTrustStoreType(); }
	/** {@inheritDoc} */ @Override protected String getSslTrustStore() { return Conf.getApsUploadSslTrustStore(); }
	/** {@inheritDoc} */ @Override protected char[] getSslTrustStorePwd() { return Conf.getApsUploadSslTrustStorePwd()==null?new char[0]:Conf.getApsUploadSslTrustStorePwd().toCharArray(); }
	

}
