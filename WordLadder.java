import java.util.*;

public class WordLadder {
    private int algorithmID;
    private String startWord;
    private String endWord;
    long excecutionTime;
    Dictionary dictionary;
    List<String> solution;

    public WordLadder()
    {
        Scanner scanner = new Scanner(System.in);
        this.algorithmID = getValidAlgorithm(scanner);
        this.startWord = getValidWord(scanner, "Enter startword: ");
        this.endWord = getValidWord(scanner, "Enter endword: ");
        scanner.close();
        this.excecutionTime = 0;
        this.dictionary = new Dictionary("dictionary.txt");
        this.solution = new ArrayList<>();
    }

    public void printInfo()
    {

        System.out.println(algorithmID);
        System.out.println(startWord);
        System.out.println(endWord);
        System.out.println(excecutionTime);
        System.out.println(solution);
    }

    private int getValidAlgorithm(Scanner scanner)
    {
        int option;
        while (true) {
            System.out.println("Choose algorithm (1: A*, 2: UCS, 3: GBFS): ");
            if (scanner.hasNextInt()) {
                option= scanner.nextInt();
                if (option>= 1 && option<= 3) {
                    break; // Break the loop if a valid choice is entered
                }
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
            } else {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
                scanner.next(); // Clear the invalid input
            }
        }
        return option;
    }

    private String getValidWord(Scanner scanner, String prompt)
    {
        String word;
    while (true) {
        System.out.print(prompt);
        word = scanner.next();
        if (word.matches("[a-zA-Z]+") && (scanner.hasNextLine() && scanner.nextLine().trim().isEmpty())) {
            break; // Break the loop if a valid single word is entered and there are no remaining tokens
        }
        System.out.println("Invalid input. Please enter a valid single word (only letters).");
    }
    return word;
    }

    public void run()
    {   
        if (dictionary.validWord(startWord) && dictionary.validWord(endWord))
        {
            if (startWord.length() != endWord.length())
            {
                System.out.println("Your starword and endword length dont match");
                return;
            }

            Search search;
            if (algorithmID == 1)
            {
                search = new AStar();
            } 
            else if (algorithmID == 2)
            {
                search = new UCS();
            }
            else if (algorithmID == 3)
            {
                search = new GBFS();
            }
            else {
                System.out.println("Invalid algorithm ID");
                return;
            }

            long start = System.currentTimeMillis();
            solution = search.findSolution(startWord, endWord, dictionary);
            long end = System.currentTimeMillis();

            this.excecutionTime = end - start;
            

        }
        else
        {
            
            System.out.println("Your input is not in the dictionary");
            return;
        }
    }

    // public static void main(String[] args)
    // {
    //     WordLadder game = new WordLadder();
    //     game.run();
    //     game.printInfo();
    // }


}
