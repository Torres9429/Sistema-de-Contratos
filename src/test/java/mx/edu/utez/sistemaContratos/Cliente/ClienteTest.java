package mx.edu.utez.sistemaContratos.Cliente;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class ClienteTest {
    @Test
    void registrarCliente() {
        ClienteService clienteService = new ClienteService();
        Cliente cliente = new Cliente("Ximena", "Campos", "ximenacampos@gmail.com", "7773486745",true);

        boolean resultado = clienteService.registrarCliente(cliente);

        assertTrue(resultado);
        assertEquals(1, clienteService.consultarClientes().size());
    }

    @Test
    void registrarClienteConDatosInvalidos() {
        ClienteService clienteService = new ClienteService();
        Cliente cliente = new Cliente("", "Campos", "", "7773486745",true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> clienteService.registrarCliente(cliente));

        assertThrows(IllegalArgumentException.class, () -> clienteService.registrarCliente(cliente));
    }
    @Test
    void consultarClientesConExito() {
        ClienteService clienteService = new ClienteService();
        Cliente cliente = new Cliente("Ximena", "Campos", "ximenacampos@gmail.com", "7773486745",true);
        clienteService.registrarCliente(cliente);

        assertFalse(clienteService.consultarClientes().isEmpty());
        assertEquals("Ximena", clienteService.consultarClientes().get(0).getNombre());
    }

    @Test
    void consultarClientesListaVacia() {
        ClienteService clienteService = new ClienteService();

        assertTrue(clienteService.consultarClientes().isEmpty());
    }


}
