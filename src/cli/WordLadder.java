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
    private long memoryUsed;
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
        this.dictionary = new Dictionary("src/mapped_dictionary.txt");
        this.result = new Result(new ArrayList<>(),0);
        this.memoryUsed = 0;
    }

    public void printInfo()
    {
        String algorithm;
        switch (algorithmID) {
            case 1:
                algorithm = "A*";
                break;
            case 2:
                algorithm = "UCS";
                break;
            case 3:
                algorithm = "GBFS";
                break;
            default:
                algorithm = "NULL";

        }

        System.out.println("Choosen Algoritm: " + algorithm);
        System.out.println("Start word: " + startWord);
        System.out.println("End word: " + endWord);
        System.out.println("Execution time: " + excecutionTime + " ms");
        System.out.println("Num of visited nodes: " + result.getNumOfVisitedNodes());
        System.out.println("Solution: " + result.getSolution());
        System.out.println("Number of paths: " + result.getSolution().size());
        System.out.println("Memory used: " + memoryUsed + " kb");
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
            System.out.println("Choose algorithm: ");
            System.out.println("1. A* (A Star Algorithm)");
            System.out.println("2. UCS (Uniform Cost Search Alogrithm)");
            System.out.println("3. GBFS (Greedy Best First Search Algorithm)");
            System.out.print("Insert number: ");
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
                System.out.println("Your starword and endword lengths don't match");
                return;
            }

            Search search;
            switch (algorithmID) {
                case 1:
                    search = new AStar();
                    break;
                case 2:
                    search = new UCS();
                    break;
                case 3:
                    search = new GBFS();
                    break;
                default:
                    System.out.println("Invalid algorithm ID");
                    return;
            }

            // Getting memory before the search method
            long startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Gettinig execution time
            long start = System.currentTimeMillis();
            result = search.findSolution(startWord, endWord, dictionary);
            long end = System.currentTimeMillis();

            this.excecutionTime = end - start;
            
             // Getting memory after search
            long endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            long memoryUsed = endMemory - startMemory;

            // Converting to KB
            this.memoryUsed = memoryUsed / (1024);

        }
        else
        { 
            System.out.println("Your input is not in the dictionary");
            return;
        }
    }



}
