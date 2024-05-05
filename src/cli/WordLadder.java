package src.cli;

import java.util.*;

import src.algorithms.AStar;
import src.algorithms.GBFS;
import src.algorithms.Search;
import src.algorithms.UCS;
import src.utils.Dictionary;
import src.utils.Result;

public class WordLadder {
    private int algorithmID;
    private String startWord;
    private String endWord;
    private long excecutionTime;
    private Dictionary dictionary;
    private Result result;

    public WordLadder()
    {
        Scanner scanner = new Scanner(System.in);
        this.algorithmID = getValidAlgorithm(scanner);
        this.startWord = getValidWord(scanner, "Enter startword: ");
        this.endWord = getValidWord(scanner, "Enter endword: ");
        scanner.close();

        this.excecutionTime = 0;
        this.dictionary = new Dictionary("../dictionary.txt");
        this.result = new Result(new ArrayList<>(),0);
    }

    public void printInfo()
    {

        System.out.println(algorithmID);
        System.out.println(startWord);
        System.out.println(endWord);
        System.out.println(excecutionTime);
        System.out.println(result.getNumOfVisitedNodes());
        System.out.println(result.getSolution());
        System.out.println(result.getSolution().size());
    }

    public List<String> getSolution()
    {
        return this.result.getSolution();
    }

    public int getNumOfVisitedNodes()
    {
        return this.result.getNumOfVisitedNodes();
    }


    public long getExecutionTime()
    {
        return this.excecutionTime;
    }

    private int getValidAlgorithm(Scanner scanner)
    {
        int option;
        while (true) {
            System.out.println("Choose algorithm (1: A*, 2: UCS, 3: GBFS): ");
            if (scanner.hasNextInt()) {
                option= scanner.nextInt();
                if (option>= 1 && option<= 3) {
                    break;
                }
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
            } else {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
                scanner.next(); 
            }
        }
        return option;
    }

    private String getValidWord(Scanner scanner, String prompt)
    {
        String word;
    while (true) {
        System.out.print(prompt);
        word = scanner.next().toLowerCase();
        if (word.matches("[a-zA-Z]+") && (scanner.hasNextLine() && scanner.nextLine().trim().isEmpty())) {
            break;
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
            result = search.findSolution(startWord, endWord, dictionary);
            long end = System.currentTimeMillis();

            this.excecutionTime = end - start;
            

        }
        else
        { 
            System.out.println("Your input is not in the dictionary");
            return;
        }
    }



}
