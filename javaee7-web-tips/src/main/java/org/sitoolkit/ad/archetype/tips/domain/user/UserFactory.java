package org.sitoolkit.ad.archetype.tips.domain.user;

import org.apache.commons.lang3.RandomStringUtils;

public class UserFactory {

    /**
     * 初期化したUserEntityを返します。
     * 
     * @return UserEntity
     */
    public static UserEntity create() {
        UserEntity user = new UserEntity();
        user.setUserId(RandomStringUtils.randomAlphanumeric(10));
        user.setPassword(RandomStringUtils.randomAlphanumeric(128));
        return user;
    }
}
