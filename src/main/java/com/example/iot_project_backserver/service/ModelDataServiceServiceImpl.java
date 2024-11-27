package com.example.iot_project_backserver.service;

import com.example.iot_project_backserver.entity.*;
import com.example.iot_project_backserver.repository.ECG_ResultRepository;
import com.example.iot_project_backserver.repository.EcgRepository;
import com.example.utils.ExcelGenerator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModelDataServiceServiceImpl implements ModelDataService {

    private final ECG_ResultRepository ecgResultRepository;

    public ModelDataServiceServiceImpl(ECG_ResultRepository ecgResultRepository) {
        this.ecgResultRepository = ecgResultRepository;
    }


    @Override
    public void createECGDataCSV(ECG ecg) {
        String fileName = "C:\\Users\\sunmoon\\IdeaProjects\\IoT_Back_Server\\ECG_Data.xlsx";

        // 헤더 정의
        List<String> headers = new ArrayList<>();
        headers.add("userid");
        for (double i = 0.002; i <= 30.000; i += 0.002) {
            headers.add(String.format("%.3f", i));
        }

        // 데이터 준비
        List<Object> row = new ArrayList<>();
        row.add(ecg.getUserid());
        row.addAll(ecg.getEcgdata());

        // 엑셀 파일 생성/추가
        ExcelGenerator.addOrCreateExcelFile(fileName, headers, row);
    }

    @Override
    public void createAirflowDataCSV(Airflow airflow) {
        String fileName = "C:\\Users\\sunmoon\\IdeaProjects\\IoT_Back_Server\\Airflow_Data.xlsx";

        // 헤더 정의
        List<String> headers = new ArrayList<>();
        headers.add("userid");
        for (double i = 0.002; i <= 30.000; i += 0.002) {
            headers.add(String.format("%.3f", i));
        }

        // 데이터 준비
        List<Object> row = new ArrayList<>();
        row.add(airflow.getUserid());
        row.addAll(airflow.getAirflowdata());

        // 엑셀 파일 생성/추가
        ExcelGenerator.addOrCreateExcelFile(fileName, headers, row);
    }

    @Override
    public void createEOGDataCSV(EOG eog) {
        String fileName = "C:\\Users\\sunmoon\\IdeaProjects\\IoT_Back_Server\\EOG_Data.xlsx";

        // 헤더 정의
        List<String> headers = new ArrayList<>();
        headers.add("userid");
        for (double i = 0.002; i <= 30.000; i += 0.002) {
            headers.add(String.format("%.3f", i));
        }

        // 데이터 준비
        List<Object> row = new ArrayList<>();
        row.add(eog.getUserid());
        row.addAll(eog.getEogdata());

        // 엑셀 파일 생성/추가
        ExcelGenerator.addOrCreateExcelFile(fileName, headers, row);
    }

    @Override
    public void createEMGDataCSV(EMG emg) {
        String fileName = "C:\\Users\\sunmoon\\IdeaProjects\\IoT_Back_Server\\EMG_Data.xlsx";

        // 헤더 정의
        List<String> headers = new ArrayList<>();
        headers.add("userid");
        for (double i = 0.002; i <= 30.000; i += 0.002) {
            headers.add(String.format("%.3f", i));
        }

        // 데이터 준비
        List<Object> row = new ArrayList<>();
        row.add(emg.getUserid());
        row.addAll(emg.getEmgdata());

        // 엑셀 파일 생성/추가
        ExcelGenerator.addOrCreateExcelFile(fileName, headers, row);
    }


    @Override
    public void createGSRDataCSV(GSR gsr) {
        String fileName = "C:\\Users\\sunmoon\\IdeaProjects\\IoT_Back_Server\\GSR_Data.xlsx";

        // 헤더 정의
        List<String> headers = new ArrayList<>();
        headers.add("userid");
        for (double i = 0.002; i <= 30.000; i += 0.002) {
            headers.add(String.format("%.3f", i));
        }

        // 데이터 준비
        List<Object> row = new ArrayList<>();
        row.add(gsr.getUserid());
        row.addAll(gsr.getGsrdata());

        // 엑셀 파일 생성/추가
        ExcelGenerator.addOrCreateExcelFile(fileName, headers, row);
    }


}
