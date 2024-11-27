package com.example.iot_project_backserver.service;

import com.example.iot_project_backserver.entity.*;

public interface ModelDataService {
    void createECGDataCSV(ECG ecg);
    void createAirflowDataCSV(Airflow airflow);
    void createEOGDataCSV(EOG eog);
    void createEMGDataCSV(EMG emg);
    void createGSRDataCSV(GSR gsr);
    ECG_Result saveECGResult(ECG_Result ecgResult);
}
