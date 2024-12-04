package mx.edu.utez.sistemaContratos.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteService {
    private List<Cliente> clientes = new ArrayList<>();

    public boolean registrarCliente(Cliente cliente) {
        if (cliente.getCorreo() == null || cliente.getCorreo().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio");
        }
        if (cliente.getNombre() == null || cliente.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        for (Cliente u : clientes) {
            if (u.getCorreo().equals(cliente.getCorreo())) {
                throw new IllegalArgumentException("Correo duplicado");
            }
        }
        clientes.add(cliente);
        return true;
    }

    public List<Cliente> consultarClientes() {
        return clientes;
    }

    public boolean actualizarCliente(String correo, String nuevoNombre, String nuevoApellido, String nuevoTelefono) {
        for (Cliente cliente : clientes) {
            if (cliente.getCorreo().equals(correo)) {
                cliente.setNombre(nuevoNombre);
                cliente.setApellido(nuevoApellido);
                cliente.setTelefono(nuevoTelefono);
                return true;
            }
        }
        throw new IllegalArgumentException("Cliente no encontrado");
    }

    public void validarCorreo(String correo) {
        if (correo == null || !correo.contains("@")) {
            throw new IllegalArgumentException("Correo inválido");
        }
    }

    public boolean cambiarEstadoCliente(String correo, boolean nuevoEstado) {
        for (Cliente cliente : clientes) {
            if (cliente.getCorreo().equals(correo)) {
                cliente.setEstado(nuevoEstado);
                return true;
            }
        }
        throw new IllegalArgumentException("Cliente no encontrado");
    }
}
