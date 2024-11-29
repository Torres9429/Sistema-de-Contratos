package mx.edu.utez.sistemaContratos.cliente.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findAll();

    //List<Cliente> findByStatus(boolean status);

    List<Cliente> findAllByStatusIsTrue();
}
