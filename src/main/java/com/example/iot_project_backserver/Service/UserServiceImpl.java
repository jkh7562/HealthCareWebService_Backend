package com.example.iot_project_backserver.Service;

import com.example.iot_project_backserver.Entity.Data.Average.*;
import com.example.iot_project_backserver.Entity.Data.data.BodyTemp;
import com.example.iot_project_backserver.Entity.Data.data.NIBP;
import com.example.iot_project_backserver.Entity.Data.data.SPO2;
import com.example.iot_project_backserver.Entity.User.app_user;
import com.example.iot_project_backserver.Repository.Data.Result.*;
import com.example.iot_project_backserver.Repository.Data.data.*;
import com.example.iot_project_backserver.Repository.Medical.PatientAssignmentRepository;
import com.example.iot_project_backserver.Repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EcgRepository ecgRepository;
    @Autowired
    private AirflowRepository airflowRepository;
    @Autowired
    private EmgRepository emgRepository;
    @Autowired
    private EogRepository eogRepository;
    @Autowired
    private GsrRepository gsrRepository;
    @Autowired
    private BodyTempRepository bodyTempRepository;
    @Autowired
    private Spo2Repository spo2Repository;
    @Autowired
    private NIBPRepository nibpRepository;
    @Autowired
    private PatientAssignmentRepository patientAssignmentRepository;
    private final AirFlow_ResultRepository airFlowResultRepository;
    private final BodyTemp_ResultRepository bodyTempResultRepository;
    private final ECG_ResultRepository ecgResultRepository;
    private final EMG_ResultRepository emgResultRepository;
    private final EOG_ResultRepository eogResultRepository;
    private final GSR_ResultRepository gsrResultRepository;
    private final NIBP_ResultRepository nibpResultRepository;
    private final SPO2_ResultRepository spo2ResultRepository;



    @Override
    public Optional<app_user> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public app_user saveUser(app_user newUser) {
        return userRepository.save(newUser);  // DB에 유저 저장
    }

    @Override
    public boolean existsByUserid(String userid) {
        return userRepository.existsByUserid(userid);  // ID 중복 체크
    }

    @Override
    public List<Map<String, String>> getUserInfoByName(String name) {
        List<String> assignedUserIds = patientAssignmentRepository.findAllAssignedUserIds();
        return userRepository.findByName(name)
                .stream()
                .filter(user -> "Patient".equals(user.getDivision()))
                .filter(user -> !assignedUserIds.contains(user.getUserid())) // assignedUserIds에 포함되지 않은 userid만 반환
                .map(user -> Map.of("userid", user.getUserid(), "name", user.getName()))
                .toList();
    }

    @Override
    public Optional<Map<String, String>> getUserInfoByUserid(String userid) {
        List<String> assignedUserIds = patientAssignmentRepository.findAllAssignedUserIds();
        return userRepository.findById(userid)
                .filter(user -> "Patient".equals(user.getDivision()))
                .filter(user -> !assignedUserIds.contains(user.getUserid())) // assignedUserIds에 포함되지 않은 userid만 반환
                .map(user -> Map.of("userid", user.getUserid(), "name", user.getName()));
    }
    @Override
    public Optional<app_user> findUserByUserid(String userid) {
        return Optional.ofNullable(userRepository.findByUserid(userid).orElse(null));
    }

    @Override
    public List<Float> getEcgAverageValuesByUserId(String userid) {
        // ECG 엔티티에서 해당 userid를 가진 데이터를 필터링
        return ecgRepository.findByUserid(userid)
                .stream()
                .flatMap(ecg -> ecg.getAverages().stream()) // 평균 리스트를 평탄화
                .map(EcgAverage::getEcgAverageValue) // 평균값만 추출
                .collect(Collectors.toList());
    }
    @Override
    public List<Float> getAirflowAverageValuesByUserId(String userid) {
        // Airflow 엔티티에서 해당 userid를 가진 데이터를 필터링
        return airflowRepository.findByUserid(userid)
                .stream()
                .flatMap(airflow -> airflow.getAverages().stream()) // 평균 리스트를 평탄화
                .map(AirflowAverage::getAirflowAverageValue) // 평균값만 추출
                .collect(Collectors.toList());
    }
    @Override
    public List<Float> getEmgAverageValuesByUserId(String userid) {
        // Emg 엔티티에서 해당 userid를 가진 데이터를 필터링
        return emgRepository.findByUserid(userid)
                .stream()
                .flatMap(emg -> emg.getAverages().stream()) // 평균 리스트를 평탄화
                .map(EmgAverage::getEmgAverageValue) // 평균값만 추출
                .collect(Collectors.toList());
    }
    @Override
    public List<Float> getEogAverageValuesByUserId(String userid) {
        // Eog 엔티티에서 해당 userid를 가진 데이터를 필터링
        return eogRepository.findByUserid(userid)
                .stream()
                .flatMap(eog -> eog.getAverages().stream()) // 평균 리스트를 평탄화
                .map(EogAverage::getEogAverageValue) // 평균값만 추출
                .collect(Collectors.toList());
    }
    @Override
    public List<Float> getGsrAverageValuesByUserId(String userid) {
        // Gsr 엔티티에서 해당 userid를 가진 데이터를 필터링
        return gsrRepository.findByUserid(userid)
                .stream()
                .flatMap(gsr -> gsr.getAverages().stream()) // 평균 리스트를 평탄화
                .map(GsrAverage::getGsrAverageValue) // 평균값만 추출
                .collect(Collectors.toList());
    }
    public List<Float> getTempDataByUserId(String userid) {
        List<BodyTemp> bodyTempList = bodyTempRepository.findByUserid(userid);
        // tempdata만 추출해서 반환
        return bodyTempList.stream()
                .map(BodyTemp::getTempdata)
                .toList();
    }
    public List<Integer> getSPO2DataByUserId(String userid) {
        List<SPO2> spo2List = spo2Repository.findByUserid(userid);
        // spo2data만 추출해서 반환
        return spo2List.stream()
                .map(SPO2::getSpo2data)
                .toList();
    }
    public List<Map<String, Integer>> getNIBPDataByUserId(String userid) {
        List<NIBP> nibpList = nibpRepository.findByUserid(userid);
        // systolic과 diastolic 값을 맵 형태로 반환
        return nibpList.stream()
                .map(nibp -> Map.of(
                        "systolic", nibp.getSystolic(),
                        "diastolic", nibp.getDiastolic()
                ))
                .collect(Collectors.toList());
    }

    public Map<String, Object> getMeasurementData(String userid) {
        Map<String, Object> response = new HashMap<>();
        response.put("airFlowResults", airFlowResultRepository.findByUserid(userid));
        response.put("bodyTempResults", bodyTempResultRepository.findByUserid(userid));
        response.put("ecgResults", ecgResultRepository.findByUserid(userid));
        response.put("emgResults", emgResultRepository.findByUserid(userid));
        response.put("eogResults", eogResultRepository.findByUserid(userid));
        response.put("gsrResults", gsrResultRepository.findByUserid(userid));
        response.put("nibpResults", nibpResultRepository.findByUserid(userid));
        response.put("spo2Results", spo2ResultRepository.findByUserid(userid));
        return response;
    }
}
