package com.company;

// Trie Node
public class TrieNode {
    // Declaration
    TrieNode[] child;                                   // to store child nodes
    int data;                                           // to store node value
    boolean isHop;                                      // to check whether node is hop or not
    String hopName;                                     // to store hop name

    // Constructor
    public TrieNode() {
        // Initialization
        this.child = new TrieNode[2];
        this.child[0] = null;
        this.child[1] = null;
        this.isHop = false;
        this.hopName = "";
    }
}


