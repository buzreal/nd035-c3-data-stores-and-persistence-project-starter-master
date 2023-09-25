package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.data.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

//    @PostMapping
//    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
//        throw new UnsupportedOperationException();
//    }
//
//    @GetMapping
//    public List<ScheduleDTO> getAllSchedules() {
//        throw new UnsupportedOperationException();
//    }
//
//    @GetMapping("/pet/{petId}")
//    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
//        throw new UnsupportedOperationException();
//    }
//
//    @GetMapping("/employee/{employeeId}")
//    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
//        throw new UnsupportedOperationException();
//    }
//
//    @GetMapping("/customer/{customerId}")
//    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
//        throw new UnsupportedOperationException();
//    }


    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) throws NotFoundException {
        return scheduleService.createSchedule(scheduleDTO);
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable Long petId) throws NotFoundException {
        return scheduleService.getScheduleForPet(petId);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable Long employeeId) throws NotFoundException {
        return scheduleService.getScheduleForEmployee(employeeId);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable Long customerId) throws NotFoundException {
        return scheduleService.getScheduleForCustomer(customerId);
    }
}
