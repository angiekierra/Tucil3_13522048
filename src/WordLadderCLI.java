package src;

import src.cli.WordLadder;

public class WordLadderCLI {
    public static void main(String[] args)
    {
        // Making Word Ladder Object
        WordLadder game = new WordLadder();

        // Running the game
        game.run();

        // Print results
        game.printInfo();
    }
}
