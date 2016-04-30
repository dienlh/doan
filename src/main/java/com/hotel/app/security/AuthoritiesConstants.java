package com.hotel.app.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String HRM = "ROLE_HRM";
    
    public static final String SERVICES = "ROLE_SERVICES";
    
    public static final String BUSSINESS = "ROLE_BUSSINESS";
    
    public static final String RECEPTIONIST = "ROLE_RECEPTIONIST";
    
    public static final String ACCOUNTING = "ROLE_ACCOUNTING";
    
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {
    }
}
