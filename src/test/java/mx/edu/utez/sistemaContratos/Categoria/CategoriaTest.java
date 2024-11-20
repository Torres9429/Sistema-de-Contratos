package mx.edu.utez.sistemaContratos.Categoria;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class CategoriaTest {
    @Test
    void registrarCategoriaConDatosValidos() {
        CategoriaService categoriaService = new CategoriaService();
        Categoria categoria = new Categoria("Consultoría", "Servicios de consultoría empresarial", true);

        boolean resultado = categoriaService.registrarCategoria(categoria);

        assertTrue(resultado);
        assertEquals(1, categoriaService.consultarCategorias().size());
    }

    @Test
    void registrarCategoriaConDatosInvalidos() {
        CategoriaService categoriaService = new CategoriaService();
        Categoria categoria = new Categoria("", "Descripción inválida", true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> categoriaService.registrarCategoria(categoria));

        assertEquals("El nombre de la categoría es obligatorio", exception.getMessage());
    }

    @Test
    void cambiarEstadoCategoriaHabilitar() {
        CategoriaService categoriaService = new CategoriaService();
        Categoria categoria = new Categoria("Consultoría", "Servicios de consultoría empresarial", false);
        categoriaService.registrarCategoria(categoria);

        boolean resultado = categoriaService.cambiarEstadoCategoria(categoria, true);

        assertTrue(resultado);
        assertEquals(true, categoria.isEstado());
    }
/*
    @Test
    void cambiarEstadoCategoriaInvalido() {
        CategoriaService categoriaService = new CategoriaService();
        Categoria categoria = new Categoria("Consultoría", "Servicios de consultoría empresarial", true);
        categoriaService.registrarCategoria(categoria);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> categoriaService.cambiarEstadoCategoria(categoria, false));

        assertEquals("Estado inválido", exception.getMessage());
    }*/
}