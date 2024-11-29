package mx.edu.utez.sistemaContratos.contrato.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    List<Contrato> findByStatus(boolean status);

    List<Contrato> findAllByStatusIsTrue();
}
