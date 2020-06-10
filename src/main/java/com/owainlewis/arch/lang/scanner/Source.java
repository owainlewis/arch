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

import java.io.IOException;
import java.io.PushbackReader;
import java.util.Optional;
import java.util.function.Predicate;

public final class Source {

  private int lineNumber = 1;

  private int columnNumber = -1;

  private final PushbackReader reader;

  private boolean isEOF = false;

  public Source(PushbackReader reader) {
    this.reader = reader;
  }

  public char nextChar() throws IOException {
    final int i = reader.read();
    final char c = (char) i;

    // We are EOF so just return -1
    if (i == -1) {
      isEOF = true;
      return c;
    }

    columnNumber++;

    if (c == '\n') {
      lineNumber++;
      columnNumber = -1;
    }
    return (char) i;
  }

  public Optional<Character> peekChar() throws IOException {
    final int i = reader.read();
    // Be careful not to push back an EOF since char is unsigned and will cause a 65535 to be
    // returned next time
    if (i != -1) reader.unread(i);
    return (i == -1) ? Optional.empty() : Optional.of((char) i);
  }

  public boolean nextCharIs(char c) throws IOException {
    return peekChar().map(ch -> ch == c).orElse(false);
  }

  public boolean nextCharSatisfies(Predicate<Character> predicate) throws IOException {
    return peekChar().map(predicate::test).orElse(false);
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public int getColumnNumber() {
    return columnNumber;
  }

  public boolean isEOF() {
    return isEOF;
  }
}
