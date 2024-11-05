package com.example.iot_project_backserver.service;

//import com.example.iot_project_backserver.dto.HealthDataRequestDTO;
import com.example.iot_project_backserver.entity.Airflow;
import com.example.iot_project_backserver.entity.BodyTemp;
import com.example.iot_project_backserver.entity.Eog;
import com.example.iot_project_backserver.exception.CustomException;
import com.example.iot_project_backserver.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthDataServiceImpl implements HealthDataService {

    private final AirflowRepository airflowRepository;
    private final BodyTempRepository bodyTempRepository;
    private final EogRepository eogRepository;
    private final UserRepository userRepository;

    @Autowired
    public HealthDataServiceImpl(AirflowRepository airflowRepository,
                                 BodyTempRepository bodyTempRepository,
                                 EogRepository eogRepository, UserRepository userRepository) {
        this.airflowRepository = airflowRepository;
        this.bodyTempRepository = bodyTempRepository;
        this.eogRepository = eogRepository;
        this.userRepository = userRepository;
    }
     //아이디 값이 유효한지 판단하기 위한 로직


    @Override
    public Airflow saveAirflow(Airflow airflow) {return airflowRepository.save(airflow);
    }

    @Override
    public List<Airflow> getAllAirflowData() {
        return airflowRepository.findAll();
    }

    @Override
    public BodyTemp saveBodyTempData(BodyTemp bodyTemp) {
        // 사용자 ID가 유효한지 확인
        if (!userRepository.existsByUserId(bodyTemp.getUserId())) {
            throw new CustomException("유효하지 않은 사용자 ID입니다."); // 유효하지 않은 ID일 경우 보낼 예외 메시지
        }
        //TODO 데이터를 바로 저장하는 것이 아닌 데이터의 수치 판단이 필요
        return bodyTempRepository.save(bodyTemp); //Id가 유효 할 경우 bodyTemp 데이터 저장
    }

    @Override
    public List<BodyTemp> getAllBodyTempData() {
        return bodyTempRepository.findAll();
    }

    @Override
    public Eog saveEogData(Eog eog) {
        return eogRepository.save(eog);
    }

    @Override
    public List<Eog> getAllEogData() {
        return eogRepository.findAll();
    }




}
