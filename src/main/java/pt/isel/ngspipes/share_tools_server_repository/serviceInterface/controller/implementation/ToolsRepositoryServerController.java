package pt.isel.ngspipes.share_tools_server_repository.serviceInterface.controller.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import pt.isel.ngspipes.share_core.logic.domain.AccessToken;
import pt.isel.ngspipes.share_core.logic.domain.RepositoryInfo;
import pt.isel.ngspipes.share_core.logic.domain.User;
import pt.isel.ngspipes.share_core.logic.service.accessToken.IAccessTokenService;
import pt.isel.ngspipes.share_core.logic.service.exceptions.NonExistentEntityException;
import pt.isel.ngspipes.share_core.logic.service.exceptions.ServiceException;
import pt.isel.ngspipes.share_core.logic.service.permission.Access;
import pt.isel.ngspipes.share_core.logic.service.permission.IRepositoryPermissionService;
import pt.isel.ngspipes.share_core.logic.service.repositoryInfo.IRepositoryInfoService;
import pt.isel.ngspipes.share_core.logic.service.repositoryInfo.IRepositoryService;
import pt.isel.ngspipes.share_core.logic.service.user.IUserService;
import pt.isel.ngspipes.share_tools_server_repository.serviceInterface.controller.facade.IToolsRepositoryServerController;
import pt.isel.ngspipes.tool_descriptor.interfaces.IToolDescriptor;
import pt.isel.ngspipes.tool_repository.interfaces.IToolsRepository;

import java.util.Base64;
import java.util.Collection;

@RestController
public class ToolsRepositoryServerController implements IToolsRepositoryServerController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IAccessTokenService tokenService;
    @Autowired
    private IRepositoryService repositoryService;
    @Autowired
    private IRepositoryInfoService repositoryInfoService;
    @Autowired
    private IRepositoryPermissionService permissionService;



    @Override
    public ResponseEntity<byte[]> getLogo(@PathVariable String repositoryName, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception {
        if(!validAccess(repositoryName, authHeader, Access.Operation.GET))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        IToolsRepository repository = getRepository(repositoryName);

        return new ResponseEntity<>(repository.getLogo(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> setLogo(@PathVariable String repositoryName, @RequestHeader(value = "Authorization", required = false) String authHeader, @RequestBody(required = false) byte[] logo) throws Exception {
        if(!validAccess(repositoryName, authHeader, Access.Operation.UPDATE))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        IToolsRepository repository = getRepository(repositoryName);
        repository.setLogo(logo);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Collection<IToolDescriptor>> getAll(@PathVariable String repositoryName, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception {
        if(!validAccess(repositoryName, authHeader, Access.Operation.GET))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        IToolsRepository repository = getRepository(repositoryName);

        return new ResponseEntity<>(repository.getAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<IToolDescriptor> get(@PathVariable String repositoryName, @PathVariable String toolName, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception {
        if(!validAccess(repositoryName, authHeader, Access.Operation.GET))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        IToolsRepository repository = getRepository(repositoryName);

        return new ResponseEntity<>(repository.get(toolName), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> insert(@PathVariable String repositoryName, @RequestBody IToolDescriptor tool, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception {
        if(!validAccess(repositoryName, authHeader, Access.Operation.INSERT))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        IToolsRepository repository = getRepository(repositoryName);
        repository.insert(tool);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> update(@PathVariable String repositoryName, @RequestBody IToolDescriptor tool, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception {
        if(!validAccess(repositoryName, authHeader, Access.Operation.UPDATE))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        IToolsRepository repository = getRepository(repositoryName);
        repository.update(tool);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable String repositoryName, @PathVariable String toolName, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception {
        if(!validAccess(repositoryName, authHeader, Access.Operation.DELETE))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        IToolsRepository repository = getRepository(repositoryName);
        repository.delete(toolName);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    private boolean validAccess(String repositoryName, String authHeader, Access.Operation operations) throws Exception {
        AccessToken token = getToken(authHeader);
        User user = token == null ? getUser(authHeader) : token.getOwner();

        return permissionService.hasPermission(repositoryName, user, token, operations);
    }

    private IToolsRepository getRepository(String repositoryName) throws ServiceException {
        RepositoryInfo repositoryMetadata = repositoryInfoService.getById(repositoryName);

        if(repositoryMetadata == null)
            throw new NonExistentEntityException("There is no ToolsRepository with with:" + repositoryName);

        IToolsRepository repository = repositoryService.getToolsRepository(repositoryMetadata);

        if(repository == null)
            throw new NonExistentEntityException("There is no ToolsRepository with with:" + repositoryName);

        return repository;
    }

    private User getUser(String authHeader) throws Exception {
        if(authHeader == null || authHeader.isEmpty())
            return null;

        authHeader = authHeader.replace("Basic", "");
        authHeader = new String(Base64.getDecoder().decode(authHeader));

        if(!authHeader.contains(":"))
            return null;

        String userName = authHeader.split(":")[0];
        String password = authHeader.split(":")[1];

        return userService.getById(userName);
    }

    private AccessToken getToken(String authHeader) throws Exception {
        if(authHeader == null || authHeader.isEmpty())
            return null;

        authHeader = authHeader.replace("Bearer", "");

        AccessToken token = tokenService.getAccessTokenByToken(authHeader);

        return token;
    }

}
