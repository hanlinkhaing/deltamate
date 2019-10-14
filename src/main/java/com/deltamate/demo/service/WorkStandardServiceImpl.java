package com.deltamate.demo.service;

import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.deltamate.demo.model.Ex_Fty;
import com.deltamate.demo.model.StyleTable;
import com.deltamate.demo.model.WorkStandard;
import com.deltamate.demo.repository.Ex_FtyRepository;
import com.deltamate.demo.repository.StyleTableRepository;
import com.deltamate.demo.repository.WorkStandardRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class WorkStandardServiceImpl implements WorkStandardService {

    private WorkStandardRepository workStandardRepository;

    private StyleTableRepository styleTableRepository;

    private Ex_FtyRepository ex_ftyRepository;

    private final String PATH = "workStandard//";

    public WorkStandardServiceImpl(WorkStandardRepository workStandardRepository,
                                   StyleTableRepository styleTableRepository, Ex_FtyRepository ex_ftyRepository) {
        this.workStandardRepository = workStandardRepository;
        this.styleTableRepository = styleTableRepository;
        this.ex_ftyRepository = ex_ftyRepository;
    }

    @Override
    public List<WorkStandard> findAll() {
        return workStandardRepository.findAll();
    }

    @Override
    public void insertWorkStandard(WorkStandard workStandard) throws Exception {
        if (null != workStandard.getFile() && !workStandard.getFile().isEmpty()) {
            saveWorkStandardFile(workStandard);
        }
        saveWorkStandardAndMembers(workStandard);
    }

    @Override
    public WorkStandard searchWorkStandardByStyle(long styleID) {
        StyleTable styleTable = styleTableRepository.findById(styleID)
                .orElseThrow(()->new EntityNotFoundException(styleID +" not found."));
        return styleTable.getWorkStandard();
    }

    @Transactional
    @Override
    public void updateWorkStandard(long styleID, WorkStandard workStandard) throws Exception {
        StyleTable originalStyle = styleTableRepository.findById(styleID).orElseThrow(() -> new EntityNotFoundException(styleID + " not found!"));
        WorkStandard originalWork = originalStyle.getWorkStandard();
        StyleTable styleTable = workStandard.getStyleTable();
        if (null != workStandard.getFile() && !workStandard.getFile().isEmpty()) {
            File oldExcelFile = new File(originalWork.getPath());
            oldExcelFile.delete();
            File oldPDFFile = new File(originalWork.getPath().replace(".xlsx", ".pdf"));
            oldPDFFile.delete();
            File oldPath = new File(originalWork.getPath().replace(originalWork.getOrderNo()+".xlsx", ""));
            oldPath.delete();
            saveWorkStandardFile(workStandard);
            originalWork.setPath(workStandard.getPath());
        }
        List<Ex_Fty> ex_fties = styleTable.getEx_ftys();
        for (Ex_Fty exfty : ex_fties) {
            exfty.setStyle_fk(originalStyle);
            ex_ftyRepository.save(exfty);
        }
        originalStyle.setEx_ftys(ex_fties);
        originalStyle.setStyleCode(styleTable.getStyleCode());
        originalStyle.setBuyerCode(styleTable.getBuyerCode());
        originalStyle.setBagType(styleTable.getBagType());
        originalStyle.setBuyer(styleTable.getBuyer());
        originalStyle.setGroupName(styleTable.getGroupName());
        originalStyle.setGrandTotal(styleTable.getGrandTotal());
        originalStyle.setInsertDate(LocalDateTime.now());

        originalWork.setOrderNo(workStandard.getOrderNo());
        originalWork.setInsertedDate(LocalDate.now());
    }



    @Override
    public void deleteWorkStandard(long id) {
        workStandardRepository.deleteById(id);
    }

    @Override
    public void removeEx_FtiesByStyle(long styleID) {
        StyleTable styleTable = styleTableRepository.findById(styleID).orElseThrow
                (()->new EntityNotFoundException(styleID +" not found."));
        ex_ftyRepository.deleteByStyle_fk(styleTable);
    }

    private void saveWorkStandardFile(WorkStandard workStandard) throws Exception {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");
        LocalDateTime localDateTime = LocalDateTime.now();

        String filePath = PATH + dateTimeFormatter.format(localDateTime);
        File uploadDir = new File(filePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        String fileName = uploadDir.getAbsolutePath() +
                File.separator + workStandard.getOrderNo() + ".xlsx";
        File serverFile = new File(fileName);
        workStandard.setPath(fileName);

        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
        stream.write(workStandard.getFile().getBytes());
        stream.close();

        Workbook workbook = new Workbook(workStandard.getFile().getInputStream());
        workbook.calculateFormula();
        for (int i = 1; i < workbook.getWorksheets().getCount(); i++)
        {
            workbook.getWorksheets().get(i).setVisible(false);
        }

        for (int j = 0; j < workbook.getWorksheets().getCount(); j++)
        {
            Worksheet ws = workbook.getWorksheets().get(j);
            workbook.save(uploadDir.getAbsolutePath() +
                    File.separator + workStandard.getOrderNo() + ".pdf");

            if (j < workbook.getWorksheets().getCount() - 1)
            {
                workbook.getWorksheets().get(j + 1).setVisible(true);
                workbook.getWorksheets().get(j).setVisible(false);
            }
        }
    }

    private void saveWorkStandardAndMembers(WorkStandard workStandard){
        StyleTable styleTable = workStandard.getStyleTable();
        styleTable.setInsertDate(LocalDateTime.now());
        styleTable.setWorkStandard(workStandard);
        workStandard.setInsertedDate(LocalDate.now());
        workStandard.setStyleTable(styleTable);
        for (Ex_Fty ex : styleTable.getEx_ftys()) {
            ex.setStyle_fk(styleTable);
        }
        styleTableRepository.save(styleTable);
    }
}
