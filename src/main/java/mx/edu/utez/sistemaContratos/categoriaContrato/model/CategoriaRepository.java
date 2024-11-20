package mx.edu.utez.sistemaContratos.categoriaContrato.model;

import mx.edu.utez.sistemaContratos.contrato.model.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Contrato> findAllByStatusIsTrue();
}
