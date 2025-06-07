package com.arka.microservice.inventory.domain.config;

import java.util.regex.Pattern;

public class Const {

    //PATTERNS
    public static final Pattern PATTERN_PHONE = Pattern.compile("^[0-9]{6,10}$");
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

}
