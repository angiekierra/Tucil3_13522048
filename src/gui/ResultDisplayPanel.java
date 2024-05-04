package src.gui;


import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ResultDisplayPanel extends JPanel {
    private JTextArea resultTextArea;
    private JPanel infoPanel;
    private JLabel runtimeValueLabel;
    private JLabel visitedValueLabel;
    private JLabel lengthValueLabel;

    public ResultDisplayPanel() {
        setLayout(new BorderLayout());

        
        // Info panel
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(3, 2));
        JLabel runtimeLabel = new JLabel("Runtime:");
        runtimeValueLabel = new JLabel();
        JLabel visitedLabel = new JLabel("Visited Nodes:");
        visitedValueLabel = new JLabel();
        JLabel lengthLabel = new JLabel("Length of String:");
        lengthValueLabel = new JLabel();
        
        infoPanel.add(runtimeLabel);
        infoPanel.add(runtimeValueLabel);
        infoPanel.add(visitedLabel);
        infoPanel.add(visitedValueLabel);
        infoPanel.add(lengthLabel);
        infoPanel.add(lengthValueLabel);
        add(infoPanel, BorderLayout.NORTH);
        
        // Result text area
        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false); 
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        add(scrollPane, BorderLayout.CENTER);
        
    }

    // Method to display the results in the text area
    public void displayResults(List<String> results) {
        StringBuilder sb = new StringBuilder();
        for (String word : results) {
            sb.append(word).append("\n");
        }
        resultTextArea.setText(sb.toString());
    }

    // Method to update the additional information
    public void updateInfo(long runtime, int visitedNodes, int lengthOfString) {
        runtimeValueLabel.setText(String.valueOf(runtime));
        visitedValueLabel.setText(String.valueOf(visitedNodes));
        lengthValueLabel.setText(String.valueOf(lengthOfString));
    }
}
 