package com.example.uruG.SubSistemas;
import com.example.uruG.*;
import com.example.uruG.Servicios.GeneralException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Component
public class Fachada {

    private static Fachada instancia = new Fachada();

    public static Fachada getInstancia() {
        return instancia;
    }

    private Fachada() {
    }

    @Autowired
    private SubSistemaUSUARIOS subUsu;
    @Autowired
    private SubSistemaPedidos subPedido;



    public Usuario login(String username, String password) {
        return subUsu.login(username, password);
    }

    public ArrayList<Pedido> pedidosPorCliente(Cliente cliente) {
        return subPedido.pedidosPorCliente(cliente);
    }

    public ArrayList<Cliente> obtenerClientes() { return subUsu.obtenerClientes();
    }

    public Cliente obtenerClientePorId(int clienteId) {
        return subUsu.obtenerClientePorId(clienteId);
    }

    public Vendedor obtenerVendedorPorId(int idVendedor) {
        return subUsu.obtenerVendedorPorId(idVendedor);
    }

    public void guardarArticulos(ArrayList<Articulo> articulos) {
         subPedido.guardarArticulos(articulos);
    }

    public void guardarPedido(Pedido pedido) throws Exception {
        subPedido.guardarPedido(pedido);
    }

    public ArrayList<Pedido> pedidosPorVendedor(Vendedor vendedor) {
        return subPedido.pedidosPorVendedor(vendedor);
    }


    public Cadeteria obtenerCadeteriaPorId(int cadeteriaId) { return subUsu.obtenerCadeteriaPorId(cadeteriaId); }

    public Pedido obtenerPedidoPorId( int idPedido) { return subPedido.obtenerPedidoPorId(idPedido); }

    public Administrativo obtenerAdministrativoPorId(int adminId) throws Exception {
        return subUsu.obtenerAdministrativoPorId(adminId);
    }
    public ArrayList<Cadeteria> obtenerCadeterias() {
        return subUsu.obtenerCadeterias();
    }

    public ArrayList<Pedido> pedidosPorCadeteria(Cadeteria cadeteria) {
        return subPedido.pedidosPorCadeteria(cadeteria);
    }

    public ArrayList<Pedido> pedidosAll() throws Exception {
        //return subUsu.pedidosAll();
        return subPedido.pedidosAll();
    }

    public void articuloSinStock(int articuloId) {
        subPedido.articuloSinStock(articuloId);
    }




    public void eliminarPedido(int pedidoId) throws Exception {
         subPedido.eliminarPedido(pedidoId);
    }

    public void guardarCadeteria(Cadeteria cadeteria) throws Exception {
        subUsu.guardarCadeteria(cadeteria);
    }
    public void eliminarCadeteria(int cadeteria) throws Exception {
        subUsu.eliminarCadeteria(cadeteria);

    }

    public void eliminarCliente(int idCliente) throws Exception {
        subUsu.eliminarCliente(idCliente);
    }





    public void inicarArmado(int pedidoId) throws Exception {
        subPedido.inicarArmado(pedidoId);
    }

    public void pedidoListo(int idpedido) throws Exception {
        subPedido.pedidoListo(idpedido);
    }

    public void pedidoEntregado(int idpedido) throws Exception {
        subPedido.pedidoEntregado(idpedido);
    }


    public void pedidoFacturado(int pedidoId, Administrativo admin) throws Exception {
        subPedido.pedidoFacturado(pedidoId,admin);
    }

    public void iniciarEditadoPedidoPorVendedor(Pedido pedido) throws Exception {
        subPedido.iniciarEditadoPedidoPorVendedor(pedido);
    }

    public void modificarCadeteria(int clienteId, int cadeteriaId) {
        subUsu.modificarCadeteria(clienteId,cadeteriaId);
    }

    public void modificarPassword(int id, String passviejo, String passnuevo) throws Exception {
        subUsu.modificarPassword( id,  passviejo,  passnuevo);
    }

    public void guardarCliente(Cliente cli) throws Exception {
        subUsu.guardarCliente(cli);
    }


    public AuxiliarDeposito obtenerAuxDepoPorId(int id) throws Exception {
        return subUsu.obtenerAuxDepoPorId(id);
    }

    public void guardarAvance(int pedidoid, ArrayList<Articulo> listaArticulos) throws Exception {
        subPedido.guardarAvance(pedidoid, listaArticulos);
    }

    public void pedidoArmado(int pedidoid, AuxiliarDeposito aux, ArrayList<Articulo> listaArticulos) throws Exception {
        subPedido.pedidoArmado(pedidoid, aux,listaArticulos);
    }

    public void enviarWP(int pedidoId) {
        subPedido.enviarWP(pedidoId);
    }

    public void marcarRecibidoPorCliente(int p, Cliente cli) throws Exception {
        subPedido.marcarRecibidoPorCliente(p,cli);
    }

    public void guardarCliente2(Cliente cliente, MultipartFile pdf) throws Exception {
        subUsu.guardarCliente2(cliente, pdf);
    }



    public void guardar(Pedido pedido) throws Exception {
        subPedido.guardar(pedido);
    }
}
