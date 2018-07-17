package pt.isel.ngspipes.share_tools_server_repository.serviceInterface.controller.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pt.isel.ngspipes.share_core.logic.domain.Group;
import pt.isel.ngspipes.share_core.logic.domain.ToolsRepository;
import pt.isel.ngspipes.share_core.logic.domain.User;
import pt.isel.ngspipes.share_core.logic.service.ICurrentUserSupplier;
import pt.isel.ngspipes.share_core.logic.service.PermissionService;
import pt.isel.ngspipes.share_core.logic.service.PermissionService.Access;
import pt.isel.ngspipes.share_core.logic.service.exceptions.ServiceException;
import pt.isel.ngspipes.share_core.logic.service.toolsRepository.ToolsRepositoryService;
import pt.isel.ngspipes.share_tools_server_repository.serviceInterface.controller.facade.IRepositoryController;

import java.util.Collection;

@RestController
public class RepositoryController implements IRepositoryController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private ICurrentUserSupplier currentUserSupplier;
    @Autowired
    private ToolsRepositoryService repositoryService;



    public ResponseEntity<Collection<ToolsRepository>> getAllRepositories() throws Exception {
        if(!isValidAccess(Access.Operation.GET, null))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Collection<ToolsRepository> repositories = repositoryService.getAll();

        hidePasswords(repositories);

        return new ResponseEntity<>(repositories, HttpStatus.OK);
    }

    public ResponseEntity<ToolsRepository> getRepository(@PathVariable int repositoryId) throws Exception {
        if(!isValidAccess(Access.Operation.GET, repositoryId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        ToolsRepository repository = repositoryService.getById(repositoryId);

        if(repository != null)
            hidePasswords(repository);

        return new ResponseEntity<>(repository, HttpStatus.OK);
    }

    public ResponseEntity<Integer> insertRepository(@RequestBody ToolsRepository repository) throws Exception {
        if(!isValidAccess(Access.Operation.INSERT, repository.getId()))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        repositoryService.insert(repository);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<Void> updateRepository(@RequestBody ToolsRepository repository, @PathVariable int repositoryId) throws Exception {
        if(repository.getId() != repositoryId)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(!isValidAccess(Access.Operation.UPDATE, repositoryId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        repositoryService.update(repository);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteRepository(@PathVariable int repositoryId) throws Exception {
        if(!isValidAccess(Access.Operation.DELETE, repositoryId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        repositoryService.delete(repositoryId);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    private boolean isValidAccess(Access.Operation operation, Integer repositoryId) throws ServiceException {
        User currentUser = currentUserSupplier.get();

        Access access = new Access();
        access.userName = currentUser == null ? null : currentUser.getUserName();
        access.operation = operation;
        access.entity = ToolsRepository.class;
        access.entityId = Integer.toString(repositoryId);

        return permissionService.isValid(access);
    }

    private void hidePasswords(Collection<ToolsRepository> repositories) {
        for(ToolsRepository repository : repositories)
            hidePasswords(repository);
    }

    private void hidePasswords(ToolsRepository repository) {
        repository.getOwner().setPassword("");

        for(User user : repository.getUsersAccess())
            user.setPassword("");

        for(Group group : repository.getGroupAccess())
            for(User member : group.getMembers())
                member.setPassword("");
    }

}
