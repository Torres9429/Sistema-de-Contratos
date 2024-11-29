package mx.edu.utez.sistemaContratos.categoriaContrato.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CategoriaDto {
    @NotNull(groups = {CategoriaDto.Modify.class, CategoriaDto.ChangeStatus.class}, message = "El ID no puede quedar vacío")
    private Long id;
    @NotBlank(groups = {Modify.class, Register.class}, message = "Es necesario el nombre")
    private String nombre;
    @NotBlank(groups = {Modify.class, Register.class}, message = "Es necesario la descripción")
    private String descripcion;

    public CategoriaDto() {
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

    public interface Register {
    }

    public interface Modify {
    }

    public interface ChangeStatus {
    }
}
