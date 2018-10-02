package pt.isel.ngspipes.share_tools_server_repository.logic.service.repositoryUserMember;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ngspipes.share_core.dataAccess.RepositoryException;
import pt.isel.ngspipes.share_core.logic.service.Service;
import pt.isel.ngspipes.share_core.logic.service.ServiceUtils;
import pt.isel.ngspipes.share_core.logic.service.exceptions.ServiceException;
import pt.isel.ngspipes.share_tools_server_repository.dataAccess.repositoryUserMember.IToolsRepositoryUserMemberRepository;
import pt.isel.ngspipes.share_tools_server_repository.logic.domain.ToolsRepositoryUserMember;

import java.util.Collection;
import java.util.Date;

@org.springframework.stereotype.Service
public class ToolsRepositoryUserMemberService extends Service<ToolsRepositoryUserMember, Integer> implements IToolsRepositoryUserMemberService {

    @Autowired
    private IToolsRepositoryUserMemberRepository userMemberRepository;



    protected ToolsRepositoryUserMemberService() {
        super("RepositoryUserMembers", "ToolsRepositoryUserMember");
    }



    @Override
    @Transactional
    public void insert(ToolsRepositoryUserMember member) throws ServiceException {
        member.setCreationDate(new Date());
        super.insert(member);
    }

    @Override
    protected void validateInsert(ToolsRepositoryUserMember member) throws ServiceException { }

    @Override
    protected void validateDelete(Integer id) throws ServiceException { }

    @Override
    protected void validateUpdate(ToolsRepositoryUserMember member) throws ServiceException {
        ToolsRepositoryUserMember savedMember = getById(member.getId());

        if(savedMember != null) {
            if(!ServiceUtils.sameDate(savedMember.getCreationDate(), member.getCreationDate()))
                throw new ServiceException("Members's creationDate Cannot be changed!");

            if(!savedMember.getUser().getUserName().equals(member.getUser().getUserName()))
                throw new ServiceException("Members's user Cannot be changed!");

            if(savedMember.getRepository().getId() != member.getRepository().getId())
                throw new ServiceException("Members's repository Cannot be changed!");
        }
    }

    @Override
    protected Integer getId(ToolsRepositoryUserMember member) throws ServiceException {
        return member.getId();
    }

    @Override
    public Collection<ToolsRepositoryUserMember> getMembersWithUser(String userName) throws ServiceException {
        try {
            return userMemberRepository.getMembersWithUser(userName);
        } catch (RepositoryException e) {
            throw new ServiceException("Error getting Members with User!");
        }
    }

    @Override
    public Collection<ToolsRepositoryUserMember> getMembersOfRepository(int repositoryId) throws ServiceException {
        try {
            return userMemberRepository.getMembersOfRepository(repositoryId);
        } catch (RepositoryException e) {
            throw new ServiceException("Error getting Members of Repository!");
        }
    }

}
