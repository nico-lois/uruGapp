package com.example.uruG.Servicios;
import com.example.uruG.*;

import java.util.ArrayList;

public class ApiResponse {
    private String mensaje = null;
    private Cliente cliente = null;
    private Vendedor vendedor = null;
    private Cadeteria cadeteria = null;
    private Pedido pedido = null;
    private AuxiliarDeposito auxDep = null;
    private Administrativo admin = null;
    private ArrayList<Articulo> articulos = null;
    private ArrayList<Cliente> clientes = null;
    private ArrayList<Cadeteria> cadeterias = null;
    private ArrayList<Pedido> pedidos = null;
    private byte[] pdf = null;

    public ApiResponse() {
    }

    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }
    public Cadeteria getCadete() {
        return cadeteria;
    }
    public void setCadete(Cadeteria cadeteria) {
        this.cadeteria = cadeteria;
    }
    public AuxiliarDeposito getAuxDep() {
        return auxDep;
    }
    public void setAuxDep(AuxiliarDeposito auxDep) {
        this.auxDep = auxDep;
    }
    public Administrativo getAdmin() {
        return admin;
    }
    public void setAdmin(Administrativo admin) {
        this.admin = admin;
    }
    public ArrayList<Articulo> getArticulos() {
        return articulos;
    }
    public void setArticulos(ArrayList<Articulo> articulos) {
        this.articulos = articulos;
    }
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }
    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Cadeteria getCadeteria() {
        return cadeteria;
    }

    public void setCadeteria(Cadeteria cadeteria) {
        this.cadeteria = cadeteria;
    }
    public ArrayList<Cadeteria> getCadeterias() {
        return cadeterias;
    }
    public void setCadeterias(ArrayList<Cadeteria> cadeterias) {
        this.cadeterias = cadeterias;
    }
    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }
    public void setPedidos(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public byte[] getPdf() {
        return pdf;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }
}
