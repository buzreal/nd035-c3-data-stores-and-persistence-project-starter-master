package com.udacity.jdnd.course3.critter.data.pet;

import com.udacity.jdnd.course3.critter.data.schedule.Schedule;
import com.udacity.jdnd.course3.critter.data.user.Customer;
import com.udacity.jdnd.course3.critter.data.user.Employee;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private PetType type;
    private String name;
    @Column(name = "ownerId") // Define the column name
    private Long ownerId;
    private LocalDate birthDate;
    private String notes;
    @ManyToMany
    @JoinTable(
            name = "pet_employee",
            joinColumns = { @JoinColumn(name = "pet_id")},
            inverseJoinColumns = { @JoinColumn(name = "employee_id")})
    List<Employee> employees;
    @ManyToMany(mappedBy = "pets")
    List<Schedule> schedules;


    @ManyToOne
    @JoinColumn(name = "ownerId", referencedColumnName = "id", insertable = false, updatable = false)
    private Customer owner;


    // No argument constructor
    public Pet() {}



    public Pet(Long id,
               PetType type,
               String name,
               Long ownerId,
               LocalDate birthDate,
               String notes,
               List<Employee> employees,
               List<Schedule> schedules,
               Customer owner)
    {
        this.id = id;
        this.type = type;
        this.name = name;
        this.ownerId =ownerId;
        this.birthDate = birthDate;
        this.notes = notes;
        this.employees = employees;
        this.schedules = schedules;
        this.owner = owner;
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }


    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }
}
