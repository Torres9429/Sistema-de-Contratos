package mx.edu.utez.sistemaContratos.usuario.model;

import mx.edu.utez.sistemaContratos.contrato.model.Contrato;
import mx.edu.utez.sistemaContratos.role.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", columnDefinition = "VARCHAR(70)")
    private String nombre;

    @Column(name = "apellidos", columnDefinition = "VARCHAR(70)")
    private String apellidos;

    @Column(name = "correo", columnDefinition = "VARCHAR(20)")
    private String correo;

    @Column(name = "telefono", columnDefinition = "VARCHAR(10)")
    private String telefono;

    @Column(name = "contrasena", columnDefinition = "VARCHAR(70)")
    private String contrasena;

    @Column(name = "status", columnDefinition = "BOOL DEFAULT TRUE")
    private boolean status;
    @ManyToMany
    @JsonIgnore
    private List<Contrato> contratos;

    @ManyToMany
    @JoinTable(
            name = "usuario_role",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public Usuario() {}

    public Usuario(String nombre, String apellidos, String correo, String telefono, String contrasena, boolean status) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.status = status;
    }

    // Getters y Setters
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
