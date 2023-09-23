package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.pet.Pet;
import com.udacity.jdnd.course3.critter.data.pet.PetDTO;
import com.udacity.jdnd.course3.critter.data.pet.PetRepository;
import com.udacity.jdnd.course3.critter.data.user.Customer;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    @Autowired
    private  PetRepository petRepository;

    public List<PetDTO> getAllPets() {
        List<Pet> pets = petRepository.findAll();
        return mapPetsToDTOs(pets);
    }


    public Pet getPetById(Long id) throws NotFoundException {
        return petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet not found"));
    }


    public PetDTO savePet(PetDTO petDTO) {
        Pet pet = new Pet();
        petDTO.setName(pet.getName());
        petDTO.setType(pet.getType());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setOwnerId(pet.getOwnerId());
        pet = petRepository.save(pet);

        return mapPetToDTO(pet);
    }

    public PetDTO getPet(Long petId) throws NotFoundException {
        Optional<Pet> petOptional = petRepository.findById(petId);
        if (petOptional.isPresent()) {
            return mapPetToDTO(petOptional.get());
        } else {
            throw new NotFoundException("Pet not found");
        }
    }

    public Pet createPet(Pet pet) {
        return petRepository.save(pet);
    }

    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }

    public List<PetDTO> getPetsByOwner(Long ownerId) {
        Customer owner = new Customer();
        owner.setId(ownerId);
        List<Pet> pets = petRepository.findByOwner(owner);
        return mapPetsToDTOs(pets);
    }

    private PetDTO mapPetToDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setName(pet.getName());
        petDTO.setType(pet.getType());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setOwnerId(pet.getOwnerId());
        return petDTO;
    }

    private List<PetDTO> mapPetsToDTOs(List<Pet> pets) {
        List<PetDTO> petDTOs = new ArrayList<>();
        for (Pet pet : pets) {
            PetDTO petDTO = new PetDTO();
            petDTO.setId(pet.getId());
            petDTO.setName(pet.getName());
            petDTO.setType(pet.getType());
            petDTO.setBirthDate(pet.getBirthDate());
            petDTO.setOwnerId(pet.getOwnerId());
            // Map other attributes as needed
            petDTOs.add(petDTO);
        }
        return petDTOs;
    }
}
