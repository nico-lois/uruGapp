package com.example.uruG.Repositorios;


import com.example.uruG.*;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface PedidoRepo extends CrudRepository<Pedido, Integer> {
    List<Pedido> findByCliente(Cliente cliente);
    List<Pedido> findByVendedor(Vendedor vendedor);
    List<Pedido> findByCadeteria(Cadeteria cadeteria);
}
