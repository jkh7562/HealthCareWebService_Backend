package com.example.iot_project_backserver.Service;

import com.example.iot_project_backserver.Entity.*;
import com.example.iot_project_backserver.exception.CustomException;
import com.example.iot_project_backserver.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
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

    private final BodyTemp_ResultRepository bodyTempResultRepository;
    private final NIBP_ResultRepository nibpResultRepository;
    private final SPO2_ResultRepository spo2ResultRepository;
    private final ECG_ResultRepository ecgResultRepository;
    private final EMG_ResultRepository emgResultRepository;
    private final EOG_ResultRepository eogResultRepository;
    private final GSR_ResultRepository gsrResultRepository;
    private final AirFlow_ResultRepository airFlowResultRepository;


    @Autowired
    public HealthDataServiceImpl(AirflowRepository airflowRepository,
                                 BodyTempRepository bodyTempRepository,
                                 EogRepository eogRepository,
                                 EcgRepository ecgRepository,
                                 EmgRepository emgRepository,
                                 GsrRepository gsrRepository,
                                 NIBPRepository nibpRepository,
                                 Spo2Repository spo2Repository,
                                 ECG_ResultRepository ecgResultRepository,
                                 BodyTemp_ResultRepository bodyTempResultRepository,
                                 NIBP_ResultRepository nibpResultRepository,
                                 SPO2_ResultRepository spo2ResultRepository,
                                 EMG_ResultRepository emgResultRepository,
                                 EOG_ResultRepository eogResultRepository,
                                 GSR_ResultRepository gsrResultRepository,
                                 AirFlow_ResultRepository airFlowResultRepository,
                                 UserRepository userRepository) {
        this.airflowRepository = airflowRepository;
        this.bodyTempRepository = bodyTempRepository;
        this.eogRepository = eogRepository;
        this.ecgRepository = ecgRepository;
        this.emgRepository = emgRepository;
        this.gsrRepository = gsrRepository;
        this.nibpRepository = nibpRepository;
        this.spo2Repository = spo2Repository;
        this.ecgResultRepository = ecgResultRepository;
        this.bodyTempResultRepository = bodyTempResultRepository;
        this.nibpResultRepository = nibpResultRepository;
        this.spo2ResultRepository = spo2ResultRepository;
        this.emgResultRepository = emgResultRepository;
        this.eogResultRepository = eogResultRepository;
        this.gsrResultRepository = gsrResultRepository;
        this.airFlowResultRepository = airFlowResultRepository;
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
    @Override
    public void saveOrUpdateBodyTempResult(BodyTemp bodyTemp) {
        validateUserid(bodyTemp.getUserid());

        // BodyTemp 상태를 판단
        String status = BodyTempStatus(bodyTemp.getTempdata());

        // 동일한 사용자 ID와 날짜가 있는 데이터 검색
        Optional<BodyTemp_Result> existingResult = bodyTempResultRepository.findByUseridAndDate(bodyTemp.getUserid(), new Date());

        if (existingResult.isPresent()) {
            // 기존 데이터가 있으면 업데이트
            BodyTemp_Result resultToUpdate = existingResult.get();
            resultToUpdate.setBodyTempResult(status);
            bodyTempResultRepository.save(resultToUpdate);
            System.out.println("Updated record for userid: " + bodyTemp.getUserid() + ", date: " + resultToUpdate.getDate());
        } else {
            // 기존 데이터가 없으면 새로 저장
            BodyTemp_Result newResult = new BodyTemp_Result();
            newResult.setUserid(bodyTemp.getUserid());
            newResult.setBodyTempResult(status);
            newResult.setDate(new Date());
            bodyTempResultRepository.save(newResult);
            System.out.println("Saved new record for userid: " + bodyTemp.getUserid() + ", date: " + newResult.getDate());
        }
    }


    //spo2 데이터 저장
    @Override
    public SPO2 saveSPO2Data(SPO2 spo2) {
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
    public void saveOrUpdateSPO2Result(SPO2 spo2) {
        validateUserid(spo2.getUserid());

        // SPO2 상태를 판단
        String status = SPO2Status(spo2.getSpo2data());

        // 동일한 사용자 ID와 날짜가 있는 데이터 검색
        Optional<SPO2_Result> existingResult = spo2ResultRepository.findByUseridAndDate(spo2.getUserid(), new Date());

        if (existingResult.isPresent()) {
            // 기존 데이터가 있으면 업데이트
            SPO2_Result resultToUpdate = existingResult.get();
            resultToUpdate.setSPO2Result(status);
            spo2ResultRepository.save(resultToUpdate);
            System.out.println("Updated record for userid: " + spo2.getUserid() + ", date: " + resultToUpdate.getDate());
        } else {
            // 기존 데이터가 없으면 새로 저장
            SPO2_Result newResult = new SPO2_Result();
            newResult.setUserid(spo2.getUserid());
            newResult.setSPO2Result(status);
            newResult.setDate(new Date());
            spo2ResultRepository.save(newResult);
            System.out.println("Saved new record for userid: " + spo2.getUserid() + ", date: " + newResult.getDate());
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

    //NIBP_Result 데이터 저장
    @Override
    public void saveOrUpdateNIBPResult(NIBP nibp) {
        validateUserid(nibp.getUserid());

        // NIBP 상태를 판단
        String status = NIBPStatus(nibp.getSystolic(),nibp.getDiastolic());

        // 동일한 사용자 ID와 날짜가 있는 데이터 검색
        Optional<NIBP_Result> existingResult = nibpResultRepository.findByUseridAndDate(nibp.getUserid(), new Date());

        if (existingResult.isPresent()) {
            // 기존 데이터가 있으면 업데이트
            NIBP_Result resultToUpdate = existingResult.get();
            resultToUpdate.setNIBPResult(status);
            nibpResultRepository.save(resultToUpdate);
            System.out.println("Updated record for userid: " + nibp.getUserid() + ", date: " + resultToUpdate.getDate());
        } else {
            // 기존 데이터가 없으면 새로 저장
            NIBP_Result newResult = new NIBP_Result();
            newResult.setUserid(nibp.getUserid());
            newResult.setNIBPResult(status);
            newResult.setDate(new Date());
            nibpResultRepository.save(newResult);
            System.out.println("Saved new record for userid: " + nibp.getUserid() + ", date: " + newResult.getDate());
        }
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
                            airAverage.setAirflowAverageValue(avg);
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
                            eogAverage.setEogAverageValue(avg);
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






    @Override
    public void saveOrUpdateAirflowResult(AirFlow_Result airflowResult) {
        // 동일한 사용자 ID와 날짜가 있는 데이터 검색
        Optional<AirFlow_Result> existingResult = airFlowResultRepository.findByUseridAndDate(airflowResult.getUserid(), airflowResult.getDate());

        if (existingResult.isPresent()) {
            // 기존 데이터가 있으면 업데이트
            AirFlow_Result resultToUpdate = existingResult.get();
            resultToUpdate.setAirFlowResult(airflowResult.getAirFlowResult());
            airFlowResultRepository.save(resultToUpdate);
            System.out.println("Updated record for userid:" + airflowResult.getUserid() + ", date: " + airflowResult.getDate());
        } else {
            // 기존 데이터가 없으면 새로 저장
            airFlowResultRepository.save(airflowResult);
            System.out.println("Saved new record for userid:" + airflowResult.getUserid() + ", date: " + airflowResult.getDate());
        }
    }


    @Override
    public Map<String, Object> callFastAPIAirFlow(Airflow airflow) {
        validateUserid(airflow.getUserid());
        try {
            // FastAPI로 전송할 JSON 데이터 준비
            Map<String, Object> payload = new HashMap<>();
            payload.put("userid", airflow.getUserid());
            payload.put("airflowdata", airflow.getAirflowdata());

            // FastAPI 서버 URL
            String fastApiUrl = "http://127.0.0.1:8082/airflow"; // Airflow 전용 URL

            // RestTemplate 생성 및 JSON Content-Type 설정
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

            // POST 요청 전송
            ResponseEntity<Map> response = restTemplate.postForEntity(fastApiUrl, request, Map.class);

            // FastAPI의 응답 처리
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new RuntimeException("FastAPI call failed with status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error calling FastAPI", e);
        }
    }




    @Override
    public void saveOrUpdateGSRResult(GSR_Result gsrResult) {
        // 동일한 사용자 ID와 날짜가 있는 데이터 검색
        Optional<GSR_Result> existingResult = gsrResultRepository.findByUseridAndDate(gsrResult.getUserid(), gsrResult.getDate());

        if (existingResult.isPresent()) {
            // 기존 데이터가 있으면 업데이트
            GSR_Result resultToUpdate = existingResult.get();
            resultToUpdate.setGsrResult(gsrResult.getGsrResult());
            gsrResultRepository.save(resultToUpdate);
            System.out.println("Updated record for userid:" + gsrResult.getUserid() + ", date: " + gsrResult.getDate());
        } else {
            // 기존 데이터가 없으면 새로 저장
            gsrResultRepository.save(gsrResult);
            System.out.println("Saved new record for userid:" + gsrResult.getUserid() + ", date: " + gsrResult.getDate());
        }
    }

    @Override
    public Map<String, Object> callFastAPIGSR(GSR gsr) {
        validateUserid(gsr.getUserid());
        try {
            // FastAPI로 전송할 JSON 데이터 준비
            Map<String, Object> payload = new HashMap<>();
            payload.put("userid", gsr.getUserid());
            payload.put("gsrdata", gsr.getGsrdata());

            // FastAPI 서버 URL
            String fastApiUrl = "http://127.0.0.1:8082/gsr"; // GSR 전용 URL

            // RestTemplate 생성 및 JSON Content-Type 설정
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

            // POST 요청 전송
            ResponseEntity<Map> response = restTemplate.postForEntity(fastApiUrl, request, Map.class);

            // FastAPI의 응답 처리
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new RuntimeException("FastAPI call failed with status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error calling FastAPI", e);
        }
    }




    @Override
    public void saveOrUpdateEOGResult(EOG_Result eogResult) {
        // 동일한 사용자 ID와 날짜가 있는 데이터 검색
        Optional<EOG_Result> existingResult = eogResultRepository.findByUseridAndDate(eogResult.getUserid(), eogResult.getDate());

        if (existingResult.isPresent()) {
            // 기존 데이터가 있으면 업데이트
            EOG_Result resultToUpdate = existingResult.get();
            resultToUpdate.setEogResult(eogResult.getEogResult());
            eogResultRepository.save(resultToUpdate);
            System.out.println("Updated record for userid:" + eogResult.getUserid() + ", date: " + eogResult.getDate());
        } else {
            // 기존 데이터가 없으면 새로 저장
            eogResultRepository.save(eogResult);
            System.out.println("Saved new record for userid:" + eogResult.getUserid() + ", date: " + eogResult.getDate());
        }
    }
    @Override
    public Map<String, Object> callFastAPIEOG(EOG eog) {
        validateUserid(eog.getUserid());
        try {
            // FastAPI로 전송할 JSON 데이터 준비
            Map<String, Object> payload = new HashMap<>();
            payload.put("userid", eog.getUserid());
            payload.put("eogdata", eog.getEogdata());

            // FastAPI 서버 URL
            String fastApiUrl = "http://127.0.0.1:8082/eog"; // EOG 전용 URL

            // RestTemplate 생성 및 JSON Content-Type 설정
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

            // POST 요청 전송
            ResponseEntity<Map> response = restTemplate.postForEntity(fastApiUrl, request, Map.class);

            // FastAPI의 응답 처리
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new RuntimeException("FastAPI call failed with status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error calling FastAPI", e);
        }
    }





    @Override
    public void saveOrUpdateEMGResult(EMG_Result emgResult) {
        // 동일한 사용자 ID와 날짜가 있는 데이터 검색
        Optional<EMG_Result> existingResult = emgResultRepository.findByUseridAndDate(emgResult.getUserid(), emgResult.getDate());

        if (existingResult.isPresent()) {
            // 기존 데이터가 있으면 업데이트
            EMG_Result resultToUpdate = existingResult.get();
            resultToUpdate.setEmgResult(emgResult.getEmgResult());
            emgResultRepository.save(resultToUpdate);
            System.out.println("Updated record for userid:" + emgResult.getUserid() + ", date: " + emgResult.getDate());
        } else {
            // 기존 데이터가 없으면 새로 저장
            emgResultRepository.save(emgResult);
            System.out.println("Saved new record for userid:" + emgResult.getUserid() + ", date: " + emgResult.getDate());
        }
    }

    @Override
    public Map<String, Object> callFastAPIEMG(EMG emg) {
        validateUserid(emg.getUserid());
        try {
            // FastAPI로 전송할 JSON 데이터 준비
            Map<String, Object> payload = new HashMap<>();
            payload.put("userid", emg.getUserid());
            payload.put("emgdata", emg.getEmgdata());

            // FastAPI 서버 URL
            String fastApiUrl = "http://127.0.0.1:8082/emg"; // EMG 전용 URL

            // RestTemplate 생성 및 JSON Content-Type 설정
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

            // POST 요청 전송
            ResponseEntity<Map> response = restTemplate.postForEntity(fastApiUrl, request, Map.class);

            // FastAPI의 응답 처리
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new RuntimeException("FastAPI call failed with status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error calling FastAPI", e);
        }
    }




    //ECG 판단 데이터 저장
    @Override
    public void saveOrUpdateECGResult(ECG_Result ecgResult) {
        // 동일한 사용자 ID와 날짜가 있는 데이터 검색
        Optional<ECG_Result> existingResult = ecgResultRepository.findByUseridAndDate(ecgResult.getUserid(), ecgResult.getDate());

        if (existingResult.isPresent()) {
            // 기존 데이터가 있으면 업데이트
            ECG_Result resultToUpdate = existingResult.get();
            resultToUpdate.setEcgResult(ecgResult.getEcgResult());
            ecgResultRepository.save(resultToUpdate);
            System.out.println("Updated record for userid:" + ecgResult.getUserid() + ", date: " + ecgResult.getDate());
        } else {
            // 기존 데이터가 없으면 새로 저장
            ecgResultRepository.save(ecgResult);
            System.out.println("Saved new record for userid:" + ecgResult.getUserid() + ", date: " + ecgResult.getDate());
        }
    }

    @Override
    public Map<String, Object> callFastAPIECG(ECG ecg){
        validateUserid(ecg.getUserid());
        try {
            // FastAPI로 전송할 JSON 데이터 준비
            Map<String, Object> payload = new HashMap<>();
            payload.put("userid", ecg.getUserid());
            payload.put("ecgdata", ecg.getEcgdata());

            // FastAPI 서버 URL
            String fastApiUrl = "http://127.0.0.1:8082/ecg";

            // RestTemplate 생성 및 JSON Content-Type 설정
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

            // POST 요청 전송
            ResponseEntity<Map> response = restTemplate.postForEntity(fastApiUrl, request, Map.class);

            // FastAPI의 응답 처리
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new RuntimeException("FastAPI call failed with status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error calling FastAPI", e);
        }
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

    //NIBP 상태 판단
    private String NIBPStatus(int Systolic , int Diastolic) {
        if (Systolic >= 90 && Systolic <= 120 && Diastolic >= 60 && Diastolic <= 80) {
            return "정상";
        } else {
            return "비정상";
        }
    }

    //SPO2 상태 판단
    private String SPO2Status (int spo2){
        if (spo2 >= 95 && spo2 <= 100) {
            return "정상";
        } else {
            return "비정상";
        }
    }

}
