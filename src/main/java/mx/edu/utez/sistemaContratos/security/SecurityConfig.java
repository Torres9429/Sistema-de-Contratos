package mx.edu.utez.sistemaContratos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors() // Habilita CORS usando el CorsConfigurationSource definido
                .and()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/usuarios/change-password-gral"
                        ).permitAll()
                        .requestMatchers(
                                "/auth/login",
                                "/register",
                                "/usuarios/change-password",
                                "/usuarios/send-email",
                                "/usuarios/verify-code"
                        ).permitAll()
                        .requestMatchers(
                                "/contratos/all",
                                "/clientes/all",
                                "/categoria/all",
                                "/contratos/status",
                                "/usuarios/update",
                                "/usuarios/change-password",
                                "/usuarios/verify-password",
                                "/usuarios/{id}"

                        ).hasAnyAuthority("ROLE_GERENTE_ACCESS")
                        .requestMatchers(
                                "/categoria/*",
                                "/usuarios/*",
                                "/clientes/*",
                                "/contratos/*"
                        ).hasAnyAuthority("ROLE_ADMIN_ACCESS"/*,"ROLE_GERENTE_ACCESS"*/)
                        .anyRequest().authenticated()

                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5500")); // Configura los orígenes permitidos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setExposedHeaders(Arrays.asList("Authorization")); // Permite exponer ciertos headers
        configuration.setAllowCredentials(true); // Permite envío de credenciales como cookies

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
