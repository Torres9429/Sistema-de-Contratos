package mx.edu.utez.sistemaContratos.contrato.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ContratoDto {
    @NotNull(groups = {ContratoDto.Modify.class, ContratoDto.ChangeStatus.class},message = "El id no puede ser nulo")
    private Long id;
    @NotBlank(groups = {Modify.class, Register.class}, message = "Es necesario el nombre")
    private String nombre;
    @NotBlank(groups = {Modify.class, Register.class}, message = "Es necesario la descripción")
    private String descripcion;
    @NotNull(groups = {Modify.class, Register.class}, message = "La fecha no puede ser nula")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaVencimiento;
    @NotNull(groups = {ContratoDto.Modify.class, ContratoDto.ChangeStatus.class},message = "El id del cliente no puede ser nulo")
    private Long clienteId;
    @NotNull(groups = {ContratoDto.Modify.class, ContratoDto.ChangeStatus.class},message = "El id de la categoría no puede ser nulo")
    private Long categoriaId;

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

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public interface Register {
    }

    public interface Modify {
    }

    public interface ChangeStatus {
    }
}