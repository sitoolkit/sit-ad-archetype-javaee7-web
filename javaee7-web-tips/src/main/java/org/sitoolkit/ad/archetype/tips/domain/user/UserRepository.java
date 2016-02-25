package org.sitoolkit.ad.archetype.tips.domain.user;

import org.sitoolkit.ad.archetype.tips.infrastructure.data.jpa.BaseRepository;
import org.sitoolkit.ad.archetype.tips.infrastructure.layer.Repository;

/**
 * このクラスは、ユーザーマスターのDAOです。
 * 
 * @author
 */
@Repository
public class UserRepository extends BaseRepository<UserEntity, String> {

    @Override
    public Class<UserEntity> getEntityType() {
        return UserEntity.class;
    }

}
