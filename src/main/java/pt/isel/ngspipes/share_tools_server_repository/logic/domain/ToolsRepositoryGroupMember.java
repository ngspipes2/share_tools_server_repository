package pt.isel.ngspipes.share_tools_server_repository.logic.domain;

import pt.isel.ngspipes.share_authentication_server.logic.domain.Group;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "ToolsRepositoryGroupMember")
public class ToolsRepositoryGroupMember {

    @Id
    @SequenceGenerator(name="tools_repository_group_member_sequence", sequenceName="tools_repository_group_member_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tools_repository_group_member_sequence")
    private int id;
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @NotNull
    private Date creationDate;
    public Date getCreationDate() { return creationDate; }
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }

    @ManyToOne(targetEntity = Group.class, optional = false, fetch = FetchType.EAGER)
    private Group group;
    public Group getGroup() { return group; }
    public void setGroup(Group group) { this.group = group; }

    @ManyToOne(targetEntity = ToolsRepositoryMetadata.class, optional = false, fetch = FetchType.EAGER)
    private ToolsRepositoryMetadata repository;
    public ToolsRepositoryMetadata getRepository() { return repository; }
    public void setRepository(ToolsRepositoryMetadata repository) { this.repository = repository; }

    private boolean writeAccess;
    public boolean getWriteAccess() { return writeAccess; }
    public void setWriteAccess(boolean writeAccess) { this.writeAccess = writeAccess; }



    public ToolsRepositoryGroupMember(int id, Date creationDate, Group group, ToolsRepositoryMetadata repository, boolean writeAccess) {
        this.id = id;
        this.creationDate = creationDate;
        this.group = group;
        this.repository = repository;
        this.writeAccess = writeAccess;
    }

    public ToolsRepositoryGroupMember() { }

}
