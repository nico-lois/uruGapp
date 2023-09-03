package com.example.uruG;


import java.util.ArrayList;

public class pedidoDOM {

    private int pedidoId;
    private int clienteId;
    private int cadeteriaId;
    private int vendedorId;

    private int administrativoId;

    private int auxdepoId;
    private ArrayList<Articulo> articulos;

    private String observacion;



    public pedidoDOM(int pedidoId, int clienteId, int cadeteriaId, int vendedorId, int administrativoId, int auxdepoId, ArrayList<Articulo> articulos, String observacion) {
        this.pedidoId = pedidoId;
        this.clienteId = clienteId;
        this.cadeteriaId = cadeteriaId;
        this.vendedorId = vendedorId;
        this.administrativoId = administrativoId;
        this.auxdepoId = auxdepoId;
        this.articulos = articulos;
        this.observacion = observacion;
    }

    public int getPedidoId() {
        return pedidoId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public int getCadeteriaId() {
        return cadeteriaId;
    }

    public int getVendedorId() {
        return vendedorId;
    }

    public ArrayList<Articulo> getArticulos() {
        return articulos;
    }

    public int getAdministrativoId() {
        return administrativoId;
    }

    public int getAuxdepoId() {
        return auxdepoId;
    }

    public String getObservacion() {
        return observacion;
    }
}
