package com.h5g;

import java.util.Arrays;
import java.util.List;

public class TestData {

  public static final List<Object[]> GAME_TEST_DATA = Arrays.asList(new Object[][]{
      // game string, expected score, message string of expected exception/empty string no exception expected
      {"X|X|X|X|X|X|X|X|X|X||XX", 300, ""},
      {"X|X|X|X|X|X|X|X|X|9/||-", 269, ""},
      {"X|X|X|X|X|X|X|X|X|X||X9", 299, ""},
      {"X|X|X|X|X|X|X|X|X|X||-/", 280, ""},
      {"X|X|X|X|X|X|X|X|X|X||--", 270, ""},
      {"X|X|X|X|X|X|X|X|X|X||81", 287, ""},
      {"X|X|X|X|X|X|X|X|X|X||9/", 289, ""},
      {"X|X|X|X|X|X|X|X|--|X||XX", 240, ""},
      {"X|9-|9-|X|9-|11|9-|9-|9-|--||", 94, ""},
      {"9-|9-|9-|9-|9-|9-|9-|9-|9-|9-||", 90, ""},
      {"5/|5/|5/|5/|5/|5/|5/|5/|5/|5/||5", 150, ""},
      {"-/|-5|5/|5/|5/|5/|5/|5/|5/|5/||5", 135, ""},
      {"X|7/|9-|X|-8|8/|-6|X|X|X||81", 167, ""},
      {"--|--|--|X|-8|8/|-6|X|X|X||81", 119, ""},
      {"--|--|--|X|-8|8/|-6|X|-/|X||81", 101, ""},
      {"--|--|--|X|-8|8/|-6|X|-/|X||-1", 93, ""},
      {"9-|9-|9-|9-|9-|9-|9-|9-|9-|X||-/", 101, ""},
      {"--|--|--|--|--|--|--|--|--|--||", 0, ""},
      {"X|X|X|X|X|X|X|X|X|X||82", 288, ""},
      {"doesNotMatch", -1, "Unexpected format for game line"},
      {"X|9-|9-|28|9-|11|9-|9-|9-|--||", -1, "10 pins hit in frame but not marked as spare"},
      {"X|X|X|X|X|X|X|X|X|82||-", -1, "10 pins hit in frame but not marked as spare"},
      {"X|X|X|X|X|X|X|X|X|X||83", -1, "unexpected number of pins hit with bonus balls"},
      {"9-|9-|9-|9-|9-|9-|9-|9-|9-|9-||X", -1, "bonus balls present while last frame not a strike or spare"}, // unexpected bonus
      {"9-|9-|9-|9-|9-|9-|9-|9-|9-|9-||1", -1, "bonus balls present while last frame not a strike or spare"}, // unexpected bonus
      {"9-|9-|9-|9-|9-|9-|9-|9-|9-|9/||1/", -1, "last frame was spare, expected 1 bonus ball"}, // unexpected bonus x2
      {"9-|9-|9-|9-|9-|9-|9-|9-|9-|9/||-/", -1, "last frame was spare, expected 1 bonus ball"}, // unexpected bonus x2
      {"9-|9-|9-|9-|9-|9-|9-|9-|9-|9-", -1, "Unexpected format for game line"}, // no || separator
      {"9-|9-|9-|9-|9-|9-|9-|9-|9-|9-|9-||", -1, "Unexpected format for game line"}, // to many frames
      {"9-|9-|9-|9-|9-|9-|9-|9-|9-||", -1, "Unexpected format for game line"}, // missing frame
      {"XX|9-|9-|9-|9-|9-|9-|9-|9-|9-||", -1, "Unexpected format for game line"}, // double STRIKE first
      {"9-|9-|9-|XX|9-|9-|9-|9-|9-|9-||", -1, "Unexpected format for game line"}, // double STRIKE middle
      {"9-|9-|9-|9-|9-|9-|9-|9-|9-|XX||", -1, "Unexpected format for game line"}, // double STRIKE last
      {"9-|9-|9-|9-|9-|9-|9-|9-|9-|||", -1, "Unexpected format for game line"}, // empty frame last
      {"9-|9-|9-|9-||9-|9-|9-|9-|9-||", -1, "Unexpected format for game line"}, // empty frame middle
      {"|9-|9-|9-|9-|9-|9-|9-|9-|9-||", -1, "Unexpected format for game line"}, // empty frame first
      {"9-|92|9-|9-|9-|9-|9-|9-|9-|1-||", -1, "over 10 pins hit"}, // frame over 10
      {"9-|1-|9-|9-|9-|9-|9-|9-|9-|X||92", -1, "unexpected number of pins hit with bonus balls"}, // frame over 10
      {"9-/|9-|9-|9-|9-|9-|9-|9-|9-|1-||", -1, "Unexpected format for game line"}, // too long frame first
      {"9-|9-|9-|711|9-|9-|9-|9-|9-|X||XX", -1, "Unexpected format for game line"}, // too long frame middle
      {"9-|9-|9-|9-|9-|9-|9-|9-|123|X||XX", -1, "Unexpected format for game line"}, // too long frame last
      {"-|9-|9-|9-|9-|9-|9-|9-|9-|1-||", -1, "Unexpected format for game line"}, // too short frame miss first
      {"-1|9-|9-|9-|-|9-|9-|9-|9-|1-||", -1, "Unexpected format for game line"}, // too short frame miss middle
      {"-1|9-|9-|9-|9-|9-|9-|9-|9-|-||", -1, "Unexpected format for game line"}, // too short frame miss last
      {"3|9-|9-|9-|9-|9-|9-|9-|9-|1-||", -1, "Unexpected format for game line"}, // too short frame first
      {"-1|9-|9-|9-|3|9-|9-|9-|9-|1-||", -1, "Unexpected format for game line"}, // too short frame middle
      {"-1|9-|9-|9-|9-|9-|9-|9-|9-|3||", -1, "Unexpected format for game line"}, // too short frame last
      {"9`|9-|9-|9-|9-|9-|9-|9-|9-|X||--", -1, "Unexpected format for game line"}, // unexpected character frame first
      {"9-|9-|9-|9-|9a|9-|9-|9-|9-|X||-/", -1, "Unexpected format for game line"}, // unexpected character frame middle
      {"9-|9-|9-|9-|9-|9-|9-|9-|9-|90||-/", -1, "Unexpected format for game line"}, // unexpected character frame last
      {"9-|9-|9-|9-|9-|9-|9-|9-|9+|X||X4", -1, "Unexpected format for game line"}, // unexpected character frame last
      {"9-|9-|-X|9-|9-|9-|9-|9-|9-|X||XX", -1, "Unexpected format for game line"},
      {"9-|9-|9-|9-|9-|9-|9-|9-|9-|X||/", -1, "Unexpected format for game line"},
      {"9-|9-|9-|9-|9-|9-|9-|9-|9-|X||/-", -1, "Unexpected format for game line"},
      {"9-|9-|9-|9-|9-|9-|9-|9-|9-|X||", -1, "bonus balls missing"},
      {"9-|9-|9-|9-|9-|9-|9-|9-|9-|X||-", -1, "last frame was strike, expected 2 bonus balls"},

  });

}
