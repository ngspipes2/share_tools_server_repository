package pt.isel.ngspipes.share_tools_server_repository.serviceInterface.config;

public class Routes {

    public static final String TOOLS_REPOSITORIES = "/toolsrepositories";
    public static final String GET_ALL_TOOLS_REPOSITORIES = TOOLS_REPOSITORIES;
    public static final String GET_TOOLS_REPOSITORY = TOOLS_REPOSITORIES + "/{repositoryId}";
    public static final String INSERT_TOOLS_REPOSITORY = TOOLS_REPOSITORIES;
    public static final String UPDATE_TOOLS_REPOSITORY = TOOLS_REPOSITORIES + "/{repositoryId}";
    public static final String DELETE_TOOLS_REPOSITORY = TOOLS_REPOSITORIES + "/{repositoryId}";
    public static final String GET_TOOLS_REPOSITORIES_OF_USER = "/toolsrepositories/owner/{userName}";

    public static final String IMAGES_URI = "/images";

    public static final String TOOLS_REPOSITORY_IMAGE = IMAGES_URI + "/toolsRepository";
    public static final String GET_TOOLS_REPOSITORY_IMAGE = TOOLS_REPOSITORY_IMAGE + "/{repositoryId}";
    public static final String UPDATE_TOOLS_REPOSITORY_IMAGE = TOOLS_REPOSITORY_IMAGE + "/{repositoryId}";
    public static final String DELETE_TOOLS_REPOSITORY_IMAGE = TOOLS_REPOSITORY_IMAGE + "/{repositoryId}";

}
