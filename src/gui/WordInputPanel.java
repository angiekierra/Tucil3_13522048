package src.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import src.algorithms.*;
import src.utils.*;

public class WordInputPanel extends JPanel {
    private JTextField startWordField;
    private JTextField endWordField;
    private JComboBox<String> algorithmComboBox;
    private JButton calculateButton;
    private ResultDisplayPanelt result;
    private Dictionary dictionary;

    public WordInputPanel() {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));

        inputPanel.add(new JLabel("Start Word:"));
        startWordField = new JTextField();
        inputPanel.add(startWordField);

        inputPanel.add(new JLabel("End Word:"));
        endWordField = new JTextField();
        inputPanel.add(endWordField);

        inputPanel.add(new JLabel("Algorithm:"));
        algorithmComboBox = new JComboBox<>(new String[]{"A*", "UCS", "GBFS"});
        inputPanel.add(algorithmComboBox);

        add(inputPanel, BorderLayout.NORTH);

        // Create a panel for the button to control its size
        JPanel buttonPanel = new JPanel();
        calculateButton = new JButton("Calculate");
        buttonPanel.add(calculateButton);
        buttonPanel.setPreferredSize(new Dimension(300, 50)); // Set the preferred size for the button panel
        add(buttonPanel, BorderLayout.CENTER);

        dictionary = new Dictionary("dictionary.txt");

        
        handleOnClick();
    }

    // Method to initialize ActionListener for calculateButton
    private void handleOnClick() {
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startWord = startWordField.getText().trim().toLowerCase();
                String endWord = endWordField.getText().trim().toLowerCase();

                if (!isValidInput(startWord) || !isValidInput(endWord)) {
                    JOptionPane.showMessageDialog(WordInputPanel.this, "Please enter a valid input.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                
                if (result != null) {
                    remove(result);
                }

                String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
                result = new ResultDisplayPanelt();
                Search test = new AStar();
                List<String> res =  test.findSolution(startWord, endWord,dictionary).getSolution();

                System.out.println("Selected Algorithm: " + selectedAlgorithm);
                System.out.println(res);
                result.displayResults(res);
                result.updateInfo(1, 59, res.size());
                add(result, BorderLayout.SOUTH);
                revalidate();
                repaint();

                System.out.println("Start Word: " + startWord);
                System.out.println("End Word: " + endWord);
            }
        });
    }

    private boolean isValidInput(String input) {
        return !input.isEmpty() && !input.contains(" ");
    }
}
