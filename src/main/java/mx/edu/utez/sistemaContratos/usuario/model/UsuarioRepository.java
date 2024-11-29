package mx.edu.utez.sistemaContratos.usuario.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
    public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
        //<T> ScopedValue<T> findByCorreo(String correo);
        Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByCorreoAndCode(String correo, String code);

    Optional<Usuario> findFirstByCorreoAndCode(String correo, String code);
}
