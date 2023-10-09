package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.pet.Pet;
import com.udacity.jdnd.course3.critter.data.pet.PetDTO;
import com.udacity.jdnd.course3.critter.data.pet.PetRepository;
import com.udacity.jdnd.course3.critter.data.user.Customer;
import com.udacity.jdnd.course3.critter.data.user.CustomerRepository;
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
    @Autowired
    private CustomerService customerService;
    @Autowired
    CustomerRepository customerRepository;

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


    public void addPetsToOwner(Long ownerId, PetDTO petDTO) {
        Customer customer = customerRepository.getOne(ownerId);

        // Create a new pet entity and set its properties from the DTO
        Pet pet = new Pet();
        pet.setOwner(customer);
        pet.setName(petDTO.getName());
        // Save the pet entity
        Pet savedPet = petRepository.save(pet);

        // Add the pet's ID to the customer's petIds set
        customer.getPetIds().add(savedPet.getId());
        // Update the customer in the repository
        customerRepository.save(customer);
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
            // Map other attributes as needed
            petDTOs.add(petDTO);
        }
        return petDTOs;
    }
}
