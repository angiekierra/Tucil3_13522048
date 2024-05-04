package src.gui;

import java.awt.BorderLayout;


import javax.swing.*;

public class Frame{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame();
        WordInputPanel wordInputPanel = new WordInputPanel();
        
        JScrollPane scrollPane = new JScrollPane(wordInputPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}