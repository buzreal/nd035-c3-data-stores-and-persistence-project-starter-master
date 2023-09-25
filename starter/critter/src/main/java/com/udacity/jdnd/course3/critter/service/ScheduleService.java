package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.pet.Pet;
import com.udacity.jdnd.course3.critter.data.pet.PetRepository;
import com.udacity.jdnd.course3.critter.data.schedule.Schedule;
import com.udacity.jdnd.course3.critter.data.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.data.schedule.ScheduleRepository;
import com.udacity.jdnd.course3.critter.data.user.Customer;
import com.udacity.jdnd.course3.critter.data.user.CustomerRepository;
import com.udacity.jdnd.course3.critter.data.user.Employee;
import com.udacity.jdnd.course3.critter.data.user.EmployeeRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return mapSchedulesToDTOs(schedules);
    }

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) throws NotFoundException {
        Pet pet = (Pet) petRepository.findById(scheduleDTO.getPetIds())
                .orElseThrow(() -> new NotFoundException("Pet not found"));
        Employee employee = employeeRepository.findById(scheduleDTO.getEmployeeIds())
                .orElseThrow(() -> new NotFoundException("Employee not found"));

        Schedule schedule = new Schedule();
        schedule.setPets((List<Pet>) pet);
        schedule.setEmployee(employee);
        schedule.setDate(scheduleDTO.getDate());
        schedule.setActivities(scheduleDTO.getActivities());

        scheduleRepository.save(schedule);
        return mapScheduleToDTO(schedule);
    }

    public List<ScheduleDTO> getScheduleForPet(Long petId) throws NotFoundException {
        Pet pet = petRepository.findById(petId).orElse(null);
        if (pet == null) {
            throw new NotFoundException("Pet not found");
        }
        List<Schedule> schedules = scheduleRepository.findByPet(pet);
        return mapSchedulesToDTOs(schedules);
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
        return schedules.stream()
                .map(this::mapScheduleToDTO)
                .collect(Collectors.toList());
    }

    private ScheduleDTO mapScheduleToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setPetIds(schedule.getPetIds());
        scheduleDTO.setEmployeeIds(schedule.getEmployeeIds());
        scheduleDTO.setActivities(schedule.getActivities());
        scheduleDTO.setDate(schedule.getDate());
        return scheduleDTO;
    }

}

