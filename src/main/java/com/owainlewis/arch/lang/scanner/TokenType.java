package com.owainlewis.arch.lang.scanner;

public enum TokenType {
    // Core types
    INTEGER,
    FLOAT,
    STRING,
    IDENTIFIER,

    // Reserved words
    PRIVATE,
    PUBLIC,
    LET,
    MODULE,
    IMPORT,
    AS,

    EQ,
    SEMICOLON,

    // Lists [ ]
    LEFT_BRACKET,
    RIGHT_BRACKET,

    // Sets { }
    LEFT_CURLY,
    RIGHT_CURLY,

    EOF
}
