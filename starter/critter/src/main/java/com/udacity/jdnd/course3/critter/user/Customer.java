package com.udacity.jdnd.course3.critter.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idLong;

    private long id;
    private String name;
    private String phoneNumber;
    private String notes;
    private List<Long> petIds;

    // No argument constructor
    public Customer(){}

    public Customer(Long idLong,
                    long id,
                    String name,
                    String phoneNumber,
                    String notes,
                    List<Long> petIds)
    {
        this.idLong = idLong;
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
        this.petIds = petIds;
    }

    public Long getIdLong() {
        return idLong;
    }

    public void setIdLong(Long idLong) {
        this.idLong = idLong;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Long> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Long> petIds) {
        this.petIds = petIds;
    }
}
