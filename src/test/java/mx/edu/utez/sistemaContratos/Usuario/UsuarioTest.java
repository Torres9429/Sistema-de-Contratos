package mx.edu.utez.sistemaContratos.Usuario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {
    @Test
    void registrarUsuarioConCamposCompletos() {
        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario = new Usuario("Alondra", "Medina", "alomedina@gmail.com", "7771234567", "PasswordAlo1", "Usuario", true);

        boolean resultado = usuarioService.registrarUsuario(usuario);

        assertTrue(resultado);
        assertEquals(1, usuarioService.consultarUsuarios().size());
    }

    @Test
    void registrarUsuarioConCamposInvalidos() {
        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario = new Usuario("", "Medina", "correo-invalido@", "7771234567", "PasswordAlo1", "Usuario", true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> usuarioService.registrarUsuario(usuario));

        assertThrows(IllegalArgumentException.class, () -> usuarioService.registrarUsuario(usuario));
    }

    @Test
    void registrarUsuarioConCorreoDuplicado() {
        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario1 = new Usuario("Alondra", "Medina", "alomedina@gmail.com", "7771234567", "PasswordAlo1", "Usuario", true);
        Usuario usuario2 = new Usuario("Alondra", "Medina", "alomedina@gmail.com", "7771234567", "PasswordAlo1", "Usuario", true);

        usuarioService.registrarUsuario(usuario1);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> usuarioService.registrarUsuario(usuario2));

        assertEquals("Correo duplicado", exception.getMessage());
    }

    @Test
    void consultarUsuariosExitoso() {
        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario = new Usuario("Liliana", "Arcos", "arcoslili@gmail.com", "7774562890", "lili123", "Usuario", true);
        usuarioService.registrarUsuario(usuario);

        assertFalse(usuarioService.consultarUsuarios().isEmpty());
        assertEquals("Liliana", usuarioService.consultarUsuarios().get(0).getNombre());
    }

    @Test
    void consultarUsuariosListaVacia() {
        UsuarioService usuarioService = new UsuarioService();

        assertTrue(usuarioService.consultarUsuarios().isEmpty());
    }

    @Test
    void editarUsuarioExitoso() {
        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario = new Usuario("Liliana", "Arcos", "arcoslili@gmail.com", "7771234567", "Password123", "Usuario", true);
        usuarioService.registrarUsuario(usuario);
        boolean resultado = usuarioService.editarUsuario(usuario, "Liliana Actualizada", "Arcos","7771234567");

        assertTrue(resultado);
        assertEquals("Liliana Actualizada", usuario.getNombre());
    }

    @Test
    void editarUsuarioConNombreInvalido() {
        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario = new Usuario("Liliana", "Arcos", "arcoslili@gmail.com", "7771234567", "Password123", "Usuario", true);
        usuarioService.registrarUsuario(usuario);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> usuarioService.editarUsuario(usuario, "Liliana", "", "7771234567"));
        assertEquals("El apellido no puede estar vacío", exception.getMessage());
    }

    @Test
    void cambiarEstadoUsuarioHabilitar() {
        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario = new Usuario("Carlos", "Méndez", "cmendez@gmail.com", "7771234567", "Password123", "Usuario", false);
        usuarioService.registrarUsuario(usuario);

        usuario.setEstado(true);

        assertEquals(true, usuario.isEstado());
    }

    @Test
    void cambiarEstadoUsuarioDeshabilitar() {
        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario = new Usuario("Carlos", "Méndez", "cmendez@gmail.com", "7771234567", "Password123", "Usuario", true);
        usuarioService.registrarUsuario(usuario);

        usuario.setEstado(false);

        assertEquals(false, usuario.isEstado());
    }

    @Test
    void cambiarEstadoUsuarioFallo() {
        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario = new Usuario("Carlos","Méndez","cmendez@gmail.com","7771234567","Password123", "Usuario",true);

        Exception exception = assertThrows(IllegalArgumentException.class,() -> usuarioService.cambiarEstadoUsuario(usuario, false));
        assertEquals("El usuario no está registrado en el sistema.", exception.getMessage());
    }

    @Test
    void consultarPerfilUsuarioExito() {
        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario = new Usuario("Carlos","Méndez","cmendez@gmail.com","7771234567","Password123", "Usuario",true);
        usuarioService.registrarUsuario(usuario);

        Usuario perfilConsultado = usuarioService.consultarPerfilUsuario("cmendez@gmail.com");
        assertNotNull(perfilConsultado);

        assertEquals("Carlos", perfilConsultado.getNombre());
        assertEquals("Méndez", perfilConsultado.getApellido());
        assertEquals("cmendez@gmail.com", perfilConsultado.getCorreo());
        assertEquals("7771234567", perfilConsultado.getTelefono());
        assertEquals("Password123", perfilConsultado.getContrasena());
        assertEquals("Usuario", perfilConsultado.getRol());

        assertTrue(perfilConsultado.isEstado());
    }

    @Test
    void editarPerfil() {
        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario = new Usuario("Carlos","Méndez","cmendez@gmail.com","7771234567","Password123","Usuario",true);
        usuarioService.registrarUsuario(usuario);

        boolean resultado = usuarioService.editarPerfil(usuario,"Carlos Alberto","Méndez Pérez", "cmendezperez@gmail.com","7777654321");
        assertTrue(resultado);

        assertEquals("Carlos Alberto", usuario.getNombre());
        assertEquals("Méndez Pérez", usuario.getApellido());
        assertEquals("cmendezperez@gmail.com", usuario.getCorreo());
        assertEquals("7777654321", usuario.getTelefono());
    }

}
