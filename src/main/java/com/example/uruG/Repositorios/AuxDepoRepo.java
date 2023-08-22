package com.example.uruG.Repositorios;


import com.example.uruG.AuxiliarDeposito;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface AuxDepoRepo extends CrudRepository<AuxiliarDeposito, Integer> {

}