package mx.edu.utez.sistemaContratos.contrato.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    List<Contrato> findByStatus(boolean status);
}
