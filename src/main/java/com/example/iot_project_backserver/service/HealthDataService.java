package com.example.iot_project_backserver.service;


import com.example.iot_project_backserver.entity.*;

import java.util.List;
import java.util.Map;

public interface HealthDataService {
    //Airflow
    Airflow saveAirflow(Airflow airflow);
    List<Airflow> getAllAirflowData();

    //BodyTemp
    BodyTemp saveBodyTempData(BodyTemp bodyTemp);
    List<BodyTemp> getAllBodyTempData();

    //NIBP
    NIBP saveNIBPData(NIBP nibp);
    //List<NIBP> getAllNIBPData();

    // Eog 관련 메서드
    EOG saveEogData(EOG eog);
    List<EOG> getAllEogData();

    //SPO2 관련 메서드
    SPO2 saveSPO2(SPO2 spo2);

    //ECG 관련 메서드
    //ECG saveECGData(ECG ecg); //ECG 데이터 저장 하기
    void processAndSaveECGData(ECG ecg);
    void saveOrUpdateECGResult(ECG_Result ecgResult);
    Map<String,Object> callFastAPIECG(ECG ecg);


    void processAndSaveGSRData(GSR gsr);
    void processAndSaveAirflowData(Airflow airflow);
    void processAndSaveEMGData(EMG emg);
    void processAndSaveEOGData(EOG eog);


    //ECG_Result saveECGResult(ECG_Result ecgResult);

}


