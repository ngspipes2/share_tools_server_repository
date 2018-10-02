package pt.isel.ngspipes.share_tools_server_repository.dataAccess.repositoryMetadataRepository;

import pt.isel.ngspipes.share_core.dataAccess.PostgresRepository;
import pt.isel.ngspipes.share_core.dataAccess.RepositoryException;
import pt.isel.ngspipes.share_tools_server_repository.logic.domain.ToolsRepositoryMetadata;

import java.util.Collection;
import java.util.stream.Collectors;

@org.springframework.stereotype.Repository
public class ToolsRepositoryMetadataRepository extends PostgresRepository<ToolsRepositoryMetadata, Integer> implements IToolsRepositoryMetadataRepository {

    public ToolsRepositoryMetadataRepository() {
        super(ToolsRepositoryMetadata.class);
    }



    @Override
    protected void setId(ToolsRepositoryMetadata repository, Integer id) {
        repository.setId(id);
    }

    @Override
    protected Integer getId(ToolsRepositoryMetadata repository) {
        return repository.getId();
    }

    @Override
    protected void merge(ToolsRepositoryMetadata repository, ToolsRepositoryMetadata repositoryToUpdate) {
        repositoryToUpdate.setId(repository.getId());
        repositoryToUpdate.setName(repository.getName());
        repositoryToUpdate.setDescription(repository.getDescription());
        repositoryToUpdate.setCreationDate(repository.getCreationDate());
        repositoryToUpdate.setPublic(repository.getIsPublic());
        repositoryToUpdate.setOwner(repository.getOwner());
    }

    @Override
    public Collection<ToolsRepositoryMetadata> getRepositoriesOfUser(String userName) throws RepositoryException {
        return getAll().stream().filter((repository) -> repository.getOwner().getUserName().equals(userName)).collect(Collectors.toList());
    }

}
