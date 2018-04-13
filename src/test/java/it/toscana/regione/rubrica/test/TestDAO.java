package it.toscana.regione.rubrica.test;

import it.toscana.regione.rubrica.config.Constants;
import it.toscana.regione.rubrica.dao.HibernateDao;
import it.toscana.regione.rubrica.domain.Authority;
import it.toscana.regione.rubrica.domain.Contact;
import it.toscana.regione.rubrica.domain.User;
import it.toscana.regione.rubrica.domain.search.ContactSearch;

import java.util.HashSet;
import java.util.Set;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
/**
 * Implementazione dei test unitari mirati a verificare il corretto funzionamento
 * della fase di accesso ai dati.
 * @author Andrea Mallegni, T.A.I. srl
 * @version $Id: TestDAO.java 595 2010-08-23 10:27:43Z amallegni $
 */
public class TestDAO extends AbstractTransactionalDataSourceSpringContextTests {
    private static final String ROLE_TEST = "ROLE_TEST";
    /**
     * DAO.
     */
    private HibernateDao dao;        
    /**
     * Setta il DAO.
     * @param dao
     */
    public void setDao(HibernateDao dao) {
	this.dao = dao;
    }    
    /**
     * Test del salvataggio dell'utente.
     */
    public void testSaveUser() {	
	User user = getTestUser();
	dao.storeUser(user, getTestUserAuthorities(user));	
	User retrievedUser = dao.loadUser(user.getUsername());
	assertNotNull("User insertion test failure", retrievedUser);	
    }
    /**
     * Test del salvataggio del contatto.
     */
    public void testSaveContact() {	
	Contact testContact = getTestContactForTestUser();	
	dao.storeContact(testContact);
	Contact retrievedContact = dao.loadContact(testContact.getId());
	assertNotNull("Contact insertion test failure", retrievedContact);
    }
    /**
     * Informa la classe sulla locazione dell'applicationContext di test.  
     */
    protected String[] getConfigLocations() {	
	return new String[]{"applicationContext-test.xml"};
    }
    /**
     * Restituisce un utente fittizio per scopi di test.
     * @return testUser
     */
    private User getTestUser() {
	User user = new User();
	user.setEnabled(false);
	user.setUsername(Constants.TEST_USER_USERNAME);
	user.setPassword(Constants.TEST_USER_PASSWORD);		
	return user;
    }
    /**
     * Restituisce authorities fittizie per l'utente di test.
     * @param user
     * @return testAuthorities
     */
    private Set<Authority> getTestUserAuthorities(User user) {
	Set<Authority> auths = new HashSet<Authority>();
	Authority testAuth = new Authority();
	testAuth.setAuthority(ROLE_TEST);
	testAuth.setUser(user);
	auths.add(testAuth);
	return auths;
    }        
    /**
     * Restistuisce un contatto fittizio per l'utente di test.
     * @return testContact
     */
    private Contact getTestContactForTestUser() {
	Contact testContact = new Contact();
	testContact.setCap("00000");
	testContact.setCellulare("3380000000");
	testContact.setCitta("test_city");
	testContact.setCognome("test_surname");
	testContact.setEmail("test@example.com");
	testContact.setFax("050000000");
	testContact.setIndirizzo("Via test, 1");
	testContact.setNazione("test_country");
	testContact.setNome("test_name");
	testContact.setNote("comment comment comment");
	testContact.setProvincia("test_province");
	testContact.setTelCasa("050111111");
	testContact.setTelUfficio("050222222");
	testContact.setUtente(getTestUser());
	testContact.setId(-1);
	return testContact;
    }
      
}
