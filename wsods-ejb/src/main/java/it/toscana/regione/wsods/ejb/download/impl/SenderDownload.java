/** */
package it.toscana.regione.wsods.ejb.download.impl;


import javax.ejb.Stateless;

import it.toscana.regione.common.webservicetool.token.api.IConfSenderSoap;
import it.toscana.regione.wsods.ejb.download.api.ISenderDownload;
import it.toscana.regione.wsods.ejb.sender.abs.Sender;
import it.toscana.regione.wsods.entity.jpa.api.IWsodsSoapTracking;
import it.toscana.regione.wsods.singleton.Conf;


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
	
	///** {@inheritDoc} */ @Override protected QName getPortName() { return new QName(Conf.getSogeiDownloadNameSpace(), Conf.getSogeiDownloadPortName()); }

	///** {@inheritDoc} */ @Override protected QName getServiceName() { return new QName(Conf.getSogeiDownloadNameSpace(), Conf.getSogeiDownloadServiceName()); }

	///** {@inheritDoc} */ @Override protected String getEndpoint()  { return Conf.getSogeiDownloadEndpoint(); }

	///** {@inheritDoc} */ @Override protected String getUser()  { return Conf.getSogeiDownloadUsr(); }

	///** {@inheritDoc} */ @Override protected String getPwd()  { return Conf.getSogeiDownloadPwd(); }

	///** {@inheritDoc} */ @Override protected String getSoapAction()  { return Conf.getSogeiDownloadAction(); }
	
//	/** {@inheritDoc} */ @Override protected long getWsTimeout() { return Conf.getSogeiDownloadWsTimeout(); }

	///** {@inheritDoc} */ @Override protected boolean getOnJboss() { return Conf.getSogeiDownloadSslOnJboss(); }

	///** {@inheritDoc} */ @Override protected boolean getSslEnable() { return Conf.getSogeiDownloadSslEnable(); }

	///** {@inheritDoc} */ @Override protected String getSslKeyStoreType() { return Conf.getSogeiDownloadSslKeyStoreType(); }

	///** {@inheritDoc} */ @Override protected String getSslKeyStore() { return Conf.getSogeiDownloadSslKeyStore(); }

	///** {@inheritDoc} */ @Override protected char[] getSslKeyStorePwd() { return Conf.getSogeiDownloadSslKeyStorePwd()==null?new char[0]:Conf.getSogeiDownloadSslKeyStorePwd().toCharArray(); }
	
	///** {@inheritDoc} */ @Override protected String getSslCertAlias() { return Conf.getSogeiDownloadSslCertAlias(); }

	///** {@inheritDoc} */ @Override protected String getSslTrustStoreType() { return Conf.getSogeiDownloadSslTrustStoreType(); }

	///** {@inheritDoc} */ @Override protected String getSslTrustStore() { return Conf.getSogeiDownloadSslTrustStore(); }

	///** {@inheritDoc} */ @Override protected char[] getSslTrustStorePwd() { return Conf.getSogeiDownloadSslTrustStorePwd()==null?new char[0]:Conf.getSogeiDownloadSslTrustStorePwd().toCharArray(); }

	/** {@inheritDoc} */ @Override protected IConfSenderSoap getConf() {
		
		return new IConfSenderSoap() {
			
			@Override public boolean isTrakEnabled() { return Conf.isSoapTrackingToSogeiEnable(); }
						
			@Override public String getPortName() { return Conf.getSogeiDownloadPortName(); }
			@Override public String getServiceName() { return Conf.getSogeiDownloadServiceName(); }
			@Override public String getNamespaceURI() { return Conf.getSogeiDownloadNameSpace(); }
			@Override public String getUrl() { return Conf.getSogeiDownloadEndpoint(); }
			@Override public String getUsername() { return Conf.getSogeiDownloadUsr(); }
			@Override public String getPassword() { return Conf.getSogeiDownloadPwd(); }
			@Override public int getReadTimeout() { return Conf.getSogeiDownloadWsTimeout_int(); }
			@Override public String getJksPwd() { return Conf.getSogeiDownloadSslKeyStorePwd(); }
			@Override public String getJksPath() { return Conf.getSogeiDownloadSslKeyStore(); }
			@Override public String getDescrizione() { return null; }
			@Override public int getConnectTimeout() { return Conf.getSogeiDownloadWsTimeout_int(); }
			@Override public boolean getBaseAuthEnabled() { return getUsername()!=null && getUsername().trim().length()>0; }
			@Override public String getAlias() { return Conf.getSogeiDownloadSslCertAlias(); }
			@Override public String getSoapAction() { return Conf.getSogeiDownloadAction()!=null&&Conf.getSogeiDownloadAction().trim().length()>0?Conf.getSogeiDownloadAction():" p"; }
		};
	}
	
	
}
