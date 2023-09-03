package com.example.uruG.Controllers;


import com.example.uruG.*;
import java.util.Date;
import com.example.uruG.Servicios.GeneralException;
import com.example.uruG.Servicios.ApiResponse;
import com.example.uruG.SubSistemas.Fachada;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

@Controller
public class VendedorController {
    private ArrayList<Articulo> articulosPedidoActual = new ArrayList<>();

    @Autowired
    private Fachada fachada;


    @PostMapping("/agregarPedido")
    public ResponseEntity<ApiResponse> agregarPedido(@RequestBody pedidoDOM pedidoDOM) {

        ApiResponse response = new ApiResponse();

        try {

            Vendedor nuevoVendedor = fachada.obtenerVendedorPorId(pedidoDOM.getVendedorId());
            Cliente nuevoCliente = fachada.obtenerClientePorId(pedidoDOM.getClienteId());
            Cadeteria nuevaCadeteria = fachada.obtenerCadeteriaPorId(pedidoDOM.getCadeteriaId());
            String observacion = pedidoDOM.getObservacion();
            int idPedido = pedidoDOM.getPedidoId();
            ArrayList<Articulo> nuevosArticulos = pedidoDOM.getArticulos();
            fachada.guardarArticulos(nuevosArticulos);
            Pedido pedido = new Pedido(idPedido,nuevoVendedor, nuevoCliente, nuevaCadeteria, nuevosArticulos, observacion);
            fachada.guardarPedido(pedido);
            response.setPedido(fachada.obtenerPedidoPorId(idPedido));
            response.setPedidos(fachada.pedidosPorVendedor(nuevoVendedor));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMensaje("No se pudo guardar el pedido" + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }


    @PostMapping("/editarPedido")
    public ResponseEntity<ApiResponse> Editable(@RequestParam int idPedido, @RequestParam int idVendedor ) {
        ApiResponse response = new ApiResponse();

        try {
            Vendedor vendedor = fachada.obtenerVendedorPorId(idVendedor);
            Pedido pedido = fachada.obtenerPedidoPorId(idPedido);
            fachada.iniciarEditadoPedidoPorVendedor(pedido);
            response.setMensaje("El pedido fue editado con exito");
            response.setPedido(pedido);
            response.setPedidos(fachada.pedidosAll());
            response.setPedidos(fachada.pedidosPorVendedor(vendedor));
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.setMensaje("No puedes editar este pedido, ya se encuentra facturado.");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/eliminarPedido")
    public ResponseEntity<ApiResponse> eliminarPedido (@RequestParam int pedidoId, @RequestParam int idVendedor ) throws Exception {
        ApiResponse response = new ApiResponse();
        try {
            fachada.eliminarPedido(pedidoId);
            Vendedor vendedor = fachada.obtenerVendedorPorId(idVendedor);
            response.setMensaje("pedido eliminado con Exito");
            response.setPedidos(fachada.pedidosPorVendedor(vendedor));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMensaje(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("actualizar")
    public ResponseEntity<ApiResponse> actualizarPedidos(@RequestParam int idVendedor) throws Exception {
        ApiResponse response = new ApiResponse();

        try {
            Vendedor vendedor = fachada.obtenerVendedorPorId(idVendedor);
            response.setPedidos(fachada.pedidosPorVendedor(vendedor));
            response.setCadeterias(fachada.obtenerCadeterias());
            response.setClientes(fachada.obtenerClientes());
            response.setMensaje("La lista de pedidos se ha actualizado");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMensaje(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }



}
