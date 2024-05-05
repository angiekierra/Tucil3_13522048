package src;

import java.util.*;
import java.util.List;

import javax.swing.*;
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
    public static String[] colpal = {"#31636a","#6ad09d","#c8e8df","#e27eab","#f9d8e0","#eb954a", "#f8d87c","#ffeab4"}; // DARK GREEN , TURQOISE, LIGHT GREEN, DARK PINK, LIGHT PINK, ORANGE, YELLOW, LIGHT ORANGE

    public WordLadderGUI() {
        dictionary = new src.utils.Dictionary("src/dictionary.txt");


        try {
            customFont = loadCustomFont("src/gui/Font.ttf");
            System.out.println("success");
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        setTitle("Word Ladder Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        int yInit = 0;
        
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titleLabel = new JLabel("Word Ladder");
        titleLabel.setFont(customFont.deriveFont(Font.BOLD, 28));
        titleLabel.setForeground(Color.decode(colpal[5]));
        titlePanel.add(titleLabel);
    
        gbc.gridx = 0;
        gbc.gridy = yInit;
        gbc.gridwidth = 2;

        add(titlePanel,gbc);
        
        yInit++;
        gbc.gridwidth = 1;

        startLabel = new JLabel("Start Word:");
        gbc.gridx = 0;
        gbc.gridy = yInit;
        startLabel.setFont(customFont.deriveFont(Font.PLAIN,14));
        add(startLabel, gbc);
        
        startField = new RoundedTextField(15,8);
        gbc.gridx = 1;
        gbc.gridy = yInit;
        startField.setFont(customFont.deriveFont(Font.PLAIN,14));
        add(startField, gbc);
        
        yInit++;
        
        endLabel = new JLabel("End Word:");
        gbc.gridx = 0;
        gbc.gridy = yInit;
        endLabel.setFont(customFont.deriveFont(Font.PLAIN,14));
        add(endLabel, gbc);
        
        endField = new RoundedTextField(15,8);
        gbc.gridx = 1;
        gbc.gridy = yInit;
        endField.setFont(customFont.deriveFont(Font.PLAIN,14));
        add(endField, gbc);

        yInit++;

        algorithmLabel = new JLabel("Algorithm:");
        gbc.gridx = 0;
        gbc.gridy = yInit;
        algorithmLabel.setFont(customFont.deriveFont(Font.PLAIN,14));
        add(algorithmLabel, gbc);

        String[] algorithms = {"A*", "UCS", "GBFS"};
        algorithmComboBox = new JComboBox<>(algorithms);
        gbc.gridx = 1;
        gbc.gridy = yInit;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        algorithmComboBox.setFont(customFont.deriveFont(Font.PLAIN,14));
        add(algorithmComboBox, gbc);

        yInit++;

        submitButton = new RoundedButton("Submit", Color.BLUE, Color.RED,8);;
        gbc.gridx = 0;
        gbc.gridy = yInit;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        submitButton.setFont(customFont.deriveFont(Font.PLAIN,14));
        add(submitButton, gbc);
    
        this.setBackground(Color.decode(colpal[7]));
        submitButton.addActionListener(new handleButtonClick());
    }

    private class handleButtonClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String startWord = startField.getText().trim().toLowerCase();
            String endWord = endField.getText().trim().toLowerCase();

            if (!isValidInput(startWord) || !isValidInput(endWord)) {
                JOptionPane.showMessageDialog(WordLadderGUI.this, "Please enter a valid input.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (startWord.length() != endWord.length())
            {
                JOptionPane.showMessageDialog(WordLadderGUI.this, "Your input words have different lengths. No solution found", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (dictionary.validWord(startWord) && dictionary.validWord(endWord))
            {
                String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
    
    
                Search search;
                if (selectedAlgorithm == "A*")
                {
                    search = new AStar();
                } 
                else if (selectedAlgorithm == "UCS")
                {
                    search = new UCS();
                }
                else
                {
                    search = new GBFS();
                }
    
                long start = System.currentTimeMillis();
                Result result = search.findSolution(startWord, endWord, dictionary);
                long end = System.currentTimeMillis();
    
                long excecutionTime = end - start;
    
                List<String> solutions = result.getSolution();
                int nodesVisited = result.getNumOfVisitedNodes();
    
    
                showResultPopup(solutions,excecutionTime,startWord,endWord,nodesVisited);
            }
            else
            {
                JOptionPane.showMessageDialog(WordLadderGUI.this, "Your startword or endword don't exist in the ditionary. Please input a valid word", "Input Error", JOptionPane.ERROR_MESSAGE);
                return; 
            }

        }
    }

    private boolean isValidInput(String input) {
        return !input.isEmpty() && !input.contains(" ");
    }

    private void showResultPopup(List<String> solutions, long excecutionTime ,String startWord, String endWord, int nodesVisited) {
        JFrame popupFrame = new JFrame("Word Ladder Result");
        popupFrame.setLayout(new BorderLayout());

        // Create and add the result display panel
        ResultDisplayPanel resultPanel = new ResultDisplayPanel(solutions,excecutionTime,startWord,endWord,nodesVisited);
        resultPanel.setFont(customFont);
        
        popupFrame.add(resultPanel, BorderLayout.CENTER);

        popupFrame.setSize(700, 800);
        popupFrame.setLocationRelativeTo(this);
        popupFrame.setVisible(true);
    }

    private Font loadCustomFont(String fontFileName) throws IOException, FontFormatException {
        // Load custom font file
        File fontFile = new File(fontFileName);
        System.out.println("Attempting to load font from: " + fontFile.getAbsolutePath()); // Print absolute path
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WordLadderGUI().setVisible(true);
            }
        });
    }
}
