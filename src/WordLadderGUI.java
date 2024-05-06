package src;

import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import src.algorithms.*;
import src.gui.*;
import src.utils.*;

public class WordLadderGUI extends JFrame {
    private JLabel startLabel, endLabel, algorithmLabel,titleLabel;
    private JTextField startField, endField;
    private JComboBox<String> algorithmComboBox;
    private JButton submitButton;
    private src.utils.Dictionary dictionary;
    public static Font customFont;
    public static String[] colpal = {"#6ad09d","#e27eab","#f9d8e0","#eb954a", "#ffeab4"}; // TURQOISE,  DARK PINK, LIGHT PINK, ORANGE,LIGHT ORANGE

    public WordLadderGUI() {
        // Load mapped dictionary
        dictionary = new src.utils.Dictionary("src/mapped_dictionary.txt");
    
        // Load custom font
        try {
            customFont = loadCustomFont("src/gui/Font.ttf");
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    
        // Setting up main frame
        setTitle("Word Ladder Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        // Setting main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.decode(colpal[4]));
        mainPanel.setFocusable(true);

        // Set border around panel
        Border border = BorderFactory.createLineBorder(Color.decode(colpal[3]), 10);
        mainPanel.setBorder(border);
    
        // Add gridbag layouting
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.CENTER;
    
        int yInit = 0;
        
        // Add gif
        Icon startImg = new ImageIcon("src/gui/img/start.gif");
        JLabel imgLabel = new JLabel(startImg);
        gbc.gridx = 0;
        gbc.gridy = yInit;
        gbc.gridwidth = 2;
        mainPanel.add(imgLabel,gbc);

        yInit++;

        // Add title 
        titleLabel = new JLabel("Word Ladder");
        titleLabel.setFont(customFont.deriveFont(Font.BOLD, 32));
        titleLabel.setForeground(Color.decode(colpal[3]));
        gbc.gridx = 0;
        gbc.gridy = yInit;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);
        
        yInit++;
        gbc.gridwidth = 1;
    
        // Add start input label
        startLabel = new JLabel("Start Word:");
        startLabel.setFont(customFont.deriveFont(Font.PLAIN,14));
        gbc.gridx = 0;
        gbc.gridy = yInit;
        mainPanel.add(startLabel, gbc);
        
        // Add input field
        startField = new RoundedTextField(15,8);
        startField.setFont(customFont.deriveFont(Font.PLAIN,14));
        gbc.gridx = 1;
        gbc.gridy = yInit;
        mainPanel.add(startField, gbc);
        
        yInit++;
        
        // Add end input label
        endLabel = new JLabel("End Word:");
        endLabel.setFont(customFont.deriveFont(Font.PLAIN,14));
        gbc.gridx = 0;
        gbc.gridy = yInit;
        mainPanel.add(endLabel, gbc);
        
        // Add input field
        endField = new RoundedTextField(15,8);
        endField.setFont(customFont.deriveFont(Font.PLAIN,14));
        gbc.gridx = 1;
        gbc.gridy = yInit;
        mainPanel.add(endField, gbc);
    
        yInit++;
        
        // Add algorithm label
        algorithmLabel = new JLabel("Algorithm:");
        algorithmLabel.setFont(customFont.deriveFont(Font.PLAIN,14));
        gbc.gridx = 0;
        gbc.gridy = yInit;
        mainPanel.add(algorithmLabel, gbc);
    
        // Add algortihm picker
        String[] algorithms = {"A*", "UCS", "GBFS"};
        algorithmComboBox = new JComboBox<>(algorithms);
        algorithmComboBox.setFont(customFont.deriveFont(Font.PLAIN,14));
        gbc.gridx = 1;
        gbc.gridy = yInit;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(algorithmComboBox, gbc);
    
        yInit++;
    
        // Add submit button
        submitButton = new RoundedButton("Submit", Color.decode(colpal[1]), Color.decode(colpal[2]), 8);
        submitButton.setFont(customFont.deriveFont(Font.PLAIN,14));
        gbc.gridx = 0;
        gbc.gridy = yInit;
        gbc.gridwidth = 2;
        mainPanel.add(submitButton, gbc);
    
        submitButton.addActionListener(new handleButtonClick());
    
        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    // Method for executing the program
    private class handleButtonClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String startWord = startField.getText().trim().toLowerCase();
            String endWord = endField.getText().trim().toLowerCase();
            
            // Validate the word entries
            if (!isValidInput(startWord) || !isValidInput(endWord)) {
                JOptionPane.showMessageDialog(WordLadderGUI.this, "Please enter a valid input.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // If word's length are not the same
            if (startWord.length() != endWord.length()) {
                JOptionPane.showMessageDialog(WordLadderGUI.this, "Your input words have different lengths. No solution found", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // If both of the words are valid
            if (dictionary.validWord(startWord) && dictionary.validWord(endWord)) {

                String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();

                // Creating algorirtm obejects
                Search search;
                if (selectedAlgorithm.equals("A*")) 
                { 
                    search = new AStar();
                } else if (selectedAlgorithm.equals("UCS")) 
                { 
                    search = new UCS();
                } else 
                {
                    search = new GBFS();
                }
    
                // Getting memory before the search method
                long startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

                // Searching the solution & getting execution time
                long start = System.currentTimeMillis();
                Result result = search.findSolution(startWord, endWord, dictionary);
                long end = System.currentTimeMillis();
    
                long excecutionTime = end - start;
                
                // Extrating Result object
                List<String> solutions = result.getSolution();
                int nodesVisited = result.getNumOfVisitedNodes();
    
                 // Getting memory after search
                long endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                long memoryUsed = endMemory - startMemory;

                // Converting to KB
                long memoryUsedKB = memoryUsed / (1024);

                // Calling Result Display Frame
                showResultPopup(solutions, excecutionTime, startWord, endWord, nodesVisited,memoryUsedKB);

            } else {
                JOptionPane.showMessageDialog(WordLadderGUI.this, "Your startword or endword don't exist in the dictionary. Please input a valid word", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
    
    // Validating input method
    private boolean isValidInput(String input) {
        return !input.isEmpty() && !input.contains(" ");
    }

    // Method for displaying Result Frame
    private void showResultPopup(List<String> solutions, long excecutionTime ,String startWord, String endWord, int nodesVisited, long memory) {
        JFrame popupFrame = new JFrame("Word Ladder Result");
        popupFrame.setLayout(new BorderLayout());

        // Create and add the result display panel
        ResultDisplayPanel resultPanel = new ResultDisplayPanel(solutions,excecutionTime,startWord,endWord,nodesVisited, memory);
        resultPanel.setFont(customFont);
        
        popupFrame.add(resultPanel, BorderLayout.CENTER);

        popupFrame.setSize(750, 1000);
        popupFrame.setLocationRelativeTo(this);
        popupFrame.setVisible(true);
    }

    private Font loadCustomFont(String fontFileName) throws IOException, FontFormatException {
        // Load custom font file
        File fontFile = new File(fontFileName);
        if (!fontFile.exists()) {
            System.err.println("Font file not found: " + fontFileName);
            return null;
        }
    
        // Create font object from file
        Font loadedFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
    
        // Register font with GraphicsEnvironment
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(loadedFont);
    
        return loadedFont;
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WordLadderGUI().setVisible(true);
            }
        });
    }
}
