package mx.edu.utez.sistemaContratos.usuario.control;

import mx.edu.utez.sistemaContratos.usuario.model.Usuario;
import mx.edu.utez.sistemaContratos.utils.Message;
import mx.edu.utez.sistemaContratos.utils.TypesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = {"*"},methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<Message> registrarUsuario(@RequestBody Usuario usuario) {
        System.out.println("Iniciando registro de usuario: " + usuario);

        try {
            Message nuevoUsuario = usuarioService.registrarUsuario(usuario);
            System.out.println("Usuario registrado: " + nuevoUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (IllegalArgumentException e) {
            System.err.println("Error de datos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Message(e.getMessage(), TypesResponse.ERROR));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Message("Error en el servidor", TypesResponse.ERROR));
        }
    }
    @GetMapping
    public ResponseEntity<Message> consultarUsuarios() {
        List<Usuario> usuarios = usuarioService.consultarUsuarios();
        return ResponseEntity.ok(new Message(usuarios, "Usuarios consultados correctamente", TypesResponse.SUCCESS));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> editarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        try {
            Message usuarioEditado = usuarioService.editarUsuario(id, usuarioActualizado);
            return ResponseEntity.ok(usuarioEditado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Message(e.getMessage(), TypesResponse.ERROR));
        }
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Message> cambiarEstado(@PathVariable Long id, @RequestParam boolean status) {
        try {
            Message usuario = usuarioService.cambiarEstado(id, status);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Message(e.getMessage(), TypesResponse.ERROR));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Message> iniciarSesion(@RequestBody Map<String, String> credenciales) {
        try {
            String correo = credenciales.get("correo");
            String contrasena = credenciales.get("contrasena");

            // Llamamos al servicio para iniciar sesión y obtener el token
            Message respuesta = usuarioService.iniciarSesion(correo, contrasena);

            return ResponseEntity.ok(respuesta); // El token está incluido en la respuesta

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new Message(e.getMessage(), TypesResponse.ERROR));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Message> cerrarSesion() {
        // En JWT, simplemente se elimina el token en el lado del cliente
        Message mensaje = usuarioService.cerrarSesion();
        return ResponseEntity.ok(mensaje);
    }
}
