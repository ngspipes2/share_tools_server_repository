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
import pt.isel.ngspipes.share_tools_server_repository.logic.domain.ToolsRepositoryUserMember;
import pt.isel.ngspipes.share_tools_server_repository.logic.service.PermissionService;
import pt.isel.ngspipes.share_tools_server_repository.logic.service.repositoryUserMember.IToolsRepositoryUserMemberService;
import pt.isel.ngspipes.share_tools_server_repository.serviceInterface.controller.facade.IToolsRepositoryUserMemberController;

import java.util.Collection;

@RestController
public class ToolsRepositoryUserMemberController implements IToolsRepositoryUserMemberController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private ICurrentUserSupplier currentUserSupplier;
    @Autowired
    private IToolsRepositoryUserMemberService userMemberService;


    @Override
    public ResponseEntity<Collection<ToolsRepositoryUserMember>> getAllMembers() throws Exception {
        if(!isValidAccess(Access.Operation.GET, null))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Collection<ToolsRepositoryUserMember> members = userMemberService.getAll();

        hidePasswords(members);

        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ToolsRepositoryUserMember> getMember(@PathVariable int memberId) throws Exception {
        if(!isValidAccess(Access.Operation.GET, memberId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        ToolsRepositoryUserMember member = userMemberService.getById(memberId);

        if(member != null)
            hidePasswords(member);

        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> insertMember(@RequestBody ToolsRepositoryUserMember member) throws Exception {
        if(!isValidInsertAccess(member.getRepository().getId()))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        userMemberService.insert(member);

        return new ResponseEntity<>(member.getId(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> updateMember(@RequestBody ToolsRepositoryUserMember member, @PathVariable int memberId) throws Exception {
        if(member.getId() != memberId)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(!isValidAccess(Access.Operation.UPDATE, memberId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        userMemberService.update(member);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteMember(@PathVariable int memberId) throws Exception {
        if(!isValidAccess(Access.Operation.DELETE, memberId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        userMemberService.delete(memberId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Collection<ToolsRepositoryUserMember>> getMembersWithUser(@PathVariable String userName) throws ServiceException {
        if(!isValidAccess(Access.Operation.GET, null))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Collection<ToolsRepositoryUserMember> members = userMemberService.getMembersWithUser(userName);

        hidePasswords(members);

        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Collection<ToolsRepositoryUserMember>> getMembersOfRepository(@PathVariable int repositoryId) throws ServiceException {
        if(!isValidAccess(Access.Operation.GET, null))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Collection<ToolsRepositoryUserMember> members = userMemberService.getMembersOfRepository(repositoryId);

        hidePasswords(members);

        return new ResponseEntity<>(members, HttpStatus.OK);
    }


    private boolean isValidAccess(Access.Operation operation, Integer memberId) throws ServiceException {
        User currentUser = currentUserSupplier.get();

        Access access = new Access();
        access.userName = currentUser == null ? null : currentUser.getUserName();
        access.operation = operation;
        access.entity = ToolsRepositoryUserMember.class;
        access.entityId = memberId == null ? null : Integer.toString(memberId);

        return permissionService.isValid(access);
    }

    private boolean isValidInsertAccess(Integer repositoryId) throws ServiceException {
        User currentUser = currentUserSupplier.get();

        Access access = new Access();
        access.userName = currentUser == null ? null : currentUser.getUserName();
        access.operation = Access.Operation.UPDATE;
        access.entity = ToolsRepositoryMetadata.class;
        access.entityId = repositoryId == null ? null : Integer.toString(repositoryId);

        return permissionService.isValid(access);
    }

    private void hidePasswords(Collection<ToolsRepositoryUserMember> members) {
        for(ToolsRepositoryUserMember member : members)
            hidePasswords(member);
    }

    private void hidePasswords(ToolsRepositoryUserMember member) {
        member.getUser().setPassword("");
    }

}
