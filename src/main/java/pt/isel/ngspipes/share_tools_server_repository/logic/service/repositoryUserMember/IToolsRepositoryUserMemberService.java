package pt.isel.ngspipes.share_tools_server_repository.logic.service.repositoryUserMember;

import pt.isel.ngspipes.share_core.logic.service.IService;
import pt.isel.ngspipes.share_core.logic.service.exceptions.ServiceException;
import pt.isel.ngspipes.share_tools_server_repository.logic.domain.ToolsRepositoryUserMember;

import java.util.Collection;

public interface IToolsRepositoryUserMemberService extends IService<ToolsRepositoryUserMember, Integer> {

    Collection<ToolsRepositoryUserMember> getMembersWithUser(String userName) throws ServiceException;

    Collection<ToolsRepositoryUserMember> getMembersOfRepository(int repositoryId) throws ServiceException;

}
