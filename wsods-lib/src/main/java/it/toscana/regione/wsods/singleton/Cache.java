/** */
package it.toscana.regione.wsods.singleton;

import it.toscana.regione.wsods.entity.bean.api.ISoggetto;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author cciurli
 *
 */
public class Cache {
	
	private final Map<String, ISoggetto> map;
	private final Map<String, String> cf2idUni;
	private final Map<String, Long> live;
	
	private final Long DEFAULT_DELTA_TIME_TO_LIVE = new Long(1000L * 60L * 60L * 24L);
	private Long timeToLiveDelta;
	
	
	/** */
	private Cache() {
		super();
		map = new HashMap<String, ISoggetto>();
		cf2idUni = new HashMap<String, String>();
		live = new HashMap<String, Long>();
		timeToLiveDelta = DEFAULT_DELTA_TIME_TO_LIVE;
		
	}
	
	private static Cache instance_ = null;
	
	
	private synchronized static Cache get() {
		if (Cache.instance_ == null) {
			Cache.instance_ = new Cache();
		}
		return Cache.instance_;
	}
	
	
	private synchronized boolean isContainsAndValid(final String idUni) {
		if (idUni != null && live.containsKey(idUni)) {
			final Long time = live.get(idUni);
			if (time != null && time.longValue() > System.currentTimeMillis() - timeToLiveDelta) {
				return true;
			} else {
				remove(idUni);
			}
		}
		return false;
	}
	
	
	private synchronized ISoggetto get(final String idUni) {
		final ISoggetto soggetto;
		if (isContainsAndValid(idUni)) {
			soggetto = map.get(idUni);
		} else {
			soggetto = null;
		}
		return soggetto;
	}
	
	
	private synchronized void remove(final String idUni) {
		if (idUni != null) {
			if (cf2idUni.containsKey(idUni)) {
				cf2idUni.remove(idUni);
			}
			if (live.containsKey(idUni)) {
				live.remove(idUni);
			}
			if (map.containsKey(idUni)) {
				map.remove(idUni);
			}
		}
	}
	
	
	private synchronized String convert(final String cf) {
		if (cf != null) {
			if (cf2idUni.containsKey(cf)) { return cf2idUni.get(cf); }
		}
		return null;
	}
	
	
	private synchronized void add(final String idUni, final String cf, final ISoggetto value) {
		if (idUni != null && cf != null) {
			live.put(idUni, new Long(System.currentTimeMillis()));
			cf2idUni.put(cf, idUni);
			map.put(idUni, value);
		}
	}
	
	
	private synchronized void put(final ISoggetto value) {
		if (value != null) {
			final String idUni = value.getIdUni();
			final String cf = value.getCodiceFiscale();
			remove(idUni);
			add(idUni, cf, value);
		}
	}
	
	
	private synchronized void cleanOld() {
		final Set<String> keys = map.keySet();
		for (final String key : keys) {
			isContainsAndValid(key);
		}
	}
	
	
	public synchronized static void setTimeToLiveGiorni(final long giorni) {
		if (giorni > 0L) {
			Cache.get().timeToLiveDelta = giorni * 24 * 60 * 60 * 1000L;
		}
	}
	
	
	public synchronized static void setTimeToLiveOre(final long ore) {
		if (ore > 0L) {
			Cache.get().timeToLiveDelta = ore * 60 * 60 * 1000L;
		}
	}
	
	
	public synchronized static void setTimeToLiveMinute(final long minute) {
		if (minute > 10L) {
			Cache.get().timeToLiveDelta = minute * 60000L;
		}
	}
	
	
	public synchronized static void clean() {
		Cache.get().cleanOld();
	}
	
	
	public synchronized static ISoggetto idUni(final String idUni) {
		return Cache.get().get(idUni);
	}
	
	
	public synchronized static ISoggetto cf(final String cf) {
		return Cache.idUni(Cache.get().convert(cf));
	}
	
	
	public synchronized static void add(final ISoggetto value) {
		Cache.get().put(value);
	}
	
	
}
