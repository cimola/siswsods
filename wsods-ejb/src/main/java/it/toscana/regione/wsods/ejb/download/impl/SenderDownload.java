/** */
package it.toscana.regione.wsods.ejb.download.impl;


import it.toscana.regione.wsods.ejb.download.api.ISenderDownload;
import it.toscana.regione.wsods.ejb.sender.abs.Sender;
import it.toscana.regione.wsods.entity.jpa.api.IWsodsSoapTracking;
import it.toscana.regione.wsods.singleton.Conf;

import javax.ejb.Stateless;
import javax.xml.namespace.QName;


/**
 * @author cciurli
 *
 */
@Stateless(name = ISenderDownload.EJB_REF)
public class SenderDownload extends Sender implements ISenderDownload{
		
	public SenderDownload() {super();}

	/** {@inheritDoc} */ @Override public boolean isTrackerEnabled(){ return Conf.isSoapTrackingToSogeiEnable(); }

	/** {@inheritDoc} */ @Override public String getSrc() { return IWsodsSoapTracking.SRC_WSODS; }

	/** {@inheritDoc} */ @Override public String getDest() { return IWsodsSoapTracking.DEST_SOGEI; }
	
	/** {@inheritDoc} */ @Override protected QName getPortName() { return new QName(Conf.getSogeiDownloadNameSpace(), Conf.getSogeiDownloadPortName()); }

	/** {@inheritDoc} */ @Override protected QName getServiceName() { return new QName(Conf.getSogeiDownloadNameSpace(), Conf.getSogeiDownloadServiceName()); }

	/** {@inheritDoc} */ @Override protected String getEndpoint()  { return Conf.getSogeiDownloadEndpoint(); }

	/** {@inheritDoc} */ @Override protected String getUser()  { return Conf.getSogeiDownloadUsr(); }

	/** {@inheritDoc} */ @Override protected String getPwd()  { return Conf.getSogeiDownloadPwd(); }

	/** {@inheritDoc} */ @Override protected String getSoapAction()  { return Conf.getSogeiDownloadAction(); }
	
	/** {@inheritDoc} */ @Override protected long getWsTimeout() { return Conf.getSogeiDownloadWsTimeout(); }

	/** {@inheritDoc} */ @Override protected boolean getOnJboss() { return Conf.getSogeiDownloadSslOnJboss(); }

	/** {@inheritDoc} */ @Override protected boolean getSslEnable() { return Conf.getSogeiDownloadSslEnable(); }

	/** {@inheritDoc} */ @Override protected String getSslKeyStoreType() { return Conf.getSogeiDownloadSslKeyStoreType(); }

	/** {@inheritDoc} */ @Override protected String getSslKeyStore() { return Conf.getSogeiDownloadSslKeyStore(); }

	/** {@inheritDoc} */ @Override protected char[] getSslKeyStorePwd() { return Conf.getSogeiDownloadSslKeyStorePwd()==null?new char[0]:Conf.getSogeiDownloadSslKeyStorePwd().toCharArray(); }

	/** {@inheritDoc} */ @Override protected String getSslTrustStoreType() { return Conf.getSogeiDownloadSslTrustStoreType(); }

	/** {@inheritDoc} */ @Override protected String getSslTrustStore() { return Conf.getSogeiDownloadSslTrustStore(); }

	/** {@inheritDoc} */ @Override protected char[] getSslTrustStorePwd() { return Conf.getSogeiDownloadSslTrustStorePwd()==null?new char[0]:Conf.getSogeiDownloadSslTrustStorePwd().toCharArray(); }
	
	
}
