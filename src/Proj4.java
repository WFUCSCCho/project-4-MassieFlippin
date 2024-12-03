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

        //Search for an element in a Sorted BST and calculate the time
        startTime = System.nanoTime();
        for (RealEstateData data : originalArray) {
            sortedHash.contains(data);
        }
        endtime = System.nanoTime();
        time = endtime - startTime;
        System.out.println("Sorted Hash Search Runtime: " + time);
        writeToFile("Sorted Hash Search Runtime: " + time, "./analysis.txt");
        writeToFile("\n", "./analysis.txt");

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
