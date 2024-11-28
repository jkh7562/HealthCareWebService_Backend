package com.example.iot_project_backserver.Service;

import com.example.iot_project_backserver.Entity.Data.data.*;

public interface ModelDataService {
    void createECGDataCSV(ECG ecg);
    void createAirflowDataCSV(Airflow airflow);
    void createEOGDataCSV(EOG eog);
    void createEMGDataCSV(EMG emg);
    void createGSRDataCSV(GSR gsr);
}
