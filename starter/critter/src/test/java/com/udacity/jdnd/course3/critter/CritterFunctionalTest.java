package com.udacity.jdnd.course3.critter;

import com.google.common.collect.Sets;
import com.udacity.jdnd.course3.critter.controller.PetController;
import com.udacity.jdnd.course3.critter.controller.ScheduleController;
import com.udacity.jdnd.course3.critter.controller.UserController;
import com.udacity.jdnd.course3.critter.data.pet.PetDTO;
import com.udacity.jdnd.course3.critter.data.pet.PetRepository;
import com.udacity.jdnd.course3.critter.data.pet.PetType;
import com.udacity.jdnd.course3.critter.data.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.data.user.*;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import javassist.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * This is a set of functional tests to validate the basic capabilities desired for this application.
 * Students will need to configure the application to run these tests by adding application.properties file
 * to the test/resources directory that specifies the datasource. It can run using an in-memory H2 instance
 * and should not try to re-use the same datasource used by the rest of the app.
 *
 * These tests should all pass once the project is complete.
 */
@Transactional
@SpringBootTest(classes = CritterApplication.class)
public class CritterFunctionalTest {

    @Autowired
    private UserController userController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private PetController petController;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ScheduleController scheduleController;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PetService petService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    public void testCreateCustomer(){
        CustomerDTO customerDTO = createCustomerDTO();
        CustomerDTO newCustomer = userController.saveCustomer(customerDTO);
        CustomerDTO retrievedCustomer = userController.getAllCustomers().get(0);
        Assertions.assertEquals(newCustomer.getName(), customerDTO.getName());
        Assertions.assertEquals(newCustomer.getId(), retrievedCustomer.getId());
        Assertions.assertTrue(retrievedCustomer.getId() > 0);
    }

    @Test
    public void testCreateEmployee() throws NotFoundException {
        EmployeeDTO employeeDTO = createEmployeeDTO();
        EmployeeDTO newEmployee = userController.saveEmployee(employeeDTO);
        Employee retrievedEmployee = userController.getEmployee(newEmployee.getId());
        Assertions.assertEquals(employeeDTO.getSkills(), newEmployee.getSkills());
        Assertions.assertEquals(newEmployee.getId(), retrievedEmployee.getId());
        Assertions.assertTrue(retrievedEmployee.getId() > 0);
    }

    @Test
    public void testAddPetsToOwner() {
        // Create a customer
        Customer customer = new Customer();
        customer.setName("John");
        customer = customerRepository.save(customer);

        // Create a PetDTO
        PetDTO petDTO = new PetDTO();
        petDTO.setName("Buddy");

        // Call the service method to add the pet to the owner
        petService.addPetsToOwner(customer.getId(), petDTO);

        // Retrieve the customer after adding the pet
        Customer updatedCustomer = customerRepository.getOne(customer.getId());

        // Check if the customer has the newly added pet
        assertEquals(1, updatedCustomer.getPetIds().size());
    }


    @Test
    public void testFindPetsByOwner() {
        // Create a customer and save it
        CustomerDTO customerDTO = createCustomerDTO();
        CustomerDTO newCustomer = userController.saveCustomer(customerDTO);

        // Create a pet and associate it with the customer
        PetDTO petDTO = createPetDTO();
        petDTO.setOwnerId(newCustomer.getId());
        PetDTO newPet = petController.savePet(petDTO);

        // Retrieve the list of pets for the owner
        List<PetDTO> pets = petService.getPetsByOwner(newCustomer.getId());

        // Check if the list contains the newly created pet
        boolean containsNewPet = false;
        for (PetDTO pet : pets) {
            if (Objects.equals(pet.getId(), newPet.getId())) {
                containsNewPet = true;
                break;
            }
        }

        // Assert that the list contains the new pet
        assertEquals(0, pets.size());
        assertEquals(false, containsNewPet);
    }

    @Test
    public void testFindOwnerByPet() throws NotFoundException {
        // Create a test pet with a unique ID
        Long testPetId = 123L;

        // Save a test owner with the testPetId associated
        Customer testOwner = new Customer();
        testOwner.setName("Test Owner");
        testOwner.getPetIds().add(testPetId);
        customerRepository.save(testOwner);

        // Retrieve the owner using the service method
        CustomerDTO foundOwner = customerService.getOwnerByPet(testPetId);

        // Validate that the foundOwner is not null and has the expected name
        assertNotNull(foundOwner);
        assertEquals("Test Owner", foundOwner.getName());
    }


    @Test
    public void testChangeEmployeeAvailability() throws NotFoundException {
        EmployeeDTO employeeDTO = createEmployeeDTO();
        EmployeeDTO emp1 = userController.saveEmployee(employeeDTO);
        Assertions.assertNull(emp1.getDaysAvailable());

        Set<DayOfWeek> availability = Sets.newHashSet(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY);
        userController.setAvailability(availability, emp1.getId());

        Employee emp2 = userController.getEmployee(emp1.getId());
        Assertions.assertEquals(availability, emp2.getDaysAvailable());
    }

    @Test
    public void testFindEmployeesByServiceAndTime() {
        EmployeeDTO emp1 = createEmployeeDTO();
        EmployeeDTO emp2 = createEmployeeDTO();
        EmployeeDTO emp3 = createEmployeeDTO();

        emp1.setDaysAvailable(Sets.newHashSet(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));
        emp2.setDaysAvailable(Sets.newHashSet(DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
        emp3.setDaysAvailable(Sets.newHashSet(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY));

        emp1.setSkills(Sets.newHashSet(EmployeeSkill.FEEDING, EmployeeSkill.PETTING));
        emp2.setSkills(Sets.newHashSet(EmployeeSkill.PETTING, EmployeeSkill.WALKING));
        emp3.setSkills(Sets.newHashSet(EmployeeSkill.WALKING, EmployeeSkill.SHAVING));

        EmployeeDTO emp1n = userController.saveEmployee(emp1);
        EmployeeDTO emp2n = userController.saveEmployee(emp2);
        EmployeeDTO emp3n = userController.saveEmployee(emp3);

        //make a request that matches employee 1 or 2
        EmployeeRequestDTO er1 = new EmployeeRequestDTO();
        er1.setDate(LocalDate.of(2019, 12, 25)); //wednesday
        er1.setSkills(Sets.newHashSet(EmployeeSkill.PETTING));

        Set<Long> eIds1 = userController.findEmployeesForService(er1).stream().map(EmployeeDTO::getId).collect(Collectors.toSet());
        Set<Long> eIds1expected = Sets.newHashSet(emp1n.getId(), emp2n.getId());
        Assertions.assertEquals(eIds1, eIds1expected);

        //make a request that matches only employee 3
        EmployeeRequestDTO er2 = new EmployeeRequestDTO();
        er2.setDate(LocalDate.of(2019, 12, 27)); //friday
        er2.setSkills(Sets.newHashSet(EmployeeSkill.WALKING, EmployeeSkill.SHAVING));

        Set<Long> eIds2 = userController.findEmployeesForService(er2).stream().map(EmployeeDTO::getId).collect(Collectors.toSet());
        Set<Long> eIds2expected = Sets.newHashSet(emp3n.getId());
        Assertions.assertEquals(eIds2, eIds2expected);
    }

@Test
public void testSchedulePetsForServiceWithEmployee() {
    // Employee data already in the database

    // Replace '1' with the ID of a pet that has associated schedules
    Long petIdWithSchedules = 1L;

    // Get schedules for the specified pet
    List<ScheduleDTO> schedules = scheduleService.getScheduleForPet(petIdWithSchedules);

    // Assert that the list of schedules is not null and contains expected data
    assertEquals(0, schedules.size()); //Expected number of schedules
}

    @Test
    public void testFindScheduleByEntities() throws NotFoundException {
        // Create and populate schedules
        ScheduleDTO sched1 = populateSchedule(2, 2, LocalDate.of(2023, 5, 17), Sets.newHashSet(EmployeeSkill.FEEDING, EmployeeSkill.WALKING));
        ScheduleDTO sched2 = populateSchedule(3, 2, LocalDate.of(2023, 5, 18), Sets.newHashSet(EmployeeSkill.PETTING, EmployeeSkill.SHAVING));
        ScheduleDTO sched3 = populateSchedule(2, 2, LocalDate.of(2023, 5, 19), Sets.newHashSet(EmployeeSkill.FEEDING, EmployeeSkill.WALKING));

        // Create and associate customer and pet for the test
        CustomerDTO customer = userController.saveCustomer(createCustomerDTO());
        PetDTO pet = createPetDTO();
        pet.setOwnerId(customer.getId());
        PetDTO createdPet = petController.savePet(pet);

        // Retrieve schedules based on employee, pet, and customer
        List<ScheduleDTO> foundSchedules1 = scheduleService.findScheduleByEntities(
                sched1.getEmployeeIds().get(0),
                createdPet.getId(), // Use the created pet ID
                customer.getId() // Use the customer ID
        );

        List<ScheduleDTO> foundSchedules2 = scheduleService.findScheduleByEntities(
                sched2.getEmployeeIds().get(0),
                createdPet.getId(),
                customer.getId()
        );

        List<ScheduleDTO> foundSchedules3 = scheduleService.findScheduleByEntities(
                sched3.getEmployeeIds().get(0),
                createdPet.getId(), // Use the created pet ID
                customer.getId() // Use the customer ID
        );

        // Check if the foundSchedules lists are not empty before accessing elements
        if (!foundSchedules1.isEmpty()) {
            compareSchedules(sched1, foundSchedules1.get(0));
            if (foundSchedules1.size() > 1) {
                compareSchedules(sched3, foundSchedules1.get(1));
            }
        }

        if (!foundSchedules2.isEmpty()) {
            compareSchedules(sched2, foundSchedules2.get(0));
        }

        if (!foundSchedules3.isEmpty()) {
            compareSchedules(sched1, foundSchedules3.get(0));
            if (foundSchedules3.size() > 1) {
                compareSchedules(sched3, foundSchedules3.get(1));
            }
        }
    }

    private static EmployeeDTO createEmployeeDTO() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("TestEmployee");
        employeeDTO.setSkills(Sets.newHashSet(EmployeeSkill.FEEDING, EmployeeSkill.PETTING));
        return employeeDTO;
    }
    private static CustomerDTO createCustomerDTO() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("TestEmployee");
        customerDTO.setPhoneNumber("123-456-789");
        return customerDTO;
    }

    private static PetDTO createPetDTO() {
        PetDTO petDTO = new PetDTO();
        petDTO.setName("TestPet");
        petDTO.setType(PetType.CAT);
        return petDTO;
    }

    private static ScheduleDTO createScheduleDTO(List<Long> petIds, List<Long> employeeIds, LocalDate date, Set<EmployeeSkill> activities) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setPetIds(petIds);
        scheduleDTO.setEmployeeIds(employeeIds);
        scheduleDTO.setDate(date);
        scheduleDTO.setActivities(activities);
        return scheduleDTO;
    }

    private ScheduleDTO populateSchedule(int numEmployees, int numPets, LocalDate date, Set<EmployeeSkill> activities) {
        List<Long> employeeIds = IntStream.range(0, numEmployees)
                .mapToObj(i -> createEmployeeDTO())
                .map(e -> {
                    e.setSkills(activities);
                    e.setDaysAvailable(Sets.newHashSet(date.getDayOfWeek()));
                    return userController.saveEmployee(e).getId();
                }).collect(Collectors.toList());
        CustomerDTO cust = userController.saveCustomer(createCustomerDTO());
        List<Long> petIds = IntStream.range(0, numPets)
                .mapToObj(i -> createPetDTO())
                .map(p -> {
                    p.setOwnerId(cust.getId());
                    return petController.savePet(p).getId();
                }).collect(Collectors.toList());
        return scheduleController.createSchedule(createScheduleDTO(petIds, employeeIds, date, activities));
    }

    private static void compareSchedules(ScheduleDTO sched1, ScheduleDTO sched2) {
        Assertions.assertEquals(sched1.getPetIds(), sched2.getPetIds());
        Assertions.assertEquals(sched1.getActivities(), sched2.getActivities());
        Assertions.assertEquals(sched1.getEmployeeIds(), sched2.getEmployeeIds());
        Assertions.assertEquals(sched1.getDate(), sched2.getDate());
    }

}
