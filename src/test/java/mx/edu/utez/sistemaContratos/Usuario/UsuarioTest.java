package mx.edu.utez.sistemaContratos.Usuario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {
    /*
    *
Nombre del Caso de Prueba: Registrar Usuario
ID del Caso de Prueba: CP-001
Creado por: Abril Aidee Medina Villa
Fecha de Creación: 30/11/2024
Última Modificación: 31/11/2024
*
Descripción :
Validar que un administrador pueda registrar correctamente a un usuario con datos válidos.
Precondiciones:
*El rol del usuario que registra debe ser administrador.
*Los campos obligatorios (nombre, apellidos, correo, contraseña, rol) deben ser válidos y no nulos.
Pasos de Prueba:
1-Simular el envío de datos válidos para registrar un usuario.
2-Llamar al método registrarUsuario() con los datos proporcionados.
3-Verificar que el usuario se haya guardado en la base de datos con el estado activo.
Datos de Prueba:
Nombre: Juan
Apellidos: Pérez
Correo: juan.perez@dominio.com
Teléfono: 123456789
Contraseña: Contraseña123
Rol: Cliente
Status: Activo
* Resultado Esperado
El usuario se registra correctamente y aparece en la base de datos con los valores proporcionados.
     */
  @Test
    void registrarUsuarioConCamposCompletos() {
        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario = new Usuario("Juan", "Perez", "juan.perez@gmail.com", "123456789", "contraseña123", "Usuario", true);

        boolean resultado = usuarioService.registrarUsuario(usuario);

        assertTrue(resultado);
        assertEquals(1, usuarioService.consultarUsuarios().size());
    }
    /*
        *
    Nombre del Caso de Prueba: Registrar Usuario
    ID del Caso de Prueba: CP-001
    Creado por: Abril Aidee Medina Villa
    Fecha de Creación: 30/11/2024
    Última Modificación: 31/11/2024
    *
    Descripción :
    Validar que un administrador pueda registrar correctamente a un usuario con datos válidos.
    Precondiciones:
    *El rol del usuario que registra debe ser administrador.
    *Los campos obligatorios (nombre, apellidos, correo, contraseña, rol) deben ser válidos y no nulos.
    Pasos de Prueba:
    1-Simular el envío de datos válidos para registrar un usuario.
    2-Llamar al método registrarUsuario() con los datos proporcionados.
    3-Verificar que el usuario se haya guardado en la base de datos con el estado activo.
    Datos de Prueba:
    Nombre: Juan
    Apellidos: Pérez
    Correo: juan.perez@dominio.com
    Teléfono: 123456789
    Contraseña: Contraseña123
    Rol: Cliente
    Status: Activo
    * Resultado Esperado
    El usuario se registra correctamente y aparece en la base de datos con los valores proporcionados.
         */
    @Test
    void registrarUsuarioConCamposInvalidos() {
        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario = new Usuario("", "Medina", "alomedina.gmail.com", "7771234567", "PasswordAlo1", "Usuario", true);

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

}
