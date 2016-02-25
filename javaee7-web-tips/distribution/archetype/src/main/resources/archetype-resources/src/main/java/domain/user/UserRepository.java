#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain.user;

import ${package}.infrastructure.data.jpa.BaseRepository;
import ${package}.infrastructure.layer.Repository;

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
