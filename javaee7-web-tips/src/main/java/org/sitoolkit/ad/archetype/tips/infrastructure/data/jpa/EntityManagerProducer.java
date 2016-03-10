package org.sitoolkit.ad.archetype.tips.infrastructure.data.jpa;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Named
@ApplicationScoped
public class EntityManagerProducer {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Produces
    @Default
    @RequestScoped
    public EntityManager create() {
        return this.emf.createEntityManager();
    }

    public void dispose(@Disposes @Default EntityManager em) {
        if (em.isOpen()) {
            em.close();
        }
    }
}
