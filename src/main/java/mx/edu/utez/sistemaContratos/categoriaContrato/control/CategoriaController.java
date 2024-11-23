package mx.edu.utez.sistemaContratos.categoriaContrato.control;

import mx.edu.utez.sistemaContratos.categoriaContrato.model.CategoriaDto;
import mx.edu.utez.sistemaContratos.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categoria")
@CrossOrigin(origins = {"*"},methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class CategoriaController {
    private final CategoriaService categoriaService;
    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }
    @GetMapping("/all")
    public ResponseEntity<Message> findAll(){
        return categoriaService.findAll();
    }
    @GetMapping("/categorias/{id}")
    public ResponseEntity<Message> findById(@PathVariable Long id) {
        return categoriaService.findById(id);
    }
    @GetMapping("/actives")
    public ResponseEntity<Message> findActives(){
        return categoriaService.findActives();
    }
    @PostMapping("/save")
    public ResponseEntity<Message> save(@Validated(CategoriaDto.Register.class) @RequestBody CategoriaDto dto) {
        return categoriaService.saveCategory(dto);
    }

    @PutMapping("/update")
    public ResponseEntity<Message> update(@Validated(CategoriaDto.Modify.class) @RequestBody CategoriaDto dto) {
        return categoriaService.updateCategory(dto);
    }

    @PutMapping("/change-status")
    public ResponseEntity<Message> changeStatus(@Validated(CategoriaDto.ChangeStatus.class) @RequestBody CategoriaDto dto) {
        return categoriaService.changeStatusCategoria(dto);
    }
}
