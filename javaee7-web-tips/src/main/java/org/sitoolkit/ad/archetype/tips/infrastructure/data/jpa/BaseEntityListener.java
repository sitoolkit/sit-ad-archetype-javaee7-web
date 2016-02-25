package org.sitoolkit.ad.archetype.tips.infrastructure.data.jpa;

import java.security.Principal;
import java.sql.Timestamp;

import javax.inject.Inject;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class BaseEntityListener {

    @Inject
    Principal principal;

    @PrePersist
    public void prePersist(BaseEntity entity) {
        preUpdate(entity);
        entity.setCreated(entity.getUpdated());
        entity.setCreatedBy(entity.getUpdatedBy());
    }

    @PreUpdate
    public void preUpdate(BaseEntity entity) {
        entity.setUpdated(new Timestamp(System.currentTimeMillis()));
        entity.setUpdatedBy(principal.getName());
    }
}
