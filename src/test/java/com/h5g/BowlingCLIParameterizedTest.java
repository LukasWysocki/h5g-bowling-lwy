package com.h5g;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class BowlingCLIParameterizedTest {

  @Parameters(name = "testGameLine: {0}")
  public static Collection<Object[]> data() {
    return TestData.GAME_TEST_DATA;
  }

  @Parameter
  public String gameLine;
  @Parameter(1)
  public int expectedScore;
  @Parameter(2)
  public String exceptionMessage;

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
  public void testGameLineAsArg() throws IOException {
    BowlingCLI.main(gameLine);

    validateGameAnswer();
  }

  @Test
  public void testGameLineAsInput() throws IOException {
    System.setIn(new ByteArrayInputStream(gameLine.getBytes()));
    BowlingCLI.main();

    validateGameAnswer();
  }

  private void validateGameAnswer() {
    String err = errContent.toString().trim();
    String out = outContent.toString().trim();

    if (exceptionMessage.isEmpty()) {
      // expect proper score
      assertTrue("Unexpected exception: " + err, err.isEmpty());
      assertEquals("Wrong score for game line: " + gameLine, expectedScore, Integer.parseInt(out));
    } else {
      // expect exception
      if (err.isEmpty()) {
        fail("Got score but expected exception with message: " + exceptionMessage);
      } else {
        assertThat(err, containsString(exceptionMessage));
      }
    }
  }

}