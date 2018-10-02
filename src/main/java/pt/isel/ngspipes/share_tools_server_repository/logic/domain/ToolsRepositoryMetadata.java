package pt.isel.ngspipes.share_tools_server_repository.logic.domain;

import org.hibernate.validator.constraints.NotEmpty;
import pt.isel.ngspipes.share_authentication_server.logic.domain.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "ToolRepositoryMetadata")
public class ToolsRepositoryMetadata {

    @Id
    @SequenceGenerator(name="tools_repository_sequence", sequenceName="tools_repository_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tools_repository_sequence")
    private int id;
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @NotNull
    @NotEmpty
    private String name;
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    private String description;
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @NotNull
    private Date creationDate;
    public Date getCreationDate() { return creationDate; }
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }

    private boolean isPublic;
    public boolean getIsPublic() { return isPublic; }
    public void setPublic(boolean aPublic) { isPublic = aPublic; }

    @ManyToOne(targetEntity = User.class, optional = false, fetch = FetchType.EAGER)
    private User owner;
    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }



    public ToolsRepositoryMetadata(int id, String name, String description, Date creationDate, boolean isPublic, User owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.isPublic = isPublic;
        this.owner = owner;
    }

    public ToolsRepositoryMetadata() { }

}
