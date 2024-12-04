package mx.edu.utez.sistemaContratos.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioService {
    private List<Usuario> usuarios = new ArrayList<>();

    public boolean registrarUsuario(Usuario usuario) {
        if (usuario.getCorreo() == null || usuario.getCorreo().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio");
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

    public boolean editarUsuario(Usuario usuario, String nuevoNombre) {
        if (nuevoNombre == null || nuevoNombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        usuario.setNombre(nuevoNombre);
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

}