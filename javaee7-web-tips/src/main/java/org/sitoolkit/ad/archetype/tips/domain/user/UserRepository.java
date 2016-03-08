package org.sitoolkit.ad.archetype.tips.domain.user;

import org.sitoolkit.ad.archetype.tips.infrastructure.data.jpa.BaseRepository;
import org.sitoolkit.ad.archetype.tips.infrastructure.layer.Repository;

/**
 * このクラスは、ユーザーマスターのDAOです。
 * 
 * @author SIToolkit
 */
@Repository
public class UserRepository extends BaseRepository<UserEntity, String> {

    /**
     * 
     */
    private static final long serialVersionUID = 2225666869914977661L;

    @Override
    public Class<UserEntity> getEntityType() {
        return UserEntity.class;
    }

}
