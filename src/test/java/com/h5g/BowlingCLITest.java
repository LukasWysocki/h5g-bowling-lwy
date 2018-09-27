package com.h5g;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.stream.IntStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BowlingCLITest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private final InputStream originalIn = System.in;
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;

  @Before
  public void setUp() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @After
  public void tearDown() {
    System.setIn(originalIn);
    System.setOut(originalOut);
    System.setErr(originalErr);
  }

  @Test
  public void testMultipleGameLinesAsArg() throws IOException {
    String gameLine1 = "X|X|X|X|X|X|X|X|X|X||XX";
    int expectedScore1 = 300;
    String gameLine2 = "X|X|X|X|X|X|X|X|X|9/||-";
    int expectedScore2 = 269;

    BowlingCLI.main(gameLine1, gameLine2);

    String err = errContent.toString().trim();
    String out = outContent.toString().trim();

    assertTrue("Unexpected exception: " + err, err.isEmpty());

    String[] split = out.split("\\R");
    assertEquals("Expected two answers", 2, split.length);
    assertEquals("Wrong score for game line: " + gameLine1, expectedScore1, Integer.parseInt(split[0]));
    assertEquals("Wrong score for game line: " + gameLine2, expectedScore2, Integer.parseInt(split[1]));

  }

  @Test
  public void testMissingInput() throws IOException {
    System.setIn(new ByteArrayInputStream(new byte[0]));
    BowlingCLI.main();

    String err = errContent.toString().trim();
    String out = outContent.toString().trim();

    assertTrue("Unexpected output: " + out, out.isEmpty());
    assertThat(err, containsString("Missing input, provide game line"));
  }

  @Test
  public void testLargeInput() throws IOException {
    StringBuilder sb = new StringBuilder();
    IntStream.range(0, 250).forEach(sb::append);
    System.setIn(new ByteArrayInputStream(sb.toString().getBytes()));

    BowlingCLI.main();

    String err = errContent.toString().trim();
    String out = outContent.toString().trim();

    assertTrue("Unexpected output: " + out, out.isEmpty());
    assertThat(err, containsString("Unexpected format for game line"));
  }

}