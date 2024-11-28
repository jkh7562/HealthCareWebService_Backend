package com.example.iot_project_backserver.Service;


import com.example.iot_project_backserver.Entity.Data.Result.*;
import com.example.iot_project_backserver.Entity.Data.data.*;

import java.util.Map;

public interface HealthDataService {
    //Airflow
    void processAndSaveAirflowData(Airflow airflow);
    void saveOrUpdateAirflowResult(AirFlow_Result airFlowresult);
    Map<String,Object> callFastAPIAirFlow(Airflow airflow);

    //BodyTemp
    BodyTemp saveBodyTempData(BodyTemp bodyTemp);
    void saveOrUpdateBodyTempResult(BodyTemp bodyTemp);

    //NIBP
    NIBP saveNIBPData(NIBP nibp);
    void saveOrUpdateNIBPResult(NIBP nibp);

    // Eog 관련 메서드
   // EOG saveEogData(EOG eog);
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






}


