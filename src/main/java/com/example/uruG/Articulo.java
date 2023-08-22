package com.example.uruG;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.cache.spi.entry.StructuredCacheEntry;

import java.util.Objects;

@Entity
public class Articulo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private int cantidad;
    private String nombre;
    private boolean stock = true;
    private boolean seleccionado = false;
    private boolean agregado = false;
    private boolean eliminado = false;





    public Articulo() {
    }

    public Articulo(int cantidad, String nombre, boolean seleccionado) {
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public boolean isStock() {
        return stock;
    }

    public boolean isAgregado() {
        return agregado;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void marcarAgregado() {
        this.agregado = true;
    }

    public void marcarSeleccionado() {
        this.seleccionado = true;
    }

    public void marcarEliminado() {
        this.eliminado = true;
    }

    public void marcarSinStock() {
        this.stock = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Articulo articulo = (Articulo) o;
        return Objects.equals(id, articulo.id);
    }

    public void eliminarEliminado() {
        this.eliminado = false;
    }

    public void eliminarAgregado() {
        this.agregado = false;
    }
}
