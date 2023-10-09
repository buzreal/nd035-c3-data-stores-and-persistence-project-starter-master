package com.udacity.jdnd.course3.critter.data.schedule;

import com.udacity.jdnd.course3.critter.data.pet.Pet;
import com.udacity.jdnd.course3.critter.data.user.Customer;
import com.udacity.jdnd.course3.critter.data.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByEmployee(Employee id);


    List<Schedule> findByPetId(Long petId);

    List<Schedule> findByCustomer(Customer customer);

    List<Schedule> findByEmployeeIdOrPetIdOrCustomerId(Long employeeId, Long petId, Long customerId);

}
