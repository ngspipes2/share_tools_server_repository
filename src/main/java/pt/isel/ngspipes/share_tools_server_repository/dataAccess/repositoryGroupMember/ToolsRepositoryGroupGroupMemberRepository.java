package pt.isel.ngspipes.share_tools_server_repository.dataAccess.repositoryGroupMember;

import pt.isel.ngspipes.share_core.dataAccess.PostgresRepository;
import pt.isel.ngspipes.share_core.dataAccess.RepositoryException;
import pt.isel.ngspipes.share_tools_server_repository.logic.domain.ToolsRepositoryGroupMember;

import java.util.Collection;
import java.util.stream.Collectors;

@org.springframework.stereotype.Repository
public class ToolsRepositoryGroupGroupMemberRepository extends PostgresRepository<ToolsRepositoryGroupMember, Integer> implements IToolsRepositoryGroupMemberRepository {

    public ToolsRepositoryGroupGroupMemberRepository() {
        super(ToolsRepositoryGroupMember.class);
    }



    @Override
    protected void setId(ToolsRepositoryGroupMember member, Integer id) {
        member.setId(id);
    }

    @Override
    protected Integer getId(ToolsRepositoryGroupMember member) {
        return member.getId();
    }

    @Override
    protected void merge(ToolsRepositoryGroupMember member, ToolsRepositoryGroupMember memberToUpdate) {
        memberToUpdate.setId(member.getId());
        memberToUpdate.setCreationDate(member.getCreationDate());
        memberToUpdate.setGroup(member.getGroup());
        memberToUpdate.setRepository(member.getRepository());
        memberToUpdate.setWriteAccess(member.getWriteAccess());
    }

    @Override
    public Collection<ToolsRepositoryGroupMember> getMembersOfRepository(int repositoryId) throws RepositoryException {
        return getAll().stream().filter((member) -> member.getRepository().getId() == repositoryId).collect(Collectors.toList());
    }

    @Override
    public Collection<ToolsRepositoryGroupMember> getMembersWithGroup(String groupName) throws RepositoryException {
        return getAll().stream().filter((member) -> member.getGroup().getGroupName().equals(groupName)).collect(Collectors.toList());
    }
}
