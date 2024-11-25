package mx.edu.utez.sistemaContratos.cliente.model;

import jakarta.persistence.*;
@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", columnDefinition = "VARCHAR(30)")
    private String nombre;
    @Column(name = "apellidos", columnDefinition = "VARCHAR(30)")
    private String apellidos;
    @Column(name = "correo", columnDefinition = "VARCHAR(50)")
    private String correo;
    @Column(name = "telefono", columnDefinition = "VARCHAR(10)")
    private String telefono;
    @Column(name = "status", columnDefinition = "BOOL DEFAULT TRUE")
    private boolean status;

    public Cliente() {
    }

    public Cliente(Long id, String nombre, String apellidos, String correo, String telefono, boolean status) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.status = status;
    }

    public Cliente(String nombre, String apellidos, String correo, String telefono, boolean status) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
