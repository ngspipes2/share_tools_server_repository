package pt.isel.ngspipes.share_tools_server_repository.logic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ngspipes.share_authentication_server.logic.domain.User;
import pt.isel.ngspipes.share_authentication_server.logic.service.PermissionService.Access;
import pt.isel.ngspipes.share_authentication_server.logic.service.group.IGroupService;
import pt.isel.ngspipes.share_core.logic.service.IService;
import pt.isel.ngspipes.share_core.logic.service.exceptions.ServiceException;
import pt.isel.ngspipes.share_tools_server_repository.logic.domain.ToolsRepositoryGroupMember;
import pt.isel.ngspipes.share_tools_server_repository.logic.domain.ToolsRepositoryMetadata;
import pt.isel.ngspipes.share_tools_server_repository.logic.domain.ToolsRepositoryUserMember;
import pt.isel.ngspipes.share_tools_server_repository.logic.service.repositoryGroupMember.IToolsRepositoryGroupMemberService;
import pt.isel.ngspipes.share_tools_server_repository.logic.service.repositoryMetadata.IToolsRepositoryMetadataService;
import pt.isel.ngspipes.share_tools_server_repository.logic.service.repositoryUserMember.IToolsRepositoryUserMemberService;

@Service
public class PermissionService {

    @Autowired
    private IService<User, String> userService;

    @Autowired
    private IGroupService groupService;

    @Autowired
    private IToolsRepositoryMetadataService toolsRepositoryService;

    @Autowired
    private IToolsRepositoryUserMemberService userMemberService;

    @Autowired
    private IToolsRepositoryGroupMemberService groupMemberService;



    @Transactional
    public boolean isValid(Access access) throws ServiceException {
        if(access.operation.equals(Access.Operation.GET))
            return true;

        User user = access.userName == null ? null : userService.getById(access.userName);
        if(user != null && user.getRole().equals(User.Role.ADMIN))
            return true;

        switch (access.entity.getSimpleName()) {
            case "ToolsRepositoryMetadata": return isValidToolsRepositoryAccess(access);
            case "ToolsRepositoryUserMember": return isValidUserMemberAccess(access);
            case "ToolsRepositoryGroupMember": return isValidGroupMemberAccess(access);
            default: throw new ServiceException("Unknown entity " + access.entity.getSimpleName() + "!");
        }
    }

    private boolean isValidToolsRepositoryAccess(Access access) throws ServiceException {
        ToolsRepositoryMetadata repository = access.entityId == null ? null : toolsRepositoryService.getById(Integer.parseInt(access.entityId));

        if(repository == null)//accessing non existent ToolsRepositoryMetadata
            return true;

        return repository.getOwner().getUserName().equals(access.userName);
    }

    private boolean isValidUserMemberAccess(Access access) throws ServiceException {
        ToolsRepositoryUserMember member = access.entityId == null ? null : userMemberService.getById(Integer.parseInt(access.entityId));

        if(member == null)//accessing non existent Member
            return true;

        return member.getRepository().getOwner().getUserName().equals(access.userName);
    }

    private boolean isValidGroupMemberAccess(Access access) throws ServiceException {
        ToolsRepositoryGroupMember member = access.entityId == null ? null : groupMemberService.getById(Integer.parseInt(access.entityId));

        if(member == null)//accessing non existent Member
            return true;

        return member.getRepository().getOwner().getUserName().equals(access.userName);
    }

}
