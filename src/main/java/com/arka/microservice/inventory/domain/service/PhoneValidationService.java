package com.arka.microservice.inventory.domain.service;

import org.springframework.stereotype.Component;

import static com.arka.microservice.inventory.domain.config.Const.PATTERN_PHONE;

@Component
public class PhoneValidationService {
    // el patron de creacion sea entre 6 y 10 digitos
    public boolean isValidPhone(String phone){
        return phone != null && PATTERN_PHONE.matcher(phone).matches();
    }
}
