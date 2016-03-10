package org.sitoolkit.ad.archetype.tips.infrastructure.data.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.sitoolkit.ad.archetype.tips.infrastructure.search.SearchConditionDo;

/**
 *
 * @author SIToolkit
 */
public abstract class BaseRepository<E extends BaseEntity, I extends Serializable> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @Inject
    private EntityManager em;

    /**
     *
     * @return EntityManager
     */
    public EntityManager em() {
        return em;
    }

    /**
     * IDをキーにエンティティを取得します。
     * 
     * @param id
     *            エンティティのID
     * @return IDを持つエンティティ
     */
    public E find(I id) {
        return id == null ? null : em().find(getEntityType(), id);
    }

    /**
     * エンティティの永続化命令を永続化コンテキストに送ります。 永続化処理は永続化コンテキストがコミットされた後に実行されます。
     * 
     * @param entity
     *            エンティティ
     */
    public void create(E entity) {
        em().persist(entity);
    }

    /**
     * エンティティの更新命令を永続化コンテキストに送ります。 更新処理は永続化コンテキストがコミットされた後に実行されます。
     * 
     * @param entity
     *            エンティティ
     */
    public void update(E entity) {
        em().merge(entity);
    }

    /**
     * IDで指定されるエンティティを論理削除します。
     * 
     * @param id
     *            エンティティのID
     */
    public void delete(I id) {
        E entity = find(id);
        entity.setDeleted(true);
    }

    /**
     * 
     * @param condition
     *            検索条件
     * @return 検索条件に一致した件数
     */
    public int count(SearchConditionDo condition) {
        Query query = em().createQuery(condition.getCountQuery());
        for (Entry<String, Object> param : condition.getQueryParams().entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }
        return ((Long) query.getSingleResult()).intValue();
    }

    /**
     * 
     * @param condition
     *            検索条件
     * @return 検索結果
     */
    @SuppressWarnings("unchecked")
    public List<E> search(SearchConditionDo condition) {
        Query query = em().createQuery(condition.getSearchQuery());

        for (Entry<String, Object> param : condition.getQueryParams().entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }

        query.setFirstResult(condition.getPagination().getRowNumFrom());
        query.setMaxResults(condition.getPagination().getRowCntPerPage());

        return query.getResultList();
    }

    /**
     * DAOが管轄するエンティティの型を取得します。
     * 
     * @return サービスが管轄するエンティティの型
     */
    public abstract Class<E> getEntityType();

}
