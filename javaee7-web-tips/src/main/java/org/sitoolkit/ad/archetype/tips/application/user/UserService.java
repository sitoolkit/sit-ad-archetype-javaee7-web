package org.sitoolkit.ad.archetype.tips.application.user;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.sitoolkit.ad.archetype.tips.domain.user.UserEntity;
import org.sitoolkit.ad.archetype.tips.domain.user.UserRepository;
import org.sitoolkit.ad.archetype.tips.infrastructure.data.jpa.BaseRepository;
import org.sitoolkit.ad.archetype.tips.infrastructure.entitycrud.BaseService;

/**
 * このクラスは、ユーザーマスターのサービスです。
 * 
 * @author SIToolkit
 **/
@ApplicationScoped
@Transactional
public class UserService extends BaseService<UserEntity, String> {

    @Inject
    UserRepository dao;

    @Override
    public BaseRepository<UserEntity, String> getDao() {
        return dao;
    }

}
