package mx.edu.utez.sistemaContratos.Categoria;
import java.util.ArrayList;
import java.util.List;

public class CategoriaService {
    private List<Categoria> categorias = new ArrayList<>();

    public boolean registrarCategoria(Categoria categoria) {
        if (categoria.getNombre() == null || categoria.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría es obligatorio");
        }
        for (Categoria c : categorias) {
            if (c.getNombre().equals(categoria.getNombre())) {
                throw new IllegalArgumentException("La categoría ya existe");
            }
        }
        categorias.add(categoria);
        return true;
    }

    public List<Categoria> consultarCategorias() {
        return categorias;
    }

    public boolean cambiarEstadoCategoria(Categoria categoria, boolean nuevoEstado) {
        categoria.setEstado(nuevoEstado);
        return true;
    }

}
