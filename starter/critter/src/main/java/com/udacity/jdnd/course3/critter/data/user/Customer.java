package com.udacity.jdnd.course3.critter.data.user;

import com.udacity.jdnd.course3.critter.data.pet.Pet;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String phoneNumber;
    private String notes;
    @ElementCollection
    private List<Long> petIds;
    @OneToMany(mappedBy = "owner")
    @ElementCollection
    private List<Pet> pets = new ArrayList<>();

    public Customer(
                    Long id,
                    String name,
                    String phoneNumber,
                    String notes,
                    List<Long> petIds,
                    List<Pet> pets
                    )
    {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
        this.petIds = petIds;
        this.pets = pets;
    }

    // No argument constructor
    public Customer(){
        petIds = new ArrayList<>();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
