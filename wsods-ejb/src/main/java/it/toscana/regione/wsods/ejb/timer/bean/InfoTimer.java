package it.toscana.regione.wsods.ejb.timer.bean;

import it.toscana.regione.wsods.ejb.timer.api.ITimer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

	public class InfoTimer implements Serializable {

		private static final long serialVersionUID = -7684746283153456463L;

		
		public static InfoTimer[] createArrayInfoTimer(String ejbRef, final long numero,final long timeout){
			if(ITimer.EJB_REF_DOWNLOAD_INSERIMENTI_FROM_SOGEI.equals(ejbRef) && numero==1L){
				return new InfoTimer[]{InfoTimer.createInfoTimer(ITimer.EJB_REF_DOWNLOAD_INSERIMENTI_FROM_SOGEI,numero,timeout)};
			} else if (ITimer.EJB_REF_DOWNLOAD_VARIAZIONI_FROM_SOGEI.equals(ejbRef) && numero==1L) {
				return new InfoTimer[]{InfoTimer.createInfoTimer(ITimer.EJB_REF_DOWNLOAD_VARIAZIONI_FROM_SOGEI,numero,timeout)};
			} else if (ITimer.EJB_CERT_REF_DOWNLOAD_ATTESTAZIONI_FROM_SOGEI.equals(ejbRef) && numero==1L) {
				return new InfoTimer[]{InfoTimer.createInfoTimer(ITimer.EJB_CERT_REF_DOWNLOAD_ATTESTAZIONI_FROM_SOGEI,numero,timeout)};}  
			else if (ITimer.EJB_REF_SVECCHIATORE.equals(ejbRef) && numero==1L) {
				return new InfoTimer[]{InfoTimer.createInfoTimer(ITimer.EJB_REF_SVECCHIATORE,numero,timeout)};
			} else if (ITimer.EJB_REF_RIELABORATORE.equals(ejbRef)) {	
				final List<InfoTimer> infoTimer = new ArrayList<InfoTimer>();
				for(long discriminante=0 ; discriminante<numero; discriminante++){
					infoTimer.add(InfoTimer.createInfoTimer(ITimer.EJB_REF_RIELABORATORE,numero,timeout,discriminante));
				}
				return infoTimer.toArray(new InfoTimer[0]);
			} else if (ITimer.EJB_REF_RIELABORATORE_CERT.equals(ejbRef)) {	
				final List<InfoTimer> infoTimer = new ArrayList<InfoTimer>();
				for(long discriminante=0 ; discriminante<numero; discriminante++){
					infoTimer.add(InfoTimer.createInfoTimer(ITimer.EJB_REF_RIELABORATORE_CERT,numero,timeout,discriminante));
				}
				return infoTimer.toArray(new InfoTimer[0]);
			} else if (ITimer.EJB_REF_RECUPERA_SCARTATI.equals(ejbRef)) {	
				return new InfoTimer[]{InfoTimer.createInfoTimer(ITimer.EJB_REF_RECUPERA_SCARTATI,numero,timeout)};
			} else if (ITimer.EJB_REF_RECUPERA_SCARTATI_CERT.equals(ejbRef)) {	
				return new InfoTimer[]{InfoTimer.createInfoTimer(ITimer.EJB_REF_RECUPERA_SCARTATI_CERT,numero,timeout)};
			} else if (ITimer.EJB_REF_PROROGATORE.equals(ejbRef)) {	
				final List<InfoTimer> infoTimer = new ArrayList<InfoTimer>();
				for(long discriminante=0 ; discriminante<numero; discriminante++){
					infoTimer.add(InfoTimer.createInfoTimer(ITimer.EJB_REF_PROROGATORE,numero,timeout,discriminante));
				}
				return infoTimer.toArray(new InfoTimer[0]);
			} else if (ITimer.EJB_REF_ELABORATORE_ASINCORNO.equals(ejbRef) && numero==1L) {	
				return new InfoTimer[]{InfoTimer.createInfoTimer(ITimer.EJB_REF_ELABORATORE_ASINCORNO,numero,timeout)};
			} else if (ITimer.EJB_REF_ELABORATORE_ASINCORNO_CERT.equals(ejbRef) && numero==1L) {	
				return new InfoTimer[]{InfoTimer.createInfoTimer(ITimer.EJB_REF_ELABORATORE_ASINCORNO_CERT,numero,timeout)};
//			} else if (ITimer.EJB_REF_VALUTATORE_DI_RETTIFICA_E01_TIMER.equals(ejbRef) && numero==1L) {	
//				return new InfoTimer[]{ InfoTimer.createInfoTimer(ITimer.EJB_REF_VALUTATORE_DI_RETTIFICA_E01_TIMER,numero,timeout)};
//			} else if (ITimer.EJB_REF_VALUTATORE_DI_RETTIFICA_ERA_TIMER.equals(ejbRef) && numero==1L) {
//				return new InfoTimer[]{InfoTimer.createInfoTimer(ITimer.EJB_REF_VALUTATORE_DI_RETTIFICA_ERA_TIMER,numero,timeout)};
//			} else if (ITimer.EJB_REF_RETTIFICATORE_E01_TIMER.equals(ejbRef) && numero==1L) {
//				return new InfoTimer[]{InfoTimer.createInfoTimer(ITimer.EJB_REF_RETTIFICATORE_E01_TIMER,numero,timeout)};
//			} else if (ITimer.EJB_REF_RETTIFICATORE_ERA_TIMER.equals(ejbRef) && numero==1L) {
//				return new InfoTimer[]{InfoTimer.createInfoTimer(ITimer.EJB_REF_RETTIFICATORE_ERA_TIMER,numero,timeout)};
			} else if(ITimer.EJB_REF_DOWNLOAD_RECUPERO_INSERIMENTI_FROM_SOGEI.equals(ejbRef) && numero==1L){
				return new InfoTimer[]{InfoTimer.createInfoTimer(ITimer.EJB_REF_DOWNLOAD_RECUPERO_INSERIMENTI_FROM_SOGEI,numero,timeout)};
			} else if (ITimer.EJB_REF_DOWNLOAD_RECUPERO_VARIAZIONI_FROM_SOGEI.equals(ejbRef) && numero==1L) {
				return new InfoTimer[]{InfoTimer.createInfoTimer(ITimer.EJB_REF_DOWNLOAD_RECUPERO_VARIAZIONI_FROM_SOGEI,numero,timeout)};
			} else if (ITimer.EJB_REF_DOWNLOAD_RECUPERO_VARIAZIONI_AL_MINUTO_FROM_SOGEI.equals(ejbRef) && numero==1L) {
				return new InfoTimer[]{InfoTimer.createInfoTimer(ITimer.EJB_REF_DOWNLOAD_RECUPERO_VARIAZIONI_AL_MINUTO_FROM_SOGEI,numero,timeout)};
			} else if (ITimer.EJB_CERT_REF_DOWNLOAD_RECUPERO_ATTESTAZIONI_FROM_SOGEI.equals(ejbRef) && numero==1L) {
				return new InfoTimer[]{InfoTimer.createInfoTimer(ITimer.EJB_CERT_REF_DOWNLOAD_RECUPERO_ATTESTAZIONI_FROM_SOGEI,numero,timeout)};
			} else if (ITimer.EJB_REF_RECUPERO_ID_UNI_OBSOLETI.equals(ejbRef) && numero==1L) {
				return new InfoTimer[]{InfoTimer.createInfoTimer(ITimer.EJB_REF_RECUPERO_ID_UNI_OBSOLETI,numero,timeout)};
			} else if (ITimer.EJB_REF_AGGIORNAMENTO_ID_UNI_OBSOLETI.equals(ejbRef) && numero==1L) {
				return new InfoTimer[]{InfoTimer.createInfoTimer(ITimer.EJB_REF_AGGIORNAMENTO_ID_UNI_OBSOLETI,numero,timeout)};
			} else if (ITimer.EJB_REF_RECUPERA_SCARTATI_GIANOV4.equals(ejbRef)) {	
				return new InfoTimer[]{InfoTimer.createInfoTimer(ITimer.EJB_REF_RECUPERA_SCARTATI_GIANOV4,numero,timeout)};
			} else if (ITimer.EJB_REF_RECUPERA_SCARTATI_GIANOV4_CERT.equals(ejbRef)) {	
				return new InfoTimer[]{InfoTimer.createInfoTimer(ITimer.EJB_REF_RECUPERA_SCARTATI_GIANOV4_CERT,numero,timeout)};
			} else if (ITimer.EJB_REF_RICALCOLA_TITOLO_NULL.equals(ejbRef)) {	
				final List<InfoTimer> infoTimer = new ArrayList<InfoTimer>();
				for(long discriminante=0 ; discriminante<numero; discriminante++){
					infoTimer.add(InfoTimer.createInfoTimer(ITimer.EJB_REF_RICALCOLA_TITOLO_NULL,numero,timeout,discriminante));
				}
				return infoTimer.toArray(new InfoTimer[0]);
			} else {
				return new InfoTimer[0]; 
			}
		}
		
		public static InfoTimer createInfoTimer(final Serializable info){ return new InfoTimer(info!=null?info.toString():"",0,0L); }
		private static InfoTimer createInfoTimer(final String ejbRef, final long numero, long timeout, final long discriminante){ return new InfoTimer(ejbRef,numero,timeout, discriminante); }
		private static InfoTimer createInfoTimer(final String ejbRef, final long numero, long timeout){ return new InfoTimer(ejbRef,numero,timeout); }
		
		private final String ejbRef;
		private final Long discriminante;
		private final long numero;
		private final long timeout;
		
		private InfoTimer(final String ejbRef, final long numero, long timeout, final long discriminante){
			this.ejbRef = ejbRef;
			this.numero = numero;
			this.timeout = timeout;
			this.discriminante = new Long(discriminante);
		}
		
		private InfoTimer(final String ejbRef, final long numero, long timeout){
			this(ejbRef,numero,timeout,Long.MIN_VALUE);
		}

		public String getEjbRef(){return this.ejbRef;}
		public long getNumero(){return this.numero;}
		public long getTimeOut(){return this.timeout;}
		public long getDiscriminante(){return this.discriminante.longValue();}
		
		public String toString()
		{
			final StringBuffer sb = new StringBuffer("");
			sb.append(" * ejbRef: ");
			sb.append(getEjbRef());
			sb.append(", numeroDiTimer: ");
			sb.append(getNumero());
			sb.append(", timeout: ");
			sb.append(getTimeOut());
			sb.append(" ms");
			
			if(getDiscriminante()>=0L){
				sb.append(" - discriminante: ");
				sb.append(getDiscriminante());
			}
			return sb.toString();
		}
	}