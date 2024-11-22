package com.example.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

//TODO 임시 엑셀 파일 생성 추후 삭제 또는 수정 요망
public class ExcelGenerator {

    /**
     * 엑셀 파일을 생성하는 메서드
     * @param fileName 생성할 엑셀 파일 이름
     * @param numRows 데이터 행 개수
     */
    public static void addOrCreateExcelFile(String fileName, int numRows) {
        Workbook workbook;
        Sheet sheet;

        File file = new File(fileName);
        if (file.exists()) {
            // 기존 파일 열기
            try (FileInputStream fis = new FileInputStream(file)) {
                workbook = WorkbookFactory.create(fis);
                sheet = workbook.getSheetAt(0); // 첫 번째 시트 사용
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        } else {
            // 새 파일 생성
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("Random Data");
            createHeader(sheet); // 새 파일의 헤더 생성
        }

        // 데이터 추가
        int existingRows = sheet.getLastRowNum(); // 기존 데이터의 마지막 행
        populateData(sheet, existingRows + 1, numRows);

        // 파일 저장
        saveExcelFile(workbook, fileName);
    }

    /**
     * 엑셀 헤더 생성 메서드
     * @param sheet 대상 시트
     */
    private static void createHeader(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");

        double start = 0.002;
        double end = 30.000;
        double step = 0.002;
        int colIndex = 1;

        for (double value = start; value <= end; value += step) {
            headerRow.createCell(colIndex).setCellValue(String.format("%.3f", value));
            colIndex++;
        }
    }

    /**
     * 랜덤 데이터를 시트에 추가하는 메서드
     * @param sheet 대상 시트
     * @param startRow 시작 행 번호
     * @param numRows 추가할 데이터 행 개수
     */
    private static void populateData(Sheet sheet, int startRow, int numRows) {
        Random random = new Random();
        int colCount = sheet.getRow(0).getLastCellNum(); // 헤더의 열 개수

        for (int rowIndex = startRow; rowIndex < startRow + numRows; rowIndex++) {
            Row dataRow = sheet.createRow(rowIndex);
            dataRow.createCell(0).setCellValue("ID_" + rowIndex); // ID 열

            for (int cellIndex = 1; cellIndex < colCount; cellIndex++) {
                dataRow.createCell(cellIndex).setCellValue(8000 + random.nextInt(10000)); // 1~30 사이 랜덤 값
            }
        }
    }

    /**
     * 엑셀 파일 저장 메서드
     * @param workbook 워크북 객체
     * @param fileName 저장할 파일 이름
     */
    private static void saveExcelFile(Workbook workbook, String fileName) {
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
            System.out.println("엑셀 파일이 저장되었습니다: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
