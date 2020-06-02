package com.owainlewis.arch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class ReadEvalPrintLoop {

  public ReadEvalPrintLoop() {}

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

  private void eval(String code) {

  }
}
