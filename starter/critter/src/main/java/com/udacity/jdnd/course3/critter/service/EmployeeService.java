package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.user.Employee;
import com.udacity.jdnd.course3.critter.data.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.data.user.EmployeeRepository;
import com.udacity.jdnd.course3.critter.data.user.EmployeeRequestDTO;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
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

//    public Employee setAvailability(Long id, Set<DayOfWeek> daysAvailable) throws NotFoundException {
//        Employee employee = getEmployeeById(id);
//        employee.setDaysAvailable(daysAvailable);
//        return employeeRepository.save(employee);
//    }
//
//    public List<Employee> findEmployeeForService(DayOfWeek dayOfWeek) {
//        return employeeRepository.findAllByAvailabilityContains(dayOfWeek);
//    }

    public EmployeeDTO setAvailability(Long employeeId, Set<DayOfWeek> daysAvailable) throws NotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee not found"));
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
        return mapEmployeeToDTO(employee);
    }

    public List<EmployeeDTO> findEmployeeForService(EmployeeRequestDTO dayOfWeek) {
        List<Employee> employees = employeeRepository.findAll();
        List<Employee> availableEmployees = employees.stream()
                .filter(employee -> employee.getDaysAvailable().contains(dayOfWeek))
                .collect(Collectors.toList());
        return availableEmployees.stream()
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

    private Employee mapDTOToEmployee(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setSkills(dto.getSkills());
        employee.setDaysAvailable(dto.getDaysAvailable());
        return employee;
    }
}
