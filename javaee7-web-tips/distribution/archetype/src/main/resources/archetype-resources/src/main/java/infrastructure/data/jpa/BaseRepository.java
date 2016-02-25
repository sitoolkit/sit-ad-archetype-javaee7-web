#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infrastructure.data.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ${package}.infrastructure.search.SearchConditionDo;

/**
 *
 * @author SIToolkit
 */
public abstract class BaseRepository<E extends BaseEntity, ID extends Serializable>
        implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @PersistenceContext(unitName = "${artifactId}-pu")
    private EntityManager em;

    /**
     *
     * @return
     */
    protected EntityManager em() {
        return em;
    }

    /**
     * IDをキーにエンティティを取得します。
     * 
     * @param id
     *            エンティティのID
     * @return IDを持つエンティティ
     */
    public E find(ID id) {
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
    public void delete(ID id) {
        E entity = find(id);
        entity.setDeleted(true);
    }

    public int count(SearchConditionDo condition) {
        Query query = em().createQuery(condition.getCountQuery());
        for (Entry<String, Object> param : condition.getQueryParams().entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }
        return ((Long) query.getSingleResult()).intValue();
    }

    @SuppressWarnings("unchecked")
    public List<E> search(SearchConditionDo condition) {
        Query query = em().createQuery(condition.getSearchQuery());

        for (Entry<String, Object> param : condition.getQueryParams().entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }

        query.setFirstResult(condition.getPageCtrl().getRowNumFrom());
        query.setMaxResults(condition.getPageCtrl().getRowCntPerPage());

        return query.getResultList();
    }

    /**
     * DAOが管轄するエンティティの型を取得します。
     * 
     * @return サービスが管轄するエンティティの型
     */
    public abstract Class<E> getEntityType();

}
