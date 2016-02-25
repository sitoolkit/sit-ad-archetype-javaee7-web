#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.presentation;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ${package}.application.user.UserService;
import ${package}.domain.user.UserEntity;
import ${package}.infrastructure.entitycrud.EntityListController;
import ${package}.infrastructure.search.SearchConditionDo;

@Named
@RequestScoped
public class UserListController extends EntityListController<UserEntity, String, UserService> {

    @Inject
    UserService service;

    @Inject
    SearchConditionDo condition;

    @Override
    protected UserService getService() {
        return service;
    }
}
