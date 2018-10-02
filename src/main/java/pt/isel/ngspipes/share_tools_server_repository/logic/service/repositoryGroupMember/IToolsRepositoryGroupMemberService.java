package pt.isel.ngspipes.share_tools_server_repository.logic.service.repositoryGroupMember;

import pt.isel.ngspipes.share_core.logic.service.IService;
import pt.isel.ngspipes.share_core.logic.service.exceptions.ServiceException;
import pt.isel.ngspipes.share_tools_server_repository.logic.domain.ToolsRepositoryGroupMember;

import java.util.Collection;

public interface IToolsRepositoryGroupMemberService extends IService<ToolsRepositoryGroupMember, Integer> {

    Collection<ToolsRepositoryGroupMember> getMembersWithGroup(String groupName) throws ServiceException;

    Collection<ToolsRepositoryGroupMember> getMembersOfRepository(int repositoryId) throws ServiceException;

}
