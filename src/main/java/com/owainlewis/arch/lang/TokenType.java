package com.owainlewis.arch.lang;

public enum TokenType {
    // Core types
    INTEGER,
    FLOAT,
    STRING,
    CHAR,
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
    LEFT_PAREN,
    RIGHT_PAREN,
    LEFT_BRACKET,
    RIGHT_BRACKET,
    EOF
}
