package com.example.uruG.SubSistemas;

import com.example.uruG.*;
import com.example.uruG.Repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class SubSistemaUSUARIOS {
    @Autowired
    private UsuarioRepo userRepo;
    @Autowired
    private ClienteRepo clienteRepo;
    @Autowired
    private PedidoRepo pedidoRepo;
    @Autowired
    private VendedorRepo vendedorRepo;
    @Autowired
    private CadeteRepo cadeteRepo;
    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private AuxDepoRepo auxRepo;


    public Usuario login(String username, String password) {
        //ACA FALTAN VALIDACIONES
        Optional<Usuario> usuarioOptional = userRepo.findByUsernameAndPassword(username, password);
        if (usuarioOptional.isPresent()) {
            return usuarioOptional.get();
        } else {
            return null;
        }
    }



    public ArrayList<Cliente> obtenerClientes() {
        return (ArrayList<Cliente>) clienteRepo.findAll();
    }

    public Cliente obtenerClientePorId(int clienteId) {
        Optional<Cliente> clienteOpt = clienteRepo.findById(clienteId);

        if (clienteOpt.isPresent()) {
            return clienteOpt.get();
        } else {
             return null; // tirar una exeption
        }
    }

    public Vendedor obtenerVendedorPorId(int idVendedor) {

        Optional<Vendedor> vendedorOpt = vendedorRepo.findById(idVendedor);

        if (vendedorOpt.isPresent()) {
            return vendedorOpt.get();
        } else {
            return null; // tirar una exeption
        }

    }

    public Cadeteria obtenerCadeteriaPorId(int idCadeteria) {
        Optional<Cadeteria> cadeteriaOpt = cadeteRepo.findById(idCadeteria);
        if (cadeteriaOpt.isPresent())
            return cadeteriaOpt.get();
        else
            return null; // tirar una exeption
    }
    public void modificarCadeteria(int clienteId, int cadeteriaId) {

        try {
            Cliente cli = obtenerClientePorId(clienteId);
            Cadeteria cad = obtenerCadeteriaPorId(cadeteriaId);
            cli.modificarCadeteria(cad);
            clienteRepo.save(cli);
        } catch (Exception e) {

        }
    }

    public ArrayList<Cadeteria> obtenerCadeterias() {
        return (ArrayList<Cadeteria>) cadeteRepo.findAll();
    }

    ///////   ZONA DE MANEJO DE CLIENTES  ////////////

    public void guardarCliente(Cliente cliente) throws Exception {

        try {
            cliente.validarDatos();
            clienteRepo.save(cliente);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void eliminarCliente(int idCliente) throws Exception {

        try {
            clienteRepo.deleteById(idCliente);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }




    ///////   ZONA DE MANEJO DE CADETERIA  ////////////

    public void guardarCadeteria(Cadeteria cadeteria) throws Exception {
        try {
            cadeteria.validarCadeteria();
            cadeteRepo.save(cadeteria);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void eliminarCadeteria(int cadeteria) throws Exception {
        try {
            cadeteRepo.deleteById(cadeteria);
        } catch (Exception e) {
            throw new Exception("No se pudo eliminar la cadeteria");
        }
    }

    public boolean modificarPassword(int id, String passviejo, String passnuevo) throws Exception {
        try {

            Optional<Usuario> usu = userRepo.findById(id);
            if (usu.isPresent()) {
                Usuario u = usu.get();
                u.cambiarPassword(passviejo,passnuevo);
                userRepo.save(u);
                return true;
            } else {
                return false;
            }
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        }

    public Administrativo obtenerAdministrativoPorId(int adminId) throws Exception {
      try {
          Optional<Administrativo> adminOpt = adminRepo.findById(adminId);
          if (adminOpt.isPresent()) {
              return adminOpt.get();
          } else {
              throw new Exception("No hay un Administrativo con esa identificacion");
          }
      }catch(Exception e) {
          throw new Exception(e.getMessage());
      }
    }

    public AuxiliarDeposito obtenerAuxDepoPorId(int id) throws Exception {
        try {
            Optional<AuxiliarDeposito> auxOpt = auxRepo.findById(id);
            if (auxOpt.isPresent()) {
                return auxOpt.get();
            } else {
                throw new Exception("No hay un Auxiliar con esa identificacion");
            }
        }catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void guardarCliente2(Cliente cliente, MultipartFile pdf) throws Exception {

            try {
                cliente.validarDatos();
                cliente.agregarPDF(pdf);
                clienteRepo.save(cliente);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
}
