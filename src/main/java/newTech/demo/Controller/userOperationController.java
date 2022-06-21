package newTech.demo.Controller;

import newTech.demo.DTO.FileDTO;
import newTech.demo.DTO.response;
import newTech.demo.DTO.returnCode;
import newTech.demo.Service.UserOperationService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;


@RestController
public class userOperationController {

    @javax.annotation.Resource
    UserOperationService userOperationService;

    @PostMapping("/upload")
    public response<Object> testFile(FileDTO file) {
        file.setFilename(file.getFile().getName());
        System.out.println(file.getFilename());
        return new response<>(returnCode.success, file.getFilename());
    }

    public response<Object> uploadRequest(FileDTO file, Callback callback) {
        file.setFilename(file.getFile().getOriginalFilename());
        if (Objects.isNull(file.getFile()) || file.getFile().getSize() == 0) {
            return new response<>(returnCode.InvalidFile, null);
        }
        if (!file.getFilename().endsWith(".xlsx")) {
            return new response<>(returnCode.InvalidFileExtension, null);
        }
        try {
            return callback.process(file);
        } catch (DataIntegrityViolationException e) {
            return new response<>(returnCode.InvalidData, null);
        } catch (Exception e) {
            return new response<>(returnCode.InvalidFile, e.getClass());
        }
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/importUser")
    public response<Object> importUser(FileDTO file) {
        return uploadRequest(file, (file_t) -> userOperationService.importUser(file_t));
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/importForbidden")
    public response<Object> importForbidden(FileDTO file) {
        return uploadRequest(file, (file_t) -> userOperationService.importForbidden(file_t));
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/removeUser")
    public response<Object> removeUser(FileDTO file) {
        return uploadRequest(file, (file_t) -> userOperationService.removeUser(file_t));
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("getAllUser")
    public response<Object> getAllUser() {
        return userOperationService.getAllUser();
    }

    private ResponseEntity<Resource> generateResponseEntity(Workbook workbook, String filename) {
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            workbook.write(bos);
            workbook.close();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add("charset", "utf-8");
            //设置下载文件名
            filename = URLEncoder.encode(filename, StandardCharsets.UTF_8);
            headers.add("Content-Disposition", "attachment;filename=\"" + filename + "\"");
            Resource resource = new InputStreamResource(new ByteArrayInputStream(bos.toByteArray()));
            return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/x-msdownload")).body(resource);
        } catch (IOException e) {
            try {
                bos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    @GetMapping("file/forbiddenModule")
    public ResponseEntity<Resource> getForbiddenModule() {
        Workbook workbook = userOperationService.getForbiddenModule();
        return generateResponseEntity(workbook, "ForbiddenModule.xlsx");
    }

    @GetMapping("file/ImportModule")
    public ResponseEntity<Resource> getImportModule() {
        Workbook workbook = userOperationService.getImportModule();
        return generateResponseEntity(workbook, "ImportModule.xlsx");
    }

    @GetMapping("file/RemoveModule")
    public ResponseEntity<Resource> getRemoveModule() {
        Workbook workbook = userOperationService.getRemoveModule();
        return generateResponseEntity(workbook, "RemoveModule.xlsx");
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("SignUpExport")
    public ResponseEntity<Resource> getSignUpStatus() {
        Workbook workbook = userOperationService.exportSignUpStatus();
        return generateResponseEntity(workbook, "SignUpStatus.xlsx");
    }

    private interface Callback {
        response<Object> process(FileDTO file) throws IOException;
    }
}
