package mx.edu.utez.sistemaContratos.Categoria;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoriaTest {

    //cp-22
    @Test
    void consultarTodasLasCategorias() {
        CategoriaService categoriaService = new CategoriaService();
        Categoria categoria1 = new Categoria("Consultoría", "Servicios de consultoría empresarial", true);
        Categoria categoria2 = new Categoria("Servicios legales", "Contratos para servicios jurídicos", true);
        categoriaService.registrarCategoria(categoria1);
        categoriaService.registrarCategoria(categoria2);

        var categoriasRegistradas = categoriaService.consultarCategorias();

        assertEquals(2, categoriasRegistradas.size());
        assertTrue(categoriasRegistradas.contains(categoria1));
        assertTrue(categoriasRegistradas.contains(categoria2));
    }
    //cp-23
    @Test
    void consultarCategoriasActivas() {
        CategoriaService categoriaService = new CategoriaService();

        Categoria categoria1 = new Categoria("Consultoría", "Contratos de asesoría para empresas", true);
        Categoria categoria2 = new Categoria("Servicios legales", "Contratos para servicios jurídicos", true);
        Categoria categoriaInactiva = new Categoria("Soporte técnico", "Servicios de soporte a empresas", false);

        categoriaService.registrarCategoria(categoria1);
        categoriaService.registrarCategoria(categoria2);
        categoriaService.registrarCategoria(categoriaInactiva);


        List<Categoria> categoriasActivas = categoriaService.consultarCategoriasActivas();


        assertEquals(2, categoriasActivas.size());
        assertTrue(categoriasActivas.contains(categoria1));
        assertTrue(categoriasActivas.contains(categoria2));
        assertFalse(categoriasActivas.contains(categoriaInactiva));
    }
    //cp-24
    @Test
    void actualizarCategoriaExistente() {
        CategoriaService categoriaService = new CategoriaService();

        Categoria categoriaExistente = new Categoria("Servicios legales", "Contratos para servicios jurídicos", true);
        categoriaService.registrarCategoria(categoriaExistente);

        Categoria categoriaModificada = new Categoria("Servicios legales", "Contratos para servicios empresariales", true);

        boolean resultado = categoriaService.actualizarCategoria(categoriaModificada);

        assertTrue(resultado);

        Categoria categoriaActualizada = categoriaService.consultarCategorias().stream()
                .filter(c -> c.getNombre().equals("Servicios legales"))
                .findFirst()
                .orElse(null);

        assertNotNull(categoriaActualizada);
        assertEquals("Contratos para servicios empresariales", categoriaActualizada.getDescripcion());
        assertEquals(true, categoriaActualizada.isEstado());
    }
    //cp-25
    @Test
    void registrarCategoriaConDatosValidos() {
        CategoriaService categoriaService = new CategoriaService();
        Categoria categoria = new Categoria("Consultoría", "Servicios de consultoría empresarial", true);

        boolean resultado = categoriaService.registrarCategoria(categoria);

        assertTrue(resultado);
        assertEquals(1, categoriaService.consultarCategorias().size());
    }
    //26
    @Test
    void registrarCategoriaConDatosInvalidos() {
        CategoriaService categoriaService = new CategoriaService();
        Categoria categoria = new Categoria("", "Descripción inválida", true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> categoriaService.registrarCategoria(categoria));

        assertEquals("El nombre de la categoría es obligatorio", exception.getMessage());
    }
    //26
    @Test
    void cambiarEstadoCategoriaHabilitar() {
        CategoriaService categoriaService = new CategoriaService();
        Categoria categoria = new Categoria("Consultoría", "Servicios de consultoría empresarial", false);
        categoriaService.registrarCategoria(categoria);

        boolean resultado = categoriaService.cambiarEstadoCategoria(categoria, true);

        assertTrue(resultado);
        assertEquals(true, categoria.isEstado());
    }
    //cp-27
    @Test
    void deshabilitarCategoria() {
        CategoriaService categoriaService = new CategoriaService();

        Categoria categoria = new Categoria("Consultoría", "Contratos de asesoría para empresas", true);
        categoriaService.registrarCategoria(categoria);

        categoriaService.cambiarEstadoCategoria(categoria, false);

        assertFalse(categoria.isEstado());
    }



}