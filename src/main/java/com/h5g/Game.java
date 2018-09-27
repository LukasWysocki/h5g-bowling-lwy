package com.h5g;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>Validates the game description and if valid can calculate the score for given game.</p>
 *
 * <p>The {@code gameLine} describing the game is validated within {@link Game#Game(String)}.
 * <br/>
 * The expected format for {@code gameLine} parameter is "summarize email description here :)"</p>
 */
public class Game {

  private static Pattern GAME_STRING_PATTERN =
      Pattern.compile("^((X|[1-9]-|-[1-9/]|--|[1-9][/1-9])\\|){10}\\|(X[1-9X]?|-[1-9/]|--?|[1-9]-|[1-9][/1-9]?|)$");

  private final String gameLine;
  private final List<Frame> frames;

  /**
   * Initialize the game data.
   *
   * @param gameLine description of game to be scored
   * @throws IllegalArgumentException if  {@code gameLine} is not valid
   */
  // throws just for doc purposes
  public Game(String gameLine) throws IllegalArgumentException {
    this.gameLine = gameLine;

    validateGameLineString();
    frames = parseGameLine();
    validateGameFrames();
    Collections.reverse(frames);
  }

  /**
   * Calculate the game score.
   *
   * @return game score
   */
  public int score() {
    int score = 0;

    int[] nextBalls = new int[]{0, 0};

    // NOTE that at this point collection of frames is reversed (from last to first)
    for (Frame frame : frames) {
      score += frame.score(nextBalls[0], nextBalls[1]);
      nextBalls = frame.updateNextBalls(nextBalls[0]);
    }
    return score;
  }

  // throws just for doc purposes
  private void validateGameLineString() throws IllegalArgumentException {
    // check format
    if (!GAME_STRING_PATTERN.matcher(gameLine).matches()) {
      // includes checks for: # of frames, # of balls in frames (1-2)
      throw new IllegalArgumentException("Unexpected format for game line: " + gameLine);
    }
  }

  // throws just for doc purposes
  private void validateGameFrames() throws IllegalArgumentException {
    assert frames.stream().map(Frame::getType).filter(FrameType.BONUS::equals).count() <= 1;

    // validate # of pins in frame
    FrameType previousFrameType = FrameType.NORMAL;
    int bonusBalls = 0;
    for (Frame frame : frames) {
      if (frame.getType() == FrameType.BONUS) {
        bonusBalls = frame.getNumberOfBalls();
        if (frame.getFirst() < 10 && frame.getFirst() + frame.getSecond() > 10) {
          throw new IllegalArgumentException("Unexpected format, unexpected number of pins hit with bonus balls: " + gameLine);
        }
        break;
      }

      if (frame.getFirst() + frame.getSecond() > 10) {
        throw new IllegalArgumentException("Unexpected format, over 10 pins hit in frame for game line: " + gameLine);
      } else if (frame.getFirst() != 10 && frame.getFirst() + frame.getSecond() == 10 && frame.getType() != FrameType.SPARE) {
        throw new IllegalArgumentException("Unexpected format, 10 pins hit in frame but not marked as spare for game line: " + gameLine);
      }
      previousFrameType = frame.getType();

    }

    // unexpected bonus
    if (previousFrameType == FrameType.NORMAL && bonusBalls != 0) {
      throw new IllegalArgumentException("Unexpected format, bonus balls present while last frame not a strike or spare: " + gameLine);
    }
    // missing bonus
    if (previousFrameType != FrameType.NORMAL && bonusBalls == 0) {
      throw new IllegalArgumentException("Unexpected format, bonus balls missing: " + gameLine);
    }
    // expect 2 bonus ball for STRIKE
    if (previousFrameType == FrameType.STRIKE && bonusBalls != 2) {
      throw new IllegalArgumentException("Unexpected format, last frame was strike, expected 2 bonus balls: " + gameLine);
    }
    // expect 1 bonus balls for SPARE
    if (previousFrameType == FrameType.SPARE && bonusBalls != 1) {
      throw new IllegalArgumentException("Unexpected format, last frame was spare, expected 1 bonus ball: " + gameLine);
    }
  }

  private List<Frame> parseGameLine() {
    String[] split = gameLine.split("\\|\\|");
    String normal = split[0];
    String bonus = split.length > 1 ? split[1] : null;

    split = normal.split("\\|");
    List<Frame> gameFrames = Arrays.asList(split).stream().map(Frame::new).collect(Collectors.toCollection(ArrayList::new));
    if (bonus != null) {
      Frame frame = new Frame(bonus, true);
      gameFrames.add(frame);
    }

    return gameFrames;
  }

  private static class Frame {

    private int numberOfBalls = 1;
    private FrameType type = FrameType.NORMAL;
    private int first;
    private int second;

    Frame(String frame) {
      this(frame, false);
    }

    Frame(String frame, boolean bonus) {
      char[] chars = frame.toCharArray();

      first = interpret(chars[0]);
      if (chars.length > 1) {
        numberOfBalls = 2;
        second = interpret(chars[1]);
        if (type == FrameType.SPARE) {
          second = 10 - first;
        }
      }
      if (bonus) {
        type = FrameType.BONUS;
      }
    }

    int score(int nextBall_1, int nextBall_2) {
      return type.score(this, nextBall_1, nextBall_2);
    }

    int[] updateNextBalls(int nextBall_1) {
      return type.updateNextBalls(this, nextBall_1);
    }

    int getFirst() {
      return first;
    }

    int getSecond() {
      return second;
    }

    FrameType getType() {
      return type;
    }

    int getNumberOfBalls() {
      return numberOfBalls;
    }

    private int interpret(char c) {
      int value = 0;

      switch (c) {
        case 'X':
          value = 10;
          type = FrameType.STRIKE;
          break;
        case '/':
          type = FrameType.SPARE;
          break;
        case '-':
          break;
        default:
          value = Character.getNumericValue(c);
      }
      return value;
    }

  }

  private enum FrameType {
    NORMAL {
      @Override
      int score(Frame frame, int nextBall_1, int nextBall_2) {
        return frame.getFirst() + frame.getSecond();
      }
    },
    STRIKE {
      @Override
      int score(Frame frame, int nextBall_1, int nextBall_2) {
        return frame.getFirst() + frame.getSecond() + nextBall_1 + nextBall_2;
      }

      @Override
      int[] updateNextBalls(Frame previousFrame, int nextBall_1) {
        // if it's a strike there is one ball in frame, keep first ball
        return new int[]{previousFrame.getFirst(), nextBall_1};
      }
    },
    SPARE {
      @Override
      int score(Frame frame, int nextBall_1, int nextBall_2) {
        return frame.getFirst() + frame.getSecond() + nextBall_1;
      }
    },
    BONUS {
      @Override
      int score(Frame frame, int nextBall_1, int nextBall_2) {
        return 0;
      }
    };

    abstract int score(Frame frame, int nextBall_1, int nextBall_2);

    // note reverse order of frames
    int[] updateNextBalls(Frame frame, int nextBall_1) {
      return new int[]{frame.getFirst(), frame.getSecond()};
    }
  }
}


