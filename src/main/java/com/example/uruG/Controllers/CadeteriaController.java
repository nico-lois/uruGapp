package com.example.uruG.Controllers;

import com.example.uruG.Cadeteria;
import com.example.uruG.Cliente;
import com.example.uruG.Servicios.ApiResponse;
import com.example.uruG.Servicios.GeneralException;
import com.example.uruG.SubSistemas.Fachada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CadeteriaController {
    @Autowired
    private Fachada fachada; // Reemplaza por tu clase de fachada correspondiente


    @PostMapping("/cadeteria/agregarCadeteria")
    public ResponseEntity<ApiResponse> agregarCadeteria(@RequestBody Cadeteria cadeteria) throws Exception
    {

        ApiResponse response = new ApiResponse();
        try {
                fachada.guardarCadeteria(cadeteria);
                response.setCadeteria(cadeteria);
                response.setCadeterias(fachada.obtenerCadeterias());
                response.setMensaje("Cadeteria ingresado con exito");
                return ResponseEntity.ok(response);

            } catch (Exception e) {
                response.setMensaje(e.getMessage());
                return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/eliminarCadeteria")
    public ResponseEntity<ApiResponse> EliminarCadeteria(@RequestParam ("cadeteriaid") int cadeteriaid) throws Exception {

        ApiResponse response = new ApiResponse();

        try {

            fachada.eliminarCadeteria(cadeteriaid);
            response.setMensaje("Cadeteria eliminada con Exito");
            response.setCadeterias(fachada.obtenerCadeterias());
            return ResponseEntity.ok(response);

        } catch (Exception e) {

            response.setMensaje("Ups... No se pudo Eliminar la Cadeteria" + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }




}
