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
    private long memoryUsed;
    private int nodesVisited;

    public ResultDisplayPanel(List<String> solutions, long excecutionTime ,String startWord, String endWord, int nodesVisited, long memory) {
        this.solutions = solutions;
        this.endWord = endWord;
        this.startWord = startWord;
        this.excecutionTime = excecutionTime;
        this.memoryUsed = memory;
        this.nodesVisited = nodesVisited;

        // Set grid bag layouting
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

        // Set bg color
        this.setBackground(Color.decode(WordLadderGUI.colpal[3]));

    }

    // Method for creating info panel
    private JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(-1, 4, -1, 4);
        gbc.gridwidth = startWord.length();
        int yInit = 0;

        // Add result title
        JLabel resultTitleLabel = new JLabel("Result");
        resultTitleLabel.setFont(WordLadderGUI.customFont.deriveFont(Font.BOLD,28));
        resultTitleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = yInit;
        infoPanel.add(resultTitleLabel,gbc);

        yInit++;

        // Add starting and end word title
        JLabel inputLabel = new JLabel("from " + startWord + " to " + endWord);
        inputLabel.setFont(WordLadderGUI.customFont.deriveFont(Font.BOLD,24));
        inputLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = yInit;
        infoPanel.add(inputLabel,gbc);
        
        yInit++;
        
        // Add gif only if solution is found
        if (solutions.size() != 0)
        {

            Icon foungImg = new ImageIcon("src/gui/img/found.gif");
            JLabel imgLabel = new JLabel(foungImg);
            gbc.gridx = 0;
            gbc.gridy = yInit;
            infoPanel.add(imgLabel,gbc);
            
            yInit++;
        }

        // Add label for execution time
        JLabel executionTimeLabel = new JLabel("Execution Time: " + excecutionTime + " ms");
        executionTimeLabel.setFont(WordLadderGUI.customFont.deriveFont(Font.PLAIN,18));
        executionTimeLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = yInit;
        infoPanel.add(executionTimeLabel,gbc);

        yInit++;

        // Add lable for length of path
        JLabel numPathLabel = new JLabel("Total length of " + solutions.size() + " paths");
        numPathLabel.setFont(WordLadderGUI.customFont.deriveFont(Font.PLAIN,18));
        numPathLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = yInit;
        infoPanel.add(numPathLabel,gbc);

        yInit++;

        // Add lable for number of visited nodes
        JLabel nodesLabel = new JLabel("Visited " + nodesVisited + " number of nodes");
        nodesLabel.setFont(WordLadderGUI.customFont.deriveFont(Font.PLAIN,18));
        nodesLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = yInit;
        infoPanel.add(nodesLabel,gbc);
        
        yInit++;

        // Add label for memory usage
        JLabel memoryLabel = new JLabel("Used total of " + memoryUsed + " kb memory");
        memoryLabel.setFont(WordLadderGUI.customFont.deriveFont(Font.ITALIC,14));
        memoryLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = yInit;
        infoPanel.add(memoryLabel,gbc);

        // Set panel bg
        infoPanel.setBackground(Color.decode(WordLadderGUI.colpal[3]));
        return infoPanel;
    }

    // Method for creating result panel
    private JPanel createResultPanel() {
        JPanel resultPanel = new JPanel(new GridBagLayout());
        resultPanel.setBackground(Color.WHITE);
        resultPanel.setBorder(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(2, 4, 2, 4);

        // If no solution
        if (solutions.size() == 0)
        {
            // Add no solution label
            JLabel noResultLabel = new JLabel("No Result Found");
            noResultLabel.setFont(WordLadderGUI.customFont.deriveFont(Font.ITALIC,30));
            resultPanel.add(noResultLabel,gbc);

            // Add pikachu gif
            Icon failImg = new ImageIcon("src/gui/img/notFound.gif");
            JLabel imgLabel = new JLabel(failImg);
            gbc.gridy = 1;
            resultPanel.add(imgLabel,gbc);

        }
        else
        {
            // Store prev word
            String previousWord = null;

            // Display each character in a box
            for (String word : solutions) {
                
                for (int i = 0; i < word.length(); i++) {
                    // Create a label for each character
                    JLabel label = new RoundedLabel(String.valueOf(word.charAt(i)), SwingConstants.CENTER,8, Color.WHITE);
                    
                    
                    // Set background color based on whether the character matches the character in the end word
                    if (word.length() == endWord.length() && word.charAt(i) == endWord.charAt(i)) {
                        
                        label = new RoundedLabel(String.valueOf(word.charAt(i)), SwingConstants.CENTER,8, Color.decode(WordLadderGUI.colpal[0]));
                        label.setPreferredSize(new Dimension(50, 50));
                    } else {
                        label = new RoundedLabel(String.valueOf(word.charAt(i)), SwingConstants.CENTER,8, Color.decode(WordLadderGUI.colpal[1]));
                        label.setPreferredSize(new Dimension(50, 50));
                    }
                    
                    // Set a border to mark the character changes
                    if (previousWord != null)
                    {
                        if (word.charAt(i) != previousWord.charAt(i)) {
                            label.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
                        }
        
                    }
    
                    // Move the layouting accordingly
                    gbc.gridx = i;
                    resultPanel.add(label, gbc);
                }
                
                // Move into next row and storing prev word
                previousWord = word;
                gbc.gridy++;
            }
        }

        return resultPanel;
    }
}
