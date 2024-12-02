package mx.edu.utez.sistemaContratos.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;

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
    public Usuario iniciarSesion(String correo, String contrasena) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCorreo().equals(correo) && usuario.getContrasena().equals(contrasena)) {
                if (!usuario.isEstado()) {
                    throw new IllegalArgumentException("El usuario está deshabilitado");
                }
                LinkedTransferQueue<Usuario> usuariosActivos = null;
                usuariosActivos.add(usuario);
                return usuario;
            }
        }
        throw new IllegalArgumentException("Credenciales inválidas");
    }
    public boolean cerrarSesion(Usuario usuario) {
        LinkedTransferQueue<Object> usuariosActivos = null;
        if (!usuariosActivos.contains(usuario)) {
            throw new IllegalStateException("El usuario no ha iniciado sesión");
        }
        usuariosActivos.remove(usuario);
        return true;
    }


    public boolean cambiarEstadoUsuario(Usuario usuario, boolean nuevoEstado) {
        usuario.setEstado(nuevoEstado);
        return true;
    }

}