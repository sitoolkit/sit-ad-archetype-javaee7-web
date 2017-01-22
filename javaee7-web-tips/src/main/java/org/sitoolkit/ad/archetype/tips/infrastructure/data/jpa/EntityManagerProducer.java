package org.sitoolkit.ad.archetype.tips.infrastructure.data.jpa;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author SIToolkit
 */
@Named
@ApplicationScoped
public class EntityManagerProducer {

    private static final String PERSISTENCE_UNIT = "javaee7-web-tips-pu";

    @PersistenceUnit(unitName = PERSISTENCE_UNIT)
    EntityManagerFactory emf;

    @PostConstruct
    public void init() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT + "-non-ee-container");
        }
    }

    @Produces
    @RequestScoped
    public EntityManager create() {
        return emf.createEntityManager();
    }

    public void dispose(@Disposes @Default EntityManager em) {
        if (em.isOpen()) {
            em.close();
        }
    }
}
