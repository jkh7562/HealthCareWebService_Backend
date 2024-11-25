package com.example.iot_project_backserver.service;


import com.example.iot_project_backserver.entity.Airflow;
import com.example.iot_project_backserver.entity.BodyTemp;
import com.example.iot_project_backserver.entity.EOG;
import com.example.iot_project_backserver.entity.ECG;
import com.example.iot_project_backserver.entity.EMG;
import com.example.iot_project_backserver.entity.GSR;

import java.util.List;

public interface HealthDataService {
    //Airflow
    Airflow saveAirflow(Airflow airflow);
    List<Airflow> getAllAirflowData();

    //BodyTemp
    BodyTemp saveBodyTempData(BodyTemp bodyTemp);
    List<BodyTemp> getAllBodyTempData();

    // Eog 관련 메서드
    EOG saveEogData(EOG eog);
    List<EOG> getAllEogData();

    //ECG 관련 메서드
    //ECG saveECGData(ECG ecg); //ECG 데이터 저장 하기
    void processAndSaveECGData(ECG ecg);

    //void processAndSaveGSRData(GSR gsr);
    void processAndSaveAirflowData(Airflow airflow);
    //void processAndSaveEmgData(EMG emg);
    void processAndSaveEOGData(EOG eog);
}


