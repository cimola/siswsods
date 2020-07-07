/** */
package it.toscana.regione.wsods.ejb.download.impl;


import javax.ejb.Stateless;

import it.toscana.regione.common.webservicetool.token.api.IConfSenderSoap;
import it.toscana.regione.wsods.ejb.download.api.ISenderDownload;
import it.toscana.regione.wsods.ejb.sender.abs.Sender;
import it.toscana.regione.wsods.entity.jpa.api.IWsodsSoapTracking;
import it.toscana.regione.wsods.singleton.Conf;


/**
 * @author vmaltese
 *
 */
@Stateless(name = ISenderDownload.EJB_CERT_REF)
public class SenderDownloadCert extends Sender implements ISenderDownload{
		
	public SenderDownloadCert() {super();}

	/** {@inheritDoc} */ @Override public boolean isTrackerEnabled(){ return Conf.isSoapTrackingToSogeiEnable(); }

	/** {@inheritDoc} */ @Override public String getSrc() { return IWsodsSoapTracking.SRC_WSODS; }

	/** {@inheritDoc} */ @Override public String getDest() { return IWsodsSoapTracking.DEST_SOGEI; }
	
//	/** {@inheritDoc} */ @Override protected QName getPortName() { return new QName(Conf.getSogeiDownloadNameSpaceCert(), Conf.getSogeiDownloadPortNameCert()); }
//
//	/** {@inheritDoc} */ @Override protected QName getServiceName() { return new QName(Conf.getSogeiDownloadNameSpaceCert(), Conf.getSogeiDownloadServiceNameCert()); }
//
//	/** {@inheritDoc} */ @Override protected String getEndpoint()  { return Conf.getSogeiDownloadEndpointCert(); }
//
//	/** {@inheritDoc} */ @Override protected String getUser()  { return Conf.getSogeiDownloadUsrCert(); }
//
//	/** {@inheritDoc} */ @Override protected String getPwd()  { return Conf.getSogeiDownloadPwdCert(); }
//
//	/** {@inheritDoc} */ @Override protected String getSoapAction()  { return Conf.getSogeiDownloadActionCert(); }
//	
//	/** {@inheritDoc} */ @Override protected long getWsTimeout() { return Conf.getSogeiDownloadWsTimeoutCert(); }
//
//	/** {@inheritDoc} */ @Override protected boolean getOnJboss() { return Conf.getSogeiDownloadSslOnJbossCert(); }
//
//	/** {@inheritDoc} */ @Override protected boolean getSslEnable() { return Conf.getSogeiDownloadSslEnableCert(); }
//
//	/** {@inheritDoc} */ @Override protected String getSslKeyStoreType() { return Conf.getSogeiDownloadSslKeyStoreTypeCert(); }
//
//	/** {@inheritDoc} */ @Override protected String getSslKeyStore() { return Conf.getSogeiDownloadSslKeyStoreCert(); }
//
//	/** {@inheritDoc} */ @Override protected char[] getSslKeyStorePwd() { return Conf.getSogeiDownloadSslKeyStorePwdCert()==null?new char[0]:Conf.getSogeiDownloadSslKeyStorePwdCert().toCharArray(); }
//
//	/** {@inheritDoc} */ @Override protected String getSslCertAlias() { return Conf.getSogeiDownloadSslCertAliasCert(); }
//	
//	/** {@inheritDoc} */ @Override protected String getSslTrustStoreType() { return Conf.getSogeiDownloadSslTrustStoreTypeCert(); }
//
//	/** {@inheritDoc} */ @Override protected String getSslTrustStore() { return Conf.getSogeiDownloadSslTrustStoreCert(); }
//
//	/** {@inheritDoc} */ @Override protected char[] getSslTrustStorePwd() { return Conf.getSogeiDownloadSslTrustStorePwdCert()==null?new char[0]:Conf.getSogeiDownloadSslTrustStorePwdCert().toCharArray(); }

	/** {@inheritDoc} */ @Override protected IConfSenderSoap getConf() {
		
		return new IConfSenderSoap() {
			
			@Override public boolean isTrakEnabled() { return Conf.isSoapTrackingToSogeiEnable(); }
						
			@Override public String getPortName() { return Conf.getSogeiDownloadPortNameCert(); }
			@Override public String getServiceName() { return Conf.getSogeiDownloadServiceNameCert(); }
			@Override public String getNamespaceURI() { return Conf.getSogeiDownloadNameSpaceCert(); }
			@Override public String getUrl() { return Conf.getSogeiDownloadEndpointCert(); }
			@Override public String getUsername() { return Conf.getSogeiDownloadUsrCert(); }
			@Override public String getPassword() { return Conf.getSogeiDownloadPwdCert(); }
			@Override public int getReadTimeout() { return Conf.getSogeiDownloadWsTimeoutCert_int(); }
			@Override public String getJksPwd() { return Conf.getSogeiDownloadSslKeyStorePwdCert(); }
			@Override public String getJksPath() { return Conf.getSogeiDownloadSslKeyStoreCert(); }
			@Override public String getDescrizione() { return null; }
			@Override public int getConnectTimeout() { return Conf.getSogeiDownloadWsTimeoutCert_int(); }
			@Override public boolean getBaseAuthEnabled() { return getUsername()!=null && getUsername().trim().length()>0; }
			@Override public String getAlias() { return Conf.getSogeiDownloadSslCertAliasCert(); }
			@Override public String getSoapAction() { return Conf.getSogeiDownloadActionCert()!=null&&Conf.getSogeiDownloadActionCert().trim().length()>0?Conf.getSogeiDownloadActionCert():" p"; }
		};
	}
	
}
