package pt.isel.ngspipes.share_tools_server_repository.dataAccess.repositoryGroupMember;

import pt.isel.ngspipes.share_core.dataAccess.IRepository;
import pt.isel.ngspipes.share_core.dataAccess.RepositoryException;
import pt.isel.ngspipes.share_tools_server_repository.logic.domain.ToolsRepositoryGroupMember;

import java.util.Collection;

public interface IToolsRepositoryGroupMemberRepository extends IRepository<ToolsRepositoryGroupMember, Integer> {

    Collection<ToolsRepositoryGroupMember> getMembersOfRepository(int repositoryId) throws RepositoryException;

    Collection<ToolsRepositoryGroupMember> getMembersWithGroup(String groupName) throws RepositoryException;

}
