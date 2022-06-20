package newTech.demo.Service;

import newTech.demo.DTO.FileDTO;
import newTech.demo.DTO.response;
import newTech.demo.DTO.userDTO;
import newTech.demo.Module.Data.Account;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;


public interface UserOperationService {
    public response<Object> importUser(FileDTO file) throws IOException;

    public response<Object> removeUser(FileDTO file) throws IOException;

    public response<Object> insertUser(userDTO user);

    public response<Object> removeUserSingle(int id);

    public response<Object> modifyUser(Account user);

    public response<Object> importForbidden(FileDTO file) throws IOException;

    public Workbook exportSignUpStatus();

    public Workbook getImportModule();

    public Workbook getRemoveModule();

    public Workbook getForbiddenModule();
}
