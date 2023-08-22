package com.example.uruG;

import jakarta.persistence.Entity;

@Entity
public class AuxiliarDeposito extends Usuario {

    public AuxiliarDeposito() {
    }

    public AuxiliarDeposito(String username, String password, String tipo, String mail, String nombreCompleto, String celular) {
        super(username, password, mail, nombreCompleto, celular);

    }




}
