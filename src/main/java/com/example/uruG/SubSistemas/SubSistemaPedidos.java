package com.example.uruG.SubSistemas;

import com.example.uruG.*;
import com.example.uruG.Repositorios.ArticuloRepo;
import com.example.uruG.Repositorios.AuxDepoRepo;
import com.example.uruG.Repositorios.PedidoRepo;
//import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SubSistemaPedidos {

    @Autowired
    private PedidoRepo pedidoRepo;
    @Autowired
    private ArticuloRepo articuloRepo;

    public void guardarArticulos(List<Articulo> articulos) {
        for (Articulo a : articulos) {
            articuloRepo.save(a);
        }
    }
    //ESTE METODO SE DEBE USAR SOLO PARA EL GUARDADO DEL PEDIDO DE UN VENDEDOR
    public void guardarPedido(Pedido pedido) throws Exception {

        try {
            pedido.validar();
            if(pedido.getId() == 0) {
                pedidoRepo.save(pedido);
            } else {
                Pedido pedidoDB = obtenerPedidoPorId(pedido.getId());
                actualizarListas(pedidoDB.getArticulos(),pedido.getArticulos());
                pedido.modificarEstadoPedido(EstadoPedido.INGRESADO);
                pedido.revisarStock();
                pedidoRepo.save(pedido);
            }

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private void actualizarListas(List<Articulo> listaOriginal, List<Articulo> nuevaLista) {

        //AGREGAR LOS ARTICULOS QUE FUERON AGREGADOS AL PEDIDO
        for(Articulo a : nuevaLista) {
            if (!listaOriginal.contains(a)) {
                a.marcarAgregado();
                a.eliminarEliminado();
            }
        }

        //AGREGAR LOS ARTICULOS QUE FUERON ELIMINADOS EN LA NUEVA LISTA Y LOS MARCAMOS COMO ELIMINADOS
        for(Articulo a: listaOriginal ) {
            if(!nuevaLista.contains(a)) {
                a.marcarEliminado();
                a.eliminarAgregado();
                nuevaLista.add(a);
            }
        }
    }



    public  ArrayList<Pedido> pedidosPorVendedor(Vendedor vendedor) {
        return (ArrayList<Pedido>) pedidoRepo.findByVendedor(vendedor);
    }
    public ArrayList<Pedido> pedidosPorCliente(Cliente cliente) {
        return (ArrayList<Pedido>) pedidoRepo.findByCliente(cliente);
    }
    public Pedido obtenerPedidoPorId(int idPedido) {
        Optional<Pedido> pedidoOpt = pedidoRepo.findById(idPedido);
        if (pedidoOpt.isPresent()) {
            return pedidoOpt.get();
        } else {
            return null; // tirar una exeption
        }
    }
    public ArrayList<Pedido> pedidosPorCadeteria(Cadeteria cadeteria) {
        return (ArrayList<Pedido>) pedidoRepo.findByCadeteria(cadeteria);
    }
    public ArrayList<Pedido> pedidosAll() throws Exception {
        try {
            return (ArrayList<Pedido>) pedidoRepo.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    public void articuloSinStock(int articuloId) {
        Optional<Articulo> articulo = articuloRepo.findById(articuloId);
        if(articulo.isPresent()) {
            articulo.get().marcarSinStock();
        }
    }




    public void eliminarPedido(int pedidoId) throws Exception {
        try {
            pedidoRepo.deleteById(pedidoId);
        } catch (Exception e) {
            throw  new Exception("No se pudo eliminar el pedido" + e.getMessage());
        }
    }

    public void salvarPedido(Pedido pedido) {
        pedidoRepo.save(pedido);
    }
    public void inicarArmado(int pedidoId) throws Exception {
        try {
            Pedido p = obtenerPedidoPorId(pedidoId);
            if(p.marcarComoPreparacion()) {
                pedidoRepo.save(p);

            } else {
                throw new Exception("El pedido esta siendo modificado por el vendedor");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }



    private void marcarArticulosComoAgregados(List<Articulo> articulos) {
        for(Articulo a : articulos) {
            a.marcarAgregado();
        }
    }


    public boolean todosLosArticulosSeleccionados(ArrayList<Articulo> listaArticulos) {

        for (Articulo a : listaArticulos) {
            if (!a.isSeleccionado()) {
                return false;
            }
        }
        return true;
    }

    public void pedidoListo(int idpedido) throws Exception {
        try {
            Pedido p = obtenerPedidoPorId(idpedido);
            if(p.marcarComoListo()) {
                pedidoRepo.save(p);

            } else {
                throw new Exception("El pedido no se encuentra en estado Facturado");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    public void pedidoEntregado(int idpedido) throws Exception {
        try {
            Pedido p = obtenerPedidoPorId(idpedido);
            if(p.marcarComoEntregado()) {
                pedidoRepo.save(p);

            } else {
                throw new Exception("El pedido no se encuentra en estado Listo");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    public void pedidoFacturado(int pedidoId, Administrativo admin) throws Exception {
        try {
            Pedido p = obtenerPedidoPorId(pedidoId);
            if(p.marcarComoFacturado(admin)) {
                pedidoRepo.save(p);
            } else {
                throw new Exception("El pedido no puede ser Facturado en este momento ");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    public void iniciarEditadoPedidoPorVendedor(Pedido pedido) throws Exception {

        try{
            if(pedido.EsEditablePorVendedor()) {
                pedidoRepo.save(pedido);
            } else {
                throw new Exception("El pedido se encuentra facturado, ya no puede ser editado.");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    public void guardarAvance(int pedidoid, ArrayList<Articulo> listaArticulos) throws Exception {

        try {
            Pedido pedido = obtenerPedidoPorId(pedidoid);
            if (pedido.marcarComoPreparacion()) {
                pedido.actualizarListaAux(listaArticulos);
                pedido.revisarStock();
                pedidoRepo.save(pedido);
            } else {
                throw new Exception("El vendedor esta editando el pedido en este momento");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void pedidoArmado(int pedidoid, AuxiliarDeposito auxDepo, ArrayList<Articulo> listaArticulos) throws Exception {
        try {
            if(todosLosArticulosSeleccionados(listaArticulos)) {
                Pedido pedido = obtenerPedidoPorId(pedidoid);
                if(pedido.marcarComoArmado()) {
                    pedido.actualizarListaAux(listaArticulos);
                    pedido.revisarStock();
//                    List<Articulo> articulos = pedido.getArticulos();
//                    marcarArticulosComoSeleccionados(articulos);
                    pedido.setAuxDep(auxDepo);
                    pedidoRepo.save(pedido);
                } else {
                    throw new Exception("El vendedor esta editando el pedido en este momento");
                }
            } else {
                throw new Exception("Hay articulos sin marcar como agregados");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    private void marcarArticulosComoSeleccionados(List<Articulo> articulos) {
        for(Articulo a : articulos) {
            a.marcarSeleccionado();
        }
    }

    public void enviarWP(int pedidoId) {
        try {
           Pedido pedido = obtenerPedidoPorId(pedidoId);
           String cel = pedido.getCadeteria().getTelefono();
           if(pedido.getEstado() == EstadoPedido.LISTO && cel != null);
            WPtwilio wp = new WPtwilio();
            wp.enviarMensaje(cel);
        } catch (Exception e) {

        }
    }

    public void marcarRecibidoPorCliente(int p, Cliente cli) throws Exception {
        try {
            Pedido pedido = obtenerPedidoPorId(p);
            if(pedido.marcarComoRecibidoPorCliente(cli)) {
                pedidoRepo.save(pedido);
            } else {
                throw new Exception("No se pudo marcar el pedido como recibido");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void guardar(Pedido pedido) throws Exception {
        try {
            pedidoRepo.save(pedido);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
