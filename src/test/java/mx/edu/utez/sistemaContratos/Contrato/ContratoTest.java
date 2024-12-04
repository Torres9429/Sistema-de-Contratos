package mx.edu.utez.sistemaContratos.Contrato;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContratoTest {


    @Test
    void registrarContratoConDatosValidos() {
        ContratoService contratoService = new ContratoService();

        Contrato contrato = new Contrato("Contrato de Servicios", "Empresa ABC", "Servicios", "2024-12-31", true);

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
        Contrato contrato = new Contrato("Contrato de Servicios", "Empresa ABC", "Servicios", "2024-12-31",true);
        boolean resultado = contratoService.verificarDatosContrato(contrato);
        assertTrue(resultado);
    }
    //CP:36
    @Test
    void registrarContratoConDatosFaltantesOInvalidos() {
        ContratoService contratoService = new ContratoService();
        Contrato contrato = new Contrato("", "Empresa XYZ", "Consultoría", "2023-02-30", true);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            contratoService.registrarContrato(contrato);
        });
        assertEquals("El nombre es obligatorio", exception.getMessage());
    }
    //CP:37
    @Test
    void consultarTodosLosContratos() {
        ContratoService contratoService = new ContratoService();
        Contrato contrato1 = new Contrato("Contrato 1", "Empresa ABC", "Servicios", "2024-12-31",true);
        Contrato contrato2 = new Contrato("Contrato 2", "Empresa XYZ", "Mantenimiento", "2025-06-30",true);
        contratoService.registrarContrato(contrato1);
        contratoService.registrarContrato(contrato2);

        List<Contrato> contratos = contratoService.consultarContratos();

        assertEquals(2, contratos.size());
        assertEquals("Contrato 1", contratos.get(0).getNombre());
        assertEquals("Contrato 2", contratos.get(1).getNombre());
    }
    //CP:38
    @Test
    void consultaContratosActivosEInactivosPorBooleano() {
        ContratoService contratoService = new ContratoService();
        Contrato contratoActivo = new Contrato("Contrato Activo", "Empresa ABC", "Servicios", "2024-12-31", true);
        Contrato contratoInactivo = new Contrato("Contrato Inactivo", "Empresa XYZ", "Consultoría", "2023-06-30", false);

        contratoService.registrarContrato(contratoActivo);
        contratoService.registrarContrato(contratoInactivo);

        List<Contrato> contratosActivos = contratoService.consultarContratosPorEstado(true);
        List<Contrato> contratosInactivos = contratoService.consultarContratosPorEstado(false);

        assertEquals(1, contratosActivos.size());
        assertEquals("Contrato Activo", contratosActivos.get(0).getNombre());

        assertEquals(1, contratosInactivos.size());
        assertEquals("Contrato Inactivo", contratosInactivos.get(0).getNombre());
    }


    //CP:39
    @Test
    void actualizarContratoConExito() {
        ContratoService contratoService = new ContratoService();
        Contrato contrato = new Contrato("Contrato de Servicios", "Empresa ABC", "Servicios", "2024-12-31", true);
        contratoService.registrarContrato(contrato);

        contrato.setFechaVencimiento("2025-06-30");
        contratoService.actualizarContrato(contrato);

        Contrato contratoActualizado = contratoService.consultarContratos().get(0);
        assertEquals("2025-06-30", contratoActualizado.getFechaVencimiento());
    }


    //CP:40
    @Test
    void validarDatosDeActualizacion() {
        ContratoService contratoService = new ContratoService();
        Contrato contrato = new Contrato("Contrato de Servicios", "Empresa ABC", "Servicios", "2024-12-31",true);
        contratoService.registrarContrato(contrato);

        contrato.setFechaVencimiento("2023-02-30");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            contratoService.actualizarContrato(contrato);
        });

        assertEquals("La fecha de vencimiento es inválida", exception.getMessage());
    }

    //CP:41
    @Test
    void habilitarYDeshabilitarContrato() {
        ContratoService contratoService = new ContratoService();
        Contrato contrato = new Contrato("Contrato de Servicios", "Empresa ABC", "Servicios", "2024-12-31",true);
        contratoService.registrarContrato(contrato);

        assertTrue(contrato.isEstado());

        contratoService.cambiarEstadoContrato(contrato, false);
        assertFalse(contrato.isEstado());

        contratoService.cambiarEstadoContrato(contrato, true);
        assertTrue(contrato.isEstado());
    }





















}
