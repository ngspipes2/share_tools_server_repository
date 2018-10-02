package pt.isel.ngspipes.share_tools_server_repository.dataAccess.repositoryUserMember;

import pt.isel.ngspipes.share_core.dataAccess.PostgresRepository;
import pt.isel.ngspipes.share_core.dataAccess.RepositoryException;
import pt.isel.ngspipes.share_tools_server_repository.logic.domain.ToolsRepositoryUserMember;

import java.util.Collection;
import java.util.stream.Collectors;

@org.springframework.stereotype.Repository
public class ToolsRepositoryUserMemberRepository extends PostgresRepository<ToolsRepositoryUserMember, Integer> implements IToolsRepositoryUserMemberRepository {

    public ToolsRepositoryUserMemberRepository() {
        super(ToolsRepositoryUserMember.class);
    }



    @Override
    protected void setId(ToolsRepositoryUserMember member, Integer id) {
        member.setId(id);
    }

    @Override
    protected Integer getId(ToolsRepositoryUserMember member) {
        return member.getId();
    }

    @Override
    protected void merge(ToolsRepositoryUserMember member, ToolsRepositoryUserMember memberToUpdate) {
        memberToUpdate.setId(member.getId());
        memberToUpdate.setCreationDate(member.getCreationDate());
        memberToUpdate.setUser(member.getUser());
        memberToUpdate.setRepository(member.getRepository());
        memberToUpdate.setWriteAccess(member.getWriteAccess());
    }

    @Override
    public Collection<ToolsRepositoryUserMember> getMembersOfRepository(int repositoryId) throws RepositoryException {
        return getAll().stream().filter((member) -> member.getRepository().getId() == repositoryId).collect(Collectors.toList());
    }

    @Override
    public Collection<ToolsRepositoryUserMember> getMembersWithUser(String userName) throws RepositoryException {
        return getAll().stream().filter((member) -> member.getUser().getUserName().equals(userName)).collect(Collectors.toList());
    }
}
