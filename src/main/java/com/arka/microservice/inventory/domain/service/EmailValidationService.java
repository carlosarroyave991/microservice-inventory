package com.arka.microservice.inventory.domain.service;

import org.springframework.stereotype.Component;

import static com.arka.microservice.inventory.domain.config.Const.EMAIL_PATTERN;

@Component
public class EmailValidationService {
    // El patrón definido requiere:
    // - Que la parte local (antes del '@') tenga los caracteres permitidos: letras, números y los caracteres '+', '_', '.', '-'.
    // - Que esté presente el '@' como separador.
    // - Que la parte del dominio (después del '@') contenga letras, números, puntos o guiones hasta el final.

    public boolean isValidEmail(String email){
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
}
