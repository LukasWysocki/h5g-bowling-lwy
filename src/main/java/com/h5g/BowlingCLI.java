package com.h5g;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * <p>This class can be run from command line to calculate score of a Ten-Pin Bowling game.</p>
 *
 * <p>The game should be described as a text line matching "summarize email description here :)"</p>
 * <br/>
 * <p>The game description text line can be provided:
 * <ul>
 * <li>as an argument (multiple games can be scored by providing multiple arguments)</li>
 * <li>read from standard input (stdin)</li>
 * </ul>
 * The score is written to standard output (stdout), any errors are written to standard error (stderr).
 * </p>
 */
public class BowlingCLI {

  public static void main(String... args) throws IOException {

    // if any args present read write from console
    if (args.length == 0) {
      readEvalStdin();
      return;
    }

    // otherwise assume args are game lines, score and output one per line
    for (String arg : args) {
      try {
        System.out.println(new Game(arg.toUpperCase().trim()).score());
      } catch (IllegalArgumentException e) {
        System.err.println(e.getMessage());
      }
    }

  }

  private static void readEvalStdin() throws IOException {
    char[] cbuf = new char[128]; // 128 - more than proper pattern anyway
    int read = new InputStreamReader(System.in).read(cbuf);

    if (read <= 0) {
      outputErrorAndUsage();
      return;
    }

    try {
      String gameLine = new String(cbuf, 0, read).toUpperCase().trim();
      if (gameLine.isEmpty()) {
        outputErrorAndUsage();
        return;
      }
      System.out.println(new Game(gameLine).score());
    } catch (IllegalArgumentException e) {
      System.err.println(e.getMessage());
    }
  }

  private static void outputErrorAndUsage() {
    System.err.println("Missing input, provide game line... \n" + "usage summarizing email description here :)");
  }
}
