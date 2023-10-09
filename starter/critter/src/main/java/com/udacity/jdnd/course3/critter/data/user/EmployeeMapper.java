package com.udacity.jdnd.course3.critter.data.user;

public class EmployeeMapper {

    public static EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSkills(employee.getSkills());
        // Map other fields as needed
        return employeeDTO;
    }

    public static Employee convertToEntity(EmployeeDTO employeeDTO ) {
        Employee employee = new Employee();
        employee.setSkills(employeeDTO.getSkills());
        // Map other fields as needed
        return employee;
    }
}
