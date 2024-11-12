package mx.edu.utez.sistemaContratos.categoriaContrato.model;

import jakarta.validation.constraints.NotBlank;

public class CategoriaDto {
    @NotBlank(groups = {Modify.class, Register.class}, message = "Es necesario el nombre")
    private String nombre;
    @NotBlank(groups = {Modify.class, Register.class}, message = "Es necesario la descripción")
    private String descripcion;

    public CategoriaDto() {
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

    public interface Register {
    }

    public interface Modify {
    }

    public interface ChangeStatus {
    }
}
