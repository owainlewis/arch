package com.owainlewis.arch;

import com.owainlewis.arch.lang.frontend.Scanner;
import com.owainlewis.arch.lang.frontend.Source;
import com.owainlewis.arch.lang.frontend.Token;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.List;

@NoArgsConstructor
public final class ReadEvalPrintLoop {

  private Interpreter interpreter = new Interpreter();

  public void run() throws IOException {
    System.out.println("Starting REPL ...");
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(input);

    while (true) {
      System.out.print("ARCH>> ");
      String line = reader.readLine();
      String exitCommand = ":exit";
      if (line.trim().equals(exitCommand)) {
        break;
      }

      eval(line);
    }
  }

  private void eval(String input) {
    PushbackReader reader = new PushbackReader(new StringReader(input));

    Source s = new Source(reader);
    Scanner scanner = new Scanner(s);

    try {
      List<Token> tokens = scanner.scan();
      for (Token t : tokens) {
        System.out.println(t);
      }

      Parser parser = new Parser(tokens);
      List<Statement> statements = parser.parse();

      for (Statement stmt : statements) {
        // System.out.println(stmt);
      }

      interpreter.interpret(statements);

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
