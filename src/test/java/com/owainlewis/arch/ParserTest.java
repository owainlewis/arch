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

import com.owainlewis.arch.scanner.Scanner;
import com.owainlewis.arch.scanner.Source;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ParserTest {

  private Parser makeParser(String input) throws IOException {
    PushbackReader reader = new PushbackReader(new StringReader(input));
    Scanner s = new Scanner(new Source(reader));

    return new Parser(s.scan());
  }

  private Expression intExpr(int value) {
    return new Expression.Literal(Expression.Type.Integer, value);
  }

  private Expression stringExpr(String value) {
    return new Expression.Literal(Expression.Type.String, value);
  }

  @Test
  void testParseLetStatement() throws IOException {
    Parser parser = makeParser("let x = 10 ;");
    List<Statement> statements = parser.parse();

    ArrayList<Expression> expectedBody = new ArrayList<>();
    expectedBody.add(new Expression.Literal(Expression.Type.Integer, 10));
    Statement expected = new Statement.LetStmt("x", expectedBody);

    Assertions.assertEquals(Collections.singletonList(expected), statements);
  }

  @Test
  void testParseListLiteral() throws IOException {
    Parser parser = makeParser("[1 2 3]");
    List<Statement> statements = parser.parse();

    List<Expression> innerForms = Arrays.asList(intExpr(1), intExpr(2), intExpr(3));
    Statement expected = new Statement.ExpressionStmt(new Expression.ListExpr(innerForms));

    Assertions.assertEquals(new ArrayList<>(Collections.singletonList(expected)), statements);
  }

  @Test
  void testParseStringLiteral() throws IOException {
    Parser parser = makeParser("\"Hello, World!\"");
    List<Statement> statements = parser.parse();

    Statement expected = new Statement.ExpressionStmt(stringExpr("Hello, World!"));

    Assertions.assertEquals(new ArrayList<>(Collections.singletonList(expected)), statements);
  }
}
