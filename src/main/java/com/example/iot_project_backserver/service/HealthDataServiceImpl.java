package com.example.iot_project_backserver.service;

import com.example.iot_project_backserver.entity.Airflow;
import com.example.iot_project_backserver.entity.BodyTemp;
import com.example.iot_project_backserver.entity.Eog;
import com.example.iot_project_backserver.repository.AirflowRepository;
import com.example.iot_project_backserver.repository.BodyTempRepository;
import com.example.iot_project_backserver.repository.EogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthDataServiceImpl implements HealthDataService {

    private final AirflowRepository airflowRepository;
    private final BodyTempRepository bodyTempRepository;
    private final EogRepository eogRepository;

    @Autowired
    public HealthDataServiceImpl(AirflowRepository airflowRepository,
                                 BodyTempRepository bodyTempRepository,
                                 EogRepository eogRepository) {
        this.airflowRepository = airflowRepository;
        this.bodyTempRepository = bodyTempRepository;
        this.eogRepository = eogRepository;
    }


    @Override
    public Airflow saveAirflow(Airflow airflow) {
        return airflowRepository.save(airflow);
    }

    @Override
    public List<Airflow> getAllAirflowData() {
        return airflowRepository.findAll();
    }

    @Override
    public BodyTemp saveBodyTempData(BodyTemp bodyTemp) {
        return bodyTempRepository.save(bodyTemp);
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
