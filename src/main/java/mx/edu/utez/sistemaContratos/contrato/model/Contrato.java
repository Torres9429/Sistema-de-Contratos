package mx.edu.utez.sistemaContratos.contrato.model;

import mx.edu.utez.sistemaContratos.categoriaContrato.model.Categoria;
import mx.edu.utez.sistemaContratos.cliente.model.Cliente;
import mx.edu.utez.sistemaContratos.usuario.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "contratos")
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", columnDefinition = "VARCHAR(30)")
    private String nombre;
    @Column(name = "descripcion", columnDefinition = "VARCHAR(70)")
    private String descripcion;
    @Column(name = "fechaVencimiento", columnDefinition = "DATE")
    private Date fechaVencimiento;
    @Column(name = "status", columnDefinition = "BOOL DEFAULT TRUE")
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "categoriaContrato_id", nullable = false)
    private Categoria categorias;

    @ManyToMany(mappedBy = "contratos")
    @JsonIgnore
    private List<Usuario> usuarios;

    public Contrato(String nombre, String descripcion, Date fechaVencimiento) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaVencimiento = fechaVencimiento;
        this.status = true;

    }

    public Contrato(Long id, String nombre, String descripcion, Date fechaVencimiento, boolean status) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaVencimiento = fechaVencimiento;
        this.status = status;
    }

    public Contrato(String nombre, String descripcion, Date fechaVencimiento, boolean status) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaVencimiento = fechaVencimiento;
        this.status = status;
    }

    public Contrato() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Categoria getCategorias() {
        return categorias;
    }

    public void setCategorias(Categoria categorias) {
        this.categorias = categorias;
    }
}