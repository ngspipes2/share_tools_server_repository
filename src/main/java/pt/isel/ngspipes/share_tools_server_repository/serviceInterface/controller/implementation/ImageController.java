package pt.isel.ngspipes.share_tools_server_repository.serviceInterface.controller.implementation;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pt.isel.ngspipes.share_authentication_server.logic.domain.User;
import pt.isel.ngspipes.share_authentication_server.logic.service.ICurrentUserSupplier;
import pt.isel.ngspipes.share_authentication_server.logic.service.PermissionService.Access;
import pt.isel.ngspipes.share_core.logic.domain.Image;
import pt.isel.ngspipes.share_core.logic.service.IService;
import pt.isel.ngspipes.share_core.logic.service.exceptions.ServiceException;
import pt.isel.ngspipes.share_tools_server_repository.logic.domain.ToolsRepositoryMetadata;
import pt.isel.ngspipes.share_tools_server_repository.logic.service.PermissionService;
import pt.isel.ngspipes.share_tools_server_repository.serviceInterface.controller.facade.IImageController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ImageController implements IImageController {

    @Autowired
    private IService<Image, String> imageService;
    @Autowired
    private ICurrentUserSupplier currentUserSupplier;
    @Autowired
    private PermissionService permissionService;



    @Override
    public ResponseEntity<Void> getToolsRepositoryImage(HttpServletResponse response, @PathVariable String repositoryId) throws Exception {
        if(!isValidAccess(Access.Operation.GET, repositoryId, ToolsRepositoryMetadata.class))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Image image = imageService.getById("ToolsRepositoryMetadata" + repositoryId);

        response.setContentType(MediaType.IMAGE_PNG_VALUE);

        if(image == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        IOUtils.write(image.getContent(), response.getOutputStream());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateToolsRepositoryImage(@RequestPart(value = "file") MultipartFile file, @PathVariable String repositoryId) throws Exception {
        if(!isValidAccess(Access.Operation.UPDATE, repositoryId, ToolsRepositoryMetadata.class))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        if(imageService.getById("ToolsRepositoryMetadata"+repositoryId) == null)
            imageService.insert(new Image("ToolsRepositoryMetadata"+repositoryId, IOUtils.toByteArray(file.getInputStream())));
        else
            imageService.update(new Image("ToolsRepositoryMetadata"+repositoryId, IOUtils.toByteArray(file.getInputStream())));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteToolsRepositoryImage(@PathVariable String repositoryId) throws Exception {
        if(!isValidAccess(Access.Operation.UPDATE, repositoryId, ToolsRepositoryMetadata.class))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        imageService.delete("ToolsRepositoryMetadata" + repositoryId);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    private boolean isValidAccess(Access.Operation operation, String id, Class<?> entity) throws ServiceException {
        User currentUser = currentUserSupplier.get();

        Access access = new Access();
        access.userName = currentUser == null ? null : currentUser.getUserName();
        access.operation = operation;
        access.entity = entity;
        access.entityId = id;

        return permissionService.isValid(access);
    }

}
