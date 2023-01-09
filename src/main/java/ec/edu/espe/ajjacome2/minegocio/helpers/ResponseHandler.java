package ec.edu.espe.ajjacome2.minegocio.helpers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase utilitaria para manejar las respuestas de la API
 *
 */
public class ResponseHandler {
    public static ResponseEntity<Object> response(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("data", responseObj);

        return new ResponseEntity<Object>(map,status);
    }

    public static ResponseEntity<Object> response(String message, HttpStatus status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status.value());
        return new ResponseEntity<Object>(map,status);
    }
}
