package org.sitoolkit.ad.archetype.javaee7min;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
@RequestScoped
public class MyModel {

    @PersistenceContext(unitName = "javaee7-min-pu")
    private EntityManager em;

    public Object getValue() {
        Content content = em.find(Content.class, 1);
        return content.getValue();
    }

}
