package newTech.demo.Service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import newTech.demo.DTO.FileDTO;
import newTech.demo.DTO.response;
import newTech.demo.DTO.returnCode;
import newTech.demo.DTO.userDTO;
import newTech.demo.Module.Data.Account;
import newTech.demo.Module.Data.UserForbidden;
import newTech.demo.Module.Data.repository.AccountRepository;
import newTech.demo.Service.UserOperationService;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserOperationServiceImpl implements UserOperationService {
    @Resource
    AccountRepository accountRepo;

    @Resource
    BCryptPasswordEncoder passwordEncoder;

    @Resource
    RedisTemplate<Object, Object> redisTemplate;

    XSSFSheet loadSheet(FileDTO file) throws IOException {
        InputStream inputStream = file.getFile().getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        return workbook.getSheetAt(0);
    }

    @Transactional
    void readSheet(XSSFSheet sheet, readRow callback) {
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            XSSFRow row = sheet.getRow(rowNum);
            if (!Objects.isNull(row)) {
                callback.callback(row);
            }
        }
    }

    @Transactional
    @Override
    public response<Object> importUser(FileDTO file) throws IOException {
        Map<String, Integer> ColumnMap = new HashMap<>(7);
        ColumnMap.put("username", 0);
        ColumnMap.put("realName", 1);
        ColumnMap.put("eid", 2);
        ColumnMap.put("sid", 3);
        ColumnMap.put("maxCredit", 4);
        ColumnMap.put("password", 5);

        readSheet(loadSheet(file), (row) -> {
            Account account = new Account();

//                Set String Cell
            int[] String_row = new int[]{0, 1, 2, 3, 5};
            for (int i : String_row) {
                row.getCell(i).setCellType(CellType.STRING);
            }
            Account storage_account = accountRepo.findAccountByUsername(row.getCell(0).getStringCellValue());
            if (Objects.isNull(storage_account)) {
                account.setUsername(row.getCell(ColumnMap.get("username")).getStringCellValue());
                account.setRealName(row.getCell(ColumnMap.get("realName")).getStringCellValue());
                account.set_tech(false);
                account.set_phy(false);
                account.setEid(row.getCell(ColumnMap.get("eid")).getStringCellValue());
                account.setSid(row.getCell(ColumnMap.get("sid")).getStringCellValue());
                account.setMaxCredit((int) row.getCell(ColumnMap.get("maxCredit")).getNumericCellValue());
                account.setPassword(
                        passwordEncoder.encode(row.getCell(ColumnMap.get("password")).getStringCellValue()));
                account.setStep(1);
                account.set_admin(false);
                accountRepo.save(account);
            } else {
                if ((int) row.getCell(ColumnMap.get("maxCredit")).getNumericCellValue() > storage_account.getMaxCredit()) {
                    storage_account.setMaxCredit((int) row.getCell(ColumnMap.get("maxCredit")).getNumericCellValue());
                    accountRepo.save(storage_account);
                }
            }
        });
        return new response<>(returnCode.success, null);
    }

    @Transactional
    @Override
    public response<Object> removeUser(FileDTO file) throws IOException {
        readSheet(loadSheet(file), (row) -> {
            String sid = row.getCell(0).getStringCellValue();
            Account account = accountRepo.findAccountBySid(sid);
            redisTemplate.delete("login" + account.getId());
            accountRepo.deleteAccountBySid(sid);
        });
        return null;
    }

    @Override
    public response<Object> insertUser(userDTO user) {
        Account account = new Account(
                0,
                user.getUsername(),
                user.getRealName(),
                passwordEncoder.encode(user.getPassword()),
                user.is_tech(),
                user.is_phy(),
                1,
                user.is_admin(),
                user.getEid(),
                user.getSid(),
                user.getMaxCredit(),
                null
        );
        try {
            Account save_result = accountRepo.save(account);
            return new response<>(returnCode.success, null);
        } catch (Exception e) {
            return new response<>(returnCode.unKnownError, e.getMessage());
        }
    }

    @Override
    public response<Object> removeUserSingle(int id) {
        try {
            accountRepo.deleteById(id);
            return new response<>(returnCode.success, null);
        } catch (Exception e) {
            return new response<>(returnCode.unKnownError, e.getMessage());
        }
    }

    @Override
    public response<Object> modifyUser(Account user) {
        Optional<Account> record_opt = accountRepo.findById(user.getId());
        if (record_opt.isPresent()) {
            try {
                Account record = record_opt.get();
                redisTemplate.delete("login" + record.getId());
                BeanUtil.copyProperties(user, record);
                accountRepo.save(record);
                return new response<>(returnCode.success, null);
            } catch (Exception e) {
                return new response<>(returnCode.unKnownError, e.getMessage());
            }
        }
        return new response<>(returnCode.UnknownRecord, null);
    }

    @Transactional
    @Override
    public response<Object> importForbidden(FileDTO file) throws IOException {
        Map<String, Integer> ColumnMap = new HashMap<>(7);
        ColumnMap.put("sid", 0);
        ColumnMap.put("isTech", 1);
        ColumnMap.put("isPhy", 2);
        readSheet(loadSheet(file), (row) -> {
            String sid = row.getCell(ColumnMap.get("sid")).getStringCellValue();
            Account record = accountRepo.findAccountBySid(sid);
            if (!Objects.isNull(record)) {
                record.setUserForbidden(new UserForbidden(
                        0,
                        row.getCell(ColumnMap.get("isTech")).getNumericCellValue() == 1,
                        row.getCell(ColumnMap.get("isPhy")).getNumericCellValue() == 1
                ));
                accountRepo.save(record);
            }
        });
        return new response<>(returnCode.success, null);
    }

    @Override
    public Workbook exportSignUpStatus() {
        return ExcelExportUtil.exportExcel(new ExportParams(), Account.class, accountRepo.findAll());
    }

    @Override
    public Workbook getImportModule() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("用户名");
        row.createCell(1).setCellValue("姓名");
        row.createCell(2).setCellValue("学号");
        row.createCell(3).setCellValue("学考号");
        row.createCell(4).setCellValue("技术学分");
        row.createCell(5).setCellValue("密码");
        return workbook;
    }

    @Override
    public Workbook getRemoveModule() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("学号");
        return workbook;
    }

    @Override
    public Workbook getForbiddenModule() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("学号");
        row.createCell(1).setCellValue("信息技术");
        row.createCell(2).setCellValue("通用技术");
        return workbook;
    }

    interface readRow {
        public void callback(XSSFRow row);
    }
}
