package mx.edu.utez.sistemaContratos.usuario.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //<T> ScopedValue<T> findByCorreo(String correo);

    List<Usuario> findAllByStatusIsTrue();

    Optional<Object> findByCorreo(String correo);
}
