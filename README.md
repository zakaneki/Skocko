# Skočko Combination Guesser

---

## Overview
This repository contains a Java-based command-line application that attempts to guess the secret combination in the game Skočko, a popular game from the Serbian game show Slagalica. Skočko is similar to the classic board game Mastermind, where the goal is to deduce a hidden sequence of four symbols.

This program implements a strategy to guess the user's chosen combination by iteratively eliminating possibilities based on the user's feedback.

## How to Play Skočko (with this program)
In this version, **you** think of a secret combination of four symbols, and the program will try to guess it.

1.  The available symbols are: "Skocko", "Tref", "Pik", "Srce", "Karo", "Zvezda".
2.  The program will make a guess.
3.  You will provide feedback to the program in the format `x y`, where:
    *   `x` is the number of symbols in the program's guess that are correct and in the correct position.
    *   `y` is the number of symbols in the program's guess that are correct but in the wrong position.
4.  The program will use your feedback to narrow down the possible combinations and make another guess.
5.  This continues until the program guesses your combination or determines it's impossible based on your feedback.

## Implementation Details
The Skočko Combination Guesser is written in Java and can be found in [`src/slagalica/Test.java`](src/slagalica/Test.java).

*   **Game Logic**: The program manages the generation of all possible combinations (1296 for 6 symbols and 4 positions) and iteratively refines this list.
*   **Solver Algorithm**: The core guessing strategy is implemented in the `pick` method within [`slagalica.Test.java`](src/slagalica/Test.java). It attempts to choose a guess that, in the worst-case scenario (based on all possible user responses), eliminates the maximum number of remaining possibilities. This approach is inspired by strategies like Donald Knuth's five-guess algorithm for Mastermind.
*   **User Interface**: Interaction is handled via the command line. The program prints its guesses, and the user inputs feedback.

## How to Run
1.  Compile the Java code:
    ```sh
    javac src/slagalica/Test.java -d bin
    ```
2.  Run the compiled class:
    ```sh
    java -cp bin slagalica.Test
    ```
3.  Follow the on-screen prompts to play the game.

## Contributing
Contributions to enhance this project are welcome! If you have ideas for improving the guessing algorithm, optimizing the code, or adding new features, feel free to submit pull requests or open issues.

## Acknowledgments
This project is inspired by the Serbian game show Slagalica and its game Skočko. Thanks to the developers of Mastermind solver algorithms whose work provides a foundation for such guessing games.

---

Feel free to explore the Skočko Combination Guesser. If you have any questions or suggestions, please don't hesitate to get in touch. Happy guessing!