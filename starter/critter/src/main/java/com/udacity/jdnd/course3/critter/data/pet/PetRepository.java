package com.udacity.jdnd.course3.critter.data.pet;

import com.udacity.jdnd.course3.critter.data.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByOwner(Customer owner);

    Optional<Pet> findById(Long id);

}
