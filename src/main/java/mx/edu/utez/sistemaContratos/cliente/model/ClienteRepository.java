package mx.edu.utez.sistemaContratos.cliente.model;

import com.example.integradora_contratos.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Usuario, Long> {
}
