package cz.zlounym.shareit.configuration.auth;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class RoleConstants {

    private static final String ROLE_PREFIX = "ROLE_";

    public static final String ROLE_USER = ROLE_PREFIX + "USER";
    public static final String ROLE_SALES = ROLE_PREFIX + "SALES";
    public static final String ROLE_DEMO = ROLE_PREFIX + "DEMO";

    public static final String ROLE_ADMIN = ROLE_PREFIX + "ADMINISTRATOR";
    public static final String ROLE_CUSTOMER_CARE = ROLE_PREFIX + "CUSTOMER_SUPPORT";
    public static final String ROLE_DEVELOPER = ROLE_PREFIX + "DEVELOPER";

}
