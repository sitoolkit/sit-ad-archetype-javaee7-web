package org.sitoolkit.ad.archetype.tips.infrastructure.data.jpa;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.sitoolkit.ad.archetype.tips.infrastructure.code.FlagCD;

/**
 * このクラスは、エンティティクラスが継承すべき基底クラスです。 全エンティティが持つ共通の属性を定義します。
 *
 * @author SIToolkit
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class BaseEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 論理削除フラグ
     */
    @Transient
    private boolean deleted;

    /**
     * 作成日時
     */
    @Column(updatable = false)
    private Timestamp created;

    /**
     * 更新日時
     */
    @Version
    private Timestamp updated;

    /**
     * 作成者
     */
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    /**
     * 更新者
     */
    @Column(name = "updated_by")
    private String updatedBy;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Column(name = "deleted_flg")
    @Access(AccessType.PROPERTY)
    public char getDeletedFlg() {
        return FlagCD.toFlag(isDeleted());
    }

    public void setDeletedFlg(char deletedFlg) {
        this.deleted = FlagCD.toBoolean(deletedFlg);
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

}
