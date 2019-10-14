package com.deltamate.demo.service;

import com.deltamate.demo.model.Ex_Fty;
import com.deltamate.demo.model.WorkStandard;
import org.springframework.stereotype.Service;

import java.util.List;

public interface WorkStandardService {
    List<WorkStandard> findAll();
    void insertWorkStandard(WorkStandard workStandard) throws Exception;
    WorkStandard searchWorkStandardByStyle(long styleID);
    void updateWorkStandard(long id, WorkStandard workStandard) throws Exception;
    void deleteWorkStandard(long id);
    void removeEx_FtiesByStyle(long styleID);
}
