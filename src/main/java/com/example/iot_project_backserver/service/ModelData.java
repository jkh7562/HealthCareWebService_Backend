package com.example.iot_project_backserver.service;

import com.example.iot_project_backserver.entity.Airflow;
import com.example.iot_project_backserver.entity.ECG;

public interface ModelData {
    void createECGDataCSV(ECG ecg);
    void createAirflowDataCSV(Airflow airflow);
}
