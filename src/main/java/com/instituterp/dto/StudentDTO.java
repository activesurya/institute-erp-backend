package com.instituterp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private Long id;
    private String rollNumber;
    private String admissionNumber;
    private String dateOfAdmission;
    private String address;
    private String parentContact;
    private String parentName;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDTO user;
}
