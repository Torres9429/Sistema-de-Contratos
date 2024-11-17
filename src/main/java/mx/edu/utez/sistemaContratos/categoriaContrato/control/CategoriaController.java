package mx.edu.utez.sistemaContratos.categoriaContrato.control;

import mx.edu.utez.sistemaContratos.categoriaContrato.model.CategoriaDto;
import mx.edu.utez.sistemaContratos.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    private final CategoriaService categoriaService;
    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }
    @GetMapping("/todas")
    public ResponseEntity<Message> findAll(){
        return categoriaService.findAll();
    }
    @GetMapping("/activas")
    public ResponseEntity<Message> findActives(){
        return categoriaService.findActives();
    }
    @PostMapping("/agregar")
    public ResponseEntity<Message> save(@Validated(CategoriaDto.Register.class) @RequestBody CategoriaDto dto) {
        return categoriaService.saveCategory(dto);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Message> update(@Validated(CategoriaDto.Modify.class) @RequestBody CategoriaDto dto) {
        return categoriaService.updateCategory(dto);
    }

    @PutMapping("/cambiar-status")
    public ResponseEntity<Message> changeStatus(@Validated(CategoriaDto.ChangeStatus.class) @RequestBody CategoriaDto dto) {
        return categoriaService.changeStatusCategoria(dto);
    }
}
