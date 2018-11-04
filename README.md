# Solution for High5Games Ten-Pin Bowling test assignment

Solves the assigned Ten-Pin bowling game scoring problem as described in email copied in [secton below](#Task-description) .

# Build
Project uses gradle as build tool and requires java 8 as minimal version

Go to project root and execute:

`./gradlew build`

alternatively to build project and generate test coverage report execute:

`./gradlew build jacocoTestReport`

After running this command a runnable jar is present in directory `./build/libs/`.

Optionally test coverage report is present in `./build/reports/jacoco/test/html`.

# Run

## Using program arguments
Assuming that proper java 8 installation application can be run with following command run in `./build/libs/` directory:

`java -jar h5g-bowling-lwy-VERSION.jar GAME_DESCRIPTION_LINE`

where VERSION is project version (e.g. 1.0-SNAPSHOT) and GAME_DESCRIPTION_LINE is text description of game (format as per email) to be scored. Multiple games can be scored by providing multiple arguments. The games score is output one per line (assuming no errors) in order of provided arguments. Any errors are output to standard error.


## Using stdin
Alternatively when no arguments are provided program attempts to read a **single** game description from standard input and outputs the score to standard output (assuming no errors). Any errors are output to standard error.

# Design choices
* First of as performance was not mentioned to be taken into account when scoring the test assignment it was mostly ignored in preference to ( hopefully:) ) simplicity and readability. E.g. Not acting directly on string game description, multiple objects allocations per game line and per frame.

* The program does not produce an exit value as it was not mentioned as a requirement and allows for testing also the main class behaviour.

* Gradle used as it was mentioned it role description, at the moment (current projects) my daily driver is maven.

* Single `Game` source file and nested static classes, mainly due to small problem size, strong internal dependency, no additional value in separation from testing perspective and perceived understandability (of course that one is subjective).

* The game line validation regex... 
>Some people, when confronted with a problem, think 
 “I know, I'll use regular expressions.”   Now they have two problems.

and yet there it is :). Validating the game line format otherwise would be much more pain. 


* Frame format choices: 
  * format for 10 pins hit in bonus (2 balls) can use both spare mark or values that sum to 10 (e.g. both `8/` AND `82` accepted).
   * format for 10 pins hit in frame (not bonus) accepts only a spare mark (e.g. `8/` NOT `82`)

* No fetish around 100% test coverage here... AND pointing out that coverage is not 100% due to usage of assert that would trigger only in case of implementation error :).

# Task description
Raw copy of email description:
```
Just like all review processes, each team member will see the code from different angles.  However, the general review will focus on the following items.

* completeness - Does the solution solve the problem entirely?

* compilation - Does the code compile?

* cleanliness / readability - Can we understand the code?  Is it clear what the code is doing?

* test coverage - Is there sufficient test coverage.  Is the code structured in such a way that it can be tested properly.

* documentation - Is the documentation provided useful and clear.

* does it work? - We execute the solution against our own test of 100k games.

 

The instructions for the test are below.

 

 

===================================

Write a program to score a game of Ten-Pin Bowling.

 

Input: string (described below) representing a bowling game

Ouput: integer score

 

The scoring rules:

 

Each game, or "line" of bowling, includes ten turns,

or "frames" for the bowler.

 

In each frame, the bowler gets up to two tries to

knock down all ten pins.

 

If the first ball in a frame knocks down all ten pins,

this is called a "strike". The frame is over. The score

for the frame is ten plus the total of the pins knocked

down in the next two balls.

 

If the second ball in a frame knocks down all ten pins,

this is called a "spare". The frame is over. The score

for the frame is ten plus the number of pins knocked

down in the next ball.

 

If, after both balls, there is still at least one of the

ten pins standing the score for that frame is simply

the total number of pins knocked down in those two balls.

 

If you get a spare in the last (10th) frame you get one

more bonus ball. If you get a strike in the last (10th)

frame you get two more bonus balls.

These bonus throws are taken as part of the same turn.

If a bonus ball knocks down all the pins, the process

does not repeat. The bonus balls are only used to

calculate the score of the final frame.

 

The game score is the total of all frame scores.

 

Examples:

 

X indicates a strike

/ indicates a spare

- indicates a miss

| indicates a frame boundary

The characters after the || indicate bonus balls

 

X|X|X|X|X|X|X|X|X|X||XX

Ten strikes on the first ball of all ten frames.

Two bonus balls, both strikes.

Score for each frame == 10 + score for next two

balls == 10 + 10 + 10 == 30

Total score == 10 frames x 30 == 300

 

9-|9-|9-|9-|9-|9-|9-|9-|9-|9-||

Nine pins hit on the first ball of all ten frames.

Second ball of each frame misses last remaining pin.

No bonus balls.

Score for each frame == 9

Total score == 10 frames x 9 == 90

 

5/|5/|5/|5/|5/|5/|5/|5/|5/|5/||5

Five pins on the first ball of all ten frames.

Second ball of each frame hits all five remaining

pins, a spare.

One bonus ball, hits five pins.

Score for each frame == 10 + score for next one

ball == 10 + 5 == 15

Total score == 10 frames x 15 == 150

 

X|7/|9-|X|-8|8/|-6|X|X|X||81

Total score == 167
```
