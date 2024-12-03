package mx.edu.utez.sistemaContratos.usuario.control;

import mx.edu.utez.sistemaContratos.cliente.model.ClienteDto;
import mx.edu.utez.sistemaContratos.usuario.model.Usuario;
import mx.edu.utez.sistemaContratos.usuario.model.UsuarioDto;
import mx.edu.utez.sistemaContratos.utils.Message;
import mx.edu.utez.sistemaContratos.utils.TypesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = {"*"},methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/all")
    public ResponseEntity<Message> findAll() {
        return usuarioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> findById(@PathVariable Long id) {
        return usuarioService.findById(id);
    }

    @PostMapping("/save")
    public ResponseEntity<Message> save(@RequestBody UsuarioDto usuarioDto) {
        return usuarioService.save(usuarioDto);
    }

    @PutMapping("/update")
    public ResponseEntity<Message> update(@RequestBody UsuarioDto usuarioDto) {
        return usuarioService.update(usuarioDto);
    }

    @PutMapping("/changeStatus")
    public ResponseEntity<Message> changeStatus(@RequestBody UsuarioDto usuarioDto) {
        return usuarioService.changeStatus(usuarioDto);
    }

    @GetMapping("/actives")
    public ResponseEntity<Message> findActives() {
        return usuarioService.findActives();
    }

}
