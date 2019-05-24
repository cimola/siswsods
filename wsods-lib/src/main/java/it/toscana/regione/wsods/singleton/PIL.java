/** */
package it.toscana.regione.wsods.singleton;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.toscana.regione.common.pil.PlatformIndipendentLookupFactory;
import it.toscana.regione.common.pil.api.PlatformIndipendentLookup;
import it.toscana.regione.common.pil.type.Contesto;
import it.toscana.regione.common.pil.type.Platform;


/**
 * @author cciurli
 *
 */
public class PIL {

	private final static Logger	LOG	 = LoggerFactory.getLogger(PIL.class);
	
	private PlatformIndipendentLookup pil;

	
	/** */
	private PIL() { 
		super();
		pil = PlatformIndipendentLookupFactory.genera(Platform.JBOSS_AS_7, Contesto.JAVA_GLOBAL);
	}

	private static PIL instance_ = null;
	
	private synchronized static PIL get(){
		if(instance_ == null){
			instance_ = new PIL();
		}
		return instance_;
	}
	
	public synchronized static <T> T lookup(final String ejbRefName, final Class<T> apiClazz) throws NamingException{
		if (ejbRefName == null || apiClazz == null){
			LOG.warn("si e' cercato di fare la lookup di un ejb passando il refname o l'interfaccia nulle");
			return null;
		}
		
		Context ctx = null;
		try{
			ctx = new InitialContext();
			return get().pil.lookup(ctx, Conf.getEarName(), Conf.getEjbJarName(), ejbRefName, apiClazz);
		} catch (NamingException e) {
			LOG.warn(e.getMessage());
			throw e;
		} finally {
			if(ctx != null){
				try {
					ctx.close();
				} catch (NamingException e) {
					LOG.error(e.getMessage(),e);
					throw e;
				}
			}
		}
		
	} 
	
	
}
