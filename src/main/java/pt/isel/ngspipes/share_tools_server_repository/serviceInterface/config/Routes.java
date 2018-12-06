package pt.isel.ngspipes.share_tools_server_repository.serviceInterface.config;

public class Routes {

    public static final String TOOLS_REPOSITORY_SERVER_URI = "/server/{repositoryName}";

    public static final String GET_LOGO_URI = TOOLS_REPOSITORY_SERVER_URI + "/logo";
    public static final String SET_LOGO_URI = TOOLS_REPOSITORY_SERVER_URI + "/logo";
    public static final String GET_TOOLS_NAMES_URI = TOOLS_REPOSITORY_SERVER_URI + "/names";
    public static final String GET_ALL_TOOLS_URI = TOOLS_REPOSITORY_SERVER_URI + "/tools";
    public static final String GET_TOOL_URI = TOOLS_REPOSITORY_SERVER_URI + "/tools/{toolName}";
    public static final String INSERT_TOOL_URI = TOOLS_REPOSITORY_SERVER_URI + "/tools";
    public static final String UPDATE_TOOL_URI = TOOLS_REPOSITORY_SERVER_URI + "/tools/{toolName}";
    public static final String DELETE_TOOL_URI = TOOLS_REPOSITORY_SERVER_URI + "/tools/{toolName}";

}
