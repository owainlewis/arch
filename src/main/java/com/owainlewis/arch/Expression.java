package com.owainlewis.arch;

// Integer, Float, String, List, Set, Char, Identifier
public abstract class Expression {

    static final class Literal extends Expression {
        private final Object value;

        Literal(Object value) {
            this.value = value;
        }

        public Object getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Literal{" +
                    "value=" + value +
                    '}';
        }
    }

    static final class List extends Expression {
        private final Object value;

        public List(Object value) {
            this.value = value;
        }

        public Object getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "List{" +
                    "value=" + value +
                    '}';
        }
    }
}
