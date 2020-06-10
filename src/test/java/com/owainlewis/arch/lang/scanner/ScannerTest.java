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
package com.owainlewis.arch.lang.scanner;

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

    List<Token> scanned = new ArrayList<>();
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

  @Test()
  void testScanInteger() {
      List<Token> expected = new ArrayList<>();
      expected.add(new Token(TokenType.INTEGER, "10", (int) 10, 1, 1));
      expected.add(new Token(TokenType.EOF, "", null, 1, 1));

      shouldExtractTokens("10", expected);
  }

  @Test()
  void testScanFloat() {
      List<Token> expected = new ArrayList<>();
      expected.add(new Token(TokenType.FLOAT, "1.5", 1.5, 1, 2));
      expected.add(new Token(TokenType.EOF, "", null, 1, 2));

      shouldExtractTokens("1.5", expected);
  }

  @Test()
  void testScanIdentifier() {
      List<Token> expected = new ArrayList<>();
      expected.add(new Token(TokenType.IDENTIFIER, "arch", "arch", 1, 3));
      expected.add(new Token(TokenType.EOF, "", null, 1, 3));

      shouldExtractTokens("arch", expected);
  }

    @Test()
    void testScanString() {
        List<Token> expected = new ArrayList<>();
        expected.add(new Token(TokenType.STRING, "arch", "arch", 1, 5));
        expected.add(new Token(TokenType.EOF, "", null, 1, 5));

        shouldExtractTokens("\"arch\"", expected);
    }

    @Test()
    void testScanKeyword() {
        List<Token> expected = new ArrayList<>();
        expected.add(new Token(TokenType.LET, "let", null, 1, 2));
        expected.add(new Token(TokenType.EOF, "", null, 1, 2));

        shouldExtractTokens("let", expected);
    }
}
