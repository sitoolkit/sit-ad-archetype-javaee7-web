package org.sitoolkit.ad.archetype.tips.infrastructure.data.jpa;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

@Named
@RequestScoped
public class RollbackRule implements TestRule {

    @Inject
    EntityManager em;

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {
                EntityTransaction transaction = em.getTransaction();
                transaction.begin();
                try {
                    base.evaluate();
                } finally {
                    transaction.rollback();
                }
            }

        };
    }

}
