package it.toscana.regione.wsods.ejb.monitoraggio.impl;

import javax.ejb.Stateless;

import it.toscana.regione.common.webservicetool.token.api.IConfSenderSoap;
import it.toscana.regione.common.webservicetool.util.Factory;
import it.toscana.regione.monitoraggio.provider.MonitorEntryProviderLocal;
import it.toscana.regione.monitoraggio.util.Utils;
import it.toscana.regione.monitoraggio.xml.jaxb.conf.MonitorStep;
import it.toscana.regione.monitoraggio.xml.jaxb.ws.MonitorEntry;
import it.toscana.regione.monitoraggio.xml.jaxb.ws.StatusCode;

@Stateless(name="MonitorEntryProviderWsdsWs")
public class MonitorEntryProviderWsdsWs implements MonitorEntryProviderLocal {

	public MonitorEntryProviderWsdsWs() {
		// TODO Auto-generated constructor stub
	}

	private static IConfSenderSoap getConf(final MonitorStep monitorStep) {
		return new IConfSenderSoap() {
			
			@Override
			public boolean isTrakEnabled() { return false; }
			
			@Override public String getUsername() { return monitorStep.getHttpBasicUser(); }
			@Override public String getUrl() { return monitorStep.getUrl(); }
			@Override public int getReadTimeout() { return getConnectTimeout(); }
			@Override public String getPassword() { return monitorStep.getHttpBasicPassword(); }
			@Override public String getJksPwd() { return monitorStep.getKeystorePassword(); }
			@Override public String getJksPath() { return monitorStep.getKeystorePath(); }
			@Override public String getDescrizione() { return null; }
			@Override public int getConnectTimeout() { return monitorStep.getConnectionTimeout()!=null?monitorStep.getConnectionTimeout().intValue():36000; }
			@Override public boolean getBaseAuthEnabled() { return monitorStep.isHttpBasicEnabled(); }
			@Override public String getAlias() { return monitorStep.getKeystoreAlias(); }
			@Override public String getSoapAction() { return "x"; }
			@Override public String getServiceName() { return "x"; }
			@Override public String getPortName() { return "x"; }
			@Override public String getNamespaceURI() { return "x"; }
		};
	}
	
	@Override
	public MonitorEntry doStep(final MonitorStep monitorStep) {
		try {
			if(Factory.createSoapSender(getConf(monitorStep)).testWsdl()) {
				return Utils.createMonitorEntry(monitorStep.getResourceName(), StatusCode.OK, monitorStep.getOkCustomMessage());
			} else {
				return Utils.createMonitorEntry(monitorStep.getResourceName(), StatusCode.ERROR, "wsdl non recuperato");
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} 
	}
}
