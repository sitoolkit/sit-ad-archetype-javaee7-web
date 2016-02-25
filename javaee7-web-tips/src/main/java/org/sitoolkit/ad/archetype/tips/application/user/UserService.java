package org.sitoolkit.ad.archetype.tips.application.user;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import org.sitoolkit.ad.archetype.tips.domain.user.UserRepository;
import org.sitoolkit.ad.archetype.tips.domain.user.UserEntity;
import org.sitoolkit.ad.archetype.tips.infrastructure.data.jpa.BaseRepository;
import org.sitoolkit.ad.archetype.tips.infrastructure.entitycrud.BaseService;

/**
 * このクラスは、ユーザーマスターのサービスです。
 * 
 * @author
 **/
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserService extends BaseService<UserEntity, String> {

    @Inject
    UserRepository dao;

    @Override
    public BaseRepository<UserEntity, String> getDao() {
        return dao;
    }

}
