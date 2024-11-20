package mx.edu.utez.sistemaContratos.contrato.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class ContratoDto {
    @NotNull(groups = {ContratoDto.Modify.class, ContratoDto.ChangeStatus.class},message = "El id no puede ser nulo")
    private Long id;
    @NotBlank(groups = {Modify.class, Register.class}, message = "Es necesario el nombre")
    private String nombre;
    @NotBlank(groups = {Modify.class, Register.class}, message = "Es necesario la descripción")
    private String descripcion;
    @NotNull(groups = {ContratoDto.Modify.class, ContratoDto.ChangeStatus.class},message = "La fecha no puede ser nula")
    private Date fechaVencimiento;
    private Long clienteId;
    private Long categoriaContrato_id;

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

    public Long getCategoriaContrato_id() {
        return categoriaContrato_id;
    }

    public void setCategoriaContrato_id(Long categoriaContrato_id) {
        this.categoriaContrato_id = categoriaContrato_id;
    }

    public interface Register {
    }

    public interface Modify {
    }

    public interface ChangeStatus {
    }
}
