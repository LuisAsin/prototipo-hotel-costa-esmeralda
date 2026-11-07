package pe.edu.idat.costaesmeralda;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Punto de entrada del backend del Hotel Costa Esmeralda.
 * Habilita las tareas programadas (expiración de retenciones) y fija la zona horaria de Lima.
 */
@SpringBootApplication
@EnableScheduling
public class CostaEsmeraldaApplication {

    public static void main(String[] args) {
        // Zona horaria por defecto del sistema (sec. 3): todas las horas son de Lima
        TimeZone.setDefault(TimeZone.getTimeZone("America/Lima"));
        SpringApplication.run(CostaEsmeraldaApplication.class, args);
    }
}
