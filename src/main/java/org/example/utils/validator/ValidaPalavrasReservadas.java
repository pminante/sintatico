package org.example.utils.validator;

import java.util.ArrayList;
import java.util.Arrays;

public class ValidaPalavrasReservadas {
     static ArrayList<String> palavrasReservadas = new ArrayList<String>(
            Arrays.asList(
                    "and", "array", "begin", "case", "const", "div",
                    "do", "downto", "else", "end", "file", "for",
                    "function", "goto", "if", "in", "label", "mod",
                    "nil", "not", "of", "or", "packed", "procedure",
                    "program", "record", "repeat", "set", "then",
                    "to", "type", "until", "var", "while", "with",
                    "read", "write", "real", "integer"));


    public static boolean execute(String palavra){
        return palavrasReservadas.contains(palavra.toLowerCase());
    }
}
