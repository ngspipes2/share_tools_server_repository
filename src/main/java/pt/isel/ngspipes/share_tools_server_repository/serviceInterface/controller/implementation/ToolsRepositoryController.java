package pt.isel.ngspipes.share_tools_server_repository.serviceInterface.controller.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pt.isel.ngspipes.share_authentication_server.logic.domain.User;
import pt.isel.ngspipes.share_authentication_server.logic.service.ICurrentUserSupplier;
import pt.isel.ngspipes.share_authentication_server.logic.service.PermissionService.Access;
import pt.isel.ngspipes.share_core.logic.service.exceptions.ServiceException;
import pt.isel.ngspipes.share_tools_server_repository.logic.domain.ToolsRepositoryMetadata;
import pt.isel.ngspipes.share_tools_server_repository.logic.service.PermissionService;
import pt.isel.ngspipes.share_tools_server_repository.logic.service.repositoryMetadata.IToolsRepositoryMetadataService;
import pt.isel.ngspipes.share_tools_server_repository.logic.service.repositoryService.IToolsRepositoryService;
import pt.isel.ngspipes.share_tools_server_repository.serviceInterface.controller.facade.IToolsRepositoryController;

import java.util.Collection;

@RestController
public class ToolsRepositoryController implements IToolsRepositoryController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private ICurrentUserSupplier currentUserSupplier;
    @Autowired
    private IToolsRepositoryMetadataService repositoryMetadataService;
    @Autowired
    private IToolsRepositoryService repositoryService;



    public ResponseEntity<Collection<ToolsRepositoryMetadata>> getAllRepositories() throws Exception {
        if(!isValidAccess(Access.Operation.GET, null))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Collection<ToolsRepositoryMetadata> repositories = repositoryMetadataService.getAll();

        hidePasswords(repositories);

        return new ResponseEntity<>(repositories, HttpStatus.OK);
    }

    public ResponseEntity<ToolsRepositoryMetadata> getRepository(@PathVariable int repositoryId) throws Exception {
        if(!isValidAccess(Access.Operation.GET, repositoryId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        ToolsRepositoryMetadata repository = repositoryMetadataService.getById(repositoryId);

        if(repository != null)
            hidePasswords(repository);

        return new ResponseEntity<>(repository, HttpStatus.OK);
    }

    public ResponseEntity<Integer> insertRepository(@RequestBody ToolsRepositoryMetadata repository) throws Exception {
        if(!isValidAccess(Access.Operation.INSERT, repository.getId()))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        repositoryService.createRepository(repository);

        return new ResponseEntity<>(repository.getId(), HttpStatus.CREATED);
    }

    public ResponseEntity<Void> updateRepository(@RequestBody ToolsRepositoryMetadata repository, @PathVariable int repositoryId) throws Exception {
        if(repository.getId() != repositoryId)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(!isValidAccess(Access.Operation.UPDATE, repositoryId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        repositoryMetadataService.update(repository);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteRepository(@PathVariable int repositoryId) throws Exception {
        if(!isValidAccess(Access.Operation.DELETE, repositoryId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        repositoryService.deleteRepository(repositoryId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Collection<ToolsRepositoryMetadata>> getToolsRepositoriesOfUser(@PathVariable String userName) throws Exception {
        if(!isValidAccess(Access.Operation.GET, null))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Collection<ToolsRepositoryMetadata> repositories = repositoryMetadataService.getToolsRepositoriesOfUser(userName);

        hidePasswords(repositories);

        return new ResponseEntity<>(repositories, HttpStatus.OK);
    }


    private boolean isValidAccess(Access.Operation operation, Integer repositoryId) throws ServiceException {
        User currentUser = currentUserSupplier.get();

        Access access = new Access();
        access.userName = currentUser == null ? null : currentUser.getUserName();
        access.operation = operation;
        access.entity = ToolsRepositoryMetadata.class;
        access.entityId = repositoryId == null ? null : Integer.toString(repositoryId);

        return permissionService.isValid(access);
    }

    private void hidePasswords(Collection<ToolsRepositoryMetadata> repositories) {
        for(ToolsRepositoryMetadata repository : repositories)
            hidePasswords(repository);
    }

    private void hidePasswords(ToolsRepositoryMetadata repository) {
        repository.getOwner().setPassword("");
    }

}
