package pt.isel.ngspipes.share_tools_server_repository.logic.service.repositoryGroupMember;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ngspipes.share_core.dataAccess.RepositoryException;
import pt.isel.ngspipes.share_core.logic.service.Service;
import pt.isel.ngspipes.share_core.logic.service.ServiceUtils;
import pt.isel.ngspipes.share_core.logic.service.exceptions.ServiceException;
import pt.isel.ngspipes.share_tools_server_repository.dataAccess.repositoryGroupMember.IToolsRepositoryGroupMemberRepository;
import pt.isel.ngspipes.share_tools_server_repository.logic.domain.ToolsRepositoryGroupMember;

import java.util.Collection;
import java.util.Date;

@org.springframework.stereotype.Service
public class ToolsRepositoryGroupMemberService extends Service<ToolsRepositoryGroupMember, Integer> implements IToolsRepositoryGroupMemberService {

    @Autowired
    private IToolsRepositoryGroupMemberRepository groupMemberRepository;



    protected ToolsRepositoryGroupMemberService() {
        super("RepositoryGroupMembers", "ToolsRepositoryGroupMember");
    }



    @Override
    @Transactional
    public void insert(ToolsRepositoryGroupMember member) throws ServiceException {
        member.setCreationDate(new Date());
        super.insert(member);
    }

    @Override
    protected void validateInsert(ToolsRepositoryGroupMember member) throws ServiceException { }

    @Override
    protected void validateDelete(Integer id) throws ServiceException { }

    @Override
    protected void validateUpdate(ToolsRepositoryGroupMember member) throws ServiceException {
        ToolsRepositoryGroupMember savedMember = getById(member.getId());

        if(savedMember != null) {
            if(!ServiceUtils.sameDate(savedMember.getCreationDate(), member.getCreationDate()))
                throw new ServiceException("Members's creationDate Cannot be changed!");

            if(!savedMember.getGroup().getGroupName().equals(member.getGroup().getGroupName()))
                throw new ServiceException("Members's group Cannot be changed!");

            if(savedMember.getRepository().getId() != member.getRepository().getId())
                throw new ServiceException("Members's repository Cannot be changed!");
        }
    }

    @Override
    protected Integer getId(ToolsRepositoryGroupMember member) throws ServiceException {
        return member.getId();
    }

    @Override
    public Collection<ToolsRepositoryGroupMember> getMembersWithGroup(String groupName) throws ServiceException {
        try {
            return groupMemberRepository.getMembersWithGroup(groupName);
        } catch (RepositoryException e) {
            throw new ServiceException("Error getting Members with Group!");
        }
    }

    @Override
    public Collection<ToolsRepositoryGroupMember> getMembersOfRepository(int repositoryId) throws ServiceException {
        try {
            return groupMemberRepository.getMembersOfRepository(repositoryId);
        } catch (RepositoryException e) {
            throw new ServiceException("Error getting Members of Repository!");
        }
    }

}
