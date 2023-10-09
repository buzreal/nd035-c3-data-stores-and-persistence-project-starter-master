package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.user.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) throws NotFoundException {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found"));
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public EmployeeDTO setAvailability(Long employeeId, Set<DayOfWeek> daysAvailable) throws NotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee not found"));
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
        return mapEmployeeToDTO(employee);
    }

    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        // Convert the request DTO to an Employee entity
        Employee employee = EmployeeMapper.convertToEntity(employeeDTO);

        // Save the employee in the database
        Employee savedEmployee = employeeRepository.save(employee);

        // Convert the saved Employee entity to a DTO
        return EmployeeMapper.convertToDTO(savedEmployee);
    }

public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO requestDTO) {
    LocalDate date = requestDTO.getDate();
    Set<EmployeeSkill> skills = requestDTO.getSkills();

    return employeeRepository.findAll()
            .stream()
            .filter(employee -> employee.getDaysAvailable().contains(date.getDayOfWeek()))
            .filter(employee -> employee.getSkills().containsAll(skills))
            .map(this::mapEmployeeToDTO)
            .collect(Collectors.toList());
}

    private EmployeeDTO mapEmployeeToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setSkills(employee.getSkills());
        dto.setDaysAvailable(employee.getDaysAvailable());
        return dto;
    }
}
