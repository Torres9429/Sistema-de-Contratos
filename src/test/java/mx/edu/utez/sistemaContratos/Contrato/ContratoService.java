package mx.edu.utez.sistemaContratos.Contrato;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public void actualizarContrato(Contrato contrato) {
        // Validación de la fecha de vencimiento
        if (!isFechaValida(contrato.getFechaVencimiento())) {
            throw new IllegalArgumentException("La fecha de vencimiento es inválida");
        }

        // Buscar el contrato en la lista y actualizar sus datos
        for (int i = 0; i < contratos.size(); i++) {
            if (contratos.get(i).getNombre().equals(contrato.getNombre())) {
                contratos.get(i).setFechaVencimiento(contrato.getFechaVencimiento());
                contratos.get(i).setCliente(contrato.getCliente());
                contratos.get(i).setCategoria(contrato.getCategoria());
                contratos.get(i).setEstado(contrato.isEstado());
                return;
            }
        }

        // Si el contrato no se encuentra
        throw new IllegalArgumentException("El contrato no existe");
    }

    private boolean isFechaValida(String fecha) {
        try {
            LocalDate.parse(fecha);  // Verifica si la fecha es válida
            return true;
        } catch (DateTimeParseException e) {
            return false;  // Si la fecha es inválida, retorna false
        }
    }

    public void cambiarEstadoContrato(Contrato contrato, boolean nuevoEstado) {
        for (Contrato c : contratos) {
            if (c.getNombre().equals(contrato.getNombre())) {
                c.setEstado(nuevoEstado);
                return;
            }
        }
        throw new IllegalArgumentException("El contrato no existe");
    }


    public List<Contrato> consultarContratosPorEstado(boolean b) {
        return contratos.stream()
                .filter(c -> c.isEstado() == b)
                .collect(Collectors.toList());
    }

}