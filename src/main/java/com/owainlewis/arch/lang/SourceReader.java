package com.owainlewis.arch.lang;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

public final class SourceReader {

  private int lineNumber = 0;

  private int columnNumber = 0;

  private String currentLine = "";

  private final BufferedReader reader;

  public SourceReader(BufferedReader reader) {
    this.reader = reader;
  }

  public Optional<Character> nextChar() {
    columnNumber++;
    if (columnNumber >= currentLine.length()) {
      // Read the next line
      try {
        currentLine = reader.readLine();
        lineNumber++;
      } catch (IOException e) {
        return Optional.empty();
      }

      // If the next line is null we have reached the end of file
      if (currentLine == null) {
        return Optional.empty();
      }

      columnNumber = 0;
    }

    char c = currentLine.charAt(columnNumber);

    return Optional.of(c);
  }

  public Optional<Character> peekChar() {
    int nextPos = columnNumber + 1;
    return nextPos < currentLine.length()
            ? Optional.of(currentLine.charAt(nextPos))
            : Optional.of('\n');
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public int getColumnNumber() {
    return columnNumber;
  }
}
