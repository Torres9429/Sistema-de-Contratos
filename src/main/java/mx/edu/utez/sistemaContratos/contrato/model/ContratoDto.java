package mx.edu.utez.sistemaContratos.contrato.model;

import jakarta.validation.constraints.NotBlank;

public class ContratoDto {
    @NotBlank(groups = {Modify.class, Register.class}, message = "Es necesario el nombre")
    private String nombre;
    @NotBlank(groups = {Modify.class, Register.class}, message = "Es necesario la descripción")
    private String descripcion;

    public interface Register {
    }

    public interface Modify {
    }

    public interface ChangeStatus {
    }
}
