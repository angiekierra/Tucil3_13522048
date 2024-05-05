package src.gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import src.WordLadderGUI;

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
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.insets = new Insets(10, 5, 10, 5);

        // Create and add the info panel
        JPanel infoPanel = createInfoPanel();
        add(infoPanel, gbc);

        // Wrap the result panel in a scroll pane
        JPanel resultPanel = createResultPanel();
        JScrollPane scrollPane = new JScrollPane(resultPanel);

        // Add the scroll pane to the display panel
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        this.setBackground(Color.decode(WordLadderGUI.colpal[3]));

    }

    private JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(-1, 4, -1, 4);
        gbc.gridwidth = startWord.length();
        int yInit = 0;

        
        JLabel resultTitleLabel = new JLabel("Result");
        resultTitleLabel.setFont(WordLadderGUI.customFont.deriveFont(Font.BOLD,28));
        resultTitleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = yInit;
        infoPanel.add(resultTitleLabel,gbc);

        yInit++;

        JLabel inputLabel = new JLabel("from " + startWord + " to " + endWord);
        inputLabel.setFont(WordLadderGUI.customFont.deriveFont(Font.BOLD,24));
        inputLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = yInit;
        infoPanel.add(inputLabel,gbc);
        
        yInit++;
        
        JLabel executionTimeLabel = new JLabel("Execution Time: " + excecutionTime + " ms");
        executionTimeLabel.setFont(WordLadderGUI.customFont.deriveFont(Font.PLAIN,18));
        executionTimeLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = yInit;
        infoPanel.add(executionTimeLabel,gbc);

        yInit++;

        JLabel numPathLabel = new JLabel("Total length of " + solutions.size() + " paths");
        numPathLabel.setFont(WordLadderGUI.customFont.deriveFont(Font.PLAIN,18));
        numPathLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = yInit;
        infoPanel.add(numPathLabel,gbc);

        yInit++;

        JLabel nodesLabel = new JLabel("Visited " + nodesVisited + " number of nodes");
        nodesLabel.setFont(WordLadderGUI.customFont.deriveFont(Font.PLAIN,18));
        nodesLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = yInit;
        infoPanel.add(nodesLabel,gbc);
        infoPanel.setBackground(Color.decode(WordLadderGUI.colpal[3]));
        return infoPanel;
    }

    private JPanel createResultPanel() {
        JPanel resultPanel = new JPanel(new GridBagLayout());
        resultPanel.setBackground(Color.WHITE);
        resultPanel.setBorder(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(2, 4, 2, 4);

        if (solutions.size() == 0)
        {
            JLabel noResultLabel = new JLabel("No Result Found");
            noResultLabel.setFont(WordLadderGUI.customFont.deriveFont(Font.ITALIC,30));
            resultPanel.add(noResultLabel);

        }
        else
        {

            String previousWord = null;
            // Display each character in a box
            for (String word : solutions) {
                
                for (int i = 0; i < word.length(); i++) {
                    JLabel label = new RoundedLabel(String.valueOf(word.charAt(i)), SwingConstants.CENTER,8, Color.WHITE);
                    // label.setOpaque(true);
                    
                    // Set background color based on whether the character matches the corresponding character in the end word
                    if (word.length() == endWord.length() && word.charAt(i) == endWord.charAt(i)) {
                        
                        label = new RoundedLabel(String.valueOf(word.charAt(i)), SwingConstants.CENTER,8, Color.decode(WordLadderGUI.colpal[0]));
                        label.setPreferredSize(new Dimension(50, 50));
                    } else {
                        label = new RoundedLabel(String.valueOf(word.charAt(i)), SwingConstants.CENTER,8, Color.decode(WordLadderGUI.colpal[1]));
                        label.setPreferredSize(new Dimension(50, 50));
                    }
    
                    if (previousWord != null)
                    {
                        if (word.charAt(i) != previousWord.charAt(i)) {
                            label.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
                        }
        
                    }
    
    
                    gbc.gridx = i;
                    resultPanel.add(label, gbc);
                }
    
                previousWord = word;
                gbc.gridy++;
            }
        }

        return resultPanel;
    }
}
