package mx.edu.utez.sistemaContratos.usuario.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UsuarioDto {

    @NotNull(groups = {Modify.class, ChangeStatus.class}, message = "Es necesario el id")
    private Long id;

    @NotBlank(groups = {Modify.class, Register.class}, message = "Es necesario el nombre")
    private String nombre;

    @NotBlank(groups = {Modify.class, Register.class}, message = "Son necesarios los apellidos")
    private String apellidos;

    @NotBlank(groups = {Modify.class, Register.class}, message = "Es necesario el correo electrónico")
    private String correo;

    @NotBlank(groups = {Modify.class, Register.class}, message = "Es necesario el teléfono")
    private String telefono;

    @NotBlank(groups = {Modify.class, Register.class}, message = "Es necesaria la contraseña")
    private String contrasena;

    @NotBlank(groups = {Modify.class, Register.class}, message = "Es necesario el rol")
    private String role; // Corregido 'role' para mantener consistencia en los nombres

    // Constructor vacío para la serialización o la creación de instancias sin datos
    public UsuarioDto() {
    }

    // Constructor con parámetros para instanciar rápidamente con los datos
    public UsuarioDto(Long id, String nombre, String apellidos, String correo, String telefono, String contrasena, String role) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.role = role;
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

    public String getRole() { // Renombrado a getRole() para alinearse con el atributo 'role'
        return role;
    }

    public void setRole(String role) { // Renombrado a setRole() para mantener la consistencia
        this.role = role;
    }

    // Interfaces para validación
    public interface Register {}
    public interface Modify {}
    public interface ChangeStatus {}
}
