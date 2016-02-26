package org.sitoolkit.ad.archetype.tips.presentation;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.sitoolkit.ad.archetype.tips.application.user.UserService;
import org.sitoolkit.ad.archetype.tips.domain.user.UserEntity;
import org.sitoolkit.ad.archetype.tips.infrastructure.entitycrud.EntityListController;

@Named
@RequestScoped
public class UserListController extends EntityListController<UserEntity, String, UserService> {

    @Inject
    UserService service;

    @Override
    protected UserService getService() {
        return service;
    }
}
