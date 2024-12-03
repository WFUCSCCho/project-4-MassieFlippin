/**
 * @file: Proj4.java
 * @description: The Proj4.java implements the use of SeparateChainingHashTable. This program calculates the run times for
 * insertion, search, and deletion of objects into hash tables with data that is sorted, shuffled, or reversed.
 * @author: Massie Flippin
 * @date: December 3rd, 2024
 ************************/

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Proj4 {
    public static void main(String[] args) throws IOException {
        // Use command line arguments to specify the input file
        if (args.length != 2) {
            System.err.println("Usage: java TestAvl <input file> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        int numLines = Integer.parseInt(args[1]);

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line
        inputFileNameScanner.nextLine();

        // FINISH ME
        //create two array list to store the Real Estate Data
        ArrayList<RealEstateData> originalArray = new ArrayList<RealEstateData>();
        //Read the file line and store the data line by line.
        for(int i = 0; i < numLines && inputFileNameScanner.hasNextLine(); i++) {
            String line = inputFileNameScanner.nextLine().trim();//trims away any data
            if (line.isEmpty()) continue;// skip the empty lines
            String[] parts = line.split(",");
            //create a new RealEstateData object
            try {
                if (parts.length < 16) {
                    continue;
                }
                //store the data
                RealEstateData data = new RealEstateData(
                        Integer.parseInt(parts[0]), //ID
                        parts[1], //PossesionStatus
                        parts[2], // Commercial
                        parts[3], //Developer
                        Integer.parseInt(parts[4]), //price
                        Integer.parseInt(parts[5]), //sqftprice
                        parts[6],//furnished
                        Integer.parseInt(parts[7]), //bathroom
                        parts[8], //direction facing
                        parts[9], //transaction
                        parts[10], //type
                        parts[11], //city
                        Integer.parseInt(parts[12]), // bedrooms
                        Integer.parseInt(parts[13]), //floors
                        parts[14], //isprimelocation
                        parts[15]); // lifespan
                //add data to the Array
                originalArray.add(data);
                //Catch statement showing what lines might contain faulty data, skip and continue with insertion / search
            }catch (NumberFormatException e) {
                System.err.println("Error parsing line: " + line);
            }
        }
        inputFileNameScanner.close();


        //Sort the Data
        Collections.sort(originalArray);

        //Calculate the Insert and Search time for a sorted Hash Table
        SeparateChainingHashTable<RealEstateData> sortedHash = new SeparateChainingHashTable<>();
        long startTime = System.nanoTime();
        for (RealEstateData data : originalArray) {
            sortedHash.insert(data);
        }
        long endtime = System.nanoTime();
        long time = endtime - startTime;
        System.out.println("Sorted Hash Insert Runtime: " + time);
        writeToFile("Sorted Hash Insert Runtime: " + time, "./analysis.txt");
        writeToFile("\n", "./analysis.txt");

        //Search for an element in a Sorted Hash Table and calculate the time
        startTime = System.nanoTime();
        for (RealEstateData data : originalArray) {
            sortedHash.contains(data);
        }
        endtime = System.nanoTime();
        time = endtime - startTime;
        System.out.println("Sorted Hash Search Runtime: " + time);
        writeToFile("Sorted Hash Search Runtime: " + time, "./analysis.txt");
        writeToFile("\n", "./analysis.txt");

        // Demonstrate the remove operation
        System.out.println("Removing all items from the hash table ");
        startTime = System.nanoTime();
        sortedHash.makeEmpty();
        endtime = System.nanoTime();
        time = endtime - startTime;
        writeToFile("Sorted Hash MakeEmpty Runtime: " + time, "./analysis.txt");

        //Verify the hash table is empty for checking purposes
        boolean isEmpt = true;
        for (RealEstateData data : originalArray) {
            if (sortedHash.contains(data)) {
                isEmpt = false;
                System.out.println("System not working properly");
                break;
            }
        }
        if (isEmpt) {
            System.out.println("All items successfully removed. The hash table is now empty");
        }else{
            System.out.println("Failed to remove all items from the hash table");
        }

        //Shuffle the data
        Collections.shuffle(originalArray);

        //Calculate the Insert and Search time for a shuffled Hash Table
        SeparateChainingHashTable<RealEstateData> shuffledHash = new SeparateChainingHashTable<>();
        startTime = System.nanoTime();
        for (RealEstateData data : originalArray) {
            shuffledHash.insert(data);
        }
        endtime = System.nanoTime();
        time = endtime - startTime;
        writeToFile("\n", "./analysis.txt");
        System.out.println("Shuffled Hash Insert Runtime: " + time);
        writeToFile("Shuffled Hash Insert Runtime: " + time, "./analysis.txt");
        writeToFile("\n", "./analysis.txt");

        //Search for an element in a Shuffled Hash Table and calculate the time
        startTime = System.nanoTime();
        for (RealEstateData data : originalArray) {
            shuffledHash.contains(data);
        }
        endtime = System.nanoTime();
        time = endtime - startTime;
        System.out.println("Shuffled Hash Search Runtime: " + time);
        writeToFile("Shuffled Hash Search Runtime: " + time, "./analysis.txt");
        writeToFile("\n", "./analysis.txt");

        // Demonstrate the remove operation
        System.out.println("Removing all items from the shuffled hash table ");
        startTime = System.nanoTime();
        shuffledHash.makeEmpty();
        endtime = System.nanoTime();
        time = endtime - startTime;
        writeToFile("Shuffled Hash MakeEmpty Runtime: " + time, "./analysis.txt");

        //Verify the hash table is empty for checking purposes
        isEmpt = true;
        for (RealEstateData data : originalArray) {
            if (shuffledHash.contains(data)) {
                isEmpt = false;
                break;
            }
        }
        if (isEmpt) {
            System.out.println("All items successfully removed. The hash table is now empty");
        }else{
            System.out.println("Failed to remove all items from the hash table");
        }
        //Shuffle the data
        Collections.reverse(originalArray);

        //Calculate the Insert and Search time for a shuffled Hash Table
        SeparateChainingHashTable<RealEstateData> reversedHash = new SeparateChainingHashTable<>();
        startTime = System.nanoTime();
        for (RealEstateData data : originalArray) {
            reversedHash.insert(data);
        }
        endtime = System.nanoTime();
        time = endtime - startTime;
        writeToFile("\n", "./analysis.txt");
        System.out.println("Reversed Hash Insert Runtime: " + time);
        writeToFile("Reversed Hash Insert Runtime: " + time, "./analysis.txt");
        writeToFile("\n", "./analysis.txt");

        //Search for an element in a Shuffled Hash Table and calculate the time
        startTime = System.nanoTime();
        for (RealEstateData data : originalArray) {
            reversedHash.contains(data);
        }
        endtime = System.nanoTime();
        time = endtime - startTime;
        System.out.println("Reversed Hash Search Runtime: " + time);
        writeToFile("Reversed Hash Search Runtime: " + time, "./analysis.txt");
        writeToFile("\n", "./analysis.txt");

        // Demonstrate the remove operation
        System.out.println("Removing all items from the reversed hash table ");
        startTime = System.nanoTime();
        reversedHash.makeEmpty();
        endtime = System.nanoTime();
        time = endtime - startTime;
        writeToFile("Reversed Hash MakeEmpty Runtime: " + time, "./analysis.txt");
        writeToFile("\n", "./analysis.txt");
        //Verify the hash table is empty for checking purposes
        isEmpt = true;
        for (RealEstateData data : originalArray) {
            if (reversedHash.contains(data)) {
                isEmpt = false;
                break;
            }
        }
        if (isEmpt) {
            System.out.println("All items successfully removed. The hash table is now empty");
        }else{
            System.out.println("Failed to remove all items from the hash table");
        }
    }
    //implement the writeToFile path.
    public static void writeToFile(String content, String filePath) {
        try {
            //FileWriter and BufferedWriter to assure that when writeToFile is called it can write to a file.
            FileWriter fileWriter = new FileWriter(filePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            fileWriter.write(content);
            bufferedWriter.close();
            fileWriter.close();
        }
        catch (IOException ignored) {}
    }
}
