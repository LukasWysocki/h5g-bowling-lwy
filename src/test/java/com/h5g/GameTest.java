package com.h5g;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class GameTest {

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

  @Test
  public void testGameLine() {
    try {
      Game game = new Game(gameLine);
      int actualScore = game.score();

      if (!exceptionMessage.isEmpty()) {
        fail("Got score but expected exception with message: " + exceptionMessage);
      }

      assertEquals("Wrong score for game line: " + gameLine, expectedScore, actualScore);
    } catch (IllegalArgumentException t) {
      assertFalse("Unexpected exception: " + t, exceptionMessage.isEmpty());
      assertThat(t.getMessage(), containsString(exceptionMessage));
    }
  }
}