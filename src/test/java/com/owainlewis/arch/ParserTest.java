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
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.util.List;

public class ParserTest {

    private Parser makeParser(String input) throws IOException {
        PushbackReader reader = new PushbackReader(new StringReader(input));
        Scanner s = new Scanner(new Source(reader));

        return new Parser(s.scan());
    }

    @Test
    void testParseLetStatement() throws IOException {
        Parser parser = makeParser("let x = 10 ;");
        List<Statement> statements = parser.parse();
        System.out.println(statements);

    }

    @Test()
    void testParseLiteral() {

    }
}
