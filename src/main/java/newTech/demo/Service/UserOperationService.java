package newTech.demo.Service;

import newTech.demo.DTO.FileDTO;
import newTech.demo.DTO.response;
import newTech.demo.DTO.userDTO;
import newTech.demo.Module.Data.Account;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;


public interface UserOperationService {
     response<Object> importUser(FileDTO file) throws IOException;

     response<Object> removeUser(FileDTO file) throws IOException;

     response<Object> insertUser(userDTO user);

     response<Object> removeUserSingle(int id);

     response<Object> modifyUser(Account user);

     response<Object> importForbidden(FileDTO file) throws IOException;

     Workbook exportSignUpStatus();

     Workbook getImportModule();

     Workbook getRemoveModule();

     Workbook getForbiddenModule();

     response<Object> getAllUser();
}
