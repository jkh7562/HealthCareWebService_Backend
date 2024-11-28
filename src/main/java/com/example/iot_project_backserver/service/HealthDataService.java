package com.example.iot_project_backserver.service;


import com.example.iot_project_backserver.entity.*;

import java.util.List;
import java.util.Map;

public interface HealthDataService {
    //Airflow
    Airflow saveAirflow(Airflow airflow);
    List<Airflow> getAllAirflowData();
    void processAndSaveAirflowData(Airflow airflow);
    void saveOrUpdateAirflowResult(AirFlow_Result airFlowresult);
    Map<String,Object> callFastAPIAirFlow(Airflow airflow);

    //BodyTemp
    BodyTemp saveBodyTempData(BodyTemp bodyTemp);
    List<BodyTemp> getAllBodyTempData();
    void saveOrUpdateBodyTempResult(BodyTemp bodyTemp);

    //NIBP
    NIBP saveNIBPData(NIBP nibp);
    //List<NIBP> getAllNIBPData();
    void saveOrUpdateNIBPResult(NIBP nibp);

    // Eog 관련 메서드
    EOG saveEogData(EOG eog);
    List<EOG> getAllEogData();
    void processAndSaveEOGData(EOG eog);
    void saveOrUpdateEOGResult(EOG_Result eogresult);
    Map<String,Object> callFastAPIEOG(EOG eog);

    //SPO2 관련 메서드
    SPO2 saveSPO2Data(SPO2 spo2);
    void saveOrUpdateSPO2Result(SPO2 spo2);

    //ECG 관련 메서드
    //ECG saveECGData(ECG ecg); //ECG 데이터 저장 하기
    void processAndSaveECGData(ECG ecg);
    void saveOrUpdateECGResult(ECG_Result ecgResult);
    Map<String,Object> callFastAPIECG(ECG ecg);


    //GSR 관련 메서드
    void processAndSaveGSRData(GSR gsr);
    void saveOrUpdateGSRResult(GSR_Result gsrResult);
    Map<String,Object> callFastAPIGSR(GSR gsr);



    //EMG 관련 메서드
    void processAndSaveEMGData(EMG emg);
    void saveOrUpdateEMGResult(EMG_Result emgResult);
    Map<String,Object> callFastAPIEMG(EMG emg);





    //ECG_Result saveECGResult(ECG_Result ecgResult);

}


