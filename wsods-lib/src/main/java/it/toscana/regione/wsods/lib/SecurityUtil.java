package it.toscana.regione.wsods.lib;

import it.toscana.regione.wsods.type.Code;
import it.toscana.regione.wsods.exception.SecurityException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyManagementException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.apache.cxf.configuration.jsse.TLSClientParameters;

public class SecurityUtil {
	
	
	public static final String ALGORITM_SUN_X509 = "SunX509";
	public static final String PROTOCOLLO_SSL = "SSL";
	public static final String PROTOCOLLO_HTTPS = "HTTPS";
	
	
	public static KeyStore loadKeyStore(final String pathFile, final String certType, final char[] storepass) throws SecurityException  {

		if(pathFile == null){throw new SecurityException(Code.SECURE_KEY_STORE,"pathFile keystore nullo");}
		if(certType == null){throw new SecurityException(Code.SECURE_KEY_STORE,"pathFile certType nullo");}
		
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(pathFile);
			final KeyStore keystore = KeyStore.getInstance(certType);
			keystore.load(fis, storepass);

			return keystore;
		} catch (FileNotFoundException e) {
			throw new SecurityException(Code.SECURE_KEY_STORE,e);
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(Code.SECURE_KEY_STORE,e);
		} catch (CertificateException e) {
			throw new SecurityException(Code.SECURE_KEY_STORE,e);
		} catch (IOException e) {
			throw new SecurityException(Code.SECURE_KEY_STORE,e);
		} catch (KeyStoreException e) {
			throw new SecurityException(Code.SECURE_KEY_STORE,e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					throw new SecurityException(Code.SECURE_KEY_STORE,e);
				}
			}
		}
	}
	
	public static Certificate loadCertificateFromKeyStore(final KeyStore keystore, final String alias) throws SecurityException  {
		final Certificate cert;
		try {
			cert = keystore.getCertificate(alias);
		} catch (KeyStoreException e) {
			throw new SecurityException(Code.SECURE_KEY_STORE,e);
		}
		return cert;
	}

	public static KeyPair loadKeyPairFromKeyStore(final KeyStore keystore, final char[] keypass, final String alias) throws SecurityException  {

		final Certificate cert;
		try {
			cert = keystore.getCertificate(alias);
		} catch (KeyStoreException e) {
			throw new SecurityException(Code.SECURE_KEY,e);
		}

		final Key key;
		try {
			key = keystore.getKey(alias, keypass);
		} catch (UnrecoverableKeyException e) {
			throw new SecurityException(Code.SECURE_KEY,e);
		} catch (KeyStoreException e) {
			throw new SecurityException(Code.SECURE_KEY,e);
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(Code.SECURE_KEY,e);
		}

		if (key instanceof PrivateKey) {
			final PublicKey publicKey = cert.getPublicKey();
			
			final KeyPair keyPair = new KeyPair(publicKey, (PrivateKey) key);

			return keyPair;
		} else {
			throw new SecurityException(Code.SECURE_KEY,"impossibile ottenere una chiave privata");
		}

	}

	public static byte[] firma(final String algoritm, final PrivateKey privateKey, final byte[] data) throws SecurityException  {
		final Signature signature;
		try {
			signature = Signature.getInstance(algoritm);
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(Code.SECURE_FIRMA,e);
		}
		try {
			signature.initSign(privateKey);
		} catch (InvalidKeyException e) {
			throw new SecurityException(Code.SECURE_FIRMA,e);
		}
		try {
			signature.update(data);
		} catch (SignatureException e) {
			throw new SecurityException(Code.SECURE_FIRMA,e);
		}
		try {
			return signature.sign();
		} catch (SignatureException e) {
			throw new SecurityException(Code.SECURE_FIRMA,e);
		}
	}

	public static boolean verificaFirma(final String algoritm, final PublicKey publicKey, final byte[] data, final byte[] firma) throws SecurityException  {
		Signature signature;
		try {
			signature = Signature.getInstance(algoritm);
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(Code.SECURE_VERIFICA_FIRMA,e);
		}
		try {
			signature.initVerify(publicKey);
		} catch (InvalidKeyException e) {
			throw new SecurityException(Code.SECURE_VERIFICA_FIRMA,e);
		}
		try {
			signature.update(data);
		} catch (SignatureException e) {
			throw new SecurityException(Code.SECURE_VERIFICA_FIRMA,e);
		}
		try {
			return signature.verify(firma);
		} catch (SignatureException e) {
			throw new SecurityException(Code.SECURE_VERIFICA_FIRMA,e);
		}
	}
	
	public static TLSClientParameters getTLSClientParameters(final SSLSocketFactory sslSocketFactory, final String protocollo, final boolean disableCNCheck, final boolean useHostnameVerifier){
		return getTLSClientParameters(sslSocketFactory, null, null, protocollo, disableCNCheck, useHostnameVerifier);
	}

	public static TLSClientParameters getTLSClientParameters(final SSLSocketFactory sslSocketFactory, final KeyManagerFactory keyManagerFactory, final TrustManagerFactory trustManagerFactory, final String protocollo, final boolean disableCNCheck, final boolean useHostnameVerifier){
		final TLSClientParameters  tlsClientParameters = new TLSClientParameters();		
		tlsClientParameters.setSSLSocketFactory(sslSocketFactory);
		if(keyManagerFactory!=null) {tlsClientParameters.setKeyManagers(keyManagerFactory.getKeyManagers());}
		if(trustManagerFactory!=null) {tlsClientParameters.setTrustManagers(trustManagerFactory.getTrustManagers());}
		if(protocollo!=null && protocollo.trim().length()>0) {tlsClientParameters.setSecureSocketProtocol(protocollo);}
		tlsClientParameters.setDisableCNCheck(disableCNCheck);
		tlsClientParameters.setUseHttpsURLConnectionDefaultHostnameVerifier(useHostnameVerifier);
		tlsClientParameters.setUseHttpsURLConnectionDefaultSslSocketFactory(sslSocketFactory==null);
		return tlsClientParameters;
	}
	
	public static KeyManagerFactory getKeyManagerFactory(final KeyStore keyStore, final char[] pwdKeyStore) throws SecurityException {
		try{
			final KeyManagerFactory kmf = KeyManagerFactory.getInstance(ALGORITM_SUN_X509);
			kmf.init(keyStore, pwdKeyStore);
			return kmf;
		} catch (UnrecoverableKeyException e) {
			throw new SecurityException(Code.SECURE_KEY_STORE, e);
		} catch (KeyStoreException e) {
			throw new SecurityException(Code.SECURE_KEY_STORE, e);
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(Code.SECURE_GENERIC, e);
		}
	}
	public static TrustManagerFactory getTrustManagerFactory(final KeyStore trustStore) throws SecurityException{
		try{
			final TrustManagerFactory tmf = TrustManagerFactory.getInstance(ALGORITM_SUN_X509);
			tmf.init(trustStore);
			return tmf;
		} catch (KeyStoreException e) {
			throw new SecurityException(Code.SECURE_KEY_STORE, e);
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(Code.SECURE_GENERIC, e);
		}
	}

	public static SSLSocketFactory createSSLSocketFactory(final URL url,final KeyStore trustStore,final KeyStore keyStore, final char[] pwdKeyStore) throws SecurityException{
		final String errorMsg = "impossibile costruire il contesto SSL";
		final SSLContext sslContext;
		try {
			sslContext = SSLContext.getInstance(PROTOCOLLO_SSL);
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(Code.SECURE_GENERIC,errorMsg,e);
		}
		if (url!=null && url.getProtocol() != null && url.getProtocol().equalsIgnoreCase(PROTOCOLLO_HTTPS)) {
			final SecureRandom secureRandom = new SecureRandom();
			secureRandom.nextInt();
			
			final KeyManagerFactory keyManagerFactory = getKeyManagerFactory(keyStore, pwdKeyStore);
			if(keyManagerFactory == null){throw new SecurityException(Code.SECURE_KEY_STORE,errorMsg);}
			
			final TrustManagerFactory trustManagerFactory = getTrustManagerFactory(trustStore);
			if(trustManagerFactory == null){throw new SecurityException(Code.SECURE_TRUST_STORE,errorMsg);}
			try {
				
				sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), secureRandom);
			} catch (KeyManagementException e) {
				throw new SecurityException(Code.SECURE_GENERIC,errorMsg,e);
			}
		}
		return sslContext.getSocketFactory();
	}

}
