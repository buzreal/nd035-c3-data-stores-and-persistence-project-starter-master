package com.udacity.jdnd.course3.critter.data.user;

import java.time.LocalDate;
import java.util.Set;
public class EmployeeRequest {
    private Set<EmployeeSkill> skills;
    private LocalDate date;

    public java.util.Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
