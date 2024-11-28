package com.example.iot_project_backserver.Service;

import com.example.iot_project_backserver.Entity.User.app_user;
import com.example.iot_project_backserver.Repository.Medical.PatientAssignmentRepository;
import com.example.iot_project_backserver.Repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PatientAssignmentRepository patientAssignmentRepository;

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
}
