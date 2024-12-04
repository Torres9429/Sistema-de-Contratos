package mx.edu.utez.sistemaContratos.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UsuarioService {
    private List<Usuario> usuarios = new ArrayList<>();

    public boolean registrarUsuario(Usuario usuario) {
        if (usuario.getCorreo() == null || usuario.getCorreo().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio");
        }
        if (usuario.getNombre() == null || usuario.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        for (Usuario u : usuarios) {
            if (u.getCorreo().equals(usuario.getCorreo())) {
                throw new IllegalArgumentException("Correo duplicado");
            }
        }
        usuarios.add(usuario);
        return true;
    }

    public List<Usuario> consultarUsuarios() {
        return usuarios;
    }

    public boolean editarUsuario(Usuario usuario, String nuevoNombre, String nuevoApellido, String nuevoTelefono) {
        if (nuevoNombre == null || nuevoNombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (nuevoApellido == null || nuevoApellido.isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío");
        }
        if (nuevoTelefono == null || nuevoTelefono.isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío");
        }
        usuario.setNombre(nuevoNombre);
        usuario.setApellido(nuevoApellido);
        usuario.setTelefono(nuevoTelefono);
        return true;
    }

    public boolean cambiarEstadoUsuario(Usuario usuario, boolean nuevoEstado) {
        if (!usuarios.contains(usuario)) {
            throw new IllegalArgumentException("El usuario no está registrado en el sistema.");
        }
        usuario.setEstado(nuevoEstado);
        return true;
    }

    public Usuario consultarPerfilUsuario(String correo) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCorreo().equals(correo)) {
                return usuario;
            }
        }
        throw new IllegalArgumentException("Usuario no encontrado.");
    }

    public boolean editarPerfil(Usuario usuario, String nuevoNombre, String nuevoApellido, String nuevoCorreo, String nuevoTelefono) {
        if (nuevoNombre == null || nuevoNombre.isEmpty() || nuevoApellido == null || nuevoApellido.isEmpty() || nuevoCorreo == null || nuevoCorreo.isEmpty() || nuevoTelefono == null || nuevoTelefono.isEmpty()) {
            throw new IllegalArgumentException("Los datos no pueden estar vacíos");
        }
        usuario.setNombre(nuevoNombre);
        usuario.setApellido(nuevoApellido);
        usuario.setCorreo(nuevoCorreo);
        usuario.setTelefono(nuevoTelefono);
        return true;
    }

    public boolean solicitarCambioContrasena(Usuario usuario) {
        if (usuario == null || usuario.getCorreo() == null || usuario.getCorreo().isEmpty()) {
            throw new IllegalArgumentException("Usuario inválido o correo no proporcionado");
        }

        // Generar un token simple
        String token = generateToken();
        System.out.println("Solicitud de cambio de contraseña enviada a: " + usuario.getCorreo());
        System.out.println("Token de cambio de contraseña: " + token);
        return true;
    }

    private String generateToken() {
        int length = 10;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder token = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            token.append(characters.charAt(random.nextInt(characters.length())));
        }

        return token.toString();
    }


    public boolean cambiarContrasenaPorEnlace(String enlace, String nuevaContrasena) {
        // Lógica para verificar el enlace y actualizar la contraseña
        if (enlace.equals("token:abc123")) {
            // Aquí iría la lógica para cambiar la contraseña del usuario
            // En este caso, simplemente retornamos true si el enlace es válido
            return true;
        }
        return false;  // Si el enlace no es válido
    }


}
