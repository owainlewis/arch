package com.owainlewis.arch;

import com.owainlewis.arch.lang.frontend.Scanner;
import com.owainlewis.arch.lang.frontend.Source;
import com.owainlewis.arch.lang.frontend.Token;

import java.io.*;

public class Main {

  public static void main(String[] args) throws IOException {
    PushbackReader reader = new PushbackReader(new StringReader("-10"));

    Source s = new Source(reader);
    Scanner scanner = new Scanner(s);

    for (int i = 0; i<10; i++) {
          Token t = scanner.nextToken();
          if (t != null)
              System.out.printf("Line: %d Col: %d Char: %s\n", s.getLineNumber(), s.getColumnNumber(), t);
      }
    }
}
