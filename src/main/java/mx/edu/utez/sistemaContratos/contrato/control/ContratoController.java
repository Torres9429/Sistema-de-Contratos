package mx.edu.utez.sistemaContratos.contrato.control;

import mx.edu.utez.sistemaContratos.categoriaContrato.model.Categoria;
import mx.edu.utez.sistemaContratos.categoriaContrato.model.CategoriaRepository;
import mx.edu.utez.sistemaContratos.cliente.model.Cliente;
import mx.edu.utez.sistemaContratos.cliente.model.ClienteRepository;
import mx.edu.utez.sistemaContratos.contrato.model.ContratoDto;
import mx.edu.utez.sistemaContratos.utils.Message;
import mx.edu.utez.sistemaContratos.utils.TypesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contratos")
@CrossOrigin(origins = {"*"},methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ContratoController {
    private final ContratoService contratoService;
    private ClienteRepository clienteRepository;
    private CategoriaRepository categoriaRepository;

    @Autowired
    public ContratoController(ContratoService contratoService, ClienteRepository clienteRepository, CategoriaRepository categoriaRepository) {
        this.contratoService = contratoService;
        this.clienteRepository = clienteRepository;
        this.categoriaRepository = categoriaRepository;
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
    @GetMapping("/actives")
    public ResponseEntity<Message> findActives() {
        return contratoService.findActives();
    }


    @GetMapping("/clientes")
    public ResponseEntity<Message> getClientes() {
        List<Cliente> clientes = clienteRepository.findAll();  // Asegúrate de que este método devuelva todos los clientes
        return new ResponseEntity<>(new Message(clientes, "Listado de clientes", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @GetMapping("/categorias")
    public ResponseEntity<Message> getCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();  // Asegúrate de que este método devuelva todas las categorías
        return new ResponseEntity<>(new Message(categorias, "Listado de categorías", TypesResponse.SUCCESS), HttpStatus.OK);
    }
}