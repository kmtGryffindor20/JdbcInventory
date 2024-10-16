package com.inventory.backend.entities;


import java.sql.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private Long employeeId;

    private String firstName;

    private String lastName;
    
    private String phoneNumber;

    private Date hireDate;

    private String designation;

    private Employee manager;

    private Set<String> emailAddresses;

}
