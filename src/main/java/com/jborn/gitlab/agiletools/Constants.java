package com.jborn.gitlab.agiletools;

public interface Constants {
    String GLOBAL_PREFIX = "/";

    interface UserApi {
        String USER_API_PREFIX = GLOBAL_PREFIX + "user/";

        String USER_DETAILS = USER_API_PREFIX + "info";
    }

    interface Projects {
        String PROJECTS_API_PREFIX = GLOBAL_PREFIX + "projects/";

        String PROJECTS_LIST = PROJECTS_API_PREFIX + "list";
    }

    interface WebHooksApi {
        String WH_PREFIX = GLOBAL_PREFIX + "wh/";

        String WEB_HOOK = WH_PREFIX;
    }
}
