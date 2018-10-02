package pt.isel.ngspipes.share_tools_server_repository.logic.service.repositoryMetadata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ngspipes.share_authentication_server.logic.service.ICurrentUserSupplier;
import pt.isel.ngspipes.share_core.dataAccess.RepositoryException;
import pt.isel.ngspipes.share_core.logic.service.Service;
import pt.isel.ngspipes.share_core.logic.service.ServiceUtils;
import pt.isel.ngspipes.share_core.logic.service.exceptions.ServiceException;
import pt.isel.ngspipes.share_tools_server_repository.dataAccess.repositoryMetadataRepository.IToolsRepositoryMetadataRepository;
import pt.isel.ngspipes.share_tools_server_repository.logic.domain.ToolsRepositoryMetadata;

import java.util.Collection;
import java.util.Date;

@org.springframework.stereotype.Service
public class ToolsRepositoryMetadataService extends Service<ToolsRepositoryMetadata, Integer> implements IToolsRepositoryMetadataService {

    @Autowired
    private IToolsRepositoryMetadataRepository toolsRepositoryRepository;
    @Autowired
    private ICurrentUserSupplier currentUserSupplier;



    protected ToolsRepositoryMetadataService() {
        super("RepositoriesMetadata", "ToolsRepositoryMetadata");
    }



    @Override
    @Transactional
    public void insert(ToolsRepositoryMetadata repository) throws ServiceException {
        repository.setCreationDate(new Date());
        repository.setOwner(currentUserSupplier.get());

        super.insert(repository);
    }

    @Override
    protected void validateInsert(ToolsRepositoryMetadata repository) throws ServiceException { }

    @Override
    protected void validateDelete(Integer id) throws ServiceException { }

    @Override
    protected void validateUpdate(ToolsRepositoryMetadata repository) throws ServiceException {
        ToolsRepositoryMetadata savedRepository = getById(repository.getId());

        if(repository != null) {
            if(!ServiceUtils.sameDate(savedRepository.getCreationDate(), repository.getCreationDate()))
                throw new ServiceException("ToolsRepositoryMetadata's creationDate Cannot be changed!");

            if(!savedRepository.getOwner().getUserName().equals(repository.getOwner().getUserName()))
                throw new ServiceException("ToolsRepositoryMetadata's owner Cannot be changed!");
        }
    }

    @Override
    protected Integer getId(ToolsRepositoryMetadata repository) throws ServiceException {
        return repository.getId();
    }

    @Override
    public Collection<ToolsRepositoryMetadata> getToolsRepositoriesOfUser(String userName) throws ServiceException {
        try {
            return toolsRepositoryRepository.getRepositoriesOfUser(userName);
        } catch (RepositoryException e) {
            throw new ServiceException("Error getting Repositories of User!");
        }
    }

}
