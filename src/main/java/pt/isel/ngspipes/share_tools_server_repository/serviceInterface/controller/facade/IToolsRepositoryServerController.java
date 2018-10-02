package pt.isel.ngspipes.share_tools_server_repository.serviceInterface.controller.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ngspipes.share_tools_server_repository.serviceInterface.config.Routes;
import pt.isel.ngspipes.tool_descriptor.interfaces.IToolDescriptor;

import java.util.Collection;

@CrossOrigin
@RestController
public interface IToolsRepositoryServerController {

    @RequestMapping(value = Routes.GET_ALL_TOOLS_REPOSITORY_SERVER_URI, method = RequestMethod.GET)
    ResponseEntity<Collection<IToolDescriptor>> getAll(@PathVariable int repositoryId, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception;

    @RequestMapping(value = Routes.GET_TOOLS_REPOSITORY_SERVER_URI, method = RequestMethod.GET)
    ResponseEntity<IToolDescriptor> get(@PathVariable int repositoryId, @PathVariable String toolName, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception;

    @RequestMapping(value = Routes.INSERT_TOOLS_REPOSITORY_SERVER_URI, method = RequestMethod.POST)
    ResponseEntity<Void> insert(@PathVariable int repositoryId, @RequestBody IToolDescriptor tool, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception;

    @RequestMapping(value = Routes.UPDATE_TOOLS_REPOSITORY_SERVER_URI, method = RequestMethod.PUT)
    ResponseEntity<Void> update(@PathVariable int repositoryId, @RequestBody IToolDescriptor tool, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception;

    @RequestMapping(value = Routes.DELETE_TOOLS_REPOSITORY_SERVER_URI, method = RequestMethod.DELETE)
    ResponseEntity<Void> delete(@PathVariable int repositoryId, @PathVariable String toolName, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception;

}
