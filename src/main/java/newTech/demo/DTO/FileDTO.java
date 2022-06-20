package newTech.demo.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileDTO {
    private String filename;
    private MultipartFile file;
}
