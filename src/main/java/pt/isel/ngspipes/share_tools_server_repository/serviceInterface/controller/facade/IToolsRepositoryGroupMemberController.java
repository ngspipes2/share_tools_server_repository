package pt.isel.ngspipes.share_tools_server_repository.serviceInterface.controller.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ngspipes.share_tools_server_repository.logic.domain.ToolsRepositoryGroupMember;
import pt.isel.ngspipes.share_tools_server_repository.serviceInterface.config.Routes;

import java.util.Collection;

@CrossOrigin
@RestController
public interface IToolsRepositoryGroupMemberController {

    @RequestMapping(value = Routes.GET_ALL_GROUP_MEMBERS, method = RequestMethod.GET)
    ResponseEntity<Collection<ToolsRepositoryGroupMember>> getAllMembers() throws Exception;

    @RequestMapping(value = Routes.GET_GROUP_MEMBER, method = RequestMethod.GET)
    ResponseEntity<ToolsRepositoryGroupMember> getMember(@PathVariable int memberId) throws Exception;

    @RequestMapping(value = Routes.INSERT_GROUP_MEMBER, method = RequestMethod.POST)
    ResponseEntity<Integer> insertMember(@RequestBody ToolsRepositoryGroupMember member) throws Exception;

    @RequestMapping(value = Routes.UPDATE_GROUP_MEMBER, method = RequestMethod.PUT)
    ResponseEntity<Void> updateMember(@RequestBody ToolsRepositoryGroupMember member, @PathVariable int memberId) throws Exception;

    @RequestMapping(value = Routes.DELETE_GROUP_MEMBER, method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteMember(@PathVariable int memberId) throws Exception;

    @RequestMapping(value = Routes.GET_GROUP_MEMBERS_WITH_GROUP, method = RequestMethod.GET)
    ResponseEntity<Collection<ToolsRepositoryGroupMember>> getMembersWithGroup(@PathVariable String groupName) throws Exception;

    @RequestMapping(value = Routes.GET_GROUP_MEMBERS_OF_REPOSITORY, method = RequestMethod.GET)
    ResponseEntity<Collection<ToolsRepositoryGroupMember>> getMembersOfRepository(@PathVariable int repositoryId) throws Exception;

}
