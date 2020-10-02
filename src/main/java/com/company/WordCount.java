package com.company;

import java.io.*;
import java.util.*;

// to simplify the program, I did not use an external dictionary to actually validate if certain strings were actually words. if a string contains only letters(excluding apostrophes,) it is considered a word
// (i.e. "word", "asdf", "j", "can't", etc. are considered words because the strings only contain letters)
// non-letters are separated from the strings (i.e. if a line contains "they're12" the string will be saved as "they're" and will count as a word while the "12" is not saved)
public class WordCount {

    public Map<String, Integer> getWordCount(File file) {
        // a map is used to store the words and how often they appear due to the key:value pairs a map uses
        Map<String, Integer> map = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            // this variable is important because it needs to be assigned to reader.readLine() so we can call the .split() function to separate the words
            // if this variable isn't assigned and we call reader.readLine().split(), it would skip every other line because we are already reading a line in the while loop condition
            String line;
            while ((line = reader.readLine()) != null) {
                // this lets us skip empty lines and lines that only have whitespace
                if (line.trim().length() > 0) {

                    // this separates the line based on non-letters excluding apostrophes
                    // (i.e. the line "they're12 they're" will split on the "1," "2," and whitespace so that wordList[0]="they're" and wordList[1]="they're" which will count as 2 instances of the word "they're")
                    // the regex also handles consecutive delimiters
                    List<String> wordList = new ArrayList<>(Arrays.asList(line.split("[^a-zA-Z']+")));
                    // this check is primarily used when a line only contains delimiters as split() needs at least 1 non-delimiter in order to fill a list
                    if(!wordList.isEmpty()){

                        // when the delimiter of a line is the first character of the line, the split returns the delimited character as an empty string and will store it in the arraylist
                        // (i.e. the line "+!+of- -of _of" has the first character of a line as "+" and the line will be stored as wordList[0]="", wordList[1]="of", wordList[2]="of", and wordList[3]="of"
                        // due to this limitation, the first element of the list should be removed if it is an empty string
                        if (wordList.get(0).equals("")){
                            wordList.remove(0);
                        }
                        // this adds the words to the map
                        for (String s : wordList){
                            // if the key(word) exists in the map, increment the value(number of appearances) by 1
                            if (map.containsKey(s)){
                                map.compute(s, (k, v) -> v + 1);
                            }
                            // if the key(word) doesn't exist in the map, add it
                            else {
                                map.put(s, 1);
                            }
                        }
                    }
                }
            }
            reader.close();
        }
        catch (IOException e){
            System.out.println("An error occurred.");
        }
        return map;
    }

    // this prints the map in order according to value(the number of each words appearance) because the map is not sorted
    public void printSortedMap(Map<String, Integer> map){
        map.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach(entry-> System.out.println(entry.getValue() + " " + entry.getKey()));
    }
}
