package it.toscana.regione.wsods.ejb.monitoraggio.impl;

import it.toscana.regione.monitoraggio.provider.EntityManagerProvider;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name="EntityManagerProviderWsods")
public class EntityManagerProviderWsods implements EntityManagerProvider {

	/**
	 * {@link EntityManager} per la gestione della persistenza.
	 */
	@PersistenceContext(unitName = "wsods")
	private EntityManager entityManager;
	
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

}
