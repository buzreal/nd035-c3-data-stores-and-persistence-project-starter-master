package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.data.user.*;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    public UserController() {
    }

    @Autowired
    private CustomerService customerService;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> customerDTOs = customerService.getAllCustomerDTOs();
        return customerDTOs;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable Long petId) throws NotFoundException {
        return customerService.getOwnerByPet(petId);
    }


    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) throws NotFoundException {
        Customer customer = customerService.getCustomerById(id);
        CustomerDTO customerDTO = mapCustomerToDTO(customer);
        return ResponseEntity.ok(customerDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    // Method to map Customer entity to CustomerDTO
    public CustomerDTO mapCustomerToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setNotes(customer.getNotes());
        customerDTO.setPetIds(customer.getPetIds());
        return customerDTO;
    }


    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.saveEmployee(employeeDTO);
    }

    @PostMapping("/employee/{employeeId}")
    public Employee getEmployee(@PathVariable long employeeId) throws NotFoundException {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee not found"));
    }

    @PutMapping("/employee/{employeeId}")
    public EmployeeDTO setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) throws NotFoundException {

            return employeeService.setAvailability(employeeId, daysAvailable);
        }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@PathVariable EmployeeRequestDTO dayOfWeek) {
        return employeeService.findEmployeesForService(dayOfWeek);
    }

    @PostMapping
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO newCustomer = customerService.saveCustomer(customerDTO);
        return newCustomer;
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomer(@PathVariable Long id) {
        return customerService.getCustomerByOwnerId(id);
    }
}
