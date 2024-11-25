package com.example.iot_project_backserver.service;

import com.example.iot_project_backserver.entity.*;
import com.example.iot_project_backserver.exception.CustomException;
import com.example.iot_project_backserver.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class HealthDataServiceImpl implements HealthDataService {

    private final AirflowRepository airflowRepository;
    private final BodyTempRepository bodyTempRepository;
    private final EogRepository eogRepository;
    private final EcgRepository ecgRepository;
    private final UserRepository userRepository;

    @Autowired
    public HealthDataServiceImpl(AirflowRepository airflowRepository,
                                 BodyTempRepository bodyTempRepository,
                                 EogRepository eogRepository,
                                 EcgRepository ecgRepository,
                                 UserRepository userRepository) {
        this.airflowRepository = airflowRepository;
        this.bodyTempRepository = bodyTempRepository;
        this.eogRepository = eogRepository;
        this.ecgRepository = ecgRepository;
        this.userRepository = userRepository;
    }

    // 공통 처리 로직: 제네릭 메서드로 구현
    private <T, A> void processAndSaveData(
            String userId,
            T data,
            int groupSize,
            Function<List<Float>, List<A>> averageConverter,
            BiConsumer<T, List<A>> setAverageConsumer,
            Consumer<T> saveEntityConsumer,
            Supplier<Optional<T>> existingDataSupplier) {

        // 사용자 ID 검증
        validateUserId(userId);

        // 데이터 리스트 가져오기
        List<Float> dataList = getDataList(data);

        // 평균값 계산
        List<Float> averages = calculateAverages(dataList, groupSize);

        // 평균값 변환
        List<A> averageEntities = averageConverter.apply(averages);

        // 기존 데이터 확인
        Optional<T> existingDataOptional = existingDataSupplier.get();
        if (existingDataOptional.isPresent()) {
            // 기존 데이터 업데이트
            T existingData = existingDataOptional.get();
            setAverageConsumer.accept(existingData, averageEntities);
            saveEntityConsumer.accept(existingData);
        } else {
            // 새로운 데이터 추가
            setAverageConsumer.accept(data, averageEntities);
            saveEntityConsumer.accept(data);
        }
    }

    // ECG 데이터 처리 및 저장
    @Override
    public void processAndSaveECGData(ECG ecg) {
        processAndSaveData(
                ecg.getUserId(),
                ecg,
                250, // 그룹 크기
                averages -> averages.stream()
                        .map(avg -> {
                            EcgAverage ecgAverage = new EcgAverage();
                            ecgAverage.setAverageValue(avg);
                            ecgAverage.setUserId(ecg.getUserId());
                            return ecgAverage;
                        })
                        .collect(Collectors.toList()),
                ECG::setAverages,
                ecgRepository::save,
                () -> ecgRepository.findOneByUserId(ecg.getUserId())
        );
    }

    // BodyTemp 데이터 처리 및 저장
    @Override
    public BodyTemp saveBodyTempData(BodyTemp bodyTemp) {
        processAndSaveData(
                bodyTemp.getUserId(),
                bodyTemp,
                1, // 그룹 크기 (BodyTemp는 평균 계산이 필요 없음)
                averages -> new ArrayList<>(), // BodyTemp는 평균값 엔티티 필요 없음
                (data, averages) -> data.setPandan(BodyTempStatus(data.getBodydata())),
                bodyTempRepository::save,
                () -> bodyTempRepository.findOneByUserId(bodyTemp.getUserId())
        );
        return bodyTemp;
    }

    // Eog 데이터 저장
    @Override
    public Eog saveEogData(Eog eog) {
        validateUserId(eog.getUserId());
        return eogRepository.save(eog);
    }

    // Airflow 데이터 저장
    @Override
    public Airflow saveAirflow(Airflow airflow) {
        validateUserId(airflow.getUserId());
        return airflowRepository.save(airflow);
    }

    // 모든 BodyTemp 데이터 가져오기
    @Override
    public List<BodyTemp> getAllBodyTempData() {
        return bodyTempRepository.findAll();
    }

    // 모든 Eog 데이터 가져오기
    @Override
    public List<Eog> getAllEogData() {
        return eogRepository.findAll();
    }

    // 모든 Airflow 데이터 가져오기
    @Override
    public List<Airflow> getAllAirflowData() {
        return airflowRepository.findAll();
    }

    // ID 유효성 검사
    private void validateUserId(String userId) {
        if (!userRepository.existsByUserId(userId)) {
            throw new CustomException("유효하지 않은 사용자 ID입니다.");
        }
    }

    // 데이터 리스트 추출
    private <T> List<Float> getDataList(T data) {
        if (data instanceof ECG) {
            return ((ECG) data).getEcgdata();
        }
        throw new IllegalArgumentException("지원되지 않는 데이터 타입: " + data.getClass().getName());
    }

    // 평균값 계산
    private List<Float> calculateAverages(List<Float> data, int groupSize) {
        List<Float> averages = new ArrayList<>();
        int dataSize = data.size();
        for (int i = 0; i < dataSize; i += groupSize) {
            List<Float> group = data.subList(i, Math.min(i + groupSize, dataSize));
            float average = (float) group.stream().mapToDouble(Float::doubleValue).average().orElse(0.0);
            averages.add(average);
        }
        return averages;
    }

    // BodyTemp 상태 판단
    private String BodyTempStatus(float bodydata) {
        if (bodydata >= 36.5 && bodydata <= 37.5) {
            return "정상";
        } else if (bodydata > 37.5 && bodydata <= 38.0) {
            return "미열";
        } else if (bodydata > 38.0) {
            return "고열";
        } else if (bodydata < 36.0 && bodydata >= 35.5) {
            return "체온낮음";
        } else if (bodydata < 35.5) {
            return "저체온";
        } else {
            return "알 수 없음";
        }
    }
}
