package org.sitoolkit.ad.archetype.tips.infrastructure.data.jpa;

import java.security.Principal;
import java.sql.Timestamp;

import javax.inject.Inject;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class BaseEntityListener {

    @Inject
    Principal principal;

    /**
     * 
     * @param entity
     *            エンティティ
     */
    @PrePersist
    public void prePersist(BaseEntity entity) {
        preUpdate(entity);
        entity.setCreated(new Timestamp(System.currentTimeMillis()));
        entity.setCreatedBy(entity.getUpdatedBy());
    }

    @PreUpdate
    public void preUpdate(BaseEntity entity) {
        if (principal == null) {
            entity.setUpdatedBy("system");
        } else {
            entity.setUpdatedBy(principal.getName());
        }
    }
}
