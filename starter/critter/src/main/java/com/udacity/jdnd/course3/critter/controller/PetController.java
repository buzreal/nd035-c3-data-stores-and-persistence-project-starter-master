package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.data.pet.PetDTO;
import com.udacity.jdnd.course3.critter.service.PetService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

//    @PostMapping
//    public PetDTO savePet(@RequestBody PetDTO petDTO) {
//        throw new UnsupportedOperationException();
//    }
//
//    @GetMapping("/{petId}")
//    public PetDTO getPet(@PathVariable long petId) {
//        throw new UnsupportedOperationException();
//    }
//
//    @GetMapping
//    public List<PetDTO> getPets(){
//        throw new UnsupportedOperationException();
//    }

//    @GetMapping("/owner/{ownerId}")
//    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
//        throw new UnsupportedOperationException();
//    }


    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable Long ownerId) {
        return petService.getPetsByOwner(ownerId);
    }


    @GetMapping
    public List<PetDTO> getPets() {
        return petService.getAllPets();
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable Long petId) throws NotFoundException {
        return petService.getPet(petId);
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        return petService.savePet(petDTO);
    }
}
