package com.company;

// Importing Packages
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Class
public class Routing_GUI {
    // ConstructTrie Class Object
    ConstructTrie trie;                                                                     // to create trie data-structure

    // Declaration
    JFrame routingFrame;                                                                    // to create frame
    JTable routingTable, hopMatchedTable;                                                   // to create table
    DefaultTableModel routingTableModel, hopMatchedTableModel;                              // to handle operations on table
    JScrollPane routingTableScrollPane, hopMatchedTableScrollPane, searchOutputScrollPane;  // to add scrolling facility
    JButton addHopsButton, findIpAddressHopButton;                                          // Button object
    JTextField ipAddressTextField;                                                          // to take IP address prefix
    JTextArea searchOutput;                                                                 // to display output
    boolean isHopAdded;                                                                     // to identify hops are added to trie or not

    // Class Constructor
    public Routing_GUI() {
        // Frame Initialization
        routingFrame = new JFrame("Longest Prefix Matching");

        // Configuring Frame
        routingFrame.setLayout(null);
        routingFrame.setLocation(300, 50);
        routingFrame.setResizable(false);
        routingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        routingFrame.setSize(800, 600);

        /*
        ----------------------------------------------------------------------
                                Object Initialization
        ----------------------------------------------------------------------
        */
        trie = new ConstructTrie();

        // Routing Table and its widgets
        routingTableModel = new DefaultTableModel();
        routingTable = new JTable(routingTableModel);
        routingTableScrollPane = new JScrollPane(routingTable);
        addHopsButton = new JButton("Add Hops");

        // Matched Hop Table and its widgets
        hopMatchedTableModel = new DefaultTableModel();
        hopMatchedTable = new JTable(hopMatchedTableModel);
        hopMatchedTableScrollPane = new JScrollPane(hopMatchedTable);

        // IP Address text field and its widgets
        ipAddressTextField = new JTextField();
        ipAddressTextField.setText("Enter IP Address Prefix");
        findIpAddressHopButton = new JButton("Find Hop");

        // JTextArea (Output Area) and its widgets
        searchOutput = new JTextArea();
        searchOutput.setEditable(false);
        searchOutput.setText("Output Window..!");
        searchOutputScrollPane = new JScrollPane(searchOutput);

        /*
        ----------------------------------------------------------------------
             Setting isHopAdded as false because trie is not created yet
        ----------------------------------------------------------------------
         */
        isHopAdded = false;

        /*
        ----------------------------------------------------------------------
                                Formatting Widgets
        ----------------------------------------------------------------------
         */
        Font f1 = new Font("", Font.PLAIN, 17);
        // Set font to RoutingTable
        routingTable.setFont(f1);
        routingTable.setRowHeight(25);

        // Set font to HopMatchedTable
        hopMatchedTable.setFont(f1);
        hopMatchedTable.setRowHeight(25);

        // Set font to Output JTextArea
        searchOutput.setFont(f1);

        // Set font to TextField
        ipAddressTextField.setFont(f1);

        /*
        ----------------------------------------------------------------------
                                Add Columns to Table
        ----------------------------------------------------------------------
         */
        // Routing table
        routingTableModel.addColumn("Sr.No");
        routingTableModel.addColumn("Hop Address");
        routingTableModel.addColumn("Hop Name");

        // Hop Matched Table
        hopMatchedTableModel.addColumn("Matched Hop");

        /*
        ----------------------------------------------------------------------
                                  Action Listeners
        ----------------------------------------------------------------------
         */
        // AddHop Button Action Listener
        addHopsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // If hops are not already added into trie then if will execute otherwise else will execute
                if (!isHopAdded) {
                    // Create object of ReadRoutingTable Class to access file data
                    ReadRoutingTable readTable = new ReadRoutingTable();

                    // call readRoutingTableFile() method to read file in which routing table stored
                    Object[][] routingTableData = readTable.readRoutingTableFile();

                    // Method called to insert hop data into Trie and JTable
                    insertHop(routingTableData);

                    // Making isHopAdded as true to identify that hops are added into trie
                    isHopAdded = true;

                    // Display dialog box to user
                    JOptionPane.showMessageDialog(null, "Trie For Hops Constructed Successfully..!");
                } else {
                    // Display dialog box to user
                    JOptionPane.showMessageDialog(null, "Hops are already added..!");
                }
            }
        });

        // Find Hop Button Action Listener
        findIpAddressHopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get IP Address prefix from the JTextField
                String ipAddress = ipAddressTextField.getText();

                // Check that IP address is entered into correct and proper format
                Pattern p1 = Pattern.compile("[0-1]+");
                Matcher m1 = p1.matcher(ipAddress);
                boolean isMatchFound = m1.matches();

                 /*
                 -------------------------------------
                         Remove Hop Matched Rows
                 -------------------------------------
                 */
                // Get the current ros count which are already present into HopMatched Table
                int rowCnt = hopMatchedTableModel.getRowCount();
                // Remove all the rows from that HopMatched Table
                for (int i = 0; i < rowCnt; i++) {
                    hopMatchedTableModel.removeRow(0);
                }

                // If IP Prefix entered into correct format then if will execute otherwise else will execute
                if (isMatchFound) {
                    // If hops are already added into trie then if will execute otherwise else will execute
                    if (isHopAdded) {
                        // Vector to store matched Hop Names
                        Vector v1 = trie.findHops(ipAddress);

                        // If the vector is not empty then if will execute otherwise else will execute
                        if (v1.size() != 0) {
                            // for loop to display Matched Hops into JTable
                            for (int i = 0; i < v1.size(); i++) {
                                // Creating array of Object Class to store Matched Hop Details
                                Object[] o1 = new Object[1];

                                // Get Hop Name from vector and store into 0th location of object
                                o1[0] = v1.get(i);

                                // Insert Matched Hop Name row into JTable
                                hopMatchedTableModel.insertRow(i, o1);
                            }

                            // Display message into OutputPanel
                            searchOutput.setText("Output:\nHop Matched..!\nIP Prefix - " + ipAddress +
                                    "\nBest Matched Hop - " + v1.get(v1.size() - 1) + "\nPackets are forwarded to "
                                    + v1.get(v1.size() - 1) + " hop..!");

                            // Display dialog box to user
                            JOptionPane.showMessageDialog(null, "Matched Hop - " + v1.get(v1.size() - 1));

                            // Clear JTextField
                            ipAddressTextField.setText("");
                        } else {
                            // Display message into OutputPanel
                            searchOutput.setText("Output:\nNo Match Found...Packet Discarded..!");

                            // Display dialog box to user
                            JOptionPane.showMessageDialog(null, "Not Hop Matched");
                        }
                    } else {
                        // Display message into OutputPanel
                        searchOutput.setText("Output:\nHops are not present in routing table..!");

                        // Display dialog box to user
                        JOptionPane.showMessageDialog(null, "Hops are not present");
                    }

                } else {
                    // Display message into OutputPanel
                    searchOutput.setText("Output:\nPlease enter valid ip address..!");

                    // Display dialog box to user
                    JOptionPane.showMessageDialog(null, "Please Enter Valid IP Address in Binary Format");
                }
            }
        });

        /*
        ----------------------------------------------------------------------
                            Setting Positions to Widgets
        ----------------------------------------------------------------------
         */
        // Routing Table and button
        routingTableScrollPane.setBounds(0, 0, 785, 197);
        addHopsButton.setBounds(315, 220, 150, 30);

        // Hop Matched Table
        hopMatchedTableScrollPane.setBounds(0, 270, 260, 292);
        searchOutputScrollPane.setBounds(265, 270, 515, 150);

        // IP address TextField and button
        ipAddressTextField.setBounds(340, 450, 350, 30);
        findIpAddressHopButton.setBounds(460, 490, 100, 30);

        /*
        ----------------------------------------------------------------------
                                Add Widgets to Frame
        ----------------------------------------------------------------------
         */
        routingFrame.add(routingTableScrollPane);
        routingFrame.add(addHopsButton);
        routingFrame.add(hopMatchedTableScrollPane);
        routingFrame.add(searchOutputScrollPane);
        routingFrame.add(ipAddressTextField);
        routingFrame.add(findIpAddressHopButton);

        /*
        ----------------------------------------------------------------------
                                Make Frame Visible
        ----------------------------------------------------------------------
         */
        routingFrame.setVisible(true);
    }

    // Method to add hop into trie and insert that hop information into JTable
    public void insertHop(Object[][] routingTableData) {
        // Add Routing Table Values to JTable
        for (int i = 0; i < routingTableData.length; i++) {
            // Adding hop address prefix to trie
            trie.insert(String.valueOf(routingTableData[i][1]), String.valueOf(routingTableData[i][2]));

            // Adding * at last of Hop Address Prefix
            routingTableData[i][1] = routingTableData[i][1] + "*";

            // Inserting Hop information row into JTable
            routingTableModel.insertRow(i, routingTableData[i]);
        }
    }
}
