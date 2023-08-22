package com.example.uruG;
import com.example.uruG.Servicios.GeneralException;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    private Vendedor vendedor;
    @ManyToOne
    private Cliente cliente;
    @ManyToOne
    private Cadeteria cadeteria;
    @ManyToOne
    private Administrativo admin;
    @ManyToOne
    private AuxiliarDeposito auxDep;
    @OneToMany
    private List<Articulo> articulos;
    @Temporal(TemporalType.DATE)
    private Date fecha;
    private String Observaciones;
    private EstadoPedido estado;
    private boolean marcadoRecibidoPorCliente;
    private String numeroEntrega;

    private boolean sinStock = false;

    public Pedido(int id, Vendedor vendedor, Cliente cliente, Cadeteria cadeteria, List<Articulo> articulos) {
        this.vendedor = vendedor;
        this.cliente = cliente;
        this.cadeteria = cadeteria;
        this.articulos = articulos;
        this.estado = EstadoPedido.INGRESADO;
        this.id = id;
        this.fecha = new Date();
    }

    public Pedido() {
    }

    public void agregarArticulo(Articulo articulo) {
        if(articulos == null) {
            articulos = new ArrayList<>();
            articulos.add(articulo);
        }
    }

    public Integer getId() {
        return id;
    }


    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cadeteria getCadeteria() {
        return cadeteria;
    }

    public void setCadeteria(Cadeteria cadeteria) {
        this.cadeteria = cadeteria;
    }

    public Administrativo getAdmin() {
        return admin;
    }

    public void setAdmin(Administrativo admin) {
        this.admin = admin;
    }

    public AuxiliarDeposito getAuxDep() {
        return auxDep;
    }

    public void setAuxDep(AuxiliarDeposito auxDep) {
        this.auxDep = auxDep;
    }

    public List<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String observaciones) {
        Observaciones = observaciones;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public boolean isMarcadoRecibidoPorCliente() {
        return marcadoRecibidoPorCliente;
    }

    public void setMarcadoRecibidoPorCliente(boolean marcadoRecibidoPorCliente) {
        this.marcadoRecibidoPorCliente = marcadoRecibidoPorCliente;
    }

    public String getNumeroEntrega() {
        return numeroEntrega;
    }

    public void setNumeroEntrega(String numeroEntrega) {
        this.numeroEntrega = numeroEntrega;
    }

    public void setearListaArticulos(ArrayList<Articulo> articulos) {
        this.articulos = articulos;
    }

    public void modificarEstadoPedido(EstadoPedido estado) {
        this.estado = estado;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isSinStock() {
        return sinStock;
    }

    public void setSinStock(boolean sinStock) {
        this.sinStock = sinStock;
    }

    public boolean EsEditablePorVendedor() {

        if(estado == EstadoPedido.INGRESADO) {
            estado = EstadoPedido.ENEDICION;
            return true;
        }
        if(estado == EstadoPedido.ENEDICION) {
            return true;
        }
        if(estado == EstadoPedido.PREPARACION) {
            //Hay que notificar al Aux de depo que ese pedido esta siendo modificado
            estado = EstadoPedido.ENEDICION;
            return true;
        }
        if(estado == EstadoPedido.FACTURACION) {
            //Hay que notificar a facturacion que ese pedido esta siendo modificado
            estado = EstadoPedido.ENEDICION;
            return true;
        }
        //Pasadoe se punto no se podra modificar el pedido
        return false;
    }


    public boolean marcarComoPreparacion() {

        if (estado == EstadoPedido.INGRESADO || estado == EstadoPedido.PREPARACION) {
            estado = EstadoPedido.PREPARACION;
            return true;
        }
        return false;
    }

    public boolean marcarComoArmado() {

        if (estado == EstadoPedido.PREPARACION) {
            estado = EstadoPedido.FACTURACION;
            return true;
        }
        return false;
    }

    //se podria poner el numero de factura
    public boolean marcarComoFacturado(Administrativo admin) {

        if (estado == EstadoPedido.FACTURACION) {
            this.estado = EstadoPedido.FACTURADO;
            this.admin = admin;
            return true;
        } else {
            return false;
        }
    }

    public boolean marcarComoListo() {

        if (estado == EstadoPedido.FACTURADO) {
            estado = EstadoPedido.LISTO;
            return true;
        } else {
            return false;
        }
    }

    public boolean marcarComoEntregado() {

        if (estado == EstadoPedido.LISTO || estado == EstadoPedido.ENTREGADO) {
            estado = EstadoPedido.ENTREGADO;
            return true;
        } else {
            return false;
        }
    }

    public boolean marcarComoRecibidoPorCliente(Cliente cli)  {

        if (estado == EstadoPedido.ENTREGADO && cli.equals(cliente)) {
                setMarcadoRecibidoPorCliente(true);
                return true;
            }
        return false;
    }


    public void validar() throws Exception {

        if(vendedor != null && cliente != null && cadeteria != null) {
            if(articulos.size() > 0) {
            } else {
                throw new Exception("el pedido tiene que tener al menos un articulo");
            }
        } else {
            throw new Exception("el pedido tiene que tener un vendedor, un cliente y una cadeteria");
        }
    }

    public boolean agregarNumeroEntrega(String numeroEntrega) {
        if(!numeroEntrega.isBlank()) {
            this.numeroEntrega = numeroEntrega;
            return true;
        }
        return false;
    }


    public void revisarStock() {

        for(Articulo a : articulos) {
            if(a.isStock() == false && a.isEliminado() == false) {
                sinStock = true;
                return;
            }
        } sinStock = false;


    }

    public void actualizarListaAux(ArrayList<Articulo> listaArticulos) {
        setArticulos(listaArticulos);
    }
}