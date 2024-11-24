package mx.edu.utez.sistemaContratos.usuario.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false) // Clave foránea
    private Usuario usuario;

    @ManyToOne
    private Categoria categoria;
}
