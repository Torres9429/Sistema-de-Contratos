package mx.edu.utez.sistemaContratos.Contrato;

import java.util.ArrayList;
import java.util.List;

public class ContratoService {
    private List<Contrato> contratos = new ArrayList<>();

    public boolean registrarContrato(Contrato contrato) {
        if (contrato.getNombre() == null || contrato.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (contrato.getCliente() == null || contrato.getCliente().isEmpty()) {
            throw new IllegalArgumentException("El cliente es obligatorio");
        }
        if (contrato.getCategoria() == null || contrato.getCategoria().isEmpty()) {
            throw new IllegalArgumentException("La categoría es obligatoria");
        }
        if (contrato.getFechaVencimiento() == null || contrato.getFechaVencimiento().isEmpty()) {
            throw new IllegalArgumentException("La fecha de vencimiento es obligatoria");
        }
        contratos.add(contrato);
        return true;
    }

    public List<Contrato> consultarContratos() {
        return contratos;
    }

    public boolean verificarDatosContrato(Contrato contrato) {
        return contrato.getNombre() != null && !contrato.getNombre().isEmpty() &&
                contrato.getCliente() != null && !contrato.getCliente().isEmpty() &&
                contrato.getCategoria() != null && !contrato.getCategoria().isEmpty() &&
                contrato.getFechaVencimiento() != null && !contrato.getFechaVencimiento().isEmpty();
    }
}
