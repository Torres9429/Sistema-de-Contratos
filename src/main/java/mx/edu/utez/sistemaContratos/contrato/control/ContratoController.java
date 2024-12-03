package mx.edu.utez.sistemaContratos.contrato.control;

import mx.edu.utez.sistemaContratos.contrato.model.ContratoDto;
import mx.edu.utez.sistemaContratos.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contratos")
public class ContratoController {
    private final ContratoService contratoService;
    @Autowired
    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    @GetMapping("/all")
    public ResponseEntity<Message> getAll() {
        return contratoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getContratoById(@PathVariable Long id) {
        return contratoService.findById(id);
    }

    @PostMapping("/save")
    public ResponseEntity<Message> saveContrato(@Validated(ContratoDto.Register.class) @RequestBody ContratoDto dto) {
        return contratoService.save(dto);
    }

    @PutMapping("/update")
    public ResponseEntity<Message> updateContrato(@Validated(ContratoDto.Modify.class) @RequestBody ContratoDto dto) {
        return contratoService.update(dto);
    }

    @PutMapping("/change-status")
    public ResponseEntity<Message> changeStatus(@Validated(ContratoDto.ChangeStatus.class) @RequestBody ContratoDto dto) {
        return contratoService.changeStatus(dto);
    }

    // Buscar estado activo
    @GetMapping("/status")
    public ResponseEntity<Message> findActives() {
        return contratoService.findActives();
    }
}