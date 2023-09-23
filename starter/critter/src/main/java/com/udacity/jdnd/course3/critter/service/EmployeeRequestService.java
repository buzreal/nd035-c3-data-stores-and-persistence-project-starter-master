package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.user.EmployeeRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeRequestService {
    @Autowired
    private EmployeeRequestRepository employeeRequestRepository;

    public EmployeeRequestService(EmployeeRequestRepository employeeRequestRepository) {
        this.employeeRequestRepository = employeeRequestRepository;
    }


}
