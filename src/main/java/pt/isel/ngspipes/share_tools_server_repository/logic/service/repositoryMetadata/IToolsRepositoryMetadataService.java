package pt.isel.ngspipes.share_tools_server_repository.logic.service.repositoryMetadata;

import pt.isel.ngspipes.share_core.logic.service.IService;
import pt.isel.ngspipes.share_core.logic.service.exceptions.ServiceException;
import pt.isel.ngspipes.share_tools_server_repository.logic.domain.ToolsRepositoryMetadata;

import java.util.Collection;

public interface IToolsRepositoryMetadataService extends IService<ToolsRepositoryMetadata, Integer> {

    Collection<ToolsRepositoryMetadata> getToolsRepositoriesOfUser(String userName) throws ServiceException;

}
