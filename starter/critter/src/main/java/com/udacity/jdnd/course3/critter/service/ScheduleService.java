package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.schedule.Schedule;
import com.udacity.jdnd.course3.critter.data.schedule.ScheduleRepository;
import com.udacity.jdnd.course3.critter.data.user.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> getSchedulesByEmployee(Employee id) {
        Employee employeeSchedules = (Employee) scheduleRepository.findByEmployee(id); // Retrieve employee by ID from repository
        return scheduleRepository.findByEmployee(employeeSchedules);
    }

    public List<Schedule> getSchedulesByTimeRange(Date start, Date end) {
        return scheduleRepository.findByStartTimeBetween(start, end);
    }

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }
}
