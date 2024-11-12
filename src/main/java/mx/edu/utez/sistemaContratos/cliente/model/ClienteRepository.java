package mx.edu.utez.sistemaContratos.cliente.model;

import mx.edu.utez.sistemaContratos.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Usuario, Long> {
}
