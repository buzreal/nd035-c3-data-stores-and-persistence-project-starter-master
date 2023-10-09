package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.pet.Pet;
import com.udacity.jdnd.course3.critter.data.pet.PetRepository;
import com.udacity.jdnd.course3.critter.data.schedule.Schedule;
import com.udacity.jdnd.course3.critter.data.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.data.schedule.ScheduleRepository;
import com.udacity.jdnd.course3.critter.data.user.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return mapSchedulesToDTOs(schedules);
    }

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        // Ensure that pets, employees, and activities exist in the database
        List<Pet> pets = petRepository.findAllById(scheduleDTO.getPetIds());

        // Extract employee IDs from Employee objects
        List<Long> employeeIds = scheduleDTO.getEmployeeIds();

        // Map ScheduleDTO to Schedule entity
        Schedule schedule = mapDTOToSchedule(scheduleDTO, pets, employeeIds);

        // Save the schedule entity to the database
        Schedule savedSchedule = scheduleRepository.save(schedule);

        // Map the saved schedule back to DTO and return it
        return mapScheduleToDTO(savedSchedule);
    }


    public List<ScheduleDTO> findScheduleByEntities(Long employeeId, Long petId, Long customerId) {
        List<Schedule> schedules = scheduleRepository.findByEmployeeIdOrPetIdOrCustomerId(employeeId, petId, customerId);

        return schedules.stream()
                .map(this::mapScheduleToDTO)
                .collect(Collectors.toList());
    }



    public List<ScheduleDTO> getScheduleForPet(Long petId) {
        // Retrieve schedules associated with the specified petId
        List<Schedule> schedules = scheduleRepository.findByPetId(petId);

        // Map the Schedule entities to DTOs
        return schedules.stream()
                .map(this::mapScheduleToDTO)
                .collect(Collectors.toList());
    }


    public List<ScheduleDTO> getScheduleForEmployee(Long employeeId) throws NotFoundException {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            throw new NotFoundException("Employee not found");
        }
        List<Schedule> schedules = scheduleRepository.findByEmployee(employee);
        return mapSchedulesToDTOs(schedules);
    }

    public List<ScheduleDTO> getScheduleForCustomer(Long customerId) throws NotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            throw new NotFoundException("Customer (Pet Owner) not found");
        }
        List<Schedule> schedules = scheduleRepository.findByCustomer(customer);
        return mapSchedulesToDTOs(schedules);
    }

    private List<ScheduleDTO> mapSchedulesToDTOs(List<Schedule> schedules) {
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Schedule schedule : schedules) {
        scheduleDTOs.add(mapScheduleToDTO(schedule));
        }
        return scheduleDTOs;
}


    private ScheduleDTO mapScheduleToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(schedule.getActivities());

        scheduleDTO.setEmployeeIds(schedule.getEmployeeIds());

        // Convert pet IDs to Long
        List<Long> petIds = new ArrayList<>();
        for (Pet pet : schedule.getPets()) {
            petIds.add(pet.getId());
        }
        scheduleDTO.setPetIds(petIds);

        return scheduleDTO;
    }




    private Schedule mapDTOToSchedule(ScheduleDTO scheduleDTO, List<Pet> pets, List<Long> employees) {
        Schedule schedule = new Schedule();
        schedule.setDate(scheduleDTO.getDate());
        schedule.setActivities(scheduleDTO.getActivities());
        schedule.setPets(pets);
        schedule.setEmployeeIds(employees);
        return schedule;
    }


}

