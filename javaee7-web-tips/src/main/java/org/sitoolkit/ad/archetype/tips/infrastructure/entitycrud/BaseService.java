package org.sitoolkit.ad.archetype.tips.infrastructure.entitycrud;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.sitoolkit.ad.archetype.tips.infrastructure.FrameworkException;
import org.sitoolkit.ad.archetype.tips.infrastructure.data.jpa.BaseEntity;
import org.sitoolkit.ad.archetype.tips.infrastructure.data.jpa.BaseRepository;
import org.sitoolkit.ad.archetype.tips.infrastructure.search.SearchConditionDo;

/**
 * このクラスは、サービスクラスが継承すべき基底クラスです。 {@code EntityManager}インスタンスの保持し、
 * またエンティティに対するCRUD操作を提供します。
 * 
 * @author SIToolkit
 *
 * @param <E>
 *            サブクラスが管轄するエンティティの型
 * @param <I>
 *            サブクラスが管轄するエンティティのキーの型
 */
public abstract class BaseService<E extends BaseEntity, I extends Serializable> {

    /**
     * IDをキーにエンティティを取得します。
     * 
     * @param entityId
     *            エンティティのID
     * @return IDを持つエンティティ
     */
    public E find(I entityId) {
        return getDao().find(entityId);
    }

    /**
     * エンティティの永続化命令を永続化コンテキストに送ります。 永続化処理は永続化コンテキストがコミットされた後に実行されます。
     * 
     * @param entity
     *            エンティティ
     */
    public void create(E entity) {
        getDao().create(entity);
    }

    /**
     * エンティティの更新命令を永続化コンテキストに送ります。 更新処理は永続化コンテキストがコミットされた後に実行されます。
     * 
     * @param entity
     *            エンティティ
     */
    public void update(E entity) {
        getDao().update(entity);
    }

    /**
     * IDで指定されるエンティティを論理削除します。
     * 
     * @param entityId
     *            エンティティのID
     */
    public void delete(I entityId) {
        getDao().delete(entityId);
    }

    /**
     * 検索条件を基に検索処理を行い、結果を返します。
     * 
     * @param condition
     *            検索条件
     * @return 検索結果
     */
    public List<E> search(SearchConditionDo condition) {
        condition.buildQuery(getEntityType().getSimpleName());

        int count = getDao().count(condition);
        condition.getPagination().setTotalRowCnt(count);
        if (count == 0) {
            return Collections.emptyList();
        }
        return getDao().search(condition);
    }

    /**
     * エンティティのインスタンスを生成します。
     * 
     * @return エンティティ
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public E newInstance() {
        try {
            return getEntityType().newInstance();
        } catch (Exception e) {
            throw new FrameworkException(e);
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Class<E> getEntityType() {
        return getDao().getEntityType();
    }

    /**
     * サービスが管轄するエンティティの型を取得します。
     * 
     * @return サービスが管轄するエンティティの型
     */
    public abstract BaseRepository<E, I> getDao();

}
