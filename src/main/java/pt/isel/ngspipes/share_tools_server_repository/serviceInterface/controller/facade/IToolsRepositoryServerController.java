package pt.isel.ngspipes.share_tools_server_repository.serviceInterface.controller.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ngspipes.share_tools_server_repository.serviceInterface.config.Routes;
import pt.isel.ngspipes.tool_descriptor.interfaces.IToolDescriptor;

import java.util.Collection;

@CrossOrigin
@RestController
public interface IToolsRepositoryServerController {

    @RequestMapping(value = Routes.GET_LOGO_URI, method = RequestMethod.GET)
    ResponseEntity<byte[]> getLogo(@PathVariable String repositoryName, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception;

    @RequestMapping(value = Routes.SET_LOGO_URI, method = RequestMethod.POST)
    ResponseEntity<Void> setLogo(@PathVariable String repositoryName, @RequestHeader(value = "Authorization", required = false) String authHeader, @RequestBody(required = false) byte[] logo) throws Exception;

    @RequestMapping(value = Routes.GET_TOOLS_NAMES_URI, method = RequestMethod.GET)
    ResponseEntity<Collection<String>> getToolsNames(@PathVariable String repositoryName, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception;

    @RequestMapping(value = Routes.GET_ALL_TOOLS_URI, method = RequestMethod.GET)
    ResponseEntity<Collection<IToolDescriptor>> getAll(@PathVariable String repositoryName, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception;

    @RequestMapping(value = Routes.GET_TOOL_URI, method = RequestMethod.GET)
    ResponseEntity<IToolDescriptor> get(@PathVariable String repositoryName, @PathVariable String toolName, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception;

    @RequestMapping(value = Routes.INSERT_TOOL_URI, method = RequestMethod.POST)
    ResponseEntity<Void> insert(@PathVariable String repositoryName, @RequestBody IToolDescriptor tool, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception;

    @RequestMapping(value = Routes.UPDATE_TOOL_URI, method = RequestMethod.PUT)
    ResponseEntity<Void> update(@PathVariable String repositoryName, @RequestBody IToolDescriptor tool, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception;

    @RequestMapping(value = Routes.DELETE_TOOL_URI, method = RequestMethod.DELETE)
    ResponseEntity<Void> delete(@PathVariable String repositoryName, @PathVariable String toolName, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception;

}
