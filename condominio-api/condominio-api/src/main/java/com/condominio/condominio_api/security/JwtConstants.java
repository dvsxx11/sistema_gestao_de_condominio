package com.condominio.condominio_api.security;

public class JwtConstants {
    public static final String SECRET = "suaChaveSecretaMuitoForteParaJWT2025CondominioAPI";
    public static final long EXPIRATION_TIME = 86400000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}