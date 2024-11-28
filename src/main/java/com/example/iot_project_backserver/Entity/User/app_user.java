package com.example.iot_project_backserver.Entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class app_user {

    @Id
    private String userid;
    private String password;
    private String name;
    private String birth;
    private String phone_num;
    private String division;
}
