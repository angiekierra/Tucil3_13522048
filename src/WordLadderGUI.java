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
        dictionary = new src.utils.Dictionary("src/dictionary.txt");
    
        try {
            customFont = loadCustomFont("src/gui/Font.ttf");
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    
        setTitle("Word Ladder Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.decode(colpal[4]));

        Border border = BorderFactory.createLineBorder(Color.decode(colpal[3]), 10);
        mainPanel.setBorder(border);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.CENTER;
    
        int yInit = 0;
    
        titleLabel = new JLabel("Word Ladder");
        titleLabel.setFont(customFont.deriveFont(Font.BOLD, 32));
        titleLabel.setForeground(Color.decode(colpal[3]));
        gbc.gridx = 0;
        gbc.gridy = yInit;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);
        
        yInit++;
        gbc.gridwidth = 1;
    
        startLabel = new JLabel("Start Word:");
        startLabel.setFont(customFont.deriveFont(Font.PLAIN,14));
        gbc.gridx = 0;
        gbc.gridy = yInit;
        mainPanel.add(startLabel, gbc);
        
        startField = new RoundedTextField(15,8);
        startField.setFont(customFont.deriveFont(Font.PLAIN,14));
        gbc.gridx = 1;
        gbc.gridy = yInit;
        mainPanel.add(startField, gbc);
        
        yInit++;
        
        endLabel = new JLabel("End Word:");
        endLabel.setFont(customFont.deriveFont(Font.PLAIN,14));
        gbc.gridx = 0;
        gbc.gridy = yInit;
        mainPanel.add(endLabel, gbc);
        
        endField = new RoundedTextField(15,8);
        endField.setFont(customFont.deriveFont(Font.PLAIN,14));
        gbc.gridx = 1;
        gbc.gridy = yInit;
        mainPanel.add(endField, gbc);
    
        yInit++;
    
        algorithmLabel = new JLabel("Algorithm:");
        algorithmLabel.setFont(customFont.deriveFont(Font.PLAIN,14));
        gbc.gridx = 0;
        gbc.gridy = yInit;
        mainPanel.add(algorithmLabel, gbc);
    
        String[] algorithms = {"A*", "UCS", "GBFS"};
        algorithmComboBox = new JComboBox<>(algorithms);
        algorithmComboBox.setFont(customFont.deriveFont(Font.PLAIN,14));
        gbc.gridx = 1;
        gbc.gridy = yInit;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(algorithmComboBox, gbc);
    
        yInit++;
    
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
    

    private class handleButtonClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String startWord = startField.getText().trim().toLowerCase();
            String endWord = endField.getText().trim().toLowerCase();
    
            if (!isValidInput(startWord) || !isValidInput(endWord)) {
                JOptionPane.showMessageDialog(WordLadderGUI.this, "Please enter a valid input.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            if (startWord.length() != endWord.length()) {
                JOptionPane.showMessageDialog(WordLadderGUI.this, "Your input words have different lengths. No solution found", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            if (dictionary.validWord(startWord) && dictionary.validWord(endWord)) {
                String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
    
                Search search;
                if (selectedAlgorithm.equals("A*")) { // Use .equals() for string comparison
                    search = new AStar();
                } else if (selectedAlgorithm.equals("UCS")) { // Use .equals() for string comparison
                    search = new UCS();
                } else {
                    search = new GBFS();
                }
    
                long start = System.currentTimeMillis();
                Result result = search.findSolution(startWord, endWord, dictionary);
                long end = System.currentTimeMillis();
    
                long excecutionTime = end - start;
    
                List<String> solutions = result.getSolution();
                int nodesVisited = result.getNumOfVisitedNodes();
    
                showResultPopup(solutions, excecutionTime, startWord, endWord, nodesVisited);
            } else {
                JOptionPane.showMessageDialog(WordLadderGUI.this, "Your startword or endword don't exist in the dictionary. Please input a valid word", "Input Error", JOptionPane.ERROR_MESSAGE);
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
