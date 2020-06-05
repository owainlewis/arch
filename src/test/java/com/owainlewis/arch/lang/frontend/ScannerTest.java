package com.owainlewis.arch.lang.frontend;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class ScannerTest {

  private void shouldExtractTokens(String source, TokenType... tokens) {
    PushbackReader reader = new PushbackReader(new StringReader(source));
    Source s = new Source(reader);
    Scanner scanner = new Scanner(s);

    List<Token> scanned = new ArrayList<Token>();
    try {
      scanned = scanner.scan();
    } catch (IOException e) {
      e.printStackTrace();
    }

    for (Token t : scanned) {
      System.out.println(t);
    }
  }

  @Test()
  void testScanNumber() {
    shouldExtractTokens("0 5 10");
  }
}
