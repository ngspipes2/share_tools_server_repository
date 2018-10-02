package pt.isel.ngspipes.share_tools_server_repository.serviceInterface.controller.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import pt.isel.ngspipes.share_authentication_server.logic.domain.AccessToken;
import pt.isel.ngspipes.share_authentication_server.logic.domain.GroupMember;
import pt.isel.ngspipes.share_authentication_server.logic.domain.User;
import pt.isel.ngspipes.share_authentication_server.logic.service.PermissionService;
import pt.isel.ngspipes.share_authentication_server.logic.service.accessToken.IAccessTokenService;
import pt.isel.ngspipes.share_authentication_server.logic.service.groupMember.IGroupMemberService;
import pt.isel.ngspipes.share_core.logic.service.IService;
import pt.isel.ngspipes.share_core.logic.service.exceptions.ServiceException;
import pt.isel.ngspipes.share_tools_server_repository.logic.domain.ToolsRepositoryGroupMember;
import pt.isel.ngspipes.share_tools_server_repository.logic.domain.ToolsRepositoryMetadata;
import pt.isel.ngspipes.share_tools_server_repository.logic.domain.ToolsRepositoryUserMember;
import pt.isel.ngspipes.share_tools_server_repository.logic.service.repositoryGroupMember.IToolsRepositoryGroupMemberService;
import pt.isel.ngspipes.share_tools_server_repository.logic.service.repositoryMetadata.IToolsRepositoryMetadataService;
import pt.isel.ngspipes.share_tools_server_repository.logic.service.repositoryService.IToolsRepositoryService;
import pt.isel.ngspipes.share_tools_server_repository.logic.service.repositoryUserMember.IToolsRepositoryUserMemberService;
import pt.isel.ngspipes.share_tools_server_repository.serviceInterface.controller.facade.IToolsRepositoryServerController;
import pt.isel.ngspipes.tool_descriptor.interfaces.IToolDescriptor;
import pt.isel.ngspipes.tool_repository.interfaces.IToolsRepository;

import java.util.Base64;
import java.util.Collection;

@RestController
public class ToolsRepositoryServerController implements IToolsRepositoryServerController {

    @Autowired
    private IToolsRepositoryMetadataService repositoryMetadataService;
    @Autowired
    private IToolsRepositoryService repositoryService;
    @Autowired
    private IService<User, String> userService;
    @Autowired
    private IAccessTokenService tokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IToolsRepositoryUserMemberService repositoryUserMemberService;
    @Autowired
    private IToolsRepositoryGroupMemberService repositoryGroupMemberService;
    @Autowired
    private IGroupMemberService groupMemberService;



    @Override
    public ResponseEntity<Collection<IToolDescriptor>> getAll(@PathVariable int repositoryId, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception {
        if(!validateAccess(repositoryId, PermissionService.Access.Operation.GET, authHeader))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        IToolsRepository repository = repositoryService.getRepository(repositoryId);
        return new ResponseEntity<>(repository.getAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<IToolDescriptor> get(@PathVariable int repositoryId, @PathVariable String toolName, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception {
        if(!validateAccess(repositoryId, PermissionService.Access.Operation.GET, authHeader))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        IToolsRepository repository = repositoryService.getRepository(repositoryId);
        return new ResponseEntity<>(repository.get(toolName), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> insert(@PathVariable int repositoryId, @RequestBody IToolDescriptor tool, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception {
        if(!validateAccess(repositoryId, PermissionService.Access.Operation.INSERT, authHeader))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        IToolsRepository repository = repositoryService.getRepository(repositoryId);
        repository.insert(tool);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> update(@PathVariable int repositoryId, @RequestBody IToolDescriptor tool, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception {
        if(!validateAccess(repositoryId, PermissionService.Access.Operation.UPDATE, authHeader))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        IToolsRepository repository = repositoryService.getRepository(repositoryId);
        repository.update(tool);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable int repositoryId, @PathVariable String toolName, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception {
        if(!validateAccess(repositoryId, PermissionService.Access.Operation.DELETE, authHeader))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        IToolsRepository repository = repositoryService.getRepository(repositoryId);
        repository.delete(toolName);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    private boolean validateAccess(int repositoryId, PermissionService.Access.Operation operation, String authHeader) throws Exception {
        ToolsRepositoryMetadata repository = repositoryMetadataService.getById(repositoryId);

        AccessToken token = getCurrentToken(authHeader);
        User user = token == null ? getCurrentUser(authHeader) : token.getOwner();

        return validateAccess(operation, repository, user, token);
    }

    private boolean validateAccess(PermissionService.Access.Operation operation, ToolsRepositoryMetadata repository, User user, AccessToken token) throws Exception {
        if(operation.equals(PermissionService.Access.Operation.GET))
            return validateReadAccess(repository, user, token);
        else
            return validateWriteAccess(repository, user, token);
    }

    private boolean validateReadAccess(ToolsRepositoryMetadata repository, User user, AccessToken token) throws Exception {
        if(repository.getIsPublic())
            return true;

        if(user == null)
            throw new AuthenticationCredentialsNotFoundException("Not allowed!");

        if(repository.getOwner().getUserName().equals(user.getUserName()))
            return true;

        for(ToolsRepositoryUserMember member : repositoryUserMemberService.getMembersOfRepository(repository.getId()))
            if(member.getUser().getUserName().equals(user.getUserName()))
                return true;

        for(ToolsRepositoryGroupMember member : repositoryGroupMemberService.getMembersOfRepository(repository.getId()))
            for(GroupMember m : groupMemberService.getMembersOfGroup(member.getGroup().getGroupName()))
                if(m.getUser().getUserName().equals(user.getUserName()))
                    return true;

        return false;
    }

    private boolean validateWriteAccess(ToolsRepositoryMetadata repository, User user, AccessToken token) throws ServiceException {
        if(user == null)
            throw new AuthenticationCredentialsNotFoundException("Not allowed!");

        if(token != null && !token.getWriteAccess())
            return false;

        if(repository.getOwner().getUserName().equals(user.getUserName()))
            return true;

        for(ToolsRepositoryUserMember member : repositoryUserMemberService.getMembersOfRepository(repository.getId()))
            if(member.getUser().getUserName().equals(user.getUserName()) && member.getWriteAccess())
                return true;

        for(ToolsRepositoryGroupMember member : repositoryGroupMemberService.getMembersOfRepository(repository.getId())) {
            if(!member.getWriteAccess())
                continue;

            for(GroupMember m : groupMemberService.getMembersOfGroup(member.getGroup().getGroupName()))
                if(m.getUser().getUserName().equals(user.getUserName()) && member.getWriteAccess())
                    return true;
        }

        return false;
    }

    private User getCurrentUser(String authHeader) throws Exception {
        if(authHeader == null || authHeader.isEmpty())
            return null;

        authHeader = authHeader.replace("Basic", "");
        authHeader = new String(Base64.getDecoder().decode(authHeader));

        if(!authHeader.contains(":"))
            return null;

        String userName = authHeader.split(":")[0];
        String password = authHeader.split(":")[1];

        User user = userService.getById(userName);

        if(user == null)
            return null;

        if(!passwordEncoder.matches(password, user.getPassword()))
            throw new BadCredentialsException("Invalid credentials!");

        return user;
    }

    private AccessToken getCurrentToken(String authHeader) throws Exception {
        if(authHeader == null || authHeader.isEmpty())
            return null;

        authHeader = authHeader.replace("Bearer", "");

        AccessToken token = tokenService.getTokensByToken(authHeader);

        return token;
    }

}
