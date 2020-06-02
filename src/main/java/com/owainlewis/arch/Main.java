package com.owainlewis.arch;

import com.owainlewis.arch.lang.Scanner;
import com.owainlewis.arch.lang.SourceReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Objects;
import java.util.Optional;

public class Main {

    public static void main(String [] args) throws IOException {

        String input = "123";
        Reader inputString = new StringReader(input);
        BufferedReader reader = new BufferedReader(inputString);
        SourceReader src = new SourceReader(reader);

        Scanner scanner = new Scanner(src);

        System.out.println(scanner.nextToken());
    }
}
