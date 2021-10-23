package esgi;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Launcher {
    public static final String ZERO  = " ||_ _ ||";
    public static final String ONE   = "       ||";
    public static final String TWO   = "  |___ | ";
    public static final String THREE = "   ___ ||";
    public static final String FOUR  = " |  _  ||";
    public static final String FIVE  = " | ___  |";
    public static final String SIX   = " ||___  |";
    public static final String SEVEN = "   _   ||";
    public static final String EIGHT = " ||___ ||";
    public static final String NINE  = " | ___ ||";

    public static final List<String> patterns = new ArrayList<>(
            List.of(ZERO,ONE,TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,NINE)
    );

    public static void main(String[] args) {
        List<String> entries = ocrFileToEntryList("src/esgi/file.txt");
        entriesToFile(entries);
    }

    private static void entriesToFile(List<String> entries) {
        try {
            FileWriter myWriter = new FileWriter("src/esgi/ouput.txt");
            for (String entry: entries) {
                String sequence = getSequenceFromEntry(entry);
                sequence = testForValidity(sequence);
                System.out.println(sequence);
                myWriter.write(sequence+"\n");
            }
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String testForValidity(String sequence) {
        StringBuilder testedSequence = new StringBuilder();
        testedSequence.append(sequence);
        if (sequence.contains("?")){
            testedSequence.append(" ILL");
        }else if(checkSum(sequence)){
            testedSequence.append(" ERR");
        }
        return testedSequence.toString();
    }

    private static boolean checkSum(String sequence) {
        int[] sequenceArray = stringToIntArray(sequence);
        int sumCheck = 0;
        for (int i = 0; i < sequenceArray.length; i++) {
            sumCheck += sequenceArray[i]* sequenceArray.length-i;
        }
        return sumCheck % 11 == 0;
    }

    private static int[] stringToIntArray(String sequence) {
        String [] stringArray = sequence.split("");
        int size = stringArray.length;
        int [] intArray = new int [size];
        for(int i=0; i<size; i++) {
            intArray[i] = Integer.parseInt(stringArray[i]);
        }
        return intArray;
    }

    public static List<String> ocrFileToEntryList(String src){
        List<String> entries = new ArrayList<String>();
        try {
            FileInputStream file = new FileInputStream(src);
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                StringBuilder entry = new StringBuilder();
                for (int i = 0; i < 3; i++) {
                    if(scanner.hasNextLine()){
                        entry.append(scanner.nextLine());
                    }
                }
                if(scanner.hasNextLine()){
                    scanner.nextLine();
                }
                entries.add(entry.toString());
            }
            scanner.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return entries;
    }

    private static String getSequenceFromEntry(String entry) {
        String[][] entryArray = getEntryArrayFromEntry(entry);
        StringBuilder sequence = new StringBuilder();
        for (int i = 1; i <= 9; i++) {
            String pattern = getThreeByThreeRowsFromArrayWithPos(entryArray, i);
            sequence.append(testStringPattern(pattern));
        }
        return sequence.toString();
    }

    public static String getThreeByThreeRowsFromArrayWithPos(String[][] array, int position) {
        StringBuilder result = new StringBuilder();
        position--;
        for (int i = position*3; i < position*3+3; i++) {
            for (int j = 0; j < 3; j++) {
                result.append(array[i][j]);
            }
        }
        return result.toString();
    }

    private static String[][] getEntryArrayFromEntry(String entry) {
        String[] arrayOfEntryChars = entry.split("");
        String[][] sequenceArray = new String[27][3];
        int pointer = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 27; j++) {
                if(pointer<arrayOfEntryChars.length){
                    sequenceArray[j][i] = arrayOfEntryChars[pointer];
                }
                pointer++;
            }
        }
        return sequenceArray;
    }

    public static String testStringPattern(String toTestPattern) {
        int index = 0;
        for (String pattern : patterns) {
            if(toTestPattern.equals(pattern)) {
                return String.valueOf(index);
            }
            index++;
        }
        return "?";
    }

}
