package pt.isel.ngspipes.share_tools_server_repository.serviceInterface.config;

public class Routes {

    public static final String TOOLS_REPOSITORIES = "/toolsrepositories";
    public static final String GET_ALL_TOOLS_REPOSITORIES = TOOLS_REPOSITORIES;
    public static final String GET_TOOLS_REPOSITORY = TOOLS_REPOSITORIES + "/{repositoryId}";
    public static final String INSERT_TOOLS_REPOSITORY = TOOLS_REPOSITORIES;
    public static final String UPDATE_TOOLS_REPOSITORY = TOOLS_REPOSITORIES + "/{repositoryId}";
    public static final String DELETE_TOOLS_REPOSITORY = TOOLS_REPOSITORIES + "/{repositoryId}";
    public static final String GET_TOOLS_REPOSITORIES_OF_USER = "/toolsrepositories/owner/{userName}";

    public static final String USER_MEMBER_URI = "/userMember";
    public static final String GET_ALL_USER_MEMBERS = USER_MEMBER_URI;
    public static final String GET_USER_MEMBER = USER_MEMBER_URI + "/{memberId}";
    public static final String INSERT_USER_MEMBER = USER_MEMBER_URI;
    public static final String UPDATE_USER_MEMBER = USER_MEMBER_URI + "/{memberId}";
    public static final String DELETE_USER_MEMBER = USER_MEMBER_URI + "/{memberId}";
    public static final String GET_USER_MEMBERS_WITH_USER = USER_MEMBER_URI + "/user/{userName}";
    public static final String GET_USER_MEMBERS_OF_REPOSITORY = USER_MEMBER_URI + "/repository/{repositoryId}";

    public static final String GROUP_MEMBER_URI = "/groupMember";
    public static final String GET_ALL_GROUP_MEMBERS = GROUP_MEMBER_URI;
    public static final String GET_GROUP_MEMBER = GROUP_MEMBER_URI + "/{memberId}";
    public static final String INSERT_GROUP_MEMBER = GROUP_MEMBER_URI;
    public static final String UPDATE_GROUP_MEMBER = GROUP_MEMBER_URI + "/{memberId}";
    public static final String DELETE_GROUP_MEMBER = GROUP_MEMBER_URI + "/{memberId}";
    public static final String GET_GROUP_MEMBERS_WITH_GROUP = GROUP_MEMBER_URI + "/group/{groupName}";
    public static final String GET_GROUP_MEMBERS_OF_REPOSITORY = GROUP_MEMBER_URI + "/repository/{repositoryId}";

    public static final String IMAGES_URI = "/images";
    public static final String TOOLS_REPOSITORY_IMAGE = IMAGES_URI + "/toolsRepository";
    public static final String GET_TOOLS_REPOSITORY_IMAGE = TOOLS_REPOSITORY_IMAGE + "/{repositoryId}";
    public static final String UPDATE_TOOLS_REPOSITORY_IMAGE = TOOLS_REPOSITORY_IMAGE + "/{repositoryId}";
    public static final String DELETE_TOOLS_REPOSITORY_IMAGE = TOOLS_REPOSITORY_IMAGE + "/{repositoryId}";

    public static final String TOOLS_REPOSITORY_SERVER_URI = "/server";
    public static final String GET_ALL_TOOLS_REPOSITORY_SERVER_URI = TOOLS_REPOSITORY_SERVER_URI + "/{repositoryId}/tools";
    public static final String GET_TOOLS_REPOSITORY_SERVER_URI = TOOLS_REPOSITORY_SERVER_URI + "/{repositoryId}/tools/{toolName}";
    public static final String INSERT_TOOLS_REPOSITORY_SERVER_URI = TOOLS_REPOSITORY_SERVER_URI + "/{repositoryId}/tools";
    public static final String UPDATE_TOOLS_REPOSITORY_SERVER_URI = TOOLS_REPOSITORY_SERVER_URI + "/{repositoryId}/tools/{toolName}";
    public static final String DELETE_TOOLS_REPOSITORY_SERVER_URI = TOOLS_REPOSITORY_SERVER_URI + "/{repositoryId}/tools/{toolName}";

}
