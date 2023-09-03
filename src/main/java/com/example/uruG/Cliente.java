package com.example.uruG;

import com.example.uruG.Servicios.GeneralException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Entity // This tells Hibernate to make a table out of this class
public class Cliente extends Usuario {

    private String nombreResponsable;
    private String razonSocial;
    private String nombreFantasia;

    private String direccionDeEntrega;
    @ManyToOne
    private Cadeteria cadeteriaDePreferencia;
    @Lob
    @Column(name = "pdf_data", columnDefinition = "MEDIUMBLOB")
    private byte[] pdfData;


    public Cliente(String username, String password, String mail, String nombreCompleto, String celular, String nombreResponsable, String razonSocial, String nombreFantasia, String  direccionDeEntrega) {

        super(username, password, mail, nombreCompleto, celular);
        this.nombreResponsable = nombreResponsable;
        this.razonSocial = razonSocial;
        this.nombreFantasia = nombreFantasia;
        this.direccionDeEntrega = direccionDeEntrega;

    }

    public Cliente(Integer id, String username, String password, String mail, String nombreCompleto, String celular, String nombreResponsable, String razonSocial, String nombreFantasia, String direccionDeEntrega) {
        super(username, password, mail, nombreCompleto, celular, id);
        this.nombreResponsable = nombreResponsable;
        this.razonSocial = razonSocial;
        this.nombreFantasia = nombreFantasia;
        this.direccionDeEntrega = direccionDeEntrega;
    }

    public Cliente() {
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public String getNombreFantasia() {
        return nombreFantasia;
    }

    public Cadeteria getCadeteriaDePreferencia() {
        return cadeteriaDePreferencia;
    }

    public byte[] getPdfData() {
        return pdfData;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public void setNombreFantasia(String nombreFantasia) {
        this.nombreFantasia = nombreFantasia;
    }

    public String getDireccionDeEntrega() {
        return direccionDeEntrega;
    }

    public void setDireccionDeEntrega(String direccionDeEntrega) {
        this.direccionDeEntrega = direccionDeEntrega;
    }

    public void setPdfData(byte[] pdfData) {
        this.pdfData = pdfData;
    }

    public void setCadeteriaDePreferencia(Cadeteria cadeteriaDePreferencia) {
        this.cadeteriaDePreferencia = cadeteriaDePreferencia;
    }
    public boolean validarDatos() throws Exception {
        if(validarDatosGeneralesDeUsuario() && validarDatosCliente()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validarDatosCliente() throws Exception {
        if(validarNombreResponsable() && validarRazonSocial() && validarNombreFantasia() && validarCadeteriaDePreferencia()) {
            return true;
        }
        return false;
    }

    private boolean validarCadeteriaDePreferencia() throws Exception {
//        if(cadeteriaDePreferencia != null) {
//            return true;
//        } else {
//            throw new Exception("La cadeteria no puede ser vacia");
//        }
        return true;
    }

    private boolean validarNombreFantasia() throws Exception {

        if(noVacioNiNull(nombreFantasia) && validarLongitud(nombreFantasia)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validarRazonSocial() throws Exception {
        if (noVacioNiNull(razonSocial) && validarLongitud(razonSocial)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validarNombreResponsable() throws Exception {
        if (noVacioNiNull(nombreResponsable)) {
            if(validarLongitud(nombreResponsable) && validarSoloLetras(nombreResponsable)) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }




    public boolean modificarCadeteria(Cadeteria cad) throws Exception {
        if (cad != null){
            setCadeteriaDePreferencia(cad);
            return true;
        } else {
            throw new Exception("La cadeteria no puede ser Vacia");
        }
    }

    public void agregarPDF(MultipartFile pdf) throws IOException {
        this.pdfData = pdf.getBytes();
    }


}