package com.example.iot_project_backserver.Repository.Medical;

import com.example.iot_project_backserver.Entity.User.required_measurements;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface RequiredMeasurementsRepository extends JpaRepository<required_measurements, String> {
    @Modifying
    @Transactional
    void deleteByUserid(String userid);
}
