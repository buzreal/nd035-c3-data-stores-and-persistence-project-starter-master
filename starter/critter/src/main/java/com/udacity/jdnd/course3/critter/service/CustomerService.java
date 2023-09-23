package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.user.Customer;
import com.udacity.jdnd.course3.critter.data.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.data.user.CustomerRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

//    public CustomerDTO getCustomerById(Long customerId) {
//        Customer customer = customerRepository.findById(customerId);
//
//        // Convert Customer to CustomerDTO
//        CustomerDTO customerDTO = new CustomerDTO();
//        customerDTO.setId(customer.getId());
//        customerDTO.setName(customer.getName());
//        customerDTO.setPhoneNumber(customer.getPhoneNumber());
//        customerDTO.setNotes(customer.getNotes());
//
//        return customerDTO;
//    }

    // Other service methods

    // Example method to create a customer
    public Long createCustomer(CustomerDTO customerDTO) {
        // Convert CustomerDTO to Customer
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setNotes(customerDTO.getNotes());
        customer.setPetIds(customerDTO.getPetIds());

        // Save the customer in the repository
        Customer savedCustomer = customerRepository.save(customer);

        return savedCustomer.getId();
    }

//    public List<Customer> getAllCustomers() {
//        return customerRepository.findAll();
//    }
//
    public Customer getCustomerById(Long id) throws NotFoundException {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet owner not found"));
    }

//
//    public Customer createCustomer(Customer customer) {
//        return customerRepository.save(customer);
//    }
//

    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        // Map CustomerDTO to Customer entity
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setNotes(customerDTO.getNotes());
        customer.setPetIds(customerDTO.getPetIds());

        // Save the customer entity to the database
        Customer savedCustomer = customerRepository.save(customer);

        // Map the saved Customer entity back to CustomerDTO
        CustomerDTO savedCustomerDTO = new CustomerDTO();
        savedCustomerDTO.setId(savedCustomer.getId());
        savedCustomerDTO.setName(savedCustomer.getName());
        savedCustomerDTO.setPhoneNumber(savedCustomer.getPhoneNumber());
        savedCustomerDTO.setNotes(savedCustomer.getNotes());
        savedCustomerDTO.setPetIds(savedCustomer.getPetIds());

        return savedCustomerDTO;
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }


    public List<CustomerDTO> getAllCustomerDTOs() {
        // Retrieve a list of Customer entities from the database
        List<Customer> customers = customerRepository.findAll();

        // Convert the Customer entities to CustomerDTOs
        List<CustomerDTO> customerDTOs = customers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return customerDTOs;
    }

    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setNotes(customer.getNotes());
        customerDTO.setPetIds(customer.getPetIds());
        // Map other fields as needed
        return customerDTO;
    }

}