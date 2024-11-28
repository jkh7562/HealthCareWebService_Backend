package com.example.iot_project_backserver.Service;

import com.example.iot_project_backserver.Entity.User.app_user;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    Optional<app_user> getUserById(String id);       // ID로 유저 가져오기
    app_user saveUser(app_user newUser);                 // 유저 저장 메소드 추가
    boolean existsByUserid(String userid);
    List<Map<String, String>> getUserInfoByName(String name);
    Optional<Map<String, String>> getUserInfoByUserid(String userid);
    Optional<app_user> findUserByUserid(String userid);
    List<Float> getEcgAverageValuesByUserId(String userid);
    List<Float> getAirflowAverageValuesByUserId(String userid);
    List<Float> getEmgAverageValuesByUserId(String userid);
    List<Float> getEogAverageValuesByUserId(String userid);
    List<Float> getGsrAverageValuesByUserId(String userid);
    List<Float> getTempDataByUserId(String userid);
    List<Integer> getSPO2DataByUserId(String userid);
    List<Map<String, Integer>> getNIBPDataByUserId(String userid);
    Map<String, Object> getMeasurementData(String userid);
}
