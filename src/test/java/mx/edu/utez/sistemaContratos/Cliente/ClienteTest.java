package mx.edu.utez.sistemaContratos.Cliente;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    @Test
    void registrarCliente() {
        ClienteService clienteService = new ClienteService();
        Cliente cliente = new Cliente("Ximena", "Campos", "ximenacampos@gmail.com", "7773486745", true);

        boolean resultado = clienteService.registrarCliente(cliente);

        assertTrue(resultado);
        assertEquals(1, clienteService.consultarClientes().size());
    }

    @Test
    void registrarClienteConDatosInvalidos() {
        ClienteService clienteService = new ClienteService();
        Cliente cliente = new Cliente("", "Campos", "", "7773486745", true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> clienteService.registrarCliente(cliente));

        assertThrows(IllegalArgumentException.class, () -> clienteService.registrarCliente(cliente));
    }

    @Test
    void consultarClientesConExito() {
        ClienteService clienteService = new ClienteService();
        Cliente cliente = new Cliente("Ximena", "Campos", "ximenacampos@gmail.com", "7773486745", true);
        clienteService.registrarCliente(cliente);

        assertFalse(clienteService.consultarClientes().isEmpty());
        assertEquals("Ximena", clienteService.consultarClientes().get(0).getNombre());
    }

    @Test
    void consultarClientesListaVacia() {
        ClienteService clienteService = new ClienteService();

        assertTrue(clienteService.consultarClientes().isEmpty());
    }

    @Test
    void actualizarCliente() {
        ClienteService clienteService = new ClienteService();
        Cliente cliente = new Cliente("Laura", "Gómez", "lauragomez@gmail.com", "777353536", true);
        clienteService.registrarCliente(cliente);
        boolean resultado = clienteService.actualizarCliente("lauragomez@gmail.com", "Laura", "Gómez", "777353903");
        assertTrue(resultado);
        Cliente clienteActualizado = clienteService.consultarClientes().get(0);
        assertEquals("Laura", clienteActualizado.getNombre());
        assertEquals("Gómez", clienteActualizado.getApellido());
        assertEquals("777353903", clienteActualizado.getTelefono());
    }

    @Test
    void verificarValidacionCorreo() {
        ClienteService clienteService = new ClienteService();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> clienteService.validarCorreo("correo-invalido"));
        assertEquals("Correo inválido", exception.getMessage());
    }

    @Test
    void habilitarYDeshabilitarCliente() {
        ClienteService clienteService = new ClienteService();
        Cliente cliente = new Cliente("Carlos", "Méndez", "cmendez@gmail.com", "7771234567", true);
        clienteService.registrarCliente(cliente);
        boolean resultadoDeshabilitar = clienteService.cambiarEstadoCliente("cmendez@gmail.com", false);
        assertTrue(resultadoDeshabilitar);
        assertFalse(clienteService.consultarClientes().get(0).isEstado());
        boolean resultadoHabilitar = clienteService.cambiarEstadoCliente("cmendez@gmail.com", true);
        assertTrue(resultadoHabilitar);
        assertTrue(clienteService.consultarClientes().get(0).isEstado());
    }
}
