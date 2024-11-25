package com.example.iot_project_backserver.service;

import com.example.iot_project_backserver.entity.Airflow;
import com.example.iot_project_backserver.entity.ECG;
import com.example.iot_project_backserver.entity.EMG;
import com.example.iot_project_backserver.entity.EOG;

public interface ModelData {
    void createECGDataCSV(ECG ecg);
    void createAirflowDataCSV(Airflow airflow);
    void createEOGDataCSV(EOG eog);
    void createEMGDataCSV(EMG emg);
}
