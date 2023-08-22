package com.example.uruG;

import jakarta.persistence.Entity;

@Entity
public class Administrativo extends Usuario {

    public Administrativo(String username, String password, String tipo, String mail, String nombreCompleto, String celular) {
        super(username, password, mail, nombreCompleto, celular);
    }

    public Administrativo() {
    }
}
