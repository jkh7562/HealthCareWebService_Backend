package com.example.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

public class ExcelGenerator {

    /**
     * 엑셀 파일을 생성하거나 데이터를 추가하는 메서드
     *
     * @param fileName 엑셀 파일 이름
     * @param headers  헤더 데이터
     * @param data     행 데이터
     */
    /*public static void addOrCreateExcelFile(String fileName, List<String> headers, List<Object> data) {
        Workbook workbook;
        Sheet sheet;

        File file = new File(fileName);
        if (file.exists()) {
            // 기존 파일 열기
            try (FileInputStream fis = new FileInputStream(file)) {
                workbook = WorkbookFactory.create(fis);
                sheet = workbook.getSheetAt(0);
            } catch (IOException e) {
                throw new RuntimeException("엑셀 파일 열기 중 오류 발생: " + e.getMessage(), e);
            }
        } else {
            // 새 파일 생성
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("Data");
            createHeader(sheet, headers);
        }

        // 데이터 추가
        int nextRowIndex = sheet.getLastRowNum() + 1;
        addRow(sheet, nextRowIndex, data);

        // 파일 저장
        saveExcelFile(workbook, fileName);
    }*/

    public static void addOrCreateExcelFile(String fileName, List<String> headers, List<Object> data) {
        Workbook workbook;
        Sheet sheet;

        File file = new File(fileName);

        // 파일 존재 여부 확인
        if (file.exists()) {
            // 기존 파일 열기
            try (FileInputStream fis = new FileInputStream(file)) {
                String mimeType = Files.probeContentType(file.toPath());
                if (!"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(mimeType)) {
                    throw new RuntimeException("올바른 엑셀 파일 형식이 아닙니다: " + fileName);
                }
                workbook = WorkbookFactory.create(fis);
                sheet = workbook.getSheetAt(0);
            } catch (IOException e) {
                throw new RuntimeException("엑셀 파일 열기 중 오류 발생: " + e.getMessage(), e);
            }
        } else {
            // 새 파일 생성
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("Data");
            createHeader(sheet, headers);
        }

        // 데이터 추가
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("추가할 데이터가 없습니다.");
        }
        int nextRowIndex = sheet.getLastRowNum() + 1;
        addRow(sheet, nextRowIndex, data);

        // 파일 저장
        saveExcelFile(workbook, fileName);
    }

    private static void createHeader(Sheet sheet, List<String> headers) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.size(); i++) {
            headerRow.createCell(i).setCellValue(headers.get(i));
        }
    }

    private static void addRow(Sheet sheet, int rowIndex, List<Object> data) {
        Row row = sheet.createRow(rowIndex);
        for (int i = 0; i < data.size(); i++) {
            Object value = data.get(i);
            if (value instanceof String) {
                row.createCell(i).setCellValue((String) value);
            } else if (value instanceof Number) {
                row.createCell(i).setCellValue(((Number) value).doubleValue());
            } else {
                row.createCell(i).setCellValue(value != null ? value.toString() : "");
            }
        }
    }

    private static void saveExcelFile(Workbook workbook, String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            workbook.write(fos);
        } catch (IOException e) {
            throw new RuntimeException("엑셀 파일 저장 중 오류 발생: " + e.getMessage(), e);
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
