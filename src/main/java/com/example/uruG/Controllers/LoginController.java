package com.example.uruG.Controllers;
import com.example.uruG.*;
import com.example.uruG.Servicios.ApiResponse;
import com.example.uruG.SubSistemas.Fachada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private Fachada fachada;


        //@CrossOrigin(origins = "http://127.0.0.1:5500")
        @CrossOrigin(origins = "http://localhost:5500")
        @PostMapping("/login")
        public ResponseEntity<ApiResponse> login(@RequestParam("username") String username, @RequestParam("password") String password) {

            ApiResponse response = new ApiResponse();

            try {
                Usuario usu = fachada.login(username, password);

                if (usu != null) {
                    //CLIENTE
                    if (usu instanceof Cliente cliente) {
                        response.setCliente(cliente);
                        response.setPedidos(fachada.pedidosPorCliente(cliente));
                        response.setCadeterias(fachada.obtenerCadeterias());
                    }
                    //VENDEDOR
                    else if (usu instanceof Vendedor vendedor) {
                        response.setPedidos(fachada.pedidosPorVendedor(vendedor));
                        response.setVendedor(vendedor);
                        response.setClientes(fachada.obtenerClientes());
                        response.setCadeterias(fachada.obtenerCadeterias());
                    }
                    //ADMINISTRATIVO
                    else if (usu instanceof Administrativo administrativo) {
                        response.setAdmin(administrativo);
                        response.setClientes(fachada.obtenerClientes());
                        response.setCadeterias(fachada.obtenerCadeterias());
                        response.setPedidos(fachada.pedidosAll());
                    }
                    //AUXDEPOSITO
                    else if (usu instanceof AuxiliarDeposito auxDepo) {
                        response.setAuxDep(auxDepo);
                        response.setPedidos(fachada.pedidosAll());
                        response.setClientes(fachada.obtenerClientes());
                        response.setCadeterias(fachada.obtenerCadeterias());
                    }
                    else {
                        response.setMensaje("Tipo de usuario no reconocido");
                    }
                    return ResponseEntity.ok(response);
                } else {
                    response.setMensaje("Usuario o contraseña incorrecta");
                    return ResponseEntity.ok(response);
                }
            } catch (Exception e) {
                response.setMensaje("Ocurrió un error inesperado: " + e.getMessage());
                return ResponseEntity.ok(response);
            }
        }

    @PostMapping("/login/modificarPass")
    public ResponseEntity<ApiResponse> modificarCadeteria(@RequestParam("id") int id,
                                                          @RequestParam("passwordviejo") String passviejo,
                                                          @RequestParam("passwordnuevo") String passnuevo)  {
        ApiResponse response = new ApiResponse();
        try {
            fachada.modificarPassword(id,passviejo,passnuevo);
            response.setPedidos(fachada.pedidosAll());
            response.setMensaje("La contraseña se modifico con exito");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.setMensaje(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }
}


