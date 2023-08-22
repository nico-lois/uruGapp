package com.example.uruG.Repositorios;


import org.springframework.data.repository.CrudRepository;

import com.example.uruG.Usuario;

import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UsuarioRepo extends CrudRepository<Usuario, Integer> {

    Optional<Usuario> findByUsernameAndPassword(String username, String password);

    Optional<Usuario> findByUsername(String username);
}
