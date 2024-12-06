package mx.edu.utez.sistemaContratos.utils;


import mx.edu.utez.sistemaContratos.role.model.Role;
import mx.edu.utez.sistemaContratos.role.model.RoleRepository;
import mx.edu.utez.sistemaContratos.usuario.model.Usuario;
import mx.edu.utez.sistemaContratos.usuario.model.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {


            Optional<Role> optionalRole = roleRepository.findByNombre("ROLE_ADMIN_ACCESS");
            if (!optionalRole.isPresent()) {
                Role roleAdmin = new Role("ROLE_ADMIN_ACCESS");
                roleRepository.saveAndFlush(roleAdmin);

                Optional<Usuario> optionalUser = usuarioRepository.findFirstByCorreo("20233tn083@utez.edu.mx");
                if (!optionalUser.isPresent()) {
                    //Usuario usuarioAdmin = new Usuario("20233tn083@utez.edu.mx", passwordEncoder.encode("12345"));
                    Usuario usuarioAdmin = new Usuario();
                    usuarioAdmin.setApellidos("Ramirez");
                    usuarioAdmin.setNombre("Juana");
                    usuarioAdmin.setTelefono("1234567890");
                    usuarioAdmin.setCorreo("20233tn083@utez.edu.mx");
                    usuarioAdmin.setContrasena(passwordEncoder.encode("12345"));
                    usuarioAdmin.getRoles().add(roleAdmin);
                    usuarioRepository.saveAndFlush(usuarioAdmin);
                }
            }

            optionalRole = roleRepository.findByNombre("ROLE_GERENTE_ACCESS");
            if (!optionalRole.isPresent()) {
                Role roleGerente = new Role("ROLE_GERENTE_ACCESS");
                roleRepository.saveAndFlush(roleGerente);

                Optional<Usuario> optionalUser = usuarioRepository.findFirstByCorreo("20233tn093@utez.edu.mx");
                if (!optionalUser.isPresent()) {
                   Usuario userGerente = new Usuario();
                    // Usuario userGerente = new Usuario("20233tn093@utez.edu.mx", passwordEncoder.encode("12345"));
                    userGerente.setApellidos("Sanchez");
                    userGerente.setNombre("Ramona");
                    userGerente.setTelefono("1234567890");
                    userGerente.setCorreo("20233tn093@utez.edu.mx");
                    userGerente.setContrasena(passwordEncoder.encode("12345"));
                    userGerente.getRoles().add(roleGerente);
                    usuarioRepository.saveAndFlush(userGerente);
                }
            }

        };
    }
}
