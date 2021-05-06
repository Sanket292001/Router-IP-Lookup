package com.company;

// Import Packages
import java.util.Vector;

// Class
public class ConstructTrie {
    // Declaration
    TrieNode root;

    // Class Constructor
    public ConstructTrie(){
         this.root = new TrieNode();
    }

    // method to insert Hop Address to Trie
    public void insert(String hopAddress, String hopName) {
        // it is used to store value 0 or 1 which will help us to identify that we have to insert new node
        // as left or right child of the current node
        int index = 0;

        // initialize current by root node
        TrieNode current = root;

        // for loop to insert the Hop Address digits into the Trie
        for (int i = 0; i < hopAddress.length(); i++) {
            // If current digit of Hop Address is 0 then store 0 into index variable otherwise store 1
            // Here we will consider only 0 and 1 values because we are dealing with binary tries
            // If the value is 0 then we have to insert new node as a left child otherwise as a right child
            index = String.valueOf(hopAddress.charAt(i)).equals("0") ? 0 : 1;

            // Checking whether node is already present or not
            // if no then if will executed
            if (current.child[index] == null) {
                // create new TrieNode as a child of current node
                current.child[index] = new TrieNode();

                // Assign current digit value to data variable of TrieNode
                current.child[index].data = index;

                // make isHop to false for child node of current
                current.child[index].isHop = false;
            }

            // make the current point to its child node
            current = current.child[index];
        }

        // when the all hop address digits are stored into Trie then we will store hopName into last node and set isHop as true
        current.isHop = true;
        current.hopName = hopName;
    }

    // method to find the hops matching to current ip address
    public Vector findHops(String ipaddress) {
        // vector to store matched hop
        Vector<String> hopsVector = new Vector<>();

        // initialize j as 0, used to track current digit of ipaddress
        int j = 0;

        // get current digit of the ipaddress and store 0 or 1 into index
        int index =  String.valueOf(ipaddress.charAt(j)).equals("0") ? 0 : 1;

        // initialize current by root node
        TrieNode currentNode = root;

        // while loop to find the matching hops
        // loop will continue until child of currentNode is not NULL
        // and j is less than the total length of the IP prefix
        while (currentNode.child[index] != null && j < ipaddress.length()) {
            // make current node point to its child node
            currentNode = currentNode.child[index];

            // if current node has an isHop as true then that means it is one matched hop
            if (currentNode.isHop){
                // store the matched hopName into vector
                hopsVector.add(currentNode.hopName);
            }

            // try block for exception handling
            try{
                // increment j counter
                j++;

                // If j is less than length of ip address then if statement executes
                // otherwise else will execute
                if (j < ipaddress.length()){
                    // If current digit of Hop Address is 0 then store 0 into index variable otherwise store 1
                    index = String.valueOf(ipaddress.charAt(j)).equals("0") ? 0 : 1;
                }else{
                    // break the for loop
                    break;
                }
            }catch (ArrayIndexOutOfBoundsException arre){
                System.out.print("Exception: " + arre);
            }
        }

        // return vector that contain matched hops
        return hopsVector;
    }
}
