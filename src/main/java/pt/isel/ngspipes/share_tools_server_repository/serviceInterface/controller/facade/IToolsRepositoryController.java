package pt.isel.ngspipes.share_tools_server_repository.serviceInterface.controller.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ngspipes.share_tools_server_repository.logic.domain.ToolsRepositoryMetadata;
import pt.isel.ngspipes.share_tools_server_repository.serviceInterface.config.Routes;

import java.util.Collection;

@CrossOrigin
@RestController
public interface IToolsRepositoryController {

    @RequestMapping(value = Routes.GET_ALL_TOOLS_REPOSITORIES, method = RequestMethod.GET)
    ResponseEntity<Collection<ToolsRepositoryMetadata>> getAllRepositories() throws Exception;

    @RequestMapping(value = Routes.GET_TOOLS_REPOSITORY, method = RequestMethod.GET)
    ResponseEntity<ToolsRepositoryMetadata> getRepository(@PathVariable int repositoryId) throws Exception;

    @RequestMapping(value = Routes.INSERT_TOOLS_REPOSITORY, method = RequestMethod.POST)
    ResponseEntity<Integer> insertRepository(@RequestBody ToolsRepositoryMetadata repository) throws Exception;

    @RequestMapping(value = Routes.UPDATE_TOOLS_REPOSITORY, method = RequestMethod.PUT)
    ResponseEntity<Void> updateRepository(@RequestBody ToolsRepositoryMetadata repository, @PathVariable int repositoryId) throws Exception;

    @RequestMapping(value = Routes.DELETE_TOOLS_REPOSITORY, method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteRepository(@PathVariable int repositoryId) throws Exception;

    @RequestMapping(value = Routes.GET_TOOLS_REPOSITORIES_OF_USER, method = RequestMethod.GET)
    ResponseEntity<Collection<ToolsRepositoryMetadata>> getToolsRepositoriesOfUser(@PathVariable String userName) throws Exception;

}
