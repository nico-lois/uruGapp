package com.example.uruG.Controllers;

import com.example.uruG.Articulo;
import com.example.uruG.AuxiliarDeposito;
import com.example.uruG.Cadeteria;
import com.example.uruG.Pedido;
import com.example.uruG.Servicios.ApiResponse;
import com.example.uruG.SubSistemas.Fachada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class AuxDepoController {

    @Autowired
    private Fachada fachada;


    @PostMapping("/auxdepo/iniciarArmado")
    public ResponseEntity<ApiResponse> IniciarArmdo(@RequestParam ("pedidoId") int pedidoId ){

        ApiResponse response = new ApiResponse();

        try {
            fachada.inicarArmado(pedidoId);
            response.setPedidos(fachada.pedidosAll());
            response.setPedido(fachada.obtenerPedidoPorId(pedidoId));
            response.setMensaje("El pedido ah cambiado su estado a en Preparacion");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMensaje(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    //NO ES NECESARIO EL USO DE ARTICULO DOM EL ARRAY PUEDE SER DE ARTICULOS
    @PostMapping("/auxdepo/pedidoArmado")
    public ResponseEntity<ApiResponse> PedidoArmado(@RequestParam ("pedidoId") int  pedidoid,@RequestParam("auxId") int auxId, @RequestBody ArrayList<Articulo> listaArticulos) {

        ApiResponse response = new ApiResponse();

        try {
            fachada.guardarArticulos(listaArticulos);
            AuxiliarDeposito aux = fachada.obtenerAuxDepoPorId(auxId);
            fachada.pedidoArmado(pedidoid,aux,listaArticulos);
            response.setPedidos(fachada.pedidosAll());
            response.setMensaje("El pedido cambio de estado a listo para facturacion");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMensaje(e.getMessage());
            return ResponseEntity.ok(response);

        }
    }

//HAY QUE HACER ACA UNO PA SALVAR LOS CAMBIOS EN EL MEDIO ANTES DE QUE ESTE PRONTO
    @PostMapping("/auxdepo/guardarAvance")
    public ResponseEntity<ApiResponse> guardarAvance(@RequestParam ("pedidoId") int pedidoid, @RequestBody ArrayList<Articulo> listaArticulos) {
        ApiResponse response = new ApiResponse();
        try {
            fachada.guardarArticulos(listaArticulos);
            fachada.guardarAvance(pedidoid,listaArticulos);
            response.setPedidos(fachada.pedidosAll());
            response.setMensaje("El avance se guardo Correctamentee");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMensaje(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }



    @PostMapping("/auxdepo/pedidoListo")
    public ResponseEntity<ApiResponse> pedidoListo(@RequestParam ("pedidoId") int pedidoId){

        ApiResponse response = new ApiResponse();

        try {
            fachada.pedidoListo(pedidoId);
            response.setPedidos(fachada.pedidosAll());
            response.setMensaje("El pedido cambio de estado a Listo para Entregar");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMensaje(e.getMessage());
            return ResponseEntity.ok(response);

        }

    }

    @PostMapping("/auxdepo/pedidoEntregado")
    public ResponseEntity<ApiResponse> pedidoEntregado(@RequestParam ("pedidoId") int pedidoId){

        ApiResponse response = new ApiResponse();

        try {
            fachada.pedidoEntregado(pedidoId);
            response.setPedidos(fachada.pedidosAll());
            response.setMensaje("El pedido cambio de estado a Entregado");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMensaje(e.getMessage());
            return ResponseEntity.ok(response);

        }

    }


    @PostMapping("/auxdepo/actualizar")
    public ResponseEntity<ApiResponse> actualizar(@RequestParam int id) throws Exception {
        ApiResponse response = new ApiResponse();

        try {
            fachada.obtenerAuxDepoPorId(id);
            response.setPedidos(fachada.pedidosAll());
            response.setMensaje("La lista de pedidos se ha actualizado");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMensaje(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

//FALTA ARTICULO SIN STOCK

    @PostMapping("/enviarWP")
    public ResponseEntity<ApiResponse> enviarWP(@RequestParam ("pedidoId") int pedidoId) throws Exception {

        ApiResponse response = new ApiResponse();

        try {
            fachada.enviarWP(pedidoId);
            response.setMensaje("Mensaje enviado Con Exito");
            response.setPedidos(fachada.pedidosAll());
            return ResponseEntity.ok(response);

        } catch (Exception e) {

            response.setMensaje("Ups... No se pudo enviar el mensaje" + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/auxdepo/actualizar")
    public ResponseEntity<ApiResponse> actualizar() {
        ApiResponse response = new ApiResponse();

        try {
            response.setPedidos(fachada.pedidosAll());
            response.setCadeterias(fachada.obtenerCadeterias());
            response.setClientes(fachada.obtenerClientes());
            response.setMensaje("La lista de pedidos se ha actualizado");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMensaje(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/auxdepo/numeroEntrega")
    public ResponseEntity<ApiResponse> numeroEntrega(@RequestParam ("idPedido") int idPedido, @RequestParam("numeroEntrega") String numeroEntrega) {
        ApiResponse response = new ApiResponse();

        try {
            Pedido pedido = fachada.obtenerPedidoPorId(idPedido);
            pedido.agregarNumeroEntrega(numeroEntrega);
            fachada.guardar(pedido);
            response.setPedidos(fachada.pedidosAll());
            response.setMensaje("El Numero de entrega se añadio con exito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMensaje(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/auxdepo/numeroRastreo")
    public ResponseEntity<ApiResponse> numeroRastro(@RequestParam ("idPedido") int idPedido, @RequestParam("numeroRastreo") String numeroRastreo) {
        ApiResponse response = new ApiResponse();

        try {
            Pedido pedido = fachada.obtenerPedidoPorId(idPedido);
            pedido.agregarNumeroRastreo(numeroRastreo);
            fachada.guardar(pedido);
            response.setPedidos(fachada.pedidosAll());
            response.setMensaje("El Numero de Rastreo se añadio con exito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMensaje(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

}
