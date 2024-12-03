package mx.edu.utez.sistemaContratos.usuario.control;
import mx.edu.utez.sistemaContratos.cliente.model.Cliente;
import mx.edu.utez.sistemaContratos.usuario.model.Usuario;
import mx.edu.utez.sistemaContratos.usuario.model.UsuarioDto;
import mx.edu.utez.sistemaContratos.usuario.model.Usuario;
import mx.edu.utez.sistemaContratos.usuario.model.UsuarioDto;
import mx.edu.utez.sistemaContratos.usuario.model.UsuarioRepository;
import mx.edu.utez.sistemaContratos.utils.Message;
import mx.edu.utez.sistemaContratos.utils.TypesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        logger.info("La búsqueda de todos los usuarios ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(usuarios, "Listado de usuarios", TypesResponse.SUCCESS), HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario foundCliente = usuarioOptional.get();
            return new ResponseEntity<>(new Message(foundCliente, "Usuario encontrado", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Message("El usuario no existe", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> save(UsuarioDto dto) {
        // Validación de longitudes
        if (dto.getNombre().length() > 60) {
            return new ResponseEntity<>(new Message("El nombre excede el número de caracteres permitidos", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (dto.getApellidos().length() > 70) {
            return new ResponseEntity<>(new Message("Los apellidos exceden el número de caracteres permitidos", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (dto.getCorreo().length() > 100) {
            return new ResponseEntity<>(new Message("El correo excede el número de caracteres permitidos", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (dto.getTelefono().length() > 10) {
            return new ResponseEntity<>(new Message("El teléfono excede el número de caracteres permitidos", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        // Guardar el usuario
        Usuario usuario = new Usuario(dto.getNombre(), dto.getApellidos(), dto.getCorreo(), dto.getTelefono(), dto.getContrasena(),true);
        usuario = usuarioRepository.saveAndFlush(usuario);
        if (usuario == null) {
            return new ResponseEntity<>(new Message("El usuario no se registró", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("El usuario se registró correctamente");
        return new ResponseEntity<>(new Message(usuario.getCorreo(), "Usuario registrado correctamente", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> update(UsuarioDto dto) {
        // Validación de longitudes
        if (dto.getNombre().length() > 50) {
            return new ResponseEntity<>(new Message("El nombre excede el número de caracteres permitidos", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (dto.getApellidos().length() > 100) {
            return new ResponseEntity<>(new Message("Los apellidos exceden el número de caracteres permitidos", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (dto.getCorreo().length() > 150) {
            return new ResponseEntity<>(new Message("El correo excede el número de caracteres permitidos", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (dto.getTelefono().length() > 15) {
            return new ResponseEntity<>(new Message("El teléfono excede el número de caracteres permitidos", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(dto.getId());
        if (!usuarioOptional.isPresent()) {
            return new ResponseEntity<>(new Message("El usuario no existe", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        Usuario usuario = usuarioOptional.get();
        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        usuario = usuarioRepository.saveAndFlush(usuario);
        if (usuario == null) {
            return new ResponseEntity<>(new Message("El usuario no se actualizó", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("La actualización del usuario ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(usuario.getNombre(), "Usuario actualizado correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> changeStatus(UsuarioDto dto) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(dto.getId());
        if (!usuarioOptional.isPresent()) {
            return new ResponseEntity<>(new Message("El usuario no existe", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        Usuario usuario = usuarioOptional.get();
        usuario.setStatus(!usuario.isStatus());
        usuario = usuarioRepository.saveAndFlush(usuario);
        if (usuario == null) {
            return new ResponseEntity<>(new Message("El estado del usuario no se actualizó", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("El estado del usuario se actualizó correctamente");
        return new ResponseEntity<>(new Message(usuario, "Estado del usuario actualizado correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findActives(){
        List<Usuario> respuestas = usuarioRepository.findAllByStatusIsTrue();
        logger.info("Lista de usuarios activos");
        return new ResponseEntity<>(new Message(respuestas, "Usuarios con status activo", TypesResponse.SUCCESS), HttpStatus.OK);
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
        // En JWT, simplemente se elimina el token en el lado del usuario
        return new Message("Sesión cerrada correctamente", TypesResponse.SUCCESS);
    }
}

