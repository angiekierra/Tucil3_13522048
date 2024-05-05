package src;

import src.cli.WordLadder;

public class WordLadderCLI {
    public static void main(String[] args)
    {
        WordLadder game = new WordLadder();
        game.run();
        game.printInfo();
    }
}
