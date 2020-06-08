package com.owainlewis.arch.lang.frontend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class ScannerTest {

  private Scanner makeScanner(String input) {
      PushbackReader reader = new PushbackReader(new StringReader(input));
      return new Scanner(new Source(reader));
  }

  private void shouldExtractTokens(String source, List<Token> expected) {
    Scanner scanner = makeScanner(source);

    List<Token> scanned = new ArrayList<Token>();
    try {
      scanned = scanner.scan();
    } catch (IOException e) {
      e.printStackTrace();
    }

      Assertions.assertEquals(expected, scanned);
  }

  private void scanFixture(String path) {
      


  }

  @Test()
  void testIsKeyword() {
      Scanner s = makeScanner("");
      Assertions.assertTrue(s.isKeyword("private"));
      Assertions.assertTrue(s.isKeyword("public"));
      Assertions.assertTrue(s.isKeyword("module"));
      Assertions.assertTrue(s.isKeyword("import"));
      Assertions.assertTrue(s.isKeyword("as"));
      Assertions.assertTrue(s.isKeyword("let"));
  }

  void testScanInteger() {

  }

  void testScanFloat() {

  }

  void testScanIdentifier() {
      List<Token> expected = new ArrayList<>();
      expected.add(new Token(TokenType.IDENTIFIER, "foo", "foo", 1, 0));
      expected.add(new Token(TokenType.EOF, "", null, 1, 0));

      shouldExtractTokens("foo", expected);
  }

  @Test()
  void testScanNumber() {
    List<Token> expected = new ArrayList<>();
    expected.add(new Token(TokenType.INTEGER, "0", 0, 1, 0));
    expected.add(new Token(TokenType.EOF, "", null, 1, 0));

    shouldExtractTokens("0", expected);
  }
}
