package mx.edu.utez.sistemaContratos.cliente.control;

import mx.edu.utez.sistemaContratos.cliente.model.Cliente;
import mx.edu.utez.sistemaContratos.cliente.model.ClienteDto;
import mx.edu.utez.sistemaContratos.cliente.model.ClienteRepository;
import mx.edu.utez.sistemaContratos.utils.Message;
import mx.edu.utez.sistemaContratos.utils.TypesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ClienteService {
    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);
    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll() {
        List<Cliente> clientes = clienteRepository.findAll();
        logger.info("La búsqueda de todos los clientes ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(clientes, "Listado de clientes", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isPresent()) {
            Cliente foundCliente = clienteOptional.get();
            return new ResponseEntity<>(new Message(foundCliente, "Cliente encontrado", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Message("El cliente no existe", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> save(ClienteDto dto) {
        // Validación de longitudes
        if (dto.getNombre().length() > 50) {
            return new ResponseEntity<>(new Message("El nombre excede el número de caracteres permitidos", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (dto.getApellidos().length() > 100) {
            return new ResponseEntity<>(new Message("Los apellidos exceden el número de caracteres permitidos", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (dto.getCorreo().length() > 150) {
            return new ResponseEntity<>(new Message("El correo excede el número de caracteres permitidos", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (dto.getTelefono().length() > 15) {
            return new ResponseEntity<>(new Message("El teléfono excede el número de caracteres permitidos", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        // Guardar el cliente
        Cliente cliente = new Cliente(dto.getNombre(), dto.getApellidos(), dto.getCorreo(), dto.getTelefono(), true);
        cliente = clienteRepository.saveAndFlush(cliente);
        if (cliente == null) {
            return new ResponseEntity<>(new Message("El cliente no se registró", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("El cliente se registró correctamente");
        return new ResponseEntity<>(new Message(cliente, "Cliente registrado correctamente", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> update(ClienteDto dto) {
        // Validación de longitudes
        if (dto.getNombre().length() > 50) {
            return new ResponseEntity<>(new Message("El nombre excede el número de caracteres permitidos", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (dto.getApellidos().length() > 100) {
            return new ResponseEntity<>(new Message("Los apellidos exceden el número de caracteres permitidos", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (dto.getCorreo().length() > 150) {
            return new ResponseEntity<>(new Message("El correo excede el número de caracteres permitidos", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (dto.getTelefono().length() > 15) {
            return new ResponseEntity<>(new Message("El teléfono excede el número de caracteres permitidos", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Optional<Cliente> clienteOptional = clienteRepository.findById(dto.getId());
        if (!clienteOptional.isPresent()) {
            return new ResponseEntity<>(new Message("El cliente no existe", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        Cliente cliente = clienteOptional.get();
        cliente.setNombre(dto.getNombre());
        cliente.setApellidos(dto.getApellidos());
        cliente.setCorreo(dto.getCorreo());
        cliente.setTelefono(dto.getTelefono());
        cliente = clienteRepository.saveAndFlush(cliente);
        if (cliente == null) {
            return new ResponseEntity<>(new Message("El cliente no se actualizó", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("La actualización del cliente ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(cliente, "Cliente actualizado correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> changeStatus(ClienteDto dto) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(dto.getId());
        if (!clienteOptional.isPresent()) {
            return new ResponseEntity<>(new Message("El cliente no existe", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        Cliente cliente = clienteOptional.get();
        cliente.setStatus(!cliente.isStatus());
        cliente = clienteRepository.saveAndFlush(cliente);
        if (cliente == null) {
            return new ResponseEntity<>(new Message("El estado del cliente no se actualizó", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("El estado del cliente se actualizó correctamente");
        return new ResponseEntity<>(new Message(cliente, "Estado del cliente actualizado correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findActives(){
        List<Cliente> respuestas = clienteRepository.findAllByStatusIsTrue();
        logger.info("Lista de clientes activos");
        return new ResponseEntity<>(new Message(respuestas, "Clientes con status activo", TypesResponse.SUCCESS), HttpStatus.OK);
    }
}
