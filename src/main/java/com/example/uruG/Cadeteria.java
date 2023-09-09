
package com.example.uruG;

import com.example.uruG.Servicios.GeneralException;
import jakarta.persistence.*;

@Entity
public class Cadeteria {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String nombreCadeteria;

    private String nombreContacto;
    private String direccionEntrega;
    private String telefono;
    private String mail;

    //ESTE SE USA PARA AGREGAR
    public Cadeteria(String nombreCadeteria, String direccionEntrega, String telefono, String mail, String nombreContacto) {
        this.nombreCadeteria = nombreCadeteria;
        this.direccionEntrega = direccionEntrega;
        this.telefono = telefono;
        this.mail = mail;
        this.nombreContacto = nombreContacto;

    }

    //ESTE ES EL QUE SE DEBERIA USAR PARA EDITAR
    public Cadeteria(Integer id, String nombreCadeteria, String nombreContacto, String direccionEntrega, String telefono, String mail) {
        this.id = id;
        this.nombreCadeteria = nombreCadeteria;
        this.nombreContacto = nombreContacto;
        this.direccionEntrega = direccionEntrega;
        this.telefono = telefono;
        this.mail = mail;
    }

    public Cadeteria() {

    }

    public Integer getId() {
        return id;
    }

    public String getNombreCadeteria() {
        return nombreCadeteria;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public String getNombreContacto() {return nombreContacto;}

    public String getTelefono() {return telefono;}

    public String getMail() {return mail;}

    public boolean validarCadeteria() throws Exception {
        try {
            if(noVacioNiNull(nombreCadeteria) &&  noVacioNiNull(direccionEntrega) && validarMail() && validarTelefono()) {
                return true;
            } else {
                throw new Exception("El nombre de la cadeteria no puede ser vacio");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public boolean noVacioNiNull(String string) throws Exception {
        if (string != null && !string.isBlank()) {
            return true;
        } else {
            throw new Exception("hay campos obligatorios que debes llenar");
        }

    }


    private boolean validarTelefono() throws Exception {
        if (noVacioNiNull(telefono)) {
            String regex = "^(09\\d{7}|\\d{8})$"; // Acepta números de 8 dígitos o números de teléfono uruguayos
            if (telefono.matches(regex)) {
                return true;
            }
            throw new Exception("El número de celular no cumple con el formato válido en Uruguay ni es un número de 8 dígitos.");
        }
        return true;
    }


    private boolean validarMail() throws Exception {
        if(noVacioNiNull(mail)) {
            String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            if (mail.matches(regex)) {
                return true;
            }
            throw new GeneralException("El mail no cumple con el formato válido.");
        }
        //throw new GeneralException("El mail no puede ser vacio");
        //significa que podes agregar el mail despues, pero que si lo agregas tene que estar bien
        return true;
    }


}