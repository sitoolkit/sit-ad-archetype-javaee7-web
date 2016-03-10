package org.sitoolkit.ad.archetype.tips.infrastructure.data.jpa;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class TestEntityManagerProducer {

    EntityManagerFactory emf;

    @PostConstruct
    public void init() {
        emf = Persistence.createEntityManagerFactory("javaee7-web-tips-pu-test");
    }

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
