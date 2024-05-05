package src.gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ResultDisplayPanel extends JPanel {
    private List<String> solutions;
    private String endWord;
    private String startWord;
    private long excecutionTime;
    private int nodesVisited;

    public ResultDisplayPanel(List<String> solutions, long excecutionTime ,String startWord, String endWord, int nodesVisited) {
        this.solutions = solutions;
        this.endWord = endWord;
        this.startWord = startWord;
        this.excecutionTime = excecutionTime;
        this.nodesVisited = nodesVisited;
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Create and add the info panel
        JPanel infoPanel = createInfoPanel();
        add(infoPanel, gbc);

        // Create and add the result display
        // Wrap the result panel in a scroll pane
        JPanel resultPanel = createResultPanel();
        JScrollPane scrollPane = new JScrollPane(resultPanel);
        // scrollPane.setPreferredSize(new Dimension(600, 800)); // Adjust size as needed

        // Add the scroll pane to the display panel
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

    }

    private JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.gridwidth = startWord.length();
        int yInit = 0;

        
        JLabel resultTitleLabel = new JLabel("Result");
        gbc.gridx = 0;
        gbc.gridy = yInit;
        infoPanel.add(resultTitleLabel,gbc);

        yInit++;

        JLabel excecutionTimeLabel = new JLabel("Execution Time: " + excecutionTime + " ms");
        gbc.gridx = 0;
        gbc.gridy = yInit;
        infoPanel.add(excecutionTimeLabel,gbc);

        yInit++;

        JLabel numPathLabel = new JLabel("Total length of " + solutions.size() + " paths");
        gbc.gridx = 0;
        gbc.gridy = yInit;
        infoPanel.add(numPathLabel,gbc);

        yInit++;

        JLabel nodesLabel = new JLabel("Visited " + nodesVisited + " number of nodes");
        gbc.gridx = 0;
        gbc.gridy = yInit;
        infoPanel.add(nodesLabel,gbc); 
        return infoPanel;
    }

    private JPanel createResultPanel() {
        JPanel resultPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(2, 2, 2, 2);

        String previousWord = null;
        // Display each character in a box
        for (String word : solutions) {
            
            for (int i = 0; i < word.length(); i++) {
                JLabel label = new JLabel(String.valueOf(word.charAt(i)), SwingConstants.CENTER);
                label.setPreferredSize(new Dimension(50, 50));
                label.setOpaque(true);

                // Set background color based on whether the character matches the corresponding character in the end word
                if (word.length() == endWord.length() && word.charAt(i) == endWord.charAt(i)) {
            
                    label.setBackground(Color.GREEN);
                } else {
                    label.setBackground(Color.RED);
                }

                if (previousWord != null)
                {
                    if (word.charAt(i) != previousWord.charAt(i)) {
                        label.setBorder(BorderFactory.createLineBorder(Color.YELLOW,3));
                    }
    
                }


                gbc.gridx = i;
                resultPanel.add(label, gbc);
            }

            previousWord = word;
            gbc.gridy++;
        }

        return resultPanel;
    }
}
