package com.udacity.jdnd.course3.critter.data.user;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class EmployeeRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "employee_request_skills", joinColumns = @JoinColumn(name = "employee_request_id"))
    private Set<EmployeeSkill> skills;
    private LocalDate date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
