package com.owainlewis.arch;

import com.owainlewis.arch.lang.Scanner;
import com.owainlewis.arch.lang.Source;
import com.owainlewis.arch.lang.Token;

import java.io.*;

public class Main {

  public static void main(String[] args) throws IOException {
    PushbackReader reader = new PushbackReader(new StringReader("[ 1 ] ;"));

    Source s = new Source(reader);
    Scanner scanner = new Scanner(s);

    for (int i = 0; i<10; i++) {
          Token t = scanner.nextToken();
          if (t != null)
              System.out.printf("Line: %d Col: %d Char: %s\n", s.getLineNumber(), s.getColumnNumber(), t);
      }
    }
}
