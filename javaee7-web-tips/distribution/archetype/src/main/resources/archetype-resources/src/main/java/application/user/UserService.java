#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.user;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import ${package}.domain.user.UserRepository;
import ${package}.domain.user.UserEntity;
import ${package}.infrastructure.data.jpa.BaseRepository;
import ${package}.infrastructure.entitycrud.BaseService;

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
