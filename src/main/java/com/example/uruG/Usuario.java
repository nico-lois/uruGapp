package com.example.uruG;

import com.example.uruG.Servicios.GeneralException;
import jakarta.persistence.*;

import java.util.Objects;

@Entity // This tells Hibernate to make a table out of this class
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String username;

    private String password;

    private String mail;

    private String nombreCompleto;

    private String celular;

    public Usuario(String username, String password, String mail, String nombreCompleto, String celular) {
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.nombreCompleto = nombreCompleto;
        this.celular = celular;
    }

    public Usuario(String username, String password, String mail, String nombreCompleto, String celular, int id) {
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.nombreCompleto = nombreCompleto;
        this.celular = celular;
        this.id = id;
    }

    public Usuario() {
    }



    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getCelular() {
        return celular;
    }

    public boolean validarDatosGeneralesDeUsuario() throws Exception {
        if(validarPassword() && validarUsername() && validarMail() && validarCelular() && validarNombreCompleto()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validarNombreCompleto() throws Exception {
        if(noVacioNiNull(this.nombreCompleto) && validarLongitud(this.nombreCompleto) && validarSoloLetras(this.nombreCompleto)){
            return true;
        }
        throw new Exception("El Nombre no es valido no es valido");
    }

    public boolean validarPassword() throws Exception {
        if (noVacioNiNull(password)) {
            // Validar caracteres especiales, números y letras
            String regex = "^(?=.*[a-z])(?=.*[A-Z]).{6,}$"; // Sin barras diagonales
            if (password.matches(regex)) {
                return true;
            }
            throw new Exception("El password no tiene un largo de 6 digitos ni tiene al menos una mayuscula y una minuscula");
        }
        throw new Exception("El password no es válido");
    }

    private boolean validarCelular() throws Exception {

        if(noVacioNiNull(celular)){
            String regex = "^(09)[0-9]{7}$";
            if (celular.matches(regex)) {
                return true;
            }
            throw new Exception("El número de celular no cumple con el formato válido en Uruguay");
        }
        //throw new GeneralException("El celular no es valido");
        return true;
    }

    private boolean validarMail() throws Exception {
        if(noVacioNiNull(mail)){
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

    private boolean validarUsername() throws Exception {

        if(noVacioNiNull(this.username) && validarLongitud(this.username)){
            return true;
        }
        throw new Exception("El username no es valido");
    }

    public boolean noVacioNiNull(String string) throws Exception {
        if (string != null && !string.isBlank()) {
            return true;
        } else {
            throw new Exception("hay campos obligatorios que debes llenar");
        }
    }
    public boolean validarLongitud(String string) throws Exception{
        if (string.length() <= 50 ){
            if (string.length() > 3){
                return true;
            }
            throw new Exception("Debe tener al menos 8 caracteres");
        }
        throw new Exception("No puede tener mas de 50 caracteres");
    }
    public static boolean validarSoloLetras(String nombreCompleto) {
        return nombreCompleto.matches("^[a-zA-Z ]+$");
    }

    public boolean passwordCoincide(String passviejo) throws Exception {
        if(password.equals(passviejo)) {
            return true;
        }
        throw new Exception("El password ingresado no coincide con el actual correcto");
    }

    public void cambiarPassword(String passviejo, String passnuevo) throws Exception {
        if(passwordCoincide(passviejo) && validarPasswordNuevo(passnuevo)) {
            this.password = passnuevo;
        }
    }

    private boolean validarPasswordNuevo(String passnuevo) throws Exception {
        if (noVacioNiNull(passnuevo)) {
            // Validar caracteres especiales, números y letras
            String regex = "^(?=.*[a-z])(?=.*[A-Z]).{6,}$"; // Sin barras diagonales
            if (passnuevo.matches(regex)) {
                return true;
            }
            throw new Exception("El password no tiene un largo de 6 digitos ni tiene al menos una mayuscula y una minuscula");
        }
        throw new Exception("El password no es válido");

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
