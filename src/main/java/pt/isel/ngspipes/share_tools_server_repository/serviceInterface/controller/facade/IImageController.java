package pt.isel.ngspipes.share_tools_server_repository.serviceInterface.controller.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pt.isel.ngspipes.share_tools_server_repository.serviceInterface.config.Routes;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
public interface IImageController {

    @RequestMapping(value = Routes.GET_TOOLS_REPOSITORY_IMAGE, method = RequestMethod.GET)
    ResponseEntity<Void> getToolsRepositoryImage(HttpServletResponse response, @PathVariable String repositoryId) throws Exception;

    @RequestMapping(value = Routes.UPDATE_TOOLS_REPOSITORY_IMAGE, method = RequestMethod.POST)
    ResponseEntity<Void> updateToolsRepositoryImage(@RequestPart(value = "file") MultipartFile file, @PathVariable String repositoryId) throws Exception;

    @RequestMapping(value = Routes.DELETE_TOOLS_REPOSITORY_IMAGE, method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteToolsRepositoryImage(@PathVariable String repositoryId) throws Exception;

}
