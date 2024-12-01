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

    /**
     * Excel 시트의 헤더 행을 생성하는 메서드.
     *
     * @param sheet   헤더를 추가할 Excel 시트 객체.
     * @param headers 헤더에 표시할 문자열 리스트.
     */
    private static void createHeader(Sheet sheet, List<String> headers) {
        // 첫 번째 행(Row)을 생성하여 헤더 행으로 설정
        Row headerRow = sheet.createRow(0);

        // 헤더 리스트의 각 항목을 순회하며 셀(Cell)을 생성하고 값을 설정
        for (int i = 0; i < headers.size(); i++) {
            headerRow.createCell(i).setCellValue(headers.get(i)); // i번째 열에 헤더 값 설정
        }
    }

    /**
     * Excel 시트에 데이터를 추가하는 메서드.
     *
     * @param sheet    데이터를 추가할 Excel 시트 객체.
     * @param rowIndex 데이터를 추가할 행의 인덱스.
     * @param data     행에 입력할 데이터 리스트 (각 열의 값).
     */
    private static void addRow(Sheet sheet, int rowIndex, List<Object> data) {
        // 주어진 행 인덱스에 새 행(Row) 생성
        Row row = sheet.createRow(rowIndex);

        // 데이터 리스트를 순회하며 각 열에 값을 설정
        for (int i = 0; i < data.size(); i++) {
            Object value = data.get(i); // 현재 열의 데이터 가져오기

            if (value instanceof String) {
                // 데이터가 문자열(String)인 경우, 셀에 문자열 값 설정
                row.createCell(i).setCellValue((String) value);
            } else if (value instanceof Number) {
                // 데이터가 숫자(Number)인 경우, 셀에 숫자 값 설정
                row.createCell(i).setCellValue(((Number) value).doubleValue());
            } else {
                // 데이터가 문자열이나 숫자가 아닌 경우, toString()으로 변환하여 셀에 값 설정
                // 값이 null이면 빈 문자열("") 설정
                row.createCell(i).setCellValue(value != null ? value.toString() : "");
            }
        }
    }


    /**
     * Excel 파일을 저장하는 메서드.
     *
     * @param workbook Excel 파일 데이터를 담고 있는 Workbook 객체 (HSSFWorkbook 또는 XSSFWorkbook).
     * @param fileName 저장할 파일의 경로와 이름.
     */
    private static void saveExcelFile(Workbook workbook, String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            // FileOutputStream을 사용하여 지정된 경로에 Excel 데이터를 저장
            workbook.write(fos); // 워크북 내용을 파일로 씀
        } catch (IOException e) {
            // 파일 저장 중 IOException이 발생한 경우 런타임 예외로 래핑하여 던짐
            throw new RuntimeException("엑셀 파일 저장 중 오류 발생: " + e.getMessage(), e);
        } finally {
            // 워크북을 닫아 리소스를 정리
            try {
                workbook.close();
            } catch (IOException e) {
                // 워크북 닫기 중 IOException이 발생한 경우 스택 트레이스를 출력
                e.printStackTrace();
            }
        }
    }
}
