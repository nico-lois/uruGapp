package com.example.uruG.Controllers;

import com.example.uruG.Administrativo;
import com.example.uruG.Pedido;
import com.example.uruG.Servicios.ApiResponse;
import com.example.uruG.SubSistemas.Fachada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdministrativoController {
    @Autowired
    private Fachada fachada;

    //TENGO QUE PEDIR EL ADMINISTRATIVO
    @PostMapping("/admin/pedidoFacturado")
    public ResponseEntity<ApiResponse> pedidoFacturado(@RequestParam("pedidoId") int pedidoId, @RequestParam("admin") int adminId) throws Exception {
        ApiResponse response = new ApiResponse();

        try {
            Administrativo admin = fachada.obtenerAdministrativoPorId(adminId);
            fachada.pedidoFacturado(pedidoId,admin);
            response.setPedido(fachada.obtenerPedidoPorId(pedidoId));
            response.setPedidos(fachada.pedidosAll());
            response.setMensaje("El pedido se encuentra facturado y listo para terminar el armado.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMensaje(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/admin/actualizar")
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

}
