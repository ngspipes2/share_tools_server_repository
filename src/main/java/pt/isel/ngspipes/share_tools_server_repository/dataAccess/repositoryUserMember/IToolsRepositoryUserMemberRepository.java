package pt.isel.ngspipes.share_tools_server_repository.dataAccess.repositoryUserMember;

import pt.isel.ngspipes.share_core.dataAccess.IRepository;
import pt.isel.ngspipes.share_core.dataAccess.RepositoryException;
import pt.isel.ngspipes.share_tools_server_repository.logic.domain.ToolsRepositoryUserMember;

import java.util.Collection;

public interface IToolsRepositoryUserMemberRepository extends IRepository<ToolsRepositoryUserMember, Integer> {

    Collection<ToolsRepositoryUserMember> getMembersOfRepository(int repositoryId) throws RepositoryException;

    Collection<ToolsRepositoryUserMember> getMembersWithUser(String userName) throws RepositoryException;

}
