package pt.isel.ngspipes.share_tools_server_repository.serviceInterface.controller.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ngspipes.share_tools_server_repository.logic.domain.ToolsRepositoryUserMember;
import pt.isel.ngspipes.share_tools_server_repository.serviceInterface.config.Routes;

import java.util.Collection;

@CrossOrigin
@RestController
public interface IToolsRepositoryUserMemberController {

    @RequestMapping(value = Routes.GET_ALL_USER_MEMBERS, method = RequestMethod.GET)
    ResponseEntity<Collection<ToolsRepositoryUserMember>> getAllMembers() throws Exception;

    @RequestMapping(value = Routes.GET_USER_MEMBER, method = RequestMethod.GET)
    ResponseEntity<ToolsRepositoryUserMember> getMember(@PathVariable int memberId) throws Exception;

    @RequestMapping(value = Routes.INSERT_USER_MEMBER, method = RequestMethod.POST)
    ResponseEntity<Integer> insertMember(@RequestBody ToolsRepositoryUserMember member) throws Exception;

    @RequestMapping(value = Routes.UPDATE_USER_MEMBER, method = RequestMethod.PUT)
    ResponseEntity<Void> updateMember(@RequestBody ToolsRepositoryUserMember member, @PathVariable int memberId) throws Exception;

    @RequestMapping(value = Routes.DELETE_USER_MEMBER, method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteMember(@PathVariable int memberId) throws Exception;

    @RequestMapping(value = Routes.GET_USER_MEMBERS_WITH_USER, method = RequestMethod.GET)
    ResponseEntity<Collection<ToolsRepositoryUserMember>> getMembersWithUser(@PathVariable String userName) throws Exception;

    @RequestMapping(value = Routes.GET_USER_MEMBERS_OF_REPOSITORY, method = RequestMethod.GET)
    ResponseEntity<Collection<ToolsRepositoryUserMember>> getMembersOfRepository(@PathVariable int repositoryId) throws Exception;

}
