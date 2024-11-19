package com.example.iot_project_backserver.service;

import com.example.utils.ExcelGenerator;
import org.springframework.stereotype.Service;

@Service
public class ModelDataService implements ModelData {

    @Override
    public void createExampleDataCSV() {
        //ExcelGenerator.addOrCreateExcelFile("C:\\Users\\sunmoon\\IdeaProjects\\IOT_Back_Server/testdata.xlsx", 1); //윈도우 환경
        //IdeaProjects/IOT_Back_Server/path_to_new_data_file.xlsx
        ExcelGenerator.addOrCreateExcelFile("C:\\Users\\sunmoon\\IdeaProjects\\IOT_Back_Server/path_to_new_data_file.xlsx", 1);
        //ExcelGenerator.addOrCreateExcelFile("/Users/choejongsu/testdata.xlsx", 5); //맥 환경
        System.out.println("엑셀 파일 생성 완료");
    }
}
