package mx.edu.utez.sistemaContratos.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CronJobs {

    // Método que se ejecuta cada minuto
    @Scheduled(cron = "0 * * * * ?") // Ejecutar cada minuto
    public void imprimirHola() {
        System.out.println("Hola");
    }

    // Método que se ejecuta cada 5 segundos
    @Scheduled(cron = "*/5 * * * * ?") // Ejecutar cada 5 segundos
    public void imprimirCada5Segundos() {
        System.out.println("Este es un cron job cada 5 segundos");
    }

    // Método que se ejecuta todos los días a las 3 AM
    @Scheduled(cron = "0 0 3 * * ?") // Ejecutar todos los días a las 3 AM
    public void imprimirHolaAM() {
        System.out.println("¡Es la 1:00 AM! Ejecución diaria.");
    }
}
