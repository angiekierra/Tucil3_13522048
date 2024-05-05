package src.gui;

import java.util.*;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.algorithms.AStar;
import src.algorithms.GBFS;
import src.algorithms.Search;
import src.algorithms.UCS;
import src.utils.*;

public class WordLadderGUI extends JFrame {
    private JLabel startLabel, endLabel, algorithmLabel,titleLabel;
    private JTextField startField, endField;
    private JComboBox<String> algorithmComboBox;
    private JButton submitButton;
    private src.utils.Dictionary dictionary;

    public WordLadderGUI() {
        dictionary = new src.utils.Dictionary("dictionary.txt");

        setTitle("Word Ladder Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        int yInit = 0;
        
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titleLabel = new JLabel("Word Ladder");
        titlePanel.add(titleLabel);
        gbc.gridx = 0;
        gbc.gridy = yInit;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Spanning two columns
        gbc.anchor = GridBagConstraints.CENTER; // Center alignment

        add(titlePanel,gbc);
        
        yInit++;
        
        startLabel = new JLabel("Start Word:");
        gbc.gridx = 0;
        gbc.gridy = yInit;
        add(startLabel, gbc);
        
        startField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = yInit;
        add(startField, gbc);
        
        yInit++;
        
        endLabel = new JLabel("End Word:");
        gbc.gridx = 0;
        gbc.gridy = yInit;
        add(endLabel, gbc);
        
        endField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = yInit;
        add(endField, gbc);

        yInit++;

        algorithmLabel = new JLabel("Algorithm:");
        gbc.gridx = 0;
        gbc.gridy = yInit;
        add(algorithmLabel, gbc);

        String[] algorithms = {"A*", "UCS", "GBFS"};
        algorithmComboBox = new JComboBox<>(algorithms);
        gbc.gridx = 1;
        gbc.gridy = yInit;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(algorithmComboBox, gbc);

        yInit++;

        submitButton = new JButton("Submit");
        gbc.gridx = 0;
        gbc.gridy = yInit;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        add(submitButton, gbc);

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



            System.out.println("Start Word: " + startWord);
            System.out.println("End Word: " + endWord);
            System.out.println("Selected Algorithm: " + selectedAlgorithm);


            showResultPopup(solutions,excecutionTime,startWord,endWord,nodesVisited);
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
        
        popupFrame.add(resultPanel, BorderLayout.CENTER);

        popupFrame.setSize(400, 400);
        popupFrame.setLocationRelativeTo(this);
        popupFrame.setVisible(true);
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
