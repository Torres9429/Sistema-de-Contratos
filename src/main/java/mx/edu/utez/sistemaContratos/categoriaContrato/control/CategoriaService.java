package mx.edu.utez.sistemaContratos.categoriaContrato.control;

import mx.edu.utez.sistemaContratos.categoriaContrato.model.Categoria;
import mx.edu.utez.sistemaContratos.categoriaContrato.model.CategoriaDto;
import mx.edu.utez.sistemaContratos.categoriaContrato.model.CategoriaRepository;
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
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;
    Logger logger = LoggerFactory.getLogger(CategoriaService.class);
    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    //Buscar todas las categorías
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll(){
        List<Categoria> respuestasList = categoriaRepository.findAll();
        logger.info("Lista de categorías encontrada");
        return new ResponseEntity<>(new Message(respuestasList,"Lista encontrada", TypesResponse.SUCCESS), HttpStatus.OK);
    }
    //Buscar las categorías por ID
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            logger.info("Categoría encontrada");
            return new ResponseEntity<>(new Message(categoria.get(), "Categoría encontrada", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            logger.info("Categoría no encontrada");
            return new ResponseEntity<>(new Message(null, "Categoría no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }

    //Buscar categorías activas
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findActives(){
        List<Categoria> respuestas = categoriaRepository.findAllByStatusIsTrue();
        logger.info("Lista de categorias activas");
        return new ResponseEntity<>(new Message(respuestas,"Categorias con status activo",TypesResponse.SUCCESS),HttpStatus.OK);
    }
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> saveCategory(CategoriaDto categoriaDto){
        if (categoriaDto.getNombre().length() > 30) {
            return new ResponseEntity<>(new Message(categoriaDto,"El nombre excede el numero de caracteres",TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (categoriaDto.getDescripcion().length() > 70) {
            return new ResponseEntity<>(new Message(categoriaDto,"La descripcion excede el numero de caracteres",TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        Categoria cat1 = new Categoria(categoriaDto.getNombre(),categoriaDto.getDescripcion());
        cat1 = categoriaRepository.saveAndFlush(cat1);
        if (cat1 == null) {
            return new ResponseEntity<>(new Message(cat1, "No se realizó el registro", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("Registro exitoso");
        return new ResponseEntity<>(new Message(cat1, "Registro exitoso", TypesResponse.SUCCESS), HttpStatus.OK);

    }
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> updateCategory(CategoriaDto categoriaDto){
        Optional<Categoria> preguntasOptional = categoriaRepository.findById(categoriaDto.getId());
        Categoria catToUpdate = preguntasOptional.get();
        if (!preguntasOptional.isPresent()) {
            return new ResponseEntity<>(new Message("La categoria no existe", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        if (categoriaDto.getNombre().length() > 30) {
            return new ResponseEntity<>(new Message(categoriaDto,"El nombre excede el numero de caracteres",TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (categoriaDto.getDescripcion().length() > 70) {
            return new ResponseEntity<>(new Message(categoriaDto,"La descripcion excede el numero de caracteres",TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        catToUpdate.setNombre(categoriaDto.getNombre());
        catToUpdate.setDescripcion(categoriaDto.getDescripcion());

        catToUpdate = categoriaRepository.saveAndFlush(catToUpdate);
        if (catToUpdate.getId() == null) {
            return new ResponseEntity<>(new Message("El registro no se puedo actualizar", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        logger.info("Actualización exitosa");
        return new ResponseEntity<>(new Message("Registro exitoso", TypesResponse.SUCCESS), HttpStatus.OK);
    }
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> changeStatusCategoria(CategoriaDto categoriaDto) {
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(categoriaDto.getId());
        if (!categoriaOptional.isPresent()) {
            return new ResponseEntity<>(new Message("La categoria no existe", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        Categoria catToChange = categoriaOptional.get();
        Categoria catNew = categoriaOptional.get();
        catNew.setStatus(!catToChange.isStatus());
        categoriaRepository.save(catNew);
        logger.info("Registro eliminado exitosamente");
        return new ResponseEntity<>(new Message(categoriaDto, "La respuesta fue desactivada", TypesResponse.SUCCESS), HttpStatus.OK);
    }

}
