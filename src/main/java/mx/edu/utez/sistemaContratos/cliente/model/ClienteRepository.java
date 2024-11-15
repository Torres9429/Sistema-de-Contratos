package mx.edu.utez.sistemaContratos.cliente.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findAll();

    //List<Cliente> findByStatus(boolean status);

    List<Cliente> findAllByStatusIsTrue();
}
