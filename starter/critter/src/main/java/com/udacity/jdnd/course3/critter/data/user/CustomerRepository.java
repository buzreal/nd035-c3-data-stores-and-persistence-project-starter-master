package com.udacity.jdnd.course3.critter.data.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.id = :ownerId")
    Customer findCustomerByOwnerId(@Param("ownerId") Long ownerId);


    Customer findOwnerByPetIdsContains(Long petId);

}
