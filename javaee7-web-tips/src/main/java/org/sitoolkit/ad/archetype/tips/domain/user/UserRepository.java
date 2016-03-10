package org.sitoolkit.ad.archetype.tips.domain.user;

import java.util.List;

import javax.persistence.Query;

import org.sitoolkit.ad.archetype.tips.infrastructure.data.jpa.BaseRepository;
import org.sitoolkit.ad.archetype.tips.infrastructure.layer.Repository;

/**
 * このクラスは、ユーザーマスターのDAOです。
 * 
 * @author SIToolkit
 */
@Repository
public class UserRepository extends BaseRepository<UserEntity, String> {

    private static final long serialVersionUID = 2225666869914977661L;

    @Override
    public Class<UserEntity> getEntityType() {
        return UserEntity.class;
    }

    /**
     * ユーザー姓をキーにUserEntityを抽出します。
     * 
     * @param lastName
     *            ユーザー姓
     * @return UserEntity
     */
    @SuppressWarnings("unchecked")
    public List<UserEntity> selectByLastName(String lastName) {
        Query query = em().createNamedQuery(UserEntity.SELECT_BY_LAST_NAME);
        query.setParameter("lastName", lastName);
        return query.getResultList();
    }
}
