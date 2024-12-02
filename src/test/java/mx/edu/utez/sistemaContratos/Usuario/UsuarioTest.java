package mx.edu.utez.sistemaContratos.Usuario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {
    /*

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
Rol: Usuario
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
    Validar que un administrador no pueda registrar un Usuario Con Campos Invalidos.
    Precondiciones:
    *El rol del usuario que registra debe ser administrador.
    *Los campos obligatorios (nombre, apellidos, correo, contraseña, rol) deben ser válidos y no nulos.
    Pasos de Prueba:
    1-Simular el envío de datos inválidos para registrar un usuario.
    2-Llamar al método registrarUsuario() con los datos proporcionados.
    3-Verificar que el usuario se haya guardado en la base de datos con el estado activo.
    Datos de Prueba:
    Nombre:
    Apellidos: Medina
    Correo: alomedina@gmail.com
    Teléfono: 7771234567
    Contraseña: PasswordAlo1
    Rol: Usuario
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

    /*
Nombre del Caso de Prueba: Consultar Usuarios
ID del Caso de Prueba: CP-002
Creado por: Rocriguez Torres Rocio
Fecha de Creación: 30/11/2024
Última Modificación: 31/11/2024
Detalles del Caso de Prueba
Descripción:
Validar que se obtengan correctamente los usuarios registrados según el rol.
Precondiciones:
Deben existir usuarios registrados en la base de datos.
Pasos de Prueba
Llamar al método consultarUsuarios().
Verificar que devuelve una lista con los usuarios esperados según el rol del solicitante.
Datos de Prueba
Usuario 1: Administrador
Usuario 2: Cliente
nombre:Liliana
apellido:Arcos
correo:arcoslili@gmail.com
telefono:7774562890
contraseña:lili123
rol:cliente
estados:activo
Resultado Esperado
Se devuelve una lista con los usuarios esperados y visibles según las restricciones del rol del solicitante.
*/
    @Test
    void consultarUsuariosExitoso() {
        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario = new Usuario("Liliana", "Arcos", "arcoslili@gmail.com", "7774562890", "lili123", "Cliente", true);
        usuarioService.registrarUsuario(usuario);

        assertFalse(usuarioService.consultarUsuarios().isEmpty());
        assertEquals("Liliana", usuarioService.consultarUsuarios().get(0).getNombre());
    }

    /*
Nombre del Caso de Prueba: Consultar Usuarios con listas vacias
ID del Caso de Prueba: CP-002
Creado por: Rocriguez Torres Rocio
Fecha de Creación: 30/11/2024
Última Modificación: 31/11/2024
Detalles del Caso de Prueba
Descripción:
Validar que se obtengan correctamente los usuarios registrados según el rol.
Precondiciones:
Deben existir usuarios registrados en la base de datos.
Pasos de Prueba
Llamar al método consultarUsuarios().
Verificar que devuelve una lista con los usuarios esperados según el rol del solicitante.
Datos de Prueba:
no aplica
Resultado Esperado
Se devuelve una lista vacia con ningun usuarios visible.
*/
    @Test
    void consultarUsuariosListaVacia() {
        UsuarioService usuarioService = new UsuarioService();

        assertTrue(usuarioService.consultarUsuarios().isEmpty());
    }
/*
Nombre del Caso de Prueba: Editar Usuario
ID del Caso de Prueba: CP-003
Creado por: Angel Daniel Ocampo Martinez
Fecha de Creación: 30/11/2024
Última Modificación: 31/11/2024
Detalles del Caso de Prueba
Descripción
Validar que se puedan actualizar los datos de un usuario existente correctamente.
Precondiciones
El usuario debe existir en la base de datos.
Los datos a actualizar deben ser válidos.
Pasos de Prueba
Buscar al usuario en la base de datos mediante su ID.
Llamar al método editarUsuario() con los datos actualizados.
Verificar que los cambios se reflejan en la base de datos.
Datos de Prueba
Usuario antes de editar:
Nombre: Liliana Actualizada
Apellidos: Arcos
Correo: arcoslili@gmail.com
 Datos a actualizar:
Teléfono: 7771234567
Contraseña: Password123
Rol: Administrador
Resultado Esperado
El usuario se actualiza correctamente con los nuevos datos proporcionados.
*/
    @Test
    void editarUsuarioExitoso() {
        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario = new Usuario("Liliana", "Arcos", "arcoslili@gmail.com", "7771234567", "Password123", "Administrador", true);
        usuarioService.registrarUsuario(usuario);

        boolean resultado = usuarioService.editarUsuario(usuario, "Liliana Actualizada", "Arcos","7771234567");

        assertTrue(resultado);
        assertEquals("Liliana Actualizada", usuario.getNombre());
    }
    /*
    Nombre del Caso de Prueba: Editar Usuario con nombres Invalidos
    ID del Caso de Prueba: CP-003
    Creado por: Angel Daniel Ocampo Martinez
    Fecha de Creación: 30/11/2024
    Última Modificación: 31/11/2024
    Detalles del Caso de Prueba
    Descripción
    Validar que se puedan actualizar los datos de un usuario existente correctamente.
    Precondiciones
    El usuario debe existir en la base de datos.
    Los datos a actualizar deben ser inválidos.
    Pasos de Prueba
    Buscar al usuario en la base de datos mediante su ID.
    Llamar al método editarUsuario() con los datos actualizados.
    Verificar que los cambios no se reflejan en la base de datos.
    Datos de Prueba
    Usuario antes de editar:
    Nombre: Liliana
    Apellidos:
    Correo: arcoslili@gmail.com
     Datos a actualizar:
    Teléfono: 7771234567
    Contraseña: Password123
    Rol: Administrador
    Resultado Esperado
    El usuario no se actualiza correctamente con los nuevos datos proporcionados y lanza una Exeption.
    */
    @Test
    void editarUsuarioConNombreInvalido() {
        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario = new Usuario("Liliana", "Arcos", "arcoslili@gmail.com", "7771234567", "Password123", "Usuario", true);
        usuarioService.registrarUsuario(usuario);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> usuarioService.editarUsuario(usuario, "Liliana", "", "7771234567"));

        assertEquals("El apellido no puede estar vacío", exception.getMessage());
    }
/*
Nombre del Caso de Prueba: Cambio de Estado de Usuario Habilitar
ID del Caso de Prueba: CP-004
Creado por: Abril Aidee Medina Villa
Fecha de Creación: 30/11/2024
Última Modificación: 31/11/2024
Detalles del Caso de Prueba
Descripción
Validar que un administrador pueda habilitar a un usuario existente.
Precondiciones
El usuario debe existir en la base de datos.
El rol del solicitante debe ser administrador.
Pasos de Prueba
Buscar al usuario en la base de datos.
Llamar al método cambiarEstadoUsuario() con el nuevo estado.
Verificar que el estado del usuario en la base de datos ha cambiado.
Datos de Prueba
Usuario
nombre_ Carlos
apellidos: Mendez
corrreo: cmendez@gmail.com
telefono: 7771324567
contraseña: Password123
estado: Activo
Estado a cambiar:
Nuevo estado: Inactivo
Resultado Esperado :
El estado del usuario se actualiza correctamente a activo.
*/
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
