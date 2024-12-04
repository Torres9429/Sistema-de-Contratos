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
}
