package org.sitoolkit.ad.archetype.tips.infrastructure.data.jpa;

import java.security.Principal;
import java.sql.Timestamp;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class BaseEntityListener {

    /*
     * TODO JPA 2.1からEntityListenerにCDIが有効になる仕様が追加されていますが、 Wildfly
     * 10.0.0.Finalでは org.jboss.weld.exceptions.IllegalArgumentException:
     * WELD-001456: Argument resolvedBean must not be null 例外が送出されます。
     * https://issues.jboss.org/browse/WFLY-2540 一旦コメントアウトします。
     */
    // @Inject
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
