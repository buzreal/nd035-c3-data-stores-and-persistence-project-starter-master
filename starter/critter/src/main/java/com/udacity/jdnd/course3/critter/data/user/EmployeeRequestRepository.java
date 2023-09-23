package com.udacity.jdnd.course3.critter.data.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRequestRepository extends JpaRepository<EmployeeRequest, Long> {
}
