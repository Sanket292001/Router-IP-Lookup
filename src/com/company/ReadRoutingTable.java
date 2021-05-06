package com.company;

// Importing Packages
import java.io.IOException;
import java.io.RandomAccessFile;

public class ReadRoutingTable {
    // String storing file name in which hop details (Routing Table) is stored
    String FILE_NAME = "src/RoutingTable.txt";

    // Array to store Hop Details
    Object[][] fileDataArray;

    // Method to read file and return the Hop Details
    public Object[][] readRoutingTableFile(){
        try {
            // Class to open the file in read mode
            RandomAccessFile readFile = new RandomAccessFile(FILE_NAME, "r");

            // Counter is taken keep track of identify how much Hops are there in file
            int i=0;
            // loop to identify how much Hops are there in file
            while (readFile.getFilePointer() < readFile.length()){
                String record = readFile.readLine();
                // If line is empty then ignore that line (fo not increase counter)
                if (!record.equals("")) {
                    i++;
                }
            }

            // Initialize array to store Hop Data having row count as 'i'
            fileDataArray = new String[i][3];

            // Moving the file pointer to 0 position in the file
            readFile.seek(0);

            // make counter i as 0, because here we have to start from index 0
            i=0;

            // while loop to read file data line by line
            while (readFile.getFilePointer() < readFile.length()){
                // Read line
                String record = readFile.readLine();
                // If line is not empty then if will execute otherwise not
                if (!record.equals("")){
                    // Split Hop data by '!' and data get stored into array
                    String[] fields = record.split("!");

                    // Assign values to array
                    fileDataArray[i][0] = String.valueOf(i+1);          // Sr No
                    fileDataArray[i][1] = fields[0];                    // Hop Address
                    fileDataArray[i][2] = fields[1];                    // Hop Name

                    // Increment Counter
                    i++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return Hop data array
        return fileDataArray;
    }
}
