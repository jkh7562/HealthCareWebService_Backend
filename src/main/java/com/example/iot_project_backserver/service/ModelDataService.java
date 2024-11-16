package com.example.iot_project_backserver.service;

import com.example.utils.ExcelGenerator;
import org.springframework.stereotype.Service;

@Service
public class ModelDataService implements ModelData {

    @Override
    public void createExampleDataCSV() {
        ExcelGenerator.addOrCreateExcelFile("C:/testdata.xlsx", 100);
        System.out.println("엑셀 파일 생성 완료");
    }
}
