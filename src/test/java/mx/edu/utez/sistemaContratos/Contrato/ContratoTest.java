package mx.edu.utez.sistemaContratos.Contrato;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContratoTest {

    @Test
    void registrarContratoConDatosValidos() {
        ContratoService contratoService = new ContratoService();

        Contrato contrato = new Contrato("Contrato de Servicios", "Empresa ABC", "Servicios", "2024-12-31");

        boolean resultado = contratoService.registrarContrato(contrato);

        assertTrue(resultado);
        assertEquals(1, contratoService.consultarContratos().size());
        assertEquals("Contrato de Servicios", contratoService.consultarContratos().get(0).getNombre());
        assertEquals("Empresa ABC", contratoService.consultarContratos().get(0).getCliente());
        assertEquals("Servicios", contratoService.consultarContratos().get(0).getCategoria());
        assertEquals("2024-12-31", contratoService.consultarContratos().get(0).getFechaVencimiento());
    }

    @Test
    void verificarDatosContratoConDatosValidos() {
        ContratoService contratoService = new ContratoService();
        Contrato contrato = new Contrato("Contrato de Servicios", "Empresa ABC", "Servicios", "2024-12-31");
        boolean resultado = contratoService.verificarDatosContrato(contrato);
        assertTrue(resultado);
    }
}
