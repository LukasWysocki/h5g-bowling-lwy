# Solution for High5Games Ten-Pin Bowling test assignment

Solves the assigned Ten-Pin bowling game scoring problem as described in email.

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