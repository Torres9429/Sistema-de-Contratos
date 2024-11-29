package mx.edu.utez.sistemaContratos.cliente.control;

import mx.edu.utez.sistemaContratos.cliente.model.ClienteDto;
import mx.edu.utez.sistemaContratos.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = {"*"},methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/all")
    public ResponseEntity<Message> findAll() {
        return clienteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> findById(@PathVariable Long id) {
        return clienteService.findById(id);
    }

    @PostMapping("/save")
    public ResponseEntity<Message> save(@RequestBody ClienteDto clienteDto) {
        return clienteService.save(clienteDto);
    }

    @PutMapping("/update")
    public ResponseEntity<Message> update(@RequestBody ClienteDto clienteDto) {
        return clienteService.update(clienteDto);
    }

    @PutMapping("/changeStatus")
    public ResponseEntity<Message> changeStatus(@RequestBody ClienteDto clienteDto) {
        return clienteService.changeStatus(clienteDto);
    }

    @GetMapping("/actives")
    public ResponseEntity<Message> findActives() {
        return clienteService.findActives();
    }
}
