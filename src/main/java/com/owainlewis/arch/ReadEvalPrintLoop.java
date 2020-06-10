/*
 * Copyright © 2020 Owain Lewis <owain@owainlewis.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.owainlewis.arch;

import com.owainlewis.arch.lang.scanner.Scanner;
import com.owainlewis.arch.lang.scanner.Source;
import com.owainlewis.arch.lang.scanner.Token;
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
        System.out.println("Token " + t);
      }

      Parser parser = new Parser(tokens);
      List<Statement> statements = parser.parse();

      for (Statement stmt : statements) {
        System.out.println("Statement" + stmt);
      }

    //  interpreter.interpret(statements);

    } catch (Exception e) {
      System.out.println("Caught Exception while processing: " + e.getMessage());
    }
  }
}
