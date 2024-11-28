package mx.edu.utez.sistemaContratos.usuario.control;
import mx.edu.utez.sistemaContratos.usuario.model.Usuario;
import mx.edu.utez.sistemaContratos.usuario.model.UsuarioRepository;
import mx.edu.utez.sistemaContratos.utils.Message;
import mx.edu.utez.sistemaContratos.utils.TypesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Message registrarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getContrasena() == null) {
            throw new IllegalArgumentException("El usuario o la contraseña no pueden ser nulos");
        }

        usuario.setStatus(true); // Habilitar usuario por defecto
        usuarioRepository.save(usuario);
        return new Message(usuario, "Usuario registrado correctamente", TypesResponse.SUCCESS);
    }

    public List<Usuario> consultarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Message editarUsuario(Long id, Usuario usuarioActualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));

        usuarioExistente.setNombre(usuarioActualizado.getNombre());
        usuarioExistente.setApellidos(usuarioActualizado.getApellidos());
        usuarioExistente.setCorreo(usuarioActualizado.getCorreo());
        usuarioExistente.setContrasena(usuarioActualizado.getContrasena());
        usuarioExistente.setStatus(usuarioActualizado.isStatus());

        usuarioRepository.save(usuarioExistente);

        return new Message("Usuario actualizado correctamente", TypesResponse.SUCCESS);
    }

    public Message cambiarEstado(Long id, boolean status) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));

        usuario.setStatus(status);
        usuarioRepository.save(usuario);
        return new Message(usuario, "Estado actualizado correctamente", TypesResponse.SUCCESS);
    }

    public Message iniciarSesion(String correo, String contrasena) {
        // Verificar si el usuario existe
        Usuario usuario = (Usuario) usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new IllegalArgumentException("Credenciales incorrectas"));

        // Validar la contraseña
        if (!contrasena.equals(usuario.getContrasena())) {
            throw new IllegalArgumentException("Credenciales incorrectas");
        }

        return new Message("Inicio de sesión exitoso", TypesResponse.SUCCESS); // Devolver el token
    }

    public Message cerrarSesion() {
        // En JWT, simplemente se elimina el token en el lado del cliente
        return new Message("Sesión cerrada correctamente", TypesResponse.SUCCESS);
    }
}

