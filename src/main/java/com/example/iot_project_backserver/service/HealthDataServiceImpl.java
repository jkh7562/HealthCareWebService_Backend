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
    private final EmgRepository emgRepository;
    private final UserRepository userRepository;
    private final GsrRepository gsrRepository;
    private final NIBPRepository nibpRepository;
    private final Spo2Repository spo2Repository;


    @Autowired
    public HealthDataServiceImpl(AirflowRepository airflowRepository,
                                 BodyTempRepository bodyTempRepository,
                                 EogRepository eogRepository,
                                 EcgRepository ecgRepository,
                                 EmgRepository emgRepository,
                                 GsrRepository gsrRepository,
                                 NIBPRepository nibpRepository,
                                 Spo2Repository spo2Repository,
                                 UserRepository userRepository) {
        this.airflowRepository = airflowRepository;
        this.bodyTempRepository = bodyTempRepository;
        this.eogRepository = eogRepository;
        this.ecgRepository = ecgRepository;
        this.emgRepository = emgRepository;
        this.gsrRepository = gsrRepository;
        this.nibpRepository = nibpRepository;
        this.spo2Repository = spo2Repository;
        this.userRepository = userRepository;
    }

    // 공통 처리 로직: 제네릭 메서드로 구현  리스트 형식 데이터 처리 윟마
    private <T, A> void processAndSaveData(
            String userid,
            T data,
            int groupSize,
            Function<List<Float>, List<A>> averageConverter,
            BiConsumer<T, List<A>> setAverageConsumer,
            Consumer<T> saveEntityConsumer,
            Supplier<Optional<T>> existingDataSupplier) {

        // 사용자 ID 검증
        validateUserid(userid);

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
                ecg.getUserid(),
                ecg,
                250, // 그룹 크기
                averages -> averages.stream()
                        .map(avg -> {
                            EcgAverage ecgAverage = new EcgAverage();
                            ecgAverage.setEcgAverageValue(avg);
                            ecgAverage.setUserid(ecg.getUserid());
                            return ecgAverage;
                        })
                        .collect(Collectors.toList()),
                ECG::setAverages,
                ecgRepository::save,
                () -> ecgRepository.findOneByUserid(ecg.getUserid())
        );
    }

    @Override
    public BodyTemp saveBodyTempData(BodyTemp bodyTemp) {
        validateUserid(bodyTemp.getUserid());

        Optional<BodyTemp> existingBodyTemp = bodyTempRepository.findOneByUserid(bodyTemp.getUserid());
        //TODO 데이터를 바로 저장하는 것이 아닌 데이터의 수치 판단이 필요
        String pandanStatus = BodyTempStatus(bodyTemp.getTempdata());
        bodyTemp.setPandan(pandanStatus);

        if(existingBodyTemp.isPresent()){
            BodyTemp updatedBodyTemp = existingBodyTemp.get();
            updatedBodyTemp.setTempdata(bodyTemp.getTempdata());
            updatedBodyTemp.setPandan(pandanStatus);
            return bodyTempRepository.save(updatedBodyTemp);
        }else {
            return bodyTempRepository.save(bodyTemp);
        }
    }

    //spo2 데이터 저장
    @Override
    public SPO2 saveSPO2(SPO2 spo2) {
        validateUserid(spo2.getUserid());

        Optional<SPO2> existingSpo2 = spo2Repository.findOneByUserid(spo2.getUserid());
        String pandanStatus = SPO2Status(spo2.getSpo2data());
        spo2.setPandan(pandanStatus);

        if(existingSpo2.isPresent()){
            SPO2 updatedSpo2 = existingSpo2.get();
            updatedSpo2.setSpo2data(spo2.getSpo2data());
            updatedSpo2.setPandan(pandanStatus);
            return spo2Repository.save(updatedSpo2);
        } else {
            return spo2Repository.save(spo2);
        }
    }

    @Override
    public NIBP saveNIBPData(NIBP nibp) {
        validateUserid(nibp.getUserid());
        Optional<NIBP> existingNIBP =nibpRepository.findOneByUserid(nibp.getUserid());
        String pandanStatus = NIBPStatus(nibp.getSystolic(), nibp.getDiastolic());
        nibp.setPandan(pandanStatus);

        if(existingNIBP.isPresent()){
            NIBP updatedNIBP = existingNIBP.get();
            updatedNIBP.setSystolic(nibp.getSystolic());
            updatedNIBP.setDiastolic(nibp.getDiastolic());
            updatedNIBP.setPandan(pandanStatus);
            return nibpRepository.save(updatedNIBP);
        } else {
            return nibpRepository.save(nibp);
        }
    }
    // Eog 데이터 저장
    @Override
    public EOG saveEogData(EOG eog) {
        validateUserid(eog.getUserid());
        return eogRepository.save(eog);
    }

    // Airflow 데이터 저장
    @Override
    public Airflow saveAirflow(Airflow airflow) {
        validateUserid(airflow.getUserid());
        return airflowRepository.save(airflow);
    }

    @Override
    public void processAndSaveAirflowData(Airflow airflow) {
        processAndSaveData(
                airflow.getUserid(),
                airflow,
                250, // 그룹 크기
                averages -> averages.stream()
                        .map(avg -> {
                            AirflowAverage airAverage = new AirflowAverage(); // 올바른 클래스 이름
                            airAverage.setAverageValue(avg);
                            airAverage.setUserid(airflow.getUserid());
                            return airAverage;
                        })
                        .collect(Collectors.toList()), // 평균 리스트 생성
                Airflow::setAverages, // 올바른 메서드 참조
                airflowRepository::save, // 저장 메서드 참조
                () -> airflowRepository.findOneByUserid(airflow.getUserid()) // 기존 데이터 확인
        );
    }

    @Override
    public void processAndSaveEOGData(EOG eog) {
        processAndSaveData(
                eog.getUserid(),
                eog,
                250,
                averages -> averages.stream()
                        .map(avg -> {
                            EogAverage eogAverage = new EogAverage();
                            eogAverage.setAverageValue(avg);
                            eogAverage.setUserid(eog.getUserid());
                            return eogAverage;
                        })
                        .collect(Collectors.toList()),
                EOG::setAverages,
                eogRepository::save,
                () -> eogRepository.findOneByUserid(eog.getUserid())
        );
    }

    @Override
    public void processAndSaveEMGData(EMG emg) {
        processAndSaveData(
                emg.getUserid(),
                emg,
                250,
                averages -> averages.stream()
                        .map(avg -> {
                            EmgAverage emgAverage = new EmgAverage();
                            emgAverage.setEmgAverageValue(avg);
                            emgAverage.setUserid(emg.getUserid());
                            return emgAverage;
                        })
                        .collect(Collectors.toList()),
                EMG::setAverages,
                emgRepository::save,
                () -> emgRepository.findOneByUserid(emg.getUserid())
        );
    }


    @Override
    public void processAndSaveGSRData(GSR gsr) {
        processAndSaveData(
                gsr.getUserid(),
                gsr,
                250,
                averages -> averages.stream()
                        .map(avg -> {
                            GsrAverage gsrAverage = new GsrAverage();
                            gsrAverage.setGsrAverageValue(avg);
                            gsrAverage.setUserid(gsr.getUserid());
                            return gsrAverage;
                        })
                        .collect(Collectors.toList()),
                GSR::setAverages,
                gsrRepository::save,
                () -> gsrRepository.findOneByUserid(gsr.getUserid())
        );
    }




    // 모든 BodyTemp 데이터 가져오기
    @Override
    public List<BodyTemp> getAllBodyTempData() {
        return bodyTempRepository.findAll();
    }

    // 모든 Eog 데이터 가져오기
    @Override
    public List<EOG> getAllEogData() {
        return eogRepository.findAll();
    }

    // 모든 Airflow 데이터 가져오기
    @Override
    public List<Airflow> getAllAirflowData() {
        return airflowRepository.findAll();
    }

    // ID 유효성 검사
    private void validateUserid(String userid) {
        if (!userRepository.existsByUserid(userid)) {
            throw new CustomException("유효하지 않은 사용자 ID입니다.");
        }
    }

    // 데이터 리스트 추출
    private <T> List<Float> getDataList(T data) {
        if (data instanceof ECG) {
            return ((ECG) data).getEcgdata();
        } else if (data instanceof Airflow) {
            return ((Airflow) data).getAirflowdata();
        } else if (data instanceof EOG) {
            return ((EOG) data).getEogdata();
        } else if (data instanceof EMG) {
            return ((EMG) data).getEmgdata();
        } else if (data instanceof GSR) {
            return ((GSR) data).getGsrdata();
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
        }else {
            return "비정상";
        }
    }

    private String NIBPStatus(int Systolic , int Diastolic) {
        if (Systolic >= 90 && Systolic <= 120 && Diastolic >= 60 && Diastolic <= 80) {
            return "정상";
        } else {
            return "비정상";
        }
    }

    private String SPO2Status (int spo2){
        if (spo2 >= 95 && spo2 <= 100) {
            return "정상";
        } else {
            return "비정상";
        }
    }
}
