package com.company;
/*
Topic Name: Trie Data Structure
Problem Statement: Using Trie data structures, find the longest prefix match in the router for fastest IP lookup

Group No - 3
Group Members:
    1) Sanket Salunke   - 224070
    2) Nilesh Kapse     - 224071
    3) Om Balsane       - 224072
 */

// Importing Packages
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Class
public class Main {
    // Frame Object
    JFrame welcomeFrame;

    // Main class Constructor
    public Main() {

        // Initialize Frame Object
        welcomeFrame = new JFrame("Longest Prefix Matching");
        // Configuring Frame
        welcomeFrame.setLayout(null);
        welcomeFrame.setLocation(300, 50);
        welcomeFrame.setResizable(false);
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.setSize(800, 600);
        welcomeFrame.setBackground(Color.WHITE);

        // Accessing image and set it to label
        ImageIcon icon = new ImageIcon("src/Longest_Prefix_Matching_Image.png");
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(icon);

        // Accessing image and set it to button
        ImageIcon icon2 = new ImageIcon("src/Longest_Prefix_Matching_Image_Button.png");
        JButton getStartedButton = new JButton();
        getStartedButton.setIcon(icon2);
        getStartedButton.setBorder(null);

        // Adding action listener to button
        getStartedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Routing_GUI gui = new Routing_GUI();
                welcomeFrame.setVisible(false);
            }
        });

        // Setting Positions to Widgets
        imageLabel.setBounds(0, 0, 800, 565);
        getStartedButton.setBounds(273, 400, 250, 75);

        // Adding Widgets to Frame
        welcomeFrame.add(imageLabel);
        welcomeFrame.add(getStartedButton);

        // Making Frame Visible
        welcomeFrame.setVisible(true);
    }

    // Starting point of the program
    public static void main(String[] args) {
        // Creating class object
        Main obj = new Main();
    }
}

/*
Complexity Analysis
    Time Complexity : T(n) = O(mn), upper bound of the time taken to construct the trie.
where,
    n = length of the longest string
    m = number of strings in the string array.
 */