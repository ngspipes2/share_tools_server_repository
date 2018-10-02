package pt.isel.ngspipes.share_tools_server_repository.dataAccess.repositoryMetadataRepository;

import pt.isel.ngspipes.share_core.dataAccess.IRepository;
import pt.isel.ngspipes.share_core.dataAccess.RepositoryException;
import pt.isel.ngspipes.share_tools_server_repository.logic.domain.ToolsRepositoryMetadata;

import java.util.Collection;

public interface IToolsRepositoryMetadataRepository extends IRepository<ToolsRepositoryMetadata, Integer> {

    Collection<ToolsRepositoryMetadata> getRepositoriesOfUser(String userName) throws RepositoryException;

}
