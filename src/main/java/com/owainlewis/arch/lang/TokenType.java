package com.owainlewis.arch.lang;

public enum TokenType {
    // Core types
    INTEGER,
    FLOAT,
    STRING,
    ATOM,

    // Reserved words
    PRIVATE,
    PUBLIC,
    LET,
    MODULE,
    IMPORT,
    AS,

    EQ,
    SEMICOLON,

    // Other tokens
    LEFT_BRACKET,
    RIGHT_BRACKET,
    EOF
}
