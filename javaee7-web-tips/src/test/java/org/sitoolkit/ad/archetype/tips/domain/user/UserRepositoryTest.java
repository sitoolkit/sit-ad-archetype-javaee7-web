package org.sitoolkit.ad.archetype.tips.domain.user;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sitoolkit.ad.archetype.tips.infrastructure.data.jpa.RollbackRule;

@RunWith(CdiTestRunner.class)
public class UserRepositoryTest {

    @Inject
    UserRepository repo;

    @Inject
    @Rule
    public RollbackRule rollbackRule;

    @Test
    public void testSelectByLastName() {
        UserEntity user = UserFactory.create();
        user.setLastName(RandomStringUtils.randomAlphanumeric(32));

        repo.create(user);
        repo.em().flush();

        List<UserEntity> result = repo.selectByLastName(user.getLastName());

        assertThat("stored user last name", result.get(0).getLastName(), is(user.getLastName()));
    }

}
