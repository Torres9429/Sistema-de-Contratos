package mx.edu.utez.sistemaContratos.contrato.control;

import mx.edu.utez.sistemaContratos.categoriaContrato.model.Categoria;
import mx.edu.utez.sistemaContratos.categoriaContrato.model.CategoriaRepository;
import mx.edu.utez.sistemaContratos.cliente.model.Cliente;
import mx.edu.utez.sistemaContratos.cliente.model.ClienteRepository;
import mx.edu.utez.sistemaContratos.contrato.model.Contrato;
import mx.edu.utez.sistemaContratos.contrato.model.ContratoDto;
import mx.edu.utez.sistemaContratos.contrato.model.ContratoRepository;
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

@Service
@Transactional
public class ContratoService {
    private final static Logger logger = LoggerFactory.getLogger(ContratoService.class);
    private final ContratoRepository contratoRepository;
    private final ClienteRepository clienteRepository;
    private final CategoriaRepository categoriaRepository;
    @Autowired
    public ContratoService(ContratoRepository contratoRepository, ClienteRepository clienteRepository, CategoriaRepository categoriaRepository) {
        this.contratoRepository = contratoRepository;
        this.clienteRepository = clienteRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll () {
        List<Contrato> contratos = contratoRepository.findAll();
        logger.info("La búsqueda ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(contratos,"Listado de contratos", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(Long id) {
        Optional<Contrato> contratoOptional = contratoRepository.findById(id);
        if (contratoOptional.isPresent()) {
            Contrato contratoEncontrado = contratoOptional.get();
            return new ResponseEntity<>(new Message(contratoEncontrado, "El contrato ha sido encontrado", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Message("El contrato no existe", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> save(ContratoDto dto) {
        if (dto.getNombre().length() > 30) {
            return new ResponseEntity<>(new Message("El nombre excede el número de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (dto.getDescripcion().length() > 70) {
            return new ResponseEntity<>(new Message("La descripción excede el número de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (dto.getClienteId() == null) {
            return new ResponseEntity<>(new Message("El ID de cliente no puede ser nulo", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (dto.getCategoriaId() == null) {
            return new ResponseEntity<>(new Message("El ID de categoría no puede ser nulo", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Cliente cliente = clienteRepository.findById(dto.getClienteId()).orElse(null);
        if (cliente == null) {
            return new ResponseEntity<>(new Message("Cliente no encontrado", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId()).orElse(null);
        if (categoria == null) {
            return new ResponseEntity<>(new Message("Categoría de contrato no encontrada", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Contrato contrato = new Contrato(dto.getNombre(), dto.getDescripcion(),dto.getFechaVencimiento());
        contrato.setCategorias(categoria);
        contrato.setCliente(cliente);
        contrato = contratoRepository.saveAndFlush(contrato);
        if(contrato == null){
            return new ResponseEntity<>(new Message("El contrato no se registró",TypesResponse.ERROR),HttpStatus.BAD_REQUEST);
        }
        logger.info("El registro ha sido realizado correctamente");
        return new ResponseEntity<>(new Message(contrato,"El contrato se registró correctamente",TypesResponse.SUCCESS),HttpStatus.CREATED);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> update(ContratoDto dto) {
        Optional<Contrato> contratoOptional = contratoRepository.findById(dto.getId());
        if(!contratoOptional.isPresent()){
            return new ResponseEntity<>(new Message("El contrato no existe",TypesResponse.ERROR),HttpStatus.NOT_FOUND);
        }
        if (dto.getNombre().length() > 30) {
            return new ResponseEntity<>(new Message("El nombre excede el número de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (dto.getDescripcion().length() > 70) {
            return new ResponseEntity<>(new Message("La descripción excede el número de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        Contrato contrato = contratoOptional.get();
        contrato.setNombre(dto.getNombre());
        contrato.setDescripcion(dto.getDescripcion());
        contrato.setFechaVencimiento(dto.getFechaVencimiento());
        contrato = contratoRepository.saveAndFlush(contrato);
        if(contrato == null){
            return new ResponseEntity<>(new Message("El contrato no se actualizó",TypesResponse.ERROR),HttpStatus.BAD_REQUEST);
        }
        logger.info("La actualización ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(contrato,"El contrato se actualizó correctamente",TypesResponse.SUCCESS),HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> changeStatus(ContratoDto dto) {
        Optional<Contrato> contratoOptional = contratoRepository.findById(dto.getId());
        if(!contratoOptional.isPresent()){
            return new ResponseEntity<>(new Message("La pregunta no existe",TypesResponse.ERROR),HttpStatus.NOT_FOUND);
        }
        Contrato contrato = contratoOptional.get();
        contrato.setStatus(!contrato.isStatus());
        contrato = contratoRepository.saveAndFlush(contrato);
        if(contrato == null){
            return new ResponseEntity<>(new Message("El estado del contrato no se actualizó",TypesResponse.ERROR),HttpStatus.BAD_REQUEST);
        }
        logger.info("La actualización ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(contrato,"El estado del contrato se actualizó correctamente",TypesResponse.SUCCESS),HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findActives(){
        List<Contrato> respuestas = contratoRepository.findAllByStatusIsTrue();
        logger.info("Lista de contratos activos");
        return new ResponseEntity<>(new Message(respuestas,"Contratos con status activo",TypesResponse.SUCCESS),HttpStatus.OK);
    }
}