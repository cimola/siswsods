/** */
package it.toscana.regione.wsods.factory;


import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.AutocertificazioneType;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.EsenzioneType;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.GetEsenzioniAps2WsOdsResponse;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.GetListaAps2WsOdsResponse;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.ID4SOGEIType;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.SoggettoType;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.UploadAps2WsOdsResponse;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.UploadWebApp2ApsRequest;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.request.DownloadAutocertificazione;
import it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.request.DownloadCertificazione;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;


/**
 * @author cciurli
 *
 */
public class JaxbElementFactory {

	private static final it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.request.ObjectFactory SogeiDownloadRequest = new it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.request.ObjectFactory();
	private static final it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.ObjectFactory rt = new it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.ObjectFactory();
	private static final it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.request.ObjectFactory SogeiDownloadRequestCert = new it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.request.ObjectFactory();
	
	private final static QName _DownloadAutocertificazione_QNAME = new QName("http://downloadautocertrequest.xsd.cert.esenzionireddito.sanita.finanze.it", "DownloadAutocertificazione");
	private final static QName _DownloadCertificazione_QNAME = new QName("http://downloadcertrequest.xsd.cert.esenzionireddito.sanita.finanze.it", "DownloadCertificazione");
	/** */
	private JaxbElementFactory() { }
	
	public static JAXBElement<DownloadAutocertificazione> createSogeiDownloadRequest(){
		DownloadAutocertificazione element = SogeiDownloadRequest.createDownloadAutocertificazione();
		JAXBElement<DownloadAutocertificazione> jaxb = new JAXBElement<DownloadAutocertificazione>(_DownloadAutocertificazione_QNAME, DownloadAutocertificazione.class, element);
		return jaxb;
	}
	
	public static JAXBElement<DownloadCertificazione> createSogeiDownloadRequestCert(){
		DownloadCertificazione element = SogeiDownloadRequestCert.createDownloadCertificazione();
		JAXBElement<DownloadCertificazione> jaxb = new JAXBElement<DownloadCertificazione>(_DownloadCertificazione_QNAME, DownloadCertificazione.class, element);
		return jaxb;
	}

	public static JAXBElement<GetEsenzioniAps2WsOdsResponse> createGetEsenzioniAps2WsOdsResponse() {
		final GetEsenzioniAps2WsOdsResponse value = rt.createGetEsenzioniAps2WsOdsResponse();
		value.setGetEsenzioni(rt.createGetEsenzioniResponse());
		return rt.createGetEsenzioniAps2WsOdsResponse(value);
	}
	public static JAXBElement<GetListaAps2WsOdsResponse> createGetListaAps2WsOdsResponse() {
		final GetListaAps2WsOdsResponse value = rt.createGetListaAps2WsOdsResponse();
		value.setGetLista(rt.createGetListaResponse());
		return rt.createGetListaAps2WsOdsResponse(value);
	}
	
	public static JAXBElement<UploadAps2WsOdsResponse> createUploadWAps2WsOdsResponse() {
		final UploadAps2WsOdsResponse value = rt.createUploadAps2WsOdsResponse();
		return rt.createUploadWAps2WsOdsResponse(value);
	}
	
	public static JAXBElement<UploadWebApp2ApsRequest> createUploadWebApp2ApsRequest() {
		final it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.ObjectFactory rt = new it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.ObjectFactory();
		final UploadWebApp2ApsRequest value = rt.createUploadWebApp2ApsRequest();
		return rt.createUploadWebApp2ApsRequest(value);
	}
	
	public static AutocertificazioneType createAutocertificazioneType(){
		return rt.createAutocertificazioneType();
	}
	public static EsenzioneType createEsenzioneType(){
		return rt.createEsenzioneType();
	}
	public static AutocertificazioneType createNew(final AutocertificazioneType clazz ){
		return rt.createAutocertificazioneType();
	}
	public static EsenzioneType createNew(final EsenzioneType clazz ){
		return rt.createEsenzioneType();
	}

	
	public static SoggettoType createSoggettoTypeIdUni(final String idUni){
		SoggettoType soggettoType = rt.createSoggettoType();
		soggettoType.setIdUniversale(rt.createSoggettoTypeIdUniversale(idUni));
		return	soggettoType;
	}
	public static SoggettoType createSoggettoTypeCodiceFiscale(final String cf){
		SoggettoType soggettoType = rt.createSoggettoType();
		soggettoType.setCodiceFiscale(rt.createSoggettoTypeCodiceFiscale(cf));
		return	soggettoType;
	}
	public static ID4SOGEIType createID4SOGEIType() {
		final it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.ObjectFactory rt = new it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.ObjectFactory();
		final ID4SOGEIType value = rt.createID4SOGEIType();
		return value;
	}
}
