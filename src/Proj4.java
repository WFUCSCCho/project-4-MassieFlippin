import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
        ArrayList<RealEstateData> originalArray = new ArrayList<>();
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

        //Insert into the hash table and calculate the run time
        SeparateChainingHashTable<RealEstateData> hashTable = new SeparateChainingHashTable<>();
        long startTime = System.nanoTime();
        for(RealEstateData data : originalArray) {
            hashTable.insert(data);
        }
        long endTime = System.nanoTime();
        //Output the insertion time
        System.out.println("Data inserted into the hash table.");
        for (RealEstateData data : originalArray) {
            System.out.println(data);
        }
        long runtime = endTime-startTime;
        writeToFile("Sorted Hash Table Insert Runtime: " + runtime, "./analysis.txt" );
        writeToFile("\n", "./analysis.txt" );

        //Search for an element in a sorted Hash table
        startTime = System.nanoTime();
        for(RealEstateData data : originalArray) {
            hashTable.contains(data);
        }
        endTime = System.nanoTime();
        runtime = endTime-startTime;
        writeToFile("Sorted Hash Table Search Runtime: " + runtime, "./analysis.txt" );
        writeToFile("\n", "./analysis.txt" );

        //Perform remove
        startTime = System.nanoTime();
        if (!originalArray.isEmpty()) {
            //select the first item from the data to be removed
            RealEstateData removedata = originalArray.get(0);
            //check to make sure the first item is in the hash table
            if (hashTable.contains(removedata)) {
                System.out.println("Sample data found. Removing: " + removedata);
                hashTable.remove(removedata);
            }
            //verify removal
            if (!hashTable.contains(removedata)) {
                System.out.println("Sample data successfully removed");
            } else {
                System.out.println("Failed to remove data.");
            }
        }else{
            System.out.println("Sample data not found.");
        }
        endTime = System.nanoTime();
        runtime = endTime-startTime;
        writeToFile("Sorted Hash Table Remove Runtime: " + runtime, "./analysis.txt" );
        writeToFile("\n", "./analysis.txt" );
        System.out.println("Test if values are still in data set: ");
        for (RealEstateData data : originalArray) {
            System.out.println(hashTable.contains(data));
        }
    }
    //Implement WriteToFileFunction
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
