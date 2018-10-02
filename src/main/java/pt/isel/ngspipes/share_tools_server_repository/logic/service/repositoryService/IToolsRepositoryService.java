package pt.isel.ngspipes.share_tools_server_repository.logic.service.repositoryService;

import pt.isel.ngspipes.share_core.logic.service.exceptions.ServiceException;
import pt.isel.ngspipes.share_tools_server_repository.logic.domain.ToolsRepositoryMetadata;
import pt.isel.ngspipes.tool_repository.interfaces.IToolsRepository;

public interface IToolsRepositoryService {

    void createRepository(ToolsRepositoryMetadata repo) throws ServiceException;

    void deleteRepository(int id) throws ServiceException;

    IToolsRepository getRepository(int id) throws ServiceException;

}
