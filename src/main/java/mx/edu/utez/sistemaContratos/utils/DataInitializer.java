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


            Optional<Role> optionalRole = roleRepository.findByName("ROLE_ADMIN_ACCESS");
            if (!optionalRole.isPresent()) {
                Role roleAdmin = new Role("ROLE_ADMIN_ACCESS");
                roleRepository.saveAndFlush(roleAdmin);

                Optional<Usuario> optionalUsuario = usuarioRepository.findByCorreo("20233tn083@utez.edu.mx");
                if (!optionalUsuario.isPresent()) {
                    Usuario usuarioAdmin = new Usuario("20233tn093@utez.edu.mx", passwordEncoder.encode("20233tn083"));
                    usuarioAdmin.getRoles().add(roleAdmin);
                    usuarioRepository.saveAndFlush(usuarioAdmin);
                }
            }

            optionalRole = roleRepository.findByName("ROLE_GERENTE_ACCESS");
            if (!optionalRole.isPresent()) {
                Role roleGerente = new Role("ROLE_GERENTE_ACCESS");
                roleRepository.saveAndFlush(roleGerente);

                Optional<Usuario> optionalUsuario = usuarioRepository.findByUsername("gerenteUsuario");
                if (!optionalUsuario.isPresent()) {
                    Usuario gerente = new Usuario("gerente", passwordEncoder.encode("password123"));
                    gerente.getRoles().add(roleGerente);
                    usuarioRepository.saveAndFlush(gerente);
                }
            }

        };
    }
}

