package com.example.uruG.Controllers;

import com.example.uruG.Cadeteria;
import com.example.uruG.Cliente;
import com.example.uruG.Pedido;
import com.example.uruG.Servicios.GeneralException;
import com.example.uruG.Servicios.ApiResponse;
import com.example.uruG.SubSistemas.Fachada;
import com.example.uruG.Vendedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ClienteController {
    @Autowired
    private Fachada fachada;

    //@CrossOrigin(origins = "http://localhost:5500")

    @PostMapping("/cliente/modificarCadeteria")
    public ResponseEntity<ApiResponse> modificarCadeteria(@RequestParam("id") int clienteId,
                                                          @RequestParam("cadeteriaDePreferencia") int cadeteriaId)  {
        ApiResponse response = new ApiResponse();
        try {
            fachada.modificarCadeteria(clienteId,cadeteriaId);
            response.setMensaje("La cadeteria de preferencia ha sido modificada con exito");
            response.setCadeterias(fachada.obtenerCadeterias());
            response.setCliente(fachada.obtenerClientePorId(clienteId));
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.setMensaje(e.getMessage());
            return ResponseEntity.ok(response);
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/cliente/modificarPass")
    public ResponseEntity<ApiResponse> modificarCadeteria(@RequestParam("id") int clienteId,
                                                          @RequestParam("passwordviejo") String passviejo,
                                                          @RequestParam("passwordnuevo") String passnuevo)  {
        ApiResponse response = new ApiResponse();
        try {
            fachada.modificarPassword(clienteId,passviejo,passnuevo);
            response.setCliente(fachada.obtenerClientePorId(clienteId));
            response.setMensaje("La contraseña se modifico con exito");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.setMensaje(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/cliente/registroCliente")
    public ResponseEntity<ApiResponse> registroCliente(@RequestBody Cliente cliente) throws GeneralException {
        ApiResponse response = new ApiResponse();
        try {
                fachada.guardarCliente(cliente);
                response.setCliente(cliente);
                response.setClientes(fachada.obtenerClientes());
                response.setMensaje("Cliente ingresado con exito");
                return ResponseEntity.ok(response);

            } catch (Exception ex) {
                response.setMensaje(ex.getMessage());
                return ResponseEntity.ok(response);
            }
    }



    @PostMapping("/cliente/marcadoRecibidoPorCliente")
    public ResponseEntity<ApiResponse> marcadoRecibidoPorCliente(@RequestParam("id") int pedidoId,
                                                                  @RequestParam("idCliente") int clienteId) {
        ApiResponse response = new ApiResponse();

        try {
            //podriamos validar que ese cliente es el mismo que hizo
            // el pedido y no solo el estado

            Cliente cli = fachada.obtenerClientePorId(clienteId);

            fachada.marcarRecibidoPorCliente(pedidoId,cli);
            response.setPedidos(fachada.pedidosPorCliente(cli));
            response.setMensaje("El pedido ha sido marcado como recibido");
            return ResponseEntity.ok(response);

            } catch (Exception e) {
                response.setMensaje(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @DeleteMapping("/eliminarCliente")
    public ResponseEntity<ApiResponse> EliminarCliente(@RequestParam ("idCliente") int idCliente) throws Exception {

        ApiResponse response = new ApiResponse();

        try {

            fachada.eliminarCliente(idCliente);
            response.setMensaje("Cliente eliminado con Exito");
            response.setClientes(fachada.obtenerClientes());
            return ResponseEntity.ok(response);

        } catch (Exception e) {

            response.setMensaje("Ups... No se pudo Eliminar el cliente, tienen pedidos asociados - HIBERNATE:" + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }


    @PostMapping("/cliente/verPdf")
    public ResponseEntity<byte[]> verPdf(@RequestParam("id") int clienteId) throws Exception {
        try {
            Cliente cli = fachada.obtenerClientePorId(clienteId);
            byte[] pdfData = cli.getPdfData();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);

            return new ResponseEntity<>(pdfData, headers, HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping("actualizarPedidosClientes")
    public ResponseEntity<ApiResponse> actualizarPedidosClientes(@RequestParam int idCliente) throws Exception {
        ApiResponse response = new ApiResponse();

        try {
            Cliente cliente = fachada.obtenerClientePorId(idCliente);
            response.setPedidos(fachada.pedidosPorCliente(cliente));
            response.setMensaje("La lista de pedidos se ha actualizado");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMensaje(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/cliente/valorar")
    public ResponseEntity<ApiResponse> valorar(@RequestParam ("id") int idPedido, @RequestParam("idCliente") int idCliente, @RequestParam("valor") int valoracion) throws Exception {
        ApiResponse response = new ApiResponse();

        try {
            Pedido pedido = fachada.obtenerPedidoPorId(idPedido);
            Cliente cli = fachada.obtenerClientePorId(idCliente);
            pedido.agregarValoracion(valoracion,cli);
            fachada.guardar(pedido);
            response.setPedidos(fachada.pedidosPorCliente(cli));
            response.setMensaje("La lista de pedidos se ha actualizado");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMensaje(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }
}


